package com.tesis.app.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


import android.content.Context;

import com.tesis.app.ActGeneral;
import com.tesis.app.R;
import com.tesis.app.dal.DBHandler;
import com.tesis.app.utils.Configuracion;
import com.tesis.app.utils.Utilitario;
import com.tesis.app.utils.Configuracion.EnumUrl;
public class HttpSincronizar extends HttpConexion {
	private Context ioContext;	
	String formIDT;
	
	public HttpSincronizar(String formSincCode, Context poContext) 
	{		
		super(poContext, poContext.getString(R.string.http_sincro));
		ioContext = poContext;
		formIDT = formSincCode;
	}
	
	@Override
	public String fnConectar()
	{
		SubConHttp();
		return null;
	}

	private void SubConHttp()
	{
		if(!Utilitario.fnVerSignal(ioContext))//OffLine
		{
			((ActGeneral)ioContext).setHttpResultado("No hay señal.");
			((ActGeneral)ioContext).setIdHttpResultado(-1);	
		}
		
		try { 
           	DBHandler dbh = new DBHandler (ioContext);
    	    boolean guardadoExitoso = dbh.sincForm_setForm(formIDT);
    	    dbh.backupDB(ioContext);
    	    dbh.close();
    	    if(guardadoExitoso){
    	    	((ActGeneral)ioContext).setHttpResultado( "Sincronización y guardado exitosos!");
    			((ActGeneral)ioContext).setIdHttpResultado(1);
                return;
    	    }
    	    else
    	    {
    	    	((ActGeneral)ioContext).setHttpResultado("No se puede sincronizar el formulario.");
    			((ActGeneral)ioContext).setIdHttpResultado(-1);
    	    }
    	    return;
    	 } catch (Exception e) {
    		 ((ActGeneral)ioContext).setHttpResultado("No se puede sincronizar el formulario.");
 			((ActGeneral)ioContext).setIdHttpResultado(-1);
         }
	}
	
	public static BufferedReader fnSincronizar(Context setcontext, EnumUrl poEnumUrl, String formSincCode){
		URL url;
		BufferedReader in = null ;
		try { 
			url = new URL(Configuracion.fnUrl(setcontext, poEnumUrl, formSincCode));
			in = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return in;
	}	
}
