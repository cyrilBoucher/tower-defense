package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Primitives.Sprite;

public class QuitButton extends Button {

	private Sprite sQuitButton;
	
	public QuitButton(float x, float y, float width, float height) {
		super(x, y, width, height);

		sQuitButton = new Sprite(x,y,width,height,new String[]{"quit_button"});
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		sQuitButton.draw(mvpMatrix);	
		touchBox.draw(mvpMatrix);
	}

	@Override
	public void click(float x, float y) {
	
	}	

}
