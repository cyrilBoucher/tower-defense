package Utilities;

import com.example.td.GameCamera;

public final class Conversion {

	public static float screenToWorldX(float x, GameCamera main_camera)
    {
    	int width = main_camera.getViewportWidth();
    	
    	float visibleXTiles = main_camera.getRightClipPlane() - main_camera.getLeftClipPlane();
        
        float centerX = main_camera.getCenterX();
        
        float centerXToPlane = main_camera.getRightClipPlane();
        
    	return (x * visibleXTiles/width) + (centerX-centerXToPlane);
    }
    
    public static float screenToWorldY(float y, GameCamera main_camera)
    {
		int height = main_camera.getViewportHeight();
    	
    	float visibleYTiles = main_camera.getTopClipPlane() - main_camera.getBottomClipPlane();
        
        float centerY = main_camera.getCenterY();
        
        float centerYToPlane = main_camera.getTopClipPlane();
        
    	return (height-y) * visibleYTiles/height + (centerY-centerYToPlane);
    }
	
	public static float mapToWorld(int v)
	{
		return v - 0.5f;
	}
	
	public static int worldToMap(float v)
	{
		return (int)(v + 1.0f);
	}
}
