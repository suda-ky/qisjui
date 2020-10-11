package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.DownLoadDao;
import entity.DownloadList;



public class DownLoadController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			String download = request.getParameter("download");
			String id = request.getParameter("id");
			if (download.equals("download")) {
				DownLoadDao dLoadDao = new DownLoadDao();
				DownloadList dl = dLoadDao.downloadToId(Integer.parseInt(id));
				if (dl !=null) {
					String path = dl.getPath();

					response.setContentType(getServletContext().getMimeType(path.substring(9)));
					/*ʹ��outputStream����ͷ����������������������أ��ļ����أ���������ֻ�ܴ��ļ���ȴ���ܱ��浽��Ӧ��Ŀ¼λ��
					�ļ����ع�����web�����о���ʹ�õ��Ĺ��ܣ�ʹ��HttpServletResponse����Ϳ���ʵ���ļ�����
					getOutputStream():��ͷ��˷������ݣ��ֽ�����getWrite():��ͷ��˷������� ���ֽ�����*/
					      //1.��ȡҪ���ص��ļ��ľ���·��
					response.setHeader("Content-Disposition", "attachment;filename="+new String(path.substring(9).getBytes("gb2312"),"ISO-8859-1"));
					//3.����content-disposition��Ӧͷ��������������ص���ʽ���ļ�
					//����content-disposition��Ӧͷ�������������������ʽ�򿪣�����ע���ļ��ַ��������ʽ������utf-8����Ȼ���������
					String fullFileName = getServletContext().getRealPath("/"+path);
					InputStream in = new FileInputStream(fullFileName);
					OutputStream out = response.getOutputStream();
		
					int b;
					while ((b=in.read())!=-1) {
						out.write(b);
					}
					in.close();
					out.close();
				}else{
					request.setAttribute("error", "��Ǹ����Դ���ش���!");
					request.setAttribute("errorUrl", "downLoadList.jsp");
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
				
			}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
