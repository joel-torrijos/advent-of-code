package com.joeltorrijos.year2018.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07 {

	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day07-test-input.txt";
		String fileName = "src/main/resources/2018/day07-input.txt";

		System.out.println(partOne(fileName));
//		System.out.println(partTwo(fileName));
		
	}
	
	public static String partOne(String fileName) {
	
//		int result = 0;
		Set<Character> set = new LinkedHashSet<>();
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			List<String> lines = stream.collect(Collectors.toList());
			
			// TODO make array size dynamic
			// Setup grid
			int nodes[][] = new int[26][26];
			boolean[] checked = new boolean[26];
			
			for(int i = 0; i < nodes.length; i++) {
				Arrays.fill(nodes[i], 0);
				Arrays.fill(checked, false);
			}
			
			for(int y = 0; y < nodes.length; y++) {
				for (int x = 0; x < nodes[y].length; x++) {
					System.out.print(nodes[y][x]);
				}
				System.out.println();
			}

			System.out.println("=========");
			// Populate grid
			for(String line: lines) {
				int y = line.charAt(36) - 65;
				int x = line.charAt(5) - 65;
				nodes[y][x] = 1;
//				System.out.println(line.charAt(5) + " " + line.charAt(36));
			}
			
			for(int y = 0; y < nodes.length; y++) {
				for (int x = 0; x < nodes[y].length; x++) {
					System.out.print(nodes[y][x]);
				}
				System.out.println();
			}
			
			
			
			Set<Character> result = new LinkedHashSet<>();
			
//			int nodes[][] = {{0,0,1,0,0,0},
//					         {1,0,0,0,0,0},
//					         {0,0,0,0,0,0},
//					         {1,0,0,0,0,0},
//					         {0,1,0,1,0,1},
//					         {0,0,0,1,0,0}};
			boolean notYetDone = true;
			do 
			{
				notYetDone = false;
				
				for(int y = 0; y < nodes.length; y++) {
					char c = ((char) (y + 65));
					boolean noDependencies = true;
					
					for (int x = 0; x < nodes[y].length; x++) {
						if(nodes[y][x] != 0) {
							notYetDone = true;
							noDependencies = false;
							break;
						}
					}
					
					if(noDependencies == true && checked[y] == false) {
						notYetDone = true;
						checked[y] = true;
						result.add(c);
						for (int y1 = 0; y1 < nodes.length; y1++) {
							nodes[y1][y] = 0;
						}
						break;
					}
				}
			} while(notYetDone);
	
//			String resultString = "";
			String resultString = result.stream()
										.map(c-> Character.toString(c))
										.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
										.toString();
			
			return resultString;
//			System.out.println(result);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String partTwo(String fileName) {
		
//		int result = 0;
		Set<Character> set = new LinkedHashSet<>();
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			List<String> lines = stream.collect(Collectors.toList());
			
			// TODO make array size dynamic
			// Setup grid
			int nodes[][] = new int[26][26];
			boolean[] checked = new boolean[26];
			
			for(int i = 0; i < nodes.length; i++) {
				Arrays.fill(nodes[i], 0);
				Arrays.fill(checked, false);
			}
			
//			for(int y = 0; y < nodes.length; y++) {
//				for (int x = 0; x < nodes[y].length; x++) {
//					System.out.print(nodes[y][x]);
//				}
//				System.out.println();
//			}

			System.out.println("=========");
			// Populate grid
			for(String line: lines) {
				int y = line.charAt(36) - 65;
				int x = line.charAt(5) - 65;
				nodes[y][x] = 1;
//				System.out.println(line.charAt(5) + " " + line.charAt(36));
			}
			
//			for(int y = 0; y < nodes.length; y++) {
//				for (int x = 0; x < nodes[y].length; x++) {
//					System.out.print(nodes[y][x]);
//				}
//				System.out.println();
//			}
			
			Set<Character> result = new LinkedHashSet<>();
			
//			int nodes[][] = {{0,0,1,0,0,0},
//					         {1,0,0,0,0,0},
//					         {0,0,0,0,0,0},
//					         {1,0,0,0,0,0},
//					         {0,1,0,1,0,1},
//					         {0,0,0,1,0,0}};
			
			List<Stack<Task>> workers = new LinkedList<>();
			workers.add(new Stack<Task>());
			workers.add(new Stack<Task>());
			
			LinkedList<Integer> available = new LinkedList<>();
			
			boolean notYetDone = true;
			int currentTime = 0;
			do 
			{
				notYetDone = false;
				for(int y = 0; y < nodes.length; y++) {
					char c = ((char) (y + 65));
					boolean noDependencies = true;
					
					for (int x = 0; x < nodes[y].length; x++) {
						if(nodes[y][x] != 0) {
							notYetDone = true;
							noDependencies = false;
							break;
						}
					}
					
					if(noDependencies == true && checked[y] == false) {
						available.add(y);
					}
					
				}
				
				int nextMinTime = available.peek() + 1 + currentTime;
				// give tasks to workers
				for(Integer i : available) {
					for(Stack<Task> worker: workers) {
						if(worker.empty()) {
							//asign
							int name = available.poll();
							int timeToFinish = name + 1;
							worker.push(new Task(name, timeToFinish));
							
							if(nextMinTime > timeToFinish + currentTime) {
								nextMinTime = timeToFinish + currentTime;
							}
						}
					} 
				}
			} while(notYetDone);
	
			
			
			String resultString = result.stream()
										.map(c-> Character.toString(c))
										.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
										.toString();
			
			return resultString;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}

class Task {
	private int name;
	private int doneAt;
	
	public Task(int name, int doneAt) {
		this.name = name;
		this.doneAt = doneAt;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(int doneAt) {
		this.doneAt = doneAt;
	}
	
}





