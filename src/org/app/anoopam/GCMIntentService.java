package org.app.anoopam;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
 
import com.google.android.gcm.GCMBaseIntentService;
 
import static org.app.anoopam.CommonUtilities.SENDER_ID;
import static org.app.anoopam.CommonUtilities.displayMessage;

 
public class GCMIntentService extends GCMBaseIntentService{
   
    private static final String TAG = "GCMIntentService";
    public static final String PREFS_NAME = "DhruServer";
    
 
    public GCMIntentService() {
        super(SENDER_ID);
    }
 
    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, "Your device registred with GCM");
    }
 
    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
       // Log.i(TAG, "Device unregistered");
       // displayMessage(context, getString(R.string.gcm_unregistered));
      //  ServerUtilities.unregister(context, registrationId);
    }
 
    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
       
        String message = intent.getExtras().getString("msg");
        String id = intent.getExtras().getString("id");
        String title = intent.getExtras().getString("title");
        displayMessage(context, message);
        generateNotification(context, message,id,title);
    }
 
    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
     //  String message = getString(R.string.gcm_deleted, total);
     //  displayMessage(context, message);
     //  notifies user
     //  generateNotification(context, message);
    }
 
    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }
 
    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        return super.onRecoverableError(context, errorId);
    }
 
    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    @SuppressLint("NewApi")
	private  void generateNotification(Context context, String message,String ids,String title) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        /*
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
       
        Notification notification = new Notification(icon, message, when);
       
         
        Intent notificationIntent = new Intent(context, QuickNews.class);
        notificationIntent.putExtra("qi", ids);
        
        SharedPreferences settings1 = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor2 = settings1.edit();
	    editor2.putString("IDS", message);
	    
	    editor2.commit();
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
      
        PendingIntent intent =PendingIntent.getActivity(context, 0, notificationIntent, 0);
       
       
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
      
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
         
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);  
        */
        /////////////////////////////////////////////////////////////////
        SharedPreferences settings1 = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor2 = settings1.edit();
	    editor2.putString("IDS", message);
	    editor2.commit();
	    
	    
        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final Intent productIntent = new Intent(this, QuickNews.class);
        productIntent.putExtra("IDS", message);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(QuickNews.class);
        stackBuilder.addNextIntent(productIntent);
        Random r = new Random();
        int i1 = r.nextInt(80 - 65) + 65;
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(i1, PendingIntent.FLAG_UPDATE_CURRENT);
       
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
                                                                                        .setContentTitle(title)
                                                                                        .setContentText(message)                                                                                    
                                                                                        .setContentIntent(pendingIntent)
                                                                                        .setWhen(System.currentTimeMillis())
                                                                                        .setAutoCancel(true);
        
        mBuilder.setStyle(new NotificationCompat.BigTextStyle(mBuilder).bigText(message));
         
        mBuilder.setVibrate(new long[] { 500, 500, 500, 500, 500 });
        mBuilder.setLights(Color.BLUE, 3000, 3000);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        mNotificationManager.notify(i1, mBuilder.build());
        ///////////////////////////////////////////////////////////////////////////
     
 
    }

 
}