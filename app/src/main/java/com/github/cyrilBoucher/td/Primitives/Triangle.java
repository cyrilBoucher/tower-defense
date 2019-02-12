package com.github.cyrilBoucher.td.Primitives;


public class Triangle extends Primitive {

    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3, float[] color) {
    	
    	drawOrder = new short [] { 0, 1, 2};
    	
    	primColor = color;
    	
    	primCoords = new float[] {
    	            // in counterclockwise order:
    	            x1, y1, 0.0f,   // top
    	            x2, y2, 0.0f,   // bottom left
    	            x3, y3, 0.0f    // bottom right
    	};
    }
    
    public Triangle(float x, float y, float width, float height, float[] color)
    {
    	this(x, y+height/2f, x-width/2f, y-height/2f, x+width/2f, y-height/2f,color);
    }
    
    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3)
    {
    	this(x1, y1, x2, y2, x3, y3,new float[] { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f });
    }
    
    public Triangle(float x, float y, float width, float height)
    {
    	this(x, y+height/2f, x-width/2f, y-height/2f, x+width/2f, y-height/2f);
    }
}
