package com.bloodante.hereby;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {
	
	TextView introText;
	AsyncHttpClient client = new AsyncHttpClient();
	LinearLayout mMessageList;
	HerebyApplication mApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mApplication = (HerebyApplication) getApplication();
		introText = (TextView) findViewById(R.id.herebyIntro);
		mMessageList = (LinearLayout) findViewById(R.id.messageList);
		mMessageList.setVisibility(View.INVISIBLE);
		
		mApplication.startUpdatingLocation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
	       //Inflate the layout for this fragment
	       View rootView = inflater.inflate(R.layout.message_list, container,false);
	}
	
	public void leaveMsg(View v) {
		startActivity(new Intent(this, SendMessage.class));
	}
	
	public void getLocal(View v) {
		introText.setText("Pending...");
		RequestParams params = new RequestParams();
		params.put("lat", String.valueOf(mApplication.getLat()));
		params.put("lon", String.valueOf(mApplication.getLon()));
		client.get("http://192.168.1.100:3000/message", params, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				introText.setVisibility(View.INVISIBLE);
				mMessageList.setVisibility(View.VISIBLE);
				mMessageList.removeAllViews();
				try {
					JSONArray data = response.getJSONArray("data");
					for (int i = 0; i < data.length(); i++) {
						JSONObject elem = data.getJSONObject(i);
						TextView text = new TextView(MainActivity.this);
						text.setText(elem.optString("message"));
						mMessageList.addView(text);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				introText.setText("Failed!");
			}
		});
	}

}
