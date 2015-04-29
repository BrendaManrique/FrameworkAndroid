package com.tesis.app.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class CResult implements Parcelable { 
	Integer RESID;
	Integer FRMID;
	Integer STRID;
	Float RESVAL;
	Integer VERSION;
	String RESNAME;
	
	public CResult()
	{		
		RESID = 0;
		FRMID = 0;
		STRID = 0;
		RESVAL = (float)0;
		VERSION = 0;
		RESNAME = "";
	}
	
	//---------------- PARCELABLE --------------------
	
	public CResult(Parcel in) {
		RESID = 0;
		FRMID = 0;
		STRID = 0;
		RESVAL = (float)0;
		VERSION = 0;
		RESNAME = "";
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(RESID);
		dest.writeInt(FRMID);
		dest.writeInt(STRID);
		dest.writeFloat(RESVAL);
		dest.writeInt(VERSION);
		dest.writeString(RESNAME);
	}
	
	private void readFromParcel(Parcel in) {
		RESID = in.readInt();
		FRMID = in.readInt();
		STRID = in.readInt();
		RESVAL = in.readFloat();
		VERSION = in.readInt();
		RESNAME = in.readString();
	}
		
	 public static final Parcelable.Creator<CResult> CREATOR = new Parcelable.Creator<CResult>() {
	        public CResult createFromParcel(Parcel in) {
	            return new CResult(in);
	        }

	        public CResult[] newArray(int size) {
	            return new CResult[size];
	        }
	    };

	//---------------- SETTERS & GETTERS --------------------	
		
	public Integer getRESID() {
		return RESID;
	}

	public void setRESID(Integer rESID) {
		RESID = rESID;
	}

	public Integer getFRMID() {
		return FRMID;
	}

	public void setFRMID(Integer fRMID) {
		FRMID = fRMID;
	}

	public Integer getSTRID() {
		return STRID;
	}

	public void setSTRID(Integer sTRID) {
		STRID = sTRID;
	}

	public Float getRESVAL() {
		return RESVAL;
	}

	public void setRESVAL(Float rESVAL) {
		RESVAL = rESVAL;
	}

	public Integer getVERSION() {
		return VERSION;
	}

	public void setVERSION(Integer vERSION) {
		VERSION = vERSION;
	}

	public String getRESNAME() {
		return RESNAME;
	}

	public void setRESNAME(String rESNAME) {
		RESNAME = rESNAME;
	}

}
