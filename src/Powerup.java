import java.awt.Image;


public class Powerup {
	
	private int x;
	private int y;
	private Image img;
	
	public Powerup(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public Image getImage() {
		
		return img;
		
	}
	
	public void setImage(Image img) {
		
		this.img = img; 
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void taken(Program program) {
		
	}
	
	

}
