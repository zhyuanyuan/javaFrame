package com.credithc.generator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

/**
 * 生成窗体类
 * 
 * */
public class CodeGenWin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	private JToolBar toolBar = new JToolBar();
	private JButton btnGenerate = new JButton("生成");
	
	private JPanel pnlNorth = new JPanel();
	private JPanel pnlSettings = new JPanel();
	private JTabbedPane tpnlDbTables = new JTabbedPane();
	
	private JTable tblDbTables = new JTable();
	private JScrollPane spnlDbTables = new JScrollPane(tblDbTables);  
	
//	private JTextArea txtaConsole = new JTextArea();
//	private JScrollPane spnlConsole = new JScrollPane(txtaConsole);  
	

	private JLabel lblSampleDir = new JLabel("样板项目路径");
	private JTextField txtSampleDir = new JTextField("C:\\Users\\dingjian151013\\Desktop\\code\\fw-sample\\fw-sample-data");
	
	private JLabel lSampleDir = new JLabel("项目路径");
	private JTextField tSampleDir = new JTextField("C:\\Users\\dingjian151013\\Desktop\\code\\fw-sample");
	private JButton btSampleDir = new JButton("path");

	private JLabel lTargetDir = new JLabel("模板存放路径");
	private JTextField tTargetDir = new JTextField("C:\\Users\\dingjian151013\\Desktop\\code\\fw-generator\\model");
	private JButton bSampleDir = new JButton("copy");
	
	private JButton btnSampleDir = new JButton("path");
	private JButton bttSampleDir = new JButton("path");

	private JLabel lblTargetDir = new JLabel("模板生成路径");
	private JTextField txtTargetDir = new JTextField("C:\\Users\\dingjian151013\\Desktop\\code\\fw-generator\\template\\build-generator");
	private JButton btnTargetDir = new JButton("path");
	private JLabel lblDbType = new JLabel("数据库类型");
	private JComboBox<String> cbxDbType = new JComboBox<String>();
	private JLabel lblUserName = new JLabel("用户名");
	private JTextField txtUserName = new JTextField("sample");
	private JLabel lblPassword = new JLabel("密码");
	private JTextField txtPassword = new JTextField("sample");
	private JLabel lblDbUrl = new JLabel("数据库URL");
	private JTextField txtDbUrl = new JTextField("jdbc:mysql://localhost:3306/dj");
	private JLabel lblDbName = new JLabel("数据库名称");
	private JTextField txtDbName = new JTextField("test");	
	private JLabel lblDbDriver = new JLabel("数据库驱动");
	private JTextField txtDbDriver = new JTextField("com.mysql.jdbc.Driver");
	private JButton btnGetTableList = new JButton("query");
	private JLabel lblProjectName = new JLabel("项目名称");
	private JTextField txtProjectName = new JTextField("test");

	public CodeGenWin() {
		
 		initWindow();
		addComponents();
		addListeners();
		setVisible(true);
	}

	@SuppressWarnings("serial")
	private void initWindow() {
		setSize(800,600);  
		setLocationRelativeTo(null);  
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize(); 
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-getWidth())/2;
        int y=(height-getHeight())/2;
        setLocation(x,y);
        
		cbxDbType.setEditable(false);
		cbxDbType.addItem("mysql");
	    cbxDbType.addItem("oracle");
	    tblDbTables.setModel(new javax.swing.table.DefaultTableModel(
	    		new Object [][] {
	    			
	    		},
	    		new String [] {
	    			"序号","表名"
	    		}
	    	) {
	    		boolean[] canEdit = new boolean [] {
	    			false,false
	    		};

	    		public boolean isCellEditable(int rowIndex, int columnIndex) {
	    			return canEdit [columnIndex];
	    		}
	    	});
	    
  	    
	    toolBar.add(btnGenerate);
	    toolBar.add(btnGetTableList);
	    toolBar.add(bSampleDir);
	    
	    tpnlDbTables.addTab("表列表", spnlDbTables);
