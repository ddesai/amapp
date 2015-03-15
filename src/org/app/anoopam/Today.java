package org.app.anoopam;


import android.app.Activity;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;


public class Today  extends TabActivity{ 
	
	private TabHost mTabHost;
	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
	}
	public static final String PREFS_NAME = "AM";
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multitab);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		 Resources res = getResources(); // Resource object to get Drawables
		 TabHost tabHost = getTabHost(); // The activity TabHost
		 TabHost.TabSpec spec; // Reusable TabSpec for each tab
		 Intent intent; // Reusable Intent for each tab
		 
		 // Create an Intent to launch an Activity for the tab (to be reused)
		 intent = new Intent().setClass(this, Uk.class);//uk
		 spec = tabHost.newTabSpec("home")
		 .setIndicator("UK")		 
		 .setContent(intent);		 
		 tabHost.addTab(spec);

		 // Do the same for the other tabs

		 intent = new Intent().setClass(this, Mogri.class);
		 spec = tabHost.newTabSpec("about")
		 .setIndicator("Mogri")
		 .setContent(intent);
		 tabHost.addTab(spec);


		 intent = new Intent().setClass(this, Usa.class);//usa
		 spec = tabHost.newTabSpec("usa")
		 .setIndicator("USA")
		 .setContent(intent);
		 tabHost.addTab(spec);


		 intent = new Intent().setClass(this, Kharghar.class);//khabarg
		 spec = tabHost.newTabSpec("kharghar")
		 .setIndicator("Kharghar")
		 .setContent(intent);
		 tabHost.addTab(spec);
		 
		 

		 intent = new Intent().setClass(this, Surat.class);//surat
		 spec = tabHost.newTabSpec("surat")
		 .setIndicator("Surat")
		 .setContent(intent);
		 tabHost.addTab(spec);
		 
		
		 
		 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		 
		 tabHost.setCurrentTab(settings.getInt("Default",1));
	}

	private void setupTab(final View view, final String tag) {
		View tabview = createTabView(mTabHost.getContext(), tag);

		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
			public View createTabContent(String tag) {return view;}
		});
		mTabHost.addTab(setContent);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.thakorji, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			final Dialog dialog = new Dialog(Today.this);
			dialog.setContentView(R.layout.settings);
			dialog.setTitle("Default Thakorji Darshan");
			
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			int def=settings.getInt("Default",1);
			
				
			
			RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.thakorjiuk);
			if(def==0)
			{
				rd1.setChecked(true);
			}
			rd1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();						
				    	editor.putInt("Default",0);
				    	editor.commit();
				    	Toast.makeText(getApplicationContext(),"Default Thakorji Darshan Selected...",
								Toast.LENGTH_LONG).show();
				    	dialog.dismiss();
					}
			 });
			RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.thakorjimogri);
			if(def==1)
			{
				rd2.setChecked(true);
			}
			rd2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();						
				    	editor.putInt("Default",1);
				    	editor.commit();
				    	Toast.makeText(getApplicationContext(),"Default Thakorji Darshan Selected...",
								Toast.LENGTH_LONG).show();
				    	dialog.dismiss();
					}
			});
			RadioButton rd3 = (RadioButton) dialog.findViewById(R.id.thakorjiusa);
			if(def==2)
			{
				rd3.setChecked(true);
			}
			rd3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();						
				    	editor.putInt("Default",2);
				    	Toast.makeText(getApplicationContext(),"Default Thakorji Darshan Selected...",
								Toast.LENGTH_LONG).show();
				    	editor.commit();
				    	dialog.dismiss();
					}
			 });
			RadioButton rd4 = (RadioButton) dialog.findViewById(R.id.thakorjikhar);
			if(def==3)
			{
				rd4.setChecked(true);
			}
			rd4.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();						
				    	editor.putInt("Default",3);
				    	Toast.makeText(getApplicationContext(),"Default Thakorji Darshan Selected...",
								Toast.LENGTH_LONG).show();
				    	editor.commit();
				    	dialog.dismiss();
					}
			 });
			RadioButton rd5 = (RadioButton) dialog.findViewById(R.id.thakorjisurat);
			if(def==5)
			{
				rd5.setChecked(true);
			}
			rd5.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();						
				    	editor.putInt("Default",4);
				    	Toast.makeText(getApplicationContext(),"Default Thakorji Darshan Selected...",
								Toast.LENGTH_LONG).show();
				    	editor.commit();
				    	dialog.dismiss();
					}
			 });
			dialog.show();
		}
		if (item.getItemId() == R.id.previous) {
			 Intent i=new Intent(getBaseContext(),Dashboard.class);
             startActivity(i);
		}
		return false;
	}
	
	
	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}
	
	
}