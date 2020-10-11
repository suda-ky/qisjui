package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DButil;
import entity.Resource;

public class ResourceDao {
	public List<Resource> resourceSelect(String uname){
		List<Resource> list = new ArrayList<Resource>();
		Resource resource = null;
		String sql = "select * from t_resource where "
				+ "id in (select resource_id from t_role_resource where "
				+ "role_id in (select id from t_user_role where user_name = ? ))";
		Object[] prams = {uname};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while (rs.next()) {
				resource = new Resource();
				resource.setId(rs.getInt("id"));
				resource.setResourceName(rs.getString("resourceName"));
				resource.setUrl(rs.getString("url"));
				list.add(resource);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
