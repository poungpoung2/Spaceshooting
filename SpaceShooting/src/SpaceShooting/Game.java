package SpaceShooting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * This is a class that manages the actual game. This class manages the movement
 * and attack process of the player on screen and spawn, movement, and attack of
 * enemies. When an attack overlaps with an Unit, it deals damage to the enemy
 * (player->Enemy, Enemy->player). Player can kill enemies and earn 1000 points
 * for each one. The player's score will be displayed on the top left corner.
 * When the player gets 20000 points, the player will start a fight with the
 * final boss, Peter. When the player defeats Peter, the ending credit will be
 * displayed on the screen. When the hp of the player becomes 0, the game will
 * be over.
 * 
 * @author taekyeong
 *
 */

public class Game extends Thread {
	// Create a variable delay that will decide the delay of the game
	private int delay;
	// Create a variable called pretime which will store the time before cnt
	// increases
	private long pretime;
	// Create a variable cnt that will be used as a mesure of time for cooldown and
	// spawn process
	private int cnt;
	// Create a variable to store player's score
	private int score;
	// Create an object player, which is player's ship for the game
	private Player player;

	// Create images for all the Units in the game
	private Image playerImage;
	private Image enemy1Image;
	private Image enemy2Image;
	private Image peterImage;

	// Create boolean variables to check player's motion (Moving up, down left,
	// right, and attacking)
	private boolean up = false, down = false, left = false, right = false, shooting;
	// Create variables to check if the game is over, if the boss in spawned, if the
	// player completed the game
	private boolean isOver = false, isBoss = false, isVictory = false;

	// Create an ArrayList of attacks to store player's attack
	private ArrayList<Attack> playerAttackList = new ArrayList<Attack>();
	// Create an ArrayList of enemies to store all the enemies
	private ArrayList<Unit> enemyList = new ArrayList<Unit>();
	// Creae an ArrayList of attacks to store all the enemie's attack
	private ArrayList<Attack> enemyAttackList = new ArrayList<Attack>();

