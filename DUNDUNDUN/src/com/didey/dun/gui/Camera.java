package com.didey.dun.gui;

import com.didey.dun.engine.GameObject;

public class Camera {
	
	private static float x, y;
	
	public static int camX;
	public static int camY;
	
	public Camera(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject Player){		
		
		if(!Game.isPaused){
			x += ((-Player.getX() + Game.camWidth / 2) - x) * 0.0400;
			y += ((-Player.getY() + Game.camHeight / 2) - y) * 0.0700;
		}
		
		camX = (int) x;
		camY = (int) y;
	}
	
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	
}
