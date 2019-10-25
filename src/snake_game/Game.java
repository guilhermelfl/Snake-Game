package snake_game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	private Thread thread;

	public static final int width = 40;
	public static final int height = 30;
	public static final int scale = 10;
	public BufferedImage layer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	private boolean isRunning = true;
	private boolean alreadyPressedInThisTick = false;

	public Snake snake;
	public Apple apple;

	public static void main(String[] args) {
		System.out.println("Iniciando Snake");
		Game game = new Game();
		initFrame(game);
		game.start();
	}

	public Game() {
		this.setPreferredSize(new Dimension(width * scale, height * scale));
		this.addKeyListener(this);
		apple = new Apple(width, height);
		snake = new Snake(width, height);
	}

	private static void initFrame(Game game) {
		JFrame frame = new JFrame("Snake Game");
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long lastTime = System.nanoTime();
		double amountOfTicks = 10.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.0;
		int frames = 0;
		double timer = System.currentTimeMillis();

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				//System.out.println("FPS: " + frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
	}

	private void tick() {
		apple.tick();
		snake.tick();
		testSnakeEatApple();
		testSnakeSelfColision();
		alreadyPressedInThisTick = false;
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = layer.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);

		apple.render(g);
		snake.render(g);

		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, width * scale, height * scale, null);
		g.dispose();
		bs.show();
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		movingGamePress(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void testSnakeEatApple() {
		Rectangle snakeHeadBounds = new Rectangle(snake.getHead().componentBodyXPosition,
				snake.getHead().componentBodyYPosition, ComponentBody.componentBodySize,
				ComponentBody.componentBodySize);

		Rectangle appleBounds = new Rectangle(apple.getAppleXPos(), apple.getAppleYPos(),
				ComponentBody.componentBodySize, ComponentBody.componentBodySize);

		if (snakeHeadBounds.intersects(appleBounds)) {
			snake.grow();
			apple.resetApple();
			
			
		}
	}

	private void testSnakeSelfColision() {
		if (snake.getXSpeed() == 0 && snake.getYSpeed() == 0)
			return;

		Rectangle snakeHeadBounds = new Rectangle(snake.getHead().componentBodyXPosition,
				snake.getHead().componentBodyYPosition, ComponentBody.componentBodySize,
				ComponentBody.componentBodySize);

		for (int i = snake.getSnake().size() - 1; i > 1; i--) {
			Rectangle snakeBodyBounds = new Rectangle(snake.getSnake().get(i).componentBodyXPosition,
					snake.getSnake().get(i).componentBodyYPosition, ComponentBody.componentBodySize,
					ComponentBody.componentBodySize);

			if (snakeHeadBounds.intersects(snakeBodyBounds)) {
				snake.resetSnake();
				apple.resetApple();
				return;
			}

		}
	}

	private void movingGamePress(KeyEvent e) {
		if (!alreadyPressedInThisTick) {
			if (snake.getXSpeed() == 0) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					snake.setXSpeed(1);
					snake.setYSpeed(0);
					alreadyPressedInThisTick = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					snake.setXSpeed(-1);
					snake.setYSpeed(0);
					alreadyPressedInThisTick = true;
				}
			}
			if (snake.getYSpeed() == 0) {

				if (e.getKeyCode() == KeyEvent.VK_UP) {
					snake.setXSpeed(0);
					snake.setYSpeed(-1);
					alreadyPressedInThisTick = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					snake.setXSpeed(0);
					snake.setYSpeed(1);
					alreadyPressedInThisTick = true;
				}
			}
		}
	}
}
