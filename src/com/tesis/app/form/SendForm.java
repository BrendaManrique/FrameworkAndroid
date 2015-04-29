package com.tesis.app.form;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.tesis.app.clases.CForm;
import com.tesis.app.clases.CMapper;
import com.tesis.app.utils.Utilitario;
import com.tesis.app.R;

import org.codehaus.jackson.map.ObjectMapper;

import android.provider.Settings.Secure;

public class SendForm extends Activity {
	static String tag = SendForm.class.getSimpleName(); //Solo para log
	ProgressDialog progressDialog;
	Handler progressHandler;
	CForm theForm;
	Context context = this;	
	ObjectMapper m = new ObjectMapper();
	String id_android="NA";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_send);
		id_android = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	    Log.i(tag, "Mi ID:" + id_android);
		theForm = new CForm();
	    Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        theForm = extras.getParcelable("theForm");

		final TextView text = (TextView) findViewById(R.id.textViewSend);
		
		try {
			//Dialogo de espera
            this.progressDialog = ProgressDialog.show(context, theForm.getFRMNAME(), "Saving Form Data", true,false);
            //Clase Handler es para compartir datos con diferentes hilos.
            this.progressHandler = new Handler() {
            	
            	//Crea la clase handler para usarla luego
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0: 
                            progressDialog.setMessage("" + (String) msg.obj);
                            break;
                        case 1:
                            progressDialog.cancel();
                            text.setText("DATOS ENVIADOS CORRECTAMENTE!");
                            break;
                        case 2:
                        	progressDialog.cancel();
                        	text.setText("INTENTE DE NUEVO.");
                        	break;
                    }
                    super.handleMessage(msg);
                }

            };
            //----INSTANCIA UN THREAD PARA TRANSMITIR DATA
            Thread workthread = new Thread(new TransmitFormData(theForm));
            workthread.start();			
			
		} catch (Exception e) {
			Log.e(tag,"Error in SubmitForm()::" + e.getMessage());
			e.printStackTrace();
            Message msg = new Message();
            msg.what = 1;
            this.progressHandler.sendMessage(msg);
		
		}	
	}
		
	//CONECTA AL SERVIDOR DESCRITO EN EL CAMPO URL
	private class TransmitFormData implements Runnable
	{
        CForm theForm = new CForm();
        Message msg;
        TransmitFormData(CForm form) {
            this.theForm = form;
        }
        
        //Clase que corre cuando se instanció un hilo
        public void run() {

            try { 
            	String numEquipo = Utilitario.fnNumEquipo(context);
            	
            	msg = new Message();
                msg.what = 0;
                msg.obj = ("Conectando al Servidor");
                progressHandler.sendMessage(msg);
                
                URL url = new URL(theForm.getFRMURL());
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
             
                dos.writeBytes(theForm.getFRMIDT()+"_"+theForm.getFRMID().toString()+"_"+theForm.result.get(0).getVERSION().toString()+"_"+id_android+":"+CMapper.toJson(theForm,true));
                dos.flush();
                msg = new Message();
                msg.what = 0;
                msg.obj = ("Data enviada");
                progressHandler.sendMessage(msg);
                InputStream is = conn.getInputStream();
                // RECIBE RESPUESTA DEL SERVIDOR
                int ch;

                StringBuffer b =new StringBuffer();
                while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
                String s = b.toString();
                    
                Boolean bSuccess = false;
                if (s.indexOf("SUCCESS") != -1) {
                    bSuccess = true;
                }
                  
                dos.close();
                
                if (bSuccess) {
                    msg = new Message();
                    msg.what = 0;
                    msg.obj = ("Formulario enviado correctamente");
                    progressHandler.sendMessage(msg);

                    msg = new Message();
                    msg.what = 1;
                    progressHandler.sendMessage(msg);
                    return;
                }

            } catch (Exception e) {
                Log.d(tag, "Falla al enviar data: " + e.getMessage());
                msg = new Message();
                msg.what = 0;
                msg.obj = ("Error enviando datos");
                progressHandler.sendMessage(msg);
            }
            msg = new Message();
            msg.what = 2;
            progressHandler.sendMessage(msg);
        }

	}

}