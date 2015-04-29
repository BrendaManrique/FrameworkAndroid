package com.tesis.app.utils; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;


public class Utilitario {

	static String tag = Utilitario.class.getSimpleName(); //Solo para log
	
	public static boolean fnVerSignal(Context poContext)
	{
		boolean lbResultado;
		boolean lbCon3g=false;
		boolean lbconData=false;
		boolean lbWifi=false;
		
		TelephonyManager loTelephonyManager; //Para ver redes 3G
		ConnectivityManager loConnectivityManager; //Para ver conectividad en general
		NetworkInfo loNetworkInfo;  //Para ver la informacion de la red
		loConnectivityManager = (ConnectivityManager) poContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		loTelephonyManager = (TelephonyManager) poContext.getSystemService(Context.TELEPHONY_SERVICE);
		
		if (loTelephonyManager.getDataState() == TelephonyManager.DATA_CONNECTED) {
			lbCon3g=true;
			
		} else if (loTelephonyManager.getDataState() == TelephonyManager.DATA_DISCONNECTED) {
			lbCon3g=false;
		}
		try 
		{
			//Verifica data
			loNetworkInfo = loConnectivityManager.getActiveNetworkInfo();
			if (loNetworkInfo.isConnected()) {
				lbconData=true;
			} else {
				lbconData=false;
			}
		} catch (SecurityException e) {
			lbconData=false;
		} catch (NullPointerException e){
			lbconData=false;
		}
		try 
		{
			//Verifica conexión Wifi
			NetworkInfo loNetworkInfo2 = loConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (loNetworkInfo2.isConnected()) {
				lbWifi=true;
			} else {
				lbWifi=false;
			}
		} catch (SecurityException e) {
			lbWifi=false;
		} catch (NullPointerException e){
			lbWifi=false;
		}
		lbResultado=lbCon3g || lbconData || lbWifi;
		return lbResultado;		
	}
	
	public static boolean fnVerStorage(){
		//-------------------------------CHEQUEA EXTERNAL STORAGE
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
	
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		//Si se puede escribir en memoria externa
		return (mExternalStorageAvailable) && (mExternalStorageWriteable);
	}
/*
	public static boolean fnVerOperador(Context poContext)
	{
		boolean lbResultado;
		if(fnVerSignal(poContext))
		{
			TelephonyManager loTelephonyManager = (TelephonyManager) poContext.getSystemService(Context.TELEPHONY_SERVICE);
			if(loTelephonyManager.getSimOperatorName().toUpperCase().equals("NEXTEL"))
			{
				lbResultado=true;
				SharedPreferences loSharedPreferences = poContext.getSharedPreferences(ConfiguracionNextel.CONSPREOPERADOR, Context.MODE_PRIVATE);
				SharedPreferences.Editor loEditor = loSharedPreferences.edit();;
	            loEditor.putString("Nextel","NEXTEL");
	            loEditor.commit();
			}
			else
			{
				lbResultado=true;//TODO (cambiar a false)
			}
		}
		else
		{
			if(poContext.getSharedPreferences(ConfiguracionNextel.CONSPREOPERADOR, Context.MODE_PRIVATE).getString("Nextel", "").equals("NEXTEL"))
			{
				lbResultado=true;
			}
			else
			{
				lbResultado=true;//TODO (cambiar a false)
			}
		}
		
		return lbResultado;
		
	}
	*/
	 
	public static String fnModeloEquipo(Context poContext)
	{
		String lsDispositivo=android.os.Build.BRAND + "-" + android.os.Build.MODEL;
		return Uri.encode(lsDispositivo);
	}
	
	public static String fnNumEquipo(Context poContext)
	{
		String lsRespuesta;
		TelephonyManager loTelephonyManager =(TelephonyManager)poContext.getSystemService(poContext.TELEPHONY_SERVICE);
		lsRespuesta = loTelephonyManager.getLine1Number();
		return lsRespuesta;
	}
	
	public static String fnNumIMEI(Context poContext)
	{
		String lsRespuesta;
		TelephonyManager loTelephonyManager =(TelephonyManager)poContext.getSystemService(Context.TELEPHONY_SERVICE);
		lsRespuesta = loTelephonyManager.getDeviceId();
		return lsRespuesta;
	}
	
	public static String fnNumSim(Context poContext)
	{
		String lsRespuesta;
		TelephonyManager loTelephonyManager =(TelephonyManager)poContext.getSystemService(Context.TELEPHONY_SERVICE);
		lsRespuesta = loTelephonyManager.getSimSerialNumber();
		return lsRespuesta;
	}
	
	public static void backupDatabase() throws IOException {
	    //Open your local db as the input stream
	    String inFileName = "/data/data/pe.com.nextel.tailoy/databases/database.sqlite";
	    File dbFile = new File(inFileName);
	    FileInputStream fis = new FileInputStream(dbFile);

	    String outFileName = Environment.getExternalStorageDirectory()+"/database.sqlite";
	    //Open the empty db as the output stream
	    OutputStream output = new FileOutputStream(outFileName);
	    //transfer bytes from the inputfile to the outputfile
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = fis.read(buffer))>0){
	        output.write(buffer, 0, length);
	    }
	    //Close the streams
	    output.flush();
	    output.close();
	    fis.close();
	}

	public static double fnRedondeo(double pdValor, int piPrecision)
	{
	    BigDecimal bd = new BigDecimal(pdValor);
	    BigDecimal rounded = bd.setScale(piPrecision, BigDecimal.ROUND_HALF_UP);
	    return rounded.doubleValue();
	}
	
	public static String fnFechaFormato(String psFecha)
	{
		StringBuffer loSB=new StringBuffer();
		loSB.append(psFecha.substring(6,8));
		loSB.append("/");
		loSB.append(psFecha.substring(4,6));
		loSB.append("/");
		loSB.append(psFecha.substring(0,4));
		return loSB.toString();
	}
	
	public static String fnVersion(Context poContext)
	{
		String lsVersion="";
		PackageInfo loPackageInfo=null;
		try {
			loPackageInfo = poContext.getPackageManager().getPackageInfo(poContext.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		//lsVersion=poContext.getString(R.string.actlogin_lblversion) + " : "+ loPackageInfo.versionName;
		//lsVersion=String.format(poContext.getString(R.string.actlogin_lblversion) , loPackageInfo.versionName);
		return lsVersion;
	}
	/*
	public String fnDireccionPorLocacion(Context poContext, Location poLocation) {
		String lsDireccion=poContext.getString(R.string.msg_dirnoencontrada);
        if (poLocation == null) {
            return null;
        }
        int liMaxResultado = 1;
        Geocoder loGeocoder = new Geocoder(poContext, Locale.getDefault());
        List<Address> loListaDirecciones=null;
		try 
		{
			loListaDirecciones = loGeocoder.getFromLocation(poLocation.getLatitude(), poLocation.getLongitude(), liMaxResultado);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(loListaDirecciones!=null)
		{
	        if (loListaDirecciones.size() == 1) 
	        {
	        	Address loAddress= loListaDirecciones.get(0);
	        	lsDireccion = loAddress.getAddressLine(0) + "," + loAddress.getLocality()+ " " +  loAddress.getAdminArea() + "/ " + loAddress.getCountryName();
	        }
		}
		return lsDireccion;
    }
    */
	
	public static String fnNomCarTxt(String psNomAplicacion)
	{
		return "/Prueba/"+  psNomAplicacion + Constantes.CONSSINNOMBRECARPETA ;
	}
}
