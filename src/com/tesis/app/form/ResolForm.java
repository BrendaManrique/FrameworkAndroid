package com.tesis.app.form;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.tesis.app.ActResult;
import com.tesis.app.clases.CForm;
import com.tesis.app.dal.DBHandler;

public class ResolForm {

	static String tag = ResolForm.class.getSimpleName(); 
	
	public static boolean fnResolutor(CForm theForm,Context context)
	{
		float result = 0;
		String itemDatPnt="";
		Integer tsolid=-1;
		Integer ctrlid=0;
		Integer ctrlNumeric=2;
		Integer ctrlChoice=3;
	    String  type="";
	    String strname="";
		int i;
		
        for (i=0;i<theForm.items.size();i++) {   
        	if(theForm.items.get(i).getITMSOLVE().equals("T"))
        	{
				tsolid=theForm.items.get(i).getTSOLID();
				ctrlid = theForm.items.get(i).getCTRLID();       		

        		//Tipo de resolución                     
        		if(tsolid.equals(1)) //R. Simple
        		{
        			if(ctrlid.equals(ctrlNumeric)) 	type="SN";
            		if(ctrlid.equals(ctrlChoice))   type="SC"; 
        		}
        		if(tsolid.equals(2)) //R. Límites
        		{
        			if(ctrlid.equals(ctrlNumeric)) 	type="LN"; 
        		}
        		
        		itemDatPnt=fnResolutor(type,theForm, i);
        		if(itemDatPnt.equals("0"))
        			theForm.itemsData.get(i).setDITMGOOD("F");
        		else
        			theForm.itemsData.get(i).setDITMGOOD("T");
        		//Agrega resultado a la clase contenedora de datos ingresados
        		theForm.itemsData.get(i).setDITMPNT(itemDatPnt);
        		result = result + Float.valueOf(itemDatPnt);
        	}
        }
        strname = fnGenerateResults(theForm,String.valueOf(result)); // Resultado segun las estrategias
        //Guarda Resultado en BD
        DBHandler dbh1 = new DBHandler (context);
        dbh1.resolForm_setItemsData(theForm);
	    dbh1.resolForm_setResult(theForm, result, strname);
	    dbh1.backupDB(context);
	    dbh1.close();
        //RunForm.resval = result;		
        return true;
	}
	
	public static String fnResolutor(String type,CForm theForm, int i)
	{		
		String itemDatVal=theForm.itemsData.get(i).getDITMVAL().trim();
		String itemPnt="";
	    int count=0;
	    for( int j=0;j<theForm.items.get(i).val.size();j++ ) //Tamaño de valores
	    {
	    	String s = theForm.items.get(i).val.get(j).getVALNAME();
	    	if(type.equals("SN")) 
			 {
	    		 if(itemDatVal.isEmpty())
	    			 return "0";
				 if(s.equals(itemDatVal))
	        		 return theForm.items.get(i).pnt.get(0).getPNTNAME();
			 }
	    	
			 if(type.equals("SC")) 
			 {     
				  if(s.equals(itemDatVal))
				  {
					  int svalue = Integer.valueOf(s);
					  if(theForm.items.get(i).pnt.size()>1)
						  //Extrae el puntaje correspondiente a la posición
						  itemPnt = theForm.items.get(i).pnt.get(svalue).getPNTNAME();
					  else
						  itemPnt = theForm.items.get(i).pnt.get(0).getPNTNAME();
					  return itemPnt;
				  }
			  }
			 
			  if(type.equals("LN")) 
			  {
				  if(itemDatVal.isEmpty())
					  return "0";
				 if(fnEvalLimits(s,itemDatVal)) 
				 {
					 itemPnt = theForm.items.get(i).pnt.get(count).getPNTNAME();
					 return itemPnt;
				 }
				 count++;
			  }
			 }
		return "0";
	}

	private static boolean fnEvalLimits(String itemVal, String itemDatVal)
	{
		boolean dentroDeLimites=false;
		 List<String> limits = Arrays.asList(itemVal.split("-"));
		 float varx = Float.valueOf(limits.get(0));
		 float vary = Float.valueOf(limits.get(1));
		 float itemDatValf = Float.valueOf(itemDatVal); //Dato ingresado
		 if(varx <= itemDatValf && itemDatValf <= vary)
		 {
			 dentroDeLimites = true;
		 }
		return dentroDeLimites;
	}
	
	private static String fnGenerateResults(CForm theForm ,String result)
	{
		for (int i=0;i<theForm.strategy.size();i++) { 
			
			if(theForm.strategy.get(i).getSTRTSOL()==1)
        	{
        		if(theForm.strategy.get(i).getSTRVAL().equals(result))
        			return theForm.strategy.get(i).getSTRNAME();
        	}
        	if(theForm.strategy.get(i).getSTRTSOL()==2)
        	{
        		 if(fnEvalLimits(theForm.strategy.get(i).getSTRVAL(),result))
        			  return theForm.strategy.get(i).getSTRNAME();
        	}
        }
		return "";
	}
}
