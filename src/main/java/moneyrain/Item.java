package moneyrain;

import javafx.scene.image.Image;

/**
 * Luokkaa hyödynnetään MoneyRain ja SlalomMadness arcadepeleissä itemien luomiseen.
 * @author Samuel Laisaar
 * @version 12.12.21
 */
public class Item {
	private Image img;
	private String name;
	private int width;
	private int height;
	private int xPos;
	private int yPos;
	private boolean isDangerous;
	private boolean givesHp;
	private boolean isCollected = false;
	
	public Item(Image img, int width, int height, int xPos, int yPos, boolean isDangerous, boolean givesHp) {
		this.img = img;
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.isDangerous = isDangerous;
		this.givesHp = givesHp;
	}
	
	public Item(String name, Image img, int width, int height, int xPos, int yPos, boolean isDangerous, boolean givesHp) {
		this.name = name;
		this.img = img;
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.isDangerous = isDangerous;
		this.givesHp = givesHp;
	}

	public Image getImg() {
		return img;
	}
	
	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public void fall() {
		yPos+=1;
	}
	
	public void ascend(int speed) {
		yPos = yPos - speed;
	}
	
	public boolean isDangerous() {
		return isDangerous;
	}
	
	public boolean isGivesHp() {
		return givesHp;
	}
	
	public void isCollected() {
		isCollected = true;
	}
	
	public boolean checkCollected() {
		return isCollected;
	}
	
}