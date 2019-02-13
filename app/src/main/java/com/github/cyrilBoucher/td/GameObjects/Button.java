package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Interfaces.IClickable;

import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public class Button extends GameObject implements IDrawable, ITouchable, IClickable{

	protected TouchBox touchBox;
	
	public Button(float x, float y, float width, float height)
	{
		touchBox = new TouchBox(x, y, width, height);
	}

	@Override
	public void draw(float[] mvpMatrix) {
		
		//_Debug : uncomment for collision debugging
		//touchBox.draw(mvpMatrix);
	}

	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click(float x, float y) {
	}

	@Override
	public void multiClick(float x1, float y1, float x2, float y2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean collide(WorldPoint wp) {
		
		return touchBox.collide(wp);
	}

	@Override
	public CollisionableObject getCollisionableObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
