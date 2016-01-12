package com.didey.dun.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.didey.dun.gui.BufferedImageLoader;
import com.didey.dun.gui.Console;
import com.didey.dun.gui.Game;
import com.didey.dun.gui.Handler;
import com.didey.dun.objects.Player;

public class KeyInput extends KeyAdapter implements KeyListener {

	BufferedImageLoader loader = new BufferedImageLoader();

	public static int jumps;
	
	Handler handler;
	Game game = new Game();
	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_E)
			Game.isPaused ^= true;

		if(key == KeyEvent.VK_F5 && !Game.isPaused){
			Game.debugMode ^= true;		
			if(Game.debugMode){
				Logger.info("Debug mode has been ENABLED!");
			} else {
				Logger.info("Debug mode has been DISABLED!");
			}
		}
			
		
		if(key == KeyEvent.VK_SLASH){
			Console.createConsole();
		}
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ObjectId.Player && !Game.isPaused) {
				if (key == KeyEvent.VK_D){
					tempObject.setVelX(Player.MOVE_SPEED);
					Player.facingRight = true;
				}
				if (key == KeyEvent.VK_A ){	
					tempObject.setVelX(-Player.MOVE_SPEED);
					Player.facingRight = false;
				}
				if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W ) {
					if(jumps < 2){
						tempObject.setJumping(true);
						tempObject.setVelY(-15);
						jumps++;
					}
				}
			}

			if (key == KeyEvent.VK_ESCAPE) {
				System.exit(1);
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
