package org.app.anoopam.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public final class GcmUtils {
    // Google project id
    public static final String SENDER_ID = "574631367690";
    static AsyncTask<Void, Void, Void> mRegisterTask;

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
    
    /**
     * starts the GCM GCM registration, if not already done
     *
     * @param ctx
     */
    public static void startGcmRegistration(Context ctx) {
        GCMRegistrar.checkDevice(ctx);
        GCMRegistrar.checkManifest(ctx);
        final String regId = GCMRegistrar.getRegistrationId(ctx);

        if (regId.equals("")) {
            GCMRegistrar.register(ctx, GcmUtils.SENDER_ID);
        } else {
            final Context context = ctx;
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
    }
}