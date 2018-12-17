package GameObjects;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import Utilities.WorldPoint;

import com.example.td.Map;

public class EnemyWave implements IDrawable, ICollisionable
{
	private ConcurrentLinkedQueue<Enemy> mEnemyList;

	private int nbGruntEnemy;
	private int nbEliteEnemy;
	private int EliteEnemyCreated = 0;
	private int GruntEnemyCreated = 0;
	private double timeBeforeNext;
	private double currentTime = 0.0;
	
	private boolean resourcesAvailable = false;
	
	private int resources;
	
	private Map map;
	
	public EnemyWave(int nbg_enemy, int nbe_enemy, double tbn, Map m)
	{
		timeBeforeNext = tbn;
		nbGruntEnemy = nbg_enemy;
		nbEliteEnemy = nbe_enemy;
		map = m;
		mEnemyList = new ConcurrentLinkedQueue<Enemy>();
	}
	
	public void update(double deltaT) 
	{	
		for(Iterator<Enemy> ite = mEnemyList.iterator(); ite.hasNext();)
		{
			Enemy e = ite.next();
			
			e.update(deltaT);
			
			if(!e.isAlive)
			{
				resources += 50;
				resourcesAvailable = true;
				ite.remove();
			}
		}
		
		if(GruntEnemyCreated < nbGruntEnemy)
		{
			currentTime += deltaT;
			
			if(currentTime >= timeBeforeNext)
			{
				mEnemyList.add(new GruntEnemy(map.getStartPos().getColumn(),map.getStartPos().getRow(), map));
				GruntEnemyCreated++;
				currentTime = 0.0;
			}
		}
		
		if(EliteEnemyCreated < nbEliteEnemy)
		{
			currentTime += deltaT;
			
			if(currentTime >= timeBeforeNext)
			{
				mEnemyList.add(new EliteEnemy(map.getStartPos().getColumn(),map.getStartPos().getRow(), map));
				EliteEnemyCreated++;
				currentTime = 0.0;
			}
		}
	}
	
	public void isInArea(Tower t)
	{
		for(Enemy e : mEnemyList)
		{
			WorldPoint e_pos = e.getPosition();
			if(t.isInArea(e_pos) && t.canAttack())
			{
				t.attack(e_pos);
			}
		}
	}
	
	public boolean isDead()
	{
		if(mEnemyList.size() == 0 && (GruntEnemyCreated + EliteEnemyCreated) == (nbGruntEnemy + nbEliteEnemy))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public ConcurrentLinkedQueue<Enemy> getEnemyList()
	{	
		return mEnemyList;
	}

	@Override
	public void draw(float[] mvpMatrix) 
	{
		for(Enemy e : mEnemyList)
        {
        	e.draw(mvpMatrix);
        }
	}

	@Override
	public boolean collide(ICollisionable ico) 
	{
		for(Iterator<Enemy> ite = mEnemyList.iterator(); ite.hasNext();)
		{
			Enemy e = ite.next();
			
			if(e.collide(ico))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public CollisionableObject getCollisionableObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getResources()
	{
		int resourcesToReturn = resources;
		resources = 0;
		resourcesAvailable = false;
		return resourcesToReturn;
	}
	
	public boolean resourcesAreAvailabe()
	{
		return resourcesAvailable;
	}
}
