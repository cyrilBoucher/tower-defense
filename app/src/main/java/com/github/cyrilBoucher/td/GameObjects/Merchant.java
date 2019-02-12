package com.github.cyrilBoucher.td.GameObjects;


public class Merchant {
	
	public Merchant() {
	}
	
	public boolean buyTower(Player p, TowerType typeOfTower, UsableBlock ub)
	{
		if(p.getResources() >= typeOfTower.getCost())
		{
			p.payRessources(typeOfTower.getCost());
			ub.createTower(typeOfTower);	
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void sellTower(Player p, UsableBlock ub)
	{
		p.addResources(ub.getTower().getType().getCost()/2);
		ub.sellTower();
	}
	
	public void UpgradeTower(Player p, Tower towerToBeUpgraded)
	{
		if(p.getResources() >= towerToBeUpgraded.getType().getUpgradeCost())
		{
			p.payRessources(towerToBeUpgraded.getType().getUpgradeCost());
			
			towerToBeUpgraded.upgrade();
		}
	}

}
