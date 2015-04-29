package com.tesis.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ActGeneral extends Activity{

	//---------------------------------------
		//propiedades para la conexion Http
		//---------------------------------------
		private String httpResultado;
		private int idHttpResultado;
		Context context = this ;
		
		public String getHttpResultado() {
			return httpResultado;
		}
		
		public void setHttpResultado(String psHttpResultado) {
			this.httpResultado = psHttpResultado;
		}
		
		public void setIdHttpResultado(int idHttpResultado) {
			this.idHttpResultado = idHttpResultado;
		}
		
		public int getIdHttpResultado() {
			return idHttpResultado;
		}
		//---------------------------------------
		//Propiedades para el mensaje luego de la conexion
		//---------------------------------------
		private String msgOk="Correcto";
		private String msgError="Atención";
		public void setMsgOk(String msgOk) {
			this.msgOk = msgOk;
		}
		public String getMsgOk() {
			return msgOk;
		}
		public void setMsgError(String msgError) {
			this.msgError = msgError;
		}
		public String getMsgError() {
			return msgError;
		}
		private AlertDialog.Builder  ioAlertDialog;		

		protected void SubSetControles()
		{}
		
		protected void SubIniActionBar() 
		{}
		
		protected void SubAccDesMensaje()
		{}		
		
		public void subHttpResultado()
		{
			ioAlertDialog = new AlertDialog.Builder(context);
			ioAlertDialog.setCancelable(false);
			ioAlertDialog.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {						
					    }
					  });
			
			switch (getIdHttpResultado()) {
				case 1:
					ioAlertDialog.setTitle(getMsgOk());
					ioAlertDialog.setMessage(getHttpResultado());
					ioAlertDialog.setIcon(R.drawable.ic_dialog_in);				
					break;
				case -1:
					ioAlertDialog.setTitle(getMsgError());
					ioAlertDialog.setMessage(getHttpResultado());
					ioAlertDialog.setIcon(R.drawable.ic_dialog_al);
					break;
				case 2:
					SubAccDesMensaje();
					return;
			}
			ioAlertDialog.show();
		}		
}
