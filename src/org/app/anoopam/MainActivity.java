package org.app.anoopam;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	AsyncTask<Void, Void, Void> mRegisterTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        
        
        	///////////START GCM/////////////////
		   GCMRegistrar.checkDevice(this);
		   GCMRegistrar.checkManifest(this);
			final String regId = GCMRegistrar.getRegistrationId(this);
		       
	        if (regId.equals("")) {
	            GCMRegistrar.register(this, org.app.anoopam.CommonUtilities.SENDER_ID);
	        } else {
	        	
	        	 final Context context = this;
	             mRegisterTask = new AsyncTask<Void, Void, Void>() {
	
	                 @Override
	                 protected Void doInBackground(Void... params) {
	                	 GCMRegistrar.setRegisteredOnServer(context, true);
	                     return null;
	                 }
	
	                 @Override
	                 protected void onPostExecute(Void result) {
	                     mRegisterTask = null;
	                 }
	
	             };
	             mRegisterTask.execute(null, null, null);
	        }
	        /////////////END THIS/////////////
	        
	        
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    Intent i = new Intent(getBaseContext(), Dashboard.class);
                    startActivity(i);
                    finish();
                    //test 
                } catch (Exception e) {
                }
            }
        };
        background.start();
    }
}
