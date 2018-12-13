package com.joeltorrijos.year2018.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class TrackSimulation {
	
	private Tile[][] tracks;
	private List<Cart> carts;
	
	public TrackSimulation(String fileName) {
		this.carts = new ArrayList<>();
		this.tracks = createTracks(fileName);

	}
	
	public Tile[][] createTracks(String fileName) {
		
		Tile[][] tracks;
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int lineLength = br.readLine().length();
			br.close();
			int lineCount = (int) stream.count();
			
			tracks = new Tile[lineCount][lineLength];
			
			String[] lines = Files.lines(Paths.get(fileName)).toArray(size -> new String[size]);
			
			
			for(int y = 0; y < lineCount; y++) {
				for(int x = 0; x < lineLength; x++) {
					char c = lines[y].charAt(x);
					Direction direction  = Direction.charToDirection(c);
					TrackType trackType = TrackType.charToTrackType(c);
					
					Cart cart = null;
					
					if (direction != null) {
						cart = new Cart(x, y, direction);
						carts.add(cart);
					}
					
					
					tracks[y][x] = new Tile(trackType, cart);
				}		
			}
			

			return tracks;
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	// Display
	public void printTracks() {
		for(int y = 0; y < tracks.length; y++) {
			for(int x = 0; x < tracks[y].length; x++) {
				System.out.print(tracks[y][x].display());
			}				
			System.out.println();
		}
		
	}
	
	public String simulate() {
		boolean crash = false;
		int i = 0;

		do {

			for(Cart cart : carts) {
				// Check origin
				int currentX = cart.getX(), currentY = cart.getY();
				int[] newCoordinates = cart.willMoveTo();
				Tile origin = tracks[currentY][currentX];
				Tile destination = tracks[newCoordinates[1]][newCoordinates[0]];

				if( !cart.move(newCoordinates, origin, destination)) {
					crash = true;
					StringBuilder sb = new StringBuilder();
					sb.append(newCoordinates[0]).append(",").append(newCoordinates[1]);
					return new String(sb);
				}

				
			}
			
			carts.sort(Comparator.comparingInt(Cart::getY)
					.thenComparingInt(Cart::getX));

			i++;
		} while(crash == false);
		
		return "";
	}
	
	public String simulate2() {
		
		do {

			Iterator<Cart> cartsIterator = carts.iterator();
			while(cartsIterator.hasNext()) {
				Cart cart = cartsIterator.next();
				
				// Skip iteration if cart has crashed
				if(cart.isCrashed()) {
					continue;
				}
				
				// Check origin
				int currentX = cart.getX(), currentY = cart.getY();
				int[] newCoordinates = cart.willMoveTo();
				Tile origin = tracks[currentY][currentX];
				Tile destination = tracks[newCoordinates[1]][newCoordinates[0]];

				if(!cart.move(newCoordinates,origin,destination)) {
					cartsIterator.remove();
				}

				
			}
			
			carts.removeIf(Cart::isCrashed);
			
			// Sort by y-coordinate then x-coordinate
			carts.sort(Comparator.comparingInt(Cart::getY)
					.thenComparingInt(Cart::getX));

		} while(carts.size() > 1);
		
		Cart lastCart = carts.get(0);
		StringBuilder sb = new StringBuilder();
		sb.append(lastCart.getX()).append(",").append(lastCart.getY());
		return new String(sb);
	}
	

	
}
