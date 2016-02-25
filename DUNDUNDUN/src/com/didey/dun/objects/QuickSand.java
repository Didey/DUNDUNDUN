package com.didey.dun.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.didey.dun.engine.GameObject;
import com.didey.dun.engine.ObjectId;
import com.didey.dun.engine.Texture;
import com.didey.dun.gui.Game;

public class QuickSand extends GameObject{

	private int width = 32;
	private int height = 32;
	Texture tex = new Texture();
	
	public QuickSand(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(tex.block[2], (int)x, (int)y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
