package com.github.cyrilBoucher.td.GameObjects;

import java.util.ArrayList;

import Interfaces.IClickable;
import com.github.cyrilBoucher.td.Primitives.Sprite;

public class DefeatMenu extends Menu{

	public DefeatMenu(float x, float y, float width, float height) 
	{
		buttons = new ArrayList<IClickable>();
		
		String[] file_names = new String[]{"defeat_menu"};
		sMenu = new Sprite(x, y,width,height,file_names);
		
		buttons.add(new MenuButton(5.0f, 3.0f, 4.5f, 0.7f));
	}

}
