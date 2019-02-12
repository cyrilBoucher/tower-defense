package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Utilities.GridPoint;
import com.github.cyrilBoucher.td.Utilities.WorldPoint;

public abstract class GameObject {
	
	protected WorldPoint worldPosition;
	protected GridPoint gridPosition;

	public abstract void update(double deltaT);
}
