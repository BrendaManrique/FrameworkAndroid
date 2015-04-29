package com.tesis.app.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class CStrategy implements Parcelable { 
	Integer STRID;
	String STRNAME;
	Integer FRMID;
	String STRLABEL;
	Integer STRTSOL;
	String STRVAL;
	
	public CStrategy()
	{		
		STRID = 0;
		STRNAME = "";
		FRMID = 0;
		STRLABEL = "";
		STRTSOL = -1;
		STRVAL = "";
	}
	
	//---------------- PARCELABLE --------------------
	
	public CStrategy(Parcel in) {
		STRID = 0;
		STRNAME = "";
		FRMID = 0;
		STRLABEL = "";
		STRTSOL = -1;
		STRVAL = "";
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(STRID);
		dest.writeString(STRNAME);
		dest.writeInt(FRMID);
		dest.writeString(STRLABEL);
		dest.writeInt(STRTSOL);
		dest.writeString(STRVAL);
	}
	
	private void readFromParcel(Parcel in) {
		STRID = in.readInt();
		STRNAME = in.readString();
		FRMID = in.readInt();
		STRLABEL = in.readString();
		STRTSOL = in.readInt();
		STRVAL = in.readString();
	}
		
	 public static final Parcelable.Creator<CStrategy> CREATOR = new Parcelable.Creator<CStrategy>() {
	        public CStrategy createFromParcel(Parcel in) {
	            return new CStrategy(in);
	        }

	        public CStrategy[] newArray(int size) {
	            return new CStrategy[size];
	        }
	    };

	//---------------- SETTERS & GETTERS --------------------	
	
	public Integer getSTRID() {
		return STRID;
	}
	public void setSTRID(Integer sTRID) {
		STRID = sTRID;
	}
	public String getSTRNAME() {
		return STRNAME;
	}
	public void setSTRNAME(String sTRNAME) {
		STRNAME = sTRNAME;
	}
	public Integer getFRMID() {
		return FRMID;
	}
	public void setFRMID(Integer fRMID) {
		FRMID = fRMID;
	}
	public String getSTRLABEL() {
		return STRLABEL;
	}
	public void setSTRLABEL(String sTRLABEL) {
		STRLABEL = sTRLABEL;
	}
	public Integer getSTRTSOL() {
		return STRTSOL;
	}
	public void setSTRTSOL(Integer sTRTSOL) {
		STRTSOL = sTRTSOL;
	}
	public String getSTRVAL() {
		return STRVAL;
	}
	public void setSTRVAL(String sTRVAL) {
		STRVAL = sTRVAL;
	}
}
