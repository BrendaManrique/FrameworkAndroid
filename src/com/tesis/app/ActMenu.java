package com.tesis.app;

import com.tesis.app.clases.CForm;
import com.tesis.app.utils.Configuracion;
import com.tesis.app.utils.Utilitario;
import com.tesis.app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActMenu extends Activity{
	String tag = ActMenu.class.getSimpleName();
	CForm theForm = new CForm();
	Context context = this;
	GridView grdMenu;	
	boolean signalOn;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_act_menu);
	   
	    try{
	    SubSetControles();
	    grdMenu.setOnItemClickListener(
	    	new OnItemClickListener() 
	    	{
	    		@Override
	    		public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	    		{
	    			switch (position) {
	    			case 0: //Sincronizar
	    				theForm = new CForm();
	    				signalOn = Utilitario.fnVerSignal(context);
	    				if (signalOn == true)
	    				{
	    					Intent newSincForm = new Intent(ActMenu.this,ActTipoSinc.class);
	    					startActivity(newSincForm);
	    				} else{
	    					Toast.makeText(ActMenu.this, "No hay señal", Toast.LENGTH_SHORT).show();
	    			    }
	    				break;	    					
	    			case 1: //Evaluar
	    				Intent newRunForm = new Intent(ActMenu.this,ActTipoEv.class);
	    				newRunForm.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    					startActivity(newRunForm);
	    				break;
	    			case 2: //Mostrar resultados
	    				theForm = new CForm();
	    				Intent newResForm = new Intent(ActMenu.this,ActTipoRes.class);
		    			newResForm.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	    				startActivity(newResForm);
	    				return;
	    			}
	    		}
	    	}
	    );
	    }catch(Exception e)
	    {
	    	Log.i(tag,e.toString());
	    }
	}

	public void SubSetControles(){
		grdMenu = (GridView)findViewById(R.id.GridView01);
		grdMenu.setAdapter(new ImageAdapter(this));
	}
	
	public class ImageAdapter extends BaseAdapter{

		Context mContext;
		public static final int ACTIVITY_CREATE = 10;
		
		public ImageAdapter(Context c){
			mContext = c;
		}
		
		@Override
		public int getCount() {
			return Configuracion.LstOpcMenu.length;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View v;
		    AlphaAnimation blinkanimation= new AlphaAnimation(0, 1); //Cambia la imagen de invisible a visible
			blinkanimation.setDuration(1500);
		    
		    if(convertView == null){
		    	
		    	LayoutInflater li = getLayoutInflater();
		    	v = li.inflate(R.layout.icon, null);
				//Textos
				TextView tv = (TextView)v.findViewById(R.id.icon_text);
				tv.setTextColor(Color.WHITE);
				tv.setText(Configuracion.LstOpcMenu[position]);
				//Iconos
				ImageView iv = (ImageView)v.findViewById(R.id.icon_image);
				iv.setImageResource(Configuracion.LstIcoMenu[position]);
				iv.startAnimation(blinkanimation);
				tv.startAnimation(blinkanimation);
		    }
		    else{
		    	v = convertView;
		    }
		    return v;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}		
	}
}
