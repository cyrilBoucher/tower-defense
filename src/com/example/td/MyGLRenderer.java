package com.example.td;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import GameObjects.Base;
import GameObjects.Block;
import GameObjects.Enemy;
import GameObjects.EnemyWave;
import GameObjects.Projectile;
import GameObjects.Tower;
import Primitives.Grid;
import TextToTexture.GLText;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    
    private MyGame mGame;
    private GLText mText;
    private Context mContext;
    private int width = 100;                           // Updated to the Current Width + Height in onSurfaceChanged()
	private int height = 100;
    
    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private float[] mMProjMatrix = new float[16];
	private float[] mMVMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
    
    public MyGLRenderer(Context context)
    { 
    	mContext = context;
    	// __Debug
    	Log.d(TAG+ " Create", "Camera is created");
    } 

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

    	// Set the background frame color
    	GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
         
    	//Loading Textures
        TexturePool.loadTextures(mContext);
    	
    	mText = new GLText(mContext.getAssets());
        mText.load("Roboto-Regular.ttf", 32, 2, 2);
        mGame.setmText(mText);
    	// __Debug
    	Log.d(TAG+ " Create", "Surface is created");
    	
    	// Set up alpha blending
    	GLES20.glEnable(GLES20.GL_BLEND);
    	// Blend function to take into account alpha channel
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
    	
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.multiplyMM(mMVPMatrix, 0, mMProjMatrix, 0, mMVMatrix, 0);
        /*if(mText != null)
		{
			mText.begin(1.0f, 1.0f, 1.0f, 1.0f, mMVPMatrix);         // Begin Text Rendering (Set Color WHITE)
			mText.draw("Ceci est le menu !", -100, 5, 0);
			mText.end();
		}*/
        //Draw the game
        if(mMVPMatrix != null)
        {
        	mGame.draw(mMVPMatrix);
        }
        
        //Update the game
        mGame.update();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        //Get the width and height for the landscape orientation
    	GLES20.glViewport(0, 0, width, height);
    	
    	Screen.setScreenWidth(width);
    	Screen.setScreenHeight(height);
    	
    	float ratio = (float) width / height;

		// Take into account device orientation
		if (width > height) {
			Matrix.frustumM(mMProjMatrix, 0, -ratio, ratio, -1, 1, 1, 10);
		}
		else {
			Matrix.frustumM(mMProjMatrix, 0, -1, 1, -1/ratio, 1/ratio, 1, 10);
		}
		
		// Save width and height
		this.width = width;                             // Save Current Width
		this.height = height;                           // Save Current Height
		
		int useForOrtho = Math.min(width, height);
		
		//TODO: Is this wrong?
		Matrix.orthoM(mMVMatrix, 0,
				-useForOrtho/2,
				useForOrtho/2,
				-useForOrtho/2,
				useForOrtho/2, 0.1f, 100f);
    }

    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
    * Utility method for debugging OpenGL calls. Provide the name of the call
    * just after making it:
    *
    * <pre>
    * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
    *
    * If the operation is not successful, the check throws an error.
    *
    * @param glOperation - Name of the OpenGL call to check.
    */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

   public void addSquare(float x, float y, float size, float color){
	   //mSquareRequestList.add(new float[] {x,y,size,color});
	   Log.d("Renderer", "Adding Square x:" + Float.toString(x) + " y:" + Float.toString(y));
	   //mSquareList.add(new Square(x, y, size, color));
   }
   
   public void setGame(MyGame g)
   {
	   mGame = g;
   }
   
   public GLText getText()
   {
	   return mText;
   }
}