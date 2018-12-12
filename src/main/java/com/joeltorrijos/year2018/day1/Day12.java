package com.joeltorrijos.year2018.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {
	
	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day12-test-input.txt";
		String fileName = "src/main/resources/2018/day12-input.txt";

		System.out.println(partOne(fileName, 20));
		System.out.println(partTwo(fileName, 50000000000L));
		
	}
	
	public static int partOne(String fileName, int maxGeneration) {
	
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			
			String initialState = br.readLine().replace("initial state: ", "");
			br.close();

			// TODO fix regex
			String spreadConditions = Files.lines(Paths.get(fileName))
												.filter(s -> !s.startsWith("initial state"))
												.filter(((Predicate<String>) String::isEmpty).negate())
												.map(s->s)
												.collect(Collectors.joining("\n"));


			Pattern p = Pattern.compile("([.|#]{2})(.*)([.|#]{2})\\s=>\\s(.)");
			Matcher matcher = p.matcher(spreadConditions);
			StringBuffer sb = new StringBuffer();
			
			while (matcher.find()) {
			    String output = matcher.replaceAll("($1)($2)($3) $4");  
			    sb.append(output);

			}
			
			// TODO fix regex
			Map<String, String> conditions = Arrays.stream(new String(sb).split("\n"))
													.map(s -> s.replaceAll("[.]", "[.]"))
													.map(s -> s.split(" "))
													.collect(Collectors.toMap(s -> s[0], 
															s -> {
																if(s[1].equals("[.]")) return ".";
																else return s[1];
													}));
			
			String currentState = initialState;
			int total = 0;
			int indexOfZero = 0;
			
			for(int gen = 0; gen < maxGeneration; gen++)
			{
				// TODO change concatenation
				currentState = "..." + currentState + "...";
				indexOfZero += 3;
				char[] chars = new char[currentState.length()];
				Arrays.fill(chars, '.');
								
				Iterator<Map.Entry<String, String>> conditionsIt = conditions.entrySet().iterator();
				while(conditionsIt.hasNext()) {
					Map.Entry<String, String> condition = conditionsIt.next();
					p = Pattern.compile("(?=(" + condition.getKey() + "))");
					matcher = p.matcher(currentState).useTransparentBounds(true);
					sb = new StringBuffer();
					
					while (matcher.find()) {
						int index = matcher.start() + 2;
						chars[index] = condition.getValue().charAt(0);
				    }
				}
				String newState = new String(chars);
				currentState = newState;

			}
			
			total = 0;
			for(int i = 0; i < currentState.length(); i++) {
				if(currentState.charAt(i) == '#') 
					total += (i - indexOfZero);
			}

			return total;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
	public static long partTwo(String fileName, long maxGeneration) {
	
		// partial sum?
		
		long partialSum = partOne(fileName, 167);
		
		long total = ((maxGeneration - 167) * 55) + partialSum;

		return total;
	}

}
