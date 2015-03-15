package org.app.anoopam;

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


import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.widget.Toast;
public class Mogri  extends FragmentActivity{
	
	AsyncTask<Void, Void, Void> mRegisterTask;
	AsyncTask<Void, Void, Void> mRegisterTask2;
	TestFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    public static final String PREFS_NAME = "AM";
    
    protected void onResume()
    {
    	super.onResume(); 
    }
    
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uk);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
	     
		final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final SharedPreferences.Editor editor = settings.edit();
    	
		
		  final Bitmap[] offerImagesB = {
				drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
				drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
				drawableToBitmap(getResources().getDrawable(R.drawable.settings)),
				drawableToBitmap(getResources().getDrawable(R.drawable.settings))
		  };
		  
		  
		    mRegisterTask = new AsyncTask<Void, Void, Void>() {
		    	 ProgressDialog progressDialog;
		   		@Override
		       	 protected void onPreExecute() {
			   			progressDialog = ProgressDialog.show(Mogri.this,"", "Loading please wait...");
			   	        progressDialog.show();
		       	        super.onPreExecute();
		       	} 
		   		
		   		 @Override
		         protected Void doInBackground(Void... params) {
		   			 
		   		
				HttpClient httpclient2 = new DefaultHttpClient();
				HttpPost httppost2 = new HttpPost("http://anoopam.org/eCommunity/thakorji.php?action=mogri1");
				try {
				    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			        nameValuePairs.add(new BasicNameValuePair("status","true"));
			        httppost2.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        HttpResponse response = httpclient2.execute(httppost2);
			        HttpEntity entity = response.getEntity();
			        InputStream is = entity.getContent();
			        String data=convertStreamToString(is);
			        JSONObject json_data = null;
					try {
						json_data = new JSONObject(data);
						
						if(!settings.getString("MOGRIDate","").equals( json_data.getString("STATUS")))
						{
							editor.putString("MOGRI1","");
							editor.putString("MOGRI2","");
							editor.putString("MOGRI3","");
							editor.putString("MOGRI4","");
							editor.commit();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ClientProtocolException e) {
			    } catch (IOException e) {
			    }
		
				
			
		   if(settings.getString("MOGRI1","").equals(""))
		   { 		
			   	
			   			 
			   			 
			   			 
			   			 	HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost("http://anoopam.org/eCommunity/thakorji.php?action=mogri1");
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
									offerImagesB[0]=decodeBase64(json_data.getString("DATA"));
									//editor.putLong("MOGRI1", Long.parseLong(json_data.getString("DATA")));
									String path=saveToInternalSorage( decodeBase64(json_data.getString("DATA") ),"MOGRI1.jpg");
									editor.putString("MOGRI1",path);
									editor.putString("MOGRIDate", json_data.getString("STATUS"));
							    	editor.commit();
								
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (ClientProtocolException e) {
						    } catch (IOException e) {
						    }
							
							httppost = new HttpPost("http://anoopam.org/eCommunity/thakorji.php?action=mogri3");
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
									offerImagesB[1]=decodeBase64(json_data.getString("DATA"));
									//editor.putLong("MOGRI2", Long.parseLong(json_data.getString("DATA")));
									String path=saveToInternalSorage( decodeBase64(json_data.getString("DATA") ),"MOGRI2.jpg");
									editor.putString("MOGRI2",path);
									editor.commit();
								
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (ClientProtocolException e) {
						    } catch (IOException e) {
						    }
							

							httppost = new HttpPost("http://anoopam.org/eCommunity/thakorji.php?action=mogri5");
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
									offerImagesB[2]=decodeBase64(json_data.getString("DATA"));
									//editor.putLong("MOGRI3", Long.parseLong(json_data.getString("DATA")));
									String path=saveToInternalSorage( decodeBase64(json_data.getString("DATA") ),"MOGRI3.jpg");
									editor.putString("MOGRI3",path);
									editor.commit();
								
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (ClientProtocolException e) {
						    } catch (IOException e) {
						    }
							
							
							httppost = new HttpPost("http://anoopam.org/eCommunity/thakorji.php?action=mogri7");
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
									offerImagesB[3]=decodeBase64(json_data.getString("DATA"));
									//editor.putLong("MOGRI4", Long.parseLong(json_data.getString("DATA")));
									String path=saveToInternalSorage( decodeBase64(json_data.getString("DATA") ),"MOGRI4.jpg");
									editor.putString("MOGRI4",path);
									editor.commit();
								
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (ClientProtocolException e) {
						    } catch (IOException e) {
						    }
							
							
						
				   
			
		   }else{
			   offerImagesB[0]=(loadImageFromStorage(settings.getString("MOGRI1",""),"MOGRI1.jpg"));
			   offerImagesB[1]=(loadImageFromStorage(settings.getString("MOGRI2",""),"MOGRI2.jpg"));
			   offerImagesB[2]=(loadImageFromStorage(settings.getString("MOGRI3",""),"MOGRI3.jpg"));
			   offerImagesB[3]=(loadImageFromStorage(settings.getString("MOGRI4",""),"MOGRI4.jpg"));
		   }
		 
		   //Comm.stopLoader(Mogri.this);
			
  			return null;
  		 }
		   		 
		   		 
		   		 
  		 @Override
        protected void onPostExecute(Void result) {
            mRegisterTask = null;
            
            
            mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
			mAdapter.setCount(4);
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
	       
	     //   Comm.stopLoader(Mogri.this);
	        if (progressDialog != null) {
                progressDialog.dismiss();
            }

        }
  	 };
  	 mRegisterTask.execute(null, null, null);
		
			
			

	        
        
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
}
