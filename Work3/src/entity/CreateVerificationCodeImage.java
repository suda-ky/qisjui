package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


public class CreateVerificationCodeImage {
	
	public static final int WIDTH = 100; //鍥剧墖瀹藉害
	public static final int HEIGHT = 30; //鍥剧墖楂樺害
	public static final int LENGTH = 4; //楠岃瘉鐮侀暱搴�
	public static final int LINECOUNT = 20; //骞叉壈绾挎潯鏁�
	
	//楠岃瘉鐮佺殑鎵�鏈夊瓧绗︼紝鍘绘帀涓嶅彲璇嗗埆o01i绛夊瓧绗�
	public static final String str = "23456789"+"abcdefghjkmnpqrstuvwxyz"+"ABCDEFGHJKMNPQRSTUVWXYZ";
	
	public static Random random = new Random();
	
	//鐢熸垚鍥涗綅楠岃瘉鐮�
	public String createCode() {
		String code = "";
		for (int i = 0; i < LENGTH; i++) {
			//浠庝笂杈圭殑瀛楃搴撻殢鏈烘娊鍙栧洓涓瓧绗︼紝鏀惧叆code褰撲腑
			char c = str.charAt(random.nextInt(str.length()));
			code += c;
		}
		return code;
	}
	
	//鑾峰彇棰滆壊
	public Color getColor() {
		return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
	}
	
	//瀛椾綋鏍峰紡
	public Font getFont() {
		return new Font("Fixedsys",Font.CENTER_BASELINE,22);
	}
	
	//缁樺埗瀛楃
	public void drawChar(Graphics g,String code) {
		g.setFont(getFont());//璋冪敤闅忔満鐢熸垚鐨勫瓧浣撴牱寮�
		for (int i = 0; i < LENGTH; i++) {
			char c = code.charAt(i);
			g.setColor(getColor());//璋冪敤闅忔満鐢熸垚鐨勯鑹�
			g.drawString(c+"", 20*i+10, 20);
		}
	}
	
	//缁樺埗闅忔満绾�
	public void drawLine(Graphics g) {
		int x = random.nextInt(WIDTH);
		int y = random.nextInt(HEIGHT);
		int x1 = random.nextInt(13);
		int y1 = random.nextInt(15);
		g.setColor(getColor());//璋冪敤闅忔満鐢熸垚鐨勯鑹�
		g.drawLine(x, y, x+x1, y+y1);
	}
	public BufferedImage createImage(String code) {
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		//鑾峰彇鐢荤瑪
		Graphics g = image.getGraphics();
		//璁剧疆鑳屾櫙棰滆壊骞剁粯鍒剁煩褰㈣儗鏅�
		g.setColor(getColor());
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//缁樺埗楠岃瘉鐮�
		drawChar(g, code);
		//缁樺埗闅忔満绾�
		drawLine(g);
		//缁樺埗鍥剧墖
		g.dispose();
		
	return image;
	}
	
	
}


