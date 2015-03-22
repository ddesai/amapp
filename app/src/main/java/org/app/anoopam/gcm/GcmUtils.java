package org.app.anoopam.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.app.anoopam.util.GeneralUtils;

import java.io.IOException;

public final class GcmUtils {
    // Google project id
    public static final String SENDER_ID = "574631367690";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    static AsyncTask<Void, Void, Void> mRegisterTask;
    static final String LOG_TAG = GcmUtils.class.getSimpleName();
    public static final String KEY_REG_ID = "registration_id";
    private static final String KEY_APP_VERSION = "appVersion";

    /**
     * Tag used on log messages.
     */
    static final String DISPLAY_MESSAGE_ACTION = "org.app.anoopam.DISPLAY_MESSAGE";
    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by the
     * UI and the background service.
     * 
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    public static void verifyGcmRegistration(Activity activity) {
        GoogleCloudMessaging gcm;
        String regId;
        // Checks the device for Play Services
        // If successful, then proceed with the registration.
        if (checkPlayServices(activity)) {
            gcm = GoogleCloudMessaging.getInstance(activity);
            regId = getRegistrationId(activity.getBaseContext());

            if (regId.isEmpty()) {
                registerInBackground(activity.getBaseContext(), gcm);
            }
        } else {
            Log.i(LOG_TAG, "No valid Google Play Services APK found.");
        }
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = GeneralUtils.getAMSharedPrefs(context);
        String regId = prefs.getString(KEY_REG_ID, "");
        Log.d(LOG_TAG, "RegistrationID: "+regId);
        Toast.makeText(context, regId, Toast.LENGTH_LONG).show();
        if (regId.isEmpty()) {
            Log.i(LOG_TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(KEY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(LOG_TAG, "App version changed.");
            return "";
        }
        return regId;
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private static void storeRegistrationId(Context context, String regId) {
        int appVersion = getAppVersion(context);
        Log.i(LOG_TAG, "Saving regId "+regId + " on app version " + appVersion);
        SharedPreferences.Editor editor = GeneralUtils.getAMSharedPrefsEditor(context);
        editor.putString(KEY_REG_ID, regId);
        editor.putInt(KEY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private static boolean checkPlayServices(Activity activity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(LOG_TAG, "This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private static void registerInBackground(final Context context, final GoogleCloudMessaging gcm) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                GoogleCloudMessaging gcmInstance = gcm;
                String msg = "";
                String regId = "";
                try {
                    if (gcmInstance == null) {
                        gcmInstance = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcmInstance.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regId;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    // sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.
                    storeRegistrationId(context, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }
            @Override
            protected void onPostExecute(String msg) {
            }
        }.execute(null, null, null);
    }
}