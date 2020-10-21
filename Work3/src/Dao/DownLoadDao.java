package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DButil;
import entity.DownloadList;



public class DownLoadDao {
	public List<DownloadList>	ListSelect() {
		List<DownloadList> lists = new ArrayList<DownloadList>();
		DownloadList download = null;
		String sql = "select * from t_downloadlist where 1=?";
		Object[] prams = {"1"};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while (rs.next()) {
				download = new DownloadList();
				download.setId(rs.getInt("id"));
				download.setName(rs.getString("name"));
				download.setPath(rs.getString("path"));
				download.setDescription(rs.getString("description"));
				download.setSize(rs.getLong("size"));
				download.setStar(rs.getInt("star"));
				download.setImage(rs.getString("image"));
				
				lists.add(download);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}
	//通过资源id查找下载资源
	public DownloadList	downloadToId(int id) {
		DownloadList download = null;
		String sql = "select * from t_downloadlist where id = ?";
		Object[] prams = {id};
		ResultSet rs = DButil.executeQuery(sql, prams);
		try {
			while (rs.next()) {
				download = new DownloadList();
				download.setId(rs.getInt("id"));
				download.setName(rs.getString("name"));
				download.setPath(rs.getString("path"));
				download.setDescription(rs.getString("description"));
				download.setSize(rs.getLong("size"));
				download.setStar(rs.getInt("star"));
				download.setImage(rs.getString("image"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return download;
	}
}
