package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Primitives.Circle;

public class CoinPile extends GameObject implements IDrawable{

	private Circle coin;
	
	public CoinPile(float x, float y, float radius)
	{	
		coin = new Circle(x, y, radius, new float[]{1.0f,0.84f,0.0f,1.0f});
	}
	@Override
	public void draw(float[] mvpMatrix) {
		coin.draw(mvpMatrix);
	}

	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub
		
	}

}
