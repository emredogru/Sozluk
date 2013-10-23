package com.example.sozluk;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class Sozluk extends Activity {
	int requestCode=1000;
	boolean returned=true;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!checkConnection(getApplicationContext())){
			wirelessSettings(getApplicationContext());
		}
		setContentView(R.layout.activity_sozluk);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sozluk, menu);
		return true;
	}
	public boolean checkConnection(Context ctx){
		//İnternet erişimi sorgulanıyor.
		ConnectivityManager conMgr = (ConnectivityManager) ctx
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo i = conMgr.getActiveNetworkInfo();
		  if (i == null)
		    return false;
		  if (!i.isConnected())
		    return false;
		  if (!i.isAvailable())
		    return false;
		return true;
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==this.requestCode){
			returned=true;
		}
	}
	
	public void wirelessSettings(final Context ctx){
		//İnternet erişimi olana kadar kablosuz ayarlarına gönderiyor.
		Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!checkConnection(getApplicationContext())){
					if(returned){
						runOnUiThread(new Runnable() { 
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(ctx, "Bağlantı Yok", Toast.LENGTH_SHORT).show();
							}
						});
					
					  startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS),requestCode);
					  returned=false;
					
					}
				}
				
			}
		});
		thread.start();
	}

}
