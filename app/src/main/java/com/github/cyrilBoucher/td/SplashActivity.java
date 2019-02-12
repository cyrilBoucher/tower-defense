package com.github.cyrilBoucher.td;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // __Debug
        Log.d("Create", "View is created");
        
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
		mGLView = new MyGLSurfaceView(this);
		
        setContentView(mGLView);    
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
        ((MyGLSurfaceView) mGLView).setMyGamePause();
        
        // __Debug
        Log.d("Pause", "Thread is paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
        ((MyGLSurfaceView) mGLView).setMyGameResume();
        
        // __Debug
        Log.d("Resume", "Thread is resumeed");
    }
}