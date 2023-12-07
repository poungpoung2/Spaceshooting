package SpaceShooting;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This is a class called Enemy1, which extends the Unit function. It will save
 * the attack image, x and y coordinates, hp, and speed of the enemy1 by
 * inheriting Unit class,and castSkill method will be called when the enemy1 can
 * cast skills. When the skill is cast, the Enemy1 shoots one bullet moving from
 * left to right , which can attack the player.
 * 
 * @author taekyeong
 *
 */
public class Enemy1 extends Unit {
	// Create a variable to store Enemy1's attack image
	private Image attackImage;

	public Enemy1(int x, int y, int hp, int speed, Image image) {
		// Get the variables from its parent class
		super(x, y, hp, speed, image);
		// Get the Enemy1's attack image from the file
		attackImage = new ImageIcon("src/images/Enemy1_attack.png").getImage();
		// Set width and height of player's image based on the Enemy1's image
		setWidth(image.getWidth(null));
		setHeight(image.getHeight(null));
	}

	public void castSkill(ArrayList<Attack> enemyAttackList, int currentTime) {
		// If the enemy can cast its skill by comparing the time it can use skill and
		// the current time
		if (onCooldown(currentTime)) {
			// Create a new attack of Enemy1, by using the Enmey2's x y coordinates and
			// attackImage
			Attack attack = new Attack(getX(), getY() + 15, 10, 6, 0, 100, attackImage);
			// Add Enemy'1s attack in the enemyAttackList
			enemyAttackList.add(attack);
			// Set the new time when Enemy1 can use its skill by adding the current time and
			// the cooldown of the used skill
			setOnCool(currentTime + attack.getCooldown());
		}
	}
}
