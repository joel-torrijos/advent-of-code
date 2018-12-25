package com.joeltorrijos.year2018;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class FrequencyCounter {

	public static void main(String[] args) {
		
		String fileName = "src/main/resources/2018/input.txt";

		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
	}
	
	public static Integer toInt(String line) {
		if(line.startsWith("+")) 
			return Integer.parseInt(line.substring(1));
		else 
			return Integer.parseInt(line);
		
	}

	public static Integer partOne(String uri) {
		Integer result = 0;
		try(Stream<String> stream = Files.lines(Paths.get(uri))) {
			result = stream.map(FrequencyCounter::toInt)
						   .reduce(0, Integer::sum);
			
			return result;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static Integer partTwo(String fileName){
		Integer input;
		Set<Integer> resultingFrequencies = new HashSet<>();
		Integer resultingFrequency = 0;
		boolean notFound = true;
		
		do  {
			
			Scanner fileReader;
			try {
				fileReader = new Scanner(new File(fileName));
				while (fileReader.hasNext()) {
					String line = fileReader.nextLine();
					
					resultingFrequency += toInt(line);

					if(!resultingFrequencies.add(resultingFrequency)) {
						notFound = false;
						return resultingFrequency;
					}
						
				}
				fileReader.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		} while(notFound);
		
		return resultingFrequency;
	}

}
