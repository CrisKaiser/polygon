package com.polygon;

import java.awt.Canvas;
import java.awt.Dimension;
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
	
	public static final float LOGIC_UPDATE_RATE = 60.0f;
	
	private Thread thread;
	private boolean running = false;
	private Render render;
	private Game game;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	
	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		this.screen = new Screen(WIDTH, HEIGHT);
		this.img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.pixels = ( (DataBufferInt) img.getRaster().getDataBuffer()).getData();
		this.game = new Game();
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
	    
	    long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / LOGIC_UPDATE_RATE; 
		double delta = 0.0;
		int frames = 0;
		int updates = 0;
		
		requestFocus();
		while(running) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1.0) {
				update();
				delta = 0.0;
				updates++;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}


	}

	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.render(game);
		
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
		frame.setResizable(false);
		frame.pack();
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		
		game.start();
	}
}
