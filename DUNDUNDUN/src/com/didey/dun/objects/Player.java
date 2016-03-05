package com.didey.dun.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.didey.dun.engine.Animation;
import com.didey.dun.engine.GameObject;
import com.didey.dun.engine.KeyInput;
import com.didey.dun.engine.ObjectId;
import com.didey.dun.engine.Texture;
import com.didey.dun.gui.BufferedImageLoader;
import com.didey.dun.gui.Camera;
import com.didey.dun.gui.Game;
import com.didey.dun.gui.Handler;

public class Player extends GameObject {

	private float width = 48, height = 96;

	public static float gravity = 0.5f;
	public static float MAX_SPEED = 10;
	public static float MOVE_SPEED = 8;
	public static boolean facingRight = true;

	public static boolean showInstructions = false;
	public static boolean canGlide = false;

	public static Font pausedFont = new Font("consolas", Font.PLAIN, 34);

	public static int getPX;
	public static int getPY;
	private static int PLAYER_ANIM_SPEED = 7;
	public static float hp = 500.0f;

	public static boolean isSlowed = false;

	public static float setBulletSpeed = 0.2f;

	private Handler handler;
	private Camera cam;

	Texture tex = Game.getInstance();

	public static float startingX, startingY;

	private Animation playerWalk;

	public Player(float x, float y, Handler handler, Camera cam, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.cam = cam;
		startingX = (int) x;
		startingY = (int) y;

		playerWalk = new Animation(PLAYER_ANIM_SPEED, tex.player[1], tex.player[2]);
	}

	private void Collision(LinkedList<GameObject> object) {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ObjectId.Block) {
				if (getBoundsTop().intersects(tempObject.getBounds())) {
					y = tempObject.getY() + 32;
					velY = 0;
				}
				if (getBounds().intersects(tempObject.getBounds())) {
					y = tempObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
					KeyInput.jumps = 0;
					canGlide = false;
				} else
					falling = true;

				if (getBoundsRight().intersects(tempObject.getBounds())) {
					x = tempObject.getX() - width;
					KeyInput.jumps = 0;
					canGlide = true;
				}
				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					x = tempObject.getX() + 35;
					KeyInput.jumps = 0;
					canGlide = true;
				}
			} else if (tempObject.getId() == ObjectId.Flag) {
				// switch levels
				if (getBounds().intersects(tempObject.getBounds())) {
					handler.switchLevel();
				}
			}

		}
	}

	public void tick(LinkedList<GameObject> object) {
		if (!Game.isPaused) {
			if (!canGlide) {
				x += velX;
				y += velY;
			} else {
				if (KeyInput.jumps <= 2) {
					x += velX;
					y += velY;
				} else {
					x += velX;
					y += velY / 5;
				}
			}

			getPX = (int) x;
			getPY = (int) y;
			// gravity turns off is the game isnt paused
			if (Game.entityGravity && !Game.isPaused) {
				if (falling || jumping) {
					velY += gravity;

					if (velY > MAX_SPEED) {
						velY = MAX_SPEED;
					}

				}
			}
		}
		Collision(object);
		playerWalk.runAnimation();
	}

	public void render(Graphics g) {
		g.setFont(pausedFont);

		if (Game.LEVEL == 1) {
			g.drawImage(Game.logo, (int) startingX - 100, (int) startingY - 200, 484, 197, null);
		}

		if (Game.isPaused) {
			g.setColor(Color.MAGENTA);
			// g.drawString("Game is paused!", getPX - 97, getPY - 10);
		}

		if (velX > 0 && !jumping) {
			playerWalk.drawAnimation(g, (int) x, (int) y, (int) width, (int) height);
		} else if (velX > 0 && jumping) {
			if (facingRight)
				g.drawImage(tex.player[3], (int) x, (int) y, (int) width, (int) height, null);
			else
				g.drawImage(tex.player[3], (int) x + (int) width, (int) y, (int) -width, (int) height, null);
		} else if (velX == 0 && !jumping) {
			if (facingRight)
				g.drawImage(tex.player[0], (int) x, (int) y, (int) width, (int) height, null);
			else
				g.drawImage(tex.player[0], (int) x + (int) width, (int) y, (int) -width, (int) height, null);
		} else if (velX == 0 && jumping) {
			if (facingRight)
				g.drawImage(tex.player[3], (int) x, (int) y, (int) width, (int) height, null);
			else
				g.drawImage(tex.player[3], (int) x + (int) width, (int) y, (int) -width, (int) height, null);
		} else if (velX < 0 && !jumping) {
			playerWalk.drawAnimation(g, (int) x + (int) width, (int) y, (int) -width, (int) height);
		} else if (velX < 0 && jumping) {
			if (facingRight)
				g.drawImage(tex.player[3], (int) x, (int) y, (int) width, (int) height, null);
			else
				g.drawImage(tex.player[3], (int) x + (int) width, (int) y, (int) -width, (int) height, null);
		}

		Graphics2D g2d = (Graphics2D) g;

		// debug mode code
		if (Game.debugMode) {
			g.setColor(Color.RED);
			g2d.draw(getBounds());
			g2d.draw(getBoundsRight());
			g2d.draw(getBoundsLeft());
			g2d.draw(getBoundsTop());

			g.setColor(Color.BLUE);
			g.drawString("Player X: " + Integer.toString(getPX), getPX - 375, getPY - 200);
			g.drawString("Player Y: " + Integer.toString(getPY), getPX - 375, getPY - 150);
			g.drawString("FPS: " + Integer.toString(Game.fps), getPX - 375, getPY - 100);
			g.drawString("Ticks: " + Integer.toString(Game.ticks), getPX - 375, getPY - 50);
			g.drawString("Jumps: " + Integer.toString(KeyInput.jumps), getPX - 375, getPY);
			g.drawString("Move Speed: " + Float.toString(MOVE_SPEED), getPX + 100, getPY - 200);
		}

	}

	public Rectangle getBounds() {

		return new Rectangle((int) ((int) x + (width / 2) - (width / 2) / 2), (int) ((int) y + (height / 2)),
				(int) width / 2, ((int) height / 2) + 1);
	}

	public Rectangle getBoundsTop() {

		return new Rectangle((int) ((int) x + (width / 2) - (width / 2) / 2), (int) y, (int) width / 2,
				(int) height / 2);
	}

	public Rectangle getBoundsRight() {

		return new Rectangle((int) ((int) x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
	}

	public Rectangle getBoundsLeft() {

		return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
	}
}
