package Utilities;

public class MyVector3D 
{
    private double x, y, z;
    
    public double getX() 
    {
		return x;
	}

	public double getY() 
	{
		return y;
	}
	
	public double getZ() 
	{
		return z;
	}

    public MyVector3D(double _x, double _y, double _z)
    {
        x = _x;
        y = _y;
        z = _z;
    }
    
    public MyVector3D(MyVector3D v)
    {
    	x = v.getX();
    	y = v.getY();
    	z = v.getZ();
    }
    
    public MyVector3D()
    {
    	x = 0;
    	y = 0;
    	z = 0;
    }
    
    public static MyVector3D sum(MyVector3D v1, MyVector3D v2)
    {
    	MyVector3D temp;
    	temp = new MyVector3D(v1.getX()+v2.getX(), v1.getY()+v2.getY(), v1.getZ()+v2.getZ());
    	return temp;
    }
    
    public static double dotProduct(MyVector3D v1, MyVector3D v2)
    {
    	double temp;
    	temp = v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
    	return temp;
    }
    
    public static MyVector3D invert(MyVector3D v1)
    {
    	MyVector3D temp = new MyVector3D(-v1.getX(), -v1.getY(), -v1.getZ());
    	return temp;
    }
    
    public static MyVector3D scalarProduct(MyVector3D v1, double s)
    {
    	MyVector3D temp = new MyVector3D(v1.getX()*s, v1.getY()*s, v1.getZ()*s);
    	return temp;
    }
    
    public static MyVector3D scalarProduct(double s, MyVector3D v1)
    {
    	return scalarProduct(v1, s);
    }
    
    public static MyVector3D crossProduct(MyVector3D v1, MyVector3D v2)
    {
    	MyVector3D temp;
    	temp = new MyVector3D(v1.getY() * v2.getZ() - v1.getZ() * v2.getY(),
    						  v1.getZ() * v2.getX() - v1.getX() * v2.getZ(),
    						  v1.getX() * v2.getY() - v1.getY() * v2.getX());
    	return temp;
    }
    
    public double length()
    {
    	return Math.sqrt(x*x + y*y + z*z);
    }
    
    public void normalize()
    {
    	double l = length();
    	x = x/l;
    	y = y/l;
    	z = z/l;
    }
    
    @Override 
    public String toString() 
    {
    	String temp;
    	temp = "(" + x + ";" + y + ";" + z + ")";
    	return temp;
    }
    
}
