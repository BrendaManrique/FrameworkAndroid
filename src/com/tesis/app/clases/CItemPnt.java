package com.tesis.app.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class CItemPnt implements Parcelable  {
	Integer PNTID;
	Integer ITMID;
	Integer PNTINDEX;
	String PNTNAME;
	
	public CItemPnt()
	{		
		PNTID = 0;
		ITMID = 0;
		PNTINDEX = 0;
		PNTNAME = "";
	}
	//---------------- PARCELABLE --------------------
	
	public CItemPnt(Parcel in) {
		PNTID = 0;
		ITMID = 0;
		PNTINDEX = 0;
		PNTNAME = "";
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(PNTID);
		dest.writeInt(ITMID);
		dest.writeInt(PNTINDEX);
		dest.writeString(PNTNAME);
	}
	
	private void readFromParcel(Parcel in) {
		PNTID = in.readInt();
		ITMID = in.readInt();
		PNTINDEX = in.readInt();
		PNTNAME = in.readString();
	}
		
	 public static final Parcelable.Creator<CItemPnt> CREATOR = new Parcelable.Creator<CItemPnt>() {
	        public CItemPnt createFromParcel(Parcel in) {
	            return new CItemPnt(in);
	        }

	        public CItemPnt[] newArray(int size) {
	            return new CItemPnt[size];
	        }
	    };

	//---------------- SETTERS & GETTERS --------------------	
		

	
	public Integer getPNTID() {
		return PNTID;
	}
	public void setPNTID(Integer pNTID) {
		PNTID = pNTID;
	}
	public Integer getITMID() {
		return ITMID;
	}
	public void setITMID(Integer iTMID) {
		ITMID = iTMID;
	}
	public Integer getPNTINDEX() {
		return PNTINDEX;
	}
	public void setPNTINDEX(Integer pNTINDEX) {
		PNTINDEX = pNTINDEX;
	}
	public String getPNTNAME() {
		return PNTNAME;
	}
	public void setPNTNAME(String pNTNAME) {
		PNTNAME = pNTNAME;
	}
}
