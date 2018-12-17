package GameObjects;

import java.util.ArrayList;

import Interfaces.IClickable;
import Primitives.Sprite;

public abstract class Menu implements IDrawable{

	protected Sprite sMenu;
	
	protected ArrayList<IClickable> buttons;

	@Override
	public void draw(float[] mvpMatrix) {

		sMenu.draw(mvpMatrix);
		
		for(IClickable b : buttons)
		{
			Button button = (Button)b;
			button.draw(mvpMatrix);
		}
		
	}
	
	public ArrayList<IClickable> getButtons()
	{
		return buttons;
	}
}