	/**
	 * This is method that will be executed when the function starts. It manages
	 * every actions/events in the game
	 */
	@Override
	public void run() {
		// When the class first starts, reset/initialize all the variables and settings
		reset();
		// Keep executing this loop until the program is done
		while (true) {
			// Keep executing this loop until the player dies or completes the game
			while (!isOver && !isVictory) {
				// Get the time before the cnt increases
				pretime = System.currentTimeMillis();
				// If the current time - pretime is smaller the delay
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						// Use Thread.sleep to delay the game for a proper amount of time every time
						Thread.sleep(delay - System.currentTimeMillis() + pretime);
						// Call a method keyProcess to check player's key process
						keyProcess();
						// Call a method to check player's attack and if it attacked any of the enemies
						playerAttackProcess();
						// Call a method to spawn enemies into the fields
						enemySpawnProcess();
						// Call a method to move enemies on the screen
						enemyMoveProcess();
						// Call a method to check if any of the enemies attack hit the player
						enemyAttackProcess();
						// Call a method to check if player crashed with any of the enemies
						crashCheck();
						// Increase cnt
						cnt++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This is method that will be used to iniltize all the variables and setting
	 * before the game starts
	 */
	public void reset() {

		// Set isOver to false as the game just started
		isOver = false;
		// Set cnt to 0
		cnt = 0;
		// Set score to 0
		score = 0;
		// Set the delay of the game to 20
		delay = 20;

		// Set images for each Units in the game
		playerImage = new ImageIcon("src/images/spaceship.png").getImage();
		enemy1Image = new ImageIcon("src/images/Enemy1.png").getImage();
		enemy2Image = new ImageIcon("src/images/Enemy2.png").getImage();
		peterImage = new ImageIcon("src/images/Peter.png").getImage();

		// Initialize player's spawn x, y coordinate, its speed, and hp
		int playerX = 10;
		int playerY = (Main.SCREEN_HEIGHT - 70) / 2;
		int playerSpeed = 10;
		int playerHp = 150;

		// Create a new player object
		player = new Player(playerX, playerY, playerHp, playerSpeed, playerImage);

		// Clear all the ArrayLists that store player's attack, enemy, and enemies
		// attack before the game starts
		playerAttackList.clear();
		enemyList.clear();
		enemyAttackList.clear();

	}

	/**
	 * This is a function that checks if a certain key is pressed to activate
	 * certain actions of the player and updates player's status based on it
	 */
	private void keyProcess() {
		// If up is true (it will be set true when the player pressed W key) and it does
		// not get out of the screen
		if (up && player.getY() - player.getSpeed() > 0)
			// Set player's y coordiante to y-coordinate - it's speed, so the ship moves up
			player.setY(player.getY() - player.getSpeed());
		// If down is true (it will be set true when the player pressed S key) and it
		// does
		// not get out of the screen
		if (down && player.getY() + player.getHeight() + player.getSpeed() < Main.SCREEN_HEIGHT) {
			// Set player's y coordiante to y-coordinate + it's speed, so the ship moves
			// down
			player.setY(player.getY() + player.getSpeed());
		}
		// If left is true (it will be set true when the player pressed A key) and it
		// does
		// not get out of the screen
		if (left && player.getX() - player.getSpeed() > 0)
			// Set player's x coordiante to x-coordinate - it's speed, so the ship moves
			// left
			player.setX(player.getX() - player.getSpeed());
		// If right is true (it will be set true when the player pressed D key) and it
		// does
		// not get out of the screen
		if (right && player.getX() + player.getWidth() + player.getSpeed() < Main.SCREEN_WIDTH)
			// Set player's x coordiante to x-coordinate + it's speed, so the ship moves
			// right
			player.setX(player.getX() + player.getSpeed());
		// If player tries to shooting bullets (it will be set true when the player
		// pressed Space)
		if (shooting) {
			// Use player's castSkill method and send playerAttackList and cnt so the attack
			// can be stored in the arrayList and cnt will be used to update the cooldown
			// time of the player
			player.castSkill(playerAttackList, cnt);
		}
	}

	/**
	 * This is method that will be used to process player's attack. It will fire
	 * player's attack and check if hits any of the enemies if the enemy gets hit
	 * the attack will be removed from the ArrayList and if the enemy dies, give
	 * 1000 points to the player
	 */
	private void playerAttackProcess() {
		// Loop through all the player's attack
		for (int i = 0; i < playerAttackList.size(); i++) {
			// Get the player's attack in each index
			Attack playerAttack = playerAttackList.get(i);
			// Fire the attack
			playerAttack.fire();
			// Loop through all the enemies to check if any of them get hit by the attack
			for (int j = 0; j < enemyList.size(); j++) {
				// Get the enemy in each index
				Unit enemy = enemyList.get(j);
				// If the player's x, y coordinates are greater than the enemies x, y
				// coordinates but each of them are smaller than the enemies-x coordinate +
				// width and y-coordinate + height, the player's attack hit enemy
				if (playerAttack.getX() > enemy.getX() && playerAttack.getX() < enemy.getX() + enemy.getWidth()
						&& playerAttack.getY() > enemy.getY()
						&& playerAttack.getY() < enemy.getY() + enemy.getHeight()) {
					// Reduce enemy's Hp by subtracting player's damage from the enemy's orginal
					// hp
					enemy.setHp(enemy.getHp() - playerAttack.getDamage());
					// Remove player's attack from the arrayList
					playerAttackList.remove(playerAttack);
					// Decrease i by 1 as one of the attack is removed from the arrayList
					i--;
					// If the enemy health is lower than 0
					if (enemy.getHp() <= 0) {
						// Remove enemy from the arrayList
						enemyList.remove(enemy);
						// Decrease j by 1 as one of the enemy is removed from the arrayList
						j--;
						// Add score 1000
						score += 1000;
						// If the killed enemy was Peter
						if (isBoss == true) {
							// Set victory to true as the player completed the game
							isVictory = true;
						}
					}
				}

			}

		}
	}

	/**
	 * This is a method that receives an image and a number and spawn an enemy on
	 * the screen based on the given number
	 * 
	 * @param image
	 * @param x
	 */
	private void spawn(Image image, int x) {
		Random ran = new Random();
		// Get a random y-coordinate in the range where the enemy will be on the screen
		int y = ran.nextInt(720 - image.getHeight(null));
		Unit enemy = null;
		switch (x) {
		// If x is 1
		case 1:
			// Spawn Enemy1
			enemy = new Enemy1(1120, y, 10, 2, image);
			break;
		// If x is 2
		case 2:
			// Spawn Enemy2
			enemy = new Enemy2(1120, y, 20, 2, image);
			break;
		// If x is 3
		case 3:
			// Spawn Peter
			enemy = new Peter(900, (720 - image.getHeight(null)) / 2, 400, 0, image);
			break;
		}
		// Add the spawned enemy into the enmeyList
		enemyList.add(enemy);
	}

	/**
	 * This is a method that spawns enemy on the screen on a each specific time
	 * using cnt. If the score is greater than or equal to 20000, It spawns the
	 * final boss and stops spawning enemies.
	 */
	private void enemySpawnProcess() {
		// If boss is spawned yet spawn enemies
		if (!isBoss) {
			// Do not spawn enemy until cnt becomes bigger than 200, to give player time to
			// prepare and move around
			if (cnt >= 200) {
				// Spawn enemy 1 every 200 cnt
				if (cnt % 200 == 0) {
					spawn(enemy1Image, 1);
				}
				// Spawn enemy 2 every 250 cnt
				if (cnt % 250 == 0) {
					spawn(enemy2Image, 2);
				}
				// If the score is bigger than 20000
				if (score >= 20000) {
					// Clear the enemyList
					enemyList.clear();
					// Spawn the boss peter
					spawn(peterImage, 3);
					// Set isBoss true so that enemies are not spawned anymore
					isBoss = true;
				}
			}
		}
	}

	/**
	 * This is method that loops through all the enemies in the list and castskill
	 * and moves the enemy. If the enemy move out of the screen the enemy is removed
	 * from the list
	 */
	private void enemyMoveProcess() {
		// Loop through all the enemy
		for (int i = 0; i < enemyList.size(); i++) {
			// Get enemy in each index
			Unit enemy = enemyList.get(i);
			// Castskill of the enemy
			enemy.castSkill(enemyAttackList, cnt);
			// Move the enemy
			enemy.move();
			// If enemy is at the goes out of the screen
			if (enemy.getX() < 0) {
				// Remove the enemy from the enemyList
				enemyList.remove(enemy);
				// Reduce i by 1 since 1 element is removed from the list
				i--;
			}
		}
	}

	/**
	 * This is a method that process enemy's attack. It loops through all the enemy
	 * attacks if the player gets hit by it. It decreases the player's hp and
	 * removes the attack from the list. However, when an Peter's certain attacks do
	 * not get removed even the player gets hit so it can do damage over a period
	 */
	private void enemyAttackProcess() {
		// Loop through all enemy's attack
		for (int i = 0; i < enemyAttackList.size(); i++) {
			// Get enemyAttack in the index
			Attack enemyAttack = enemyAttackList.get(i);
			// Fire the attack
			enemyAttack.fire();
			// Check if the enemy attack hits the player (Looking at the x coordinates,
			// there are 2 possible situations
			// that either the player or the attack is in front when they crash. For
			// the first case, check if the player's x-coordinate is greater than the
			// enemy's x coordinate and then make sure that player's x coordinate is smaller
			// than the sum of enemy's attack and the width of the attack so some part of
			// the aircraft overlaps with the attack. For the second case, check if the
			// enemy attack's x-coordinate is greater than player's x-coordinate but smaller
			// than the sum of player's x coordinate and width so they overlap. The same
			// rule applies to the y-coordinate as well (Either player or enemy attack is
			// above). In this case instead of the width
			// the height of player and enemyAttack will be used to check if the player and
			// the enemy attack overlaps)
			if (((enemyAttack.getX() < player.getX() && player.getX() < enemyAttack.getX() + enemyAttack.getWidth())
					|| (player.getX() < enemyAttack.getX() && enemyAttack.getX() < player.getX() + player.getWidth()))
					&& ((enemyAttack.getY() < player.getY()
							&& player.getY() < enemyAttack.getY() + enemyAttack.getHeight())
							|| (player.getY() < enemyAttack.getY()
									&& enemyAttack.getY() < player.getY() + player.getHeight()))) {
				// Reduce player's hp by subtracting enemyAttack's damage from the orignal hp
				player.setHp(player.getHp() - enemyAttack.getDamage());
				// If it is not Peter's 3rd attack (which has a damage of 5)
				if (!isBoss || enemyAttack.getDamage() != 5) {
					// Remove attack from the enemyAttackList
					enemyAttackList.remove(i);
					// Reduce i by 1 as an element is removed from the arrayList
					i--;
				}
				// Check if player died
				if (player.getHp() <= 0)
					// Set isOver true if he or she did
					isOver = true;
			}
		}

	}

	/**
	 * This is a method that checks if a player and a enemy crash. Enemy do not get
	 * damage by getting crashed but player does. This method loops through all the
	 * enemies and check if the player crashed with any of them and reduces player
	 * hp if they do and set isOver true if player dies from that
	 */
	private void crashCheck() {
		// Loop through enemyList
		for (int i = 0; i < enemyList.size(); i++) {
			// Get enemy from the ArrayList
			Unit enemy = enemyList.get(i);
			// Use the same formula that has been used to find if an enemyAttack and player
			// has crashed to check if the player and enemy have overlapped
			if (((enemy.getX() < player.getX() && player.getX() < enemy.getX() + enemy.getWidth())
					|| (player.getX() < enemy.getX() && enemy.getX() < player.getX() + player.getWidth()))
					&& ((enemy.getY() < player.getY() && player.getY() < enemy.getY() + enemy.getHeight())
							|| (player.getY() < enemy.getY() && enemy.getY() < player.getY() + player.getHeight()))) {
				// Reduce player's hp by 10 from the orginal hp
				player.setHp(player.getHp() - 10);
				// If player hp is lower than 0
				if (player.getHp() <= 0)
					// The game is done
					isOver = true;
			}

		}
	}

	/**
	 * This is a function that draws the graphics of the game, including player,
	 * player attacks, enemies, enemy attacks, health bar, and scoring board, and
	 * ending screen
	 * 
	 * @param g
	 */
	public void gameDraw(Graphics g) {
		// Call a method to draw graphics related to the player
		playerDraw(g);
		// Call a method to draw graphics related to the enemy
		enemyDraw(g);
		// Call a method to draw graphics related to the info
		infoDraw(g);
	}

	/**
	 * This is a method that is used to display ending screen, game over message,
	 * and the scoring board
	 * 
	 * @param g
	 */
	public void infoDraw(Graphics g) {
		// If player have completed the game
		if (isVictory) {
			// Create an image for the end Screen
			Image endScreen = new ImageIcon("src/images/EndingScreen.png").getImage();
			// Display the end screen image to the background
			g.drawImage(endScreen, 0, 0, null);
			// If player has failed to complete the game
		} else if (isOver) {
			// Create an image for the gameOver Screen
			Image gameOverScreen = new ImageIcon("src/images/overScreen.png").getImage();
			g.drawImage(gameOverScreen, 0, 0, null);
		} else {
			// Set a color to white
			g.setColor(Color.WHITE);
			// Set the font to Arial, bold, and size 40
			g.setFont(new Font("Arial", Font.BOLD, 40));
			// Draw the score into the top left corner of the screen
			g.drawString("SCORE : " + score, 40, 80);
			// If the game is over

		}
	}

	/**
	 * This is a method that is used to draw player, player's health bar, and
	 * attacks
	 * 
	 * @param g
	 */
	public void playerDraw(Graphics g) {
		// Draw player's ship, based on its image and x and y coordinates
		g.drawImage(player.getImage(), player.getX(), player.getY(), null);
		// Set the color to green
		g.setColor(Color.GREEN);
		// Draw the health bar of the player at the top of the player, which is green
		g.fillRect(player.getX() - 1, player.getY() - 40, player.getHp(), 20);
		// Loop through all player attacks
		for (int i = 0; i < playerAttackList.size(); i++) {
			// Get the playerAttack in the index
			Attack playerAttack = playerAttackList.get(i);
			// Draw the attack based on image and x and y coordinates
			g.drawImage(playerAttack.getImage(), playerAttack.getX(), playerAttack.getY(), null);
		}
	}

	/**
	 * This is a method that is used to draw enemy, enemy's health bar, and attacks
	 * 
	 * @param g
	 */
	public void enemyDraw(Graphics g) {
		// Loop through all the enemyList
		for (int i = 0; i < enemyList.size(); i++) {
			// Get the enemy from the ArrayList
			Unit enemy = enemyList.get(i);
			// Draw enemies, based on its image and x and y coordinates
			g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), null);
			// Set the color to rend
			g.setColor(Color.RED);
			// Draw the health bar of the enemy at the top of the enemy, which is red
			g.fillRect(enemy.getX() + 1, enemy.getY() - 40, enemy.getHp() * 4, 20);
		}
		// Loop through all enemy attacks
		for (int i = 0; i < enemyAttackList.size(); i++) {
			// Get the enemyAttack in the index
			Attack enemyAttack = enemyAttackList.get(i);
			// Draw the attack based on image and x and y coordinates
			g.drawImage(enemyAttack.getImage(), enemyAttack.getX(), enemyAttack.getY(), null);
		}
	}

	// Setters for variables that will be used to move players and manage attacks
	// when the corresponding keys are pressed
	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
}