import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;


public class ThugLife extends Powerup {
	
	
	public ThugLife(int x, int y) {
		
		super(x, y);
		super.setImage(new ImageIcon(this.getClass().getResource("PU_Thuglife.png")).getImage());
		
		
		
	}
	
	public void stopThuglife() {
		Program.thuglife = false;
	}
	
	
	@Override
	public void taken(Program program) {
		Program.thuglife = true;
		Program.playSound("ThugLife.wav");
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  stopThuglife();
			  }
			}, 10000);
	}
	

	

}
