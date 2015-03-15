package org.app.anoopam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class Comm {
	static ProgressDialog progressDialog;
	
	  
	public static void startLoader2(final Context container){
        progressDialog = ProgressDialog.show(container,"", "Loading please wait...");
        progressDialog.show();
	}
		
		
	public static void startLoader(final Activity activity){
	       
        progressDialog = ProgressDialog.show(activity,"", "Loading please wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
	}
	
	public  static void stopLoader(final Activity activity){      
		try{
        progressDialog.dismiss();
		}catch(Exception justop){
			
		}
	}
	
	
	  
	public static void stopLoader2(final Context container){
		progressDialog.dismiss();
	}
}
