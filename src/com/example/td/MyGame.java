
package com.example.td;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import GameObjects.Base;
import GameObjects.Button;
import GameObjects.DefeatMenu;
import GameObjects.Enemy;
import GameObjects.EnemyWave;
import GameObjects.IDrawable;
import GameObjects.ITouchable;
import GameObjects.MainMenu;
import GameObjects.Menu;
import GameObjects.MenuBar;
import GameObjects.Merchant;
import GameObjects.Player;
import GameObjects.Projectile;
import GameObjects.TouchEvent;
import GameObjects.Tower;
import GameObjects.TowerType;
import GameObjects.UnusableBlock;
import GameObjects.UsableBlock;
import GameObjects.VictoryMenu;
import Interfaces.IClickable;
import Interfaces.IDragable;
import Primitives.Grid;
import TextToTexture.GLText;
import Utilities.WorldPoint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.SystemClock;
import android.util.Log;

public class MyGame implements IDrawable{
	
	public enum GameState {
		LOADING_MENU,
		START_MENU,
		LOADING_GAME,
		PLAYING,
		LOADING_VICTORY_MENU,
		VICTORY,
		LOADING_DEFEAT_MENU,
		DEFEAT
	}

	private GameCamera mCamera;
	
	private AssetManager mMapFilesManager;
	
	private TouchEventHandler mTouchEvtHdlr;
	
	private Player mPlayer;
	private GLText mText;
	private Menu mMenu;
	private MenuBar mMenuBar;
	private Merchant mMerchant;
	private Map mMap;
	private Grid mGrid;
	private List<UnusableBlock> mUnusableBlockList;
	private List<UsableBlock> mUsableBlockList;
	private Base mBase;
	private ConcurrentLinkedQueue<Tower> mTowerList;
	private EnemyWave CurrentWave;
	private ConcurrentLinkedQueue<Projectile> mProjectileList;
	private ConcurrentLinkedQueue<IClickable> mClickableObjects;
	private ConcurrentLinkedQueue<IDragable> mDragableObjects;
	private Button mPlayButton;
	private Button mMenuButton;
	private MySoundPlayer mSound;
	private boolean menuPlayedOnce = false;
	private boolean loadOnce = false;
	private boolean BGMplayedOnce = false;
	private boolean victoryPlayedOnce = false;
	private boolean defeatPlayedOnce = false;
	private int mWavesLeft = 0;
	
	private static GameState currentState; 
	
	private double previousTime = SystemClock.uptimeMillis()/1000.0f;
	
	public MyGame(MySoundPlayer sound)
	{
		currentState = GameState.LOADING_MENU;
		
		mSound = sound;
		mSound.LoadingIntro();
		
		mCamera = new MenuCamera();
		
		mClickableObjects = new ConcurrentLinkedQueue<IClickable>();
		mDragableObjects = new ConcurrentLinkedQueue<IDragable>();
	}
	
