package snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Apple {
	
	private int maxXPos, maxYPos;
	private ComponentBody apple;
	
	public Apple(int maxX, int maxY) {
		maxXPos = maxX;
		maxYPos = maxY;
		resetApple();
	}
	
	public void resetApple() {
		Random rand = new Random();
		apple = new ComponentBody(rand.nextInt(maxXPos-ComponentBody.componentBodySize),
									rand.nextInt(maxYPos-ComponentBody.componentBodySize));
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(g==null) {
			return;
		}
		g.setColor(Color.red);
		g.fillRect(apple.componentBodyXPosition, apple.componentBodyYPosition,
				ComponentBody.componentBodySize, ComponentBody.componentBodySize);
	}
	
	public int getAppleXPos() {
		return apple.componentBodyXPosition;
	}
	
	public int getAppleYPos() {
		return apple.componentBodyYPosition;
	}
}
