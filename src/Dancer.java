import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;


public abstract class Dancer {
	
	private String name;
	private int numFrames;
	private int currentFrame;
	private int x;
	private int y;
	private int delay;
	private int currentDelay;
	
	
	public Dancer(Program p, int x, int y, String name, int numFrames, int delay , int start, int time) {
		this.name = name;
		this.numFrames = numFrames;
		this.delay = delay;
		currentFrame = 0;
		currentDelay = start;
		this.x = x;
		this.y = y;
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  p.dancers.remove(this);
			  }
			}, time);

	}
	
	public void nextFrame() {
		
		if(delay == currentDelay) {
			currentDelay = 0;
			incFrame();
		}
		else {
			currentDelay++;
		}
		

	}
	
	public void incFrame() {
		if(currentFrame == numFrames - 1) {
			currentFrame = 0;
		}
		else {
			currentFrame++;
		}
	}
	
	public int getCurrentDelay() {
		return currentDelay;
	}
	
	public Image getFrame() {
		
		String frameString = "";
		
		if(currentFrame < 10) {
			frameString = "0" + currentFrame;
		}
		else {
			frameString = "" + currentFrame;
		}
		
		String end = "";
		
		if(name == "snoop" || name == "link") {
			end = ".gif";
		}
		else {
			end = ".png";
		}
		
		//System.out.println(name + "/" + "frame_0" + frameString + ".gif");
		return new ImageIcon(this.getClass().getResource(name + "/" + "frame_0" + frameString + end)).getImage();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	

}
