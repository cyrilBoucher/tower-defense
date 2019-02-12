package com.github.cyrilBoucher.td.Primitives;

public class WSquare extends WPrimitive{
	
	public WSquare(float x, float y, float width, float height, float[]color)
	{
		// Change thickness of the circle
	    //GLES20.glLineWidth(5.0f);
	    
		primColor = color;
		
		drawOrder = new short [] { 0, 1, 2, 3 };
		
		primCoords = new float[] {
                x-width/2f,  y+height/2f, 0.0f,   // top left
                x-width/2f, y-height/2f, 0.0f,   // bottom left
                 x+width/2f, y-height/2f, 0.0f,   // bottom right
                 x+width/2f,  y+height/2f, 0.0f }; // top right
	}
	
	public WSquare(float x, float y, float size, float[] color)
	{
		this(x,y,size,size,color);
	}
}
