package com.joeltorrijos.year2018.day15;

import java.awt.Point;

public abstract class Unit {
	private Point coordinates;
	private char display;
	private int hp = 200;
	private int damage = 3;

	public Unit(char display, int x, int y) {
		this.display = display;
		this.coordinates = new Point(x,y);
	}
	
	public int getX() {
		return (int) coordinates.getX();
	}
	
	public int getY() {
		return (int) coordinates.getY();
	}
	
	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}


	public String getDisplay() {
		return String.valueOf(display);
	}

	public void setDisplay(char display) {
		this.display = display;
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void attack(Unit unit) {
		int enemyHp = unit.getHp();
		enemyHp -= this.getDamage();
		unit.setHp(enemyHp);
	}
	
	public void die(Tile tile) {
		tile.setUnit(null);
//		Point empty = null;
//		this.setCoordinates(null);
	}
	
	public boolean isDead() {
		return hp <= 0 && coordinates == null;
	}
	
	public boolean isAlive() {
		return hp > 0 && coordinates != null;
	}
}
