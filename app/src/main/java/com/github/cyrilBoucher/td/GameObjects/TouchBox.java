package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public class TouchBox extends BoundingBox{

	public TouchBox(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public boolean collide(WorldPoint wp)
	{
		if(getBottomRightCorner().getY() < wp.getY()) return false;
		if(getBottomRightCorner().getX() < wp.getX()) return false;
		if(getTopLeftCorner().getY() > wp.getY()) return false;
		if(getTopLeftCorner().getX() > wp.getX()) return false;
		return true;
	}

	@Override
	public WorldPoint getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
}
