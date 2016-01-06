package com.didey.dun.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.didey.dun.engine.Animation;
import com.didey.dun.engine.GameObject;
import com.didey.dun.engine.ObjectId;
import com.didey.dun.engine.Texture;
import com.didey.dun.gui.Game;
import com.didey.dun.gui.Handler;

public class Player extends GameObject {

	private float width = 48, height = 96;

	public static float gravity = 0.5f;
	public static float MAX_SPEED = 10;
	public static boolean facingRight = true;
	public static float MOVE_SPEED = 5;

	public static boolean showInstructions = false;

	private Font pausedFont = new Font("Impact", Font.PLAIN, 34);
	public static int getPX;
	public static int getPY;

	private Handler handler;

	Texture tex = Game.getInstance();

	private Animation playerWalk;

	public Player(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;

		playerWalk = new Animation(10, tex.player[1], tex.player[2]);
	}

	private void Collision(LinkedList<GameObject> object) {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ObjectId.Sign) {
				if (getBounds().intersects(tempObject.getBounds())) {
					// sign collision
				} else {
					// when not colliding w/sign

				}
			}

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
				} else
					falling = true;

				if (getBoundsRight().intersects(tempObject.getBounds())) {
					x = tempObject.getX() - width;
				}

				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					x = tempObject.getX() + 35;
				}
			}
		}
	}

	public void tick(LinkedList<GameObject> object) {
		if (!Game.isPaused) {

			x += velX;
			y += velY;

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

		if (Game.isPaused) {
			g.setColor(Color.MAGENTA);
			g.drawString("Game is paused!", getPX - 97, getPY - 10);
		}

		g.setColor(Color.BLUE);
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

			g.drawString("PLAYER X: " + Integer.toString(getPX), getPX - 300, getPY - 200);
			g.drawString("PLAYER Y: " + Integer.toString(getPY), getPX - 300, getPY - 150);
			g.drawString("FPS: " + Integer.toString(Game.fps), getPX - 300, getPY - 100);
			g.drawString("TICKS: " + Integer.toString(Game.ticks), getPX - 300, getPY - 50);
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
