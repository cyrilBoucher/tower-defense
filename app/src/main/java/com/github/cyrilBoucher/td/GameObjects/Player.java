package com.github.cyrilBoucher.td.GameObjects;

import java.util.ArrayList;

public class Player {

	public int score;
	private int resources;
	private ArrayList<Tower> playerTowers;
	
	public Player() {
		score = 0;
		resources = 200;
		playerTowers = new ArrayList<Tower>();
	}
	
	public void addScore(int points)
	{
		score += points;
	}
	
	public void addResources(int gain)
	{
		resources += gain;
	}
	
	public void payRessources(int amount)
	{
		resources -= amount;
	}

	public int getResources() {
		return resources;
	}

	public ArrayList<Tower> getPlayerTowers() {
		return playerTowers;
	}

}
