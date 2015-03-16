package org.app.anoopam.asynctasks;

import android.content.Context;

import org.app.anoopam.util.TodayUpdateHelper;

/**
 * Pings the server to get the date of the latest ThakorjiToday
 * Updates the status in the local storage
 *
 * @author ddesai
 */
public class SyncDateWithServerAsyncTask extends AbstractAsyncTask {

    /**
     * initializes the DateSyncer
     *
     * @param ctx
     * @param actionParam
     * @param statusKey
     */
    public SyncDateWithServerAsyncTask (Context ctx, String actionParam, String statusKey) {
        super(ctx, actionParam, statusKey);
    }

    @Override
    protected String doInBackground(Void... params) {
        TodayUpdateHelper.updateTodayDateStatusFromServer(mContext, mActionParam, mKey);
        return null;
    }
    
    @Override
    protected void onPostExecute(String param) {
        
    }
}