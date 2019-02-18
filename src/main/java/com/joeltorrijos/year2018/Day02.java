package com.joeltorrijos.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Day02 {

	public static void main(String[] args) {
		String fileName = "src/main/resources/2018/day02-input.txt";
		
		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
		
	}
	
	public static int partOne(String fileName) {

		try (Scanner fileReader = new Scanner(new File(fileName))) {
			
			Map<Integer, Integer> resultMap = new HashMap<>();
			Map<Character, Integer> lineResult = new HashMap<>();
			
			while (fileReader.hasNext()) {
				String line = fileReader.nextLine();
				
				for(char ch : line.toCharArray()) {
					lineResult.put(ch, lineResult.getOrDefault(ch, 0) + 1);
				}
				
				if(lineResult.containsValue(2)) {
					resultMap.put(2, resultMap.getOrDefault(2, 0) + 1);
				}
				
				if(lineResult.containsValue(3)) {
					resultMap.put(3, resultMap.getOrDefault(3, 0) + 1);
				}
				
				lineResult.clear();
			}

			return resultMap.get(2) * resultMap.get(3);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String partTwo(String fileName) {
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			String[] lines = stream.toArray(String[]::new);
			
			for(int i = 0; i < lines.length; i++) {
				for(int j = i+1; j<lines.length; j++) {
					int diff = 0;
					String result = "";
					
					for(int n = 0; n < lines[i].length(); n++) {
						char first = lines[i].charAt(n);
						char second = lines[j].charAt(n);
						
						if((first ^ second) > 0) {
							diff++;
						} else {
							result += lines[i].charAt(n);			
						}
						
						if(diff > 1)
						{
							break;
						}
					}
					
					if(diff == 1)
					{
						return result;
					}
					
				}
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
