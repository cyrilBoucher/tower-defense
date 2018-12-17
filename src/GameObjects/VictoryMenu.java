package GameObjects;

import java.util.ArrayList;

import Interfaces.IClickable;
import Primitives.Sprite;

public class VictoryMenu extends Menu{

	public VictoryMenu(float x, float y, float width, float height) 
	{
		buttons = new ArrayList<IClickable>();
		
		String[] file_names = new String[]{"victory_menu"};
		sMenu = new Sprite(x, y,width,height,file_names);
		
		buttons.add(new MenuButton(5.0f, 3.0f, 4.5f, 0.7f));
	}

}
