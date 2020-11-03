package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.User;
import Dao.LoginDao;

/**
 *
 *自动登录过滤器
 *过滤器之间的跳转，是根据XXXFilter的首字母大小排序进行跳转的
 *先执行AFilter，再执行BFilter，以此类推
 *有可能也是因为创间的先后顺序
 *
 *如果是选择了免登录的直接跳转到首页
 *@author 菜鸟一号 
 */

public class AutoLoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		//获取访问地址
		String path = request.getServletPath();
		System.out.println("autofilter"+path);
		//获得cookie中的值
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String uname = null;
			String pwd = null;
			for (Cookie cookie : cookies) {
//				System.out.println("autologin:"+cookie.getName());
				if ("username".equals(cookie.getName())) {
					uname = cookie.getValue();
				}else if ("password".equals(cookie.getName())) {
					pwd = cookie.getValue();
				}
			}
			if (uname !=null && pwd != null) {
				//根据cookie中的值，去查询数据库 如果正确则传到首页，否则跳到错误页面
			LoginDao logindao = new LoginDao();
			User user = logindao.loginSelect(uname, pwd);
			if (user!=null) {
//				request.setAttribute("username", user.getName());
//				request.setAttribute("password", user.getPassWord());
//				request.setAttribute("vimage", "abc");
//				request.getSession().setAttribute("verity","abc");
				request.getSession().setAttribute("chiName", user.getChiName());
				request.getSession().setAttribute("uname", user.getName());
				request.getRequestDispatcher("/main.jsp").forward(request, response);
				}else{
				request.setAttribute("error", "抱歉，用户名或密码错误!");	
				request.setAttribute("errorUrl", "login.html");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			}else{
				//没查到放行
				chain.doFilter(request, response);
			}
		}else{
//			System.out.println("从首页控制器跳转的，直接放行");
			//从首页控制器跳转的，直接放行
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
