package GameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import Interfaces.IClickable;
import Primitives.Sprite;
import Utilities.Conversion;
import Utilities.WorldPoint;

public class Tower extends GameObject implements IDrawable, ITouchable, IClickable {
	
	//_Debug : if no texture handling, uncomment
	//private WCircle tower;
	
	private Sprite sTower;
	
	private double reloadTime = 2.5;
	private double projectileTimeToLive = 8.0;
	private double time = 10.0;
	private boolean canAttack = false;
	
	//Tower Modification events
	private boolean clicked = false;
	private boolean needsUpgrade = false;
	private boolean needsToBeSold = false;
	private boolean menuNeedsToBeClosed = false;
	
	private int maxLevel;
	private int level;
	private TowerType type;
	private float areaRadius;
	
	private TouchBox touchBox;
	
	private TowerModificationMenu towerMMenu;
	
	private ConcurrentLinkedQueue<Projectile> projectileList;
    

	public Tower(int x, int y, TowerType tt)
	{
		//_Debug : if no texture handling, uncomment
		//tower = new WCircle(x,y,1.0f);
		
		areaRadius = 2.0f;
		
		worldPosition = new WorldPoint(Conversion.mapToWorld(x), Conversion.mapToWorld(y));
		
		sTower = new Sprite(worldPosition.getX(), worldPosition.getY(),0.49f,1.0f,new String[]{"tower"});
		
		touchBox = new TouchBox(worldPosition.getX(), worldPosition.getY(), 1.0f, 1.0f);
		
		type = tt;
		
		projectileList = new ConcurrentLinkedQueue<Projectile>();
		
		level = 1;
		maxLevel = 2;
	}
	
	public void attack(WorldPoint enemyPos)
	{
		projectileList.add(new Projectile(worldPosition, enemyPos));
		canAttack = false;
	}
	
	public void upgrade()
	{
		if(level < maxLevel)
		{
			level++;
		}
		
		clicked = false;
		needsUpgrade = false;
	}
	
	public boolean isInArea(WorldPoint pos)
	{
		float dist = (float) Math.sqrt((pos.getX() - worldPosition.getX())*(pos.getX() - worldPosition.getX()) + (pos.getY() - worldPosition.getY())*(pos.getY() - worldPosition.getY()));
		
		if(dist <= areaRadius)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public TowerType getType() {
		return type;
	}
	
	public int getLevel() {
		return level;
	}

	
	@Override
	public void draw(float[] mvpMatrix)
	{
		//_Debug : if no texture handling, uncomment
        //tower.draw(mvpMatrix);
		
		sTower.draw(mvpMatrix);
        
        for(Projectile p : projectileList)
        {
        	p.draw(mvpMatrix);
        }
        
        if(towerMMenu != null)
        {
        	towerMMenu.draw(mvpMatrix);
        }
	}
	
	@Override
	public void update(double deltaT)
	{
		if(time >= reloadTime)
		{
			canAttack = true;
			
			time = 0.0;
		}
		else
		{
			time += deltaT;
		}
		
		//Updating projectiles if tower has shot
		for(Projectile p : projectileList)
		{
			if(p != null)
			{
				p.update(deltaT);
				if(p.getProjectileTTL() >= projectileTimeToLive)
				{
					projectileList.remove(p);
				}
			}
		}
		
		if(towerMMenu != null)
		{
			towerMMenu.update(deltaT);
		}
	}

	public boolean canAttack() {
		return canAttack;
	}
	
	public boolean projectilesCollide(ICollisionable ico)
	{
		for(Iterator<Projectile> itp = projectileList.iterator(); itp.hasNext();)
		{
			Projectile p = itp.next();
			
			if(p.collide(ico))
			{
				itp.remove();
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void click(float x, float y) {

		if(towerMMenu == null)
		{
			towerMMenu = new TowerModificationMenu(this);
			clicked = true;
		}
		else
		{
			askForClosing();
		}
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
	public CollisionableObject getCollisionableObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void closeMenu()
	{
		towerMMenu = null;
		
		menuNeedsToBeClosed = false;
	}
	
	public void passPlayerResources(int p_resources)
	{
		if(towerMMenu != null)
		{
			towerMMenu.setPlayerResources(p_resources);
		}
	}
	
	public boolean hasReachedMaxLevel()
	{
		if(level >= maxLevel)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public boolean isClicked()
	{
		return clicked;
	}
	
	public void setClicked(boolean state)
	{
		clicked = state;
	}
	
	public boolean needsAnUpgrade()
	{
		return needsUpgrade;
	}
	
	public boolean isToBeSold()
	{
		return needsToBeSold;
	}
	
	public boolean menuNeedsClosing()
	{
		return menuNeedsToBeClosed;
	}
	
	public void askForUpgrade()
	{
		needsUpgrade = true;
	}
	
	public void askForSell()
	{
		needsToBeSold = true;
	}
	
	public void askForClosing()
	{
		menuNeedsToBeClosed = true;
	}
	
	public boolean isMenuDisplayed()
	{
		if(towerMMenu == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public ArrayList<IClickable> getMenuIcons()
	{
		return towerMMenu.getIcons();
	}
}
