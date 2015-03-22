package org.app.anoopam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.app.anoopam.gcm.GcmUtils;
import org.app.anoopam.thakorjitoday.TodayDateSyncer;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        init();
    }
    
    /**
     * initializes the App
     */
    private void init() {
        // syncs the Date with the Server
        TodayDateSyncer.syncWithServer(this);
        
        // starts GCM Registration, if not already done
        GcmUtils.verifyGcmRegistration(this);
        
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