package com.tesis.app.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class CItemOpt implements Parcelable  {
	Integer OPTID;
	Integer ITMID;
	Integer OPTINDEX;
	String OPTNAME;
	
	public CItemOpt()
	{		
		OPTID = 0;
		ITMID = 0;
		OPTINDEX = 0;
		OPTNAME = "";
	}
	
	//---------------- PARCELABLE --------------------
	
	public CItemOpt(Parcel in) {
		OPTID = 0;
		ITMID = 0;
		OPTINDEX = 0;
		OPTNAME = "";
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(OPTID);
		dest.writeInt(ITMID);
		dest.writeInt(OPTINDEX);
		dest.writeString(OPTNAME);
	}
	
	private void readFromParcel(Parcel in) {
		OPTID = in.readInt();
		ITMID = in.readInt();
		OPTINDEX = in.readInt();
		OPTNAME = in.readString();
	}
		
	 public static final Parcelable.Creator<CItemOpt> CREATOR = new Parcelable.Creator<CItemOpt>() {
	        public CItemOpt createFromParcel(Parcel in) {
	            return new CItemOpt(in);
	        }

	        public CItemOpt[] newArray(int size) {
	            return new CItemOpt[size];
	        }
	    };

	//---------------- SETTERS & GETTERS --------------------	
		

	public Integer getOPTID() {
		return OPTID;
	}
	public void setOPTID(Integer oPTID) {
		OPTID = oPTID;
	}
	public Integer getITMID() {
		return ITMID;
	}
	public void setITMID(Integer iTMID) {
		ITMID = iTMID;
	}
	public Integer getOPTINDEX() {
		return OPTINDEX;
	}
	public void setOPTINDEX(Integer oPTINDEX) {
		OPTINDEX = oPTINDEX;
	}
	public String getOPTNAME() {
		return OPTNAME;
	}
	public void setOPTNAME(String oPTNAME) {
		OPTNAME = oPTNAME;
	}	
}
