package com.example.td;

import Interfaces.IDragable;
import android.util.Log;

public class InGameCamera extends GameCamera implements IDragable{
	
	private float mLimitLeft;
	private float mLimitRight;
	private float mLimitBottom;
	private float mLimitTop;
	private float mLimitZoomIn;
	private float mLimitZoomOut;
	
	public InGameCamera(float cam_pos_x, float cam_pos_y, float map_size_x, float map_size_y)
	{
		setViewport(Screen.getScreenWidth(), Screen.getScreenHeight());
		setLookAt(cam_pos_x, cam_pos_y, cam_pos_x, cam_pos_y);
		
		float leftToRight = 10.0f;
		float bottomToTop = mViewportHeight*leftToRight/mViewportWidth;
		setProjectionMatrix(-(leftToRight/2.0f), (leftToRight/2.0f), -(bottomToTop/2.0f), (bottomToTop/2.0f), 10.0f, -100.0f);
		
		setCameraLimits(0.0f, map_size_x, 0.0f, map_size_y, 4.0f, map_size_y);
	}
	
	public void move(float dX, float dY)
    {	
    	if(((mEyeX+dX) + mRight) > mLimitRight || ((mEyeX+dX) + mLeft) < mLimitLeft)
 	   	{
    		dX = 0.0f;
 	   	}
    	
    	if(((mEyeY+dY) + mTop) > mLimitTop || ((mEyeY+dY) + mBottom) < mLimitBottom)
 	   	{
    		dY = 0.0f;
 	   	}
    	
    	setLookAt(mEyeX+dX, mEyeY+dY, mCenterX+dX, mCenterY+dY);
    }
    
	public void zoom(float dX, float dY)
    {	
    	//Zoom out
    	if((mEyeX + (mRight+dX)) > mLimitRight || (mEyeX + (mLeft-dX)) < mLimitLeft ||
    		(mEyeY + (mTop+dY)) > mLimitTop || (mEyeY + (mBottom-dY)) < mLimitBottom)
 	   	{
    		dX = 0.0f;
    		dY = 0.0f;
 	   	}
    	
    	//Zoom in
    	if((mRight - mLeft) + 2.0f*dX < mLimitZoomIn || (mTop - mBottom) + 2.0f*dY < mLimitZoomIn)
    	{
    		dX = 0.0f;
    		dY = 0.0f;
    	}
    	
    	setProjectionMatrix(mLeft-dX, mRight+dX, mBottom-dY, mTop+dY, mNear, mFar);
    }
	
	public void setCameraLimits(float l_left, float l_right, float l_bottom, float l_top, float l_zoom_in, float l_zoom_out)
    {
    	mLimitLeft = l_left;
    	mLimitRight = l_right;
    	mLimitBottom = l_bottom;
    	mLimitTop = l_top;
    	mLimitZoomIn = l_zoom_in;
    	mLimitZoomOut = l_zoom_out;
    }

	@Override
	public void drag(float dX, float dY) {
		move(dX, dY);
	}

	@Override
	public void multiDrag(float dX1, float dY1, float dX2, float dY2) {
		// TODO Auto-generated method stub
		
	}

}
