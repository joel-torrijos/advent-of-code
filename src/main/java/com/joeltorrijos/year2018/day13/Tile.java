package com.joeltorrijos.year2018.day13;

import java.util.Optional;

import com.joeltorrijos.year2018.day13.Cart;
import com.joeltorrijos.year2018.day13.TrackType;

public class Tile {
	private TrackType type;
	private Cart cart;
	
	public Tile(TrackType trackType, Cart cart) {
		this.type = trackType;
		this.cart = cart;
	}
	
	public TrackType getType() {
		return type;
	}

	public void setType(TrackType type) {
		this.type = type;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String display() {
		if(cart != null) {
			return cart.display();
		}
		return type.getDisplay();
	}
	
}
