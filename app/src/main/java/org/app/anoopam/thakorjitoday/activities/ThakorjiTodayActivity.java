package org.app.anoopam.thakorjitoday.activities;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import org.app.anoopam.R;

//TODO: Remove the TabActivity and replace it by Fragments

public class ThakorjiTodayActivity extends TabActivity {

    private static final int MOGRI_THAKORJI = 0;
    private static final int UK_THAKORJI = 1;
    private static final int USA_THAKORJI = 2;
    private static final int KHARGAR_THAKORJI = 3;
    private static final int SURAT_THAKORJI = 4;

    private TabHost mTabHost;
    public static final String PREFS_NAME = "AM";

    private void setupTabHost() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
    }

    /**
     * sets the default center for the Thakorji Today
     * @param center
     */
    private void setDefaultCenter(int center) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("Default", center);
        editor.commit();
        Toast.makeText(getApplicationContext(), "Default Thakorji Darshan Selected...", Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitab);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        TabHost tabHost = getTabHost(); // The activity TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        // Mogri
        intent = new Intent().setClass(this, MogriThakorjiActivity.class);
        spec = tabHost.newTabSpec("mogri").setIndicator("Mogri").setContent(intent);
        tabHost.addTab(spec);

        // UK
        intent = new Intent().setClass(this, UkThakorjiActivity.class);
        spec = tabHost.newTabSpec("uk").setIndicator("UK").setContent(intent);
        tabHost.addTab(spec);

        // USA
        intent = new Intent().setClass(this, UsaThakorjiActivity.class);
        spec = tabHost.newTabSpec("usa").setIndicator("USA").setContent(intent);
        tabHost.addTab(spec);

        // Khargar
        intent = new Intent().setClass(this, KhargharThakorjiActivity.class);
        spec = tabHost.newTabSpec("kharghar").setIndicator("Kharghar").setContent(intent);
        tabHost.addTab(spec);

        // Surat
        intent = new Intent().setClass(this, SuratThakorjiActivity.class);
        spec = tabHost.newTabSpec("surat").setIndicator("Surat").setContent(intent);
        tabHost.addTab(spec);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        // sets the current tab to the default Center for thakorji Today
        // If it doesnt get the default (previously selected and stored) from SharedPrefs
        // then make the MOGRI center default
        tabHost.setCurrentTab(settings.getInt("Default", MOGRI_THAKORJI));
    }

    private void setupTab(final View view, final String tag) {
        View tabview = createTabView(mTabHost.getContext(), tag);

        TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent);
    }

    private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }
}