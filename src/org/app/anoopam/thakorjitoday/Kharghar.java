package org.app.anoopam.thakorjitoday;

import org.app.anoopam.CirclePageIndicator;
import org.app.anoopam.PageIndicator;
import org.app.anoopam.R;
import org.app.anoopam.util.GeneralUtils;
import org.app.anoopam.util.TodayUpdateHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class Kharghar extends FragmentActivity {
    AsyncTask<Void, Void, Void> mRegisterTask;
    AsyncTask<Void, Void, Void> mRegisterTask2;
    AMViewAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    Context mContext; 
    SharedPreferences mSettings; 

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mSettings = GeneralUtils.getAMSharedPrefs(mContext);
        setContentView(R.layout.uk);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Bitmap[] offerImagesB = new Bitmap[4];
        TodayUpdateHelper.getThakorjiTodayBitmaps(offerImagesB, getResources().getDrawable(R.drawable.settings));

        mRegisterTask = new AsyncTask<Void, Void, Void>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(Kharghar.this, "", "Loading please wait...");
                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                TodayUpdateHelper.updateTodayDateStatusFromServer(mContext, "kharghar1", "KHARDate");

                if (mSettings.getString("KHAR1", "").equals("")) {
                    offerImagesB[0] = TodayUpdateHelper.getImageFromServer(mContext, "kharghar1", "KHAR1.jpg", "KHARDate", true);
                    offerImagesB[1] = TodayUpdateHelper.getImageFromServer(mContext, "kharghar3", "KHAR2.jpg");
                    offerImagesB[2] = TodayUpdateHelper.getImageFromServer(mContext, "kharghar5", "KHAR3.jpg");
                    offerImagesB[3] = TodayUpdateHelper.getImageFromServer(mContext, "kharghar7", "KHAR4.jpg");
                } else {
                    offerImagesB[0] = (TodayUpdateHelper.loadImageFromStorage(mSettings.getString("KHAR1", ""), "KHAR1.jpg"));
                    offerImagesB[1] = (TodayUpdateHelper.loadImageFromStorage(mSettings.getString("KHAR2", ""), "KHAR2.jpg"));
                    offerImagesB[2] = (TodayUpdateHelper.loadImageFromStorage(mSettings.getString("KHAR3", ""), "KHAR3.jpg"));
                    offerImagesB[3] = (TodayUpdateHelper.loadImageFromStorage(mSettings.getString("KHAR4", ""), "KHAR4.jpg"));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                mRegisterTask = null;
                mAdapter = new AMViewAdapter(getSupportFragmentManager());
                mAdapter.setCount(4);
                mAdapter.setImagesB(offerImagesB);
                mPager = (ViewPager) findViewById(R.id.pager);
                mPager.setAdapter(mAdapter);

                CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
                mIndicator = indicator;
                indicator.setViewPager(mPager);

                final float density = getResources().getDisplayMetrics().density;
                indicator.setRadius(6 * density);
                indicator.setPageColor(0xFFFFFFFF);
                indicator.setFillColor(0x88a7a7a7);
                indicator.setStrokeColor(0xFF878585);
                indicator.setStrokeWidth(1 * density);

                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        };
        mRegisterTask.execute(null, null, null);
    }
}
