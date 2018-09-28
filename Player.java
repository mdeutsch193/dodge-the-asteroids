/*
 * Martin Deutsch
 * 11/23/15
 * Player.java
 */
 
import java.util.*;
import java.awt.*;

// represents a cell the user can control
public class Player extends Cell {
	
	// the player's current health
	private int health;
	
	// calls the parent constructor
	public Player(double x0, double y0) {
		super(x0, y0);
		this.health = 3;
	}
	
	 // updates the cell's position
 	public void updateState(Landscape scape) {
 		
 		// move the player
 		int dir = scape.getDirection();
 		if (dir < 0 && this.getX() > 2) {
 			this.setX(this.getX()-5);
 		}
 		if (dir > 0 && this.getX() < scape.getWidth()-5) {
 			this.setX(this.getX() + 5);
 		}
 		
 		// if hit by asteroid, decrement health
 		MyArrayList<Cell> agents = scape.getAgents();
 		for (Cell agent : agents) {
 			if (agent instanceof Asteroid) {
 				if (this.isNeighbor(agent)) {
 					this.health--;
 				}
 			}
 		}
	}
 
	// checks if the given cell is within 5 of this cell
 	public boolean isNeighbor(Cell cell) {
 		if (Math.abs(this.getX() - cell.getX()) < 4) {
 			if (Math.abs(this.getY() - cell.getY()) < 4) {
 				return true;
 			}
 		}
 		return false;
 	}	
 	
 	// returns the current health of the player
 	public int getHealth() {
 		return this.health;
 	}
 	
 	// draws the cell
 	public void draw(Graphics g, int x0, int y0, int scale) {
 		int x = x0 + (int)(this.getX() * scale);
		int y = y0 + (int)(this.getY() * scale);
		if (this.health == 3) {
			g.setColor(new Color(192, 192, 192));
		}
		else if (this.health == 2) {
			g.setColor(new Color(255, 130, 0));
		}
		else if (this.health == 1) {
			g.setColor(new Color(255, 0, 0));
		}
		else g.setColor(new Color(0, 0, 0));
		
		g.fillOval(x, y, scale+7, scale+7);
	}
	
	// unit test
	public static void main(String[] args) {
		Player p = new Player(0, 0);
		System.out.println(p);
	}
}