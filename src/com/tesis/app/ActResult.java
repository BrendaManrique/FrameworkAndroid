package com.tesis.app;

import java.util.Vector;

import com.tesis.app.clases.CForm;
import com.tesis.app.dal.DBHandler;
import com.tesis.app.form.SendForm;
import com.tesis.app.utils.ResultArrayAdapter;
import com.tesis.app.utils.Utilitario;
import com.tesis.app.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActResult extends ListActivity {
	final String tag = ActResult.class.getSimpleName();
	CForm theForm;
	final Context context = this;
	String nombreArchivo="";
	String nombreDefecto = "";

	Vector<String> itmlabel = new Vector<String>();
	Vector<String> itmval = new Vector<String>();
	Vector<String> ditmval = new Vector<String>();
	Vector<String> ditmgood = new Vector<String>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_resultmain);
		theForm = new CForm();
		Float resval =(float) 0;
		String formResultCode = "";
		String strlabel ="";
		
        //START INTENT
        Intent startingIntent = getIntent();
        
        if(startingIntent == null) {
        	Log.e(tag,"Error, no existe llamada de Intent...");
        	finish();
        	return;
        }
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        formResultCode = extras.getString("formResultCode");
        
        theForm = extras.getParcelable("theForm");
        
        DBHandler dbh = new DBHandler (context);
		resval = dbh.actResult_getResval(theForm);
		strlabel = dbh.actResult_getStrlabel(theForm);
	    dbh.close();
	    
        TextView text = (TextView) findViewById(R.id.rpoints);
	    text.setText("PUNTAJE: "+resval.toString());
	
	    TextView text1 = (TextView) findViewById(R.id.rstrategy);
	    text1.setText(strlabel);
	        
	    String defpnt = "0";
	    String defvalFinal = ""; //Primer valor correcto
	    String defval = "";
	    String defvalant="";
	    String correcto = "F"; 
	    
	    //Extrae preguntas y respuestas
	    for (int i=0;i<theForm.items.size();i++) { 
	    	if (theForm.items.get(i).getITMSOLVE().equals("T"))
	    	{   //LABEL
	    		itmlabel.add(theForm.items.get(i).getITMLABEL());
	    		//VALOR CORRECTO
	    		defval="";
	    		for(int j=0;j<theForm.items.get(i).pnt.size();j++)
	    		{
	    			correcto = "F"; 
	    			//Saco el puntaje
	    			defpnt = theForm.items.get(i).pnt.get(j).getPNTNAME();
	    			
	    			if(!defpnt.equals("0")) //Si el puntaje no es 0
	    			{	defvalant=defval;
	    				correcto = "T";  //Si es correcto se almacena en defvalFinal
	    				//Saco el valor del puntaje
	    				defval = theForm.items.get(i).val.get(j).getVALNAME();
		    			
	    				//Si son varios valores
		    			if (theForm.items.get(i).getCTRLID().equals(3)) //Choice
		    			{	//El valor viene a ser el index de la opción
		    				int index = Integer.parseInt(defval);
		    				//Saco el nombre de la opción
		    				// Primer valor correcto reemplazado por opción
		    				defval = theForm.items.get(i).opt.get(index).getOPTNAME();
		    			}
	    			}
	    			else
	    			{
	    				correcto = "F"; 
	    			}
	    			if(j>0 && correcto == "T" && defvalant!="")
	    				defvalFinal += " , "+defval;
	    			if(j>0 && correcto == "T" && defvalant=="")
	    				defvalFinal += defval;

	    			if(j==0 && correcto == "T")
	    				defvalFinal = defval;
	    		}
	    		itmval.add(defvalFinal);
	    		defvalFinal="";
	    		
	    		//VALOR INGRESADO
	    		String ditmv = theForm.itemsData.get(i).getDITMVAL();
	    		if(theForm.items.get(i).getCTRLID().equals(3)) //Choice
	    		{
	    			int ind = Integer.parseInt(theForm.itemsData.get(i).getDITMVAL());
	    			ditmv = theForm.items.get(i).opt.get(ind).getOPTNAME();
	    		}
	    		if(theForm.items.get(i).getCTRLID().equals(6)) //Choice
	    		{
	    			if(ditmv.length()==0)
	    				ditmv = "NO GUARDADO" ;
	    			else
	    				ditmv = "GUARDADO";
	    		}
	    		ditmval.add(ditmv);
	    		
	    		//RPTA CORRECTA/INCORRECTA
	    		ditmgood.add(theForm.itemsData.get(i).getDITMGOOD());
	    	}else
	    	{
	    		//LABEL
	    		itmlabel.add(theForm.items.get(i).getITMLABEL());
	    		itmval.add("-");
	    		//VALOR INGRESADO
	    		String ditmv = theForm.itemsData.get(i).getDITMVAL();
	    		if(theForm.items.get(i).getCTRLID().equals(3)) //Choice
	    		{
	    			int ind = Integer.parseInt(theForm.itemsData.get(i).getDITMVAL());
	    			ditmv = theForm.items.get(i).opt.get(ind).getOPTNAME();
	    		}
	    		if(theForm.items.get(i).getCTRLID().equals(6)) //Choice
	    		{
	    			if(ditmv.length()==0)
	    				ditmv = "NO GUARDADO" ;
	    			else
	    				ditmv = "GUARDADO";
	    		}
	    		ditmval.add(ditmv);
	    		ditmgood.add("-");	    		
	    	}
	    }
	    //Pasar el vector al array
	    String[] sitmlabel = itmlabel.toArray(new String[itmlabel.size()]);
	    String[] sitmval = itmval.toArray(new String[itmval.size()]);
	    String[] sditmval = ditmval.toArray(new String[ditmval.size()]);
	    String[] sditmgood = ditmgood.toArray(new String[ditmgood.size()]);
	    
	    ListView lv = (ListView) findViewById(android.R.id.list);
	    ResultArrayAdapter adapter = new ResultArrayAdapter(this, sitmlabel,sitmval,sditmval,sditmgood);
	    lv.setAdapter(adapter);
	    lv.setTextFilterEnabled(true);
	    
	    //Setea nombre por defecto
	    DBHandler dbh1 = new DBHandler (context);	
	    nombreDefecto = dbh1.actResult_getName(theForm);
		dbh1.actResult_setName(theForm,nombreDefecto);
	    dbh1.close();
	    
	    
	    Button btnSaveResult = (Button) this.findViewById(R.id.btnSaveResult);
	    btnSaveResult.setVisibility(View.VISIBLE);
	    btnSaveResult.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				// PopUp de activity_act_nameform para guardar nombre de formulario
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.activity_act_nameform, null);
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setView(promptsView);
 
				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextName);
				userInput.setText(nombreDefecto);
				alertDialogBuilder
					.setTitle("Nombrar")
					.setCancelable(false)
					.setIcon(R.drawable.ic_dialog_in)
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						nombreArchivo = userInput.getText().toString();
						DBHandler dbh = new DBHandler (context);						
						boolean setNombre =  dbh.actResult_setName(theForm,nombreArchivo);
						dbh.backupDB(context);
					    dbh.close();
					    if(setNombre == true){
						    Toast alert = Toast.makeText(ActResult.this,"Nombre guardado!", Toast.LENGTH_LONG);
				    		alert.setGravity(Gravity.CENTER, 0, 0);
				    		alert.show();
					    }
					    }
					  });
//					.setNegativeButton("Cancel",
//					  new DialogInterface.OnClickListener() {
//					    public void onClick(DialogInterface dialog,int id) {
//						dialog.cancel();
//					    }
//					  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
 	
			}
		});

		 Button btnSendResult = (Button) this.findViewById(R.id.btnSendResult);
		 btnSendResult.setOnClickListener(new Button.OnClickListener()
			{
				public void onClick(View v)
				{
					boolean signalOn = Utilitario.fnVerSignal(context);
    				if (signalOn == true)
    				{

    					Intent sendForm = new Intent(ActResult.this,SendForm.class);
    					sendForm.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    					Bundle extras = new Bundle();
    	        		extras.putParcelable("theForm",theForm);
    	        		sendForm.putExtras(extras);
    				    startActivity(sendForm);	
    			        
    				} else{
    					Toast.makeText(ActResult.this, "No hay señal", Toast.LENGTH_SHORT).show();
    			    }
						

				}
			});
	}
}


