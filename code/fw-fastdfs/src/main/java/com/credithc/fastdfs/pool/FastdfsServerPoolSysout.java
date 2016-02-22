package com.credithc.fastdfs.pool;

import java.util.logging.Logger;

public class FastdfsServerPoolSysout {
	
	private static Logger logger=Logger.getLogger("FastdfsServerPoolSysout");
	
	public static void info(Object o){
		logger.info(o.toString());
	}
	public static void warn(Object o) {
		logger.warning(o.toString());
	}

}
