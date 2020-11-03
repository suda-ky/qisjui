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
import Dao.UserDao;

public class RegisterController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//判断是添加还是更新
		String flag = request.getParameter("flag");
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
		user.setRole("普通用户");
		user.setCity(new CityDao().selectToId(cityId));
		LoginDao ld = new LoginDao();
		Map<String, Object> map = new HashMap<String, Object>();
		if(flag.equals("update")){
			UserDao ud = new UserDao();
			if (ud.updateUser(user)) {
				map.put("code", 1);
				map.put("info", "修改成功！");
			}else{
				map.put("code", 0);
				map.put("info", "修改失败！");
			}
		}else{
			if(ld.insertUser(user)){//插入用户成功
				map.put("code", 1);
				map.put("info", "添加成功！");
				//并给用户设置访问权限，插入到t_user_role
				RoleDao rd =  new RoleDao();
				Role role = rd.selectToroleName("普通用户");
				rd.insertUserRole(role.getId(), uname);
			}else{//失败
				map.put("code", 0);
				map.put("info", "添加失败！");
			}
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
