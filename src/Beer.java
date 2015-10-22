
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;


public class Beer extends Powerup {
	

	
	public Beer(int x, int y) {
		
		super(x, y);
		super.setImage(new ImageIcon(this.getClass().getResource("PU_Beer.png")).getImage());
		
		
		
	}
	
	public void startWiggle() {
		Program.playSound("Wiggle.wav");
		Program.wiggle = true;
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  stopWiggle();
			  }
			}, 10000);
	}
	
	public void stopWiggle() {
		Program.wiggle = false;
		TransparentWindow.stoppedWiggle = true;
	}
	
	
	@Override
	public void taken(Program program) {
		Program.playSound("Drinking.wav");

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  startWiggle();
			  }
			}, 3200);
	}
	

	

}
