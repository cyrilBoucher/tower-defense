package Primitives;

public class Cross extends Primitive{

	private Line line1;
	private Line line2;
	
	public Cross(float x, float y, float width, float height, float[]color)
	{
		primColor = color;
		
		line1 = new Line(x-width/2f, y+height/2f, x+width/2f, y-height/2f);
		line2 = new Line(x-width/2f, y-height/2f, x+width/2f, y+height/2f);
	}
	
	public void draw(float[] mvpMatrix)
	{
		line1.draw(mvpMatrix);
		line2.draw(mvpMatrix);
	}
}
