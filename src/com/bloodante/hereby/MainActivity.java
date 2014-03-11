package com.bloodante.hereby;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity {
	
	TextView introText;
	AsyncHttpClient client = new AsyncHttpClient();
	ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		introText = (TextView) findViewById(R.id.herebyIntro);
		mListView = (ListView) findViewById(R.id.mainListView);
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
	
	public void getLocal(View v) {
		introText.setText("Pending...");
		client.get("http://10.0.2.2:3000", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, String content) {
				introText.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				introText.setText("Failed!");
			}
		});
	}

}
