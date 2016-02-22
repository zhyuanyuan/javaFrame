package com.credithc.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 项目模板类
 * 下载项目模板是修改包名，路径名
 * */
public class CheckGenerator {
	
	private static String COMMON_REPLACE_FILE_CONTEXT_PATTERN = "^(import|package|.*<%.*import.*=|.*com.*dao.*|\\s*<.*>fw\\-.*<.*>|\\s*<.*>.*<.*>).*";
	private static String PROPERTIES_REPLACE_FILE_CONTEXT_PATTERN = "^(.+=).*";
	private static String SAMPLE_PLACEHOLDER = "sample|fw-sample";

	private String tSampleDir;

	private String tTargetDir;

	private String projectName;


	public CheckGenerator(String tSampleDir, String tTargetDir,
			String projectName) {
		this.tSampleDir = tSampleDir;
		this.tTargetDir = tTargetDir;
		this.projectName = projectName;
	}

	public void generateTemplate() {
		File sampleDirFile = new File(tSampleDir);
		if (!sampleDirFile.exists() || !sampleDirFile.isDirectory()) {
			System.err.println(tSampleDir + " is not a dirctory");
			return;
		}

		// 模板路径
		File targetDirFile = new File(tTargetDir);
		if (targetDirFile.exists()) {
			System.out.println("clean sub files of " + targetDirFile);
			File[] tdrs = targetDirFile.listFiles();
			for (File tdr : tdrs) {
				if (!tdr.getName().endsWith("include")
						&& !tdr.getName().endsWith("svn")) {
					delete(tdr);
				}
			}
		} else {
			targetDirFile.mkdirs();
		}

		File[] files = sampleDirFile.listFiles(new SampleFileFilter());

		for (File f : files) {
			scanFile(f, tTargetDir, projectName);
		}
	}

	private static void scanFile(File f, String tTargetDir, String rojectName) {
		if (f.isDirectory()) {
			String dirName = f.getName();
			dirName = replacePathPlaceholder(dirName, rojectName);
			File newDir = new File(tTargetDir, dirName);
			newDir.mkdirs();

			File[] files = f.listFiles(new SampleFileFilter());
			for (File f2 : files) {
				scanFile(f2, tTargetDir + File.separator + dirName, rojectName);
			}
		} else {
			String fName = f.getName();
			fName = replacePathPlaceholder(fName, rojectName);
			File newFile = new File(tTargetDir, fName);
			try {
				newFile.createNewFile();
				replaceFilePlaceholder(f, newFile, rojectName);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return;
			}
		}

	}

	private static String replacePathPlaceholder(String dirName,
			String projectName) {
		return dirName.replaceAll(SAMPLE_PLACEHOLDER, projectName);
	}

	private static void replaceFilePlaceholder(File sourceFile, File newFile,
			String projectName) {
		try (Scanner scanner = new Scanner(sourceFile);
				PrintWriter pr = new PrintWriter(new FileWriter(newFile));) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line != null) {
					if (line.matches(COMMON_REPLACE_FILE_CONTEXT_PATTERN)) {
						line = line.replaceAll(SAMPLE_PLACEHOLDER, projectName);
					} else if (newFile.getName().endsWith(".properties")
							&& line.matches(PROPERTIES_REPLACE_FILE_CONTEXT_PATTERN)) {
						line = line.replaceAll(SAMPLE_PLACEHOLDER, projectName);
					}
				}
				pr.println(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println(sourceFile + " is not found");
			return;
		} catch (IOException e) {
			System.err.println("replace file placeholder error");
			return;
		}

	}


private void delete(File f) {
	if (f.isFile()) {
		f.delete();
	} else {
		File[] fs = f.listFiles();
		for (File f2 : fs) {
			delete(f2);
		}
		f.delete();
	}
}
}
