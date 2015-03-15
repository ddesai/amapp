package org.app.anoopam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Quote extends Activity{ 
	
	public static final String PREFS_NAME = "AM";
	 AsyncTask<Void, Void, Void> mRegisterTask;
	int i;
	String QT;
	int pos;
	String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quote);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		Calendar rightNow = Calendar.getInstance();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final SharedPreferences.Editor editor = settings.edit();
		
		final ImageView quote = (ImageView) findViewById(R.id.quote);
		
		
		////////////////////////////////////////////////////
		i=rightNow.get(Calendar.DATE);
	
			if(!settings.getString("Q"+i,"").equals("")){
				quote.setImageBitmap(loadImageFromStorage(settings.getString("Q"+i,""),"Q"+i+".jpg"));
			}else{		
				mRegisterTask = new AsyncTask<Void, Void, Void>() {
			   		@Override
			       	 protected void onPreExecute() {
			   				Comm.startLoader(Quote.this);
			       	        super.onPreExecute();
			       	} 
			   		 @Override
			         protected Void doInBackground(Void... param) {
			   			
						
							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost("http://anoopam.org/eCommunity/todays_quote.php?action="+i);
							try {
							    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
						        nameValuePairs.add(new BasicNameValuePair("search","data"));
						        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						        HttpResponse response = httpclient.execute(httppost);
						        HttpEntity entity = response.getEntity();
						        InputStream is = entity.getContent();
						        String data=convertStreamToString(is);
						        JSONObject json_data = null;
								try {
									json_data = new JSONObject(data);
									//quote.setImageBitmap(decodeBase64(json_data.getString("DATA")));
									QT=(json_data.getString("DATA"));
									pos=i;
								//	editor.putString("Q"+i,path);
							//		editor.commit();
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} catch (ClientProtocolException e) {
						    } catch (IOException e) {
						    }
					return null;
		   		 }
		   		 @Override
              protected void onPostExecute(Void result) {
                  mRegisterTask = null;
                  quote.setImageBitmap(decodeBase64(QT));
                  path=saveToInternalSorage( decodeBase64(QT),"Q"+pos+".jpg");
                  editor.putString("Q"+pos,path);
				  editor.commit();
                  Comm.stopLoader(Quote.this);
              }
				
		   	 };
		   	 mRegisterTask.execute(null, null, null);
			}
		
	
		//////////////////////////////////////////////////
		
		
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comm, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.previous) {
			 Intent i=new Intent(getBaseContext(),Dashboard.class);
            startActivity(i);
		}
		return false;
	}
	private Bitmap loadImageFromStorage(String path,String name)
	{
	    try {
	        File f=new File(path, name);
	        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
	        return b;
	    } 
	    catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
		return null;

	}
	private String saveToInternalSorage(Bitmap bitmapImage,String name){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
      
        File directory = cw.getDir("AnoopamMission", Context.MODE_PRIVATE);
        File mypath=new File(directory,name);
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
	public static Bitmap decodeBase64(String input) 
	{
	    byte[] decodedByte = Base64.decode(input, 0);
	    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
	}
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
}
