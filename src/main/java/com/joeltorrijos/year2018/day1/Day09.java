package com.joeltorrijos.year2018.day1;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Day09 {
	


	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day09-test-input.txt";
		String fileName = "src/main/resources/2018/day09-input.txt";

//		System.out.println(partOne(9,25));
//		System.out.println(partOne(9,48));
//		System.out.println(partOne(1,95));
//		System.out.println(partOne(10,1618));
//		System.out.println(partTwo(fileName));
		
		System.out.println(partTwo(9, 25));
		
//		System.out.println(partTwo(418, 70769));
//		System.out.println(partTwo(9, 25));
//		System.out.println(partTwo(10, 1618));
//		System.out.println(partTwo(418, 7076900));
		
		
	}
	
	public static int partOne(int players, int lastMarble) {
	
		LinkedList<Integer> marbles = new LinkedList<>();
		Map<Integer, Integer> score = new HashMap<>(); // player, score
		
		int currentPlayer = 1;
		int currentMarble = 1;
		int index =0;
		int lastIndex = 0;
		
		do {
			
			if(currentMarble % 23 == 0) {
				
				score.put(currentPlayer, score.getOrDefault(currentPlayer, 0)+currentMarble);

				lastIndex -= 7;
				if(lastIndex < 0) {
					System.out.println("CC " + lastIndex);
					// TODO WHY
					lastIndex += marbles.size() + 1;
				}
				score.put(currentPlayer, score.getOrDefault(currentPlayer, 0)+marbles.remove(lastIndex));
				
			} else if (lastIndex == marbles.size() - 1) {
				marbles.add(0, currentMarble);
				lastIndex = 0;
			} else if (marbles.isEmpty()) {
				//start
				marbles.add(currentMarble);
				lastIndex = 0;
			} else {
//				System.out.println("should be at " + index);
				index += 2;
				marbles.add(lastIndex +=2 , currentMarble);
			}
			
			if(currentPlayer < players) 
				currentPlayer++;				
			else 
				currentPlayer = 1;
			currentMarble++;
			
		} while(currentMarble <= lastMarble);
		
		score.entrySet().stream().max(Map.Entry.comparingByValue());

		return score.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
	}
	
	static class CircularDequeue<T> extends ArrayDeque<T> {
		
		void rotate(int num) {
			if (num == 0) {
				return;
				
				// 1 2 3 4 5
				// rotate 2
				// 5 1 2 3 4
				// 4 5 1 2 3
				
			} else if (num > 0) {
				for(int i = 0; i < num; i++) {					
					T t = this.removeLast();
//					System.out.println("Putting ");
					this.addFirst(t);
				}
			} else {
				
				// 1 2 3 4 5
				// rotate -2
				// 2 3 4 5 1
				// 3 4 5 1 2
				
				for(int i = num; i < 0; i++) {					
					T t = this.remove();
					this.addLast(t);
				}
			}
			
		}
		
	}
	
	public static long partTwo(int players, int lastMarble) {
	
	CircularDequeue<Integer> marbles = new CircularDequeue<>();
	Map<Integer, Long> score = new HashMap<>(); // player, score
	
	int currentPlayer = 0;
	int currentMarble = 0;
	int index =0;
	int lastIndex = 0;
	
	marbles.add(0);
	currentPlayer++;
	currentMarble++;
	
	do {
		
		if(currentMarble % 23 == 0) {
//			System.out.println(marbles);
			score.put(currentPlayer, score.getOrDefault(currentPlayer, 0L)+Long.valueOf(currentMarble));
			
//			System.out.println(marbles);

			//lastIndex -= 7;
//			if(lastIndex < 0) {
//				System.out.println("CC " + lastIndex);
//				// TODO WHY
//				lastIndex += marbles.size() + 1;
//			}
			marbles.rotate(-7);
//			System.out.println(marbles);
			Long removed = Long.valueOf(marbles.removeLast());
//			System.out.println(removed);
			
			score.put(currentPlayer, score.getOrDefault(currentPlayer, 0L)+removed);
			
		} else {
//			index += 2;
//			marbles.add(lastIndex +=2 , currentMarble);
			marbles.rotate(2);
			marbles.addLast(currentMarble);
		}
		
		System.out.println(marbles);
		
		if(currentPlayer < players) 
			currentPlayer++;				
		else 
			currentPlayer = 1;
		currentMarble++;
		
	} while(currentMarble <= lastMarble);
	
	score.entrySet().stream().max(Map.Entry.comparingByValue());

	return score.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
}
	
}
