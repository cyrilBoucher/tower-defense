package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public interface IMovable {

	public void move(double deltaT);
	
	public WorldPoint getPosition();
}
