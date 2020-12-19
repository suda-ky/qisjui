package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import util.DButil;
import entity.Menu;

public class MenuDao {
	public List<Menu> queryMenu(){
		List<Menu> list = new ArrayList<Menu>();
		String sql = "select * from t_menu";
		Object[] prams = {};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while (rs.next()) {
				Menu menu = new Menu(rs.getInt("id"), rs.getString("name"), rs.getInt("pid"));
				list.add(menu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public boolean updateMenu(Menu menu){
		//动态的往list数组中加数据
		String sql = "update t_menu set "
		+ "name = ? where id = '"+menu.getId()+"' ";

		//将list数组转化为数组
		Object[] prams = {menu.getName()};
		boolean flag = DButil.insertQuery(sql, prams);
		return flag;
	}
	public boolean deleteMenu(Menu menu){
		//动态的往list数组中加数据
		//List<Object> listPrams = new ArrayList<Object>();
		//因为用户表中，所有只要修改city_id字段就可以
		String sql = "delete from t_menu where id = ?";

		//将list数组转化为数组
		Object[] prams = {menu.getId()};
		
		return DButil.deleteQuery(sql, prams);
	}
	
	public static void main(String[] args) {
//		List<Menu> list = new MenuDao().queryMenu();
		
		System.out.println(String.valueOf(new MenuDao().deleteMenu(new Menu(25,"qwe",0))));
	}
}
