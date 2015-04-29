package com.tesis.app;
import android.content.Context;

import com.tesis.app.dal.DBHandler;
import com.tesis.app.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;

public class ActSplash extends Activity {

	private long splashDelay = 2000; //2 segundos
	final String tag = ActSplash.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_act_splash);
		
		
		AlphaAnimation blinkanimation= new AlphaAnimation(0, 1); 
		blinkanimation.setDuration(3000); 
		
		ImageView imgIcon = (ImageView) findViewById(R.id.imageView1);
		imgIcon.startAnimation(blinkanimation);
		
		
		 TimerTask task = new TimerTask() 
	  		{
	  			@Override
	  			public void run() 
	  			{

	  				startActivity(new Intent(ActSplash.this,ActMenu.class));
	  				finish();//Destruimos esta activity para prevenir que el usuario retorne aqui presionando el boton Atras.
	  	     
	  			}
	  		};
	  		Timer timer = new Timer();
	  		timer.schedule(task, splashDelay);//Pasado los 6 segundos dispara la tarea
	}
}
