package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DButil;
import entity.City;
import entity.Province;

public class CityDao {
		//根据省份id查询城市
	public List<City> selectToProId(String provinceId){
		List<City> list = new ArrayList<City>();
		String sql = "select * from t_city where province_id=?";
		Object[] prams = {provinceId};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while (rs.next()) {
				City city = new City();
				city.setId(rs.getInt(1));
				city.setCity(rs.getString(2));
				list.add(city);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//根据id查询城市
	public City selectToId(String cityId){
		City city = null;
		String sql = "select * from t_city where id = ?";
		Object[] prams = {cityId};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while (rs.next()) {
				city = new City();
				city.setId(rs.getInt(1));
				city.setCity(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return city;
	}
}
