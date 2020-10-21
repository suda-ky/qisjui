package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DButil {
	private static Connection connection = null;
	private static PreparedStatement pst = null; 
	private static ResultSet rs = null;
	
	private static Connection getConnection(){
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
				//杩炴帴鏁版嵁搴�
				connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/excise2?useUnicode=true&characterEncoding=utf-8","root","123456");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return connection;
	}
	
	//鍏抽棴鏁版嵁搴撹繛鎺�
	private static void closeAll() {
		
			try {
//			if (rs != null) {
//				rs.close();
//			}
			if (pst != null) {
				pst.close();
			}
			if (connection != null) {
				connection.close();
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//鍒涘缓涓�涓煡璇㈡柟娉�
	public static ResultSet executeQuery(String sql,Object[] prams) {
		connection = getConnection();
		try {
			pst = connection.prepareStatement(sql);
				for (int i = 0; i < prams.length; i++) {
					//(绗嚑涓�?锛屼紶浠�涔堝��)
					pst.setObject(i+1, prams[i]);
				}
			rs = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	//鎻掑叆鏁版嵁
	public static boolean insertQuery(String sql,Object[] prams) {
		connection = getConnection();
		boolean flag = false;
		try {
				pst = connection.prepareStatement(sql);
				for (int i = 0; i < prams.length; i++) {
					//(绗嚑涓�?锛屼紶浠�涔堝��)
					pst.setObject(i+1, prams[i]);
				}
				pst.executeUpdate();
				flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = flag;
		}
		return flag;
	}
	
}
