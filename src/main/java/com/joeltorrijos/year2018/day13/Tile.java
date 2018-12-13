package com.joeltorrijos.year2018.day13;

import com.joeltorrijos.year2018.day13.Cart;
import com.joeltorrijos.year2018.day13.TrackType;

public class Tile {
	TrackType type;
	Cart cart;
	
	public Tile(TrackType trackType, Cart cart) {
		this.type = trackType;
		this.cart = cart;
	}
	
	public String display() {
		if(cart != null) {
			return cart.display();
		}
		return type.getDisplay();
	}
	
}
