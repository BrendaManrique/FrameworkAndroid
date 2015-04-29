package com.tesis.app.widget;

import com.tesis.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.text.method.DigitsKeyListener;

public class XmlGuiEditText extends LinearLayout  {
	TextView label;
	EditText txtBox;
	public XmlGuiEditText(Context context,String labelText,String initialText) {
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);
		
		label = new TextView(context);
		label.setTextSize(16);
		label.setText(labelText);
		txtBox = new EditText(context);
		txtBox.setText(initialText);
		txtBox.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		txtBox.setBackgroundResource(R.xml.custom_edittext);
		this.addView(label);		
		this.addView(txtBox);
	}
	public XmlGuiEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public void makeNumeric()
	{
		DigitsKeyListener dkl = new DigitsKeyListener(true,true);
		txtBox.setKeyListener(dkl);
	}

	//---------------- SETTERS & GETTERS --------------------		
	public String getValue()
	{
		return txtBox.getText().toString();
	}
	public void setValue(String v)
	{
		txtBox.setText(v);
	}
}