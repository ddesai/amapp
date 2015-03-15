package org.app.anoopam.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * TODO: Write Javadoc for GeneralUtils.
 *
 * @author ddesai
 */
public class GeneralUtils {
    public static final String PREFS_NAME = "AM";
    
    /**
     * returns the AM SharedPreferences Editor 
     *
     * @param ctx
     * @return SharedPreferences.Editor
     */
    public static SharedPreferences.Editor getAMSharedPrefsEditor(Context ctx) {
        return ctx.getSharedPreferences(PREFS_NAME, 0).edit();    
    }    
    
    /**
     * returns the AM SharedPreferences 
     *
     * @param ctx
     * @return SharedPreferences
     */
    public static SharedPreferences getAMSharedPrefs(Context ctx) {
        return ctx.getSharedPreferences(PREFS_NAME, 0);
    }    

}
