package com.tesis.app.widget;

import com.tesis.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class XmlGuiChoice extends LinearLayout  {
	String tag = XmlGuiChoice.class.getName();
	TextView label;
	ArrayAdapter<String> aa;
	Spinner spinner;
	
	public XmlGuiChoice(Context context,String labelText,String options) {
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);
		
		label = new TextView(context);
		label.setText(labelText);
		label.setTextSize(16);
		spinner = new Spinner(context);
		String []opts = options.split("\\|");
		aa = new ArrayAdapter<String>( context, android.R.layout.simple_spinner_item,opts);
		spinner.setAdapter(aa);
		spinner.setBackgroundResource(R.xml.custom_spinner);
		spinner.setPopupBackgroundResource(R.xml.custom_spinnerlist);
		this.addView(label);
		this.addView(spinner);
	}

	public XmlGuiChoice(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	//---------------- SETTERS & GETTERS --------------------	
	
	public String getValue()
	{
		return (String) spinner.getSelectedItem().toString();
	}

	public String getPosition()
	{
		return String.valueOf(spinner.getSelectedItemPosition());
	}
}
