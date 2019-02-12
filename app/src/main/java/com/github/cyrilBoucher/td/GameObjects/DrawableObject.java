package com.github.cyrilBoucher.td.GameObjects;

import android.opengl.Matrix;

public class DrawableObject extends GameObject implements IDrawable{

	protected float[] objMVPMatrix;
	
	public DrawableObject()
	{
		objMVPMatrix = new float[16];
		Matrix.setIdentityM(objMVPMatrix, 0);
	}
	
	public float[] getObjMVPMatrix() {
		return objMVPMatrix;
	}
	
	public void setObjMVPMatrix(float[] objMVPMatrix) {
		this.objMVPMatrix = objMVPMatrix;
	}

	@Override
	public void draw(float[] mvpMatrix) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub
		
	}
}
