package com.github.cyrilBoucher.td.GameObjects;

import Interfaces.IAnimatable;
import com.github.cyrilBoucher.td.Primitives.Sprite;
import com.github.cyrilBoucher.td.Utilities.Conversion;
import com.github.cyrilBoucher.td.Utilities.GridPoint;
import com.github.cyrilBoucher.td.Utilities.WorldPoint;
import android.opengl.Matrix;

import com.github.cyrilBoucher.td.Map;


public class Enemy extends GameObject implements ICollisionable, IMovable, IDrawable, IAnimatable{
	
	//_Debug : if no texture handling, uncomment
	//private Square enemy;
	
	//Animation
	protected Sprite[] aSprites = new Sprite[4];
	protected int sIndex = 0;
	private int fCount = 0;
	private int framesToSkip = 8;
	protected Sprite sEnemy;
	private LifeBar lifebar;
	
	private CollisionableObject co;
	private MovableObject mo;
	private DrawableObject dO;
	
	protected int maxVitality;
	protected int vitality;
	public boolean isAlive;
	protected float speed;
	
	private int dX;
	private int dY;
	
	private Map map;
    
	public Enemy(int x, int y, Map m)
	{
		worldPosition = new WorldPoint(Conversion.mapToWorld(x), Conversion.mapToWorld(y));
		gridPosition = new GridPoint(y, x);
		
		map = m;
		
		isAlive = true;
		
		//_Debug : if no texture handling, uncomment
		//enemy = new Square(worldPosition.getX(),worldPosition.getY(),0.5f);
		
		lifebar = new LifeBar(worldPosition.getX() - 0.25f, worldPosition.getY() - 0.4f, 0.5f, 0.05f, new float[]{1.0f,0.0f,0.0f,1.0f});
		
		mo = new MovableObject(worldPosition.getX(),worldPosition.getY());
		dO = new DrawableObject();
		
		co = new CollisionableObject(worldPosition.getX(), worldPosition.getY());
		co.setObjCBox(0.5f, 0.5f, mo, dO);
	}
	
	public WorldPoint getPosition()
	{
		float[] v_pos = worldPosition.getVector();
		float[] t_pos = new float[4];
		
		Matrix.multiplyMV(t_pos, 0, mo.getObjTransMatrix(), 0, v_pos, 0);
		
		return new WorldPoint(t_pos[0], t_pos[1]);
	}
	
	public GridPoint getGridPosition()
	{	
		return gridPosition;
	}
	
	@Override
	public CollisionableObject getCollisionableObject() {
		return co;
	}
	
	public void advance(int new_r, int new_c)
	{
		gridPosition.setRow(new_r);
		gridPosition.setColumn(new_c);
	}
	
	public int takeDamage(int damage)
	{
		vitality -= damage;
		int points = 10;
		
		if(vitality <= 0)
		{
			die();
			points = 80;
		}
		
		return points;
	}
	
	public void die()
	{
		isAlive = false;
	}
	
	public void sacrifice()
	{
		isAlive = false;
	}
	
	@Override
	public void move(double deltaT)
	{
		float[] newT = new float[16];
		Matrix.setIdentityM(newT, 0);
		
		float[] v_pos = worldPosition.getVector();
		float[] t_pos = new float[4];
		
		Matrix.multiplyMV(t_pos, 0, mo.getObjTransMatrix(), 0, v_pos, 0);
		
		WorldPoint wp = this.getPosition();
		WorldPoint cp = new WorldPoint(Conversion.mapToWorld(gridPosition.getColumn()+dX), Conversion.mapToWorld(gridPosition.getRow()+dY));
		
		float dist = (float) Math.sqrt((cp.getX() - wp.getX())*(cp.getX() - wp.getX()) + (cp.getY() - wp.getY())*(cp.getY() - wp.getY()));
		
		if(dist <= 1.0f)
		{
			GridPoint dest = map.getDestination(this);
			
			if(dest == null){
				return;
			}
			
			dX = dest.getColumn()-gridPosition.getColumn();
			dY = dest.getRow()-gridPosition.getRow();
		}
		Matrix.translateM(newT, 0, (float)(speed*deltaT)*dX, (float)(speed*deltaT)*dY, 0.0f);
		Matrix.multiplyMM(mo.getObjTransMatrix(), 0, mo.getObjTransMatrix(), 0, newT, 0);

		Matrix.multiplyMV(t_pos, 0, mo.getObjTransMatrix(), 0, v_pos, 0);
		
		advance((int)t_pos[1]+1, (int)t_pos[0]+1);
	}
	
	@Override
	public void draw(float[] mvpMatrix)
	{
		Matrix.multiplyMM(dO.getObjMVPMatrix(), 0, mvpMatrix, 0, mo.getObjTransMatrix(), 0);
		
		//_Debug : if no texture handling, uncomment
        //enemy.draw(dO.getObjMVPMatrix());
		
		sEnemy.draw(dO.getObjMVPMatrix());
        lifebar.draw(dO.getObjMVPMatrix());
        
        //_Debug : uncomment for collision debugging
        //co.draw(mvpMatrix);
	}
	
	@Override
	public void update(double deltaT)
	{
		move(deltaT);
		
		run(deltaT);
		
		float ratio = (float)vitality/(float)maxVitality;
		
		lifebar.updateLifeBar(ratio);
	}

	@Override
	public boolean collide(ICollisionable ico) {
		
		if(co.collide(ico))
		{
			if(ico instanceof Projectile)
			{
				takeDamage(1);
				return true;
			}
			else if(ico instanceof Base)
			{
				sacrifice();
				return true;
			}
			else
			{
				return false;
			}
				
		}
		else
		{
			return false;
		}
	}

	@Override
	public void run(double deltaT) {
		
		if(fCount == 0)
		{
			sIndex = (sIndex+1)%aSprites.length;
			sEnemy = aSprites[sIndex];
		}
		
		fCount = (fCount+1)%framesToSkip;
	}
}
