package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Primitives.Sprite;
import com.github.cyrilBoucher.td.Utilities.MyVector2D;
import com.github.cyrilBoucher.td.Utilities.WorldPoint;
import android.opengl.Matrix;

public class Projectile extends GameObject implements ICollisionable, IMovable, IDrawable{
	
	private MyVector2D direction;

	private float speed = 2.0f;
	private double projectileTTL = 0.0;
	
	//_Debug : if no texture handling, uncomment
	//private Square projectile;
	
	private Sprite sProjectile;
	
	private int vitality;
	
	private CollisionableObject co;
	private MovableObject mo;
	private DrawableObject dO;
	
	public Projectile(WorldPoint towerPosition, WorldPoint targetPosition) 
	{
		worldPosition = towerPosition;	
		
		//_Debug : if no texture handling, uncomment
		//projectile = new Square(worldPosition.getX(), worldPosition.getY(), 0.1f, new float[]{0.0f,1.0f,0.0f,1.0f});
		
		sProjectile = new Sprite(worldPosition.getX(), worldPosition.getY(), 0.1f, 0.1f, new String[]{"projectile"});
		
		mo = new MovableObject(worldPosition.getX(),worldPosition.getY());
		dO = new DrawableObject();
		
		co = new CollisionableObject(worldPosition.getX(), worldPosition.getY());
		co.setObjCBox(0.1f, 0.1f, mo, dO);
		
		direction = new MyVector2D(targetPosition.getX() - towerPosition.getX(), targetPosition.getY() - towerPosition.getY());
		direction.normalize();
	}
	
	@Override
	public CollisionableObject getCollisionableObject() {
		return co;
	}

	@Override
	public void draw(float[] mvpMatrix) {
		Matrix.multiplyMM(dO.getObjMVPMatrix(), 0, mvpMatrix, 0, mo.getObjTransMatrix(), 0);
		
		//_Debug : if no texture handling, uncomment
        //projectile.draw(dO.getObjMVPMatrix());
		
		sProjectile.draw(dO.getObjMVPMatrix());
		
		//_Debug : uncomment for collision debugging
        //co.draw(mvpMatrix);
	}

	@Override
	public void move(double deltaT)
	{
		Matrix.translateM(mo.getObjTransMatrix(), 0, (float)(speed*deltaT*direction.getDx()), (float)(speed*deltaT*direction.getDy()), 0.0f);
	}
	
	@Override
	public void update(double deltaT) {
		move(deltaT);
		projectileTTL += deltaT;
	}

	@Override
	public boolean collide(ICollisionable ico) {
		return co.collide(ico);
	}
	
	public MovableObject getMo() {
		return mo;
	}

	@Override
	public WorldPoint getPosition() {
		float[] v_pos = worldPosition.getVector();
		float[] t_pos = new float[4];
		
		Matrix.multiplyMV(t_pos, 0, mo.getObjTransMatrix(), 0, v_pos, 0);
		
		return new WorldPoint(t_pos[0], t_pos[1]);
	}

	public double getProjectileTTL() {
		return projectileTTL;
	}
}
