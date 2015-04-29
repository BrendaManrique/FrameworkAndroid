package com.tesis.app.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class CItemData implements Parcelable {
	Integer DITMID;
	Integer FRMID;
	Integer VERSION;
	Integer ITMID;
	String DITMVAL;
	String DITMPNT;
	String DITMGOOD;
	
	public CItemData()
	{		
		DITMID = 0;
		FRMID = 0;
		VERSION = 0;
		ITMID = 0;
		DITMVAL = "";
		DITMPNT = "";
		DITMGOOD = "";
	}
	//---------------- PARCELABLE --------------------
	
	public CItemData(Parcel in) {
		DITMID = 0;
		FRMID = 0;
		VERSION = 0;
		ITMID = 0;
		DITMVAL = "";
		DITMPNT = "";
		DITMGOOD = "";
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(DITMID);
		dest.writeInt(FRMID);
		dest.writeInt(VERSION);
		dest.writeInt(ITMID);
		dest.writeString(DITMVAL);
		dest.writeString(DITMPNT);
		dest.writeString(DITMGOOD);
	}
	
	private void readFromParcel(Parcel in) {
		DITMID = in.readInt();
		FRMID = in.readInt();
		VERSION = in.readInt();
		ITMID = in.readInt();
		DITMVAL = in.readString();
		DITMPNT = in.readString();
		DITMGOOD = in.readString();
	}
		
	 public static final Parcelable.Creator<CItemData> CREATOR = new Parcelable.Creator<CItemData>() {
	        public CItemData createFromParcel(Parcel in) {
	            return new CItemData(in);
	        }

	        public CItemData[] newArray(int size) {
	            return new CItemData[size];
	        }
	    };

	//---------------- SETTERS & GETTERS --------------------	
		
	public Integer getDITMID() {
		return DITMID;
	}
	public void setDITMID(Integer dITMID) {
		DITMID = dITMID;
	}
	public Integer getFRMID() {
		return FRMID;
	}
	public Integer getVERSION() {
		return VERSION;
	}
	public void setVERSION(Integer vERSION) {
		VERSION = vERSION;
	}
	public void setFRMID(Integer fRMID) {
		FRMID = fRMID;
	}
	public Integer getITMID() {
		return ITMID;
	}
	public void setITMID(Integer iTMID) {
		ITMID = iTMID;
	}
	public String getDITMVAL() {
		return DITMVAL;
	}
	public void setDITMVAL(String dITMVAL) {
		DITMVAL = dITMVAL;
	}
	public String getDITMPNT() {
		return DITMPNT;
	}
	public void setDITMPNT(String dITMPNT) {
		DITMPNT = dITMPNT;
	}
	public String getDITMGOOD() {
		return DITMGOOD;
	}
	public void setDITMGOOD(String dITMGOOD) {
		DITMGOOD = dITMGOOD;
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Field FRMID: " + this.FRMID + "\n");
		sb.append("Field ITMID: " + this.ITMID + "\n");
		sb.append("Field DITMVAL: " + this.DITMVAL + "\n");
		sb.append("Field DITMPNT: " + this.DITMPNT + "\n");
		return sb.toString();
	}
}