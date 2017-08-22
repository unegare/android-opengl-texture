package com.example.garet.myapplicationopengl003;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by garet on 19.08.17.
 */

public class MyGLSurfaceView extends GLSurfaceView{
    private final MyGLRenderer mGLRenderer;
    //private Context context;

    public MyGLSurfaceView (Context context) {
        super(context);
        setEGLContextClientVersion(3);
        //super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        mGLRenderer = new MyGLRenderer(context);
        setRenderer(mGLRenderer);
        //setRendererMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
