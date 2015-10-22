import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Program {
	
	BufferedImage oldImage;
	Robot robot;
	HashMap map;
	boolean foundCapture;
	boolean active;
	Color myColor;
	private ArrayList<Powerup> powerups;
	private int PUSize;
	private int headX;
	private int headY;
	private int captureX;
	private int captureY;
	public ArrayList<Dancer> dancers;
	public static int roomWidth = 0;
	public static int roomHeight = 0;
	public static boolean thc = false;
	public static boolean wiggle = false;
	public static boolean thuglife = false;
	public static boolean hide = false;
	
	public final static Color orange = new Color(255, 136, 52);
	public final static Color red = new Color(255, 69, 69);
	public final static Color lime = new Color(106, 255, 48);
	public final static Color pink = new Color(255, 164, 186);
	public final static Color yellow = new Color(255, 233, 43);
	public final static Color blue = new Color(68, 68, 255);
	public final static Color cyan = new Color(2, 209, 192);
	
	
	public Program() throws AWTException {
		oldImage = null;
		robot = new Robot();
		map = new HashMap();
		foundCapture = false;
		active = false;
		myColor = cyan;
		powerups = new ArrayList<Powerup>();
		dancers = new ArrayList<Dancer>(); 
		PUSize = 50; //dele room size på 18 senere
		

	}
	


	public BufferedImage processImage(BufferedImage raw) {
		
		BufferedImage img = deepCopy(raw);
		int headSumX = 0;
		int headSumY = 0;
		int headCount = 0;
		active = false;
		
		for(int y=0; y<img.getHeight(); y++)
		{
			for(int x=0; x<img.getWidth(); x++)
			{
				
				Color newColor = new Color(img.getRGB(x, y));
				Color oldColor = new Color(oldImage.getRGB(x, y));
				
				
				if (oldColor.equals(Color.BLACK) && newColor.equals(myColor)) {
					active = true;
					headSumX += x;
					headSumY += y;
					headCount++;
					img.setRGB(x, y, new Color(255, 255, 255).getRGB());
					map.put("" + x + y, 1);
				}
				

			}
		}
	
		if(headCount != 0) {
			headX = headSumX / headCount;
			headY = headSumY / headCount;
			
			img.setRGB((int)headX, (int)headY, new Color(255, 0, 0).getRGB());
		}
	
		//System.out.println("Active: " + active);
		return img;
	}
	
	public void collisionDetection() {
		
		for(int i = 0; i < powerups.size(); i++) {
			
			Powerup pu = powerups.get(i);
			
			
			if(Math.sqrt(Math.pow(headX - pu.getX(), 2) + Math.pow(headY - pu.getY(),  2)) < PUSize * 0.72 ) {
				powerups.get(i).taken(this);
				powerups.remove(i);
			}
			
		}
		
		//System.out.println("headX: " + headX);
		//System.out.println("PU x: " + powerups.get(0).getX());
		
	}
	
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public ArrayList<Integer> findCapture(int width, int height) {
		
		int startX = 1;
		int startY = 1;
		
		ArrayList<Integer> c = new ArrayList<Integer>();
		c.add(0);
		c.add(0);
		c.add(0);
		c.add(0);
		
		Rectangle fullScreen = new Rectangle(startX, startY, (int) width - 1, (int) height - 1);
		BufferedImage img = robot.createScreenCapture(fullScreen);
		

		for(int y=startY; y<img.getHeight(); y++)
		{
			for(int x=startX; x<img.getWidth(); x++)
			{
				
				Color a = new Color(img.getRGB(x, y));
				int sum = a.getRed() + a.getGreen() + a.getBlue();
				
				try {
					if (!a.equals(Color.BLACK) &&  a.getRed() == a.getGreen() && a.getGreen() == a.getBlue() && sum > 150) {
						
						if(img.getRGB(x, y + 1) == Color.BLACK.getRGB() && img.getRGB(x + 100, y + 1) == Color.BLACK.getRGB() && img.getRGB(x, y + 100) == Color.BLACK.getRGB()) {
							Color a2 = new Color(img.getRGB(x - 1, y + 1));
							int sum2 = a2.getRed() + a2.getGreen() + a2.getBlue();
							if((!a2.equals(Color.BLACK) &&  a2.getRed() == a2.getGreen() && a2.getGreen() == a2.getBlue() && sum2 > 150)) {
								c.set(0, x);
								c.set(1, y + 1);
							}
						}
					}
						
					if (!a.equals(Color.BLACK) &&  a.getRed() == a.getGreen() && a.getGreen() == a.getBlue()) {
						if(img.getRGB(x - 1, y) == Color.BLACK.getRGB()) {
							if(img.getRGB(x - 1, y + 1) == Color.BLACK.getRGB() && img.getRGB(x - 100, y + 1) == Color.BLACK.getRGB() && img.getRGB(x - 1, y - 100) == Color.BLACK.getRGB()) {
								Color a3 = new Color(img.getRGB(x - 1, y + 2));
								int sum3 = a3.getRed() + a3.getGreen() + a3.getBlue();
								if(!a3.equals(Color.BLACK) &&  a3.getRed() == a3.getGreen() && a3.getGreen() == a3.getBlue() && sum3 > 150) {
									c.set(2, x - 1);
									c.set(3, y);
									System.out.println(c.get(0) + ", " + c.get(1) + ", " + c.get(2) + ", " + c.get(3));
									System.out.println("" + (c.get(2) - c.get(0)) + ", " + (c.get(3) - c.get(1)));
									break;
								}
							}
						}
					}
				}
				catch(Exception e) {
					
				}
				
				
			}
		}
		
		if(c.get(0) != 0 && c.get(1) != 0 && c.get(2) != 0 && c.get(3) != 0) {
			if(Math.abs((c.get(2) - c.get(0)) - (c.get(3) - c.get(1))) < 3) {
				foundCapture = true;	
				captureX = c.get(0);
				captureY = c.get(1);
				roomWidth = c.get(2) - c.get(0);
				roomHeight = c.get(3) - c.get(1);
			}
		}
		
		return c;
		
	}
	
	public void left() {
		robot.keyPress(KeyEvent.VK_LEFT);
	}
	
	public void right() {
		robot.keyPress(KeyEvent.VK_RIGHT);
	}
	
	public void straight() {
		robot.keyRelease(KeyEvent.VK_RIGHT);
		robot.keyRelease(KeyEvent.VK_LEFT);
	}
	
	public ArrayList<Powerup> getPowerups() {
		return powerups;
	}
	
	public ArrayList<Dancer> getDancers() {
		return dancers;
	}
	
	public int getPUSize() {
		return PUSize;
	}
	
	public int getCaptureX() {
		return captureX;
	}
	
	public int getCaptureY() {
		return captureY;
	}
	
	public int getHeadX() {
		return headX;
	}
	
	public int getHeadY() {
		return headY;
	}
	
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		          Program.class.getResource("sounds/" + url));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	public static int randOne() {
		int[] myIntArray = {20, -20};
		
		int size = 2;
		int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
		
		return myIntArray[item];
		
	}
	
	public void spawnPowerups() {
		
		int r = randInt(0, 1000);
		if(r < 15) {
			
			int x = randInt(30, roomWidth - 30);
			int y = randInt(30, roomHeight - 30);
			
			int type = randInt(0, 3);
			if(type == 0) {
				powerups.add(new Dance(x, y));
			}
			else if(type == 1) {
				powerups.add(new Thc(x, y));
			}
			else if(type == 2) {
				powerups.add(new Beer(x, y));
			}
			else if(type == 3) {
				powerups.add(new ThugLife(x, y));
			}
			
		}
		
	}
	

	
    
    public static void main(String[] args) throws AWTException {
        
        int del = 30;
		int k = 10000;
		
		Program program = new Program();
		
		TransparentWindow window = new TransparentWindow(program);
		window.refresh();

		
		// Creates the delay of 5 sec so that you can open notepad before
		// Robot start writting
		program.robot.delay(2000);
		System.out.println("typing");
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		ArrayList<Integer> c = new ArrayList<Integer>();
		
		JFrame frame = new JFrame();
		
		

		frame.getContentPane().setLayout(new FlowLayout());
		ImageIcon ic = new ImageIcon(program.getClass().getResource("banner2.png"));
		frame.getContentPane().add(new JLabel(ic));

		
		 String labels[] = { "red", "orange", "pink", "yellow","blue", "cyan", "lime"};
		JComboBox comboBox1 = new JComboBox(labels);
		comboBox1.setMaximumRowCount(5);
		comboBox1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBox1.getSelectedItem());

                String s = comboBox1.getSelectedItem().toString();
                
                if(s == "red") {
                	program.myColor = red;
                }
                else if(s == "orange") {
                	program.myColor = orange;
                }
                else if(s == "pink") {
                	program.myColor = pink;
                }
                else if(s == "yellow") {
                	program.myColor = yellow;
                }
                else if(s == "blue") {
                	program.myColor = blue;
                }
                else if(s == "cyan") {
                	program.myColor = cyan;
                }
                else if(s == "lime") {
                	program.myColor = lime;
                }
            }
        });

		
		frame.getContentPane().add(comboBox1, BorderLayout.SOUTH); 
		

		
		frame.pack();
		frame.setVisible(true);
		
		
		while(program.foundCapture == false) {
			
			c = program.findCapture((int) width, (int) height);
			
			if(program.foundCapture == false) {
				System.out.println("Did not find capture");
			}
			
			program.robot.delay(100);
		}
		
		System.out.println("Found capture!");
		program.powerups.add(new Dance(200, 200));

		window.refresh();
		
		for(int i = 0; i < c.size(); i++) {
			System.out.println(c.get(i));
		}
		
		Rectangle captureSize = new Rectangle(c.get(0), c.get(1), c.get(2) - c.get(0), c.get(3) - c.get(1));
		BufferedImage rawImage = program.robot.createScreenCapture(captureSize);

		
		for(int i = 0; i < k; i++) {

			

			
			window.refresh();

			program.oldImage = rawImage;
			rawImage = program.robot.createScreenCapture(captureSize);
			BufferedImage image = program.processImage(rawImage);




			program.collisionDetection();
			
			if(thc == false)
				program.spawnPowerups();

						
			
					




			
			
			
			if(hide == true) {
				hide = false;
			}
		


			
			
			
		}
		System.out.println("Done");
    }
}
