package com.example.sqlitedatabaseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainDatabaseActivity extends Activity {
	private TextView enteredText;
	//private Button btnSubmit;
/*
 * 	public void doSmall(View v)  {
	
	    	Intent intent = new Intent(this, SettingPageActivity.class);
	    	startActivity(intent);

	   }(non-Javadoc)
 * @see android.app.Activity#onCreate(android.os.Bundle)
 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main_database);
		
	    TextView allNearby =  (TextView) findViewById(R.id.AllNearby);
	    TextView primary =  (TextView) findViewById(R.id.Primary);
	    TextView secondary =  (TextView) findViewById(R.id.Secondary);
	     Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
	     enteredText = (TextView) findViewById(R.id.searchSchool);
	    
	 	btnSubmit.setOnClickListener(new OnClickListener() {
	 		  
		 	  @Override
		 	  public void onClick(View v) {
		  
			    	Intent intent = new Intent(MainDatabaseActivity.this,SearchResultsActivity.class);
			    	intent.setAction("Settings");
			    	intent.putExtra("enteredText", String.valueOf(enteredText.getText()));
			    	startActivity(intent);
		 	    Toast.makeText(MainDatabaseActivity.this,
		 		"OnClickListener : " + 
		                 "\nenteredText : "+ String.valueOf(enteredText.getText()) ,
		 			Toast.LENGTH_SHORT).show();
		    	//Intent intent = new Intent();
		 	   
		 	  }

		 	});
	 	
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
	    	Intent intent = new Intent(this, MainActivity.class);
	    	intent.putExtra("allNearBy", "allNearBy");
	    	startActivity(intent);
    	}
    
    public void doAllNearbyPrimary(View v)  {
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra("allNearBy", "allNearByPrimary");
    	startActivity(intent);
	}
    
    public void doAllNearbySecondary(View v)  {
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra("allNearBy", "allNearBySecondary");
    	startActivity(intent);
	}    
}
