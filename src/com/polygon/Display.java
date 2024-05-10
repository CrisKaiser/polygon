package com.polygon;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Display extends Canvas{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Polygon";
	
	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.requestFocus();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle(TITLE);
		
	}
}
