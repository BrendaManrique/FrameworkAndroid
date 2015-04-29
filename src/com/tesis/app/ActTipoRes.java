package com.tesis.app;

import java.util.ArrayList;
import java.util.List;

import com.tesis.app.dal.DBHandler;
import com.tesis.app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ActTipoRes extends Activity {
	final String tag = ActTipoRes.class.getSimpleName();
	List<String> listName = new ArrayList<String>();
	final Context context = this;	   
	long seleccionado;
	String selForm="";
	String selVersion="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_tipres);
		Spinner sp = (Spinner) findViewById(R.id.spinner1);
		final List<String> listForm = new ArrayList<String>();  
		final List<String> listVersion = new ArrayList<String>();  
		
		//Obtiene la lista de respuestas de formularios
	    DBHandler dbh = new DBHandler (context);	
	    String[][] lista = dbh.actTipoRes_getList();
		dbh.close();
		   
		if(lista.length>=1)
		{	
			for (int i = 0; i < lista.length; i++) {
			    listForm.add(lista[i][0]);
			    listVersion.add(lista[i][1]);
			    listName.add(lista[i][2]!=null?lista[i][2]:"FormSN");           
			}
		}else{
			Toast.makeText(ActTipoRes.this, "No posee formularios", Toast.LENGTH_LONG).show();
			finish();
		}
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_spinner_item, listName);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sp.setAdapter(adapter);
               
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {            
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	seleccionado = parentView.getItemIdAtPosition(position); 
            	selForm = listForm.get(position);
            	selVersion = listVersion.get(position);
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        });
        
		Button btnResForm = (Button) this.findViewById(R.id.btnResForm);		
		btnResForm.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				if(selForm == ""){
					Toast.makeText(ActTipoRes.this, "Seleccione un item", Toast.LENGTH_LONG).show();
				}
				else
				{
					Intent newRunForm = new Intent(ActTipoRes.this,ActResultD.class);
					newRunForm.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					Bundle extras = new Bundle();
	        		extras.putInt("formID",Integer.valueOf(selForm));
	        		extras.putInt("resVersion",Integer.valueOf(selVersion));
	        		newRunForm.putExtras(extras);
					startActivity(newRunForm);				
				}					
			}
		});
	}
}

