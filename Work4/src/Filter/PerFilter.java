package Filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.ResourceDao;
import entity.Resource;

public class PerFilter implements Filter {
	private String notCheckUrl;

	@Override
	public void destroy() {
		// 销毁

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		//过滤方法，主要对request和response进行一些处理，然后交给下一个过滤器或者servlet
			HttpServletRequest request = (HttpServletRequest)req;
			HttpServletResponse response = (HttpServletResponse)resp;
			HttpSession session = request.getSession();
			//获取访问地址
			String path = request.getServletPath();
			System.out.println("permissonfilter"+path);
			//除了判断main.jsp,其他的放行
			if (notCheckUrl.contains(path)) {
				//用户权限控制
				if (path.equals("/MainController.do")) {
					String message = request.getParameter("message");
					String user = request.getParameter("user");
					String download = request.getParameter("download");
					String uname = (String)session.getAttribute("uname");
					boolean flag = false;//
					if (message !=null &&message.equals("logout")) {
//						chain.doFilter(request, response);
						flag = true;
					}
//					System.out.println(uname);
					//读取数据库
					List<Resource> resList =new ResourceDao().resourceSelect(uname);
					if (resList!=null) {
						for (Resource resource : resList) {
							String[] tmp = resource.getUrl().split("=");
							for (int i = 0; i < tmp.length; i++) {
								if (download!=null&& download.equals(tmp[i])) {
									flag = true;
									break;
								}
								if (user!=null&& user.equals(tmp[i])) {
									flag = true;
									break;
								}
							}
						}
					}
					//如果为true，则放行，如果为false则转到error.jsp;
					if (flag) {
						chain.doFilter(request, response);
					}else{
						request.setAttribute("error", "抱歉，当前用户没有访问该资源的权限!");
						request.setAttribute("errorUrl", "main.jsp");
						request.getRequestDispatcher("/error.jsp").forward(request, response);
					}
				}else{
					chain.doFilter(req, resp);
				}
			}else{
				//获取session中存放的值，如果有值则放行，否则跳转到error.jsp
				String uname = (String)session.getAttribute("chiName");
					if (uname!=null) {
						chain.doFilter(req, resp);
					}else{
						request.setAttribute("error", "抱歉，您没有登陆不能访问该资源!");
						request.setAttribute("errorUrl", "login.html");
						request.getRequestDispatcher("/error.jsp").forward(request, response);
					}
			}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 初始化
		//获取配置中的初始化值,这些路径不拦截
		notCheckUrl = config.getInitParameter("notCheckUrl");
	}

}
