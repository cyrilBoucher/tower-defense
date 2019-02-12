package Text;

import com.github.cyrilBoucher.td.TexturePool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Text {

	private String textToRender;
	private boolean initialised;
	
	private TextSprite textSprite;
	private int[] textureName;
	private int texId;
	
	public Text()
	{
		/*textureName = new int[1];
		
		GLES20.glGenTextures(1, textureName, 0);
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureName[0]);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);*/
		
		texId = TexturePool.getTexture("text");
        
        textToRender = "";
	}
	
	public void drawText(String text, float x, float y, float[] mvpMatrix)
	{
		if(!textToRender.equals(text) || !initialised)
		{	
			textToRender = text;
			
			// Create an empty, mutable bitmap
			Bitmap bitmap = Bitmap.createBitmap(128, 32, Bitmap.Config.ARGB_4444);
			// get a canvas to paint over the bitmap
			Canvas canvas = new Canvas(bitmap);
			bitmap.eraseColor(Color.TRANSPARENT);

			// Draw the text
			Paint textPaint = new Paint();
			textPaint.setTextSize(32);
			textPaint.setTextAlign(Align.LEFT);
			textPaint.setAntiAlias(true);
			textPaint.setARGB(255, 255, 0, 0);
			// draw the text centered
			canvas.drawText(textToRender, 5,28, textPaint);
			
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
			
			textSprite = new TextSprite(x,y,1.0f,0.25f,texId);

            bitmap.recycle();
            
            initialised = true;
		}
		
		textSprite.draw(mvpMatrix);
	}
}
