package com.joeltorrijos.year2018.day13;

import java.util.ArrayDeque;
import java.util.Deque;

public class Cart {
	private int x, y;
	private Direction currentDirection;
	private Deque<IntersectionDirection> intersectionDirs;
	private boolean crashed;

	public Cart(int x, int y, Direction currentDirection) {
		this.x = x;
		this.y = y;
		this.currentDirection = currentDirection;
		this.crashed = false;
		
		this.intersectionDirs = new ArrayDeque<>();
		this.intersectionDirs.add(IntersectionDirection.LEFT);
		this.intersectionDirs.add(IntersectionDirection.STRAIGHT);
		this.intersectionDirs.add(IntersectionDirection.RIGHT);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Deque<IntersectionDirection> getIntersectionDirs() {
		return intersectionDirs;
	}

	public void setIntersectionDirs(Deque<IntersectionDirection> intersectionDirs) {
		this.intersectionDirs = intersectionDirs;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}
	
	public boolean isCrashed() {
		return crashed;
	}

	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
	}

	public boolean move(int[] newCoordinates, Tile origin, Tile destination) {
		
		origin.cart = null;
		
		// Check if destination is empty
		if(destination.cart == null) {
			x = newCoordinates[0];
			y = newCoordinates[1];
			destination.cart = this;
			this.changeDirection(destination);
		} else {
			setCrashed(true);
			destination.cart.setCrashed(true);
			destination.cart = null;			
			return false;
		}
		
		return true;
	}
	
	public int[] willMoveTo() {
		int newX = this.x;
		int newY = this.y;
		switch(currentDirection) {
			case UP:
					newY--;
				break;
			case RIGHT:
					newX++;
				break;
			case DOWN:
					newY++;
				break;
			case LEFT:
					newX--;
				break;
			default:
				break;
		}
		
		return new int[]{newX,newY};
		
	}
	
	public void changeDirection(Tile destination) {
		if (destination.type == TrackType.INTERSECTION) {
			IntersectionDirection currentIntersectionDir = intersectionDirs.removeFirst();
			
			if(currentIntersectionDir == IntersectionDirection.LEFT) {
				currentDirection = currentDirection.left();				
			} else if (currentIntersectionDir == IntersectionDirection.STRAIGHT) {
				currentDirection = currentDirection.straight();	
			} else if (currentIntersectionDir == IntersectionDirection.RIGHT) {
				currentDirection = currentDirection.right();	
			} 
			
			intersectionDirs.addLast(currentIntersectionDir);
			
		} else if (destination.type == TrackType.LEFT_CURVE) {
			if(currentDirection == Direction.RIGHT) {
				currentDirection = currentDirection.left();
			} else if(currentDirection == Direction.UP) {
				currentDirection = currentDirection.right();
			} else if(currentDirection == Direction.LEFT) {
				currentDirection = currentDirection.left();
			} else if(currentDirection == Direction.DOWN) {
				currentDirection = currentDirection.right();
			}
			
		} else if (destination.type == TrackType.RIGHT_CURVE) {
			if(currentDirection == Direction.RIGHT) {
				currentDirection = currentDirection.right();
			} else if(currentDirection == Direction.UP) {
				currentDirection = currentDirection.left();
			} else if(currentDirection == Direction.LEFT) {
				currentDirection = currentDirection.right();
			} else if(currentDirection == Direction.DOWN) {
				currentDirection = currentDirection.left();
			}
		}
	}
	
	public String display() {
		return this.currentDirection.display();
	}
}