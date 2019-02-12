package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.MyGame;

public class MenuButton extends Button 
{
	//_Debug : Button is integrated to background
	//private Sprite sMenuButton;
	
	public MenuButton(float x, float y, float width, float height) 
	{
		super(x, y, width, height);
		
		//_Debug : Button is integrated to background
		//sMenuButton = new Sprite(x,y,width,height,new String[]{"quit_button"});
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		//_Debug : Button is integrated to background
		//sMenuButton.draw(mvpMatrix);	
		
		//_Debug : uncomment for collision debugging
		//touchBox.draw(mvpMatrix);
	}

	@Override
	public void click(float x, float y) 
	{
		MyGame.changeState(MyGame.GameState.LOADING_MENU);	
	}
}
