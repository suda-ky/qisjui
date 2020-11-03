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
		
		//调用生成验证码的类
		CreateVerificationCodeImage creCodeImage = new CreateVerificationCodeImage();
		//产生四位随机数
		String code = creCodeImage.createCode();
		//获取HttpSession对象
		HttpSession session = request.getSession();
		//将随机数存放到session对象中
		session.setAttribute("verity",code);
		//设置返回的内容
		response.setContentType("img/jpeg");
		//设置浏览器不缓存图片，点一次就刷新一次
		response.setHeader("Prama", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		//设置验证码失效时间
		response.setDateHeader("Expires", 0);
		//调用验证码类中的createImage()方法,生成图片，以字节流发送，交给img的src属性解析
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
