package GameObjects;

import com.example.td.Map;

import Primitives.Sprite;
import Utilities.GridPoint;

public class EliteEnemy extends Enemy{

	public EliteEnemy(int x, int y, Map m) {
		super(x, y, m);

		speed = 0.3f;
		
		vitality = 4;
		maxVitality = 4;
		
		for(int i = 0; i < 4; i++)
		{
			aSprites[i] = new Sprite(worldPosition.getX(),worldPosition.getY(),0.5f,0.5f,new String[]{"warrior2_" + Integer.toString(i+1)});
		}
		
		sEnemy = aSprites[sIndex];
	}


}
