package com.tesis.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XmlGuiLabel extends LinearLayout {
	TextView label;
	public XmlGuiLabel(Context context,String labelText) {
		super(context);
		label = new TextView(context);
		label.setText(labelText);
		label.setTextSize(16);
		this.setOrientation(LinearLayout.VERTICAL);

		this.addView(label);
	}
	public XmlGuiLabel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
}