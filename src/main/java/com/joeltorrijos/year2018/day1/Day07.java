package com.joeltorrijos.year2018.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day07 {
	
	static Deque<Worker> availableWorkers = new ArrayDeque<>();
	static Deque<Worker> workingWorkers = new ArrayDeque<>();

	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day07-test-input.txt";
		String fileName = "src/main/resources/2018/day07-input.txt";

		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName, 5, 60));
		
	}
	
	public static String partOne(String fileName) {
	
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			String input = stream.collect(Collectors.joining("\n"));
			
			String patternString = "Step\\s*([A-Z])\\s*must\\s*be\\s*finished\\s*before\\s*step\\s*([A-Z])\\s*can\\s*begin.";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(input);

			Set<String> stepNames = new TreeSet<>();

			while(matcher.find()) {
				stepNames.add(matcher.group(1));
				stepNames.add(matcher.group(2));
			}
			
			List<Step> steps = stepNames.stream().map(stepName -> new Step(stepName)).collect(Collectors.toList());
			
			List<String> lines = Arrays.stream(input.split("\n")).collect(Collectors.toList());
			
			lines.forEach(line -> {
				int dependent = ((int) line.charAt(36))-65;
				int dependentOn = ((int) line.charAt(5))-65;
				steps.get(dependent).addDependency(steps.get(dependentOn));
			});
			
			String result = "";
			do
			{
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
				
			} while (steps.size() > 0);
			
			return result;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static int partTwo(String fileName, int numOfWorkers, int timeOffset) {

		for(int i = 1; i <= numOfWorkers; i++) {
			availableWorkers.add(new Worker(i));
		}
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			String input = stream.collect(Collectors.joining("\n"));
			
			String patternString = "Step\\s*([A-Z])\\s*must\\s*be\\s*finished\\s*before\\s*step\\s*([A-Z])\\s*can\\s*begin.";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(input);

			Set<String> stepNames = new TreeSet<>();

			while(matcher.find()) {
				stepNames.add(matcher.group(1));
				stepNames.add(matcher.group(2));
			}
			
			List<Step> steps = stepNames.stream().map(stepName -> new Step(stepName)).collect(Collectors.toList());

			//Add connections
			List<String> lines = Arrays.stream(input.split("\n")).collect(Collectors.toList());
			lines.forEach(line -> {
				int dependent = ((int) line.charAt(36))-65;
				int dependentOn = ((int) line.charAt(5))-65;
				steps.get(dependent).addDependency(steps.get(dependentOn));
			});
			
			int[] time = IntStream.range(1, steps.size()+1).map(i -> i + timeOffset).toArray();
			
			List<Step> availableSteps = new LinkedList<>();
			int currentTime = 0;
			
			do
			{
				Set<Integer> donesAt = new HashSet<>(); 
				boolean checkForAvailable;
				
				do 
				{
					checkForAvailable = false;
					
					// Check for available and add to queue
					Iterator<Step> it = steps.iterator();
					while(it.hasNext()) {
						Step currentStep = it.next();
						
						if(currentStep.hasRan()) {
							it.remove();
							continue;
						}
						
						if(!currentStep.hasDependencies())
						{
							System.out.println(currentTime + ": " + "Adding " + currentStep.getName() + " to available");
							availableSteps.add(currentStep);
							it.remove();
						}
					}
					
					// Give available step to available worker
					Iterator<Worker> availableWorkersIt = availableWorkers.iterator();
					while(availableWorkersIt.hasNext()) {
						Worker worker = availableWorkersIt.next();
						
						if(worker.getSteps().isEmpty() && !availableSteps.isEmpty()) {
							Step available = availableSteps.remove(0);
							System.out.println(currentTime + ": " + "Adding " + available.getName() + " to worker " + worker.getName());
							worker.getSteps().add(available); // add step to worker
							workingWorkers.add(worker);
							availableWorkersIt.remove(); // remove worker from available worker queue
						}
					}
					
					donesAt = new HashSet<>(); 
					
					// Worker run;
					Iterator<Worker> workingWorkersIt = workingWorkers.iterator();
					while(workingWorkersIt.hasNext()) {
						Worker workingWorker = workingWorkersIt.next();
						if(!workingWorker.getSteps().isEmpty()) {
							if(workingWorker.getSteps().peek().isRunning() == false) {
								int name = workingWorker.getSteps().peek().getName().charAt(0) - 65;
								int doneAt = currentTime + time[name];
								workingWorker.getSteps().peek().setRunning(true);
								workingWorker.getSteps().peek().setDoneAt(doneAt);
								
								System.out.println(currentTime + ": " + "Running " +  workingWorker.getSteps().peek().getName()
										+ " at " + currentTime + " and will be done at " + doneAt);
								
								donesAt.add(doneAt);
							}
							
							if(workingWorker.getSteps().peek().isRunning() == true && workingWorker.getSteps().peek().getDoneAt() <= currentTime) {
								System.out.println(currentTime + ": " + workingWorker.getSteps().peek().getName() + " finished at " + currentTime);
								workingWorker.getSteps().peek().run2();
								workingWorker.getSteps().remove();
								availableWorkers.addLast(workingWorker);
								workingWorkersIt.remove(); // remove from working workers queue
								checkForAvailable = true;
							} else {
								donesAt.add(workingWorker.getSteps().peek().getDoneAt());
							}
						}
					}
					
				} while(checkForAvailable);
				
				System.out.println("deadlines " + donesAt);
				
				currentTime = donesAt.stream().min(Integer::compare).map(x -> x).orElse(currentTime);

				System.out.println(currentTime);

			} while (steps.size() > 0);
			
			return currentTime;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	static class Step implements Comparable<Step>{
		
		private List<Step> dependencies = new ArrayList<>();
		private Set<Step> parents = new TreeSet<>();
		private String name;
		private boolean ran = false;
		private boolean running = false;
		private int doneAt;
		
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
		
		public boolean isRan() {
			return ran;
		}

		public void setRan(boolean ran) {
			this.ran = ran;
		}

		public int getDoneAt() {
			return doneAt;
		}

		public void setDoneAt(int doneAt) {
			this.doneAt = doneAt;
		}
		
		public boolean isRunning() {
			return running;
		}

		public void setRunning(boolean running) {
			this.running = running;
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
			this.dependencies.remove(dependency);
			dependency.getParents().remove(this);
		}
		
		public String run() {
			this.ran = true;
			String result = "";
			
			result += this.getName();

			while(parents.iterator().hasNext()) {
				Step parent = parents.iterator().next();
				parent.removeDependency(this);
				
				if(!parent.hasDependencies()) {
					result += parent.run();
				}
				
			}
			
			return result;
			
		}
		
		public void run2() {
			this.ran = true;

			while(parents.iterator().hasNext()) {
				Step parent = parents.iterator().next();
				
				parent.removeDependency(this);

			}

		}
		
		public boolean hasRan() {
			return ran;
		}
		
		@Override
		public int compareTo(Step o) {
			return this.getName().compareTo(o.getName());
		}	
	}

	static class Worker {
		private int name;
		private ArrayDeque<Step> steps = new ArrayDeque<>();
		
		public Worker(int name) {
			this.name = name;
			steps = new ArrayDeque<>();
		}

		public int getName() {
			return name;
		}

		public void setName(int name) {
			this.name = name;
		}

		public ArrayDeque<Step> getSteps() {
			return steps;
		}

		public void setSteps(ArrayDeque<Step> steps) {
			this.steps = steps;
		}
		
	}
}





