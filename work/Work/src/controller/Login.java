package controller;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import Dao.LoginDao;

public class Login extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			//����ǰ̨����
			String name = request.getParameter("username");
			String pwd  = request.getParameter("password");
			String vimage = request.getParameter("vimage");
			String verity = (String)request.getSession().getAttribute("verity");
			//����user���ݿ�
			//У����֤��
			if (verity.equalsIgnoreCase(vimage)) {
				//��֤��ͨ�������ж��û���������
				LoginDao lDao = new LoginDao();
				User user = lDao.loginSelect(name, pwd);
				if (user!=null && name.equals(user.getName())) {
					//�û�����ȷ���ж�����
					if (pwd.equals(user.getPassWord())) {
						//��ѯ���ݿ���ȷ����ѡ�������������¼���򴴽�cookie
						String autoLogin = request.getParameter("sevenAutoLogin");
						if (autoLogin != null && autoLogin.equals("ok")) {
								Cookie cookie1 = new Cookie("username", name);
								Cookie cookie2 = new Cookie("password", pwd);
			
								cookie1.setMaxAge(7*24*60*60);
								cookie2.setMaxAge(7*24*60*60);

								System.out.println("cookiePath:"+request.getContextPath());
								cookie1.setPath(request.getContextPath());
								cookie2.setPath(request.getContextPath());
				
								response.addCookie(cookie1);
								response.addCookie(cookie2);
						}
						//��ת��main.jsp,��Я���û���,���û����Ž�session��
//						���ж��Ƿ��login��½������������ת��error.jsp

						request.getSession().setAttribute("chiName", user.getChiName());
						request.getSession().setAttribute("uname", user.getName());
						request.getRequestDispatcher("/main.jsp").forward(request, response);
					}else {
					request.setAttribute("error", "��Ǹ������������벻��ȷ");
					request.setAttribute("errorUrl", "login.html");
					request.getRequestDispatcher("/error.jsp").forward(request, response);
					}
				}else{
					request.setAttribute("error", "��Ǹ����������û���������");
					request.setAttribute("errorUrl", "login.html");
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			}else{
				request.setAttribute("error", "��Ǹ�����������֤�벻��ȷ");
				request.setAttribute("errorUrl", "login.html");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}	
	}

}
