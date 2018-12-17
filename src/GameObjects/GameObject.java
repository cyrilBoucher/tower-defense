package GameObjects;

import Utilities.GridPoint;
import Utilities.WorldPoint;

public abstract class GameObject {
	
	protected WorldPoint worldPosition;
	protected GridPoint gridPosition;

	public abstract void update(double deltaT);
}
