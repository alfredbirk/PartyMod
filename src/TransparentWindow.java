import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class TransparentWindow {
	
	Program p;
	Window w;
	Graphics myGraphics;
	int red = 255;
	int green = 0;
	int blue = 0;
	int state = 0;
	int change = 15;
	int wiggleDir = 0;
	static boolean stoppedWiggle = false;
	static boolean stoppedThc = false;
	
	Image glasses = new ImageIcon(this.getClass().getResource("Glasses.png")).getImage();
	
	
	public TransparentWindow(Program p) {
		this.p = p;
	}
	
	public void refresh() {

		if(w == null) {
			
	
			w = new Window(null)
			{
			  @Override
			  public void paint(Graphics g)
			  {
					  if(myGraphics == null) {
						  myGraphics = g;
					  }
					  
					  if(stoppedThc == true) {
						  g.clearRect(p.getCaptureX(), p.getCaptureY(), Program.roomWidth, Program.roomHeight);
						  stoppedThc = false;
						  
					  }
					
					  if(Program.thc == false) {
					    ArrayList<Powerup> powerups = p.getPowerups();
					    
					    for(int i = 0; i < powerups.size(); i++) {

					    	Powerup pu = powerups.get(i);
					    	
					    	
					    	g.drawImage(pu.getImage(), pu.getX() + p.getCaptureX() - p.getPUSize() / 2, pu.getY() + p.getCaptureY() - p.getPUSize() / 2, p.getPUSize(), p.getPUSize(), this);
					    }
					}

				    
				    
				    ArrayList<Dancer> dancers = p.getDancers();
				    
				    for(int i = 0; i < dancers.size(); i++) {
				    	
				    	Dancer dancer = dancers.get(i);
				    	Image frame = dancer.getFrame();
				    	
				    	if(dancer.getCurrentDelay() >= 0) {
				    		g.drawImage(frame, dancer.getX() + p.getCaptureX() - frame.getWidth(null) / 2, dancer.getY() + p.getCaptureY() - frame.getHeight(null) / 2, frame.getWidth(null), frame.getWidth(null), this);			    		
				    	}
				    	
				    	dancer.nextFrame();
				    	
				    }
				    

				    if(Program.thc == true) {
					    if(state == 0) {
					    	if(red >= change && green <= 255 - change) {
					    		red -= change;
					    		green += change;
					    	}
					    	else {
					    		state = 1;
					    		red = 0;
					    		green = 255;
					    	}
					    }
					    else if(state == 1) {
					    	if(green >= change && blue <= 255 - change) {
					    		green -= change;
					    		blue += change;
					    	}
					    	else {
					    		state = 2;
					    		green = 0;
					    		blue = 255;
					    	}
					    }
					    else if(state == 2) {
					    	if(blue >= change && blue <= 255 - change) {
					    		blue -= change;
					    		red += change;
					    	}
					    	else {
					    		state = 0;
					    		blue = 0;
					    		red = 255;
					    	}
					    }
					    
					    Color myColor = new Color(red, green, blue, 50 );
					    g.setColor(myColor);
					    
					    if(Program.hide == true) {
						    Color noColor = new Color(0, 0, 0, 0);
						    g.setColor(noColor);
					    }
					    

					    	g.fillRect(p.getCaptureX(), p.getCaptureY(), Program.roomWidth, Program.roomHeight);					    						    	

					    
				    }
					    

					    if(Program.wiggle == true) {
					    	
					    	if(wiggleDir == 0) {
					    		p.straight();
					    		p.left();
					    	}
					    	
					    	if(wiggleDir == 10) {
					    		p.straight();
					    		p.right();
					    	}

					    	wiggleDir++;
					    	
					    	if(wiggleDir >= 20) {
					    		wiggleDir = 0;
					    	}
					    	
					    }

					    
					    if(stoppedWiggle == true) {
					    	p.straight();
					    	stoppedWiggle = false;
					    }
					    
					    
					    if(Program.thuglife == true && Program.thc == false) {
					    	g.drawImage(glasses, p.getHeadX() + p.getCaptureX(), p.getHeadY() + p.getCaptureY(), 29, 6, this);
					    }
					    
					    



				    /*
				    Color noColor = new Color(0, 0, 0, 0);
				    g.setColor(noColor);
					*/
					    
				    
				    
			    
			    
			  }
			  
			 
			  
			  @Override
			  public void update(Graphics g)
			  {
			    paint(g);
			  }
			};
		}
		else {
			w.paint(myGraphics);
		}
		
		w.setAlwaysOnTop(true);
		w.setBounds(w.getGraphicsConfiguration().getBounds());
		w.setBackground(new Color(0, true));
		w.setVisible(true);
		
	}
	
	
}

