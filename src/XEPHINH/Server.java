package XEPHINH;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Server {
	
	public final static int port= 5555;
	int n=3;
	DataOutputStream dos;
	DataInputStream dis;
	public Server() {
		try {
			ServerSocket server =new ServerSocket(port);
			Socket client;
			Boolean tt= true;
			while(true) {
				client= server.accept();
				System.out.println("OK");
				dos= new DataOutputStream(client.getOutputStream());
				dis = new DataInputStream(client.getInputStream());
				ImageIO.write(ImageIO.read(new File("E:/Ddd/java&eclipse/Java/Ltm/img1.jpg")), "jpg", client.getOutputStream());
				System.out.println("gui anh");
					
			 	Random rd= new Random();
				int n= 3+ Math.abs(rd.nextInt(3));
				System.out.println(n);
				dos.writeInt(n);
				System.out.println("gui n");
				tt= dis.readBoolean();				
				//client.close();
			
			}
		}catch (Exception e) {
			System.out.println("Error 1");
		}		
	}
	
	public static void main(String[] args) {
		new Server();
	}
}
