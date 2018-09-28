/*
 * Martin Deutsch
 * 11/23/15
 * Spawner.java
 */
 
import java.util.*;
import java.awt.*;

// represents a cell which spawns asteroids
public class Spawner extends Cell {

	// calls the parent constructor
	public Spawner(double x0, double y0) {
		super(x0, y0);
	}
	
	// spawns 2 new asteroid agents
	public void updateState(Landscape scape) {
		Random gen = new Random();
		for (int i = 0; i < 2; i++) {
			scape.addAgent( new Asteroid(gen.nextDouble()*scape.getWidth(), 0) );
			}
	}
	
	// does nothing
 	public void draw(Graphics g, int x, int y, int scale) {
 		return;
	}
	
	// unit test
	public static void main(String[] args) {
		Spawner s = new Spawner(0, 0);
		System.out.println(s.toString());
	}
}