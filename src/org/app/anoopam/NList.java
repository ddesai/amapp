package org.app.anoopam;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class NList extends ArrayAdapter<String> {
	

private final Activity context;
private final String[] title;
private final String[] detail;


private List<String> filteredModelItemsArray;



public NList(Activity context,String[] title, String[] detail) {
	
	super(context, R.layout.notificationlist, title);
	this.context = context;
	this.title = title;
	this.detail = detail;
}


@SuppressLint({ "ViewHolder", "InflateParams" })
@Override
public View getView(int position, View view, ViewGroup parent) {
	
		String  m = filteredModelItemsArray.get(position);
		LayoutInflater inflater = context.getLayoutInflater();

	 	View rowView= inflater.inflate(R.layout.notificationlist, null, true);
	  	 
			  
		TextView services = (TextView) rowView.findViewById(R.id.listtitle);
		TextView imeis = (TextView) rowView.findViewById(R.id.listcontent);
		
		
		services.setText(title[position]);
		imeis.setText(detail[position]);

		return rowView;
	 
	}

}