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
	private static HashMap<String, Integer> resources = createResourcesMap();
	
	private static int[] texNames;

	private static HashMap<String, Integer> createResourcesMap()
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("main_menu", R.drawable.main_menu);
		map.put("map1", R.drawable.map1);
		map.put("defeat_menu", R.drawable.defeat_menu);
		map.put("play_button", R.drawable.play_button);
		map.put("quit_button", R.drawable.quit_button);
		map.put("projectile", R.drawable.projectile);
		map.put("tower", R.drawable.tower);
		map.put("victory_menu", R.drawable.victory_menu);
		map.put("sell_button", R.drawable.sell_button);
		map.put("upgrade_button", R.drawable.upgrade_button);
		map.put("resources", R.drawable.resources);
		map.put("ingame_menu_bar", R.drawable.ingame_menu_bar);
		map.put("warrior1_1", R.drawable.warrior1_1);
		map.put("warrior1_2", R.drawable.warrior1_2);
		map.put("warrior1_3", R.drawable.warrior1_3);
		map.put("warrior1_4", R.drawable.warrior1_4);
		map.put("warrior2_1", R.drawable.warrior2_1);
		map.put("warrior2_2", R.drawable.warrior2_2);
		map.put("warrior2_3", R.drawable.warrior2_3);
		map.put("warrior2_4", R.drawable.warrior2_4);

		return map;
	}
	
	public static boolean loadTextures(Context context)
	{
		textures = new HashMap<String, Integer>();

		// generate one more texture name for text
		texNames = new int[resources.size() + 1];
		int texNamesIt = 0;
		
		GLES20.glGenTextures(resources.size(), texNames, 0);
	       
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        for(HashMap.Entry<String, Integer> entry : resources.entrySet())
        {
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), entry.getValue(), options);

			if (bitmap == null) {
				Log.w("textureproblem", "Resource " + entry.getKey() + "with ID " + entry.getValue() + " could not be decoded.");
				return false;
			}
            
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texNames[texNamesIt]);

            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            bitmap.recycle();
            
            textures.put(entry.getKey(), texNames[texNamesIt++]);
        }

        // finally add one last texture for the text to be rendered on
		textures.put("text", texNames[texNamesIt]);
        
        return true;
	}
	
	public static int getTexture(String tex_name)
	{
		return textures.get(tex_name);
	}
}
