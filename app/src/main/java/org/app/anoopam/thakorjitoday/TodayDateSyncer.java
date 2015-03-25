package org.app.anoopam.thakorjitoday;

import android.content.Context;
import android.content.SharedPreferences;

import org.app.anoopam.asynctasks.SyncDateWithServerAsyncTask;
import org.app.anoopam.util.GeneralUtils;

/**
 * TodayDateSyncer - Synchronizes the Today's date with server
 *
 * @author ddesai
 */
public class TodayDateSyncer {

    final static String DATE_STATUS_KEY = "USADate";
    final static String DATE_STATUS_KEY_PARAM = "usa1";
    
    /**
     * Synchronizes the device status with Thakorji Today's date from server
     *
     * @param ctx
     */
    public static void syncWithServer(Context ctx) {
        new SyncDateWithServerAsyncTask(ctx, DATE_STATUS_KEY_PARAM, DATE_STATUS_KEY).execute();
    }
    
    /**
     * If there is non-Empty value, that means it's current 
     * // FIXME can be done in a better way
     * 
     * @param ctx
     * @return true if the DATE_STATUS_KEY is not Empty
     */
    public static boolean isDateCurrent(Context ctx) {
        SharedPreferences settings = GeneralUtils.getAMSharedPrefs(ctx);
        return !settings.getString(DATE_STATUS_KEY, "").equals("");
    }
}
