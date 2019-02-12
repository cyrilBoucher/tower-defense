package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Utilities.WorldPoint;
import android.opengl.Matrix;

public class MovableObject extends GameObject implements IMovable{

	protected float[] objTransMatrix;
	
	public MovableObject(float x, float y)
	{
		worldPosition = new WorldPoint(x,y);
		
		objTransMatrix = new float[16];
		
		Matrix.setIdentityM(objTransMatrix, 0);
	}
	
	public float[] getObjTransMatrix() {
		return objTransMatrix;
	}
	
	public void setObjTransMatrix(float[] objTransMatrix) {
		this.objTransMatrix = objTransMatrix;
	}
	
	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(double deltaT) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WorldPoint getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
}
