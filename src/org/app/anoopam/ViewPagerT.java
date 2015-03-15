package org.app.anoopam;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

@SuppressLint("ValidFragment")
public final class ViewPagerT extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    int imageSource;
    Bitmap myBitmap;
    
    public ViewPagerT(int imageSource) {
        this.imageSource = imageSource;
        
    }
    public ViewPagerT(Bitmap imageSource) {
        this.myBitmap = imageSource;
        
    }
    public ViewPagerT() {
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            imageSource = savedInstanceState.getInt(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.page, null);
        /*
        ImageView image = (ImageView)root.findViewById(R.id.slider_image);
        image.setImageResource(imageSource);
        image.setImageBitmap(myBitmap);
        */
        
        WebView webview = (WebView)root.findViewById(R.id.slider_image);
        String html="<html><body bgcolor='#b6d8f0'><center><img src='{IMAGE_URL}' /></center></body></html>";
        Bitmap bitmap = myBitmap;
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        // Convert bitmap to Base64 encoded image for web
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imgageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        String image2 = "data:image/png;base64," + imgageBase64;
        html = html.replace("{IMAGE_URL}", image2);
        webview.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", "");
        setRetainInstance(true);
        return root;
    }
   
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CONTENT, imageSource);
    }
}