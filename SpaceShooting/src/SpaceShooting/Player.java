package SpaceShooting;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This is a class called player, which extends the Unit function. It will save
 * the attack image, x and y coordiatnes, hp, and speed of the player ,and
 * castSkill method will be called when the player presses the space button.
 * When the skill is cast, the player shoots one bullet moving from right to
 * left , which can attack enemies.
 * 
 * @author taekyeong
 *
 */

public class Player extends Unit {
	// Create a variable to store player's attack image
	private Image attackImage;

	public Player(int x, int y, int hp, int speed, Image image) {
		// Get the variables from its parent class
		super(x, y, hp, speed, image);
		// Get the player's attack image from the file
		attackImage = new ImageIcon("src/images/player_attack.png").getImage();
		// Set width and height of player's image based on the player image
		setWidth(image.getWidth(null));
		setHeight(image.getHeight(null));
	}

	public void castSkill(ArrayList<Attack> attacks, int currentTime) {
		// If the player can cast its skill by comparing the time he or she can use
		// skill and the current time
		// time
		if (onCooldown(currentTime)) {
			// Create a new attack of Player, by using the Enmey2's x y coordinates and
			// attackImage
			Attack attack = new Attack(getX() + 70, getY() + 25, 5, -15, 0, 25, attackImage);
			// Add player's attack in the attacks arraylist
			attacks.add(attack);
			// Set the new time when player can use his or her skill by adding the current
			// time and
			// the cooldown of the used skill
			setOnCool(currentTime + attack.getCooldown());
		}
	}

}
