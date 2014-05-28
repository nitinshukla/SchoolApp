package com.example.sqlitedatabaseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainDatabaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main_database);

	    TextView allNearby =  (TextView) findViewById(R.id.AllNearby);
	    TextView primary =  (TextView) findViewById(R.id.Primary);
	    TextView secondary =  (TextView) findViewById(R.id.Secondary);

	    allNearby.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	doAllNearby(v);
	        }
	    });
	    
	    primary.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	doAllNearbyPrimary(v);
	        }
	    });

	    secondary.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	doAllNearbySecondary(v);
	        }
	    });
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

	
    public void doAllNearby(View v)  {
	    	Intent intent = new Intent(this, ShowLocationActivity.class);
	    	startActivity(intent);
    	}
    
    public void doAllNearbyPrimary(View v)  {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
	}
    
    public void doAllNearbySecondary(View v)  {
    	Intent intent = new Intent(this, SettingPageActivity.class);
    	startActivity(intent);
	}    
}
