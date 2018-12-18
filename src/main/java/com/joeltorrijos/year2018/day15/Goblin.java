package com.joeltorrijos.year2018.day15;

import java.util.function.Predicate;

public class Goblin extends Unit {

	public Goblin(char display, int x, int y) {
		super(display, x, y);
	}
		
	@Override
	public boolean isEnemy(Unit unit) {
		return unit instanceof Elf;
	}
	

	@Override
	public String toString() {
		return "Goblin (" + this.getX() + "," + this.getY() + ") [" + getHp() + "]";
	}

	@Override
	public Predicate<Tile> hasAdjacentEnemy() {
		return (tile) -> tile.isOccupied() && tile.getUnit() instanceof Elf;
	}

}
