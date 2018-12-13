package com.joeltorrijos.year2018.day13;

public enum Direction {
	UP ('^'), LEFT ('<'), RIGHT('>'), DOWN('v');
	
	private Direction left, right, straight;
	private char display;

	static {
		UP.straight = UP;
		DOWN.straight = DOWN;
		LEFT.straight = LEFT;
		RIGHT.straight = RIGHT;
		
		UP.left = LEFT;
		DOWN.left = RIGHT;
		LEFT.left = DOWN;
		RIGHT.left = UP;
		
		UP.right = RIGHT;
		DOWN.right = LEFT;
		LEFT.right = UP;
		RIGHT.right = DOWN;
	}
	
	Direction(char c) {
		this.display = c;
	}
	
	
	public static Direction charToDirection(char c) {
		switch (c) {
			case '^':
				return UP;
			case 'v':
				return DOWN;
			case '<':
				return LEFT;
			case '>':
				return RIGHT;
			default:
				return null;
		}
	}
	
	public String display() {
		return String.valueOf(this.display);
	}
	
	
	// For Intersections
	public Direction straight () {
		return straight;
	}
	public Direction left () {
		return left;
	}
	public Direction right () {
		return right;
	}
	
	
}
