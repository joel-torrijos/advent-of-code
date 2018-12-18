package com.joeltorrijos.year2018.day15;

import java.util.function.Predicate;

public class Elf extends Unit{

	public Elf(char display, int x, int y) {
		super(display, x, y);
	}
	
	@Override
	public boolean isEnemy(Unit unit) {
		return unit instanceof Goblin;
	}
	
	
	@Override
	public Predicate<Tile> hasAdjacentEnemy() {
		return (tile) -> tile.isOccupied() && tile.getUnit() instanceof Goblin;
	}
	
	@Override
	public String toString() {
		return "Elf (" + this.getX() + "," + this.getY() + ") [" + getHp() + "]";
	}
	

}
