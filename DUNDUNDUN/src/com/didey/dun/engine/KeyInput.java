package com.didey.dun.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.didey.dun.gui.BufferedImageLoader;
import com.didey.dun.gui.Console;
import com.didey.dun.gui.Game;
import com.didey.dun.gui.Handler;
import com.didey.dun.objects.Bullet;
import com.didey.dun.objects.Player;

public class KeyInput extends KeyAdapter implements KeyListener {

	BufferedImageLoader loader = new BufferedImageLoader();

	public static int jumps;


	
	Handler handler;
	Game game = new Game();

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public static void quackJump() throws Exception {
		AudioInputStream jumpStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Patrick\\git\\DUNDUNDUN\\DUNDUNDUN\\audio\\quack2.wav"));
		Clip clip = AudioSystem.getClip();
		clip.open(jumpStream);
		clip.loop(0);
		try {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-10.0f);			
		} catch(IllegalArgumentException e){
			
		}
	}
	
	public static void glideAudio() throws Exception {
		AudioInputStream jumpStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Patrick\\git\\DUNDUNDUN\\DUNDUNDUN\\audio\\glide.wav"));
		Clip clip = AudioSystem.getClip();
		clip.open(jumpStream);
		clip.loop(0);
		try {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(+5.0f);			
		} catch(IllegalArgumentException e){
			
		}
	}

	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_E)
			Game.isPaused ^= true;

		if (key == KeyEvent.VK_ESCAPE) 
			System.exit(1);
		
		if (key == KeyEvent.VK_F5 && !Game.isPaused) {
			Game.debugMode ^= true;
			if (Game.debugMode) {
				Logger.info("Debug mode has been ENABLED!");
			} else {
				Logger.info("Debug mode has been DISABLED!");
			}
		}

		if (key == KeyEvent.VK_C) {
			if (!Game.isPaused) {
				if (Player.facingRight)
					handler.addObject(new Bullet(Player.getPX + 50, Player.getPY + 30, 0, ObjectId.Bullet));
				else
					handler.addObject(new Bullet(Player.getPX - 50, Player.getPY + 30, 1, ObjectId.Bullet));
			}
		}

		if (key == KeyEvent.VK_SLASH) {
			Console.createConsole();
		}

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ObjectId.Player && !Game.isPaused) {
				if (key == KeyEvent.VK_D) {
					tempObject.setVelX(Player.MOVE_SPEED);
					Player.facingRight = true;
				}
				if (key == KeyEvent.VK_A) {
					tempObject.setVelX(-Player.MOVE_SPEED);
					Player.facingRight = false;
				}
				if (key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE) {
					if (jumps < 2) {
						tempObject.setJumping(true);
						tempObject.setVelY(-13);
						
						try {
							quackJump();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
						jumps++;
					} else {
						if (jumps < 2) {
							jumps++;
							Player.canGlide = false;
						} else {
							try {
								glideAudio();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							jumps++;
							Player.canGlide = true;
						}
					}
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ObjectId.Player) {
				if (key == KeyEvent.VK_D && tempObject.getVelX() > 0)
					tempObject.setVelX(0);
				if (key == KeyEvent.VK_A && tempObject.getVelX() < 0)
					tempObject.setVelX(0);
			}
		}
	}
}
