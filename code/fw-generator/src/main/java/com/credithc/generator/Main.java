package com.credithc.generator;

import javax.swing.SwingUtilities;

/**
 * 主方法
 * 
 */

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {  
	        public void run() {
	        	new CodeGenWin();
	        }
	    });
	}

}
