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
			String id = request.getParameter("id");
			if (download.equals("download")) {
				DownLoadDao dLoadDao = new DownLoadDao();
				DownloadList dl = dLoadDao.downloadToId(Integer.parseInt(id));
				if (dl !=null) {
					String path = dl.getPath();

					response.setContentType(getServletContext().getMimeType(path.substring(9)));
					/*使用outputStream流向客服端浏览器输出中文数据下载（文件下载）以上内容只能打开文件，却不能保存到相应的目录位置
					文件下载功能是web开发中经常使用到的功能，使用HttpServletResponse对象就可以实现文件下载
					getOutputStream():向客服端发送数据（字节流）getWrite():向客服端发送数据 （字节流）*/
					      //1.获取要下载的文件的绝对路径
					response.setHeader("Content-Disposition", "attachment;filename="+new String(path.substring(9).getBytes("gb2312"),"ISO-8859-1"));
					//3.设置content-disposition响应头控制浏览器以下载的形式打开文件
					//设置content-disposition响应头，控制浏览器以下载形式打开，这里注意文件字符集编码格式，设置utf-8，不然会出现乱码
					String fullFileName = getServletContext().getRealPath("/"+path);
					InputStream in = new FileInputStream(fullFileName);
					OutputStream out = response.getOutputStream();
		
					int b;
					while ((b=in.read())!=-1) {
						out.write(b);
					}
					in.close();
					out.close();
				}else{
					request.setAttribute("error", "抱歉，资源下载错误!");
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
