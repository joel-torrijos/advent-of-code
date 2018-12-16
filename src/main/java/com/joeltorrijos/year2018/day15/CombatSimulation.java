package com.joeltorrijos.year2018.day15;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CombatSimulation {
	
	private Tile[][] tiles;
	private List<Unit> units = new ArrayList<>();
	// TODO change
	private List<Goblin> goblins = new ArrayList<>();
	private List<Elf> elves = new ArrayList<>();
	
	public CombatSimulation(String fileName) {
//		this.carts = new ArrayList<>();
		this.tiles = generateMap(fileName);

	}
	
	public Tile[][] generateMap(String fileName) {
		
		Tile[][] tiles;
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int lineLength = br.readLine().length();
			br.close();
			int lineCount = (int) stream.count();
			
			tiles = new Tile[lineCount][lineLength];
			
			String[] lines = Files.lines(Paths.get(fileName)).toArray(size -> new String[size]);
			
			
			for(int y = 0; y < lineCount; y++) {
				for(int x = 0; x < lineLength; x++) {
					char c = lines[y].charAt(x);
					TileType tileType = TileType.charToTileType(c);
					
					// TODO
					Unit unit = null;
					
					if (c == 'E') {
						unit = new Elf(c, x, y);
						units.add(unit);
						elves.add((Elf) unit);
					} else if (c == 'G') {
						unit = new Goblin(c, x ,y);
						units.add(unit);
						goblins.add((Goblin) unit);
						// TODO add to list
					}
					
					tiles[y][x] = new Tile(tileType, unit);
//					System.out.print(tileType.getDisplay());
				}		
//				System.out.println();
			}
			

			return tiles;
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	// Display
	public void printMap(Tile[][] tiles) {
		for(int y = 0; y < tiles.length; y++) {
			for(int x = 0; x < tiles[y].length; x++) {
				System.out.print(tiles[y][x].getDisplay());
			}				
			System.out.println();
		}
		
	}
	
	public List<Point> getAdjacentTiles(Point point, Predicate<Tile> predicate) {
		List<Point> adjacentTiles = new ArrayList<>();
		int currentX = point.x;
		int currentY = point.y;
		
		// TODO Ignore diagonal
		for(int y = -1; y < 2; y++) {
			for(int x = -1; x < 2; x++) {
				
				if(x == y || (x == 1 && y == -1) || (x == -1 && y == 1))
				{
//					System.out.println("diagonal ignored");
					continue;
				}
				
				int newX = currentX + x;
				int newY = currentY + y;
				if((newY >= 0 && newY < tiles[newY].length) && 
						(newX >= 0 && newX < tiles.length)) {
					
					Tile potential = tiles[currentY+y][currentX + x];
					
					// Check 
//					if(potential.getTileType() == TileType.OPEN_CAVERN) {
					if(predicate.test(potential)) {
						adjacentTiles.add(new Point(newX, newY));
//						System.out.println(x + "," + y);
					}
				}
			}			
		}
		
		return adjacentTiles;
	}
	
	public int simulate() {
		
		int totalHp = 0;
		
		Predicate<Tile> hasAdjacentOpenSpaces = tile -> tile.getTileType() == TileType.OPEN_CAVERN && !tile.isOccupied();
		Predicate<Tile> hasAdjacentGoblin = (tile) -> tile.isOccupied() && tile.getUnit() instanceof Goblin ;
		Predicate<Tile> hasAdjacentElves = (tile) -> tile.isOccupied() && tile.getUnit() instanceof Elf;
		List<Point> openSpaceCoordinates = new ArrayList<>();
		List<Point> enemyCoordinates = new ArrayList<>();;
	
		printMap(tiles);
		
		System.out.println();
		int turn = 0;
		do 
		{
			turn++;
			printMap(tiles);
			System.out.println("========== TURN " + turn + "================");
//			units.forEach( unit -> System.out.println("\t" + unit.getCoordinates()));
			
			units.sort(Comparator.comparing(Unit::getX).thenComparing(Unit::getY));
//			System.out.println("After Sort");
//			units.forEach( unit -> System.out.println("\t" + unit.getCoordinates()));
			for(Unit unit: units) {
				System.out.println(unit + "'s turn");
				
				openSpaceCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentOpenSpaces);

				
				if(unit instanceof Elf) {
					enemyCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentGoblin);

				} else if(unit instanceof Goblin) {
					enemyCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentElves);

				}

				
				
				if(openSpaceCoordinates.isEmpty() && enemyCoordinates.isEmpty()) {
					System.out.println("~ CANT DO ANYTHING");
				} else if (!enemyCoordinates.isEmpty()) {
					
					if(unit instanceof Elf) {
						enemyCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentGoblin);
	
					} else if(unit instanceof Goblin) {
						enemyCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentElves);
					}
					

					List<Tile> enemyTiles = enemyCoordinates.stream().collect(ArrayList<Tile>::new, (list, element) -> {
						list.add(tiles[element.y][element.x]);
					}, ArrayList::addAll);
					
					Unit target = enemyTiles.stream().map(Tile::getUnit).min(Comparator.comparing(Unit::getHp)
							.thenComparing(Unit::getY).thenComparing(Unit::getX)).get();
					System.out.println("   " + unit + " attacked " + target);
					unit.attack(target);
					
					if(target.getHp() <= 0) {
						target.die(tiles[target.getY()][target.getX()]);
						goblins.remove(target);
						elves.remove(target);
						System.out.println(target + " died. " + target.isDead());
					}
					
					
	//				.forEach(System.out::println);
					
					// TODO Get adjacent enemies
					// TODO Get lowest HP then by reading order
					// TODO Remove if dead
					
				} else {
					
					
					List<Point> reachableEnemies = getReachableEnemies(unit);

					Optional<Point> opt = getMoveFromShortestPath(reachableEnemies);
					if(opt.isPresent()) {
						Point nextMove = opt.get();
						System.out.println("   MOVE TO (" + nextMove.getX() + "," + nextMove.getY() + ")");
						moveUnit(unit.getCoordinates(), nextMove);
						
						if(unit instanceof Elf) {
							enemyCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentGoblin);

						} else if(unit instanceof Goblin) {
							enemyCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentElves);

						}

						
						
						if(openSpaceCoordinates.isEmpty() && enemyCoordinates.isEmpty()) {
							System.out.println("~ CANT DO ANYTHING");
						} else if (!enemyCoordinates.isEmpty()) {
							
							if(unit instanceof Elf) {
								enemyCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentGoblin);
			
							} else if(unit instanceof Goblin) {
								enemyCoordinates = getAdjacentTiles(unit.getCoordinates(), hasAdjacentElves);
							}
							

							List<Tile> enemyTiles = enemyCoordinates.stream().collect(ArrayList<Tile>::new, (list, element) -> {
								list.add(tiles[element.y][element.x]);
							}, ArrayList::addAll);
							
							Unit target = enemyTiles.stream().map(Tile::getUnit).min(Comparator.comparing(Unit::getHp)
									.thenComparing(Unit::getY).thenComparing(Unit::getX)).get();
							System.out.println("   " + unit + " attacked " + target);
							unit.attack(target);
							
							if(target.getHp() <= 0) {
								target.die(tiles[target.getY()][target.getX()]);
								goblins.remove(target);
								elves.remove(target);
								System.out.println(target + " died. " + target.isDead());
							}
						}
						
						
						
					} else {
						System.out.println("DOING NOTHING");
					}
				}
				
				
				System.out.println();
	//			findShortestPath(unit.getCoordinates(), goblin.getCoordinates());
			}
			
		
		// Remove dead
		units = units.stream().filter(Unit::isAlive).collect(Collectors.toList());
		System.out.println();
		
		System.out.println("After Turn " + turn);
