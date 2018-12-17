package Utilities;

public class MyVector2D 
{
    private float dX;
    private float dY;
    
    public float getDx() 
    {
		return dX;
	}

	public float getDy() 
	{
		return dY;
	}

    public MyVector2D(float _dx, float _dy)
    {
        dX = _dx;
        dY = _dy;
    }
    
    public MyVector2D(MyVector2D v)
    {
    	dX = v.getDx();
    	dY = v.getDy();
    }
    
    public MyVector2D()
    {
    	dX = 0;
    	dY = 0;
    }
    
    public static MyVector2D sum(MyVector2D v1, MyVector2D v2)
    {
    	MyVector2D temp;
    	temp = new MyVector2D(v1.getDx()+v2.getDx(), v1.getDy()+v2.getDy());
    	return temp;
    }
    
    public static float dotProduct(MyVector2D v1, MyVector2D v2)
    {
    	float temp;
    	temp = v1.getDx()*v2.getDx() + v1.getDy()*v2.getDy();
    	return temp;
    }
    
    public static MyVector2D invert(MyVector2D v1)
    {
    	MyVector2D temp = new MyVector2D(-v1.getDx(), -v1.getDy());
    	return temp;
    }
    
    public static MyVector2D scalarProduct(MyVector2D v1, float s)
    {
    	MyVector2D temp = new MyVector2D(v1.getDx()*s, v1.getDy()*s);
    	return temp;
    }
    
    public static MyVector2D scalarProduct(float s, MyVector2D v1)
    {
    	return scalarProduct(v1, s);
    }
    
    public float getAngle(MyVector2D v)
    {
    	this.normalize();
    	v.normalize();
    	
    	return (float) Math.acos(dotProduct(this, v));
    }
    
    public float getAngle(float dx, float dy)
    {
    	this.normalize();
    	MyVector2D v = new MyVector2D(dx,dy);
    	
    	return (float) Math.acos(dotProduct(this, v));
    }
    
    public float length()
    {
    	return (float) Math.sqrt((dX*dX + dY*dY));
    }
    
    public void normalize()
    {
        float l = length();
    	dX = dX/l;
    	dY = dY/l;
    }
    
    @Override 
    public String toString() 
    {
    	String temp;
    	temp = "(" + dX + ";" + dY + ")";
    	return temp;
    }
    
}
