package com.github.cyrilBoucher.td;

public class MenuCamera extends GameCamera{

	public MenuCamera()
	{
		
	}
	
	public void set()
	{
		setViewport(Screen.getScreenWidth(), Screen.getScreenHeight());
		setLookAt(5.0f, 5.0f, 5.0f, 5.0f);
		
		float leftToRight = 10.0f;
		float bottomToTop = mViewportHeight*leftToRight/mViewportWidth;
		setProjectionMatrix(-(leftToRight/2.0f), (leftToRight/2.0f), -(bottomToTop/2.0f), (bottomToTop/2.0f), 10.0f, -100.0f);
	}
}
