package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.LoginDao;

import com.google.gson.Gson;

import entity.User;


public class NameAjax extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uname = request.getParameter("username");
		
//		System.out.println("uname"+uname);
		LoginDao ld = new LoginDao();
		User user = ld.loginSelect(uname, null);
		Map<String, Object> map = new HashMap<String, Object>();
		//如果查到数据，则给出提示
		if (user==null) {
			map.put("code", 0);
		}else {
			map.put("code", 1);
		}
		//转化成json字符串
		String json = new Gson().toJson(map);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

}
