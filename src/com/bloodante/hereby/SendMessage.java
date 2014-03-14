package com.bloodante.hereby;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SendMessage extends Activity{
	
	AsyncHttpClient client;
	HerebyApplication mApplication;
	EditText editText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message);
		
		client = new AsyncHttpClient();
		mApplication = (HerebyApplication) getApplication();
		editText = (EditText) findViewById(R.id.messageText);
	}
	
	public void leaveMessage(View v) {
		RequestParams param = new RequestParams();
		String msg = editText.getText().toString();
		param.put("lat", String.valueOf(mApplication.getLat()));
		param.put("lon", String.valueOf(mApplication.getLon()));
		param.put("message", msg);
		client.post("http://192.168.1.100:3000/message", param, new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				Toast.makeText(SendMessage.this,
						getResources().getString(R.string.toast_post_failed),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Toast.makeText(SendMessage.this,
						getResources().getString(R.string.toast_post_success),
						Toast.LENGTH_SHORT).show();
				SendMessage.this.finish();
			}
			
		});
	}

}
