package com.github.cyrilBoucher.td.GameObjects;

import com.github.cyrilBoucher.td.Primitives.Cross;
import com.github.cyrilBoucher.td.Primitives.WSquare;
import com.github.cyrilBoucher.td.Utilities.Conversion;

public class UnusableBlock extends Block{

	private WSquare outline;
	private Cross cross;
	
	public UnusableBlock(int x, int y) {
		super(x, y);
		
		outline = new WSquare(Conversion.mapToWorld(x), Conversion.mapToWorld(y), 1.0f, new float[]{0.0f,0.0f,0.0f,1.0f});
		cross = new Cross(Conversion.mapToWorld(x), Conversion.mapToWorld(y), 1.0f, 1.0f, new float[]{0.0f,0.0f,0.0f,1.0f});
	}

	@Override
	public void draw(float[] mvpMatrix)
	{
		//_Debug : if no texture handling, uncomment
		//block.draw(mvpMatrix);
		
		outline.draw(mvpMatrix);
		cross.draw(mvpMatrix);
	}
}
