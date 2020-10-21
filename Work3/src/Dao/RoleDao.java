package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.City;
import entity.Role;
import util.DButil;


public class RoleDao {
	//插入到用户权限表当中
	public boolean insertUserRole(int roleId,String userName){
		boolean flag = false;
		String sql = "insert into t_user_role (role_id,user_name) values(?,?)";
		Object[] prams = {roleId,userName};
		flag = DButil.insertQuery(sql, prams);
		return flag;
	}
	//根据用户的角色名查询roleId
	public Role selectToroleName(String roleName){
		Role role = null;
		String sql = "select * from t_role where roleName = ?";
		Object[] prams = {roleName};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while (rs.next()) {
				role = new Role();
				role.setId(rs.getInt(1));
				role.setRoleName(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return role;
	}
}
