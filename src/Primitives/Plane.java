package Primitives;

import android.util.Log;


public class Plane extends Primitive
{
	private float mX;
	private float mY;
	private float mWidth;
	private float mHeight;
	
    public Plane(float x, float y, float width, float height, float[] color)
    {
    	primColor = color;
    	
    	drawOrder = new short[] { 0, 1, 2, 0, 2, 3 };
    	
    	primCoords = new float[] {
                x,  y+height, 0.0f,   // top left
                x, y, 0.0f,           // bottom left
                x+width, y, 0.0f,    // bottom right
                x+width,  y+height, 0.0f }; // top right
    	
    	mX = x;
    	mY = y;
    	mWidth = width;	
    	mHeight = height;
    }
    
    public void updatePlane(float ratio)
    {
    	float width = mWidth*ratio;
    	
    	primCoords = new float[] {
                mX,  mY+mHeight, 0.0f,   // top left
                mX, mY, 0.0f,           // bottom left
                mX+width, mY, 0.0f,    // bottom right
                mX+width,  mY+mHeight, 0.0f }; // top right
    	
    	vertexBuffer.put(primCoords);
        vertexBuffer.position(0);
    	
    }
}