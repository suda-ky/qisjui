package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.CreateVerificationCodeImage;

public class CreateVerfifyCodeImageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//璋冪敤鐢熸垚楠岃瘉鐮佺殑绫�
		CreateVerificationCodeImage creCodeImage = new CreateVerificationCodeImage();
		//浜х敓鍥涗綅闅忔満鏁�
		String code = creCodeImage.createCode();
		//鑾峰彇HttpSession瀵硅薄
		HttpSession session = request.getSession();
		//灏嗛殢鏈烘暟瀛樻斁鍒皊ession瀵硅薄涓�
		session.setAttribute("verity",code);
		//璁剧疆杩斿洖鐨勫唴瀹�
		response.setContentType("img/jpeg");
		//璁剧疆娴忚鍣ㄤ笉缂撳瓨鍥剧墖锛岀偣涓�娆″氨鍒锋柊涓�娆�
		response.setHeader("Prama", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		//璁剧疆楠岃瘉鐮佸け鏁堟椂闂�
		response.setDateHeader("Expires", 0);
		//璋冪敤楠岃瘉鐮佺被涓殑createImage()鏂规硶,鐢熸垚鍥剧墖锛屼互瀛楄妭娴佸彂閫侊紝浜ょ粰img鐨剆rc灞炴�цВ鏋�
		BufferedImage vImage = creCodeImage.createImage(code); 
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(vImage, "jpeg", out);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
