package com.github.cyrilBoucher.td;

import android.opengl.Matrix;

public abstract class GameCamera {
	
	protected int mViewportHeight;
	protected int mViewportWidth;
	
	protected float mEyeX;
	protected float mEyeY;
	
	protected float mCenterX;
	protected float mCenterY;
	
	protected float mLeft;
	protected float mRight;
	protected float mBottom;
	protected float mTop;
	protected float mNear;
	protected float mFar;
	
	// mMVPMatrix is an abbreviation for "Model View Projection Matrix"
	protected final float[] mMVPMatrix = new float[16];
	protected final float[] mProjectionMatrix = new float[16];
	protected final float[] mViewMatrix = new float[16];
    
    public GameCamera()
    {
    	
    }
    
    public void setViewport(int width, int height)
    {
    	mViewportWidth = width;
    	mViewportHeight = height;
    }
    
    public void setLookAt(float eyeX, float eyeY, float centerX, float centerY)
    {
    	mEyeX = eyeX;
    	mEyeY = eyeY;
    	mCenterX = centerX;
    	mCenterY = centerY;
    	
    	Matrix.setLookAtM(mViewMatrix, 0, mEyeX, mEyeY, 1.0f, mCenterX, mCenterY, 0f, 0.0f, 1.0f, 0.0f);
    }
    
    public float[] getViewMatrix()
    {
    	return mViewMatrix;
    }
    
    public void setProjectionMatrix(float left, float right, float bottom, float top, float near, float far)
    {
    	mLeft = left;
    	mRight = right;
    	mBottom = bottom;
    	mTop = top;
    	mNear = near;
    	mFar = far;
    	
    	Matrix.orthoM(mProjectionMatrix, 0, mLeft, mRight, mBottom, mTop, mNear, mFar);
    }
    
    public float[] getProjectionMatrix()
    {
    	return mProjectionMatrix;
    }
    
    public float[] getModelViewProjectionMatrix()
    {
    	// Calculate the projection and view transformation
    	Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0); 
    	
    	return mMVPMatrix;
    }
    
    public int getViewportWidth()
    {
    	return mViewportWidth;
    }
    
    public int getViewportHeight()
    {
    	return mViewportHeight;
    }
    
    public float getRightClipPlane()
    {
    	return mRight;
    }
    
    public float getLeftClipPlane()
    {
    	return mLeft;
    }
    
    public float getBottomClipPlane()
    {
    	return mBottom;
    }
    
    public float getTopClipPlane()
    {
    	return mTop;
    }
    
    public float getCenterX()
    {
    	return mCenterX;
    }
    
    public float getCenterY()
    {
    	return mCenterY;
    }

}
