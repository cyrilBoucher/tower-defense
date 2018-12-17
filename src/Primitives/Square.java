package Primitives;

public class Square extends Primitive{
	
    public Square(float x, float y, float width, float height, float[] color) 
    {
    	drawOrder = new short [] { 0, 1, 2, 0, 2, 3 };
    	
    	primColor = color;
    	
    	primCoords = new float[] {
                x-width/2f,  y+height/2f, 0.0f,   // top left
                x-width/2f, y-height/2f, 0.0f,   // bottom left
                 x+width/2f, y-height/2f, 0.0f,   // bottom right
                 x+width/2f,  y+height/2f, 0.0f }; // top right	
    }
    
    public Square(float x, float y, float size, float[] color) 
    {
    	this(x, y, size, size, color);
    }
    
    public Square(float x, float y, float size) 
    {
    	this(x, y, size, size, new float[] { 0.2f, 0.709803922f, 0.898039216f, 1.0f });
    }
}