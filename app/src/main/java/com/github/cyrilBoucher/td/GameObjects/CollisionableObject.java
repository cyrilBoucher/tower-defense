package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public class CollisionableObject extends GameObject implements ICollisionable, IDrawable{

	protected CollisionBox objCBox;
	
	public CollisionableObject(float x, float y)
	{
		worldPosition = new WorldPoint(x,y);
	}

	public CollisionBox getObjCBox() {
		return objCBox;
	}

	public void setObjCBox(CollisionBox objCBox) {
		this.objCBox = objCBox;
	}
	
	public void setObjCBox(float s, MovableObject mo) {
		this.objCBox =  new CollisionBox(worldPosition.getX(), worldPosition.getY(), s, s, mo);
	}
	
	public void setObjCBox(float w, float h, MovableObject mo) {
		this.objCBox =  new CollisionBox(worldPosition.getX(), worldPosition.getY(), w, h, mo);
	}
	
	public void setObjCBox(float w, float h, DrawableObject dO) {
		this.objCBox =  new CollisionBox(worldPosition.getX(), worldPosition.getY(), w, h, null, dO);
	}
	
	public void setObjCBox(float w, float h, MovableObject mo, DrawableObject dO) {
		this.objCBox =  new CollisionBox(worldPosition.getX(), worldPosition.getY(), w, h, mo, dO);
	}

	@Override
	public boolean collide(ICollisionable ico) {
		
		return objCBox.collide(ico.getCollisionableObject().getObjCBox());
	}

	@Override
	public void draw(float[] mvpMatrix) 
	{
		objCBox.draw(mvpMatrix);
	}

	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CollisionableObject getCollisionableObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
