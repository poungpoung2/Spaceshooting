package SpaceShooting;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This is a class called Enemy2, which extends the Unit function. It will save
 * the attack image, x and y coordinates, hp, and speed of the enemy1 by
 * inheriting Unit class,and castSkill method will be called when the enemy1 can
 * cast skills. When the skill is cast, the Enemy1 shoots 3 bullets moving from
 * left to right. There are going to be one bullet moving straight, one moving
 * diagonally up, and one diagonally down which can attack the player.
 * 
 * @author taekyeong
 *
 */

public class Enemy2 extends Unit {
	// Create a variable to store Enemy2's attack image
	private Image attackImage;

	public Enemy2(int x, int y, int hp, int speed, Image image) {
		// Get the variables from its parent class
		super(x, y, hp, speed, image);
		// Get the Enemy2's attack image from the file
		attackImage = new ImageIcon("src/images/Enemy2_attack.png").getImage();
		// Set width and height of Enemy2's image based on the player image
		setWidth(image.getWidth(null));
		setHeight(image.getHeight(null));
	}

	public void castSkill(ArrayList<Attack> enemyAttackList, int currentTime) {
		// If the enemy can cast its skill by comparing the time it can use skill and
		// the current time
		if (onCooldown(currentTime)) {
			// Use a for loop to create 3 bullets ones that move diagonally up, straight,
			// and diagonally down
			for (int i = -4; i <= 4; i += 4) {
				// Create a new attack of Enemy2, by using the Enmey2's x y coordinates and
				// attackImage
				Attack attack = new Attack(getX(), getY(), 20, 6, i, 300, attackImage);
				// Add the attack to the enemyAttackList
				enemyAttackList.add(attack);
				// Set the new time when Enemy2 can use his skill by adding the current time and
				// the cooldown of the used skill
				setOnCool(currentTime + attack.getCooldown());
			}
		}
	}
}
