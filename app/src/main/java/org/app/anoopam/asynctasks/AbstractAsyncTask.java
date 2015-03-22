package org.app.anoopam.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

public abstract class AbstractAsyncTask extends AsyncTask<Void, String, String> {
    static final String LOG_TAG = AbstractAsyncTask.class.getSimpleName();
    Context mContext;
    String mActionParam;
    String mKey;

    public AbstractAsyncTask(Context context, String actionParam, String key) {
        this.mContext = context;
        this.mActionParam = actionParam;
        this.mKey = key;
    }
}
