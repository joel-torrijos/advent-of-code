package com.joeltorrijos.year2018.day13;

public enum TrackType {
 
	STRAIGHT_VERTICAL ('|'), STRAIGHT_HORIZONAL('-'), LEFT_CURVE('/'), 
	RIGHT_CURVE('\\'), INTERSECTION('+'), NONE(' ');
	
	private final char display;
	
	TrackType(char c) {
		this.display = c;
	}
	
	public static TrackType charToTrackType(char c) {
		switch (c) {
			case '|':
			case '^':
			case 'v':
				return STRAIGHT_VERTICAL;
			case '-':
			case '>':
			case '<':
				return STRAIGHT_HORIZONAL;
			case '/':
				return LEFT_CURVE;
			case '\\':
				return RIGHT_CURVE;
			case '+':
				return INTERSECTION;
			case ' ':
				return NONE;
			default:
				return null;
		}
	}
	
	String getDisplay() {
		return String.valueOf(this.display);
	}

}
