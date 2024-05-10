package com.polygon;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.polygon.render.Render;
import com.polygon.render.Screen;

public class Display extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Polygon";
	
	private Thread thread;
	private boolean running = false;
	private Render render;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	
	public Display() {
		this.screen = new Screen(WIDTH, HEIGHT);
		this.img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.pixels = ( (DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	private void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	private void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	

	@Override
	public void run() {
		System.out.println("render");
		while(running) {
			update();
			render();
		}
		
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.render();
		
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();
	}
	private void update() {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.requestFocus();
		
		game.start();
	}
}
