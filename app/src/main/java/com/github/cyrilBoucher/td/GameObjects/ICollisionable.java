package com.github.cyrilBoucher.td.GameObjects;


public interface ICollisionable {

	public boolean collide(ICollisionable ico);
	
	public CollisionableObject getCollisionableObject();
}
