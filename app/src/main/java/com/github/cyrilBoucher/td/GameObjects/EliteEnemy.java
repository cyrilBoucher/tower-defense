package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Map;

import com.github.cyrilBoucher.td.Primitives.Sprite;

public class EliteEnemy extends Enemy{

	public EliteEnemy(int x, int y, Map m) {
		super(x, y, m);

		speed = 0.3f;
		
		vitality = 4;
		maxVitality = 4;
		
		for(int i = 0; i < aSprites.length - 1; i++)
		{
			aSprites[i] = new Sprite(worldPosition.getX(),worldPosition.getY(),0.5f,0.5f,new String[]{"warrior2_" + Integer.toString(i+1)});
		}

		// re-use second sprite for smooth animation
		aSprites[aSprites.length - 1] = aSprites[1];
		
		sEnemy = aSprites[sIndex];
	}


}
