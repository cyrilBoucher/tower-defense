package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Primitives.Square;
import com.github.cyrilBoucher.td.Primitives.Triangle;

public class Arrow extends GameObject implements IDrawable{

	public enum Orientation
	{
		Up,
		Down,
		Right,
		Left
	}
	
	private Orientation aOrientation;
	private Square base;
	private Triangle tip;
	
	public Arrow(float x, float y, float width, float height)
	{
		base = new Square(x, y-height/4,width/2,height/2,new float[]{0.0f,1.0f,0.0f,1.0f});
		tip = new Triangle(x,y+height/4,width,height/2, new float[]{0.0f,1.0f,0.0f,1.0f});
	}
	@Override
	public void draw(float[] mvpMatrix) {
		base.draw(mvpMatrix);
		tip.draw(mvpMatrix);
	}

	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub
		
	}

	
}
