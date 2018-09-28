/* 
 * Landscape.java
 * Martin Deutsch
 * 11/23/15
 */
 
 import java.util.*;
 
 /*
  * Represents a 2D grid of Cell objects
  */
 public class Landscape {
 	
 	 // the height in pixels of the landscape
 	private double height;
 
 	// the width in pixels of the landscape
 	private double width;
 
 	// an arraylist of all the agents in the landscape
 	private MyArrayList<Cell> agents;
 	
 	// negative if the player is moving left, positive if moving right
 	private int direction;
 	
 	// creates a landscape with the given number of rows and 
 	// columns, and initializes the arraylist and player direction
 	public Landscape(int rows, int cols) {
 		this.height = (double) rows;
 		this.width = (double) cols;
 		this.agents = new MyArrayList<Cell>();
 		this.direction = 0;
 	}
 	
 	// clears the landscape of agents
 	public void reset() {
 		this.agents.clear();
 	}
 	
 	// returns the height rounded to an integer
 	public int getRows() {
 		return (int)(this.height+.5);
 	}
 	
 	// returns the height as a double
 	public double getHeight() {
 		return this.height;
 	}
 	
  	// returns the number of columns
 	public int getCols() {
 		return (int)(this.width+.5);
 	}
 	
 	// returns the width as a double
 	public double getWidth() {
 		return this.width;
 	}
 	
 	// adds cell c to the landscape
 	public void addAgent(Cell c) {
 		this.agents.add(c);
 	}
 	
 	// removes cell c from the landscape
 	public void removeAgent(Cell c) {
 		this.agents.remove(c);
 	}
 	
 	// returns the list of agents
 	public MyArrayList<Cell> getAgents() {
 		return this.agents;
 	}
 	
 	// sets the direction for the player to move
 	public void setDirection(int dir) {
 		this.direction = dir;
 	}
 	
 	// returns the direction the player is moving
 	public int getDirection() {
 		return this.direction;
 	}
 	
 	// calls updateState on each cell in the landscape
 	public boolean advance() {
 		Player p = (Player) agents.get(0);
 		if (p.getHealth() <= 0) {
 			return false;
 		}
 		else {
 			for (Cell c : this.agents) {
 				c.updateState(this);
 			}
 			return true;
 		}
 	}
 	
 	// initializes the landscape with a spawner and the player
 	public void initialize() {
 		this.addAgent(new Player(this.width/2, this.height-5));
 		this.addAgent(new Spawner(0, 0) );
	}
 	
 	// unit test
	public static void main(String[] args) {
		Landscape scape = new Landscape(50, 75);
		scape.initialize();
		for (Cell c : scape.getAgents() ) {
			System.out.println(c);
		}
		scape.advance();
		for (Cell c : scape.getAgents() ) {
			System.out.println(c);
		}
	}
 }