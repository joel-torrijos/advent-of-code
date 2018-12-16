package com.joeltorrijos.year2018.day15;

public enum TileType {
 
	WALL ('#'), OPEN_CAVERN ('.');
	
	private final char display;
	
	TileType(char c) {
		this.display = c;
	}
	
	public static TileType charToTileType(char c) {
		switch (c) {
			case '#':
				return WALL;
			case '.':
			case 'G':
			case 'E':
				return OPEN_CAVERN;
			default:
				return null;
		}
	}
	
	String getDisplay() {
		return String.valueOf(this.display);
	}

}
