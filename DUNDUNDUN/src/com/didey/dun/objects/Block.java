package com.didey.dun.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.didey.dun.engine.GameObject;
import com.didey.dun.engine.ObjectId;
import com.didey.dun.engine.Texture;
import com.didey.dun.gui.Game;

public class Block extends GameObject{

	Texture tex = Game.getInstance();
	private int type;
	
	
	public Block(float x, float y, int type, ObjectId id) {
		super(x, y, id);
		this.type = type;
	}

	
	public void tick(LinkedList<GameObject> object) 
	{
		
	}

	
	public void render(Graphics g) 
	{
		if(type == 0){
			g.drawImage(tex.block[0], (int)x, (int)y, null);
		} else {
			g.drawImage(tex.block[1], (int)x, (int)y, null);
		}
		
		Graphics2D g2d = (Graphics2D) g;
		if(Game.debugMode){
			g.setColor(Color.RED);
			g2d.draw(getBounds());
		}
	} 


	@Override
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 32, 32);
	}
}
