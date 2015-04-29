package com.tesis.app.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class CItemVal implements Parcelable {
	Integer VALID;
	Integer ITMID;
	Integer VALINDEX;
	String VALNAME;
	
	public CItemVal()
	{		
		VALID = 0;
		ITMID = 0;
		VALINDEX = 0;
		VALNAME = "";
	}
	//---------------- PARCELABLE --------------------
	
	public CItemVal(Parcel in) {
		VALID = 0;
		ITMID = 0;
		VALINDEX = 0;
		VALNAME = "";
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(VALID);
		dest.writeInt(ITMID);
		dest.writeInt(VALINDEX);
		dest.writeString(VALNAME);
	}
	
	private void readFromParcel(Parcel in) {
		VALID = in.readInt();
		ITMID = in.readInt();
		VALINDEX = in.readInt();
		VALNAME = in.readString();
	}
		
	 public static final Parcelable.Creator<CItemVal> CREATOR = new Parcelable.Creator<CItemVal>() {
	        public CItemVal createFromParcel(Parcel in) {
	            return new CItemVal(in);
	        }

	        public CItemVal[] newArray(int size) {
	            return new CItemVal[size];
	        }
	    };

	//---------------- SETTERS & GETTERS --------------------	
	
	public Integer getVALID() {
		return VALID;
	}
	public void setVALID(Integer vALID) {
		VALID = vALID;
	}
	public Integer getITMID() {
		return ITMID;
	}
	public void setITMID(Integer iTMID) {
		ITMID = iTMID;
	}
	public Integer getVALINDEX() {
		return VALINDEX;
	}
	public void setVALINDEX(Integer vALINDEX) {
		VALINDEX = vALINDEX;
	}
	public String getVALNAME() {
		return VALNAME;
	}
	public void setVALNAME(String vALNAME) {
		VALNAME = vALNAME;
	}
}
