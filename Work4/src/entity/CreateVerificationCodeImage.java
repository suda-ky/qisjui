package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;



public class CreateVerificationCodeImage {
	
	public static final int WIDTH = 100; //图片宽度
	public static final int HEIGHT = 30; //图片高度
	public static final int LENGTH = 4; //验证码长度
	public static final int LINECOUNT = 20; //干扰线条数
	
	//验证码的所有字符，去掉不可识别o01i等字符
	public static final String str = "23456789"+"abcdefghjkmnpqrstuvwxyz"+"ABCDEFGHJKMNPQRSTUVWXYZ";
	
	public static Random random = new Random();
	
	//生成四位验证码
	public String createCode() {
		String code = "";
		for (int i = 0; i < LENGTH; i++) {
			//从上边的字符库随机抽取四个字符，放入code当中
			char c = str.charAt(random.nextInt(str.length()));
			code += c;
		}
		return code;
	}
	
	//获取颜色
	public Color getColor() {
		return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
	}
	
	//字体样式
	public Font getFont() {
		return new Font("Fixedsys",Font.CENTER_BASELINE,22);
	}
	
	//绘制字符
	public void drawChar(Graphics g,String code) {
		g.setFont(getFont());//调用随机生成的字体样式
		for (int i = 0; i < LENGTH; i++) {
			char c = code.charAt(i);
			g.setColor(getColor());//调用随机生成的颜色
			g.drawString(c+"", 20*i+10, 20);
		}
	}
	
	//绘制随机线
	public void drawLine(Graphics g) {
		int x = random.nextInt(WIDTH);
		int y = random.nextInt(HEIGHT);
		int x1 = random.nextInt(13);
		int y1 = random.nextInt(15);
		g.setColor(getColor());//调用随机生成的颜色
		g.drawLine(x, y, x+x1, y+y1);
	}
	public BufferedImage createImage(String code) {
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		//获取画笔
		Graphics g = image.getGraphics();
		//设置背景颜色并绘制矩形背景
		g.setColor(getColor());
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//绘制验证码
		drawChar(g, code);
		//绘制随机线
		drawLine(g);
		//绘制图片
		g.dispose();
		
	return image;
	}
	
	
}


