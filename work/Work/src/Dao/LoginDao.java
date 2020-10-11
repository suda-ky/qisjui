package Dao;


import java.sql.ResultSet;
import java.sql.SQLException;

import util.DButil;
import controller.Login;
import entity.User;


public class LoginDao {
	
	public User loginSelect(String username,String pwd) {
		User user = null;
		String sql = "select * from t_user where userName = ? and passWord = ?";
		Object[] prams = {username,pwd};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while(rs.next()){
				
			user = new User();
			user.setName(rs.getString("userName"));
			user.setPassWord(rs.getString("passWord"));
			user.setChiName(rs.getString("chiName"));
			user.setRole(rs.getString("role"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
}

