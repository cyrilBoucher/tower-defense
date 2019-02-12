package com.github.cyrilBoucher.td.Primitives;

import java.util.LinkedList;
import java.util.List;

public class Circle {
    
	// number of triangles to represent the cricle
    static final int NB_TRIANGLES = 24;
    
    private List<Triangle> Triangles = new LinkedList<Triangle>();
    
    public Circle(float x, float y, float radius) {
    	
    	float a = 0f;
    	for(int i = 0; i < NB_TRIANGLES; i++)
    	{
    		float x1, y1, x2, y2, x3, y3;
    		
    		x1 = x;
    		y1 = y;
    		
    		
    		x2 = (float)((radius/2)*Math.sin(a) + x); 
    		y2 = (float)((radius/2)*Math.cos(a) + y);
    		
    		a += Math.toRadians(360f/NB_TRIANGLES);
    		
    		x3 = (float)((radius/2)*Math.sin(a) + x); 
    		y3 = (float)((radius/2)*Math.cos(a) + y);
    		
    		Triangles.add(new Triangle(x1,y1,x2,y2,x3,y3));
    	}		
    }

    public Circle(float x, float y, float radius, float[] color)
    {
    	float a = 0f;
    	for(int i = 0; i < NB_TRIANGLES; i++)
    	{
    		float x1, y1, x2, y2, x3, y3;
    		
    		x1 = x;
    		y1 = y;
    		
    		
    		x2 = (float)((radius/2)*Math.sin(a) + x); 
    		y2 = (float)((radius/2)*Math.cos(a) + y);
    		
    		a += Math.toRadians(360f/NB_TRIANGLES);
    		
    		x3 = (float)((radius/2)*Math.sin(a) + x); 
    		y3 = (float)((radius/2)*Math.cos(a) + y);
    		
    		Triangles.add(new Triangle(x1,y1,x2,y2,x3,y3,color));
    	}
    }
    
    public void draw(float[] mvpMatrix) {
    	
    	for(Triangle t : Triangles)
    	{
    		t.draw(mvpMatrix);
    	}
    }
}
