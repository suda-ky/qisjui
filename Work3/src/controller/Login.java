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
			request.setCharacterEncoding("utf-8");//缁熶竴缂栫爜
			//鎺ュ彈鍓嶅彴鏁版嵁
			String name = request.getParameter("username");
			String pwd  = request.getParameter("password");
			String vimage = request.getParameter("vimage");//鐢ㄦ埛杈撳叆鐨勯獙璇佺爜
			String verity = (String)request.getSession().getAttribute("verity");
			String autoLogin = request.getParameter("autoLogin");//鏄惁鍏嶇櫥褰�
			
//			System.out.println(name+"\n"+pwd+"\n"+vimage+"\n"+autoLogin);
//			System.out.println("鐢ㄦ埛锛�"+vimage+"\n鐢熸垚"+verity);
			
			//鍒涘缓涓�涓狹ap,瀛樻斁杩斿洖鐨勬暟鎹�
			Map<String, Object> map = new HashMap<String, Object>();
			//璋冪敤user鏁版嵁搴�
			//鏍￠獙楠岃瘉鐮�
			if (verity.equalsIgnoreCase(vimage)) {
				//楠岃瘉鐮侀�氳繃锛屽垯鍒ゆ柇鐢ㄦ埛鍚嶆垨瀵嗙爜
				LoginDao lDao = new LoginDao();
				User user = lDao.loginSelect(name, pwd);
				if (user!=null) {
					//鐢ㄦ埛鍚嶆纭紝鍒ゆ柇瀵嗙爜
					map.put("code", 0);
					map.put("info", "鐧婚檰鎴愬姛");
					//灏嗗垽鏂殑锛堟槸鍚﹂�夋嫨浜嗗厤鐧诲綍锛屼腑鏂囧悕锛夋暟鎹斁鍏ュ埌session褰撲腑
					request.getSession().setAttribute("chiName", user.getChiName());
					request.getSession().setAttribute("uname", user.getName());
					if (pwd.equals(user.getPassWord())) {
						//鏌ヨ鏁版嵁搴撴纭紝骞堕�夋嫨浜嗕竷澶╁唴鍏嶇櫥褰曪紝鍒欏垱寤篶ookie
						if (autoLogin != null && autoLogin.equals("ok")) {
								Cookie cookie1 = new Cookie("username", name);
								Cookie cookie2 = new Cookie("password", pwd);
								//璁剧疆瀛樺叆鏃堕暱锛屼竷澶�
								cookie1.setMaxAge(7*24*60*60);
								cookie2.setMaxAge(7*24*60*60);
								//璁剧疆璺緞
								System.out.println("cookiePath:"+request.getContextPath());
								cookie1.setPath(request.getContextPath());
								cookie2.setPath(request.getContextPath());
								//鍙戦�佸埌娴忚鍣�
								response.addCookie(cookie1);
								response.addCookie(cookie2);
						}
					}else {
						map.put("code", 3);
						map.put("info", "瀵嗙爜涓嶆纭紒");
					}
				}else{
					map.put("code", 2);
					map.put("info", "鐢ㄦ埛鍚嶄笉瀛樺湪锛�");
					}
			}else{
				map.put("code", 1);
				map.put("info", "楠岃瘉鐮佷笉姝ｇ‘锛�");
			}
			//璋冪敤瀵煎叆鐨刧son鏋跺寘
			String json = new Gson().toJson(map);//灏唌ap杞寲鎴愬瓧绗︿覆
			//杈撳嚭json鍒伴〉闈�
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
	}

}
