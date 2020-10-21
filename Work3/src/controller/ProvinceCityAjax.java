package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.CityDao;
import Dao.LoginDao;
import Dao.ProvinceDao;

import com.google.gson.Gson;

import entity.City;
import entity.Province;
import entity.User;


public class ProvinceCityAjax extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String provinceId = request.getParameter("provinceId");
		
//		System.out.println("uname"+uname);
		String json = "";
		//鐪佷唤id涓虹┖锛屽垯鏌ュ嚭鎵�鏈夌殑鐪佷唤锛屼笉涓虹┖杩欐煡璇㈢渷浠戒笅闈㈢殑鍩庡競
		if (provinceId==null) {
			List<Province> list = new ArrayList<Province>();
			list = new ProvinceDao().fillSelect();
			//杞寲鎴恓son瀛楃涓�
			 json = new Gson().toJson(list);
		}else {
			List<City> clist = new ArrayList<City>();
			clist = new CityDao().selectToProId(provinceId);
			//杞寲鎴恓son瀛楃涓�
			 json = new Gson().toJson(clist);
		}
//		System.out.println(json);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
}
