package com.bloodante.hereby;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.*;

public class SendMessage extends Activity{
	AsyncHttpClient client = new AsyncHttpClient();
	EditText editText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_mesage);
		
		editText = (EditText) findViewById(R.id.leaveMessage);
	}
	
	public void leaveMessage(View v) {
		client.get("http://192.168.1.100:3000", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, String content) {
				editText.setText("success!");
			}
			
			@Override
			public void onFailure(Throwable error, String content) {
				editText.setText("error");
			}
		});
	}

}
