package com.github.cyrilBoucher.td.GameObjects;

import android.opengl.Matrix;
import com.github.cyrilBoucher.td.Primitives.Plane;
import com.github.cyrilBoucher.td.Primitives.WSquare;
import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public class LifeBar extends GameObject implements IDrawable{

	private WSquare outline;
	private Plane fill;
	
	private float[] scaleMatrix;
	
	public LifeBar(float x, float y, float width, float height, float[] color)
	{
		worldPosition = new WorldPoint(x,y);
		outline = new WSquare(x+width/2, y+height/2, width, height, new float[]{1.0f,1.0f,1.0f,1.0f});
		fill = new Plane(x, y, width, height, color);
		
		scaleMatrix = new float[16];
	}
	
	public void updateLifeBar(float ratio)
	{
		fill.updatePlane(ratio);
		Matrix.scaleM(scaleMatrix, 0, 2.0f, 0.0f, 0.0f);
	}

	@Override
	public void draw(float[] mvpMatrix) {

		outline.draw(mvpMatrix);
		fill.draw(mvpMatrix);
		
	}

	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub
		
	}
}
