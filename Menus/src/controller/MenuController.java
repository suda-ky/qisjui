package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.MenuDao;

import com.google.gson.Gson;

import entity.Menu;

public class MenuController extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MenuController() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String flag = request.getParameter("flag");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
//		String pid = request.getParameter("pid");
		MenuDao dao = new MenuDao();
		
		Map<String, Object> map = new HashMap<String, Object>();
//		先判断是查询还是修改或者删除
		if (flag.equals("query")) {
			//如果查到数据，则放入到map中
			List<Menu> list = dao.queryMenu();
			if (list!=null) {
				map.put("code", 1);
				map.put("menus", list);
			}else {
				map.put("code", 0);
			}
		}else if(flag.equals("update")){//更新节点
			if(dao.updateMenu(new Menu(Integer.parseInt(id),name,0))){
				map.put("code", 1);
			}else{
				map.put("code", 0);
			}
		}else{//删除
			if(dao.deleteMenu(new Menu(Integer.parseInt(id),name,0))){
				map.put("code", 1);
			}else{
				map.put("code", 0);
			}
		}
//		System.out.println(map.toString());
		//转化成json字符串
		String json = new Gson().toJson(map);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

}
