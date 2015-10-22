import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;


public class Thc extends Powerup {
	

	
	public Thc(int x, int y) {
		
		super(x, y);
		super.setImage(new ImageIcon(this.getClass().getResource("PU_THC.png")).getImage());
		
		
		
	}
	
	public void stopThc(Program p, Snoop s) {
		Program.thc = false;
		TransparentWindow.stoppedThc = true;
		p.dancers.remove(s);
		
	}
	

	@Override
	public void taken(Program program) {
		Program.thc = true;
		Program.playSound("SmokeWeedEveryday.wav");
		Snoop s = new Snoop(program, getX(), getY());
		program.getDancers().add(s);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  stopThc(program, s);
			  }
			}, 24000);
	}
	


}
