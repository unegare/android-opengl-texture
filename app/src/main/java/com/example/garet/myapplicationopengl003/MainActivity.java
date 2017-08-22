package com.example.garet.myapplicationopengl003;

import android.app.ActionBar;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyGLSurfaceView mGLSurfaceView;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mGLSurfaceView = new MyGLSurfaceView(this);
        mGLSurfaceView.setLayoutParams(params);
        mGLSurfaceView.setVisibility(View.VISIBLE);

        RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        layout.addView (mGLSurfaceView);
        setContentView(layout);
    }

    @Override
    protected void onResume () {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause () {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    public native String stringFromJNI();
}