package com.joeltorrijos.year2018.day15;

public class Tile {
	private TileType tileType;
	private Unit unit;
	
	public Tile(TileType tileType, Unit unit) {
		this.tileType = tileType;
		this.unit = unit;
	}
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public TileType getTileType() {
		return this.tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}
	
	public boolean isOccupied() {
		return unit != null;
	}

	public String getDisplay() {
		if(this.isOccupied()) {
			return unit.getDisplay();
		}
		return tileType.getDisplay();
	}
	
}
