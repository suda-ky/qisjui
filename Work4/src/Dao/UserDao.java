package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DButil;
import entity.City;
import entity.Page;
import entity.Province;
import entity.User;


public class UserDao {
	//查询所有的用户
		public List<User> selectAllUser(User user,Page page){
			List<User> list = new ArrayList<User>();
			User u = null;
			//动态的往list数组中加数据
			List<Object> listPrams = new ArrayList<Object>();
			String sql = "select u.userName,u.`passWord`,u.chiName,u.email,c.id,c.city,p.id,p.province"
			+ " from t_user u,t_city c,t_province p "
			+ "where u.city_id = c.id and c.province_id = p.id ";
			//如果输入框没有值，则查询全部
			//如果条件，则追加条件到sql语句中去
			//小细节，注意空格,
			//不能使用常规的添加?，会报错，必须得使用转义字符\*，才能进行查询
			if (user.getName()!=null && !"".equals(user.getName())) {
				sql += "and u.userName like \"%\"?\"%\" ";
				listPrams.add(user.getName());
			}
			if (user.getChiName()!=null && !"".equals(user.getChiName())) {
				sql += "and u.chiName like \"%\"?\"%\" ";
				listPrams.add(user.getChiName());
			}
			if (user.getEmail()!=null && !"".equals(user.getEmail())) {
				sql += "and u.email like \"%\"?\"%\" ";
				listPrams.add(user.getEmail());
			}
			if (user.getCity().getProvince().getProvince()!=null && !"".equals(user.getCity().getProvince().getProvince())) {
				sql += "and p.province like \"%\"?\"%\" ";
				listPrams.add(user.getCity().getProvince().getProvince());
			}
			//追加查询约束
			//从第几条数据开始查，默认从0，也即是第一条
			int begin = page.getPageSize() * (page.getPageNum() - 1);
			sql += "ORDER BY "+page.getSort()+" "+page.getSortOrder()+" LIMIT "+begin+","+page.getPageSize()+" ";
			//将list数组转化为数组
			Object[] prams = listPrams.toArray();

			ResultSet rs = DButil.executeQuery(sql, prams);
			try {
				while(rs.next()){
					u = new User();
					City city = new City();
					Province province = new Province();
					u.setName(rs.getString("u.userName"));
					u.setPassWord(rs.getString("u.passWord"));
					u.setChiName(rs.getString("u.chiName"));
//					u.setRole(rs.getString("u.role"));
					u.setEmail(rs.getString("u.email"));
					city.setId(rs.getInt("c.id"));
					city.setCity(rs.getString("c.city"));
					province.setId(rs.getInt("p.id"));
					province.setProvince(rs.getString("p.province"));
					city.setProvince(province);
					u.setCity(city);
					list.add(u);
					}
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
		//查询记录总条数
		public int countUser(User user,Page page){
			int count = 0;
			//动态的往list数组中加数据
			List<Object> listPrams = new ArrayList<Object>();
			String sql = "select count(0)"
			+ " from t_user u,t_city c,t_province p "
			+ "where u.city_id = c.id and c.province_id = p.id ";
			//如果输入框没有值，则查询全部
			//如果条件，则追加条件到sql语句中去
			//小细节，注意空格
			if (user.getName()!=null && !"".equals(user.getName())) {
				sql += "and u.userName like \"%\"?\"%\" ";
				listPrams.add(user.getName());
			}
			if (user.getChiName()!=null && !"".equals(user.getChiName())) {
				sql += "and u.chiName like \"%\"?\"%\" ";
				listPrams.add(user.getChiName());
			}
			if (user.getEmail()!=null && !"".equals(user.getEmail())) {
				sql += "and u.email like \"%\"?\"%\" ";
				listPrams.add(user.getEmail());
			}
			if (user.getCity().getProvince().getProvince()!=null && !"".equals(user.getCity().getProvince().getProvince())) {
				sql += "and p.province like \"%\"?\"%\" ";
				listPrams.add(user.getCity().getProvince().getProvince());
			}
			//追加查询约束
			//从第几条数据开始查，默认从0，也即是第一条
			//int begin = page.getPageSize() * (page.getPageNum() - 1);
			//sql += "ORDER BY "+page.getSort()+" "+page.getSortOrder()+" LIMIT "+begin+","+page.getPageSize()+" ";
			//将list数组转化为数组
			Object[] prams = listPrams.toArray();

			ResultSet rs = DButil.executeQuery(sql, prams);
			try {
				while(rs.next()) {
					count = rs.getInt(1);
				}
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return count;
		}
		public boolean updateUser(User user){
			//动态的往list数组中加数据
			//List<Object> listPrams = new ArrayList<Object>();
			//因为用户表中没有省份，所有只要修改city_id字段就可以
			String sql = "update t_user set "
			+ "chiName = ?,email = ?,passWord = ?,city_id = ? where userName = '"+user.getName()+"' ";

			//将list数组转化为数组
			Object[] prams = {user.getChiName(),user.getEmail(),user.getPassWord(),user.getCity().getId()};
			boolean flag = DButil.insertQuery(sql, prams);
			return flag;
		}
		public boolean deleteUser(Object[] names){
			//动态的往list数组中加数据
			//List<Object> listPrams = new ArrayList<Object>();
			//因为用户表中没有省份，所有只要修改city_id字段就可以
			String sql = "delete from t_user where userName = ?";

			//将list数组转化为数组
			Object[] prams = names;
			
			return DButil.deleteQuery(sql, prams);
		}
}
