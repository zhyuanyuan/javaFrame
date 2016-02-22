package com.credithc.generator;

import java.util.ListIterator;

import cn.org.rapid_framework.generator.GeneratorConstants;
import cn.org.rapid_framework.generator.GeneratorFacade;

/**
 * 生成代码类
 * 
 * */
public class CodeGenerator {

	private String dbUserName;
	private String dbPassword;
	private String dbUrl;
	private String dbDriver;

	public CodeGenerator(String cbxDbType,String sampleDir, String targetDir,
			String projectName, String dbUserName, String dbPassword,
			String dbUrl, String dbDriver, String dbName, String jTable) {
		
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
		this.dbUrl = dbUrl;
		this.dbDriver = dbDriver;

	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public void generatProject() {

		try {
			System.getProperties().put("outRoot", "generator-output");
			System.getProperties().put(GeneratorConstants.JDBC_DRIVER.code,
					dbDriver);
			System.getProperties().put(GeneratorConstants.JDBC_URL.code, dbUrl);
			System.getProperties().put(GeneratorConstants.JDBC_USERNAME.code,
					dbUserName);
			System.getProperties().put(GeneratorConstants.JDBC_PASSWORD.code,
					dbPassword);

			GeneratorFacade g = new GeneratorFacade();
			g.getGenerator().setTemplateRootDir("template");
			// 先删除输出目录
			g.deleteOutRootDir();

			GenerateCodeListener gc = new GenerateCodeListener(null, null,
					null, null, null, null, null, null, null,null);

			
			ListIterator<String> iter = gc.getList().listIterator();

			while (iter.hasNext()) {
				String str = iter.next();
				g.generateByTable(str);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
