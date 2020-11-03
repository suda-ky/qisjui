package Dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DButil;
import controller.Login;
import entity.City;
import entity.User;



public class LoginDao {
	//登陆验证，注册ajax验证请求
	public User loginSelect(String username,String pwd) {
		User user = null;
		String sql = "";
		Object[] prams = null;
		if (pwd==null||pwd=="") {
			sql = "select u.*,c.*  from t_user u,t_city c where u.city_id = c.id and userName = ?";
			prams = new Object[1];
			prams[0] = username;	
		}else{
			sql = "select u.*,c.*  from t_user u,t_city c where u.city_id = c.id and userName = ? and passWord = ?";
			prams = new Object[2];
			prams[0] = username;
			prams[1] = pwd;
		}
		
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while(rs.next()){
			user = new User();
			City city = new City();
			user.setName(rs.getString("u.userName"));
			user.setPassWord(rs.getString("u.passWord"));
			user.setChiName(rs.getString("u.chiName"));
			user.setRole(rs.getString("u.role"));
			user.setEmail(rs.getString("u.email"));
			city.setId(rs.getInt("c.id"));
			city.setCity(rs.getString("c.city"));
			user.setCity(city);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	//邮箱查询用户
	public boolean selectToEmail(String email){
		boolean flag = false;
		String sql = "select * from t_user where email = ?";
		Object[] prams = {email};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			if(rs.next()){
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	//插入用户信息
	public boolean insertUser(User user) {
		boolean flag = false;
		String sql = "insert into t_user(userName,password,chiName,Role,email,city_id) VALUES(?,?,?,?,?,?)";
		Object[] prams ={user.getName(),user.getPassWord(),user.getChiName(),user.getRole(),user.getEmail(),user.getCity().getId()};  
		flag = DButil.insertQuery(sql, prams);
		return flag;
	}
}

