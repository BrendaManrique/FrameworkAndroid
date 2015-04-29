package com.tesis.app.utils;

public class Constantes {

	public final static int TIMEOUT=120000;
	/*-----------------------------------------------------
	 MANEJO DE CONSTANTES
	 -----------------------------------------------------*/
	public final static int CONSRESSERVIDOROKNOMSG = 2;
	public final static int CONSRESSERVIDOROK = 1;
	public final static int CONSRESSERVIDORERROR = -1;
	public final static int CONSRESSERVIDORALGUNERROR = 0;
	
	/*-----------------------------------------------------
	 CONSTANTES PREFERENCAS (GENERAL)
	-----------------------------------------------------*/
	public final static String CONSPREFERENCIA="Actual";
	public final static String CONSPREOPERADOR="operador";
	
	/*-----------------------------------------------------
	 SINCRONIZACION
	 -----------------------------------------------------*/
	public final static String CONSSINNOMBRETXT="sincro.txt";
	public final static String CONSSINNOMBRECARPETA="/txt/";
	public final static String CONSIMGNOMBRECARPETA="/Img/";
	public final static String CONSFOTNOMBRECARPETA="/Foto/";
	public final static String CONSIMGNOMBREZIP = "imagenes.zip";
	
	public final static String LOGIN ="Login.ashx";
	public final static String SINCRONIZAR ="ScriptDB.txt";
	public final static String RUTA="/Android/";
	public final static String RUTA1="/Scripts/";
	
	/*-----------------------------------------------------
	 ROLES DE PREFERNCIAS
	 -----------------------------------------------------*/
	public static enum EnumRolPreferencia {ADMINISTRADOR,USUARIO};	
	
	/*-----------------------------------------------------
	 TIPO DE BUSQUEDA
	 -----------------------------------------------------*/
	public static enum EnumTipBusqueda {NOMBRE,CODIGO,OTRO,OTRO2,OTRO3};
	
	/*-----------------------------------------------------
	 FLUJO, cuando se reutiliza varias actividades pero en diferentes flujos
	 -----------------------------------------------------*/
	public static enum EnumFlujo {FLUJO1,FLUJO2,FLUJO3,FLUJO4};
	
	/*-----------------------------------------------------
	 Estado de la transaccion
	 -----------------------------------------------------*/
	public static enum EnumEstBean {NUEVO,NOENVIADO,ENVIADO};
	
	//-- TIPO DE CONECCIONES
	public static enum EnumTipConexion {CONHTTP};
}
