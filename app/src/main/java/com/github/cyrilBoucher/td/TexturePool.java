package com.github.cyrilBoucher.td;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class TexturePool 
{

	private static HashMap<String, Integer> textures;
	private static String[] texStrings = new String[]{
		"main_menu",
		"map1",
		"defeat_menu",
		"play_button",
		"quit_button",
		"projectile",
		"tower",
		"victory_menu",
		"sell_button",
		"upgrade_button",
		"resources",
		"ingame_menu_bar",
		"warrior1_1",
		"warrior1_2",
		"warrior1_3",
		"warrior1_4",
		"warrior2_1",
		"warrior2_2",
		"warrior2_3",
		"warrior2_4",
		"text"
	};
	
	private static int[] texNames;
	
	public static boolean loadTextures(Context context)
	{
		textures = new HashMap<String, Integer>();
		
		texNames = new int[texStrings.length];
		
		GLES20.glGenTextures(texStrings.length, texNames, 0);
	       
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        
        for(int i = 0; i < texStrings.length; i++)
        {
        	// Retrieve our image from resources.
        	if(texStrings[i] != "text")
        	{
	            int id = context.getResources().getIdentifier("drawable/" + texStrings[i], null, context.getPackageName());
	            
	        	Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
	
	            if (bitmap == null) {
	                Log.w("textureproblem", "Resource ID " + id + " could not be decoded.");
	                return false;
	            } 
        	
            
            
            
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texNames[i]);

            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            bitmap.recycle();
        	}
            
            textures.put(texStrings[i], texNames[i]);
        }
        
        return true;
	}
	
	public static int getTexture(String tex_name)
	{
		return textures.get(tex_name);
	}
}
