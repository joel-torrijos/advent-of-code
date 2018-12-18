package com.joeltorrijos.year2018.day17;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// TODO
public class Day17 {
	
	static char[][] tiles;

	public static void main(String[] args) {
		String fileName = "src/main/resources/2018/day17/day17-test-input.txt";
//		String fileName = "src/main/resources/2018/day17/day17-input.txt";
		
		partOne(fileName);

	}
	
	public static void partOne(String fileName) {
		List<Point> points = parseInput(fileName);
		
		generateMap(points);
		simulate();
		printMap(tiles);
//		points.forEach(System.out::println);
	}
	
	public static List<Point> parseInput(String fileName){
		
		List<Point> points = new ArrayList<>();
		Pattern p = Pattern.compile("[xy]=(\\d+),\\s[xy]=(\\d+)..(\\d+)");

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			stream.forEach(line -> {
				Matcher matcher = p.matcher(line);
				int start = 0;
				int end = 0;
				final int constant;
				
				if(matcher.find()) {
					constant = Integer.parseInt(matcher.group(1));
					start = Integer.parseInt(matcher.group(2));
					end = Integer.parseInt(matcher.group(3));
//					System.out.println(matcher.group(1) +  " " + matcher.group(2) + " " + matcher.group(3));
					if(line.startsWith("x")) {
//						System.out.println("Constant X");
						points.addAll(IntStream.rangeClosed(start, end)
											   .mapToObj(i -> new Point(constant, i))
											   .collect(Collectors.toList()));
					}
					else {
//						System.out.println("Constant Y");
						points.addAll(IntStream.rangeClosed(start, end)
											   .mapToObj(i -> new Point(i, constant))
											   .collect(Collectors.toList()));
					}
				}
				
				
			});
			
//			points.forEach(System.out::println);
			return points;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return points;
	}

	public static void generateMap(List<Point> points) {
		
		int minimumX = (int) points.stream().min(Comparator.comparing(Point::getX)).get().getX();
		int minimumY = (int) points.stream().min(Comparator.comparing(Point::getY)).get().getY();
		int maximumX = (int) points.stream().max(Comparator.comparing(Point::getX)).get().getX();
		int maximumY = (int) points.stream().max(Comparator.comparing(Point::getY)).get().getY();
		
		// OFFSET
		final int adjustedMinimumX = minimumX > 0 ? (minimumX - 1) : minimumX;
		final int adjustedMinimumY = minimumY > 0 ? (minimumY - 1) : minimumY;
		final int adjustedMaximumX = maximumX++;
		int y = maximumY + 1;
		
		// Adjust points based on new minimum
		points.forEach(point -> { point.x  = point.x - adjustedMinimumX; point.y = point.y - adjustedMinimumY;});
		maximumX = (int) points.stream().max(Comparator.comparing(Point::getX)).get().getX();
		maximumY = (int) points.stream().max(Comparator.comparing(Point::getY)).get().getY();
		
//		points.forEach(System.out::println);
//		System.out.println(adjustedMinimumX + " " +  adjustedMinimumY + " " + adjustedMaximumX + " " + maximumY);
//		System.out.println(y);

		
		tiles = new char[maximumY + 1][maximumX + 1];
		
		Arrays.stream(tiles).forEach(row -> Arrays.fill(row, '.'));
//		System.out.println((0-adjustedMinimumX) + " " + (adjustedMinimumX));
		
		points.stream().forEach(point -> tiles[point.y][point.x] = '#');
		tiles[0-adjustedMinimumY][500-adjustedMinimumX] = '+';
		
		
	}
	
	public static void printMap(char[][] tiles) {

		for(char[] rows : tiles) {
			for(char tile: rows) {
				System.out.print(tile);
			}
			System.out.println();
		}
	}
	
	public static Optional<Point> findSpring(char[][] tiles) {
		for(int y = 0; y < tiles.length; y++) {
			for(int x = 0; x < tiles[y].length; x++)
			{
				if(tiles[y][x] == '+') {
					return Optional.of(new Point(x,y));
				}
			}
		}
		
		return Optional.empty();
	}

