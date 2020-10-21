package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.DownLoadDao;
import entity.DownloadList;

public class MainController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			String message = request.getParameter("message");
			String user = request.getParameter("user");
			String download = request.getParameter("download");
			
//			System.out.println(message+user+download);
			if (message !=null &&message.equals("logout")) {
				//鍒犻櫎cookie
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
//					System.out.println(cookie.getName()+" "+cookie.getMaxAge());
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
				//娓呴櫎session缂撳瓨
				request.getSession().invalidate();
				//杞埌鐧婚檰椤甸潰
				response.sendRedirect("login.jsp");
			}
			//璧勬簮涓嬭浇椤甸潰涓庤祫婧愮鐞�
			if (download !=null && download.equals("select")) {
				List<DownloadList> dList = new ArrayList<DownloadList>();
				DownLoadDao dao = new DownLoadDao();
				dList = dao.ListSelect();
				request.setAttribute("list", dList);
				request.getRequestDispatcher("/downLoadList.jsp").forward(request, response);
			}else if(download !=null && download.equals("manage")){
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			//鐢ㄦ埛绠＄悊浠ュ強涓汉涓績
			if (user !=null && user.equals("manage")) {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}else if (user !=null && user.equals("center")){
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
