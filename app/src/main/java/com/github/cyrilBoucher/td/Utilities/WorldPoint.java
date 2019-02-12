package com.github.cyrilBoucher.td.Utilities;

public class WorldPoint {

	private float x;
	private float y;
	
	private float[] vector;
	
	public WorldPoint (float _x, float _y)
	{
		x = _x;
		y = _y;
		
		vector = new float[4];
		
		vector[0] = x;
		vector[1] = y;
		vector[2] = 0.0f;
		vector[3] = 1.0f;
	}
	
	public float[] getVector()
	{
		return vector;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
		this.vector[0] = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
		this.vector[1] = y;
	}
	
}
