package snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Snake {

	private int maxXPos, maxYPos;
	private ArrayList<ComponentBody> snake = new ArrayList();
	private int xSpeed, ySpeed;

	public Snake(int maxX, int maxY) {
		maxXPos = maxX;
		maxYPos = maxY;
		resetSnake();
	}

	public void resetSnake() {
		xSpeed = 0;
		ySpeed = 0;
		snake.clear();
		for (int i = 0; i < 6; i++) {
			snake.add(new ComponentBody(maxXPos / 2, maxYPos / 2));
		}
	}

	public void tick() {

		for (int i = snake.size() - 1; i >= 1; i--) {
			snake.get(i).componentBodyXPosition = snake.get(i - 1).componentBodyXPosition;
			snake.get(i).componentBodyYPosition = snake.get(i - 1).componentBodyYPosition;
		}
		
		// movement of the snake
		snake.get(0).componentBodyXPosition = snake.get(0).componentBodyXPosition + xSpeed;
		snake.get(0).componentBodyYPosition = snake.get(0).componentBodyYPosition + ySpeed;
		

		// transport between borders
		if (snake.get(0).componentBodyXPosition >= maxXPos) {
			snake.get(0).componentBodyXPosition = 0;
		}
		if (snake.get(0).componentBodyXPosition < 0) {
			snake.get(0).componentBodyXPosition = maxXPos-ComponentBody.componentBodySize;
		}
		if (snake.get(0).componentBodyYPosition >= maxYPos) {
			snake.get(0).componentBodyYPosition = 0;
		}
		if (snake.get(0).componentBodyYPosition < 0) {
			snake.get(0).componentBodyYPosition = maxYPos-ComponentBody.componentBodySize;
		}

	}

	public void render(Graphics g) {
		if (g == null) {
			return;
		}
		g.setColor(Color.green);
		for (int i = 0; i < snake.size(); i++) {
			g.fillRect(snake.get(i).componentBodyXPosition, snake.get(i).componentBodyYPosition,
					ComponentBody.componentBodySize, ComponentBody.componentBodySize);
		}
	}

	public int getXSpeed() {
		return xSpeed;
	}

	public int getYSpeed() {
		return ySpeed;
	}

	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public ComponentBody getHead() {
		return snake.get(0);
	}

	public ArrayList<ComponentBody> getSnake() {
		return snake;
	}

	public void grow() {
		snake.add(new ComponentBody(snake.get(snake.size() - 1).componentBodyXPosition,
				snake.get(snake.size() - 1).componentBodyYPosition));
	}

}
