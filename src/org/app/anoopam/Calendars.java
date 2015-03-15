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
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Calendars  extends FragmentActivity{
	public static final String PREFS_NAME = "AM";
	TestFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    AsyncTask<Void, Void, Void> mRegisterTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uk);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		Calendar rightNow = Calendar.getInstance();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		 final Bitmap[] offerImagesB = {

				 	drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
					drawableToBitmap(getResources().getDrawable(R.drawable.settings))
		 };
		 
		 
		if(settings.getString("CALENDAR","").equals((rightNow.get(Calendar.YEAR)+"")))
		{
			for(int i=1;i<=12;i++)
			{
				offerImagesB[i-1]=(loadImageFromStorage(settings.getString("CAL"+i,""),"CAL"+i+".jpg"));
			}
		}else{
			
             mRegisterTask = new AsyncTask<Void, Void, Void>() {
        	 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        	 SharedPreferences.Editor editor = settings.edit();
        	 Calendar rightNow = Calendar.getInstance();
        	 @Override
        	    protected void onPreExecute() {
        		 Comm.startLoader(Calendars.this);
        	        super.onPreExecute();
        	    }

	         @Override
	         protected Void doInBackground(Void... params) {
	        	
					for(int i=1;i<=12;i++)
					{
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost("http://anoopam.org/eCommunity/calendar.php?action="+(rightNow.get(Calendar.YEAR)+"") );
						try {
						    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					        nameValuePairs.add(new BasicNameValuePair("year",(rightNow.get(Calendar.YEAR)+"")));
					        nameValuePairs.add(new BasicNameValuePair("month",i+""));
					        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					        HttpResponse response = httpclient.execute(httppost);
					        HttpEntity entity = response.getEntity();
					        InputStream is = entity.getContent();
					        String data=convertStreamToString(is);
					        JSONObject json_data = null;
							try {
								json_data = new JSONObject(data);
								offerImagesB[i-1]=decodeBase64(json_data.getString("DATA"));
							
								String path=saveToInternalSorage( decodeBase64(json_data.getString("DATA") ),"CAL"+i+".jpg");
								editor.putString("CAL"+i,path);
								if(i==12){
								editor.putString("CALENDAR", rightNow.get(Calendar.YEAR)+"");
								}
						    	editor.commit();
						
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (ClientProtocolException e) {
					    } catch (IOException e) {
					    }
						
					}
			
					return null;
                 }

                 @Override
                 protected void onPostExecute(Void result) {
                     mRegisterTask = null;
                     Comm.stopLoader(Calendars.this);
                     finish();
                     startActivity(getIntent());
                 }

             };
             mRegisterTask.execute(null, null, null);
		}
		mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
		mAdapter.setCount(12);
		mAdapter.setImagesB(offerImagesB);	
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator = indicator;
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(6 * density);
        indicator.setPageColor(0xFFFFFFFF);
        indicator.setFillColor(0x88a7a7a7);
        indicator.setStrokeColor(0xFF878585);
        indicator.setStrokeWidth(1*density);
        
		
	}
	public static Bitmap drawableToBitmap (Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);

	    return bitmap;
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
