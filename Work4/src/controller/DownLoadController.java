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
			String id = request.getParameter("id");//文件id
			if (download.equals("download")) {
				DownLoadDao dLoadDao = new DownLoadDao();
				DownloadList dl = dLoadDao.downloadToId(Integer.parseInt(id));
				if (dl !=null) {
					String path = dl.getPath();
					//System.out.println(path);
					//设置文件MIME类型
					response.setContentType(getServletContext().getMimeType(path.substring(9)));
//					response.setContentType("application/octet-stream; charset=utf-8");
					//download/宠物医院模板1.0.pptx
					//System.out.println(path.substring(9));
					//设置Content-Disposition
					//new String(fileName.getBytes("gb2312"),"ISO-8859-1") 中文编码，防止乱码
					response.setHeader("Content-Disposition", "attachment;filename="+new String(path.substring(9).getBytes("gb2312"),"ISO-8859-1"));
					//读取目标文件，通过response将目标文件写到客户端
					//获取目标文件的绝对路径
					String fullFileName = getServletContext().getRealPath("/"+path);
					InputStream in = new FileInputStream(fullFileName);
					OutputStream out = response.getOutputStream();
					//写文件
					int b;
					while ((b=in.read())!=-1) {
						out.write(b);
					}
					in.close();
					out.close();
				}else{
					request.setAttribute("error", "抱歉，该下载资源错误，请重试!");
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
