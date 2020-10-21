package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.Role;
import entity.User;
import Dao.CityDao;
import Dao.LoginDao;
import Dao.RoleDao;


public class RegisterController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uname = request.getParameter("uname");
		String chiname = request.getParameter("chiname");
		String email = request.getParameter("email");
		String cityId = request.getParameter("cityId");
		String pwd = request.getParameter("pwd");
		User user = new User();
		user.setName(uname);
		user.setChiName(chiname);
		user.setEmail(email);
		user.setPassWord(pwd);
		user.setRole("鏅�氱敤鎴�");
		user.setCity(new CityDao().selectToId(cityId));
		LoginDao ld = new LoginDao();
		Map<String, Object> map = new HashMap<String, Object>();
		if(ld.insertUser(user)){//鎻掑叆鐢ㄦ埛鎴愬姛
			map.put("code", 1);
			//骞剁粰鐢ㄦ埛璁剧疆璁块棶鏉冮檺锛屾彃鍏ュ埌t_user_role
			RoleDao rd =  new RoleDao();
			Role role = rd.selectToroleName("鏅�氱敤鎴�");
			
			rd.insertUserRole(role.getId(), uname);
		}else{//澶辫触
			map.put("code", 0);
		}
		//杞寲鎴恓son瀛楃涓�
		String json = new Gson().toJson(map);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	
}