	public void update() {
		
		double deltaT = SystemClock.uptimeMillis()/1000.0f - previousTime;
		previousTime = SystemClock.uptimeMillis()/1000.0f;
		
		if(currentState == GameState.LOADING_MENU)
		{
			if(mSound.isMediaPlaying() == true)
			{
				mSound.StopInGame();
			}
			
			mClickableObjects.clear();
			
			//Initialising menu	
			mMenu = new MainMenu(5.0f, 5.0f, 10.0f, 6.0f);
			mClickableObjects.addAll(mMenu.getButtons());
			
			((MenuCamera) mCamera).set();
			currentState = GameState.START_MENU;
			
			if(mSound.loaded == true)
	        {
				currentState = GameState.START_MENU;
	        }
		}
		else if(currentState == GameState.START_MENU)
		{
			loadOnce = false;
			if(mSound.isMediaPlaying() == true)
			{
				mSound.StopInGame();
			}
			
			if(menuPlayedOnce == false && mSound.loaded == true)
			{
				mSound.PlaySound("MM_MainMenuIntro", 0, 0.3f);
				menuPlayedOnce = true;
				mSound.loaded = false;
			}
			
		}
		else if(currentState == GameState.LOADING_GAME)
		{
			if(loadOnce == false)
			{
				mSound.StopSound();
				
				//Freeing useless resources
				mClickableObjects.removeAll(mMenu.getButtons());
				mMenu = null;
				
				//Loading resources
				setMap("Map1.txt");
				
				mPlayer = new Player();
				
				mMerchant = new Merchant();
				
				mCamera = new InGameCamera(mMap.getCamPosX(), mMap.getCamPosY(), mMap.getSizeX(), mMap.getSizeY());
				mDragableObjects.add((InGameCamera) mCamera);
				
				mGrid = new Grid(mMap.getSizeX(), mMap.getSizeY());
				
				mMenuBar = new MenuBar(5.0f, mCamera.getCenterY()+mCamera.getTopClipPlane()-0.5f, 3.0f, 1.0f);
				
				menuPlayedOnce = false;
				defeatPlayedOnce = false;
				victoryPlayedOnce = false;
				BGMplayedOnce = false;
				loadOnce = true;
				
				if(mSound.loaded == false)
		        {
					mSound.LoadingSound();
		        }
			}
			
			if(mSound.loaded == true)
			{
				currentState = GameState.PLAYING;
			}
		}
		else if(currentState == GameState.PLAYING)
		{
			if(CurrentWave == null && mWavesLeft > 0)
			{
				CurrentWave = new EnemyWave(4, 1, 2.0, mMap); 
			}
			
			if(BGMplayedOnce == false)
			{
				mSound.PlayInGame();
				BGMplayedOnce = true;
			}
			
			for(UsableBlock ub : mUsableBlockList)
			{
				ub.update(deltaT);
				
				if(ub.isClicked())
				{
					if(ub.getTower() == null)
					{	
						if(mMerchant.buyTower(mPlayer, TowerType.Basic, ub))
						{
							mClickableObjects.add(ub.getTower());
							mSound.PlaySound("MG_Tower_Spawn", 0, 0.4f);
						}
					}		
					
					ub.setClicked(false);
				}
				
				if(ub.getTower() != null && ub.getTower().isClicked())
				{
					if(ub.towerMenuIsDisplayed())
					{
						ub.passPlayerResources(mPlayer.getResources());
						mClickableObjects.addAll(ub.getTowerMenuClickables());
					}
					
					if(ub.towerMenuNeedsToBeClosed())
					{
						mClickableObjects.removeAll(ub.getTowerMenuClickables());
						ub.getTower().closeMenu();
					}
					
					if(ub.towerNeedsAnUpgrade())
					{
						mMerchant.UpgradeTower(mPlayer, ub.getTower());
						ub.getTower().setClicked(false);
					}
					
					if(ub.towerNeedsToBeSold())
					{
						mMerchant.sellTower(mPlayer, ub);
					}
				}
			}
			
			for(Iterator<Enemy> ite = CurrentWave.getEnemyList().iterator(); ite.hasNext();)
			{
				Enemy e = ite.next();
				
				for(UsableBlock ub : mUsableBlockList)
		        {
					if(ub.towerProjectilesCollide(e))
					{
						e.takeDamage(ub.getTower().getLevel());
						mSound.PlaySound("MG_Enemy_Damage", 0, 0.4f);
						//mSound.PlaySound("MG_Enemy_Death", 0, 0.3f);
					}
					
					if(ub.fireTowerProjectileIfInRange(e.getPosition()) == true)
					{
						mSound.PlaySound("MG_Tower_Shot", 0, 0.3f);
					}
		        }
			}
			
			if(CurrentWave.collide(mMap.getBase()))
			{
				mMap.getBase().takeDamage();

				mSound.PlaySound("MG_Base_Damage", 0, 0.3f);
				
				//Game is lost
				if(mMap.getBase().isDestroyed() == true)
				{
					currentState = GameState.LOADING_DEFEAT_MENU;
					mSound.StopSound();
					mSound.StopInGame();
				}
			}
			
			if(CurrentWave.isDead())
			{
				CurrentWave = null;
				mWavesLeft--;
			}
			else
			{
				CurrentWave.update(deltaT);
				
				if(CurrentWave.resourcesAreAvailabe())
				{
					mPlayer.addResources(CurrentWave.getResources());
				}
			}
			
			if(mWavesLeft <= 0)
			{
				//The battle is won
				currentState = GameState.LOADING_VICTORY_MENU;
				mSound.StopSound();
				mSound.StopInGame();
			}
			
			mBase.update(deltaT);
			mMenuBar.update(mPlayer.getResources(), mCamera.getCenterX(), mCamera.getCenterY()+mCamera.getTopClipPlane()-0.5f);
		}
		else if(currentState == GameState.LOADING_VICTORY_MENU)
		{
			mCamera = new MenuCamera();
			((MenuCamera) mCamera).set();
			clearMap();
			
			mMenu = new VictoryMenu(5.0f,5.0f,10.0f,6.0f);
			
			mClickableObjects.addAll(mMenu.getButtons());
			
			currentState = GameState.VICTORY;
		}
		else if(currentState == GameState.VICTORY)
		{
			if(victoryPlayedOnce == false)
			{			
				mSound.PlayVictory();
				victoryPlayedOnce = true;
			}
		}
		else if(currentState == GameState.LOADING_DEFEAT_MENU)
		{
			mCamera = new MenuCamera();
			((MenuCamera) mCamera).set();
			clearMap();
			
			mMenu = new DefeatMenu(5.0f,5.0f,10.0f,6.0f);
			
			mClickableObjects.addAll(mMenu.getButtons());
			
			currentState = GameState.DEFEAT;
		}
		else if(currentState == GameState.DEFEAT)
		{
			if(defeatPlayedOnce == false)
			{
				mSound.PlayDefeat();
				defeatPlayedOnce = true;
			}
		}
		
		handleEvents();
	}
	
