package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public class CollisionBox extends BoundingBox{
	
	public CollisionBox(float x, float y, float width, float height, MovableObject mo, DrawableObject dO) {
		super(x, y, width, height, mo, dO);
	}
	
	public CollisionBox(float x, float y, float width, float height, DrawableObject dO) {
		super(x, y, width, height, dO);
	}
	
	public CollisionBox(float x, float y, float width, float height, MovableObject mo) {
		super(x, y, width, height, mo);
	}
	
	public CollisionBox(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public boolean collide(CollisionBox _cbox)
	{
		if(getBottomRightCorner().getY() < _cbox.getTopLeftCorner().getY()) return false;
		if(getBottomRightCorner().getX() < _cbox.getTopLeftCorner().getX()) return false;
		if(getTopLeftCorner().getY() > _cbox.getBottomRightCorner().getY()) return false;
		if(getTopLeftCorner().getX() > _cbox.getBottomRightCorner().getX()) return false;
		return true;
	}

	@Override
	public WorldPoint getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
}
