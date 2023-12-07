package SpaceShooting;

/**
 * This is a program that crates a game called space shooting. There is a player
 * ship that a player can control with WASD and shot missiles with a space key.
 * There are enemies that are attacking players. There are two type of basic
 * enemies. By killing enemies, player should score 20,000 points in order to
 * fight the boss, Peter Hassard. When the player kills the boss, the player
 * clears the game.
 * 
 * @author taekyeong
 *
 */

public class Main {
	// Declare the screen width and height for the game
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;

	public static void main(String[] args) {

		// Start the game
		new SpaceShooting();

	}
}
