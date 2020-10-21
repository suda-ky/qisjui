package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.DownLoadDao;
import entity.DownloadList;

public class DownLoadController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			String download = request.getParameter("download");
			String id = request.getParameter("id");//鏂囦欢id
			if (download.equals("download")) {
				DownLoadDao dLoadDao = new DownLoadDao();
				DownloadList dl = dLoadDao.downloadToId(Integer.parseInt(id));
				if (dl !=null) {
					String path = dl.getPath();
					//System.out.println(path);
					//璁剧疆鏂囦欢MIME绫诲瀷
					response.setContentType(getServletContext().getMimeType(path.substring(9)));
//					response.setContentType("application/octet-stream; charset=utf-8");
					//download/瀹犵墿鍖婚櫌妯℃澘1.0.pptx
					//System.out.println(path.substring(9));
					//璁剧疆Content-Disposition
					//new String(fileName.getBytes("gb2312"),"ISO-8859-1") 涓枃缂栫爜锛岄槻姝贡鐮�
					response.setHeader("Content-Disposition", "attachment;filename="+new String(path.substring(9).getBytes("gb2312"),"ISO-8859-1"));
					//璇诲彇鐩爣鏂囦欢锛岄�氳繃response灏嗙洰鏍囨枃浠跺啓鍒板鎴风
					//鑾峰彇鐩爣鏂囦欢鐨勭粷瀵硅矾寰�
					String fullFileName = getServletContext().getRealPath("/"+path);
					InputStream in = new FileInputStream(fullFileName);
					OutputStream out = response.getOutputStream();
					//鍐欐枃浠�
					int b;
					while ((b=in.read())!=-1) {
						out.write(b);
					}
					in.close();
					out.close();
				}else{
					request.setAttribute("error", "鎶辨瓑锛岃涓嬭浇璧勬簮閿欒锛岃閲嶈瘯!");
					request.setAttribute("errorUrl", "downLoadList.jsp");
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
				
			}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
