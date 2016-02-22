package com.credithc.generator;

import java.io.File;
import java.io.FileFilter;

/**
 * 过滤文件
 * 
 * */
public class SampleFileFilter implements FileFilter{
	private String [] excludeDir = new String[]{".settings", "target", ".svn"};
	private String [] excludeFile = new String[]{".classpath"};
	private String [] excludeSubfix = new String[]{"class","project"};
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()){
			if(exists(excludeDir, f.getName())){
				return false;
			}
			return true;
		}else{
			if(exists(excludeFile, f.getName())){
				return false;
			}
			String fileName = f.getName();
			int indexDot = fileName.indexOf('.');
			if(indexDot >= 0){
				String subfix = fileName.substring(indexDot + 1);
				if(exists(excludeSubfix, subfix)){
					return false;
				}
			}
			return true;
		}
	}
	
	private boolean exists(String[] exclude, String name) {
		if(name == null || "".equals(name.trim())){
			return false;
		}
		if(exclude != null && exclude.length > 0){
			for(String ex : exclude){
				if(ex.equals(name)){
					return true;
				}
			}
		}
		return false;
	}
	
}
