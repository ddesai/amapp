package org.app.anoopam.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write Javadoc for ThakorjiTodayHelper.
 *
 * @author ddesai
 */
public class TodayUpdateHelper {
	
    /**
     * creates 4 bitmaps for ThakorjiToday
     *
     * @param bitmaps
     * @param drawable
     */
    public static void getThakorjiTodayBitmaps(Bitmap[] bitmaps, Drawable drawable) {
        bitmaps[0] = drawableToBitmap(drawable);
        bitmaps[1] = drawableToBitmap(drawable);
        bitmaps[2] = drawableToBitmap(drawable);
        bitmaps[3] = drawableToBitmap(drawable);
    }
    
    
    /**
     * Store something on Local Storage
     * 
     * 
     */
    
    
    /**
     * Converts Stream to String
     *
     * @param is
     * @return String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
        } catch (IOException e) {
        }
        return sb.toString();
    }
    
    /**
     * converts Drawable to Bitmap
     *
     * @param drawable
     * @return Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    
    /**
     * gets Bitmap from the URL passed in the String
     *
     * @param src
     * @return Bitmap
     */
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * gets the Bitmap from the String
     *
     * @param input
     * @return Bitmap
     */
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    
    /**
     * loads the Bitmap from the given image path
     *
     * @param path
     * @param name
     * @return Bitmap
     */
    public static Bitmap loadImageFromStorage(String path, String name) {
        try {
            File f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * stores the Bitmap to the internal storage
     *
     * @param ctx
     * @param bitmapImage
     * @param name
     * @return String - absolute path of the image
     */
    public static String saveImageToInternalStorage(Context ctx, Bitmap bitmapImage, String name) {
        ContextWrapper cw = new ContextWrapper(ctx);
        File directory = cw.getDir("AnoopamMission", Context.MODE_PRIVATE);
        File mypath = new File(directory, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }
    
    /**
     * update the DateStatus locally to align with Today's date from server
     *
     * @param ctx
     * @param actionParam
     * @param dateStatusKey
     */
    public static void updateTodayDateStatusFromServer(Context ctx, String actionParam, String dateStatusKey) {
        HttpClient httpclient = new DefaultHttpClient();
        String amServerBaseUrl = "http://anoopam.org/eCommunity/thakorji.php?action=";
        SharedPreferences settings = GeneralUtils.getAMSharedPrefs(ctx);
        SharedPreferences.Editor editor = settings.edit();
        HttpPost httppost = new HttpPost(amServerBaseUrl+actionParam);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("status", "true"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            String data = TodayUpdateHelper.convertStreamToString(is);
            JSONObject json_data = null;
            try {
                json_data = new JSONObject(data);
                if (!settings.getString(dateStatusKey, "").equals(json_data.getString("STATUS"))) {
                    editor.clear();
                    editor.commit();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * returns Bitmap image after loading it from server 
     * and stores it on internal storage
     *
     * @param ctx
     * @param actionParam
     * @param imageName
     * @return Bitmap - image obtained from the server
     */
    public static Bitmap getImageFromServer(Context ctx, String actionParam, String imageName) {
        return getImageFromServer(ctx, actionParam, imageName, null, false);
    }
    
    /**
     * returns Bitmap image after loading it from server 
     * and stores it on internal storage
     * - it also updates the dateStatus
     * 
     * @param ctx
     * @param actionParam
     * @param imageName - e.g. usa1.jpg
     * @param dateStatusKey
     * @param updateStatus
     * @return Bitmap - image obtained from the server
     */
    public static Bitmap getImageFromServer(Context ctx, String actionParam, 
            String imageName, String dateStatusKey, boolean updateStatus) {
        HttpClient httpclient = new DefaultHttpClient();
        String amServerBaseUrl = "http://anoopam.org/eCommunity/thakorji.php?action=";
        SharedPreferences.Editor editor = GeneralUtils.getAMSharedPrefsEditor(ctx);
        HttpPost httppost = new HttpPost(amServerBaseUrl+actionParam);
        Bitmap newImage = null;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("search", "data"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            String data = TodayUpdateHelper.convertStreamToString(is);
            JSONObject json_data = null;
            try {
                json_data = new JSONObject(data);
                newImage = TodayUpdateHelper.decodeBase64(json_data.getString("DATA"));
                String path = TodayUpdateHelper.saveImageToInternalStorage(ctx,
                        TodayUpdateHelper.decodeBase64(json_data.getString("DATA")), imageName);
                editor.putString(imageName, path);
                if(updateStatus) {
                    editor.putString(dateStatusKey, json_data.getString("STATUS"));
                }
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImage;
    }
}
