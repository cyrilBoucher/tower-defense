package GameObjects;

import com.example.td.InGameCamera;

import Utilities.WorldPoint;

public interface IMovable {

	public void move(double deltaT);
	
	public WorldPoint getPosition();
}
