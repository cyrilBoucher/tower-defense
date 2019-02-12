package com.github.cyrilBoucher.td.Primitives;

import android.opengl.GLES20;

public class Line extends Primitive {
    
    public Line(float x1, float y1, float x2, float y2, float[] color) {
   	 
    	//Change the thickness of the line
        GLES20.glLineWidth(1.0f);
        
        drawOrder = new short[] { 0, 1 };
        
    	primColor = color;
    	
    	primCoords = new float[] {
	        x1,  y1, 1.0f,  
	        x2, y2, 1.0f };
    }
    
    public Line(float x1, float y1, float x2, float y2)
    {
    	this(x1,y1,x2,y2,new float[]{ 0.0f, 0.0f, 0.0f, 1.0f});
    }
    
    @Override
    public void drawElements()
    {
    	// Draw elements using lines
        GLES20.glDrawElements(
        		GLES20.GL_LINES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
    }
}
