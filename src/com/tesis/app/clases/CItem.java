package com.tesis.app.clases;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.tesis.app.widget.XmlGuiCamera;
import com.tesis.app.widget.XmlGuiChoice;
import com.tesis.app.widget.XmlGuiEditText;
import com.tesis.app.widget.XmlGuiGps;

//class to handle each individual form
public class CItem implements Parcelable  {// implements Parcelable{
	Integer ITMID;
	public String ITMNAME;
	String ITMLABEL;
	Integer CTRLID;
	String ITMREQUI;
	String ITMOPT;
	String ITMSOLVE;
	Integer TSOLID;
	String ITMVAL;
	String ITMPNT;
	public Object obj; // mantiene la implementación GUI, ejemplo un EditText
	public List<CItemOpt> opt; 
	public List<CItemVal> val; 
	public List<CItemPnt> pnt;
	
	public CItem()
	{		
		ITMID = 0;
		ITMNAME= "";
		ITMLABEL = "";
		CTRLID = 0;
		ITMREQUI = "";
		ITMOPT = "";
		ITMSOLVE = "";
		TSOLID = -1;
		ITMVAL = "";
		ITMPNT = "";
		//obj = new Object();
		this.opt = new ArrayList<CItemOpt>(); //Lista de opciones de un ítem
		this.val = new ArrayList<CItemVal>(); //Lista de valores de un ítem 
		this.pnt = new ArrayList<CItemPnt>(); //Lista de puntajes de un ítem 
	}
	
	//---------------- PARCELABLE --------------------
	
	public CItem(Parcel in) {
		ITMID = 0;
		ITMNAME= "";
		ITMLABEL = "";
		CTRLID = 0;
		ITMREQUI = "";
		ITMOPT = "";
		ITMSOLVE = "";
		TSOLID = -1;
		ITMVAL = "";
		ITMPNT = "";
		//obj = new Object();
		this.opt = new ArrayList<CItemOpt>(); //Lista de opciones de un ítem
		this.val = new ArrayList<CItemVal>(); //Lista de valores de un ítem 
		this.pnt = new ArrayList<CItemPnt>(); //Lista de puntajes de un ítem
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(ITMID);
		dest.writeString(ITMNAME);
		dest.writeString(ITMLABEL);
		dest.writeInt(CTRLID);
		dest.writeString(ITMREQUI);
		dest.writeString(ITMOPT);
		dest.writeString(ITMSOLVE);
		dest.writeInt(TSOLID);
		dest.writeString(ITMVAL);
		dest.writeString(ITMPNT);
		//dest.writeValue(obj);
		//dest.writeParcelable((Parcelable) obj,flags);
		dest.writeTypedList(opt);
		dest.writeTypedList(val);
		dest.writeTypedList(pnt);
	}
	
	private void readFromParcel(Parcel in) {
		ITMID = in.readInt();
		ITMNAME = in.readString();
		ITMLABEL = in.readString();
		CTRLID = in.readInt();
		ITMREQUI = in.readString();
		ITMOPT = in.readString();
		ITMSOLVE = in.readString();
		TSOLID = in.readInt();
		ITMVAL = in.readString();
		ITMPNT = in.readString();
		//Object obj = null;
	    //obj = in.readValue(null);
		in.readTypedList(opt, CItemOpt.CREATOR);
		in.readTypedList(val, CItemVal.CREATOR);
		in.readTypedList(pnt, CItemPnt.CREATOR);
	}
	
	 public static final Parcelable.Creator<CItem> CREATOR = new Parcelable.Creator<CItem>() {
	        public CItem createFromParcel(Parcel in) {
	            return new CItem(in);
	        }

	        public CItem[] newArray(int size) {
	            return new CItem[size];
	        }
	    };

	//---------------- SETTERS & GETTERS --------------------
		
	public Integer getITMID() {
		return ITMID;
	}

	public void setITMID(Integer iTMID) {
		ITMID = iTMID;
	}

	public String getITMNAME() {
		return ITMNAME;
	}

	public void setITMNAME(String iTMNAME) {
		ITMNAME = iTMNAME;
	}

	public String getITMLABEL() {
		return ITMLABEL;
	}

	public void setITMLABEL(String iTMLABEL) {
		ITMLABEL = iTMLABEL;
	}

	public Integer getCTRLID() {
		return CTRLID;
	}

	public void setCTRLID(Integer cTRLID) {
		CTRLID = cTRLID;
	}

	public String getITMREQUI() {
		return ITMREQUI;
	}

	public void setITMREQUI(String iTMREQUI) {
		ITMREQUI = iTMREQUI;
	}

	public String getITMOPT() {
		return ITMOPT;
	}

	public void setITMOPT(String iTMOPT) {
		ITMOPT = iTMOPT;
	}

	public String getITMSOLVE() {
		return ITMSOLVE;
	}

	public void setITMSOLVE(String iTMSOLVE) {
		ITMSOLVE = iTMSOLVE;
	}

	public Integer getTSOLID() {
		return TSOLID;
	}

	public void setTSOLID(Integer tSOLID) {
		TSOLID = tSOLID;
	}

	public String getITMVAL() {
		return ITMVAL;
	}

	public void setITMVAL(String iTMVAL) {
		ITMVAL = iTMVAL;
	}

	public String getITMPNT() {
		return ITMPNT;
	}

	public void setITMPNT(String iTMPNT) {
		ITMPNT = iTMPNT;
	}

	public List<CItemOpt> getOpt() {
		return opt;
	}

	public void setOpt(List<CItemOpt> opt) {
		this.opt = opt;
	}

	public List<CItemVal> getVal() {
		return val;
	}

	public void setVal(List<CItemVal> val) {
		this.val = val;
	}

	public List<CItemPnt> getPnt() {
		return pnt;
	}

	public void setPnt(List<CItemPnt> pnt) {
		this.pnt = pnt;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Field Name: " + this.ITMNAME + "\n");
		sb.append("Field Label: " + this.ITMLABEL + "\n");
		sb.append("Field Type: " + this.CTRLID + "\n");
		sb.append("Required? : " + this.ITMREQUI + "\n");
		sb.append("Options : " + this.ITMOPT + "\n");
		sb.append("Value : " + (String) this.getData() + "\n");
		return sb.toString();
	}
	
//	public String getFormattedResult()
//	{
//		return this.ITMNAME + "= [" + (String) this.getData() + "]";
//	}
	
	
	public Object getData()
	{
		if (CTRLID.equals(1) || CTRLID.equals(2)) //1 = Text y 2 = Numeric
		{
			if (obj != null) {
				XmlGuiEditText b = (XmlGuiEditText) obj;
				return b.getValue();
			}
		}
		if (CTRLID.equals(3)) { // 3 = Choice
			if (obj != null) {
				XmlGuiChoice b = (XmlGuiChoice) obj;
				return b.getPosition();
			}
		}
		if (CTRLID.equals(4)) { // 4 = label
			return "-";
		}
		if (CTRLID.equals(5)) { // 5 = gps
			if (obj != null) {
				XmlGuiGps b = (XmlGuiGps) obj;
				return b.getValue();
			}
		}
		
		if (CTRLID.equals(6)) { // 6 = camara
			if (obj != null) {
				XmlGuiCamera b = (XmlGuiCamera) obj;
				return b.getValue();
			}
		}
		return null;
	}
	

}
