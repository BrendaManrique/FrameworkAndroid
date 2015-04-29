package com.tesis.app.widget;


import com.tesis.app.R;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XmlGuiGps  extends LinearLayout {
	TextView label;
	EditText txtBox;
	EditText txtBox1;
	Button buttonGps;
	
	
	public XmlGuiGps(final Context context,String labelText,String buttonText) {
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);
		
		try {
			label = new TextView(context);
			label.setTextSize(16);
			label.setText(labelText);
			
			txtBox = new EditText(context);
			txtBox.setEnabled(false);
			txtBox1 = new EditText(context);
			txtBox1.setEnabled(false);
			
			buttonGps = new Button(context);
			buttonGps.setBackgroundResource(R.xml.custom_button_red);
			LayoutParams params = new LayoutParams(
			        LayoutParams.WRAP_CONTENT,      
			        LayoutParams.WRAP_CONTENT
			);
			//params.setMargins(0,20,0,0);
			buttonGps.setLayoutParams(params);
			buttonGps.setText(buttonText);
			buttonGps.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
					Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER );
			       if (location!=null){
			    	   double longitude = location.getLongitude();
			    	   double latitude = location.getLatitude();
			    	   Log.i("gps", String.valueOf(latitude)+","+String.valueOf(longitude));
			    	   txtBox.setText("Latitud: "+String.valueOf(location.getLatitude()));
					   txtBox1.setText("Longitud: "+String.valueOf(location.getLongitude()));
			    	}
			       else
			       {
			    	   txtBox.setText("No se puede determinar.");   
			       }
			    }
			} );
			    
			this.addView(label);		
			this.addView(txtBox);
			this.addView(txtBox1);
			this.addView(buttonGps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("GPS",e.toString() );
		}
	}

	//---------------- SETTERS & GETTERS --------------------	
	public String getValue()
	{
		return txtBox.getText().toString()+"/"+txtBox1.getText().toString();
	}
}
