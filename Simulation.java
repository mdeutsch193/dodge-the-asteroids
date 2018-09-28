/* 
 * Simulation.java
 * Martin Deutsch
 * 11/23/15
 */
 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
 /*
  * Creates a UI and runs the game 
  */
 public class Simulation {
 	
 	// the list of high scores
 	private MyHashMap highScores;
 	// the landscape with astroids and the player
 	private Landscape landscape;
 	// the display where the game is displayed
	private LandscapeDisplay display;
	// controls whether the game is playing, paused or stopped
	private enum PlayState { PLAY, PAUSE, STOP }
	// the current state of the game
	private PlayState state;
	// number of iterations
	private int iterations;
	
	// initialize the game
	public Simulation(int rows, int cols) {
		highScores = new MyHashMap();
		this.landscape = new Landscape(rows, cols);
		this.display = new LandscapeDisplay(landscape, 5);
		this.iterations = 0;
		
		landscape.initialize();
		this.state = PlayState.PLAY;
		this.setupUI();
	}
	
	// setup the panel where the game is displayed
	public void setupUI() {
		JButton pause = new JButton("Pause");
		JButton quit = new JButton("Quit");
		JButton left = new JButton("--->");
		JButton right = new JButton("<---");
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.add(right);
		panel.add(pause);
		panel.add(quit);
		panel.add(left);
		
		this.display.add(panel, BorderLayout.SOUTH);
		this.display.pack();
		
		// listen for keystrokes
		Control control = new Control();
		pause.addActionListener(control);
		quit.addActionListener(control);
		left.addActionListener(control);
		right.addActionListener(control);
		this.display.addKeyListener(control);
		this.display.setFocusable(true);
		this.display.requestFocus();
	}
	
	// runs one iteration of the simulation
	public boolean iterate() throws InterruptedException
	{
		this.iterations++;
		boolean stillPlaying = true;
		if (this.state == PlayState.PLAY)
		{
			// update the landscape and display
			stillPlaying = this.landscape.advance();
			this.display.update();
		}
		Thread.sleep(100);
		return stillPlaying;
	}
	
	// displays the high scores and asks if the user wants to play again
	public boolean displayHighScores() throws InterruptedException {
		final JFrame frame = new JFrame("High Scores");
    	frame.setPreferredSize(new Dimension(400, 400));
                
		// get the user's name and add it to the high scores list
        final JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel instructions = new JLabel("Enter your name:");
		final JTextField field = new JTextField(10);
		
		field.addActionListener(new ActionListener() { // when user enters their name
			public void actionPerformed(ActionEvent e) {
				if (highScores.get(field.getText()) == null ||
						(int) highScores.get(field.getText()) < iterations) {
        			highScores.put(field.getText(), iterations);
        		}
        		state = PlayState.PAUSE;
    		}
		});
		
        pane.add(instructions);
        pane.add(field);       
		frame.add(pane, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		while (state != PlayState.PAUSE) {
			Thread.sleep(2000);
		}
		
		// display the high scores list
		JTextArea text = new JTextArea(highScores.toString());
		text.setEditable(false);
		JPanel scores = new JPanel(new FlowLayout(FlowLayout.CENTER));
		scores.add(text);
		frame.remove(field);
		frame.add(scores);
		frame.pack();
		
		// ask if the user wants to play again
		JLabel label = new JLabel("Play again?");
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		JPanel playAgain = new JPanel(new FlowLayout(FlowLayout.CENTER));
		playAgain.add(label);
		playAgain.add(yes);
		playAgain.add(no);
		frame.add(playAgain, BorderLayout.SOUTH);
		frame.pack();
		yes.addActionListener(new ActionListener() { // if user hits "yes" button
			public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		display.dispose();
        		reset();
    		}
		});
		
		no.addActionListener(new ActionListener() { // if user hits "no" button
			public void actionPerformed(ActionEvent e) {
        		frame.dispose();
				state = PlayState.STOP;
    		}
		});
		
		while (state == PlayState.PAUSE) {
			Thread.sleep(1000);
		}
		if (state == PlayState.STOP) {
			return false;
		}
		else return true;
	}
	
	// reset and restart the game 
	public void reset() {
		this.landscape.reset();
		this.landscape.setDirection(0);
		this.display = new LandscapeDisplay(landscape, 5);
		this.iterations = 0;
		
		landscape.initialize();
		this.state = PlayState.PLAY;
		
		this.setupUI();
	}
	
	// allows keyboard controls
	private class Control extends KeyAdapter implements ActionListener
	{
		// responds to key presses
		public void keyTyped(KeyEvent e)
		{
			if (("" + e.getKeyChar()).equalsIgnoreCase("p") && 
				state == PlayState.PLAY)
			{
				state = PlayState.PAUSE;
				System.out.println("Game paused");
			}
			else if (("" + e.getKeyChar()).equalsIgnoreCase("p") && 
				state == PlayState.PAUSE)
			{
				state = PlayState.PLAY;
				System.out.println("Game resumed");
			}
			else if (("" + e.getKeyChar()).equalsIgnoreCase("a")) {
				landscape.setDirection(-1);
			}
			else if (("" + e.getKeyChar()).equalsIgnoreCase("d")) {
				landscape.setDirection(1);
			}
			else if (("" + e.getKeyChar()).equalsIgnoreCase("q"))
			{
				state = PlayState.STOP;
				System.out.println("Game ended");
			}
		}

		// responds to button presses
		public void actionPerformed(ActionEvent event)
		{
			if (event.getActionCommand().equalsIgnoreCase("Pause") &&
				state == PlayState.PLAY)
			{
				state = PlayState.PAUSE;
				((JButton) event.getSource()).setText("Play");
			}
			else if (event.getActionCommand().equalsIgnoreCase("Play") &&
				state == PlayState.PAUSE)
			{
				state = PlayState.PLAY;
				((JButton) event.getSource()).setText("Pause");
			}
			else if (event.getActionCommand().equalsIgnoreCase("Quit"))
			{
				state = PlayState.STOP;
			}
			else if (event.getActionCommand().equalsIgnoreCase("--->"))
			{
				landscape.setDirection(1);
			}
			else if (event.getActionCommand().equalsIgnoreCase("<---"))
			{
				landscape.setDirection(-1);
			}
		}
	}
		
	// runs the game
	public static void main(String[] args) throws InterruptedException {
		Simulation game = new Simulation(100, 150);
		do {
			boolean playing = true;
		
			while (playing && game.state != PlayState.STOP)
			{
				playing = game.iterate();
			}
		}
		while (game.displayHighScores());
		
		// close application
		game.display.dispose();
	}
}