package com.polygon.render;

import java.util.Random;

public class Screen extends Render{
	
	private Render snow;

	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		snow = new Render(256, 256);
		for (int i = 0; i < 256* 256; i++) {
			snow.pixels[i] = random.nextInt();
		}
	}
	
	
	public void render() {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}
		draw(snow, 0, 0);
	}
	

}
