package com.didey.dun.engine;

import java.awt.image.BufferedImage;

import com.didey.dun.gui.BufferedImageLoader;

public class Texture {

	SpriteSheet bs, ps;
	private BufferedImage block_sheet = null;
	private BufferedImage player_sheet = null;

	public BufferedImage[] block = new BufferedImage[2];
	public BufferedImage[] player = new BufferedImage[4];

	public Texture() {

		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			block_sheet = loader.loadImage("/block_sheet.png");
			player_sheet = loader.loadImage("/player_sheet.png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		bs = new SpriteSheet(block_sheet);
		ps = new SpriteSheet(player_sheet);

		getTextures();
	}

	private void getTextures() {
		block[0] = bs.grabImage(3, 1, 32, 32); // dirt
		block[1] = bs.grabImage(4, 1, 32, 32); // grass

		player[0] = ps.grabImage(1, 1, 57, 64);
		player[1] = ps.grabImage(2, 1, 57, 64);
		player[2] = ps.grabImage(3, 1, 57, 64);
		player[3] = ps.grabImage(4, 1, 57, 64);
		

	}
}
