package SpaceShooting;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * This is class called Attack. It manages all types of attack in the game. The
 * attack class consists of x, y coordinates, damage, width and height of image,
 * cooldown, bullet's x, y speed, and image. It implements interface Bullet so
 * it has a method fire. When fire is called, the bullet changes its x y
 * coordinates based on its x and y speed
 * 
 * @author taekyeong
 *
 */
public class Attack implements Bullet {
	// Create variables to store x, y coordiantes of attack, height, width of
	// attack, bullet's x and y speed, cooldown, damage.
	private int x, y, width, height, bullet_speed_x, bullet_speed_y, cooldown, damage;
	// Create a variable to store image of the attack
	private Image image;

	// Constructor
	public Attack(int x, int y, int damage, int bullet_speed_x, int bullet_speed_y, int cooldown, Image image) {
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.bullet_speed_x = bullet_speed_x;
		this.bullet_speed_y = bullet_speed_y;
		this.cooldown = cooldown;
		this.image = image;
		// Set width based on the width of the image
		this.width = image.getWidth(null);
		// Set height based on the height of the image
		this.height = image.getHeight(null);
	}

	/*
	 * This is method from the interface bullet. This method manages the movement of
	 * the attack. When it is called, it changes the attack's x and y coordiantes
	 * based on its x and y speed
	 */
	public void fire() {
		this.x -= bullet_speed_x;
		this.y -= bullet_speed_y;
	}

	// Getters and setters

	public int getCooldown() {
		return cooldown;
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

	public Image getImage() {
		return image;
	}

	public int getDamage() {
		return damage;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
