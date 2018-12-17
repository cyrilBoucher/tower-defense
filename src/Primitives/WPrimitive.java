package Primitives;

import android.opengl.GLES20;

public abstract class WPrimitive extends Primitive {
	
	@Override
	public void drawElements()
	{
		// Draw elements using line loops
        GLES20.glDrawElements(
        		GLES20.GL_LINE_LOOP, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
	}

}