	public boolean loadAssets(Context context)
	{
		mMapFilesManager = context.getAssets();
		
		if(mMapFilesManager != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void setMap(String file_name)
	{
		if(mMapFilesManager == null)
		{
			Log.d("Assets", "Manager is null");
		}
		
		mMap = new Map(mMapFilesManager, file_name);
		mUnusableBlockList = mMap.getUnusableBlockLists();
		mUsableBlockList = mMap.getUsableBlockLists();
		mClickableObjects.addAll(mUsableBlockList);
		mBase = mMap.getBase();
		mWavesLeft = mMap.getNumberOfWaves();		
	}
	
	public void clearMap()
	{
		mMap = null;
		mUnusableBlockList = null;
		mUsableBlockList = null;
		mClickableObjects.clear();
		mBase = null;
	}
	
	public Map getMap()
	{
		return mMap;
	}
	
	public EnemyWave getCurrentWave()
	{
		return CurrentWave;
	}
	
	public ConcurrentLinkedQueue<Tower> getTowerList()
	{
		return mTowerList;
	}
	
	public ConcurrentLinkedQueue<Projectile> getProjectileList()
	{	
		return mProjectileList;
	}
	
	public GameCamera getCamera()
	{
		return mCamera;
	}
	
	public void setEventHandler(TouchEventHandler teh)
	{
		mTouchEvtHdlr = teh;
	}
	
	private void handleEvents()
	{
		if(!mTouchEvtHdlr.queueIsEmpty())
		{
			TouchEvent evt = mTouchEvtHdlr.pull();
			
			switch(evt.getTouchType())
			{
				case CLICK:
				{
					for(Iterator<IClickable> itcl = mClickableObjects.iterator(); itcl.hasNext();)
					{
						IClickable clickable = itcl.next();
						ITouchable touchable = (ITouchable) clickable;
						
						if(touchable != null && touchable.collide(new WorldPoint(evt.getxPoint1(), evt.getyPoint1())))
						{
							clickable.click(evt.getxPoint1(), evt.getyPoint1());
						}
					}
					
					break;
				}
				
				case DRAG:
				{
					for(Iterator<IDragable> itdr = mDragableObjects.iterator(); itdr.hasNext();)
					{
						IDragable dragable = itdr.next();
						
						dragable.drag(evt.getxPoint1(), evt.getyPoint1());
					}
					
					break;
				}
			}
		}
	}

	public void draw(float[] mvpMatrix) 
	{
		mvpMatrix = mCamera.getModelViewProjectionMatrix();
		
		if(currentState == GameState.LOADING_GAME)
		{
			
		}
		else if(currentState == GameState.START_MENU)
		{
			mMenu.draw(mvpMatrix);
		}
		else if(currentState == GameState.PLAYING)
		{	
			//Draw Map
			mMap.draw(mvpMatrix);
			//_Debug : if no texture handling, uncomment
			// Draw unusable blocks
	        /*for(UnusableBlock unb : mUnusableBlockList)
	        {
	        	if(mvpMatrix != null)
	        	{
	        		unb.draw(mvpMatrix);
	        	}
	        }*/
	        
	        // Draw usable blocks
	        for(UsableBlock ub : mUsableBlockList)
	        {
	        	ub.draw(mvpMatrix);
	        }
	        
	        //Draw base
	        mBase.draw(mvpMatrix);
	        
	        //Draw the grid
	        mGrid.draw(mvpMatrix);
	        
	        //If current wave is not dead, draw it
	        if(CurrentWave != null)
	        {
	        	CurrentWave.draw(mvpMatrix);
	        }
	        
	        mMenuBar.draw(mvpMatrix);
		}
		else if(currentState == GameState.VICTORY)
		{
			mMenu.draw(mvpMatrix);
		}
		else if(currentState == GameState.DEFEAT)
		{
			mMenu.draw(mvpMatrix);
		}
	}
	
	public static void changeState(GameState st)
	{
		currentState = st;
	}
	
	public void setSound(MySoundPlayer soundpool)
	{
		mSound = soundpool;
	}
	
	public void setPause()
	{
		mSound.PauseInGame();
		mSound.PauseSound();
	}
	
	public void setResume()
	{
		mSound.ResumeInGame();
	}
	
	public GLText getmText() {
		return mText;
	}

	public void setmText(GLText mText) {
		this.mText = mText;
	}
}
