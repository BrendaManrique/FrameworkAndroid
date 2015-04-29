package com.tesis.app.utils;

import com.tesis.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultArrayAdapter extends BaseAdapter {
	final String tag = ResultArrayAdapter.class.getSimpleName();
	
    // Declare Variables
    Context context;
	private final String[] itmlabel;
	private final String[] itmval;
	private final String[] ditmval;
	private final String[] ditmgood;
 
    public ResultArrayAdapter(Context context, String[] itmlabel,  String[] itmval, String[] ditmval, String[] ditmgood ) {
    	super();
    	//super(context, R.layout.activity_act_result, itmlabel);
    	this.context = context;
        this.itmlabel = itmlabel;
        this.itmval = itmval;
        this.ditmval = ditmval;
        this.ditmgood = ditmgood;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
    	View rowView = inflater.inflate(R.layout.activity_act_result, parent, false);
    	ImageView imageView = (ImageView) rowView.findViewById(R.id.resultlogo);
		TextView titmlabel = (TextView) rowView.findViewById(R.id.ritmlabel);		
		titmlabel.setText(itmlabel[position]);
		TextView titmval = (TextView) rowView.findViewById(R.id.ritmval);		
		titmval.setText("Correcta: "+itmval[position]);
		TextView tditmval = (TextView) rowView.findViewById(R.id.rditmval);		
		tditmval.setText("Ingresada: "+ditmval[position]);
		
		if (ditmgood[position].equals("T")) {
				imageView.setImageResource(R.drawable.draw_check);
		} else if (ditmgood[position].equals("F")) {
				imageView.setImageResource(R.drawable.draw_wrong);
		}
		
        return rowView;
    }


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itmlabel.length;
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itmlabel[position];
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}