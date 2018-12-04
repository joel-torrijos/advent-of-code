package com.joeltorrijos.year2018.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {

	public static void main(String[] args) {
		String fileName = "src/main/resources/2018/day04-input.txt";

		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
		
	}
	
	public static int partOne(String fileName) {
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		// REGEX
		// 
		int result = 0;
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
		Map<LocalDateTime, String> unsorted = stream.map(s -> new String[] {s.substring(1,17), s.substring(19, s.length())})
													  .collect(LinkedHashMap<LocalDateTime, 
															   String>::new, (map, elem) -> map.put(LocalDateTime.parse(elem[0],formatter), elem[1]), 
															   LinkedHashMap::putAll);
		Map<LocalDateTime, String> sorted = unsorted.entrySet()
												  .stream()
												  .sorted(Map.Entry.comparingByKey())
												  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
									                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		Map<Integer, Integer> guardShifts = new HashMap<>();
		
		
		Map<String, Integer> minutes = new HashMap<>();
		Integer guard = -1;
		LocalDateTime startSleep = null;
		LocalDateTime endSleep = null;
		
		// cant use a foreach or a map since we have to save a variable
		for(Map.Entry<LocalDateTime, String> log : sorted.entrySet()) {
			
			if (log.getValue().startsWith("Guard")) {
				String[] parts = log.getValue().split(" ");
				guard = Integer.parseInt(parts[1].substring(1,parts[1].length()));
				if(!guardShifts.containsKey(guard)) {
					guardShifts.put(guard, 0);
				}
				
			} else if(log.getValue().startsWith("falls")){
				startSleep = log.getKey();
			} else if(log.getValue().startsWith("wakes")){
				endSleep = log.getKey();
				
				Duration duration = Duration.between(endSleep, startSleep);
			    Integer diff = Math.toIntExact(Math.abs(duration.toMinutes()));

				guardShifts.put(guard, guardShifts.getOrDefault(guard,0)+diff);
				
				for(int i = 0; i < diff; i++)
				{
					String id = guard + "-" + (startSleep.getMinute()+i);
					minutes.put(id, minutes.getOrDefault(id,0)+1);
					
				}
				
				startSleep = null;
				endSleep = null;
			}
		}
		
		int guardWithMostSleep = Collections.max(guardShifts.entrySet(), Map.Entry.comparingByValue()).getKey();
		int mostSleptMinute = Integer.parseInt(Collections.max(minutes.entrySet().stream()
							.filter( map -> map.getKey().startsWith(String.valueOf(guardWithMostSleep)))
							.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
									                        (oldValue, newValue) -> oldValue, LinkedHashMap::new))
							.entrySet(), Map.Entry.comparingByValue()).getKey().split("-")[1]);
		
		return guardWithMostSleep * mostSleptMinute;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public static int partTwo(String fileName) {
		
		// TODO 
		// Improve: regex

		Map<LocalDateTime, String> logs = new HashMap<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		// REGEX
		// 
		int result = 0;
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
		Map<LocalDateTime, String> unsorted = stream.map(s -> new String[] {s.substring(1,17), s.substring(19, s.length())})
													  .collect(LinkedHashMap<LocalDateTime, 
															   String>::new, (map, elem) -> map.put(LocalDateTime.parse(elem[0],formatter), elem[1]), 
															   LinkedHashMap::putAll);
		Map<LocalDateTime, String> sorted = unsorted.entrySet()
												  .stream()
												  .sorted(Map.Entry.comparingByKey())
												  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
									                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		Map<Integer, Integer> guardShifts = new HashMap<>();
		
		
		Map<String, Integer> minutes = new HashMap<>();
		Integer guard = -1;
		LocalDateTime startSleep = null;
		LocalDateTime endSleep = null;
		
		// cant use a foreach or a map since we have to save a variable
		for(Map.Entry<LocalDateTime, String> log : sorted.entrySet()) {
			
			if (log.getValue().startsWith("Guard")) {
				String[] parts = log.getValue().split(" ");
				guard = Integer.parseInt(parts[1].substring(1,parts[1].length()));
				if(!guardShifts.containsKey(guard)) {
					guardShifts.put(guard, 0);
				}
				
			} else if(log.getValue().startsWith("falls")){
				startSleep = log.getKey();
			} else if(log.getValue().startsWith("wakes")){
				endSleep = log.getKey();
				
				Duration duration = Duration.between(endSleep, startSleep);
			    Integer diff = Math.toIntExact(Math.abs(duration.toMinutes()));

				guardShifts.put(guard, guardShifts.getOrDefault(guard,0)+diff);
				
				for(int i = 0; i < diff; i++)
				{
					String id = guard + "-" + (startSleep.getMinute()+i);
					minutes.put(id, minutes.getOrDefault(id,0)+1);
					
				}
				
				startSleep = null;
				endSleep = null;
			}
		}

		Map.Entry<String,Integer> mostSlept = Collections.max(minutes.entrySet(),Map.Entry.comparingByValue()); 

		int guardWithMostSleep = Integer.parseInt(mostSlept.getKey().split("-")[0]);
		int mostSleptMinute = Integer.parseInt(mostSlept.getKey().split("-")[1]);
		
		return guardWithMostSleep * mostSleptMinute;
		

		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
