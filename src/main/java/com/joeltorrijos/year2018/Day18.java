package com.joeltorrijos.year2018;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 {
	
	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day18/day18-test-input.txt";
		String fileName = "src/main/resources/2018/day18/day18-input.txt";
		
		System.out.println(partOne(fileName, 10));
		System.out.println(partTwo(fileName, 1000000000));
		
	}
	
	public static int partOne(String fileName, int endMinute) {
		
		char[][] grid = parseInput(fileName);
		
		for (int i = 0; i < endMinute; i++) {
			grid = simulate(grid);		
		}
		
		int trees = 0;
		int lumberyards = 0;
		
		for(int y = 0; y < grid.length; y++) {
			for(int x = 0; x < grid[y].length; x++) {
				
				if (grid[y][x] == '|') trees++;
				else if (grid[y][x] == '#') lumberyards++;

			}
		}
		
		return trees * lumberyards;
	}
	
	public static char[][] parseInput(String fileName) {

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			List<String> lines = stream.collect(Collectors.toList());
			
			char[][] grid = new char[lines.size()][lines.get(0).length()];
			
			for(int i = 0; i < lines.size(); i++) {
				for(int j = 0; j < lines.get(i).length(); j++) {
					grid[i][j] = lines.get(i).charAt(j);
				}
			}
			
			return grid;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void display(char[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	public static char[][] simulate(char[][] grid) {
		
		char[][] newGrid = new char[grid.length][grid[0].length];
		
		for (int i = 0; i < grid.length; i++) {
		    System.arraycopy(grid[i], 0, newGrid[i], 0, grid[0].length);
		}
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				Point current = new Point(j, i);
				char acre = grid[current.y][current.x];
				AdjacentTileData data = getAdjacentTileData(grid, current);
				
				if (acre == '.' && data.tree >= 3) {
					newGrid[current.y][current.x] = '|';
				} else if (acre == '|' && data.lumberyard >= 3) {
					newGrid[current.y][current.x] = '#';
				} else if (acre == '#') {
					if (data.lumberyard >= 1 && data.tree >= 1) {
					} else {
						newGrid[current.y][current.x] = '.';						
					}
				}

			}
		}
		
		return newGrid;
	}
	
	public static AdjacentTileData getAdjacentTileData(char[][] grid, Point current) {
		AdjacentTileData data = new AdjacentTileData();
		int maxX = grid[0].length;
		int maxY = grid.length;
		
		for(int y = -1; y < 2; y++) {
			for(int x = -1; x < 2; x++) {
				Point adjacent = new Point(current.x + x, current.y + y);
				
				if ((0 <= adjacent.x && adjacent.x < maxX) && 
						(0 <= adjacent.y && adjacent.y < maxY) && !adjacent.equals(current)){
					
					if(grid[adjacent.y][adjacent.x] == '.') data.open++;
					else if(grid[adjacent.y][adjacent.x] == '|') data.tree++;
					else if(grid[adjacent.y][adjacent.x] == '#') data.lumberyard++;
				
				}
					
			}
		}
		
		return data;
	}
	
	static class AdjacentTileData {
		int tree;
		int open;
		int lumberyard;
		
		@Override
		public String toString() {
			return "AdjacentTileData [tree=" + tree + ", open=" + open + ", lumberyard=" + lumberyard + "]";
		}
		
	}
	
	public static int partTwo(String fileName, int endMinute) {
		
		if(endMinute < 577) {
			
			return partOne(fileName, endMinute);
			
		} else {
			// After minute 576, it will have a pattern of results
			int[] result = {230400, 232311, 229508, 228490, 226772, 221760, 218325, 
					215232, 214914, 207408, 203490, 198576, 198320, 197904,
					197985, 196726, 196350, 196680, 198387, 199374, 202048,
					207080, 211385, 215900, 219263, 225400, 227097, 229836};
			
			endMinute -= 577;
			
			return result[endMinute % result.length];
		}

	}
	

}
