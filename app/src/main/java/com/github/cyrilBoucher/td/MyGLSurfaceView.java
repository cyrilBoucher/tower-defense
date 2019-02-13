package com.github.cyrilBoucher.td;


import com.github.cyrilBoucher.td.GameObjects.TouchEvent;
import com.github.cyrilBoucher.td.GameObjects.TouchType;
import com.github.cyrilBoucher.td.Utilities.Conversion;
import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView {

	private int[] mActivePointers = new int[2];
	private int mActivePointerIdx = 0;
	private boolean hasMoved = false;
	
    private final MyGLRenderer mRenderer;
    private final MyGame mGame;
    private final TouchEventHandler mTouchEvtHdlr;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // __Debug
        Log.d("Create", "Renderer is created");
        
        MySoundPlayer mSound = new MySoundPlayer(context);
        mGame = new MyGame(mSound);
        
        mTouchEvtHdlr = new TouchEventHandler();
        mGame.loadAssets(context);
        mGame.setEventHandler(mTouchEvtHdlr);
        
        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer(context);
        mRenderer.setGame(mGame);

        setRenderer(mRenderer);
        
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);  
    }
    
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final double CLICK_DRAG_LIMIT_SENSIBILITY = 10.0;
    private float mPreviousX;
    private float mPreviousY;
    private float mFirstX;
    private float mFirstY;
    private float mCurrentDist;
    private float mPreviousDist;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
		if(e.getPointerCount()>2) return false;
		
		// get pointer index from the event object
	    int pointerIndex = e.getActionIndex();

	    // get pointer ID
	    int pointerId = e.getPointerId(pointerIndex);

	    // get masked (not specific to a pointer) action
	    int maskedAction = e.getActionMasked();

	    switch (maskedAction) {

		    case MotionEvent.ACTION_DOWN:
		    case MotionEvent.ACTION_POINTER_DOWN: {
		      // We have a new pointer. Lets add it to the list of pointers

		      PointF f = new PointF();
		      f.x = e.getX(pointerIndex);
		      f.y = e.getY(pointerIndex);
		      mActivePointers[mActivePointerIdx] = pointerIndex;
      		  mActivePointerIdx++;
      		  if(e.getPointerCount() < 2)
		      {
		    	  mFirstX = e.getX(pointerIndex);
		    	  mFirstY = e.getY(pointerIndex);
		      }
		      if(e.getPointerCount() == 2)
		      {
		    	  float x1, x2, y1, y2;
		    	  
		    	  x1 = e.getX(mActivePointers[0]);
		    	  x2 = e.getX(mActivePointers[1]);
		    		
		    	  y1 = e.getY(mActivePointers[0]);
		    	  y2 = e.getY(mActivePointers[1]);
		    	  
		    	  float worldX1 = Conversion.screenToWorldX(x1, mGame.getCamera());
		          float worldY1 = Conversion.screenToWorldY(y1, mGame.getCamera());
		          float worldX2 = Conversion.screenToWorldX(x2, mGame.getCamera());
		          float worldY2 = Conversion.screenToWorldY(y2, mGame.getCamera());
		          
		          float dist = (float) Math.sqrt((worldX2 - worldX1)*(worldX2 - worldX1) + (worldY2 - worldY1)*(worldY2 - worldY1));
		          
		          mCurrentDist = dist;
		          mPreviousDist = dist;
		      }
		      break;
		    }
		    case MotionEvent.ACTION_MOVE: { // a pointer was moved
		    	if(e.getPointerCount() < 2)
		    	{
		    		if(Math.sqrt(Math.pow(e.getX(pointerIndex) - mFirstX, 2) + Math.pow(e.getY(pointerIndex) - mFirstY, 2)) > CLICK_DRAG_LIMIT_SENSIBILITY)
		    		{
			    		drag(e.getX(pointerIndex), e.getY(pointerIndex));
			    		hasMoved = true;
		    		}
		    	}
		    	else
		    	{
		    		float x1, x2, y1, y2;
		    		
		    		x1 = e.getX(mActivePointers[0]);
		    		x2 = e.getX(mActivePointers[1]);
		    		
		    		y1 = e.getY(mActivePointers[0]);
		    		y2 = e.getY(mActivePointers[1]);
		    		
		    		if(Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) > CLICK_DRAG_LIMIT_SENSIBILITY)
		    		{
			    		zoom(x1, y1, x2, y2);	
			    		hasMoved = true;
		    		}
		    	}
		    }
		    break;
		     
		    case MotionEvent.ACTION_UP:
		    case MotionEvent.ACTION_POINTER_UP:
		    	if(e.getPointerCount() < 2 && hasMoved == false)
		    	{
					performClick();
		    		click(e.getX(pointerIndex), e.getY(pointerIndex));
		    	}
		    	else
		    	{
		    		hasMoved = false;
		    	}
		    case MotionEvent.ACTION_CANCEL: {
		    	mActivePointerIdx = 0;
		      break;
		    }
		}
	    
	    mPreviousX = Conversion.screenToWorldX(e.getX(pointerIndex), mGame.getCamera());
        mPreviousY = Conversion.screenToWorldY(e.getY(pointerIndex), mGame.getCamera());
	    
    	return true;
	 }
    
    public void drag(float x, float y)
    {    	
    	float worldX = Conversion.screenToWorldX(x, mGame.getCamera());
        float worldY = Conversion.screenToWorldY(y, mGame.getCamera());
    	
    	float dx = worldX - mPreviousX;
        float dy = worldY - mPreviousY;
        
    	TouchEvent evenement = new TouchEvent(-dx * TOUCH_SCALE_FACTOR, -dy * TOUCH_SCALE_FACTOR, TouchType.DRAG);
    	
    	mTouchEvtHdlr.push(evenement);
    }
    
    public void multiDrag(float x1, float y1, float x2, float y2)
    {    	
    	
    }

	@Override
	public boolean performClick()
    {
		return super.performClick();
    }

	public void click(float x, float y)
	{
		float worldX = Conversion.screenToWorldX(x, mGame.getCamera());
		float worldY = Conversion.screenToWorldY(y, mGame.getCamera());

		TouchEvent evenement = new TouchEvent(worldX, worldY, TouchType.CLICK);

		mTouchEvtHdlr.push(evenement);
	}
    
    public void zoom(float x1, float y1, float x2, float y2)
    {
    	TouchEvent evenement = new TouchEvent(x1, y1, TouchType.MULTI_DRAG, x2, y2);
    }
    
    public void setMyGamePause()
    {
 	   mGame.setPause();
    }
    
    public void setMyGameResume()
    {
    	mGame.setResume();
    }
}
