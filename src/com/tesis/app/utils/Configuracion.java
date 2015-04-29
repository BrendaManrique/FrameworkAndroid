package com.tesis.app.utils;

import com.tesis.app.R;
import android.content.Context;

public class Configuracion {

	//-- ENUM URL
	public static enum EnumUrl {LOGIN,SINCRONIZAR,SINCRONIZARFORM};
		
	//-- LISTA OPCIONES MENU
	public static Integer[] LstOpcMenu = {
			R.string.actMenu_Sincronizar,
			R.string.actMenu_Evaluar,
			R.string.actMenu_Resultados
	};
	
	//-- LISTA ICONOS MENU: Deben corresponder en orden con las opc. de menu
	public static Integer[] LstIcoMenu = {		
			//R.drawable.ico_menu_sincro, 
			R.drawable.draw_refresh,
			R.drawable.draw_evaluate,
	        R.drawable.draw_result
	};
	
	public static String fnUrl(Context poContext, EnumUrl poEnumUrl , String formSincCode)
	{
		String lsUrl = "";
		String lsRuta = "http://" + poContext.getString(R.string.app_urlserver) + Constantes.RUTA;
		String lsRuta1 = "http://" + poContext.getString(R.string.app_urlserver) + Constantes.RUTA1;
		
		if(poEnumUrl == EnumUrl.LOGIN)
		{
			lsUrl = lsRuta + Constantes.LOGIN ;
		}
		else if(poEnumUrl == EnumUrl.SINCRONIZAR)
		{
			lsUrl = lsRuta1 + Constantes.SINCRONIZAR;
		}
		else if(poEnumUrl == EnumUrl.SINCRONIZARFORM)
		{
			lsUrl = lsRuta1 + formSincCode+ ".txt";
		}
		return lsUrl;
	}
}
