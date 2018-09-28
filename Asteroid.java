/*
 * Martin Deutsch
 * 11/23/15
 * Asteroid.java
 */
 
import java.util.*;
import java.awt.*;

// Represents a cell which moves down the screen
public class Asteroid extends Cell {
	
	// calls the parent constructor
	public Asteroid(double x0, double y0) {
		super(x0, y0);
	}
	
	 // updates the cell's position
 	public void updateState(Landscape scape) {
 		this.setY(this.getY() + 5);
 		
		// if asteroid is outside the landscape, it is removed
		if (this.getY() < 0) {
			scape.removeAgent(this);
			return;
		}
	}
 		
 	// draws the cell
 	public void draw(Graphics g, int x0, int y0, int scale) {
 		int x = x0 + (int)(this.getX() * scale);
		int y = y0 + (int)(this.getY() * scale);
		
		g.setColor(new Color(255, 255, 0));
		g.fillRect(x, y, scale, scale);
	}
	
	// unit test
	public static void main(String[] args) {
		Asteroid a = new Asteroid(10, 10);
		System.out.println(a);
	}
}