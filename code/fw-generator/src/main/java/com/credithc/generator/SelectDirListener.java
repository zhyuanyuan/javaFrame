package com.credithc.generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 文件类路径
 * 
 * */
public class SelectDirListener implements ActionListener {
	
	private JTextField text;
	
	public SelectDirListener(JTextField text){
		this.text = text;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc=new JFileChooser();  
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
		jfc.showDialog(new JLabel(), "选择");  
		File file=jfc.getSelectedFile();  
		if(file != null){
			text.setText(file.getPath());
		}
	}

}
