package com.joeltorrijos.year2018.day15;

public class Elf extends Unit{

	public Elf(char display, int x, int y) {
		super(display, x, y);
	}
	
	@Override
	public String toString() {
		return "Elf (" + this.getX() + "," + this.getY() + ") [" + getHp() + "]";
	}
	
	

}
