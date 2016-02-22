package com.credithc.generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/**
 * 项目模板监听类
 * 
 * */
public class CheckSampleDir implements ActionListener{
	
	private JTextField tSampleDir;
	private JTextField tTargetDir;
	private JTextField txtProjectName;
	
	public CheckSampleDir(JTextField tSampleDir, JTextField tTargetDir, JTextField txtProjectName){
		
		this.tSampleDir=tSampleDir;
		this.tTargetDir=tTargetDir;
		this.txtProjectName = txtProjectName;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CheckGenerator generator = new CheckGenerator(tSampleDir.getText(), tTargetDir.getText(), txtProjectName.getText());

		generator.generateTemplate();
		
		
	}

}
