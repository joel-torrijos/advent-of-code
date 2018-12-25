package com.joeltorrijos.year2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {
	
	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day10-test-input.txt";
		String fileName = "src/main/resources/2018/day10-input.txt";

		System.out.println(partOne(fileName));
//		System.out.println(partTwo(fileName));
		
	}
	
	public static int partOne(String fileName) {
	
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {

			String text = stream.collect(Collectors.joining("\n"));

			String patternString = "position=<\\s*(-?\\d*,\\s*-?\\d*)>\\s*velocity=<\\s*(-?\\d*,\\s*-?\\d*)>";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(text);

			List<Point> points = new ArrayList<>();
			
			while(matcher.find()) {
				int x = Integer.parseInt(matcher.group(1).split(",")[0].trim());
				int y = Integer.parseInt(matcher.group(1).split(",")[1].trim());
				int velocityX = Integer.parseInt(matcher.group(2).split(",")[0].trim());
				int velocityY = Integer.parseInt(matcher.group(2).split(",")[1].trim());
				points.add(new Point(x,y,velocityX, velocityY));
	        }
			
			boolean notSmallEnough = true;
			int seconds = 0;
			
			do {
				// Adjust to 0,0
				int minCurrentX = points.stream().min(Comparator.comparing(p -> p.currentX)).get().currentX;
				int minCurrentY = points.stream().min(Comparator.comparing(p -> p.currentY)).get().currentY;
				
				points.forEach(point -> { point.currentX -= minCurrentX; point.currentY -= minCurrentY;});
				
				int adjustedMinCurrentX = points.stream().min(Comparator.comparing(p -> p.currentX)).get().currentX;
				int adjustedMinCurrentY = points.stream().min(Comparator.comparing(p -> p.currentY)).get().currentY;
				int maxCurrentX = points.stream().max(Comparator.comparing(p -> p.currentX)).get().currentX;
				int maxCurrentY = points.stream().max(Comparator.comparing(p -> p.currentY)).get().currentY;
				
				// Manhattan distance of minimum and maximum possible x y
				int distance = Math.abs(maxCurrentX - adjustedMinCurrentX) + Math.abs(maxCurrentY - adjustedMinCurrentY);
				
				
				if (distance < 100) {
					int maxX = points.stream().max(Comparator.comparing(p -> p.currentX)).get().currentX + 1;
					int maxY = points.stream().max(Comparator.comparing(p -> p.currentY)).get().currentY + 1;
					
					// Setup Grid
					char[][] grid = new char[maxY][maxX];
					
					Arrays.stream(grid).forEach(row -> Arrays.fill(row, '.'));

					points.forEach(point -> grid[point.currentY][point.currentX] = '#');
							
					// Display
					System.out.println(seconds + " seconds");
					for(int y = 0; y < maxY; y++) {
						for(int x = 0; x < maxX; x++) {
							System.out.print(grid[y][x]);
						}
						System.out.println();
					}					
		
					System.out.println();
					
				}

				// Move sleighs
				points.forEach(Point::move);
				seconds++;
				
			} while(notSmallEnough);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	static class Point {
		int x, y, velocityX, velocityY, currentX, currentY;

		public Point(int x, int y, int velocityX, int velocityY) {
			this.x = x;
			this.y = y;
			this.velocityX = velocityX;
			this.velocityY = velocityY;
			this.currentX = this.x;
			this.currentY = this.y;
		}
		
		public void move() {
			this.currentX += this.velocityX;
			this.currentY += this.velocityY;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + ", velocityX=" + velocityX + ", velocityY=" + velocityY + "]";
		}
	}
}
