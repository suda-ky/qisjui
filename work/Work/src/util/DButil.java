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
				//连接数据库
				connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/suda","root","root");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return connection;
	}
	
	//关闭数据库连接
	private static void closeAll() {
		
			try {
			if (rs != null) {
				rs.close();
			}
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
	//创建一个查询方法
	public static ResultSet executeQuery(String sql,Object[] prams) {
		connection = getConnection();
		
		try {
			pst = connection.prepareStatement(sql);
				for (int i = 0; i < prams.length; i++) {
		
					pst.setObject(i+1, prams[i]);
				}
			rs = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
}
