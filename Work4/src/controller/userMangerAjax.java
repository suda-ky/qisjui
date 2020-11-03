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

import Dao.LoginDao;
import Dao.UserDao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.City;
import entity.Page;
import entity.Province;
import entity.User;


public class userMangerAjax extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String flag = request.getParameter("flag");//识别
		String queryParams = request.getParameter("queryParams");
		String pageParams = request.getParameter("pageParams");
		String names = request.getParameter("names");
//		System.out.println(queryParams);
//		System.out.println(pageParams);
		
		//json将字符串转换成java对象
		Gson gson = new GsonBuilder().serializeNulls().create();
		HashMap<String, Object> mapPage = gson.fromJson(pageParams, HashMap.class);
		//page对象
		Page page = new Page();
		if(pageParams!=null&&!"".equals(pageParams)){
		page.setPageSize(Integer.parseInt(mapPage.get("pageSize").toString()));
		//System.out.println(mapPage.get("pageNum").getClass().getName());
		//page.setPageNum((int)Math.ceil((Double) mapPage.get("pageNum")));
		page.setPageNum(Integer.parseInt(mapPage.get("pageNum").toString()));
		page.setSort(mapPage.get("sort").toString());
		page.setSortOrder(mapPage.get("sortOrder").toString());
		}
		//user对象
		User user = new User();
		HashMap<String, Object> mapUser = gson.fromJson(queryParams, HashMap.class);
		if(queryParams!=null&&!"".equals(queryParams)){
			user.setName(mapUser.get("userName").toString());
			user.setChiName(mapUser.get("chiName").toString());
			user.setEmail(mapUser.get("email").toString());
			Province province = new Province();
			province.setProvince(mapUser.get("provinceName").toString());
			City city = new City();
			city.setProvince(province);
			user.setCity(city);
		}else{
			user.setName("");
			user.setChiName("");
			user.setEmail("");
			Province province = new Province();
			province.setProvince("");
			City city = new City();
			city.setProvince(province);
			user.setCity(city);
		}
//		System.out.println("123"+user.getCity().getProvince().getProvince());
		
		UserDao ud = new UserDao();
		List<User> list = new ArrayList<User>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		//如果查到数据，则给出提示
		if (flag.equals("allUser")) {
			count = ud.countUser(user, page);
			list = ud.selectAllUser(user,page);
			map.put("rows", list);
			map.put("total", count);
		}else if(flag.equals("delete")){
			//ud.deleteUser(ids);
			List<Object> namesList = gson.fromJson(names, ArrayList.class);
			if(ud.deleteUser(namesList.toArray())){
				map.put("code", 1);
			}else{
				map.put("code", 0);
			}
			//System.out.println(namesList);
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
