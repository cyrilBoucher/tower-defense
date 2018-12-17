package Primitives;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;

public class Grid {
	
	private List<Line> mLineList;
	
	private int sizeSquare = 1;

    public Grid(int sizeX, int sizeY) {
    	 
    	//Initialise line list
    	mLineList = new LinkedList<Line>();
    	
    	int nbHorLines = sizeY;
    	int nbVerLines = sizeX;
    	
    	//Draw horizontal lines
    	for(int i = 0; i <= nbHorLines; i++)
    	{
    		float x1, x2, y1, y2;
    		 
            y2 = y1 =  sizeSquare*i;

            x1 = 0.0f;
            x2 = nbVerLines*sizeSquare;
                        
            mLineList.add(new Line(x1,y1,x2,y2,new float[]{1.0f,1.0f,1.0f,0.5f}));
    	}
    	
    	//Draw vertical lines  	
    	for(int i = 0; i <= nbVerLines; i++)
    	{
    		float x1, x2, y1, y2;
    		
            x2 = x1 = sizeSquare*i;

            y1 = 0;
            y2 = nbHorLines*sizeSquare;
                        
            mLineList.add(new Line(x1,y1,x2,y2,new float[]{1.0f,1.0f,1.0f,0.5f}));
    	}
    	
    }

    public void draw(float[] mvpMatrix) {
    	
    	// Draw lines
        for(Line l : mLineList)
        {
        	l.draw(mvpMatrix);
        }
    }
}
