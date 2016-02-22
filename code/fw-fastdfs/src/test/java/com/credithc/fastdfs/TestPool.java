package com.credithc.fastdfs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.credithc.fastdfs.FastdfsServer;
import com.credithc.fastdfs.FastdfsServerImpl;


public class TestPool {
	FastdfsServer is = new FastdfsServerImpl("10.100.1.74",22122,10);
	
	public static void main(String[] args) throws Exception{
		TestPool tp = new TestPool();
		String fileid =  tp.uploadFile()  ;
		System.out.println(fileid);
		System.out.println(tp.getFileByID(fileid));
		tp.deleteFile(fileid);
		System.out.println(tp.getFileByID(fileid));
	}
	
	public String uploadFile() throws Exception {
		
		File file = new File("d:\\kkk.txt");
		
		return is.uploadFile(file);
		
	}
	
	public String getFileByID(String fileId) throws Exception {
		File file = new File("d:\\"+fileId.substring(20)+".txt");
		OutputStream output = new FileOutputStream(file);
		BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
		bufferedOutput.write(is.getFileByID(fileId));
		bufferedOutput.flush();
		bufferedOutput.close();
		return "OK";
		
	}
	
	public boolean deleteFile(String fileId) throws Exception {
		
		return is.deleteFile(fileId);
		
	}
}
