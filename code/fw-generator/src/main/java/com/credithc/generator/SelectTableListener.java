package com.credithc.generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * 查询数据库表类
 * 
 * */
public class SelectTableListener implements ActionListener {

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

	public SelectTableListener(JComboBox<String> cbxDbType,JTextField txtSampleDir,JTextField txtTargetDir, JTextField txtProjectName,JTextField txtUserName, JTextField txtPassword,
			JTextField txtDbUrl, JTextField txtDbDriver, JTextField txtDbName,JTable jTable) {

		this.cbxDbType=cbxDbType;
		this.txtSampleDir = txtSampleDir;
		this.txtTargetDir = txtTargetDir;
		this.txtProjectName = txtProjectName;
		this.txtUserName = txtUserName;
		this.txtPassword = txtPassword;
		this.txtDbUrl = txtDbUrl;
		this.txtDbDriver = txtDbDriver;
		this.txtDbName = txtDbName;
		this.jTable = jTable;

	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public void actionPerformed(ActionEvent e) {
		CodeGenerator generator = new CodeGenerator((String)cbxDbType.getSelectedItem(), txtSampleDir.getText(), txtTargetDir.getText(), txtProjectName.getText(),
				txtUserName.getText(), txtPassword.getText(),txtDbUrl.getText(), txtDbDriver.getText(), txtDbName.getText(),jTable.getToolTipText());
		try {
//		if(cbxDbType.getSelectedItem().equals("oracle")){
//			Class.forName(txtDbDriver.getText());
//			Connection conn = DriverManager.getConnection(txtDbUrl.getText(),
//					txtUserName.getText(), txtPassword.getText());
//
//			String sql = "SELECT table_name FROM USER_TABLES ";
//			PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
//			//st.setString(1, txtDbName.getText());
//			ResultSet rs = st.executeQuery();
//			DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
//			dtm.setRowCount(0);	
//	
//			while (rs.next()) {
//				Vector v1 = new Vector();
//				v1.add("1");
//				v1.add( rs.getString("table_name"));
//				dtm.addRow(v1);
//			}
//
//			
//		}else (cbxDbType.getSelectedItem().equals("mysql")){

		
			Class.forName(txtDbDriver.getText());
			Connection conn = DriverManager.getConnection(txtDbUrl.getText(),
					txtUserName.getText(), txtPassword.getText());

			String sql = "select table_name from information_schema.tables where table_schema= ? ";
			PreparedStatement st = (PreparedStatement) conn
					.prepareStatement(sql);
			st.setString(1, txtDbName.getText());

			ResultSet rs = st.executeQuery();
			DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
			dtm.setRowCount(0);	
	
			int count=0;
			while (rs.next()) {
				Vector v1 = new Vector();
				for(int i=count; i<=rs.getRow(); i++){	
				v1.add(i+1);
				count=i+1;
				  for(int a=0;a<=rs.getRow(); a++){
				v1.add( rs.getString("table_name"));
				
				dtm.addRow(v1);
				break;
				  }
				  break;
				}
			}
//		}
		} catch (ClassNotFoundException | SQLException e1) {
			
			e1.printStackTrace();
		}
	}

}
