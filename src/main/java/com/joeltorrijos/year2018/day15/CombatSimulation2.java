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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CombatSimulation2 {
	
	private Tile[][] tiles;
	private List<Unit> units = new ArrayList<>();
	private Goblin goblin;
	
	public CombatSimulation2(String fileName) {
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
//						elves.add((Elf) unit);
					} else if (c == 'G') {
						unit = new Goblin(c, x ,y);
						units.add(unit);
						goblin = (Goblin) unit;
//						goblins.add((Goblin) unit);
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
					continue;
				}
				
				int newX = currentX + x;
				int newY = currentY + y;
				if((newY >= 0 && newY < tiles[newY].length) && 
						(newX >= 0 && newX < tiles.length)) {
					
					Tile potential = tiles[currentY+y][currentX + x];
					
					// Check 
					if(predicate.test(potential)) {
						adjacentTiles.add(new Point(newX, newY));
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
		List<Point> enemyCoordinates = new ArrayList<>();
		
		long goblinsLeft;
		long elvesLeft;
		
		
		int round = 0;
		do 
		{
//			System.out.println("Before sort");
//			units.forEach(System.out::println);	
			units = units.stream().sorted(Comparator.comparing(Unit::getY).thenComparing(Unit::getX)).collect(Collectors.toList());
//			System.out.println("After sort");
//			units.forEach(System.out::println);	
			
//			System.out.println("After Round " + (round -1) + "/Starting Round " + round);
//			printMap(tiles);
//			units.forEach(System.out::println);			

			System.out.println("z");
			
			Iterator<Unit> unitIt = units.iterator();
			
			while(unitIt.hasNext()) 
			{
				
				Unit unit = unitIt.next();
				System.out.println(unit);

				
				if(unit.isDead()) {
					continue;
				} else if (hasAdjacentEnemies(unit)) {
					System.out.println("serious");
					
					Unit target;
					if(chooseWhoToAttack(unit).isPresent()) {
						target = chooseWhoToAttack(unit).get();
						System.out.println("ATTACKING " + target);						
						unit.attack(target);
						
						if(target.getHp() <= 0) {
							target.die(tiles[target.getY()][target.getX()]);
							System.out.println(target + " died at " + round);
						}
					}
					
				} else {
					Point nextMove;
					System.out.println("oist");
					if(chooseWhereToMove(unit).isPresent()) {
						nextMove = chooseWhereToMove(unit).get();
//						System.out.println("Next Move for " + unit + ": " + nextMove);
						moveUnit(unit.getCoordinates(),nextMove);
						
						if (hasAdjacentEnemies(unit)) {
							
							Unit target;
							if(chooseWhoToAttack(unit).isPresent()) {
								target = chooseWhoToAttack(unit).get();
//								System.out.println("ATTACKING " + target);						
								unit.attack(target);
								
								if(target.getHp() <= 0) {
									target.die(tiles[target.getY()][target.getX()]);
									System.out.println(target + " died at " + round);
								}
							}
							
						}
						
						
					} else {
						System.out.println("Rip");
					}
					
					
				}
				System.out.println("what");
				
				goblinsLeft = units.stream().filter(unit1 -> unit1 instanceof Goblin).count();
				elvesLeft = units.stream().filter(unit1 -> unit1 instanceof Elf).count();
//				System.out.println("Condition: " + ((goblinsLeft <= 0 || elvesLeft <= 0) && unitIt.hasNext()));
//				System.out.println("\t" + (goblinsLeft <= 0 || elvesLeft <= 0));
//				System.out.println("\t" + unitIt.hasNext());
				if((goblinsLeft <= 0 || elvesLeft <= 0)) {
					System.out.println("Ending since there are more people left");
					units.forEach(System.out::println);
					int sum = units.stream().map(Unit::getHp).reduce(0, Integer::sum);
					
					return round * sum; 

				}
				
				

			}
			
			goblinsLeft = units.stream().filter(unit1 -> unit1 instanceof Goblin).count();
			elvesLeft = units.stream().filter(unit1 -> unit1 instanceof Elf).count();
			
			
			round++;
			
			// Remove the dead units
			units = units.stream().filter(Unit::isAlive).collect(Collectors.toList());
			
			goblinsLeft = units.stream().filter(unit -> unit instanceof Goblin).count();
			elvesLeft = units.stream().filter(unit -> unit instanceof Elf).count();
			
//		} while(goblinsLeft > 0 && elvesLeft > 0);
	} while(false);
		
		System.out.println("");
		System.out.println("Round " + round);
		printMap(tiles);
		units.forEach(System.out::println);
		int sum = units.stream().map(Unit::getHp).reduce(0, Integer::sum);
		
		long result = round * sum;
		
		System.out.println(result);
		
		return (int) result;
	
	}
	
	
	public Deque<Point> findShortestPath(Point start, Point end) {
		
		Predicate<Tile> isOpenCavern = (Tile tile) -> tile.getTileType() == TileType.OPEN_CAVERN;
		Predicate<Tile> occupiedByTarget = (Tile tile) -> (tile.isOccupied() && tile.getUnit().getCoordinates().equals(end));
		Predicate<Tile> isNotOccupied = (Tile tile) -> !tile.isOccupied();

		Set<AStarNode> OPEN = new LinkedHashSet<>();
		Set<AStarNode> CLOSED = new LinkedHashSet<>();
		Map<AStarNode, AStarNode> cameFrom = new HashMap<>();
		
		AStarNode S = new AStarNode(0, 0, start);
		AStarNode target = new AStarNode(0, 0, end);

		OPEN.add(S);
		cameFrom.put(S, null);
		AStarNode current = null;
		
		while(!OPEN.isEmpty()) {
//			System.out.println("Getting lowest F");
//			OPEN.forEach(n -> System.out.println("\t" + n));
//			current = OPEN.stream().min(Comparator.comparing(AStarNode::getF)
//												  .thenComparing(AStarNode::getY)
//												  .thenComparing(AStarNode::getX)).get();
			current = OPEN.stream().min(Comparator.comparing(AStarNode::getY)
												  .thenComparing(AStarNode::getX)
												  .thenComparing(AStarNode::getF)).get();
//			System.out.println("Current: " + current);
			OPEN.remove(current);
			CLOSED.add(current);
			
			if(current.equals(target)) {
				break;
			}
			
			for(Point adjacentTile : getAdjacentTiles(current.getCoordinates(), (occupiedByTarget.or(isNotOccupied)).and(isOpenCavern))) {
				int H = Math.abs(adjacentTile.x - end.x) + Math.abs(adjacentTile.y - end.y);
				int G = 1 + current.getG();
				AStarNode node = new AStarNode(H, G, adjacentTile);
				
				if(CLOSED.contains(node)) {
					continue;
				} else if (!OPEN.contains(node)) {
					OPEN.add(node);
					cameFrom.put(node, current);
				} else {
					// TODO
				}
			}
			
		}
		
		// Unreachable
		if(!current.equals(target)) {
			return null;
		}
		
		Deque<Point> path = new ArrayDeque<>();

		// does not include the origin
		while(cameFrom.get(current) != null) {
			path.addFirst(current.getCoordinates());
			AStarNode parent = cameFrom.get(current);
			current = parent;
		}
		
		return path;
	}
	
	public boolean isReachable(Point start ,Point end) {
		if (findShortestPath(start, end) == null) {
			return false;
		}
		return true;
	}
	
	public Optional<Point> chooseWhereToMove(Unit unit) {
		// Find Targets
		List<Unit> enemies = units.stream().filter(other -> unit.isEnemy(other)).collect(Collectors.toList());
//		System.out.println("enemies " + enemies.size());
//		enemies.forEach(System.out::println);
		
		// Get points where a unit can attack an enemy
		List<Point> target = enemies.stream()
				 					.map(enemy -> getAdjacentTiles(enemy.getCoordinates(), Tile.isNotOccupied.and(Tile.isOpenCavern)))
				 					.collect(ArrayList<Point>::new, (list, elem) -> { list.addAll( elem); }, ArrayList::addAll);
		
		List<Point> reachable = target.stream().filter(point -> isReachable(unit.getCoordinates(), point)).collect(Collectors.toList());
//		System.out.println("reachable " + reachable.size());
		
		//		reachable.forEach(System.out::println);
//		
		// Choose closest distance then closest to 0,0
		List<Path> paths = reachable.stream().map(point -> findShortestPath(unit.getCoordinates(), point))
				.collect(ArrayList<Path>::new, (list, path) -> list.add(new Path(path)), ArrayList::addAll);
		
		// TODO Round 25
//		System.out.println("Paths");
//		System.out.println("Paths: " + paths.size());
//		paths.forEach(System.out::println);
//		System.out.println("Sorted paths");
//		paths.stream().sorted(Comparator.comparingInt(Path::getSize).thenComparing(path -> path.getPath().peekLast().getY()).thenComparing(path -> path.getPath().peekLast().getX()).thenComparing(Path::getFirstY).thenComparing(Path::getFirstX)).forEach(System.out::println);
//		System.out.println(paths.stream().min(Comparator.comparingInt(Path::getSize).thenComparing(Path::getFirstY).thenComparing(Path::getFirstX)).get().getPath());
		
//		System.out.println();
//		return paths.stream().min(Comparator.comparingInt(Path::getSize).thenComparing(Path::getFirstY).thenComparing(Path::getFirstX)).map(path -> path.getPath().peek());
		return paths.stream().min((Comparator.comparingInt(Path::getSize).thenComparing(path -> path.getPath().peekLast().getY()).thenComparing(path -> path.getPath().peekLast().getX()).thenComparing(Path::getFirstY).thenComparing(Path::getFirstX))).map(path -> path.getPath().peek());
		
	}
	
	public Optional<Unit> chooseWhoToAttack(Unit unit) {
		// get enemies who are adjacent
		Optional<Unit> target = getAdjacentTiles(unit.getCoordinates(), unit.hasAdjacentEnemy())
									.stream()
									.map(point -> tiles[point.y][point.x])
									.map(Tile::getUnit)
									.min(Comparator.comparing(Unit::getHp).thenComparing(Unit::getY).thenComparing(Unit::getX));
		
//		System.out.println("Sorted Adjacent Enemies: ");
//		enemies.forEach(System.out::println);
		// sort by HP then closest to 0,0
		return target;
	}
	
	class Path {
		private Deque<Point> path;
		private int size;
		
		public Path(Deque<Point> path) {
			this.path = path;
			this.size = path.size();
			
		}

		public Deque<Point> getPath() {
			return path;
		}

		public int getSize() {
			return size;
		}

		
		public int getFirstY() {
			return path.peek().y;
		}
		
		public int getFirstX() {
			return path.peek().x;
		}

		@Override
		public String toString() {
			return "Path [path=" + path + ", size=" + size + "]";
		}
		
		
		
	}
	
	
	
//	public List<Point> getReachableEnemies(Unit unit) {
//		
//		Class<?> enemyClass = unit.getClass().equals(Elf.class) ? Goblin.class : Elf.class;
//		
//		List<Unit> enemies = units.stream().filter(unit1 -> unit1.getClass() == enemyClass).collect(Collectors.toList());
//		
////		System.out.println("Enemies of " + unit);
////		enemies.forEach(System.out::println);
//
//		List<Point> reachablePoints = new ArrayList<>();
//		
//		for(Unit enemy : enemies) {
//			findShortestPathNextStep(unit.getCoordinates(), enemy.getCoordinates())
//				.ifPresent(node -> { reachablePoints.add(node.getCoordinates());});
//		}
//		
//		return reachablePoints;
//	}
	
	public boolean hasAdjacentEnemies(Unit unit) {
		getAdjacentTiles(unit.getCoordinates(), unit.hasAdjacentEnemy()).forEach(System.out::println);
		return getAdjacentTiles(unit.getCoordinates(), unit.hasAdjacentEnemy()).size() > 0;
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
		boolean used = false;
		Point coordinates;

		public AStarNode(int h, int g, Point coordinates) {
			H = h;
			G = g;
			F = h + g;
			this.coordinates = coordinates;
		}
		

		@Override
		public String toString() {
			return "AStarNode [H=" + H + ", G=" + G + ", F=" + F + ", coordinate=" + coordinates + "]";
		}
		
		public int getG() {
			return this.G;
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
		
		public void setUsed(boolean used) {
			this.used = used;
		}
		
		public boolean isUsed() {
			return used;
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
