package com.tesis.app.http;

import com.tesis.app.ActGeneral;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

public class HttpConexion extends AsyncTask <Void, Void, String> {
	public Context ioContext;
	private ProgressDialog ioProgressDialog;
	private String isMensaje;
	
	//Constructor
	public HttpConexion(Context psClase, String psMensaje) {
		ioContext = psClase;
		isMensaje = psMensaje;	
	}
	
	@Override
	protected void onPreExecute() {		
		ioProgressDialog = ProgressDialog.show(ioContext, "", isMensaje, true);
		ioProgressDialog.setMessage(Html.fromHtml("<font color='black'>" + isMensaje + "</font>"));
	}
	@Override
	protected String doInBackground(Void... params){
		return fnConectar();
	}
	@Override
	protected void onPostExecute(String result) {
		if (ioProgressDialog != null) {
			ioProgressDialog.dismiss();
			((ActGeneral) ioContext).subHttpResultado();
		}
	}
	
	public String fnConectar()
	{
		return null;
	}	
	
	

}

