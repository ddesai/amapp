package org.app.anoopam;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

public class Dashboard extends Activity{ 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
	}

	public void GoToday(View v)
	{
		Vibrator vs = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
		vs.vibrate(100);
		
		Intent i=new Intent(getBaseContext(),Today.class);
        startActivity(i);
	}
	public void GoQuote(View v)
	{
		Vibrator vs = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
		vs.vibrate(100);
		
		
		Intent i=new Intent(getBaseContext(),Quote.class);
        startActivity(i);
	}
	public void GoMantralekhan(View v)
	{
		try{
			Vibrator vs = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
			vs.vibrate(100);
			
			
			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.web.anoopam");
			startActivity( LaunchIntent );
		}catch(Exception e){
			final String appPackageName = "com.web.anoopam"; // getPackageName() from Context or Activity object
			try {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
			} catch (android.content.ActivityNotFoundException anfe) {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
			}
		}
	}
	public void GoNotification(View v){
		Vibrator vs = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
		vs.vibrate(100);
		
		
		Intent i=new Intent(getBaseContext(),Notification.class);
        startActivity(i);
	}
	public void GoCalendar(View v)
	{
		Vibrator vs = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
		vs.vibrate(100);
		
		
		Intent i=new Intent(getBaseContext(),Calendars.class);
        startActivity(i);
	}
}
