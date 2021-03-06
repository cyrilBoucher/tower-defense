package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Map;

import com.github.cyrilBoucher.td.Primitives.Sprite;

public class GruntEnemy extends Enemy{

	public GruntEnemy(int x, int y, Map m) {
		super(x, y, m);
		
		speed =  0.5f;
		
		vitality = 2;
		maxVitality = 2;
		
		for(int i = 0; i < aSprites.length - 1; i++)
		{
			aSprites[i] = new Sprite(worldPosition.getX(),worldPosition.getY(),0.5f,0.5f,new String[]{"warrior1_" + Integer.toString(i+1)});
		}

		// re-use second sprite for smooth animation
		aSprites[aSprites.length - 1] = aSprites[1];
		
		sEnemy = aSprites[sIndex];
	}


}
