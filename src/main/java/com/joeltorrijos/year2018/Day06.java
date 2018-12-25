package com.joeltorrijos.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day06 {

	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day06-test-input.txt";
		String fileName = "src/main/resources/2018/day06-input.txt";

		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
		
	}
	
	public static int partOne(String fileName) {
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			List<String> lines = stream.collect(Collectors.toList());
			
			Integer[] xCoordinates = lines.stream().map(s -> s.split(",")[0].trim())
					.map(s-> Integer.parseInt(s)).toArray(Integer[]::new);
			
			Integer[] yCoordinates = lines.stream().map(s -> s.trim().split(",")[1].trim())
					.map(s-> Integer.parseInt(s)).toArray(Integer[]::new);

			int maxX = Collections.max(Arrays.asList(xCoordinates));
			int maxY = Collections.max(Arrays.asList(yCoordinates));
			
			
			Point[][] grid = new Point[maxY+1][maxX+1];
			Map<Integer, Integer> closest = new HashMap<>();
			
			for(int i = 0; i < yCoordinates.length; i++) {
				grid[yCoordinates[i]][xCoordinates[i]] = new Point(i,0);
				closest.put(i, closest.getOrDefault(i, 1));
			}
		
			for(int j = 0; j < xCoordinates.length; j++)
			{
				int currentPointX = xCoordinates[j];
				int currentPointY = yCoordinates[j];
				
				for(int y = 0; y < maxY +1; y++) {
					for(int x = 0; x < maxX +1 ; x++) {
						int distance = Math.abs(currentPointY - y) + Math.abs(currentPointX - x);
						
						if( grid[y][x] == null) {
							grid[y][x] = new Point(j, distance);
							closest.put(j, closest.getOrDefault(j, 0)+1);
						} else {
							Integer previous = grid[y][x].getClosest();
							
							if(grid[y][x].getDistance() > distance)
							{
								grid[y][x].setClosest(j);
								grid[y][x].setDistance(distance);
								closest.put(Integer.valueOf(j), (closest.getOrDefault(Integer.valueOf(j), 0)+1));
								closest.put(previous, (closest.getOrDefault(previous, 0)-1));						
							} else if(grid[y][x].getDistance() == distance && grid[y][x].getDistance() != 0) {
								grid[y][x].setClosest(-1);
								closest.put(previous, closest.getOrDefault(previous, 0)-1);
							}
						}		
					}
				}				
			}
			
			for(int y = 0; y < maxY +1; y+=maxY) {
				for(int x = 0; x < maxX +1; x++) {
					closest.remove(grid[y][x].getClosest());
				}					
			}		
			
			for(int y = 0; y < maxY +1; y++) {
				for(int x = 0; x < maxX +1; x+=maxX) {
					closest.remove(grid[y][x].getClosest());
				}					
			}	
			
			return Collections.max(closest.entrySet(),Map.Entry.comparingByValue()).getValue();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static int partTwo(String fileName) {
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			List<String> lines = stream.collect(Collectors.toList());
			
			Integer[] xCoordinates = lines.stream().map(s -> s.split(",")[0].trim())
					.map(s-> Integer.parseInt(s)).toArray(Integer[]::new);
			
			Integer[] yCoordinates = lines.stream().map(s -> s.trim().split(",")[1].trim())
					.map(s-> Integer.parseInt(s)).toArray(Integer[]::new);

			int maxX = Collections.max(Arrays.asList(xCoordinates));
			int maxY = Collections.max(Arrays.asList(yCoordinates));
			
			
			Point[][] grid = new Point[maxY+1][maxX+1];

			for(int i = 0; i < yCoordinates.length; i++) {
				grid[yCoordinates[i]][xCoordinates[i]] = new Point(i,0, 0);
			}
		
			for(int j = 0; j < xCoordinates.length; j++)
			{
				int currentPointX = xCoordinates[j];
				int currentPointY = yCoordinates[j];
				
				for(int y = 0; y < maxY +1; y++) {
					for(int x = 0; x < maxX +1 ; x++) {
						int distance = Math.abs(currentPointY - y) + Math.abs(currentPointX - x);
						
						if( grid[y][x] == null) {
							grid[y][x] = new Point(j, distance, distance);
						} else {
							int totalDistance = grid[y][x].getTotalDistance() + distance;
							grid[y][x].setTotalDistance(totalDistance); 
						}		
					}
				}				
			}
			
			int counter = 0;

			for(int y = 0; y < maxY +1; y++) {
				for(int x = 0; x < maxX +1 ; x++) {
					if(grid[y][x].getTotalDistance() < 10000)
					{
						counter++;
					}
				}
			}		
			
			return counter;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}

class Point {
	
	private int closest;
	private int distance;
	private int totalDistance;
	
	

	public Point(int closest, int distance) {
		this.closest = closest;
		this.distance = distance;
	}

	public Point(int closest, int distance, int totalDistance) {
		super();
		this.closest = closest;
		this.distance = distance;
		this.totalDistance = totalDistance;
	}
	
	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	public int getClosest() {
		return closest;
	}
	
	public void setClosest(int closest) {
		this.closest = closest;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Point [closest=" + closest + ", distance=" + distance + "]";
	}
	
}
