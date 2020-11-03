package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.User;
import Dao.LoginDao;

public class Login extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");//统一编码
			//接受前台数据
			String name = request.getParameter("username");
			String pwd  = request.getParameter("password");
			String vimage = request.getParameter("vimage");//用户输入的验证码
			String verity = (String)request.getSession().getAttribute("verity");
			String autoLogin = request.getParameter("autoLogin");//是否免登录
			
//			System.out.println(name+"\n"+pwd+"\n"+vimage+"\n"+autoLogin);
//			System.out.println("用户："+vimage+"\n生成"+verity);
			
			//创建一个Map,存放返回的数据
			Map<String, Object> map = new HashMap<String, Object>();
			//调用user数据库
			//校验验证码
			if (verity.equalsIgnoreCase(vimage)) {
				//验证码通过，则判断用户名或密码
				LoginDao lDao = new LoginDao();
				User user = lDao.loginSelect(name, pwd);
				if (user!=null) {
					//用户名正确，判断密码
					map.put("code", 0);
					map.put("info", "登陆成功");
					//将判断的（是否选择了免登录，中文名）数据放入到session当中
					request.getSession().setAttribute("chiName", user.getChiName());
					request.getSession().setAttribute("uname", user.getName());
					if (pwd.equals(user.getPassWord())) {
						//查询数据库正确，并选择了七天内免登录，则创建cookie
						if (autoLogin != null && autoLogin.equals("ok")) {
								Cookie cookie1 = new Cookie("username", name);
								Cookie cookie2 = new Cookie("password", pwd);
								//设置存入时长，七天
								cookie1.setMaxAge(7*24*60*60);
								cookie2.setMaxAge(7*24*60*60);
								//设置路径
								System.out.println("cookiePath:"+request.getContextPath());
								cookie1.setPath(request.getContextPath());
								cookie2.setPath(request.getContextPath());
								//发送到浏览器
								response.addCookie(cookie1);
								response.addCookie(cookie2);
						}
					}else {
						map.put("code", 3);
						map.put("info", "密码不正确！");
					}
				}else{
					map.put("code", 2);
					map.put("info", "用户名不存在！");
					}
			}else{
				map.put("code", 1);
				map.put("info", "验证码不正确！");
			}
			//调用导入的gson架包
			String json = new Gson().toJson(map);//将map转化成字符串
			//输出json到页面
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
	}

}