	public static void simulate() {
		Deque<Node> stack = new ArrayDeque<>();
		Node node;
		Point start;
		if(findSpring(tiles).isPresent()) {
			start = findSpring(tiles).get();
			node = new Node(tiles, start);
					
			stack.addLast(node);
		}
		else {
			System.out.println("No spring");
		}

		
		while(!stack.isEmpty()) {
			Node current = stack.removeLast();
			System.out.println("Current: " + current);
			
			List<Node> neighbors = current.getNeighbors();
			neighbors.forEach(System.out::println);

			for(Node neighbor : neighbors) {
				if(neighbor!= null && !neighbor.isVisited) {
					stack.addLast(neighbor);
					neighbor.isVisited=true;
				} else {
					stack.removeLast();
				}
			}
			
			System.out.println(stack);
			
//			printMap(tiles);
		}
		
	}
	
	// C
	public static boolean hasFreeAdjacentTile(char[][] tiles, Point point) {
		return hasFreeTileBelow(tiles, point) || hasFreeLeftTile(tiles, point) 
				|| hasFreeRightTile(tiles, point);
	}
	
	public static boolean hasFreeTileBelow(char[][] tiles, Point point) {
		Point belowNeighbor = new Point(point.x, point.y + 1);
		Point start= new Point (0, 0);
		Point end = new Point(tiles[tiles.length-1].length, tiles.length -1);
		
		return  inRange(start, end, belowNeighbor) && isNotOccupied(tiles,belowNeighbor);
	}
	
	public static boolean hasFreeLeftTile(char[][] tiles, Point point) {
		Point leftNeighbor = new Point(point.x-1, point.y);
		Point belowLeftNeighbor = new Point(point.x-1, point.y+1);
		Point start= new Point (0, 0);
		Point end = new Point(tiles[tiles.length-1].length, tiles.length -1);
		
		return (inRange(start, end, leftNeighbor) && isNotOccupied(tiles,leftNeighbor));
	}
	
	public static boolean hasFreeRightTile(char[][] tiles, Point point) {
		Point rightNeighbor = new Point(point.x+1, point.y);
		Point start= new Point (0, 0);
		Point end = new Point(tiles[tiles.length-1].length, tiles.length -1);
		
		return inRange(start, end, rightNeighbor) && isNotOccupied(tiles,rightNeighbor);
	}
	
	
	
	public static boolean inRange(Point min, Point max, Point point) {
		return min.x <= point.x && min.y <= point.y && point.x <= max.x && point.y <= max.y;
	}
	
	public static boolean isNotOccupied(char[][] tiles, Point point) {
		return tiles[point.y][point.x] == '.';
	}
	
	static class Node {
		char[][] tiles;
		boolean isVisited;
		Point point;
		
		public Node(char[][] tiles, Point point) {
			this.tiles = tiles;
			this.isVisited = false;
			this.point = point;
		}
		
		public List<Node> getNeighbors(){
			List<Node> neighbors = new ArrayList<>();
			Point belowNeighbor = new Point(point.x, point.y + 1);
			
			if(inRange(belowNeighbor) && isNotOccupied(belowNeighbor)) {
				neighbors.add(new Node(tiles, belowNeighbor));
				return neighbors;
			} else {
				Point leftNeighbor = new Point(point.x - 1, point.y);
				Point rightNeighbor = new Point(point.x + 1, point.y);
				
				if(inRange(leftNeighbor) && isNotOccupied(leftNeighbor)) {
					neighbors.add(new Node(tiles, leftNeighbor));
				}
				
				if(inRange(rightNeighbor) && isNotOccupied(rightNeighbor)) {
					neighbors.add(new Node(tiles, rightNeighbor));
				}
				
				return neighbors;

			}

		}
		
		public boolean inRange(Point point) {
			return (0 <= point.x && point.x < tiles[tiles.length-1].length &&
					0 <= point.y && point.y < tiles.length);
		}
		
		public boolean isNotOccupied(Point point) {
			return tiles[point.y][point.x] == '.';
		}

		@Override
		public String toString() {
			return "Node [isVisited=" + isVisited + ", point=" + point + "]";
		}
		
		
		

	}
	
}
