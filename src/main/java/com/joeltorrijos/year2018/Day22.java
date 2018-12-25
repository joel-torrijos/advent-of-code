package com.joeltorrijos.year2018;

public class Day22 {
	
	public static void main(String[] args) {
		
		System.out.println(partOne(11820, "7,782"));
//		System.out.println(partTwo(fileName, 1000000000));
		
	}
	
	public static int partOne(int depth, String target) {
		
		int targetX = Integer.parseInt(target.split(",")[0]);
		int targetY = Integer.parseInt(target.split(",")[1]);
		
		int[][] erosionLevels = new int[targetY+1][targetX+1];
		
		for(int y = 0; y < erosionLevels.length; y++) {
			for(int x = 0; x < erosionLevels[y].length; x++) {
				
				int geologicalIndex = 0;
				
				if (y == 0 && x == 0) {
					geologicalIndex = 0;
				} else if (y == targetY && x == targetX) {
					geologicalIndex = 0;
				} else if (y == 0) {
					geologicalIndex = x * 16807;
				} else if (x == 0) {
					geologicalIndex = y * 48271;
				} else {
					geologicalIndex = erosionLevels[y][x-1] * erosionLevels[y-1][x];
				}
				
				erosionLevels[y][x] = (geologicalIndex + depth) % 20183;
			}			
		}
		
		int totalRiskLevel = 0;
		
		for(int y = 0; y < erosionLevels.length; y++) {
			for(int x = 0; x < erosionLevels[y].length; x++) {
				totalRiskLevel += erosionLevels[y][x] % 3;	
			}
		}			
		
		return totalRiskLevel;
	}
	
}
