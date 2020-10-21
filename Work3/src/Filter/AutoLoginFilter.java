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
 *鑷姩鐧诲綍杩囨护鍣�
 *杩囨护鍣ㄤ箣闂寸殑璺宠浆锛屾槸鏍规嵁XXXFilter鐨勯瀛楁瘝澶у皬鎺掑簭杩涜璺宠浆鐨�
 *鍏堟墽琛孉Filter锛屽啀鎵цBFilter锛屼互姝ょ被鎺�
 *鏈夊彲鑳戒篃鏄洜涓哄垱闂寸殑鍏堝悗椤哄簭
 *
 *濡傛灉鏄�夋嫨浜嗗厤鐧诲綍鐨勭洿鎺ヨ烦杞埌棣栭〉

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
		//鑾峰彇璁块棶鍦板潃
		String path = request.getServletPath();
		System.out.println("autofilter"+path);
		//鑾峰緱cookie涓殑鍊�
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
				//鏍规嵁cookie涓殑鍊硷紝鍘绘煡璇㈡暟鎹簱 濡傛灉姝ｇ‘鍒欎紶鍒伴椤碉紝鍚﹀垯璺冲埌閿欒椤甸潰
			LoginDao logindao = new LoginDao();
			User user = logindao.loginSelect(uname, pwd);
			if (user!=null) {
//				request.setAttribute("username", user.getName());
//				request.setAttribute("password", user.getPassWord());
//				request.setAttribute("vimage", "abc");
//				request.getSession().setAttribute("verity","abc");
				request.getSession().setAttribute("chiName", user.getChiName());
				request.getRequestDispatcher("/main.jsp").forward(request, response);
				}else{
				request.setAttribute("error", "鎶辨瓑锛岀敤鎴峰悕鎴栧瘑鐮侀敊璇�!");	
				request.setAttribute("errorUrl", "login.html");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
			}else{
				//娌℃煡鍒版斁琛�
				chain.doFilter(request, response);
			}
		}else{
//			System.out.println("浠庨椤垫帶鍒跺櫒璺宠浆鐨勶紝鐩存帴鏀捐");
			//浠庨椤垫帶鍒跺櫒璺宠浆鐨勶紝鐩存帴鏀捐
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
