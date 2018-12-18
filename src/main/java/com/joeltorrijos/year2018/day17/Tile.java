package com.joeltorrijos.year2018.day17;

import java.awt.Point;

public class Tile extends Point {
	private char display;

	public Tile(int x, int y, char display) {
		super(x, y);
		this.display = display;
	}
	
	public String getDisplay() {
		return String.valueOf(display);
	}

	@Override
	public String toString() {
		return "Tile [Point=(" + x + "," + y + "), display=" + display + "]";
	}
	
	
	
	
}
