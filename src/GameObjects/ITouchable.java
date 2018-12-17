package GameObjects;

import Utilities.WorldPoint;

public interface ITouchable {

	public boolean collide(WorldPoint wp);
	
	public CollisionableObject getCollisionableObject();
}
