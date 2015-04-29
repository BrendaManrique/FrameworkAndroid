package com.tesis.app.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import com.tesis.app.clases.CForm;
import com.tesis.app.clases.CItem;
import com.tesis.app.clases.CItemData;
import com.tesis.app.clases.CItemOpt;
import com.tesis.app.clases.CItemPnt;
import com.tesis.app.clases.CItemVal;
import com.tesis.app.clases.CResult;
import com.tesis.app.clases.CStrategy;
import com.tesis.app.http.HttpSincronizar;
import com.tesis.app.utils.Configuracion.EnumUrl;
import com.tesis.app.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper { 
    private static final int DATABASE_VERSION =  15;
    private static final String DATABASE_NAME =  "DB_DATILEv1.db";
    static Context setcontext =null; 
    final String tag = DBHandler.class.getSimpleName();
    String PATH = "";
       
      
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setcontext = context;
        PATH = context.getDatabasePath(DBHandler.DATABASE_NAME).toString(); 
        Log.i(tag,"Direccion: "+ PATH);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String script ="";
	
    	try {      		
			BufferedReader in =  HttpSincronizar.fnSincronizar(setcontext, EnumUrl.SINCRONIZAR,"");
			String inputLine;					
			while ((inputLine = in.readLine()) != null)
			{	script = inputLine;
				db.execSQL(script);
			     Log.i(tag,script);
			}
			in.close();
		}
    	catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 Log.i(tag,"create: "+e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			 Log.i(tag,"create: "+e.toString());
		}	
    
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
        onCreate(db);
    }
    
  //************************* Métodos SincForm **************************************
    //Inserta elementos de CForm en BD.
    public boolean sincForm_setForm(String formSincCode)
    {    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	String script ="";
    	try{
    		BufferedReader in =  HttpSincronizar.fnSincronizar(setcontext, EnumUrl.SINCRONIZARFORM,formSincCode);
    		String inputLine;					
			while ((inputLine = in.readLine()) != null)
			{	script = inputLine;
				db.execSQL(script);
			}
			in.close();
	    }catch (Exception e)
		{
			return false;
		}
        db.close(); 
		return true;
    }
    
    //************************* Métodos ActTipoEv **************************************
    public String[][] actTipoEv_getList()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	int i=0;
    	String[][] list = null;
    	try{
	    	Cursor cur = db.rawQuery("SELECT FRMNAME, FRMIDT FROM TBM_FORM WHERE FRMACTIV='T';", null);  
	    	
	    	if (cur != null && cur.moveToFirst()) {    
	    		list = new String [cur.getCount()][2];
				do {
					list[i][0]=cur.getString(0);
					list[i][1]=cur.getString(1);
					i++;
	    	     } while(cur.moveToNext());    	     
	    	}
	    }catch (Exception e)
		{
			Log.i(tag,e.toString());
			return list;
		}
    	db.close(); 
    	return list;
    }
     
  //************************* Métodos RunForm **************************************
    //Recupera registros de la BD para almacenarlo en la clase CForm y evaluar
    public CForm runForm_getForm(CForm form, String formCode)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Integer frmversi = 0;
    	Integer frmid=0;
    	CForm theForm = new CForm();
    	try{		
    	
    	//TBM_FORM
    	Cursor c = db.rawQuery("SELECT FRMID, FRMNAME, FRMURL, FRMVERSI FROM TBM_FORM WHERE FRMIDT='"+formCode+"' AND FRMACTIV='T';", null);  
    	//Nos aseguramos de que existe al menos un registro
    	if (c != null && c.moveToFirst()) {
    		theForm.setFRMID(c.getInt(0));
    		frmid =c.getInt(0);
    		theForm.setFRMIDT(formCode);
			theForm.setFRMNAME(c.getString(1));
			theForm.setFRMURL(c.getString(2));
			theForm.setFRMVERSI(c.getInt(3));
			frmversi = c.getInt(3);
    	}
   
    	int itmid=0;
        int i=0;
        int cont=0;
        //TBM_ITEM 
    	Cursor cur = db.rawQuery("SELECT ITMID, ITMNAME, ITMLABEL, CTRLID, ITMREQUI, ITMOPT, ITMSOLVE, " +
    			"TSOLID, ITMVAL, ITMPNT FROM TBM_ITEM WHERE FRMID="+frmid+";", null);  
    	if (cur != null && cur.moveToFirst()) {    	
			//Recorremos el cursor hasta que no haya más registros
    	     do {
				CItem tempItem =  new CItem();
				tempItem.setITMID(cur.getInt(0));
				itmid = cur.getInt(0);
				tempItem.setITMNAME(cur.getString(1));
				tempItem.setITMLABEL(cur.getString(2));
				tempItem.setCTRLID(cur.getInt(3));
				tempItem.setITMREQUI(cur.getString(4));
				tempItem.setITMSOLVE(cur.getString(6));
				tempItem.setTSOLID(cur.getInt(7));				
				theForm.getItems().add(tempItem);
				
				i=0;
				String itmopt="";
				Cursor cur1 = db.rawQuery("SELECT OPTID, OPTINDEX, OPTNAME FROM TBM_ITMOPT WHERE ITMID="+itmid+";", null);  
		    	if (cur1 != null && cur1.moveToFirst()) {    	
					//Recorremos el cursor hasta que no haya más registros
		    	     do {
						CItemOpt tempItemOpt =  new CItemOpt();
						tempItemOpt.setOPTID(cur1.getInt(0));
						tempItemOpt.setITMID(itmid);
						tempItemOpt.setOPTINDEX(cur1.getInt(1));
						tempItemOpt.setOPTNAME(cur1.getString(2));
						theForm.items.get(cont).getOpt().add(tempItemOpt);
						if(i==0)
							itmopt += cur1.getString(2);
						else
							itmopt += "|" + cur1.getString(2);
						
						i++;		  
		    	     } while(cur1.moveToNext());		    	     
		    	}
		    	//Para llenar array del choice
		    	theForm.items.get(cont).setITMOPT(itmopt);
		    	
		    	i=0;
				Cursor cur2 = db.rawQuery("SELECT VALID, VALINDEX, VALNAME FROM TBM_ITMVAL WHERE ITMID="+itmid+";", null);  
		    	if (cur2 != null && cur2.moveToFirst()) {    	
					//Recorremos el cursor hasta que no haya más registros
		    	     do {
						CItemVal tempItemVal =  new CItemVal();
						tempItemVal.setVALID(cur2.getInt(0));
						tempItemVal.setITMID(itmid);
						tempItemVal.setVALINDEX(cur2.getInt(1));
						tempItemVal.setVALNAME(cur2.getString(2));
						theForm.items.get(cont).getVal().add(tempItemVal);
						i++;		  
		    	     } while(cur2.moveToNext());		    	     
		    	}
		    	
		    	i=0;
				Cursor cur3 = db.rawQuery("SELECT PNTID, PNTINDEX, PNTNAME FROM TBM_ITMPNT WHERE ITMID="+itmid+";", null);  
		    	if (cur3 != null && cur3.moveToFirst()) {    	
					//Recorremos el cursor hasta que no haya más registros
		    	     do {
						CItemPnt tempItemPnt =  new CItemPnt();
						tempItemPnt.setPNTID(cur3.getInt(0));
						tempItemPnt.setITMID(itmid);
						tempItemPnt.setPNTINDEX(cur3.getInt(1));
						tempItemPnt.setPNTNAME(cur3.getString(2));
						theForm.items.get(cont).getPnt().add(tempItemPnt);
						i++;		  
		    	     } while(cur3.moveToNext());		    	     
		    	}
  
		    cont++;
    	     } while(cur.moveToNext());
    	}
    
    	
    	int version = 0;
		//TBD_ITEM 
		Cursor ver = db.rawQuery("SELECT VERSION FROM TBD_ITEM WHERE FRMID="+frmid+" ORDER BY VERSION DESC LIMIT 1;", null); 
		if (ver != null && ver.moveToFirst()) {
	          version = (int) ver.getInt(0); 
	      }
    	
      Cursor curso = db.rawQuery("SELECT DITMID, FRMID, VERSION, ITMID, DITMVAL, DITMPNT, DITMGOOD " +
    			" FROM TBD_ITEM WHERE FRMID="+frmid+" AND VERSION="+version+";", null);  
    	
    	if (curso != null && curso.moveToFirst()) {    	
			//Recorremos el cursor hasta que no haya más registros
    	     do {
				CItemData tempItemD =  new CItemData();
				tempItemD.setDITMID(curso.getInt(0));
				tempItemD.setFRMID(curso.getInt(1));
				tempItemD.setVERSION(curso.getInt(2));
				tempItemD.setITMID(curso.getInt(3));
				tempItemD.setDITMVAL(curso.getString(4));
				tempItemD.setDITMPNT(curso.getString(5));
				tempItemD.setDITMGOOD(curso.getString(6));
				theForm.getItemsData().add(tempItemD);
    	     } while(curso.moveToNext());
    	}
    	
    	//TBM_STRATEGY
    	Cursor curs = db.rawQuery("SELECT STRID,FRMID,STRNAME,STRLABEL,STRTSOL,STRVAL FROM TBM_STRATEGY " +
    			"WHERE FRMID="+frmid+";", null); 
    	
    	if (curs != null && curs.moveToFirst()) {   
			//Recorremos el cursor hasta que no haya más registros
    	     do {
				CStrategy tempStrategy =  new CStrategy();
				tempStrategy.setSTRID(curs.getInt(0));
				tempStrategy.setFRMID(curs.getInt(1));
				tempStrategy.setSTRNAME(curs.getString(2));
				tempStrategy.setSTRLABEL(curs.getString(3));
				tempStrategy.setSTRTSOL(curs.getInt(4));
				tempStrategy.setSTRVAL(curs.getString(5));
				theForm.getStrategy().add(tempStrategy);
 
    	     } while(curs.moveToNext());
    	}
    	}catch (Exception e)
    	{
    		Log.i(tag,e.toString());
    	}
    	db.close(); 
		return theForm;    	
    	
    }
  //************************* Métodos ResolForm **************************************
    public void resolForm_setItemsData(CForm theForm)
    {   SQLiteDatabase db = this.getWritableDatabase();
    	Integer frmid=0;
    	Integer itmid=0;
    	Integer version=0;
    	String ditmval="";
    	String ditmpnt="";
    	String ditmgood="";
    	try{
	      	//El contador de filas me dice cuántas veces corrí el formulario
	        Cursor c = db.rawQuery("SELECT COUNT(*) FROM TBD_RESULT WHERE FRMID="+theForm.getFRMID()+";",null);
	        if (c != null && c.moveToFirst()) {
	            version = c.getInt(0); 
	        }
	    	version = version + 1;
	    	
	    	for (int i=0;i<theForm.items.size();i++) { 
	    		frmid = theForm.itemsData.get(i).getFRMID();
	    		itmid = theForm.itemsData.get(i).getITMID();
	    		ditmval = theForm.itemsData.get(i).getDITMVAL();
	    		ditmpnt = theForm.itemsData.get(i).getDITMPNT();
	    		ditmgood = theForm.itemsData.get(i).getDITMGOOD();
	    		
	    		String INSERT_TBD_ITEM = "INSERT INTO TBD_ITEM (FRMID, VERSION, ITMID, DITMVAL, DITMPNT, DITMGOOD) values ("+ 
	    				frmid +","+ 
	    				version +","+ 
	    				itmid +",'"+ 
	    				ditmval +"','"+ 
	    				ditmpnt +"','"+
	    				ditmgood +"');";    	
	        	db.execSQL(INSERT_TBD_ITEM);
    	}
	    }catch (Exception e)
		{
			Log.i(tag,e.toString());
		}
        db.close(); 
    }
       
    public void resolForm_setResult(CForm theForm ,Float result, String strname)
    {  
    	Integer idStrategy =0;
    	Integer version=0;
    	SQLiteDatabase db = this.getWritableDatabase();
    	try{
	    	Cursor cur = db.rawQuery("SELECT STRID FROM TBM_STRATEGY WHERE " +
	    	   		"FRMID="+theForm.getFRMID()+" AND " +
	    	   		"STRNAME='"+strname+"';" ,null);
	        if (cur != null && cur.moveToFirst()) {
	               idStrategy = (int) cur.getLong(0); 
	        }
	      	//El contador de filas me dice cuántas veces corrí el formulario
	        Cursor c = db.rawQuery("SELECT COUNT(*) FROM TBD_RESULT WHERE FRMID="+theForm.getFRMID()+";",null);
	        if (c != null && c.moveToFirst()) {
	            version = c.getInt(0); 
	        }
	    	version = version + 1;
	    	
	        String INSERT_TBD_RESULT = "INSERT INTO TBD_RESULT (FRMID, VERSION, STRID, RESVAL) values ("+
	    			theForm.getFRMID() +","+ 
					version +","+ 
					idStrategy +",'"+ 
					result +"');";    	
	    	db.execSQL(INSERT_TBD_RESULT);   	
	    }catch (Exception e)
		{
			Log.i(tag,e.toString());
		}
    	db.close();
	}
  //************************* Métodos  ActResult**************************************
    public String actResult_getStrlabel(CForm theForm)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Integer strid = 0;
    	String strlabel = "";
    	try{
	        Cursor c = db.rawQuery("SELECT STRID FROM TBD_RESULT WHERE RESID=" +
	    				"(SELECT RESID FROM TBD_RESULT WHERE FRMID="+theForm.getFRMID()+" ORDER BY VERSION DESC LIMIT 1);", null);  
	    	//Nos aseguramos de que existe al menos un registro
	    	if (c != null && c.moveToFirst()) {
	    		strid =c.getInt(0);
	    	}
	    	
	    	 Cursor cu = db.rawQuery("SELECT STRLABEL FROM TBM_STRATEGY WHERE FRMID="+theForm.getFRMID()+" AND STRID="+strid+";", null);  
	     	//Nos aseguramos de que existe al menos un registro
	     	if (cu != null && cu.moveToFirst()) {
	     		strlabel =cu.getString(0);
     	}
	    }catch (Exception e)
		{
			Log.i(tag,e.toString());
			return "";
		}
    	db.close();
		return strlabel;     	
    }
    
    public Float actResult_getResval(CForm theForm)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Float resval = (float) 0;
    	try{
	        Cursor c = db.rawQuery("SELECT RESVAL FROM TBD_RESULT WHERE RESID=" +
	    				"(SELECT RESID FROM TBD_RESULT WHERE FRMID="+theForm.getFRMID()+" ORDER BY VERSION DESC LIMIT 1);", null);  
	    	//Nos aseguramos de que existe al menos un registro
	    	if (c != null && c.moveToFirst()) {
	    		resval =c.getFloat(0);
	    	}
	    }catch (Exception e)
		{
			Log.i(tag,e.toString());
		}
    	db.close();
		return resval;    	
    }   
    
    public boolean actResult_setName(CForm theForm, String formName)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	try{
    		String UPDATE_TBD_RESULT = "UPDATE TBD_RESULT SET RESNAME ='"+formName+"' WHERE RESID=" +
    				"(SELECT RESID FROM TBD_RESULT WHERE FRMID="+theForm.getFRMID()+" ORDER BY VERSION DESC LIMIT 1);";
  	      db.execSQL(UPDATE_TBD_RESULT);
  	      
  	      //Llena TBD_RESULT
      	Cursor curs = db.rawQuery("SELECT RESID, FRMID, STRID, RESVAL, VERSION, RESNAME FROM TBD_RESULT WHERE RESID=" +
      			"(SELECT RESID FROM TBD_RESULT WHERE FRMID="+theForm.getFRMID()+" ORDER BY VERSION DESC LIMIT 1);", null); 
      	
      	if (curs != null && curs.moveToFirst()) {   
  			//Recorremos el cursor hasta que no haya más registros
      	     do {
  				CResult tempResult =  new CResult();
  				tempResult.setRESID(curs.getInt(0));
  				tempResult.setFRMID(curs.getInt(1));
  				tempResult.setSTRID(curs.getInt(2));
  				tempResult.setRESVAL(curs.getFloat(3));
  				tempResult.setVERSION(curs.getInt(4));
  				tempResult.setRESNAME(curs.getString(5));
  				theForm.getResult().add(tempResult);
   
      	     } while(curs.moveToNext());
      	}
  	      
    	}catch (Exception e) {
			Log.i(tag,e.toString());
			return false;
		}
    	db.close();    
    	return true;    	
    }

    public String actResult_getName(CForm theForm)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	int frmid=0 ;
 		int version=0;
 		String formName="";
    	try{
    		 Cursor c = db.rawQuery("SELECT FRMID, VERSION FROM TBD_RESULT WHERE FRMID="+theForm.getFRMID()+" ORDER BY VERSION DESC LIMIT 1;", null);  
     		//Nos aseguramos de que existe al menos un registro
	     	if (c != null && c.moveToFirst()) {
	     		frmid =c.getInt(0);
	     		version =c.getInt(1);
	     	}	
	     	formName = "FRM"+frmid+"RES"+version;
    	}catch (Exception e) {
			Log.i(tag,e.toString());
			return "";
		}
    	db.close();    
    	return formName;    	
    }
    //************************* ActTipoRes  **************************************
    public String[][] actTipoRes_getList()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	String[][] resName = null;
    	if(ifTableExists(db, "TBD_RESULT"))
    	{
    		int count=0;  
    		Cursor c1 = db.rawQuery("SELECT COUNT(*) FROM TBD_RESULT;", null);  
     		//Nos aseguramos de que existe al menos un registro
	     	if (c1 != null && c1.moveToFirst()) {
	     		count =c1.getInt(0);
	     	}
	     	
		    resName = new String[count][3];
		    
		     try{     	
	    		 Cursor c = db.rawQuery("SELECT FRMID, VERSION, RESNAME FROM TBD_RESULT;", null); 
	    		 
	    		 //Nos aseguramos de que existe al menos un registro
	    		 int i=0;
		     	if (c != null && c.moveToFirst()) {
		     		 do {
		     			resName[i][0] =String.valueOf(c.getInt(0));
			     		resName[i][1] =String.valueOf(c.getInt(1));
			     		resName[i][2] = c.getString(2);
			     		i++;
		     	     } while(c.moveToNext());
		     	}
	    	}catch (Exception e) {
				Log.i(tag,e.toString());
				return resName;
			} 
    	}
    	db.close();   
    	return resName;   
    }
  //************************* ActResultD  **************************************
    public CForm actResultD_getForm(CForm theForm, int formID, int version)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    
    	try{   		
    	
    	//TBM_FORM
    	Cursor c = db.rawQuery("SELECT FRMID,  FRMNAME, FRMURL, FRMIDT FROM TBM_FORM WHERE FRMID="+formID+";", null);  
    	//Nos aseguramos de que existe al menos un registro
    	if (c != null && c.moveToFirst()) {
    		theForm.setFRMID(c.getInt(0));
			theForm.setFRMNAME(c.getString(1));
			theForm.setFRMURL(c.getString(2));
			theForm.setFRMIDT(c.getString(3));
    	}
    	    
    	int itmid=0;
        int i=0;
        int cont=0;
        //TBM_ITEM 
    	Cursor cur = db.rawQuery("SELECT ITMID, ITMNAME, ITMLABEL, CTRLID, ITMREQUI, ITMOPT, ITMSOLVE, " +
    			"TSOLID, ITMVAL, ITMPNT FROM TBM_ITEM WHERE FRMID="+formID+";", null);  
    	if (cur != null && cur.moveToFirst()) {    	
			//Recorremos el cursor hasta que no haya más registros
    	     do {
				CItem tempItem =  new CItem();
				tempItem.setITMID(cur.getInt(0));
				itmid = cur.getInt(0);
				tempItem.setITMNAME(cur.getString(1));
				tempItem.setITMLABEL(cur.getString(2));
				tempItem.setCTRLID(cur.getInt(3));
				tempItem.setITMREQUI(cur.getString(4));
				tempItem.setITMSOLVE(cur.getString(6));
				tempItem.setTSOLID(cur.getInt(7));				
				theForm.getItems().add(tempItem);
				
				i=0;
				String itmopt="";
				Cursor cur1 = db.rawQuery("SELECT OPTID, OPTINDEX, OPTNAME FROM TBM_ITMOPT WHERE ITMID="+itmid+";", null);  
		    	if (cur1 != null && cur1.moveToFirst()) {    	
					//Recorremos el cursor hasta que no haya más registros
		    	     do {
						CItemOpt tempItemOpt =  new CItemOpt();
						tempItemOpt.setOPTID(cur1.getInt(0));
						tempItemOpt.setITMID(itmid);
						tempItemOpt.setOPTINDEX(cur1.getInt(1));
						tempItemOpt.setOPTNAME(cur1.getString(2));
						theForm.items.get(cont).getOpt().add(tempItemOpt);
						if(i==0)
							itmopt += cur1.getString(2);
						else
							itmopt += "|" + cur1.getString(2);
						
						i++;		  
		    	     } while(cur1.moveToNext());		    	     
		    	}
		    	//Para llenar array del control que lo solicite 
		    	theForm.items.get(cont).setITMOPT(itmopt);
		    	
		    	i=0;
				Cursor cur2 = db.rawQuery("SELECT VALID, VALINDEX, VALNAME FROM TBM_ITMVAL WHERE ITMID="+itmid+";", null);  
		    	if (cur2 != null && cur2.moveToFirst()) {    	
					//Recorremos el cursor hasta que no haya más registros
		    	     do {
						CItemVal tempItemVal =  new CItemVal();
						tempItemVal.setVALID(cur2.getInt(0));
						tempItemVal.setITMID(itmid);
						tempItemVal.setVALINDEX(cur2.getInt(1));
						tempItemVal.setVALNAME(cur2.getString(2));
						theForm.items.get(cont).getVal().add(tempItemVal);
						i++;		  
		    	     } while(cur2.moveToNext());		    	     
		    	}
		    	
		    	i=0;
				Cursor cur3 = db.rawQuery("SELECT PNTID, PNTINDEX, PNTNAME FROM TBM_ITMPNT WHERE ITMID="+itmid+";", null);  
		    	if (cur3 != null && cur3.moveToFirst()) {    	
					//Recorremos el cursor hasta que no haya más registros
		    	     do {
						CItemPnt tempItemPnt =  new CItemPnt();
						tempItemPnt.setPNTID(cur3.getInt(0));
						tempItemPnt.setITMID(itmid);
						tempItemPnt.setPNTINDEX(cur3.getInt(1));
						tempItemPnt.setPNTNAME(cur3.getString(2));
						theForm.items.get(cont).getPnt().add(tempItemPnt);
						i++;		  
		    	     } while(cur3.moveToNext());		    	     
		    	}
  
		    cont++;
    	     } while(cur.moveToNext());
    	}
    	
    	//TBD_ITEM
       //Llama desde ActResult directamente de botón Resultado
    	Cursor curso = db.rawQuery("SELECT DITMID, FRMID, VERSION, ITMID, DITMVAL, DITMPNT, DITMGOOD " +
    			" FROM TBD_ITEM WHERE FRMID="+formID+" AND VERSION="+version+";", null);  
    	
    	if (curso != null && curso.moveToFirst()) {    	
			//Recorremos el cursor hasta que no haya más registros
    	     do {
				CItemData tempItemD =  new CItemData();
				tempItemD.setDITMID(curso.getInt(0));
				tempItemD.setFRMID(curso.getInt(1));
				tempItemD.setVERSION(curso.getInt(2));
				tempItemD.setITMID(curso.getInt(3));
				tempItemD.setDITMVAL(curso.getString(4));
				tempItemD.setDITMPNT(curso.getString(5));
				tempItemD.setDITMGOOD(curso.getString(6));
				theForm.getItemsData().add(tempItemD);
    	     } while(curso.moveToNext());
    	}
    	
	      // TBD_RESULT
    	Cursor cursor = db.rawQuery("SELECT RESID, FRMID, STRID, RESVAL, VERSION, RESNAME FROM TBD_RESULT " +
    			"WHERE FRMID="+theForm.getFRMID()+" AND VERSION="+version+";", null); 
    	
    	if (cursor != null && cursor.moveToFirst()) {   
			//Recorremos el cursor hasta que no haya más registros
    	     do {
				CResult tempResult =  new CResult();
				tempResult.setRESID(cursor.getInt(0));
				tempResult.setFRMID(cursor.getInt(1));
				tempResult.setSTRID(cursor.getInt(2));
				tempResult.setRESVAL(cursor.getFloat(3));
				tempResult.setVERSION(cursor.getInt(4));
				tempResult.setRESNAME(cursor.getString(5));
				theForm.getResult().add(tempResult);
 
    	     } while(cursor.moveToNext());
    	}
    
    	//TBM_STRATEGY
    	Cursor curs = db.rawQuery("SELECT STRID,FRMID,STRNAME,STRLABEL,STRTSOL,STRVAL FROM TBM_STRATEGY " +
    			"WHERE FRMID="+formID+";", null); 
    	
    	if (curs != null && curs.moveToFirst()) {    	
			//Recorremos el cursor hasta que no haya más registros
    	     do {
				CStrategy tempStrategy =  new CStrategy();
				tempStrategy.setSTRID(curs.getInt(0));
				tempStrategy.setFRMID(curs.getInt(1));
				tempStrategy.setSTRNAME(curs.getString(2));
				tempStrategy.setSTRLABEL(curs.getString(3));
				tempStrategy.setSTRTSOL(curs.getInt(4));
				tempStrategy.setSTRVAL(curs.getString(5));
				theForm.getStrategy().add(tempStrategy);
 
    	     } while(curs.moveToNext());
    	}
    	}catch (Exception e)
    	{
    		Log.i(tag,e.toString());
    	}
    	db.close(); 
		return theForm;    	   	
    }  
    
  //************************* Métodos  **************************************
	public void backupDB(Context context){
		SQLiteDatabase db = this.getWritableDatabase();
		 
		         try {
		             File f1 = new File(db.getPath());		 
		             if (f1.exists()) {		 
		                 File f2 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+ "/datilebackup.db");
		                 f2.createNewFile();	 
		                 InputStream in = new FileInputStream(f1);		 
		                 OutputStream out = new FileOutputStream(f2);		 
		                 byte[] buf = new byte[1024];		 
		                 int len;		 
		                 while ((len = in.read(buf)) > 0) {		 
		                     out.write(buf, 0, len);		 
		                 }		 
		                 in.close();		 
		                 out.close();
		                  Log.i(tag,f2.getAbsolutePath() );
		                 }		 
		         } catch (Exception e) {		 
		             e.printStackTrace();		 
		             System.out.println(e.getMessage());		 
		         }	
		  db.close();
	}
	
	boolean ifTableExists(SQLiteDatabase db, String tableName)
	{
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count > 0;
	}

}
