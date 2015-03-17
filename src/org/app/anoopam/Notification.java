package org.app.anoopam;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Notification extends Activity{
		
	NList notice;
	
		protected void onCreate(Bundle savedInstanceState) 
		{				
				super.onCreate(savedInstanceState);
				setContentView(R.layout.notification);
				
				ArrayList<String> title = new ArrayList<String>();
		        ArrayList<String> detail = new ArrayList<String>();

		        
		        title.add("Title 1");
		        detail.add("Description... Description... Description... Description...");
		        title.add("Title 2");
		        detail.add("Description... Description... Description... Description...");
		        title.add("Title 3");
		        detail.add("Description... Description... Description... Description...");
		        
		        
		        String [] titlearr = title.toArray(new String[title.size()]);
				String [] detailarr = detail.toArray(new String[detail.size()]);
				 
		        
				ListView listview = (ListView) findViewById(R.id.listnotication);
				notice = new NList(Notification.this, titlearr,detailarr);
				listview.setAdapter(notice);
				
		}
		
}
