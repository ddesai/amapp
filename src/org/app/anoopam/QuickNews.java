package org.app.anoopam;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

public class QuickNews   extends Activity {
	
	public static final String PREFS_NAME = "DhruServer";
	
	@Override
	public void onNewIntent(Intent newIntent) {
	    this.setIntent(newIntent);

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.annoucements);
		setTitle("Annoucements");
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		SharedPreferences settings1 = getSharedPreferences(PREFS_NAME, 0);
		
		TextView txt=(TextView)findViewById(R.id.txtnews);
		txt.setText(getIntent().getStringExtra("IDS")+"");
		
		
		
	}	
	

}
