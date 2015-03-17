package org.app.anoopam.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.app.anoopam.util.TodayUpdateHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class GCMRegistration {
    static final String LOG_TAG = GCMRegistration.class.getSimpleName();
    public static final String PREFS_NAME = "AM";
    static AsyncTask<Void, Void, Void> mRegisterTask;
    static Context mContext;
    static String mRegId = null;

    public static void sendDeviceID(Context ctx) {
        mContext = ctx;

        GCMRegistrar.checkDevice(ctx);
        GCMRegistrar.checkManifest(ctx);
        final SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        mRegId = GCMRegistrar.getRegistrationId(ctx);
        
        Log.i(LOG_TAG, "GcmRegId: " + mRegId);
        
        if (mRegId != null && !mRegId.isEmpty() && settings.getBoolean("GCM", true)) {
            GcmUtils.displayMessage(ctx, mRegId);
            mRegisterTask = new AsyncTask<Void, Void, Void>() {
                
                @Override
                protected Void doInBackground(Void... params) {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://anoopam.org/eCommunity/gcm.php?action=registration");
                    String android_id = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("m", android_id));
                    nameValuePairs.add(new BasicNameValuePair("gcm", mRegId));
                    try {
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        InputStream is = entity.getContent();
                        String data = TodayUpdateHelper.convertStreamToString(is);
                        if (data.equals("DONE")) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("GCM", false);
                            editor.commit();
                        }
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    mRegisterTask = null;
                    mContext = null;
                    mRegId = null;
                }
            };
            mRegisterTask.execute(null, null, null);
        }
    }
}
