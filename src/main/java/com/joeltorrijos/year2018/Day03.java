package com.joeltorrijos.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day03 {

	public static void main(String[] args) {
		String fileName = "src/main/resources/2018/day03-test-input.txt";
		
		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
		
	}
	
	public static long partOne(String fileName) {
		
		// TODO 
		// Improve: regex
		// Map gets big 

		try {
			Map<String, Integer> coordinatesMap = new HashMap<>();
			Map<Character, Integer> lineResult = new HashMap<>();
			
			Scanner fileReader = new Scanner(new File(fileName));
			
			while (fileReader.hasNext()) {
				String line = fileReader.nextLine();
				
				String coordinateInfo = line.trim().split("@")[1];
				int[] offset = Arrays.stream(coordinateInfo.split(":")[0].split(","))
						.map(String::trim).mapToInt(Integer::parseInt).toArray();
				
				int[] dimensions = Arrays.stream(coordinateInfo.split(":")[1].split("x"))
						.map(String::trim).mapToInt(Integer::parseInt).toArray();
				
				int[] startingCoordinates = { offset[0]+1, offset[1]+1 };
				int area = dimensions[0] * dimensions[1];

				for(int i = 0; i < dimensions[0]; i++) {
					for(int j = 0; j < dimensions[1]; j++) {
						String coordinates = (startingCoordinates[0]+i) + "," + (startingCoordinates[1]+j);
						coordinatesMap.put(coordinates, coordinatesMap.getOrDefault(coordinates, 0) +1);
					}
				}
				
				
			}
			
			
			long result = coordinatesMap.values().stream().filter( v -> v > 1).count();
			fileReader.close();

			return result;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String partTwo(String fileName) {
		
		// TODO 
		// Improve: regex

		try {
			Map<String, String> coordinatesMap = new HashMap<>();
			Set<String> result = new HashSet<>();
			
			Scanner fileReader = new Scanner(new File(fileName));
			
			while (fileReader.hasNext()) {
				String line = fileReader.nextLine();
				
				String id = line.trim().split("@")[0].trim();
				result.add(id.substring(1));
				
				String coordinateInfo = line.trim().split("@")[1];
				int[] offset = Arrays.stream(coordinateInfo.split(":")[0].split(","))
						.map(String::trim).mapToInt(Integer::parseInt).toArray();
				
				int[] dimensions = Arrays.stream(coordinateInfo.split(":")[1].split("x"))
						.map(String::trim).mapToInt(Integer::parseInt).toArray();
				
				int[] startingCoordinates = { offset[0]+1, offset[1]+1 };
				int area = dimensions[0] * dimensions[1];
				
				for(int i = 0; i < dimensions[0]; i++) {
					for(int j = 0; j < dimensions[1]; j++) {
						String coordinates = (startingCoordinates[0]+i) + "," + (startingCoordinates[1]+j);
						coordinatesMap.put(coordinates, coordinatesMap.getOrDefault(coordinates, "").concat(id));	

					}
				}
			}
			
			
			String[] overlapIds = coordinatesMap.values()
											.stream()
											.filter( v -> v.split("#").length > 2)
											.distinct()
											.toArray(size -> new String[size]);
			
			for(String overlapId : overlapIds) {
				String[] ids = overlapId.split("#");
				
				for(String id : ids) {
					result.remove(id);
				}

			}


			fileReader.close();

			// Return the first element of the set
			return result.iterator().next();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "";
	}

}
