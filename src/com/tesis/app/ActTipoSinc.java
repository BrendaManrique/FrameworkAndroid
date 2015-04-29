package com.tesis.app;

import com.tesis.app.http.HttpSincronizar;
import com.tesis.app.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActTipoSinc extends ActGeneral {
	
	final String tag = ActTipoSinc.class.getSimpleName();
	Context context = this;
	ProgressDialog progressDialog;
	Handler progressHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_tipsinc);
		     
		Button btnSincForm = (Button) this.findViewById(R.id.btnSincForm);		
		btnSincForm.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{				
				EditText formSincNumber = (EditText) findViewById(R.id.edtSincCodigo);
				if (!formSincNumber.getText().toString().isEmpty())
				{	SincForm(formSincNumber.getText().toString());				
				}else
				{	Toast.makeText(ActTipoSinc.this, "Ingrese nombre de Formulario", Toast.LENGTH_LONG).show();
				}
			}
		});		
	     
		final ImageView imgQR = (ImageView) findViewById(R.id.imageViewQR);
		imgQR.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	imgQR.setImageResource(R.drawable.draw_qrin);
		    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		        startActivityForResult(intent, 0);
		    }
		});	
	 }
	 
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	EditText formSincNumber = (EditText) findViewById(R.id.edtSincCodigo);
    	 ImageView imgQR = (ImageView) findViewById(R.id.imageViewQR);
    	 imgQR.setImageResource(R.drawable.draw_qr);
 	   if (requestCode == 0) {
 	      if (resultCode == RESULT_OK) {
 	         String contents = intent.getStringExtra("SCAN_RESULT");
 	        formSincNumber.setText(contents); 	        
 	      } else if (resultCode == RESULT_CANCELED) {
 	    	 Log.i(tag,"Escaneo cancelado");
 	      }
 	   }
 	}
    
    public void SincForm(String formSincCode) {
    	new HttpSincronizar(formSincCode,this).execute();
    }
}




