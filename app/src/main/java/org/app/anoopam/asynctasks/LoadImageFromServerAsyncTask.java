package org.app.anoopam.asynctasks;

import android.content.Context;

import org.app.anoopam.util.TodayUpdateHelper;

/**
 * Loads image from Server and stores on the local storage
 *
 * @author ddesai
 */
public class LoadImageFromServerAsyncTask extends AbstractAsyncTask {

    /**
     * initializes the ImageLoader from server 
     *
     * @param ctx
     * @param actionParam
     * @param imageName (mKey)
     */
    public LoadImageFromServerAsyncTask (Context ctx, String actionParam, String imageName) {
        super(ctx, actionParam, imageName);
    }

    @Override
    protected String doInBackground(Void... params) {
        //mKey is the ImageName - which is used as a key in the local stoage
        TodayUpdateHelper.getImageFromServer(mContext, mActionParam, mKey);
        return null;
    }
}
