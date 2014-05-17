package com.example.sqlitedatabaseapp;

import java.util.List;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

public class SearchResultsActivity extends ListActivity {

	private CommentsDataSource datasource;
    
    public void onCreate(Bundle savedInstanceState) {
       
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);

        
        // Get the intent, verify the action and get the query
        //Intent intent = getIntent();
        //if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
          //String query = intent.getStringExtra(SearchManager.QUERY);
          //doMySearch(query);
       // }
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
    
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

    	datasource = new CommentsDataSource(this);
        datasource.open();
        System.out.println("intent.getAction()===>"+intent.getAction());
        System.out.println("intent.getAction()===>"+intent.getClass().getFields().toString());
        System.out.println("intent.getStringExtra() spinner===>"+intent.getStringExtra("spinner"));
        System.out.println("intent.getStringExtra() spinner1===>"+intent.getStringExtra("spinner1"));
        System.out.println("intent.getStringExtra() switchStatus===>"+intent.getStringExtra("switchStatus"));
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            System.out.println("query===>"+query);
            List<Schools> values = datasource.getSearchedSchool(query);
            setContentView(R.layout.activity_test_database);
            ArrayAdapter<Schools> adapter = new ArrayAdapter<Schools>(this,
        	        android.R.layout.simple_list_item_1, values);
        	  setListAdapter(adapter);
        	  Toast.makeText(this, "Records Returned " + values.size() , Toast.LENGTH_SHORT).show();
        	  //TextView header = (TextView) findViewById(R.id.header_txt);
        	  //header.setText("Schools Ranking");
        }
    }
    
    @Override
    public boolean onSearchRequested() {
        //pauseSomeStuff();
        return super.onSearchRequested();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

/*			// Inflate the menu; this adds items to the action bar if it is present.
	    //MenuInflater inflater = getMenuInflater();
	    //inflater.inflate(R.menu.test_database, menu);
	    //return super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.test_database, menu);
	    MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    
	    return super.onCreateOptionsMenu(menu);*/
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.test_database, menu);

	    // Associate searchable configuration with the SearchView
	    SearchManager searchManager =
	           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView =
	            (SearchView) menu.findItem(R.id.action_search).getActionView();
 	    searchView.setSearchableInfo(
	            searchManager.getSearchableInfo(getComponentName()));

	    return true;			
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	    switch (item.getItemId()) {
        case R.id.action_search:
            //openSearch();
        	//startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
            return true;
        case R.id.action_settings:
            //openSettings();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
	}
}