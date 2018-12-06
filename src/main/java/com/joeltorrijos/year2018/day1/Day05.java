package com.joeltorrijos.year2018.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day05 {

	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day05-test-input.txt";
		String fileName = "src/main/resources/2018/day05-input.txt";

		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
		
	}
	
	public static int partOne(String fileName) {
	
		String content = "";
		String newContent = "";
		// REGEX
		// 
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {

		content = stream.collect(Collectors.joining(""));

		Stack<Character> stack = new Stack<>();
		
		for(int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			
			if(!stack.empty()) {
				if(stack.peek() == c -32 || stack.peek() == c +32) {
					stack.pop();
					continue;
				}
			}
			stack.push(c);
		}
		
		while(!stack.empty()) {
			newContent = stack.pop() + newContent;
		}
	
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newContent.length();
		
	}
	
	public static int partTwo(String fileName) {
		String content = "";
		String newContent = "";
		Stack<Character> stack = new Stack<>();
		int[] result = new int[26];
	
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {

			content = stream.collect(Collectors.joining(""));
			
			for(int i = 65, r = 0; i <= 90; i++, r++) {
				newContent = content.replace(Character.toString((char) i), "");
				newContent = newContent.replace(Character.toString((char) (i+ 32)), "");
				
				for(int j = 0; j < newContent.length(); j++) {
					char c = newContent.charAt(j);
					
					if(!stack.empty()) {
						if(stack.peek() == c -32 || stack.peek() == c +32) {
							stack.pop();
							continue;
						}
					}
					stack.push(c);
				}
				
				result[r] = stack.size();
				stack.clear();
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Arrays.stream(result).min().getAsInt();
		
	}

}
