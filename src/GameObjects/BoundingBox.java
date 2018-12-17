package GameObjects;

import Primitives.WSquare;
import Utilities.WorldPoint;
import android.opengl.Matrix;

public abstract class BoundingBox extends GameObject implements IMovable, IDrawable{

	private MovableObject boundObj;
	private DrawableObject drawObj;
	
	private WSquare bBox;
	
	private WorldPoint topLeftCorner;
	private WorldPoint bottomRightCorner;
	
	public BoundingBox(float x, float y, float width, float height, MovableObject mo, DrawableObject dO) 
	{
		worldPosition = new WorldPoint(x,y);
		
		topLeftCorner = new WorldPoint(x-width/2.0f, y-height/2.0f);
		bottomRightCorner = new WorldPoint(x+width/2.0f, y+height/2.0f);
		
		boundObj = mo;
		drawObj = dO;
		
		bBox = new WSquare(x,y,width, height, new float[]{1.0f,0.0f,0.0f,1.0f});
	}
	
	public BoundingBox(float x, float y, float width, float height) 
	{
		this(x, y, width, height, null, null);
	}
	
	public BoundingBox(float x, float y, float width, float height, DrawableObject dO) 
	{
		this(x, y, width, height, null, dO);
	}
	
	public BoundingBox(float x, float y, float width, float height, MovableObject mo) 
	{
		this(x, y, width, height, mo, null);
	}
	
	public WorldPoint getTopLeftCorner() 
	{
		if(boundObj != null)
		{
			float[] v_pos = topLeftCorner.getVector();
			float[] t_pos = new float[4];
			
			Matrix.multiplyMV(t_pos, 0, boundObj.getObjTransMatrix(), 0, v_pos, 0);
			
			return new WorldPoint(t_pos[0], t_pos[1]);
		}
		else
		{
			return topLeftCorner;
		}
			
	}

	public WorldPoint getBottomRightCorner() 
	{
		if(boundObj != null)
		{
			float[] v_pos = bottomRightCorner.getVector();
			float[] t_pos = new float[4];
			
			Matrix.multiplyMV(t_pos, 0, boundObj.getObjTransMatrix(), 0, v_pos, 0);
			
			return new WorldPoint(t_pos[0], t_pos[1]);
		}
		else
		{
			return bottomRightCorner;
		}
	}

	@Override
	public void draw(float[] mvpMatrix)
	{
		if(boundObj != null)
		{
			Matrix.multiplyMM(drawObj.getObjMVPMatrix(), 0, mvpMatrix, 0, boundObj.getObjTransMatrix(), 0);
			bBox.draw(drawObj.getObjMVPMatrix());
		}
		else
		{
			bBox.draw(mvpMatrix);
		}
	}
	
	@Override
	public void update(double deltaT)
	{
		
	}

	@Override
	public void move(double deltaT) {
		// TODO Auto-generated method stub
		
	}
}
