package SpaceShooting;

import java.util.ArrayList;
import java.awt.Image;
import java.util.*;

import javax.swing.ImageIcon;

/**
 * This is a class called Peter, which extends the Unit function. It will save
 * the attack image, x and y coordinates, hp, and speed of the Peter ,and
 * castSkill method will be called when the Peter can use his skills. When the
 * skill is cast, the Peter uses 4 random strong attacks. These attacks include
 * punching 4 fists, throwing 6 mushrooms, droping guns, and throwing minecraft
 * blocks towards the player, which deals a ton of damage.
 * 
 * @author taekyeong
 *
 */

public class Peter extends Unit {
	// Create a class random
	Random ran = new Random();
	// Create variables to store 4 images of Peter's attack
	private Image attackImage1, attackImage2, attackImage3, attackImage4;
	// Create an ArrayList called skills, which is going to be used to figure out
	// which skill Peter is going to use
	private ArrayList<Integer> skills = new ArrayList<Integer>();

	public Peter(int x, int y, int hp, int speed, Image image) {
		// Get the variables from its parent class
		super(x, y, hp, speed, image);
		// Get images for four each attaks that Peter has
		attackImage1 = new ImageIcon("src/images/fist.png").getImage();
		attackImage2 = new ImageIcon("src/images/mushroom.png").getImage();
		attackImage3 = new ImageIcon("src/images/apex.png").getImage();
		attackImage4 = new ImageIcon("src/images/minecraft.png").getImage();
		// Add 1~4 inside the arrayList
		for (int i = 1; i <= 4; i++)
			skills.add(i);
		// Get the width and height of Peter using the image given
		setWidth(image.getWidth(null));
		setHeight(image.getHeight(null));

	}

	public void castSkill(ArrayList<Attack> enemyAttackList, int currentTime) {
		// If the Peter can cast its skill by comparing the time he can use skill and
		// the current time
		if (onCooldown(currentTime)) {
			// Shuffle the arraylist
			Collections.shuffle(skills);
			// Create a variable that will be used to check what skill Peter is going to use
			// this turn and pull out the front number inside the shuffled ArrayList
			int usingSkill = skills.get(0);
			// Based on usingSkill's value cast put different attacks inside the
			// enemyAttackList
			switch (usingSkill) {
			// If Peter is using his first skill: punching 4 fists
			case 1:
				// Create a for-loop which will run 4 times
				for (int i = 0; i < 4; i++) {
					// Randomly get the possible y-coordinates where the attack can be drawn in the
					// frame
					int spawnY = ran.nextInt(720);
					// Create the attack based on using Peter's x coordinate and the random y
					// coordinate and attackImage1
					Attack attack = new Attack(getX(), spawnY, 25, 12, 0, 50, attackImage1);
					// Add the attack inside the enemyAttackList
					enemyAttackList.add(attack);
					// Set the new time when Peter can use his skill by adding the current time and
					// the cooldown of the used skill
					setOnCool(currentTime + attack.getCooldown());
				}
				break;
			// If Peter is using his second skill: throwing 5 mushrooms
			case 2:
				// Randomly get the possible y-coordinates where the attack can be drawn in the
				// frame
				int spawnY = ran.nextInt(720);
				// Loop 5 times, use the variable j that will determine the y-speed of the
				// attack
				for (int j = -6; j <= 6; j += 3) {
					// Create the attack based on using Peter's x coordinate and the random y
					// coordinate and attackImage2, and j
					Attack attack = new Attack(getX(), spawnY, 25, 8, j, 50, attackImage2);
					// Add the attack inside the enemyAttackList
					enemyAttackList.add(attack);
					// Set the new time when Peter can use his skill by adding the current time and
					// the cooldown of the used skill
					setOnCool(currentTime + attack.getCooldown());
				}
				break;
			// If Peter is using his third skill: dropping guns
			case 3:
				// Randomly get the possible x-coordinates where the attack can be drawn in the
				// frame
				int spawnX = ran.nextInt(900);
				// Create the attack based on using random x-coordinate and the attackImage3's
				// height and put 0 inside the attack x-speed and put negative value in the
				// y-speed so that this attack drops from top to bottom of the screen
				Attack attack = new Attack(spawnX, -attackImage3.getHeight(null), 5, 0, -10, 100, attackImage3);
				// Add the attack inside the enemyAttackList
				enemyAttackList.add(attack);
				// Set the new time when Peter can use his skill by adding the current time and
				// the cooldown of the used skill
				setOnCool(currentTime + attack.getCooldown());
				break;
			// If Peter is using his fourth skill: shooting mine craft blocks
			case 4:
				// Randomly get the possible y-coordinates where the attack can be drawn in the
				// frame
				int spawnY1 = ran.nextInt(720 - attackImage4.getHeight(null));
				// Create the attack based on using Peter's x-coordinate, the random
				// y-coordiante, and attackImage4
				Attack attack1 = new Attack(getX(), spawnY1, 200, 7, 0, 200, attackImage4);
				// Add the attack inside the enemyAttackList
				enemyAttackList.add(attack1);
				// Set the new time when Peter can use his skill by adding the current time and
				// the cooldown of the used skill
				setOnCool(currentTime + attack1.getCooldown());
				break;

			}
		}
	}
}
