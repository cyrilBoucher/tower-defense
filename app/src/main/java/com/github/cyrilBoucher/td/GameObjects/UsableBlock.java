package com.github.cyrilBoucher.td.GameObjects;

import java.util.ArrayList;

import Interfaces.IClickable;

import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public class UsableBlock extends Block implements ITouchable, IClickable{

	//_Debug : if no texture handling, uncomment
	//private Circle availableSpot;
	
	private Tower tower;
	private TouchBox touchBox;
	private boolean clicked;
	
	public UsableBlock(int x, int y) {
		super(x, y);

		//_Debug : if no texture handling, uncomment
		//availableSpot = new Circle(worldPosition.getX(), worldPosition.getY(), 1.0f, new float[]{0.83f,0.83f,0.83f,0.5f});
		
		touchBox = new TouchBox(worldPosition.getX(), worldPosition.getY(),1.0f, 1.0f);
	}

	@Override
	public void click(float x, float y) {
		
		clicked = true;
		
	}

	@Override
	public void multiClick(float x1, float y1, float x2, float y2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean collide(WorldPoint wp) {
		
		return touchBox.collide(wp);
	}
	
	@Override
	public void draw(float[] mvpMatrix)
	{
		//_Debug : if no texture handling, uncomment
		//block.draw(mvpMatrix);
		
		if(tower != null)
		{
			tower.draw(mvpMatrix);
		}
		
		//_Debug : if no texture handling, uncomment
		/*else
		{
			availableSpot.draw(mvpMatrix);
		}*/
	}
	
	@Override
	public void update(double deltaT)
	{
		if(tower != null)
		{
			tower.update(deltaT);
		}
	}

	public boolean towerProjectilesCollide(ICollisionable ico) {

		if(tower != null)
		{
			return tower.projectilesCollide(ico);
		}
		else
		{
			return false;
		}
	}
	
	public boolean fireTowerProjectileIfInRange(WorldPoint wp)
	{
		boolean inRange = false;
		if(tower != null)
		{
			if(tower.isInArea(wp) && tower.canAttack())
			{
				tower.attack(wp);
				inRange = true;
			}
		}
		return inRange;
	}

	@Override
	public CollisionableObject getCollisionableObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isClicked()
	{
		return clicked;
	}
	
	public void createTower(TowerType tt)
	{
		if(tower == null)
		{
			tower = new Tower(gridPosition.getColumn(), gridPosition.getRow(), tt);
		}
		
		clicked = false;
	}
	
	public void sellTower()
	{
		tower = null;
		
		clicked = false;
	}
	
	public void passPlayerResources(int p_resources)
	{
		tower.passPlayerResources(p_resources);
	}
	
	public void setClicked(boolean state)
	{
		clicked = state;
	}
	
	public Tower getTower()
	{
		return tower;
	}
	
	public boolean towerNeedsAnUpgrade()
	{
		return tower.needsAnUpgrade();
	}
	
	public boolean towerNeedsToBeSold()
	{
		return tower.isToBeSold();
	}
	
	public boolean towerMenuNeedsToBeClosed()
	{
		return tower.menuNeedsClosing();
	}
	
	public boolean towerMenuIsDisplayed()
	{
		return tower.isMenuDisplayed();
	}
	
	public ArrayList<IClickable> getTowerMenuClickables()
	{
		return tower.getMenuIcons();
	}
}
