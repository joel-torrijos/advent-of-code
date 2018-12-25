package com.joeltorrijos.year2018.day20;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day20 {
	
	public static void main(String[] args) {
		String fileName = "src/main/resources/2018/day20-input.txt";
		
		String regex = parseInput(fileName);
		
		System.out.println(partOne(regex));
		System.out.println(partTwo(regex));
	}
	
	public static int partOne(String regex) {
		
		Graph graph = new Graph();
		Node start = new Node(new Point(0,0));
		graph.addNode(start);
		graph.buildGraph(regex, 0, start);
		
		Map<Node, Node> cameFrom = graph.bfs(graph.nodes.get(0));
		
		List<Integer> distances = new ArrayList<>();
		
		for(int i = 1; i < graph.nodes.size(); i++) {
			
			Node destination = graph.nodes.get(i);
			Deque<Node> path = graph.getPath(cameFrom, destination);
			int dist = path.size();
			
			distances.add(dist);
			
		}
		
		return distances.stream().max(Integer::compare).get();
		
	}
	
	public static int partTwo(String regex) {
		
		Graph graph = new Graph();
		Node start = new Node(new Point(0,0));
		graph.addNode(start);
		graph.buildGraph(regex, 0, start);
		
		Map<Node, Node> cameFrom = graph.bfs(graph.nodes.get(0));
		
		List<Integer> distances = new ArrayList<>();
		
		for(int i = 1; i < graph.nodes.size(); i++) {
			
			Node destination = graph.nodes.get(i);
			Deque<Node> path = graph.getPath(cameFrom, destination);
			int dist = path.size();
			
			distances.add(dist);
		}
		
		return (int) distances.stream().filter(x -> x >= 1000).count();
	}
	
	public static String parseInput(String fileName) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			String input = stream.collect(Collectors.joining(""));
			
			return input;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	static class Graph {
		List<Node> nodes;
		
		public Graph() {
			this.nodes = new ArrayList<>();
		}
		
		public void addNode(Node node) {
			nodes.add(node);
		}
		
		public void addNeighbor(Node from, Node to) {
			from.addNeighbor(to);
			to.addNeighbor(from);
		}
		
		public void getNodeConnections()
		{
			nodes.forEach(node -> System.out.println(node + "\n\n"));
		}
		
		public Map<Node, Node> bfs(Node start) {
			Deque<Node> frontier = new ArrayDeque<>();
			frontier.addLast(start);
			Map<Node, Node> cameFrom = new HashMap<>();
			cameFrom.put(start, null);
			
			while(!frontier.isEmpty()) {
				Node current = frontier.poll();
				for(Node next : current.neighbors) {
					if (!cameFrom.containsKey(next)) {
						frontier.addLast(next);
						cameFrom.put(next, current);
					}
				}
			}

			return cameFrom;
		}
		
		public Deque<Node> getPath(Map<Node, Node> cameFrom, Node destination) {
			Deque<Node> path = new ArrayDeque<>();
			
			while(cameFrom.get(destination) != null) {
				Node current = cameFrom.get(destination);
				path.addFirst(current);
				destination = current;
			}
			
			return path;
		}
		
		public int buildGraph(String regex, int index, Node start) {
			
			Node origin = start;
			Node current = start;
			Node newRoom = null;
			
			while(index < regex.length()) {
				
				char c = regex.charAt(index++);
				
				switch(c) {
					case '(':
						index = buildGraph(regex, index, current);
						break;
					case '|':
						current = origin;
						break;
					case '^':
					case '$':
						break;
					case ')':
						return index;
					case 'N':
						Point newPoint = new Point(current.point.x, current.point.y - 1);
						if(this.containsPoint(newPoint)) {
							current = this.getPoint(newPoint);
						} else {
							newRoom = new Node(newPoint);
							this.addNode(newRoom);
							this.addNeighbor(current, newRoom);
							current = newRoom;
						}
						break;
					case 'E':
						newPoint = new Point(current.point.x + 1, current.point.y);
						if(this.containsPoint(newPoint)) {
							current = this.getPoint(newPoint);
						} else {
							newRoom = new Node(newPoint);
							this.addNode(newRoom);
							this.addNeighbor(current, newRoom);
							current = newRoom;
						}
						break;
					case 'W':
						newPoint = new Point(current.point.x - 1, current.point.y);
						if(this.containsPoint(newPoint)) {
							current = this.getPoint(newPoint);
						} else {
							newRoom = new Node(newPoint);
							this.addNode(newRoom);
							this.addNeighbor(current, newRoom);
							current = newRoom;
						}
						break;
					case 'S':
						newPoint = new Point(current.point.x, current.point.y + 1);
						if(this.containsPoint(newPoint)) {
							current = this.getPoint(newPoint);
						} else {
							newRoom = new Node(newPoint);
							this.addNode(newRoom);
							this.addNeighbor(current, newRoom);
							current = newRoom;
						}
						break;
					default:
						break;		
				}
			}
			
			return index;
		}
		
		public boolean containsPoint(Point point) {
			return this.nodes.stream().filter(node -> node.point.equals(point)).findFirst().isPresent();
		}
		
		public Node getPoint(Point point) {
			return this.nodes.stream().filter(node -> node.point.equals(point)).findFirst().get();
		}
		
	}
	
	static class Node {
		Point point;
		List<Node> neighbors;
		boolean visited;
		
		public Node(Point point) {
			this.point = point;
			this.neighbors = new ArrayList<>();
			visited = false;
		}
		
		public void addNeighbor(Node neighbor) {
			this.neighbors.add(neighbor);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			neighbors.forEach(neighbor -> {
				sb.append("\n\t(").append(neighbor.point.x).append(",").append(neighbor.point.y).append(")");
				});
			
			return "Node [point=" + point + ",\nneighbors=" + sb + ",\nvisited=" + visited + "]";
		}
	}
}
