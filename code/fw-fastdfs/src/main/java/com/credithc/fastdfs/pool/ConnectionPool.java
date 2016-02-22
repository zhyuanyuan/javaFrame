package com.credithc.fastdfs.pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

public class ConnectionPool {

	// busy connection instances
	private ConcurrentHashMap<TrackerServer, Object> busyConnectionPool = null;
	// idle connection instances
	private ArrayBlockingQueue<TrackerServer> idleConnectionPool = null;
	// delay lock for initialization

	// the connection string for ip
	private String tgStr = null;
	// the server port
	private int port = 22122;
	// the limit of connection instance
	private int size = 2;
	
	private Object obj = new Object();
	
	//heart beat
	HeartBeat beat=null;

	public ConnectionPool(String tgStr, int port, int size) {
		this.tgStr = tgStr;
		this.port = port;
		this.size = size;
		init();
		//注册心跳
		beat=new HeartBeat(this);
		beat.beat();
	}

	/**
	 * init the connection pool
	 * 
	 * @param size
	 */
	private void init() {
		initClientGlobal();
		busyConnectionPool = new ConcurrentHashMap<TrackerServer, Object>();
		idleConnectionPool = new ArrayBlockingQueue<TrackerServer>(this.size);
		TrackerServer trackerServer = null;
		try {
			TrackerClient trackerClient = new TrackerClient();
			for (int i = 0; i < size; i++) {
				trackerServer = trackerClient.getConnection();
				org.csource.fastdfs.ProtoCommon.activeTest(trackerServer.getSocket());
				idleConnectionPool.add(trackerServer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (trackerServer != null) {
				try {
					trackerServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 1. pop one connection from the idleConnectionPool,
	// 2. push the connection into busyConnectionPool;
	// 3. return the connection
	// 4. if no idle connection, do wait for wait_time seconds, and check again
	public TrackerServer checkout(int waitTimes) throws InterruptedException {
		TrackerServer client1 = idleConnectionPool.poll(waitTimes,
				TimeUnit.SECONDS);
		if (client1 == null) {
			FastdfsServerPoolSysout
					.warn("ImageServerPool wait time out ,return null");
			throw new NullPointerException(
					"ImageServerPool wait time out ,return null");
		}
		busyConnectionPool.put(client1, obj);
		return client1;
	}

	// 1. pop the connection from busyConnectionPool;
	// 2. push the connection into idleConnectionPool;
	// 3. do nessary cleanup works.
	public void checkin(TrackerServer client1) {
		if (busyConnectionPool.remove(client1) != null) {
			idleConnectionPool.add(client1);
		}
	}

	// so if the connection was broken due to some erros (like
	// : socket init failure, network broken etc), drop this connection
	// from the busyConnectionPool, and init one new connection.
	public synchronized void drop(TrackerServer trackerServer) {
		// first less connection
		//删除一个无效的连接，如果得到新连建也是无效，则启动detector线程，用于检测什么时候可以正常连接起来
		//一旦检查成功，将相应属性修改hasConnectionException修改为false，释放先前的连接，并重新建立连接池。
		try {
			trackerServer.close();
		} catch (IOException e1) {
		}
		if (busyConnectionPool.remove(trackerServer) != null) {
			try {
				FastdfsServerPoolSysout
						.warn("ImageServerPool drop a connnection");
				FastdfsServerPoolSysout.warn("ImageServerPool size:"
						+ (busyConnectionPool.size() + idleConnectionPool
								.size()));
				TrackerClient trackerClient = new TrackerClient();
				trackerServer = trackerClient.getConnection();
			} catch (IOException e) {
				trackerServer = null;
				FastdfsServerPoolSysout
						.warn("ImageServerPool getConnection generate exception");
				e.printStackTrace();
			} finally {
				if(!isContinued(trackerServer)){
					return;
				}
				//变成传过数据的
				try {
					org.csource.fastdfs.ProtoCommon.activeTest(trackerServer.getSocket());
					idleConnectionPool.add(trackerServer);
					FastdfsServerPoolSysout.warn("ImageServerPool add a connnection");
					FastdfsServerPoolSysout.warn("ImageServerPool size:"
							+ (busyConnectionPool.size() + idleConnectionPool
									.size()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public boolean isContinued(TrackerServer trackerServer){
		if (trackerServer == null && hasConnectionException) {
			return false;
		}
		if (trackerServer == null) {
			hasConnectionException = true;
			// only a thread;
			detector();
		}
		if (hasConnectionException) {
			//代表detector正在运行，就算获得连接，也要等detector做完
			return false;
		}
		return true;
	}

	private void detector() {

		new Thread() {
			@Override
			public void run() {
				String msg="detector connection fail to "+tgStr;
				while (true) {
					TrackerServer trackerServer = null;
					TrackerClient trackerClient = new TrackerClient();
					try {
						trackerServer = trackerClient.getConnection();
						Thread.sleep(5000);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally{
						if(trackerServer!=null){
							msg="detector connection success to "+tgStr;
							break;
						}
						FastdfsServerPoolSysout.warn("current ImageServerPool has size:"
								+ (busyConnectionPool.size() + idleConnectionPool
										.size()));
						FastdfsServerPoolSysout.warn(msg);
					}
				}
				FastdfsServerPoolSysout.warn(msg);
				
//				//close before tracker server
//				if(busyConnectionPool.size()!=0){
//					ImageServerPoolSysout.warn("busyConnectionPool start close trackerserver");
//					for(Entry<TrackerServer, Object> entry:busyConnectionPool.entrySet()){
//						try {
//							entry.getKey().close();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
				
				if(idleConnectionPool.size()!=0){
					FastdfsServerPoolSysout.warn("idleConnectionPool start close trackerserver");
					FastdfsServerPoolSysout.warn(msg);

					for(int i=0;i<size;i++){
						TrackerServer ts=idleConnectionPool.poll();
						if(ts!=null){
							try {
								ts.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				//re init
				hasConnectionException=false;
				init();
			}
		}.start();
	}

	boolean hasConnectionException = false;

	private void initClientGlobal() {
		InetSocketAddress[] trackerServers = new InetSocketAddress[1];
		trackerServers[0] = new InetSocketAddress(tgStr, port);
		ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServers));
		// 连接超时的时限，单位为毫秒
		ClientGlobal.setG_connect_timeout(2000);
		// 网络超时的时限，单位为毫秒
		ClientGlobal.setG_network_timeout(30000);
		ClientGlobal.setG_anti_steal_token(false);
		// 字符集
		ClientGlobal.setG_charset("UTF-8");
		ClientGlobal.setG_secret_key(null);
	}

	public ArrayBlockingQueue<TrackerServer> getIdleConnectionPool() {
		return idleConnectionPool;
	}

	
	
	
}
