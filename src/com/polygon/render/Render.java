package com.polygon.render;

public class Render {
	public final int width;
	public final int height;
	public final int[] pixels;
	
	
	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[height * width];
	}
	
	public void draw(Render render, int xOffset, int yOffset) {
		for (int y = 0; y < render.height; y++) {
			int yP = y + yOffset;
			if (yP < 0 || yP >= render.height) continue;
			for (int x = 0; x < render.width; x++) {
				int xP = x + xOffset;
				if (xP < 0 || xP >= render.width) continue;
				pixels[xP + yP * width] = render.pixels[x+y*render.width];
			}
			
		}
	}
}
