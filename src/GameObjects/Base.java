package GameObjects;

import Primitives.WCircle;
import Utilities.Conversion;
import Utilities.GridPoint;
import Utilities.WorldPoint;
import android.util.Log;


public class Base extends GameObject implements ICollisionable, IDrawable {

	private int lives;
	private int maxLives;
	private boolean isDestroyed;
	
	//_Debug : if no texture handling, uncomment
	//private WCircle base;
	
	private LifeBar lifeBar;
	private CollisionableObject co;
	private DrawableObject dO;
	
	public Base(GridPoint pos) 
	{
		gridPosition = pos;
		worldPosition = new WorldPoint(Conversion.mapToWorld(pos.getColumn()), Conversion.mapToWorld(pos.getRow()));
		
		//_Debug : if no texture handling, uncomment
		//base = new WCircle(pos.getColumn(),pos.getRow(),1.0f);
		
		lifeBar = new LifeBar(worldPosition.getX() - 0.5f, worldPosition.getY() + 0.8f, 1.0f, 0.1f, new float[]{0.0f,1.0f,0.0f,1.0f});
		maxLives = 5;
		lives = 5;
		isDestroyed = false;	
		dO = new DrawableObject();
		co = new CollisionableObject(worldPosition.getX(), worldPosition.getY());
		co.setObjCBox(1.0f, 1.0f, dO);
	}
	
	public void takeDamage()
	{
		lives--;
		if (lives <= 0)
			destroy();
	}
	
	public void destroy()
	{
		isDestroyed = true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	@Override
	public void draw(float[] mvpMatrix) {
		//_Debug : if no texture handling, uncomment
        //base.draw(mvpMatrix);
		
        lifeBar.draw(mvpMatrix);
        
        //_Debug : uncomment for collision debugging
        //co.draw(mvpMatrix);
	}

	@Override
	public void update(double deltaT) {

		float ratio = (float)lives/(float)maxLives;
		
		lifeBar.updateLifeBar(ratio);
	}

	@Override
	public boolean collide(ICollisionable ico) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CollisionableObject getCollisionableObject() {
		
		return co;
	}
	
	public int getLives()
	{
		return lives;
	}

}
