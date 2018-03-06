package XEPHINH;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Client extends JFrame implements ActionListener {
	private JPanel jpn;
	private Image img;
	private Boolean [] check=new Boolean[100];
	private int[][] pos;
	int checkwin [][];
	int width, height;
	private Vector<Image> vt= new Vector<Image>(); 
	private JButton jbtn,jbtn1;
	
	private ImageIcon imgIcon;
	private static int n=3;
	DataInputStream dis;
	DataOutputStream dos;
	BufferedImage image;
	
	public Client() {
		
		//-------------------------------------------------------
		try {
			System.out.println("nhan tu server");
			Socket client= new Socket("localhost", 5555);
			System.out.println("doc port");
			dis= new DataInputStream(client.getInputStream());
			System.out.println("luong nhan");
			dos= new DataOutputStream(client.getOutputStream());
			System.out.println("luong gui");
			ImageInputStream im= ImageIO.createImageInputStream(client.getInputStream());
			System.out.println("nhan image input stream");
			image=ImageIO.read(im);
			System.out.println("nhan anh");
			imgIcon= new ImageIcon(image);
			System.out.println("cho anh vao game");
			//n= dis.readInt();	
			//client.close();
		}catch(Exception e) {
			System.out.println("Error 2");
		}
		
		//----------------------------------------------------
	
		
		pos= arr();
		jpn= new JPanel();
		jpn.setLayout(new GridLayout(n, n, 0, 0));
		//ImageIcon imgIcon= new ImageIcon("img2.jpg");
		
		img= imgIcon.getImage();
		width= imgIcon.getIconWidth();
		height= imgIcon.getIconHeight();
		this.add(jpn, BorderLayout.CENTER);
		

        for ( int i = 0; i < n; i++) {
            for ( int j = 0; j < n; j++) {
                  Image imgTemp = createImage((ImageProducer) new FilteredImageSource(img.getSource(),
                        new CropImageFilter(j*width/n, i*height/n, (width/n), height/n)));
                  vt.add(imgTemp);
            }
        }
        for(int i = 0; i < (n*n); i++)
        {
        	check[i] = false;
        }
		
        checkwin= new int[n][n];
        for(int i =0; i<n; i++) {
        	for(int j=0; j<n; j++) {
        		if(i== (n-1) && j== (n-1)) {
        			checkwin[i][j]= n*n-1;
        			jbtn1= new JButton();
        			jpn.add(jbtn1);
        		}
        		else {
        			jbtn= new JButton();
            		jbtn.addActionListener(this);
            		Random rd= new Random();
            		int index= Math.abs(rd.nextInt(n*n-1));
            		while(check[index]) {
            			index = Math.abs(rd.nextInt(n*n-1));
            		}
            		jbtn.setIcon(new ImageIcon(vt.get(index)));
            		checkwin[i][j]= index;
            		check[index]= true;
            		jpn.add(jbtn);
        		}
        	}
        } 
		setSize(500,500);
		setVisible(true);
		setTitle("xep hinh");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Client();		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		JButton jbtn2= (JButton) e.getSource();
		Dimension size= jbtn2.getSize();
		int xC= jbtn2.getX();
		int yC= jbtn2.getY();
		int x0=jbtn1.getX();
		int y0=jbtn1.getY();
		int posX= xC/size.width;
		int posY= yC/size.height;
		int btnIndex= pos[posY][posX];
		int btnIndex1= pos[y0/size.height][x0/size.width];
		
		if(x0== xC && (y0-yC)== size.height) {
			jpn.add(jbtn1, btnIndex);
			jpn.add(jbtn2, btnIndex1);
			jpn.validate();
			int temp = checkwin[posY][posX];
            checkwin[posY][posX]=checkwin[posY+ 1][posX];
            checkwin[posY+ 1][posX]=temp;
            win(checkwin);
		}		
		if(x0== xC && (yC- y0)== size.height) {
			jpn.add(jbtn1, btnIndex);
			jpn.add(jbtn2, btnIndex1);
			jpn.validate();
			int temp = checkwin[posY][posX];
            checkwin[posY][posX]=checkwin[posY-1][posX];
            checkwin[posY-1][posX]=temp;
            win(checkwin);
		}		
		if(y0== yC && (x0- xC)== size.width) {
			jpn.add(jbtn1, btnIndex);
			jpn.add(jbtn2, btnIndex1);
			jpn.validate();
			int temp = checkwin[posY][posX];
            checkwin[posY][posX]=checkwin[posY][posX+ 1];
            checkwin[posY][posX+ 1]=temp;
            win(checkwin);
		}		
		if(y0== yC && (xC- x0)== size.width) {
			jpn.add(jbtn1, btnIndex);
			jpn.add(jbtn2, btnIndex1);
			jpn.validate();
			int temp = checkwin[posY][posX];
            checkwin[posY][posX]=checkwin[posY][posX- 1];
            checkwin[posY][posX- 1]=temp;
            win(checkwin);
		}			
	}
	
	public void win(int[][] checkwin){
		int a[][] = arr();
		boolean kt = true ;
         for(int i = 0 ; i< n ;i++){
        	 for(int j = 0 ; j<n; j++){
       		   if(checkwin[i][j]==a[i][j]){
       			   
       		   }else{
       			   kt=false;
       			break;
       		   }
       	  }
       	  System.out.println();
         }
         if(kt){
            int click = JOptionPane.showConfirmDialog(null, "You win , ban co muon tiep tuc");
            if (click==JOptionPane.YES_OPTION) {
            /*	 try {
            		Socket client= new Socket("localhost", 5000);
     				dos= new DataOutputStream(client.getOutputStream());
     				Boolean tt= true;
     				dos.writeBoolean(tt);
     				
            	 }catch (Exception e) {
					// TODO: handle exception
            		 System.out.println("Error 3");
				}*/
            }
            if (click==JOptionPane.NO_OPTION) {
                 System.exit(1);;
            }  	 
         }	
	}
	
	private static int[][] arr() {
		int a[][]= new int[n][n];
		for(int i=0; i<n; i++) {
			for(int j=0; j<n;j++) {
				a[i][j]= j+ i*n;
			}
		}
		return a;
	}
}
