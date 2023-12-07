package SpaceShooting;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This is an abstract class called Unit. This class stores information about
 * the units spawned in the game, including x,y coordinates, width and height of
 * image, hp, speed, and cooldown. This function implements interface called
 * Flying, which makes the Units to move on the scree based on their speed.
 * There is an abstract function castSkill, where each character's uniue attack
 * is implemented in the child class.
 * 
 * @author taekyeong
 *
 */

public abstract class Unit implements Flying {
	// Create variables to store x, y coordindate, width of image and height, hp,
	// speed, and time when unit can use its skill agian.
	private int x, y, width, height, hp, speed, onCool = -10000;
	// Create a variable to store unit's image
	private Image image;

	public Unit(int x, int y, int hp, int speed, Image image) {
		super();
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.speed = speed;
		this.image = image;
	}

	/**
	 * This is a method that processes each different Unit's skills. The method
	 * first checks if the Unit's skill is still on cooldown or not. If not, the
	 * unit uses its skill and add its skill inside the AttackArrayList and set up a
	 * new time when it can uses its skill again.
	 * 
	 * @param enemyAttackList
	 * @param currentTime
	 */
	public abstract void castSkill(ArrayList<Attack> AttackList, int currentTime);

	/*
	 * This is method to move the x-coordinates of the Unit on the screen based on
	 * its speed
	 */

	public void move() {
		this.x -= speed;
	}

	/*
	 * This is method to check if the Unit's skill is still on cool down or not.
	 */
	public boolean onCooldown(int currTime) {
		return onCool <= currTime;
	}

	// Create getters and setters

	public int getOnCool() {
		return onCool;
	}

	public Image getImage() {
		return image;
	}

	public void setOnCool(int time) {
		this.onCool = time;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getSpeed() {
		return speed;
	}

}
