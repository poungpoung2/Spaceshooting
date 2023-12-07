package SpaceShooting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This is a class that inherits JFrame that manages screens and keyInput of the
 * game. This class checks the players keyboard input and update the current
 * status of the player, by checking player's movement (WASD), attack (Space).
 * Also, it displays new screens on the JFrame based on the the player's
 * keyInput. Pressing Enter will start the game on the MainScreen and pressing
 * Esc will end the game.
 * 
 * @author taekyeong
 *
 */

public class SpaceShooting extends JFrame {
	// Create an variable, which will be used to make transition and formation of
	// images on the JFrame smoothly
	private Image bufferImage;
	// Create an variable. which will be used to draw/display image on the JFrame
	private Graphics screenGraphic;

	// Create variables to store each following background images: main screen,
	// loading screen, gaming screen,
	private Image mainScreen = new ImageIcon("src/images/main.png").getImage();
	private Image loadingScreen = new ImageIcon("src/images/loading.png").getImage();
	private Image gameScreen = new ImageIcon("src/images/Game_screen.png").getImage();

	// Create an boolean variable that will be used to check which screen is
	// displayed on the frame
	private boolean isMainScreen, isLoadingScreen, isGameScreen;

	// Create an object game that will be used to manage the actual gameplay
	public static Game game = new Game();

	// Set the frame that will display the content
	public SpaceShooting() {
		// Set the title of the frame
		setTitle("Space Shooting");
		// Remove the header of the frame
		setUndecorated(true);
		// Set the size of the frame to the width and height of the screen
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		// Do not let the player to change the size of the screen
		setResizable(false);
		// Locate the frame at the center of the screen
		setLocationRelativeTo(null);
		// If the program closes fully stop all the program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set the frame visible
		setVisible(true);
		setLayout(null);
		// Call init to initialize the variables
		init();
	}

	/**
	 * An method to initialize the variables
	 */
	public void init() {
		// Set isMainScreen true as the main screen will be displayed when the game is
		// first started and other screens to false
		isMainScreen = true;
		isLoadingScreen = false;
		isGameScreen = false;
		// Add the made KeyListener that will be used to detect player's keyInput
		addKeyListener(new KeyListener());
	}

	/**
	 * This is an method that will be called when the game is started. This method
	 * will change the background to the instruction screen for 2.5 seconds and
	 * display game background and actually start the game
	 */
	private void gameStart() {
		// Set isMainScren false
		isMainScreen = false;
		// Set isLoading screen true;
		isLoadingScreen = true;
		// Create an timer that will be used to count 2.5 seconds of time
		Timer loadingTimer = new Timer();
		// Create an timetask, which will be used to show the gameScreen after 2.5
		// seconds
		TimerTask loadingTask = new TimerTask() {
			@Override
			public void run() {
				// Set the loading screen to false
				isLoadingScreen = false;
				// Set the game screen to true
				isGameScreen = true;
			}
		};
		// After 2.5 seconds use the loadingTask variable to change the background to
		// the game background screen
		loadingTimer.schedule(loadingTask, 2500);
		// Start the actual game
		game.start();
	}

	/*
	 * This is an method that will be used to display the bufferImage and also the
	 * background image of the JFrame
	 */
	public void paint(Graphics g) {
		// Create bufferImage of the size of the screen
		bufferImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		// Set the graphics of the screen using the bufferImage
		screenGraphic = bufferImage.getGraphics();
		// Call screenDraw to actually draw the background image
		screenDraw(screenGraphic);
		// Draw the bufferImage of the screen on the JFrame
		g.drawImage(bufferImage, 0, 0, null);
	}

	/**
	 * This an method that will be used to draw the background image that would be
	 * displayed in the current status by using the boolean variables (isMainScreen,
	 * isLoadingScreen, isGameScreen)
	 * 
	 * @param g
	 */
	public void screenDraw(Graphics g) {
		// If the main screen should be displayed
		if (isMainScreen) {
			//Draw mainScreen
			g.drawImage(mainScreen, 0, 0, null);
		}
		// If the loading screen should be displayed
		if (isLoadingScreen) {
			//Draw loadingScreen
			g.drawImage(loadingScreen, 0, 0, null);
		}
		// If the game screen should be displayed
		if (isGameScreen) {
			//Draw the background image
			g.drawImage(gameScreen, 0, 0, null);
			//Call gameDraw to draw the components of the game (Units, attacks, health bars, score)
			game.gameDraw(g);
		}
		// Update the paint cycle to make sure the current screen is displayed
		this.repaint();
	}

	/*
	 * This is a class called KeyListener. This class will read player's keyboard
	 * input and based on it, it will manage player's movement(WASD), attack
	 * (Space), starting of the game (Enter), and quit (Esc)
	 */
	class KeyListener extends KeyAdapter {

		@Override
		/**
		 * Create a method that checks if a certain key is pressed on the keyboard
		 */
		public void keyPressed(KeyEvent e) {
			// Get the key that is pressed
			switch (e.getKeyCode()) {
			// If the Enter key ispressed
			case KeyEvent.VK_ENTER:
				// If main screen is projected now
				if (isMainScreen) {
					// Start the game
					gameStart();
				}
				break;
			// If the ESC key is pressed
			case KeyEvent.VK_ESCAPE:
				// Quit the program
				System.exit(0);
				break;
			// If W is pressed
			case KeyEvent.VK_W:
				// Set up true in game so that the player can move up
				game.setUp(true);
				break;
			// If S is pressed
			case KeyEvent.VK_S:
				// Set down true in game so that the player can move down
				game.setDown(true);
				break;
			// If A is pressed
			case KeyEvent.VK_A:
				// Set left true in game so that the player can move left
				game.setLeft(true);
				break;
			// If D is pressed
			case KeyEvent.VK_D:
				// Set right true in game so that the player can move right
				game.setRight(true);
				break;
			// If Space is pressed
			case KeyEvent.VK_SPACE:
				// Set shooting true in game so that the player can move shoot its attacks
				game.setShooting(true);
				break;
			}
		}

		/**
		 * Create a method that will check when the player stops pressing certain keys
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			// Ge the key that is released
			switch (e.getKeyCode()) {
			// If W is released
			case KeyEvent.VK_W:
				// Set up false so that the player stops moving up
				game.setUp(false);
				break;
			// If S is released

			case KeyEvent.VK_S:
				// Set down false so that the player stops moving down
				game.setDown(false);
				break;
			// If A is released

			case KeyEvent.VK_A:
				// Set left false so that the player stops moving left
				game.setLeft(false);
				break;
			// If D is released
			case KeyEvent.VK_D:
				// Set right false so that the player stops moving right
				game.setRight(false);
				break;
			// If Space is released
			case KeyEvent.VK_SPACE:
				// Set space false so that the player stops moving shooting bullets
				game.setShooting(false);
				break;
			}
		}
	}

}
