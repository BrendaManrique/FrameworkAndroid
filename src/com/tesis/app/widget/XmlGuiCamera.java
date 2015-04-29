package com.tesis.app.widget;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tesis.app.R;

public class XmlGuiCamera extends LinearLayout  {
	TextView label;
	Button buttonCameraInicio;
	Button buttonCameraStop;
	Button buttonClick;
    Preview preview;
    byte[] camData = new byte[0];
    
	public XmlGuiCamera(final Context context,String labelText) {
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);
		preview = new Preview(context);
		label = new TextView(context);
		label.setTextSize(16);
		label.setText(labelText);
		
		buttonCameraInicio = new Button(context);
		buttonCameraInicio.setBackgroundResource(R.xml.custom_button_red);
		LayoutParams params = new LayoutParams(
		        LayoutParams.WRAP_CONTENT,      
		        LayoutParams.WRAP_CONTENT
		);  		
		buttonCameraInicio.setLayoutParams(params);
		buttonCameraInicio.setText("Start");
		buttonCameraInicio.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Preview.startPreview();
		    }
		} );
		
		buttonCameraStop = new Button(context);
		buttonCameraStop.setBackgroundResource(R.xml.custom_button_red);
		buttonCameraStop.setLayoutParams(params);
		buttonCameraStop.setText("Stop");
		buttonCameraStop.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Preview.stopPreview();
		    }
		} );
		

		LinearLayout barLinearLayout = new LinearLayout(context);
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
		barLinearLayout.setLayoutParams(params3);
		barLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		barLinearLayout.addView(buttonCameraInicio);
	    barLinearLayout.addView(buttonCameraStop);
	   
	   
		FrameLayout barFrameLayout = new FrameLayout(context);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(400,400,
        		Gravity.CENTER);
        barFrameLayout.setLayoutParams(params2);		
	    barFrameLayout.addView(preview);
				
		buttonClick = new Button(context);
		buttonClick.setBackgroundResource(R.xml.custom_button_red);
		LayoutParams params1 = new LayoutParams(
		        LayoutParams.WRAP_CONTENT,      
		        LayoutParams.WRAP_CONTENT
		);
		buttonClick.setLayoutParams(params1);
		buttonClick.setText("Capturar");
		buttonClick.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if(preview.previewing)
					preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
				else
					Toast.makeText(context, "Activa la camara", Toast.LENGTH_SHORT).show();
		    }
		} );
		
		this.addView(label);		
		this.addView(barLinearLayout);
		this.addView(barFrameLayout);
		this.addView(buttonClick);
	}


	//---------------- SETTERS & GETTERS --------------------		
	public String getValue()
	{
		String str = "";

		if(camData.length !=0)
			str = Base64.encodeToString(camData, Base64.DEFAULT);
		return str;
	}
	
	public void setCamData(byte[] data)
	{
		camData = data;
	}

	//---------------- FUNCIONES DE CÁMARA --------------------	
	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};

	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};

	/** Handles data for jpeg picture */
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			setCamData(data);
			Log.d("XmlGuiCamera", "Foto Tomada - jpeg");
		}
	};
	

	

	
}