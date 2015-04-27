package org.app.anoopam.thakorjitoday.activities;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import org.app.anoopam.R;
import org.app.anoopam.activities.DashboardActivity;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thakorji, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            final Dialog dialog = new Dialog(ThakorjiTodayActivity.this);
            dialog.setContentView(R.layout.settings);
            dialog.setTitle("Default Thakorji Darshan");

            RadioButton mogriRadioButton = (RadioButton) dialog.findViewById(R.id.thakorjimogri);
            RadioButton ukRadioButton = (RadioButton) dialog.findViewById(R.id.thakorjiuk);
            RadioButton usaRadioButton = (RadioButton) dialog.findViewById(R.id.thakorjiusa);
            RadioButton khargarRadioButton = (RadioButton) dialog.findViewById(R.id.thakorjikhar);
            RadioButton suratRadioButton = (RadioButton) dialog.findViewById(R.id.thakorjisurat);

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            int defaultCenter = settings.getInt("Default", MOGRI_THAKORJI);

            switch(defaultCenter) {
                case UK_THAKORJI:
                    ukRadioButton.setChecked(true);
                    break;
                case USA_THAKORJI:
                    usaRadioButton.setChecked(true);
                    break;
                case KHARGAR_THAKORJI:
                    khargarRadioButton.setChecked(true);
                    break;
                case SURAT_THAKORJI:
                    suratRadioButton.setChecked(true);
                    break;
                default:
                    mogriRadioButton.setChecked(true);
                    break;
            }

            // Mogri Thakorji
            mogriRadioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDefaultCenter(MOGRI_THAKORJI);
                    dialog.dismiss();
                }
            });

            ukRadioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDefaultCenter(UK_THAKORJI);
                    dialog.dismiss();
                }
            });

            usaRadioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDefaultCenter(USA_THAKORJI);
                    dialog.dismiss();
                }
            });

            khargarRadioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDefaultCenter(KHARGAR_THAKORJI);
                    dialog.dismiss();
                }
            });

            suratRadioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDefaultCenter(SURAT_THAKORJI);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        if (item.getItemId() == R.id.previous) {
            Intent i = new Intent(getBaseContext(), DashboardActivity.class);
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