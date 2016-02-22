package com.credithc.generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * 生成代码监听类
 * 
 * */
public class GenerateCodeListener implements ActionListener {
	

	private JComboBox<String> cbxDbType;
	private JTextField txtSampleDir;
	private JTextField txtTargetDir;
	private JTextField txtProjectName;
	private JTextField txtUserName;
	private JTextField txtPassword;
	private JTextField txtDbUrl;
	private JTextField txtDbDriver;
	private JTextField txtDbName;
	private JTable jTable;
	private static List<String> list = new ArrayList<String>() ;


	public GenerateCodeListener(JComboBox<String> cbxDbType,JTextField txtSampleDir,JTextField txtTargetDir, JTextField txtProjectName,JTextField txtUserName, JTextField txtPassword,
			JTextField txtDbUrl, JTextField txtDbDriver, JTextField txtDbName,JTable jTable){
		
		this.txtSampleDir = txtSampleDir;
		this.cbxDbType=cbxDbType;
		this.txtTargetDir = txtTargetDir;
		this.txtProjectName = txtProjectName;
		this.txtUserName = txtUserName;
		this.txtPassword = txtPassword;
		this.txtDbUrl = txtDbUrl;
		this.txtDbDriver = txtDbDriver;
		this.txtDbName = txtDbName;
		this.jTable = jTable;
	}

	
	public void actionPerformed(ActionEvent e) {
		CodeGenerator generator = new CodeGenerator((String) cbxDbType.getSelectedItem(), txtSampleDir.getText(), txtTargetDir.getText(), txtProjectName.getText(),
				txtUserName.getText(), txtPassword.getText(), txtDbUrl.getText(), txtDbDriver.getText(),txtDbName.getText(),jTable.getToolTipText());


		int[] i =  jTable.getSelectedRows();
		for(int a:i){
			
			String table= (String) jTable.getValueAt(a, 1);
			list.add(table);
		}
		
		generator.generatProject();
		


   }


	@SuppressWarnings("rawtypes")
	public static List getList() {
		return list;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setList(List list) {
		GenerateCodeListener.list = list;
	}


	
}
