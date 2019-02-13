package com.github.cyrilBoucher.td.GameObjects;

import java.util.ArrayList;

import com.github.cyrilBoucher.td.Interfaces.IClickable;
import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public class TowerModificationMenu extends GameObject implements IDrawable {

	private int playerResources;

	private ArrayList<IClickable> icons;
	private TowerUpgradeButton upgradeButton;
	private TowerSellButton sellButton;
	private Tower towerToModify;
	
	public TowerModificationMenu(Tower t2m) {
		towerToModify = t2m;
		
		worldPosition = new WorldPoint(towerToModify.worldPosition.getX(), towerToModify.worldPosition.getY());
		
		sellButton = new TowerSellButton(worldPosition.getX(), worldPosition.getY() + 0.8f, 0.5f, 0.5f, towerToModify);
		
		icons = new ArrayList<IClickable>();
		
		icons.add(sellButton);
	}

	@Override
	public void draw(float[] mvpMatrix) {

		if(upgradeButton != null)
		{
			upgradeButton.draw(mvpMatrix);
		}
		
		sellButton.draw(mvpMatrix);

	}

	@Override
	public void update(double deltaT) {

		if(playerResources >= towerToModify.getType().getUpgradeCost() && !towerToModify.hasReachedMaxLevel())
		{
			if(upgradeButton == null)
			{
				upgradeButton = new TowerUpgradeButton(worldPosition.getX() + 0.8f, worldPosition.getY(), 0.5f, 0.5f, towerToModify);
				icons.add(upgradeButton);
			}
		}
		else
		{
			upgradeButton = null;
		}
	}
	
	public void setPlayerResources(int playerResources) 
	{
		this.playerResources = playerResources;
	}
	
	public ArrayList<IClickable> getIcons()
	{
		return icons;
	}
}