//	    tpnlDbTables.addTab("控制台", spnlConsole);
	    
	    pnlNorth.setLayout(new BorderLayout());
	    
	    tblDbTables.setFillsViewportHeight(true); 
	    
	}

	//监听方法
	private void addListeners() {
		btnSampleDir.addActionListener(new SelectDirListener(txtSampleDir));
		btnTargetDir.addActionListener(new SelectDirListener(txtTargetDir));
		btSampleDir.addActionListener(new SelectDirListener(tSampleDir));
		bttSampleDir.addActionListener(new SelectDirListener(tTargetDir));
		btnGenerate.addActionListener(new GenerateCodeListener(cbxDbType,txtSampleDir, txtTargetDir, txtProjectName, 
				txtUserName, txtPassword, txtDbUrl, txtDbDriver,txtDbName,tblDbTables));
		btnGetTableList.addActionListener(new SelectTableListener(cbxDbType,txtSampleDir, txtTargetDir, txtProjectName, 
				txtUserName, txtPassword, txtDbUrl, txtDbDriver,txtDbName,tblDbTables) );
		bSampleDir.addActionListener(new CheckSampleDir(tSampleDir,tTargetDir, txtProjectName));
		
	}

	private void addComponents() {
		getContentPane().add(pnlNorth, BorderLayout.NORTH);
		pnlNorth.add(toolBar, BorderLayout.NORTH);
		pnlNorth.add(pnlSettings, BorderLayout.CENTER);
		
		getContentPane().add(tpnlDbTables, BorderLayout.CENTER);
		
		GridBagLayout layout = new GridBagLayout();
		pnlSettings.setLayout(layout);
			
		//x,y,width,height,weightx,weighty,anchor,fill,insets,padx,pady
		//模板
		layout.setConstraints(lSampleDir, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lSampleDir);
		layout.setConstraints(tSampleDir, new GridBagConstraints(1, 0, 4, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(tSampleDir);
		layout.setConstraints(btSampleDir, new GridBagConstraints(5, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(btSampleDir);
		layout.setConstraints(bSampleDir, new GridBagConstraints(6, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(bSampleDir);
		
		layout.setConstraints(lblSampleDir, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblSampleDir);
		layout.setConstraints(txtSampleDir, new GridBagConstraints(1, 1, 4, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(txtSampleDir);
		layout.setConstraints(btnSampleDir, new GridBagConstraints(5, 1, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(btnSampleDir);
		
		layout.setConstraints(lblTargetDir, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblTargetDir);
		layout.setConstraints(txtTargetDir, new GridBagConstraints(1, 2, 4, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(txtTargetDir);
		layout.setConstraints(btnTargetDir, new GridBagConstraints(5, 2, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(btnTargetDir);
		
		layout.setConstraints(lTargetDir, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lTargetDir);
		layout.setConstraints(tTargetDir, new GridBagConstraints(1, 3, 4, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(tTargetDir);
		layout.setConstraints(bttSampleDir, new GridBagConstraints(5, 3, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(bttSampleDir);
		
		//数据库
		layout.setConstraints(lblDbType, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblDbType);
		layout.setConstraints(cbxDbType, new GridBagConstraints(1, 4, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(cbxDbType);
		layout.setConstraints(lblUserName, new GridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblUserName);
		layout.setConstraints(txtUserName, new GridBagConstraints(3, 4, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(txtUserName);
		layout.setConstraints(lblPassword, new GridBagConstraints(4, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblPassword);
		layout.setConstraints(txtPassword, new GridBagConstraints(5, 4, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(txtPassword);
		
		layout.setConstraints(lblDbUrl, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblDbUrl);
		layout.setConstraints(txtDbUrl, new GridBagConstraints(1, 5, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(txtDbUrl);
		
		layout.setConstraints(lblDbName, new GridBagConstraints(2, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblDbName);
		layout.setConstraints(txtDbName, new GridBagConstraints(3, 5, 3, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(txtDbName);
		layout.setConstraints(btnGetTableList, new GridBagConstraints(6, 5, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(btnGetTableList);
		
		layout.setConstraints(lblProjectName, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblProjectName);
		layout.setConstraints(txtProjectName, new GridBagConstraints(1, 6, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(txtProjectName);
		layout.setConstraints(lblDbDriver, new GridBagConstraints(2, 6, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(lblDbDriver);
		layout.setConstraints(txtDbDriver, new GridBagConstraints(3, 6, 3, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));  
		pnlSettings.add(txtDbDriver);
		
		
		for(Component c : pnlSettings.getComponents()){
			if(c instanceof JLabel){
				c.setPreferredSize(new Dimension(100,25));
			}
		}
		
	}

	
	
	

}
