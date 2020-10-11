package controller;


import java.awt.image.BufferedImage;
import java.io.IOException;

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
		
	
		CreateVerificationCodeImage creCodeImage = new CreateVerificationCodeImage();
		//产生四位随机字符
		String code = creCodeImage.createCode();
		HttpSession session = request.getSession();
		//将产生的四位随机字符串存放于session中
		session.setAttribute("verity",code);
		response.setContentType("img/jpeg");
		response.setHeader("Prama", "no-cache");
		response.setHeader("Cache-Control", "no-cache");

		response.setDateHeader("Expires", 0);
	
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
