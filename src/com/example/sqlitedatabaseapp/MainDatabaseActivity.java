package com.example.sqlitedatabaseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainDatabaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main_database);

/*
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_database, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    public void doSmall(View v)  {
	       // --- find the text view --
	    	//super.onCreate(savedInstanceState);
	    	//RelativeLayout relativeLayout = new RelativeLayout(this);

	    	//RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
	    	//                RelativeLayout.LayoutParams.FILL_PARENT,
	    	//                RelativeLayout.LayoutParams.FILL_PARENT);

	    	//create widgets
	    	  //      TextView text = new TextView(this);
	    	 //       text.setText("Simply dummy text");

	    	        // finally add your TextView to the RelativeLayout
	    	 //       relativeLayout.addView(text);

	    	        // and set your layout like main content
	    	  //      setContentView(relativeLayout, lParams);
	    	//setContentView(R.layout.setting_activity_database);
	    	//TextView txtView = (TextView) findViewById(R.id.footerText);
	        // -- change text size --
	       // txtView.setTextSize(14);
	    	Intent intent = new Intent(this, SettingPageActivity.class);
	    	startActivity(intent);
	       //return;
	   }
}
