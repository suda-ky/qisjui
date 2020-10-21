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
		// 閿�姣�

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		//杩囨护鏂规硶锛屼富瑕佸request鍜宺esponse杩涜涓�浜涘鐞嗭紝鐒跺悗浜ょ粰涓嬩竴涓繃婊ゅ櫒鎴栬�卻ervlet
			HttpServletRequest request = (HttpServletRequest)req;
			HttpServletResponse response = (HttpServletResponse)resp;
			HttpSession session = request.getSession();
			//鑾峰彇璁块棶鍦板潃
			String path = request.getServletPath();
			System.out.println("permissonfilter"+path);
			//闄や簡鍒ゆ柇main.jsp,鍏朵粬鐨勬斁琛�
			if (notCheckUrl.contains(path)) {
				//鐢ㄦ埛鏉冮檺鎺у埗
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
					System.out.println(uname);
					//璇诲彇鏁版嵁搴�
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
					//濡傛灉涓簍rue锛屽垯鏀捐锛屽鏋滀负false鍒欒浆鍒癳rror.jsp;
					if (flag) {
						chain.doFilter(request, response);
					}else{
						request.setAttribute("error", "鎶辨瓑锛屽綋鍓嶇敤鎴锋病鏈夎闂璧勬簮鐨勬潈闄�!");
						request.setAttribute("errorUrl", "main.jsp");
						request.getRequestDispatcher("/error.jsp").forward(request, response);
					}
				}else{
					chain.doFilter(req, resp);
				}
			}else{
				//鑾峰彇session涓瓨鏀剧殑鍊硷紝濡傛灉鏈夊�煎垯鏀捐锛屽惁鍒欒烦杞埌error.jsp
				String uname = (String)session.getAttribute("chiName");
					if (uname!=null) {
						chain.doFilter(req, resp);
					}else{
						request.setAttribute("error", "鎶辨瓑锛屾偍娌℃湁鐧婚檰涓嶈兘璁块棶璇ヨ祫婧�!");
						request.setAttribute("errorUrl", "login.html");
						request.getRequestDispatcher("/error.jsp").forward(request, response);
					}
			}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 鍒濆鍖�
		//鑾峰彇閰嶇疆涓殑鍒濆鍖栧��,杩欎簺璺緞涓嶆嫤鎴�
		notCheckUrl = config.getInitParameter("notCheckUrl");
	}

}
