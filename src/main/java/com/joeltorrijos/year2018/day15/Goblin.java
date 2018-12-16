package com.joeltorrijos.year2018.day15;

public class Goblin extends Unit {

	public Goblin(char display, int x, int y) {
		super(display, x, y);
	}
	
	

	@Override
	public String toString() {
		return "Goblin (" + this.getX() + "," + this.getY() + ") [" + getHp() + "]";
	}
	
	

}
