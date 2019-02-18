package com.joeltorrijos.year2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23 {
	
	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day23/day23-test-input.txt";
		String fileName = "src/main/resources/2018/day23/day23-input.txt";
		
		System.out.println(partOne(fileName));
//		System.out.println(partTwo(fileName, 1000000000));
		
	}
	
	public static int partOne(String fileName) {
		
		List<Nanobot> nanobots = parseInput(fileName);		
		
//		System.out.println();
		Nanobot strongest = nanobots.stream().max(Comparator.comparing(Nanobot::getRange)).get();
		long inRange = nanobots.stream().filter(strongest::inRange).count();
		
//		System.out.println(inRange);
		
		return (int) inRange;
	}
	
	public static List<Nanobot> parseInput(String fileName) {
		
		List<String> lines = new ArrayList<>();
		List<Nanobot> nanobots = new ArrayList<>();
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			lines = stream.collect(Collectors.toList());
			
			Pattern p = Pattern.compile("pos=<(-?\\d+),\\s*(-?\\d+),\\s*(-?\\d+)>,\\s*r=(-?\\d+)");
			Matcher m;
			
			for (String line : lines) {
				m = p.matcher(line);
				
				if(m.find()) {
					int x = Integer.parseInt(m.group(1));
					int y = Integer.parseInt(m.group(2));
					int z = Integer.parseInt(m.group(3));
					int r = Integer.parseInt(m.group(4));
					nanobots.add(new Nanobot(x,y,z,r));
				}
				m.reset();
			}
			
//			nanobots.forEach(System.out::println);
			
			return nanobots;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	static class Nanobot {
		int x, y, z;
		int range;
		
		public Nanobot(int x, int y, int z, int range) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.range = range;
		}
		
		public boolean inRange(Nanobot other) {
			return (Math.abs(other.x - this.x) +  Math.abs(other.y - this.y) 
				+  Math.abs(other.z - this.z)) <= range;
		}
		
		public int getRange() {
			return this.range;
		}

		@Override
		public String toString() {
			return "Nanobot [x=" + x + ", y=" + y + ", z=" + z + ", range=" + range + "]";
		}
		
		
	}
	
	
}
