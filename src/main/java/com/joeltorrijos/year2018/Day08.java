package com.joeltorrijos.year2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day08 {
	
	static int[] nums;
	static int total = 0;

	public static void main(String[] args) {
//		String fileName = "src/main/resources/2018/day08-test-input.txt";
		String fileName = "src/main/resources/2018/day08-input.txt";

		System.out.println(partOne(fileName));
		System.out.println(partTwo(fileName));
		
	}
	
	public static int partOne(String fileName) {
	
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
	
			String inputString = stream.collect(Collectors.joining(""));
			
			nums = Arrays.stream(inputString.split(" "))
						 .mapToInt(Integer::parseInt)
						 .toArray();
			int index = 0;
			
			buildTree(index);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return total;
	}
	
	public static int partTwo(String fileName) {
		
		TreeNode root = new TreeNode();
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			String inputString = stream.collect(Collectors.joining(""));
			
			nums = Arrays.stream(inputString.split(" "))
						 .mapToInt(Integer::parseInt)
						 .toArray();
			int index = 0;
			
			System.out.println("aye");
			
			root.buildTree2(index, root);
			
			System.out.println(root.value()); 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return root.value;
	}
	

	public static int buildTree(int index) {
		int numOfChildren = nums[index++];
		int numOfMetadata = nums[index++];
		
		for(int i = 0; i < numOfChildren; i++) {
			index = buildTree(index);
		}
		for(int i = 0; i < numOfMetadata; i++) {
			total += nums[index + i];
		}
		
		return index + numOfMetadata;
	}
	
	static class TreeNode {
		List<TreeNode> children = new ArrayList<>();
		List<Integer> metadata = new ArrayList<>();
		int value = 0;
		
		public int buildTree(int index) {
			int numOfChildren = nums[index++];
			int numOfMetadata = nums[index++];
			
			for(int i = 0; i < numOfChildren; i++) {
				TreeNode child = new TreeNode();
				index = buildTree(index);
			}
			for(int i = 0; i < numOfMetadata; i++) {
				System.out.println(nums[i]);
				total += nums[index + i];
			}
			
			return index + numOfMetadata;
		}
		
		public int buildTree2(int index, TreeNode node) {
			int numOfChildren = nums[index++];
			int numOfMetadata = nums[index++];
			
			for(int i = 0; i < numOfChildren; i++) {
				TreeNode child = new TreeNode();
				node.children.add(child);
				index = buildTree2(index, child);
			}
			
			for(int i = 0; i < numOfMetadata; i++) {
				node.metadata.add(nums[index + i] );
				if(numOfChildren == 0) {
					node.value += nums[index + i];
				} else {
					if(nums[index + i] <= children.size()) {
						System.out.print(nums[index + i] + "+ ");
						int temp = children.get((nums[index + i] -1)).value;
						node.value += temp;
					} else {
						node.value += 0;
					}
				}
			}
			System.out.println();
						
			return index + numOfMetadata;
		}
		
		public int value() {
			int value = 0;
			if(children.size() == 0) {
				for(Integer data: metadata) {
					value += data;
				}
			} else {
				for(Integer data: metadata) {
					if(data <= children.size()) {
						int temp = children.get(data -1).value;
						value += children.get(data -1).value();
					} else {
						value += 0;
					}
				}
			}
			
			return value;
		}
		
	}
}
