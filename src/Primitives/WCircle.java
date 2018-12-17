package Primitives;

import Utilities.Conversion;
import android.opengl.GLES20;

public class WCircle extends WPrimitive {
    
 // number of vertices and number of coordinates per vertex in this array
    static final int NB_VERTICES = 24;
    
    public WCircle(int x, int y, float radius) {
    	
    	// Change thickness of the circle
        //GLES20.glLineWidth(5.0f);
    	
    	primColor = new float[] { 0.0f, 0.0f, 1.0f, 1.0f };
    	
    	drawOrder = new short[NB_VERTICES];
    	
    	primCoords = new float[NB_VERTICES*COORDS_PER_VERTEX];
    	
    	float a = 0f;
    	for(int i = 0; i < NB_VERTICES*COORDS_PER_VERTEX; i+=COORDS_PER_VERTEX)
    	{
    		primCoords[i] = (float)((radius/2)*Math.sin(a) + Conversion.mapToWorld(x)); 
    		primCoords[i+1] = (float)((radius/2)*Math.cos(a) + Conversion.mapToWorld(y)); 
    		primCoords[i+2] = 0f; 

    		a += Math.toRadians(360f/NB_VERTICES);
    		
    		drawOrder[i/COORDS_PER_VERTEX] = (short)(i/COORDS_PER_VERTEX);
    	}	
    	
    	//init();
    }
}
