package GameObjects;

import Primitives.Square;
import Utilities.Conversion;
import Utilities.GridPoint;
import Utilities.WorldPoint;


public class Block extends GameObject implements IDrawable{

	//_Debug : if no texture handling, uncomment
	//protected Square block;
    
	public Block(int x, int y)
	{
		gridPosition = new GridPoint(y,x);
		worldPosition = new WorldPoint(Conversion.mapToWorld(x), Conversion.mapToWorld(y));
		
		//_Debug : if no texture handling, uncomment
		//block = new Square(worldPosition.getX(), worldPosition.getY(), 1.0f, new float[] {0.0f, 0.0f, 1.0f, 0.5f});
	}
	
	@Override
	public void draw(float[] mvpMatrix)
	{
		//_Debug : if no texture handling, uncomment
		//block.draw(mvpMatrix);
	}
	
	@Override
	public void update(double deltaT)
	{
		
	}
}
