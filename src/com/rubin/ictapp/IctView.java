package com.rubin.ictapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class IctView extends Activity {

	private WebView webView;
	private WebSettings webSettings;
	private ProgressDialog progressBar;
	private static final String TAG = "IctView";
	
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.ict);
		webView = (WebView) findViewById(R.id.webView);
		
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		
		progressBar = ProgressDialog.show(IctView.this, "Page Loading", "Loading...");
		
		webView.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i(TAG, "Processing webView url click...");
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				if (progressBar.isShowing()) {
					progressBar.dismiss();
					webView.clearHistory();
				}
			}
			
			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Log.e(TAG, "Error: " + description);
				Toast.makeText(IctView.this, "Error: " + description, Toast.LENGTH_SHORT).show();
				alertDialog.setTitle("Error");
				alertDialog.setMessage(description);
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
			}
		});
		
		try {
			webView.loadUrl("http://www.ict4agri.com/app");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void onBackPressed(){
		Log.d(TAG, "OnBackPressed Called");
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			showDialog(IctView.this, "Exit", "Are you sure you want to exit?");
		}
			
	}

	private void showDialog(Activity activity, String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    if (title != null)
	        builder.setTitle(title);
	    builder.setMessage(message);
	    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				IctView.this.finish();
			}
		});
	            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
	    builder.show();
		
	}
	
	
}
