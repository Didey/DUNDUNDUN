package com.didey.dun.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import com.didey.dun.engine.GameObject;
import com.didey.dun.engine.ObjectId;
import com.didey.dun.gui.Game;

public class Sign extends GameObject {

	public static int width, height;
	public static int signX;
	public static int signY;
	
	private String write;
	
	private int offset;
	
	public static boolean showInstructions;
	
	private BufferedImage image;
	
	public Sign(float x, float y, String text, ObjectId id) {
		super(x, y, id);
		try {
			image = ImageIO.read(getClass().getResource("/sign.png"));
			width = image.getWidth();
			height = image.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		write = text;
	}

	public void tick(LinkedList<GameObject> object) {
		
	}
	
	public void render(Graphics g) {
		int drawX = write.length();
		g.drawImage(image, (int)x, (int)y, null);
		g.setColor(Color.BLACK);
		g.drawString(write, (int)x - offset *2, (int)y - 50);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

}
