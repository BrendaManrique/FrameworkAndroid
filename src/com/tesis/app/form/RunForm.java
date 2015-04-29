package com.tesis.app.form;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.app.ProgressDialog;
import android.os.Handler;

import com.tesis.app.ActResult;
import com.tesis.app.clases.CForm;
import com.tesis.app.clases.CItemData;
import com.tesis.app.dal.DBHandler;
import com.tesis.app.form.ResolForm;
import com.tesis.app.widget.XmlGuiCamera;
import com.tesis.app.widget.XmlGuiChoice;
import com.tesis.app.widget.XmlGuiEditText;
import com.tesis.app.widget.XmlGuiGps;
import com.tesis.app.widget.XmlGuiLabel;
import com.tesis.app.R;

public class RunForm extends Activity {
   String tag = RunForm.class.getSimpleName(); //Solo para log
   //public static Float resultEval=(float) 0;
   ProgressDialog progressDialog;
   static CForm theForm;
   Handler progressHandler;
   String formCode = "";
   Context context = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        theForm = new CForm();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        formCode = extras.getString("formNumber");
        
       	//Carga datos del formulario
		DBHandler dbh = new DBHandler (context);		
		theForm = dbh.runForm_getForm(theForm,formCode);
	    dbh.close();
		
  		DisplayForm();    	
    }
	
	private boolean DisplayForm()
	{		
		try
		{
			ScrollView sv = new ScrollView(this); 
			final LinearLayout ll = new LinearLayout(this);
	        sv.addView(ll);
	        ll.setOrientation(android.widget.LinearLayout.VERTICAL);
	        
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	 
	        // Crea dinámicamente los controles en la vista   
	        int i;
	        for (i=0;i<theForm.items.size();i++) {
	        	if (theForm.items.get(i).getCTRLID().equals(1)) {
	        		theForm.items.get(i).obj = new XmlGuiEditText(this, theForm.items.get(i).getITMLABEL() + (theForm.items.get(i).getITMREQUI().equals("T") ? "*" : "") ,"");
	        		ll.addView((View) theForm.items.get(i).obj);
	        	}
	        	if (theForm.items.get(i).getCTRLID().equals(2)) {
	        		theForm.items.get(i).obj = new XmlGuiEditText(this, theForm.items.get(i).getITMLABEL() + (theForm.items.get(i).getITMREQUI().equals("T") ? "*" : ""),"");
	        		((XmlGuiEditText)theForm.items.get(i).obj).makeNumeric();
	        		ll.addView((View) theForm.items.get(i).obj);
	        	}
	        	if (theForm.items.get(i).getCTRLID().equals(3)) {
	        		theForm.items.get(i).obj = new XmlGuiChoice(this, theForm.items.get(i).getITMLABEL() + (theForm.items.get(i).getITMREQUI().equals("T") ? "*" : ""),theForm.items.get(i).getITMOPT());
	        		ll.addView((View) theForm.items.get(i).obj);
	        	}
	        	if (theForm.items.get(i).getCTRLID().equals(4)) {
	        		theForm.items.get(i).obj = new XmlGuiLabel(this, theForm.items.get(i).getITMLABEL() + (theForm.items.get(i).getITMREQUI().equals("T") ? "*" : ""));
	        		ll.addView((View) theForm.items.get(i).obj);
	        	}
	        	if (theForm.items.get(i).getCTRLID().equals(5)) {
	        		theForm.items.get(i).obj = new XmlGuiGps(this, theForm.items.get(i).getITMLABEL() + (theForm.items.get(i).getITMREQUI().equals("T") ? "*" : ""),"Obtiene GPS");
	        		ll.addView((View) theForm.items.get(i).obj);
	        	}
	        	if (theForm.items.get(i).getCTRLID().equals(6)) {
	        		theForm.items.get(i).obj = new XmlGuiCamera(this, theForm.items.get(i).getITMLABEL() + (theForm.items.get(i).getITMREQUI().equals("T") ? "*" : ""));
	        		ll.addView((View) theForm.items.get(i).obj);
	        	}
	        }	        
	        
	        Button btn = new Button(this);
	        btn.setBackgroundResource(R.xml.custom_button_green);
	        LayoutParams params = new LayoutParams(
	                LayoutParams.MATCH_PARENT,      
	                LayoutParams.MATCH_PARENT
	        );
	        ll.addView(btn);
	        params.setMargins(0,20,0,0);
	        btn.setLayoutParams(params);
	        
	        btn.setText("Submit");
	        btn.setOnClickListener(new Button.OnClickListener() {
	        	public void onClick(View v) {
	        		// Chequea si los campos requeridos no están vacios
	        		if (!CheckForm())
	        		{
	        			AlertDialog.Builder bd = new AlertDialog.Builder(ll.getContext());
	            		AlertDialog ad = bd.create();
	            		ad.setTitle("Error");
	            		ad.setMessage("Ingresa los campos (*)");
	            		ad.show();
	            		return;

	        		}
	        		//Si hay URL para enviar datos
	        		if (!theForm.getFRMURL().equals("")) {
	        			//Guarda datos ingresados
	        			if (!SaveDataForm()) {
		        			AlertDialog.Builder bd = new AlertDialog.Builder(ll.getContext());
		            		AlertDialog ad = bd.create();
		            		ad.setTitle("Error");
		            		ad.setMessage("Error en guardar datos ingresados");
		            		ad.show();
		            		return;
	        			}
	        			//Llama al resolutor
	        			if (!ResolForm.fnResolutor(theForm,context)) {
		        			AlertDialog.Builder bd = new AlertDialog.Builder(ll.getContext());
		            		AlertDialog ad = bd.create();
		            		ad.setTitle("Error");
		            		ad.setMessage("Error en resolución de datos por resolutor");
		            		ad.show();
		            		return;
	        			}
	        		}
	          		Intent newResult = new Intent(RunForm.this,ActResult.class);
					newResult.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					Bundle extras = new Bundle();
	        		extras.putString("formResultCode",formCode);
	        		extras.putInt("version",0);
	        		extras.putParcelable("theForm",theForm);
	        		newResult.putExtras(extras);
					startActivity(newResult);	
			        finish();
	        		
	        	}
	        } );	        
	        setContentView(sv);
	        setTitle(theForm.getFRMNAME()); 
	        return true;
		} catch (Exception e) {
			Log.e(tag,"Error mostrando el formulario:"+e.toString());
			return false;
		}
	}
	
	//Chequea campos requeridos 
	private boolean CheckForm()
	{
		try {
			int i;
			boolean good = true;
						
			for (i=0;i<theForm.items.size();i++) {
							
				if (theForm.items.get(i).getITMREQUI().equals("T")) {
					String itemValue = (String) theForm.items.get(i).getData();
					if (itemValue == null) {
						good = false;
					} else {
						if (itemValue.trim().length() == 0) {
							good = false;
						}
					}						
				}
			}
			return good;
		} catch(Exception e) {
			Log.e(tag,"Error en el checkform" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	//Guarda datos ingresados en la clase CItemData
	private boolean SaveDataForm()
	{
		int i;
		theForm.itemsData.clear() ;
		for (i=0;i<theForm.items.size();i++) {
        	CItemData tempItemData =  new CItemData();
        	tempItemData.setFRMID(theForm.getFRMID());
        	tempItemData.setITMID(theForm.items.get(i).getITMID());
        	tempItemData.setDITMVAL((String) theForm.items.get(i).getData());
			theForm.getItemsData().add(tempItemData);
        }
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}