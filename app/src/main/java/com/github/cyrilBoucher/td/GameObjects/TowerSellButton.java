package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Primitives.Sprite;

public class TowerSellButton extends Button{

	//_Debug : if no texture handling, uncomment
	//private Square background;
	//private CoinPile coinPile;
	
	private Sprite sSell;
	
	private Tower towerToSell;
	
	public TowerSellButton(float x, float y, float width, float height, Tower t2s) {
		super(x, y, width, height);
		
		//_Debug : if no texture handling, uncomment
		//background = new Square(x,y,width,height,new float[]{0.8f,0.79f,0.79f,1.0f});
		//coinPile = new CoinPile(x,y,width-0.05f);
		
		sSell = new Sprite(x,y,width,height,new String[]{"sell_button"});
		
		towerToSell = t2s;
		
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		//_Debug : if no texture handling, uncomment
		//background.draw(mvpMatrix);
		//coinPile.draw(mvpMatrix);
		
		sSell.draw(mvpMatrix);
		
		//_Debug : uncomment for collision debugging
		//touchBox.draw(mvpMatrix);
	}
	
	@Override
	public void click(float x, float y) {

		towerToSell.askForClosing();
		
		towerToSell.askForSell();
		
	}

	
}
