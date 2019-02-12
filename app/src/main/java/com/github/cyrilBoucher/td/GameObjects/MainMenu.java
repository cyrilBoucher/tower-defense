package com.github.cyrilBoucher.td.GameObjects;

import java.util.ArrayList;

import Interfaces.IClickable;
import com.github.cyrilBoucher.td.Primitives.Sprite;

public class MainMenu extends Menu{

	public MainMenu(float x, float y, float width, float height) 
	{
		buttons = new ArrayList<IClickable>();
		
		String[] file_names = new String[]{"main_menu"};
		sMenu = new Sprite(x, y,width,height,file_names);
		
		buttons.add(new PlayButton(5.0f, 3.0f, 2.0f, 1.0f));
	}

}
