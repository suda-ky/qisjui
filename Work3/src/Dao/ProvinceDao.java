package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DButil;
import entity.Province;


public class ProvinceDao {
	public List<Province> fillSelect(){
		List<Province> list = new ArrayList<Province>();
		String sql = "select * from t_province where 1=?";
		Object[] prams = {"1"};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while (rs.next()) {
				Province pro = new Province();
				pro.setId(rs.getInt(1));
				pro.setProvince(rs.getString(2));
				list.add(pro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
