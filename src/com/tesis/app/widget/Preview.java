package com.tesis.app.widget;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class Preview extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "Preview";

    static SurfaceHolder mHolder;
    public static Camera camera;
	SurfaceView surfaceView;
	static boolean previewing = false;
	
    Preview(Context context) {
        super(context);
        
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);   
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }
    
    public static void startPreview() {
	    if(!previewing){
	        camera = Camera.open();
	        if (camera != null){
	         try {
	          camera.setPreviewDisplay(mHolder);
	          camera.setDisplayOrientation(90);
	          camera.startPreview();
	          previewing = true;
	         } catch (IOException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	         }
	        }
	       }
    }
    public static void stopPreview() {
    	if(camera != null && previewing){
    	     camera.stopPreview();
    	     camera.release();
    	     camera = null;
    	     previewing = false;
    	    }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    }
}