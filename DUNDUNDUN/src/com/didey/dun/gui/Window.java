package com.didey.dun.gui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window {	
	
	private BufferedImageLoader loader;
	
	public Window(int w, int h, String title, Game game)
	{
		loader = new BufferedImageLoader();
		game.setPreferredSize(new Dimension(w, h));
		game.setMaximumSize(new Dimension(w, h));
		game.setMinimumSize(new Dimension(w, h));
		
		JFrame frame = new JFrame(title);
		frame.setIconImage(loader.loadImage("/icon.png"));
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	
		
		game.start();
	}
}
