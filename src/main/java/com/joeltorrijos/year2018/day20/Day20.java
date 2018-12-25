package com.joeltorrijos.year2018.day20;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Day20 {
	
	static Graph graph = new Graph();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		partOne("^N(E|W)N$");
//		partOne("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$");
//		partOne("^ENWWW(NEEE|SSE(EE|))$");
	}
	
	public static void partOne(String fileName) {
		
		Node start = new Node(new Point(0,0));
		graph.addNode(start);
		buildGraph(fileName, 0, start);
		
		graph.getNodeConnections();
	}
	
	public static int buildGraph(String phone, int index, Node start) {
		
		Node origin = start;
		Node current = start;
		Node newRoom = null;
		
		while(index < phone.length()) {
			
			char c = phone.charAt(index++);
			
			switch(c) {
				case '(':
//					index++;
					index = buildGraph(phone, index, current);
					break;
				case '|':
					current = origin;
					System.out.println("Starting back with " + current );
					break;
				case '^':
				case '$':
					System.out.println("Freak nasty ");
					break;
				case ')':
					return index;
				case 'N':
					newRoom = new Node(new Point(current.point.x, current.point.y - 1));
					graph.addNode(newRoom);
					graph.addEdge(current, newRoom);
					System.out.println(newRoom + " from " + current);
					current = newRoom;
					break;
				case 'E':
					newRoom = new Node(new Point(current.point.x + 1, current.point.y));
					graph.addNode(newRoom);
					graph.addEdge(current, newRoom);
					System.out.println(newRoom + " from " + current);
					current = newRoom;
					break;
				case 'W':
					newRoom = new Node(new Point(current.point.x - 1, current.point.y));
					graph.addNode(newRoom);
					graph.addEdge(current, newRoom);
					System.out.println(newRoom + " from " + current);
					current = newRoom;
					break;
				case 'S':
					newRoom = new Node(new Point(current.point.x, current.point.y + 1));
					graph.addNode(newRoom);
					graph.addEdge(current, newRoom);
					System.out.println(newRoom + " from " + current);
					current = newRoom;
					break;
					
				default:
//					from = index -2;
					break;		
			}

		}
		
		return index;
	}
	
	static class Graph {
		List<Node> nodes;
		
		public Graph() {
			this.nodes = new ArrayList<>();
		}
		
		public void addNode(Node node) {
			nodes.add(node);
		}
		
		public void addEdge(Node from, Node to) {
			Edge edge = new Edge(from, to);
			from.connections.add(edge);
			to.connections.add(edge);
		}
		
		public void getNodeConnections()
		{
			nodes.forEach(node -> node.connections.forEach(System.out::println));
		}
		
	}
	
	static class Node {
		Point point;
		List<Edge> connections;
		boolean visited;
		
		public Node(Point point) {
			this.point = point;
			this.connections = new ArrayList<>();
			visited = false;
		}
		
		
		
	}
	
	static class Edge{
		Node from;
		Node to;
		
		public Edge(Node from, Node to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public String toString() {
			return "Edge [from=" + from.point + ", to=" + to.point + "]";
		}
		
		
		
		
	}

}
