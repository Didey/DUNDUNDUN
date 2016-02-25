package com.didey.dun.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.didey.dun.engine.GameClient;
import com.didey.dun.engine.KeyInput;
import com.didey.dun.engine.Logger;
import com.didey.dun.engine.ObjectId;
import com.didey.dun.engine.Texture;
import com.didey.dun.objects.Block;
import com.didey.dun.objects.Player;
import com.didey.dun.objects.QuickSand;
import com.didey.dun.objects.Sign;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 2191010178672139316L;

	public static boolean debugMode = false;
	public static boolean entityGravity = true;
	private boolean running = false;
	public static boolean isPaused = false;

	private Thread thread;

	public static int camWidth, camHeight;
	public static int WIDTH, HEIGHT;
	public static int fps;
	public static int ticks;

	private static String randTitle;

	public static BufferedImage level = null;

	Handler handler;
	Camera cam;
	static Texture tex;

	Random rand = new Random();

	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();

		tex = new Texture();

		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level.png");

		handler = new Handler();

		cam = new Camera(0, 0);

		LoadImageLevel(level);

		// handler.addObject(new Player(100, 100, handler, ObjectId.Player));

		// handler.createLevel();

		this.addKeyListener(new KeyInput(handler));
	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				Logger.info("FPS: " + frames + " TICKS: " + updates);
				fps = frames;
				ticks = updates;
				frames = 0;
				updates = 0;
			}
		}

	}

	private void tick() {
		handler.tick();
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ObjectId.Player) {
				cam.tick(handler.object.get(i));
			}
		}
	

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;

		//////////////////////////
		// Camera code

		camWidth = getWidth();
		camHeight = getHeight();

		g.setColor(Color.cyan);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Camera control
		g2d.translate(cam.getX(), cam.getY());

		handler.render(g);

		g2d.translate(-cam.getX(), -cam.getY());

		/////////////////////////////////////////////

		g.dispose();
		bs.show();
	}

	private void LoadImageLevel(BufferedImage image) {
		int levelWidth = image.getWidth();
		int levelHeight = image.getHeight();

		Logger.info("width, height " + levelWidth + " " + levelHeight);

		for (int xx = 0; xx < levelHeight; xx++) {
			for (int yy = 0; yy < levelWidth; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				if (red == 255 && green == 255 & blue == 255)
					handler.addObject(new Block(xx * 32, yy * 32, 1, ObjectId.Block)); // grass
				
				if (red == 128 && green == 128 & blue == 128)
					handler.addObject(new Block(xx * 32, yy * 32, 0, ObjectId.Block)); // dirt
				
				if (red == 0 && green == 0 & blue == 255)
					handler.addObject(new Player(xx * 32, yy * 32, handler, ObjectId.Player));
				
				if (red == 255 && green == 0 & blue == 0)
					handler.addObject(new Sign(xx * 32, yy * 32, "I love me some pure dank memes xdxdxd", ObjectId.Sign));
				
				if (red == 255 && green == 255 & blue == 0)
					handler.addObject(new Sign(xx * 32, yy * 32, "", ObjectId.Sign));
				
				if (red == 255 && green == 0 & blue == 255)
					handler.addObject(new QuickSand(xx * 32, yy * 32, ObjectId.QuickSand));
			}
		}

	}

	public static Texture getInstance() {
		return tex;
	}

	public static void main(String args[]) {
		new Window(800, 600, "DUNDUNDUN: Version 0.1!", new Game());
		Console.displayConsole();
	}

}
