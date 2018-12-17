package GameObjects;

public class TouchEvent 
{
	//Object of an event
	private float xPoint1, xPoint2, yPoint1, yPoint2;
	private TouchType touchType;
	
	public TouchEvent(float x1, float y1, TouchType type, float... coor2)
	{
		xPoint1 = x1;
		yPoint1 = y1;
		touchType = type;
		
		xPoint2 = coor2.length > 0 ? coor2[0] : 0;
		yPoint2 = coor2.length > 1 ? coor2[1] : 0;
	}

	public float getxPoint1() 
	{
		return xPoint1;
	}

	public float getxPoint2() 
	{
		return xPoint2;
	}

	public float getyPoint1() 
	{
		return yPoint1;
	}

	public float getyPoint2() 
	{
		return yPoint2;
	}

	public TouchType getTouchType() 
	{
		return touchType;
	}
	
}
