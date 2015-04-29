package com.tesis.app;

import java.util.ArrayList;
import java.util.List;

import com.tesis.app.dal.DBHandler;
import com.tesis.app.form.RunForm;
import com.tesis.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

public class ActTipoEv extends Activity {
	final String tag = ActTipoEv.class.getSimpleName();
	List<String> listValues = new ArrayList<String>(); 
	List<String> listLabel = new ArrayList<String>(); 
	Context context = this;
	String itemSeleccionado="";
	long seleccionado;	
	String[][] list = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_tipev);
		Spinner sp = (Spinner) findViewById(R.id.spinner1);
		
		//Carga lista de formularios en memoria
		DBHandler dbh = new DBHandler (context);
	    list = dbh.actTipoEv_getList();
	    dbh.close();
 
		if( list != null)
		{
			if(list.length>=1)
			{	
				for (int i = 0; i < list.length; i++) {
				    //Almacena en control spinner el label de la lista
					listLabel.add(list[i][0]);
				    //Almacena el identificador del archivo
					listValues.add(list[i][1]);           
				}
			}else{
				Toast.makeText(ActTipoEv.this, "No posee formularios", Toast.LENGTH_LONG).show();
				finish();
			}
		}
		else {
			Toast.makeText(ActTipoEv.this, "No posee formularios", Toast.LENGTH_LONG).show();
			finish();
		}
	    //Arreglo de nombres es insertado en el control spinner 
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_spinner_item, listLabel);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sp.setAdapter(adapter);
        
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {            
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
            		int position, long id) {
            	seleccionado = parentView.getItemIdAtPosition(position); 
            	itemSeleccionado = listValues.get(position);
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
        });
        
		Button btnRunForm = (Button) this.findViewById(R.id.btnRunForm);		
		btnRunForm.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				if(itemSeleccionado == ""){
					Toast.makeText(ActTipoEv.this, "Seleccione un item", Toast.LENGTH_LONG).show();
				}
				else
				{
					int index = itemSeleccionado.lastIndexOf('.');
					if (index > 0)
						itemSeleccionado=itemSeleccionado.substring(0,index);
					Intent newRunForm = new Intent(ActTipoEv.this,RunForm.class);
					//Finaliza cuando se llame a otra activity
					newRunForm.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					Bundle extras = new Bundle();
	        		extras.putString("formNumber",itemSeleccionado);
	        		newRunForm.putExtras(extras);
					startActivity(newRunForm);
					finish();
				}							
			}
		});
	}
}




