package GameObjects;

import java.util.ArrayList;

import Utilities.GridPoint;

public class TowerCreationMenu extends GameObject implements IDrawable {

	private ArrayList<ClickableIcon> Icons;
	
	public TowerCreationMenu(GridPoint pos) {
		gridPosition = pos;
	}
	
	@Override
	public void draw(float[] mvpMatrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(double deltaT) {
		// TODO Auto-generated method stub

	}

}
