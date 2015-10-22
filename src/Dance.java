import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;


public class Dance extends Powerup {
	
	Dancer d;
	
	public Dance(int x, int y) {
		
		super(x, y);
		super.setImage(new ImageIcon(this.getClass().getResource("PU_Dance.png")).getImage());
		
	}
	
	
	
	@Override
	public void taken(Program program) {
		Program.playSound("EverybodyDanceNow.wav");
		
		int r = Program.randInt(0, 2);
		
		
		
		if(r == 0) {
			d = new Snape(program, getX(), getY());
		}
		else if(r == 1) {
			d = new Bellsprout(program, getX(), getY());
		}
		else if(r == 2) {
			d = new Link(program, getX(), getY());
		}
		
		
		program.getDancers().add(d);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  program.getDancers().remove(d);
			  }
			}, 17000);
	}
	

}
