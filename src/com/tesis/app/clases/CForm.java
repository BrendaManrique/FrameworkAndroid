package com.tesis.app.clases;

import java.util.ArrayList;
import java.util.List;


import android.os.Parcel;
import android.os.Parcelable;

public class CForm implements Parcelable  { 
	private Integer FRMID;
	private String FRMIDT;
	private String FRMNAME;
	private String FRMURL;
	private Integer FRMVERSI;
	public List<CItem> items;
	public List<CItemData> itemsData;
	public List<CStrategy> strategy;
	public List<CResult> result;
	String tag = CForm.class.getName(); //Solo para Log
	
	public CForm()
	{		
		FRMID = 0;
		FRMIDT = "";
		FRMNAME = "";
		FRMURL = "loopback";// para test, cuando no tiene url los datos se muestran al usuario.
		this.items = new ArrayList<CItem>(); //Lista de controles
		this.itemsData = new ArrayList<CItemData>(); 
		this.strategy = new ArrayList<CStrategy>();
		this.result = new ArrayList<CResult>();
	}
	
	//---------------- PARCELABLE --------------------
	
	public CForm(Parcel in) {
		FRMID = 0;
		FRMIDT = "";
		FRMNAME = "";
		FRMURL = "loopback";
		this.items = new ArrayList<CItem>();
		this.itemsData = new ArrayList<CItemData>(); 
		this.strategy = new ArrayList<CStrategy>(); 
		this.result = new ArrayList<CResult>();
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(FRMID);
		dest.writeString(FRMIDT);
		dest.writeString(FRMNAME);
		dest.writeString(FRMURL);
		dest.writeTypedList(items);
		dest.writeTypedList(itemsData);
		dest.writeTypedList(strategy);
		dest.writeTypedList(result);
	}
	
	private void readFromParcel(Parcel in) {
		FRMID = in.readInt();
		FRMIDT = in.readString();
		FRMNAME = in.readString();
		FRMURL = in.readString();
		in.readTypedList(items, CItem.CREATOR);
		in.readTypedList(itemsData, CItemData.CREATOR);
		in.readTypedList(strategy, CStrategy.CREATOR);
		in.readTypedList(result, CResult.CREATOR);
	}
	
	 public static final Parcelable.Creator<CForm> CREATOR = new Parcelable.Creator<CForm>() {
	        public CForm createFromParcel(Parcel in) {
	            return new CForm(in);
	        }

	        public CForm[] newArray(int size) {
	            return new CForm[size];
	        }
	    };

	//---------------- SETTERS & GETTERS --------------------
	
	public Integer getFRMID() {
		return FRMID;
	}
	public void setFRMID(Integer fRMID) {
		FRMID = fRMID;
	}
	public String getFRMIDT() {
		return FRMIDT;
	}
	public void setFRMIDT(String fRMIDT) {
		FRMIDT = fRMIDT;
	}
	public String getFRMNAME() {
		return FRMNAME;
	}
	public void setFRMNAME(String fRMNAME) {
		FRMNAME = fRMNAME;
	}
	public String getFRMURL() {
		return FRMURL;
	}
	public void setFRMURL(String fRMURL) {
		FRMURL = fRMURL;
	}
	public Integer getFRMVERSI() {
		return FRMVERSI;
	}
	public void setFRMVERSI(Integer fRMVERSI) {
		FRMVERSI = fRMVERSI;
	}
	public List<CItem> getItems() {
		return items;
	}
	public void setItems(List<CItem> items) {
		this.items = items;
	}
	public List<CItemData> getItemsData() {
		return itemsData;
	}
	public void setItemsData(List<CItemData> itemsData) {
		this.itemsData = itemsData;
	}
	public List<CStrategy> getStrategy() {
		return strategy;
	}
	public void setStrategy(List<CStrategy> strategy) {
		this.strategy = strategy;
	}

	public List<CResult> getResult() {
		return result;
	}
	public void setResult(List<CResult> result) {
		this.result = result;
	}

}