//		units.stream().sorted(Comparator.comparing(Unit::getY).thenComparing(Unit::getX)).forEach(System.out::println);
		units.forEach(System.out::println);
		System.out.println("Change" );
		units.sort(Comparator.comparing(Unit::getY).thenComparing(Unit::getX));
		units.forEach(System.out::println);

		
		totalHp = units.stream().map(Unit::getHp).reduce(0, Integer::sum);

		System.out.println("Outcome: " + turn + " x " + totalHp + ": " + (turn*totalHp));
		
		if(goblins.size() < 1 || elves.size() < 1) {
			return totalHp * turn;
		}

		} while(goblins.size() >= 1 && elves.size() >= 1);

		return totalHp * turn;
	}
	
	public Optional<AStarNode> findShortestPathNextStep(Point start, Point end) {
		
		Set<AStarNode> openNodes = new HashSet<>();
		Set<AStarNode> closedNodes = new LinkedHashSet<>();
		Deque<AStarNode> closedDeque = new ArrayDeque<>();
		List<AStarNode> nodes = new ArrayList<>();
		
		int H;
		int G = 0; 
		AStarNode S = new AStarNode(0,0, start,null);
		AStarNode target = new AStarNode(0, 0, end,null);
		Predicate<Tile> isOpenCavern = (Tile tile) -> tile.getTileType() == TileType.OPEN_CAVERN;
		Predicate<Tile> occupiedByTarget = (Tile tile) -> (tile.isOccupied() && tile.getUnit().getCoordinates().equals(end));
		Predicate<Tile> isNotOccupied = (Tile tile) -> !tile.isOccupied();

		
		do {
			S.setUsed(true);
			List<Point> adjacentTiles = getAdjacentTiles(S.getCoordinates(), (occupiedByTarget.or(isNotOccupied)).and(isOpenCavern));
			G++;
			for(Point adjacentTile : adjacentTiles) {
				 H = Math.abs(adjacentTile.x - end.x) + Math.abs(adjacentTile.y - end.y);
				 AStarNode node = null;
				 if(G == 1) {
					 node = new AStarNode(H,G, adjacentTile,null);
					 
				 } else {
					 node = new AStarNode(H,G, adjacentTile,S);
				 }
				 if(closedNodes.contains(node)) {
//					 System.out.println("Already in closed set");
					 continue;
				 } else if (openNodes.contains(node)) {
//					TODO
				 } else {
					 openNodes.add(node);					 
				 }
				 // if it exists in closed/open
			}
//			openNodes.forEach(System.out::println);
			Optional<AStarNode> opt = openNodes.stream()
					.min(Comparator.comparing(AStarNode::getF)
							.thenComparing(AStarNode::getMostRecent)
							.thenComparing(AStarNode::getY)
							.thenComparing(AStarNode::getX));
			if(opt.isPresent()) {
				S = opt.get();
				openNodes.remove(S);
				closedNodes.add(S);	
				closedDeque.add(S);
//				System.out.println("OPEN NODES");
//				openNodes.forEach(node -> System.out.println("\t" + node.getCoordinates()));
			} else {
				System.out.println("WHAT " + S.getCoordinates());
				System.out.println("UNREACHABLE");
				return Optional.empty();
			}
		} while (!closedNodes.contains(target) || openNodes.isEmpty());
		
		if(openNodes.isEmpty()) {
//			System.out.println("unreachable");
		}
		
		AStarNode node = null;
		Iterator<AStarNode> closedIter = closedNodes.iterator();
		while(closedIter.hasNext()) {
			node = closedIter.next();
			if(node.equals(target)) {
//				System.out.println("ZZZ " + node);
				break;
				
			}
		}
		
		return Optional.of(node.getOldestParent());
	
	}
	
	
	public List<Point> getReachableEnemies(Unit unit) {
		
		Class<?> enemyClass = unit.getClass().equals(Elf.class) ? Goblin.class : Elf.class;
		
		List<Unit> enemies = units.stream().filter(unit1 -> unit1.getClass() == enemyClass).collect(Collectors.toList());
		
//		System.out.println("Enemies of " + unit);
//		enemies.forEach(System.out::println);

		List<Point> reachablePoints = new ArrayList<>();
		
		for(Unit enemy : enemies) {
			findShortestPathNextStep(unit.getCoordinates(), enemy.getCoordinates())
				.ifPresent(node -> { reachablePoints.add(node.getCoordinates());});
		}
		
		return reachablePoints;
	}
	
	public Optional<Point> getMoveFromShortestPath(List<Point> reachablePoints) {
		return reachablePoints.stream().min(Comparator.comparing(Point::getY).thenComparing(Point::getX));
	}
	
	public void moveUnit(Point sourcePoint, Point destinationPoint) {
		Tile sourceTile = tiles[(int) sourcePoint.getY()][(int) sourcePoint.getX()];
		Tile destinationTile = tiles[(int) destinationPoint.getY()][(int) destinationPoint.getX()];
		
		Unit movingUnit = sourceTile.getUnit();
		sourceTile.setUnit(null);
		movingUnit.setCoordinates(destinationPoint);
		destinationTile.setUnit(movingUnit);
	}
	
	
	class AStarNode {
		int H; // Estimated Score from this node to destination
		int G; // Cost from origin to this node
		int F; // Score
		int mostRecent;
		boolean used = false;
		Point coordinates;
		AStarNode parent;
		
		public AStarNode(int h, int g, Point coordinates, AStarNode parent) {
			H = h;
			G = g;
			F = h + g;
			this.coordinates = coordinates;
			this.mostRecent = G * -1;
			this.parent = parent;
		}
		

		@Override
		public String toString() {
			return "AStarNode [H=" + H + ", G=" + G + ", F=" + F + ", coordinate=" + coordinates + ", parent=" + parent
					+ ", mostRecent= " + mostRecent + "]";
		}
		
		
		public double getX() {
			return coordinates.getX();
		}
		
		public double getY() {
			return coordinates.getY();
		}
		
		public int getF() {
			return F;
		}
		
		public Point getCoordinates() {
			return coordinates;
		}
		
		public int getMostRecent() {
			return mostRecent;
		}
		
		public void setUsed(boolean used) {
			this.used = used;
		}
		
		public boolean isUsed() {
			return used;
		}
		
		public AStarNode getOldestParent() {
			if(this.parent == null) {
				return this;
			}
			return parent.getOldestParent();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AStarNode other = (AStarNode) obj;
			if (coordinates == null) {
				if (other.coordinates != null)
					return false;
			} else if (!coordinates.equals(other.coordinates))
				return false;
			return true;
		}

		
	}
	


	
}
