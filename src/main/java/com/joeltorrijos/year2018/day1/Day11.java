package com.joeltorrijos.year2018.day1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day11 {
	
	public static void main(String[] args) {

		System.out.println(partOne(3613));
		System.out.println(partTwoSumTable(3613));
		
	}
	
	public static String partOne(int serialNumber) {
	
		int gridSize = 300;
		Point[][] grid = new Point[gridSize][gridSize];
		
		for(int y1 = 0 ; y1 < grid.length; y1++) {
			for(int x1 = 0; x1 <  grid[y1].length; x1++) {
				grid[y1][x1]  = new Point(x1,y1, serialNumber);
			}
		}
		
		Map<String, Integer> areaPowerLevels = new HashMap<>();
		
		for(int y1 = 0 ; y1 < grid.length; y1++) {
			for(int x1 = 0 ; x1 < grid[y1].length; x1++) {
				if (y1 + 2 < grid.length && x1 + 2 < grid[y1].length)
				{
					int areaPowerLevel = 0;
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							areaPowerLevel += grid[y1+i][x1+j].powerLevel;
						}
					}
					String topLeft = x1 + "," + y1;
					areaPowerLevels.put(topLeft, areaPowerLevel);
				}
			}
		}
		
		return areaPowerLevels.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
	}
	
	public static String partTwo(int serialNumber) {
		
		int gridSize = 300;
		Point[][] grid = new Point[gridSize][gridSize];
		

		for(int y1 = 0 ; y1 < grid.length; y1++) {
			for(int x1 = 0; x1 <  grid[y1].length; x1++) {
				grid[y1][x1]  = new Point(x1,y1, serialNumber);
			}
		}
		
		Map<String, Integer> areaPowerLevels = new HashMap<>();
		
		for(int y1 = 0 ; y1 < grid.length; y1++) {
			for(int x1 = 0 ; x1 < grid[y1].length; x1++) {
				int[] minimums = {grid.length - y1, grid[y1].length - x1};
				int min = Arrays.stream(minimums).min().getAsInt();

				for(int size = 1; size <= min; size++) {
					int areaPowerLevel = 0;
					String identifier = x1 + "," + y1 + "," + size;
					String previous = x1 + "," + y1 + "," + (size - 1);
					if(!areaPowerLevels.containsKey(previous) && size == 1) {
						areaPowerLevel += grid[y1][x1].powerLevel;
					} else {
						areaPowerLevel += areaPowerLevels.get(previous);
						
						int currentX = x1;
						int currentY = y1;
						
						int newX = currentX + size - 1;
						int newY = currentY + size - 1;
						
						// Vertical; Fixed X
						for(int i = currentY; i < newY; i++) {
							areaPowerLevel += grid[i][newX].powerLevel;
						}
						
						for(int i = currentX; i < newX; i++) {
							areaPowerLevel += grid[newY][i].powerLevel; // fixed y
						}
						
						areaPowerLevel += grid[newY][newX].powerLevel;
						
					}
					areaPowerLevels.put(identifier, areaPowerLevel);						
				}
			}
		}
		
		
		Map.Entry<String, Integer> maxPowerLevelEntry = areaPowerLevels.entrySet().stream().max(Map.Entry.comparingByValue()).get();

		return maxPowerLevelEntry.getKey();
	}

	// Summed-area table solution for Part Two
	public static String partTwoSumTable(int serialNumber) {
		
		int gridSize = 300;

		Point[][] grid = new Point[gridSize][gridSize];
		
		for(int y1 = 0 ; y1 < grid.length; y1++) {
			for(int x1 = 0; x1 <  grid[y1].length; x1++) {
				grid[y1][x1]  = new Point(x1,y1, serialNumber);
			}
		}
		
		Map<String, Integer> areaPowerLevels = new HashMap<>();
		
		int[][] summedAreaTable = new int[gridSize][gridSize];
		
		// setup
		for(int x = 0; x < summedAreaTable.length; x++) {
			summedAreaTable[0][x] = grid[0][x].powerLevel;
		}
		
		// Sum top to bottom
		for(int y = 1; y < summedAreaTable.length; y++) {
			for(int x = 0; x <  summedAreaTable[y].length; x++) {
				summedAreaTable[y][x] = grid[y][x].powerLevel + summedAreaTable[y-1][x];
			}
		}
		
		// Sum left to right
		for(int y = 0; y < summedAreaTable.length; y++) {
			for(int x = 1; x <  summedAreaTable[y].length; x++) {
				summedAreaTable[y][x] += summedAreaTable[y][x-1];
			}
		}
		
		for(int y = 0 ; y < grid.length; y++) {
			for(int x = 0 ; x < grid[y].length; x++) {
				int[] minimums = {grid.length - y, grid[y].length - x};
				int min = Arrays.stream(minimums).min().getAsInt();

				for(int size = 1; size <= min; size++) {
					int areaPowerLevel = 0;
					String identifier = x + "," + y  + "," + size;
					
					if(size == 1) {
						areaPowerLevel = grid[y][x].powerLevel;
					} else {
						int maxX = x + size - 1;
						int maxY = y + size - 1;
						
						int leftX = x - 1;
						int topY = maxY - size;
						
						int topLeft = (topY < 0 || leftX < 0) ? 0 : summedAreaTable[topY][leftX];
						int bottomLeft = (maxY < 0 || leftX < 0) ? 0 : summedAreaTable[maxY][leftX];
						int topRight = (maxY < 0 || topY < 0) ? 0 : summedAreaTable[topY][maxX];
						int bottomRight = (maxY < 0 || maxX < 0) ? 0 : summedAreaTable[maxY][maxX];
						
						areaPowerLevel = bottomRight - topRight - bottomLeft + topLeft;
					}
					
					areaPowerLevels.put(identifier, areaPowerLevel);						
				}
			}
		}
		
		
		Map.Entry<String, Integer> maxPowerLevelEntry = areaPowerLevels.entrySet().stream().max(Map.Entry.comparingByValue()).get();

		return maxPowerLevelEntry.getKey();
	}
	
	static class Point {
		int x, y, serialNum, powerLevel;

		public Point(int x, int y, int serialNum) {
			this.x = x;
			this.y = y;
			this.serialNum = serialNum;
			this.powerLevel = calculatePowerLevel();
		}
		
		public int calculatePowerLevel() {
			int rackId = this.x + 10;
			int powerLevel = ((rackId * this.y) + this.serialNum) * rackId;
			powerLevel = getNthDigit((((rackId * this.y) + this.serialNum) * rackId), 10, 3);
			return powerLevel - 5;
		}
		
		public int getNthDigit(int number, int base, int n) {    
			return (int) ((number / Math.pow(base, n - 1)) % base);
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}
}
