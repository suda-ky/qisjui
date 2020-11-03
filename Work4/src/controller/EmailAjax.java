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

public class EmailAjax extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		
		LoginDao ld = new LoginDao();		
		Map<String, Object> map = new HashMap<String, Object>();
		//如果查到数据，则给出提示
		if (ld.selectToEmail(email)) {//为真，就是数据库已经存在
			map.put("code", 1);
		}else {
			map.put("code", 0);//为假，则可以被使用
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
