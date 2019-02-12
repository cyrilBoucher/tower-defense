package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public interface ITouchable {

	public boolean collide(WorldPoint wp);
	
	public CollisionableObject getCollisionableObject();
}
