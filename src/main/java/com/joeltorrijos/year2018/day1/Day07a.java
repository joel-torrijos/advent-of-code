package com.joeltorrijos.year2018.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07a {

	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day07-test-input.txt";
		String fileName = "src/main/resources/2018/day07-input.txt";

		System.out.println("oi");
		System.out.println(partOne(fileName));
//		System.out.println(partTwo(fileName));
		
	}
	
	public static String partOne(String fileName) {
	
//		int result = 0;
		Set<Character> set = new LinkedHashSet<>();
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {

			List<Step> steps = new ArrayList<>();
			
			for(int i = 0; i < 26; i++) {
				char c = ((char) (i + 65));
				steps.add(new Step(String.valueOf(c)));
			}
			
			List<String> lines = stream.collect(Collectors.toList());
			
			lines.forEach(line -> {
				int dependent = ((int) line.charAt(36))-65;
				int dependentOn = ((int) line.charAt(5))-65;
				steps.get(dependent).addDependency(steps.get(dependentOn));
			});
			
			
//			steps.get(0).addDependency(steps.get(2));
//			steps.get(1).addDependency(steps.get(0));
//			steps.get(3).addDependency(steps.get(0));
//			steps.get(5).addDependency(steps.get(2));
//			steps.get(4).addDependency(steps.get(1));
//			steps.get(4).addDependency(steps.get(3));
//			steps.get(4).addDependency(steps.get(5));
//			
			steps.forEach(step -> System.out.println(step.getName() + " " + step.hasDependencies()));
			
			List<Step> queue = new LinkedList<>();
			
			
			String result = "";
			do
			{
				boolean go = false;
				
				Iterator<Step> it = steps.iterator();
				
				while(it.hasNext()) {
					Step currentStep = it.next();
					if(currentStep.hasRan()) {
						it.remove();
						continue;
					}
					if(!currentStep.hasDependencies())
					{
						result += currentStep.run();
						it.remove();
					}
				}
				
				// Run in queue;
//				Iterator<Step> queueIterator = queue.iterator();
//				
//				while(queueIterator.hasNext())
//				{
//					Step currentStep = queueIterator.next();
//					currentStep.run();
//					queueIterator.remove();
//				}
				
//				for(Step step: steps) {
//					if(!step.hasDependencies())
//					{
//						step.run();
//					}
//				}
////				
//				System.out.println(steps.size());
			} while (steps.size() > 0);
			return result;

			//			A.addDependency(C); 0 2
//			B.addDependency(A); 1 0
//			D.addDependency(A); 3 0
//			F.addDependency(C); 5 2
//			E.addDependency(B); 4 1
//			E.addDependency(D); 4 3
//			E.addDependency(F); 4 6
			
		
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

class Step implements Comparable<Step>{
	
	private List<Step> dependencies = new ArrayList<>();
	private Set<Step> parents = new TreeSet<>();
	private String name;
	private boolean ran = false;

	public Step(String name) {
		this.name = name;
	}

	public List<Step> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Step> dependencies) {
		this.dependencies = dependencies;
	}

	public Set<Step> getParents() {
		return parents;
	}

	public void setParents(Set<Step> parents) {
		this.parents = parents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addDependency(Step dependency) {
		this.dependencies.add(dependency);
		dependency.addParent(this);
	}
	
	public void addParent(Step parent) {
		this.parents.add(parent);
	}
	
	public boolean hasDependencies() {
		return dependencies.size() > 0;
	}
	
	public void removeDependency(Step dependency) {
//		System.out.println(this.getName() + " removes " + dependency.getName());
		this.dependencies.remove(dependency);
		dependency.getParents().remove(this);
	}
	
	public String run() {
		this.ran = true;
		String result = "";
		
		result += this.getName();
		
//		System.out.println(this.getName() + " is running...");
		while(parents.iterator().hasNext()) {
			Step parent = parents.iterator().next();
			
			parent.removeDependency(this);
//			System.out.println(parent.getName() + " " + parent.hasDependencies());
			if(!parent.hasDependencies()) {
				result += parent.run();
			}
			
		}
		
		return result;
		
	}
	
	public boolean hasRan() {
		return ran;
	}

	@Override
	public int compareTo(Step o) {
		return this.getName().compareTo(o.getName());
	}
	
	
}




