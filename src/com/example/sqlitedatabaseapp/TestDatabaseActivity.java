package com.example.sqlitedatabaseapp;

/*import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
*/
import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

//import com.example.sqlitedatabaseapp.R;

//import android.*;
public class TestDatabaseActivity extends ListActivity {
	  private CommentsDataSource datasource;

	 /* @Override public void onActivityCreated(Bundle savedInstanceState) { 
		  View v = getActivity().getLayoutInflater().inflate(R.layout.header, null); 
		  this.getListView().addHeaderView(v); 
		  rla = new RowListAdapter(getActivity().getLayoutInflater(), R.layout.itemlayout, rl); 
		  setListAdapter(rla); 
		  super.onActivityCreated(savedInstanceState);
		  } */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.fragment_test_database);

	    datasource = new CommentsDataSource(this);
	    datasource.open();
	    
	    final MySQLiteHelper dbHelper = new MySQLiteHelper(getBaseContext());
	    try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    List<Schools> values = datasource.getAllComments();
	    //TextView tv = new TextView(this);
	    //tv.setText("Hello");
	    //getListView().addHeaderView(tv);
	    //View header = getLayoutInflater().inflate(R.., null);
	    //View footer = getLayoutInflater().inflate(R.layout.footer, null);
	    //ListView listView = getListView();
	    //listView.addHeaderView(header);
	    //listView.addFooterView(footer);
	    //TextView header = (TextView) findViewById(R.id.header_txt);
	    //header.setText("Schools Ranking");
	    //TextView header1 = (TextView) findViewById(R.id.header_txt);
	    //header1.setText("Total ");
	    //TextView footer = (TextView) findViewById(R.id.footer_txt);
	    //footer.setText("This is my footer text");
	    // elements in a ListView

	    ArrayAdapter<Schools> adapter = new ArrayAdapter<Schools>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	  }

	  @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	        // TODO Auto-generated method stub
	        super.onListItemClick(l, v, position, id);
	        String abc = l.getAdapter().toString();
	        System.out.println(abc);
	        Schools item = (Schools) getListAdapter().getItem(position);
	        System.out.println(item.getSchoolName());
	 AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

	        // set the message to display
	          alertbox.setMessage("School Name Clicked is "+item.getSchoolName()).show();

	    }
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
		    if (item.getItemId() == R.id.action_search) {
		    	return true;
		    } else if (item.getItemId() == R.id.action_settings) {
		    	return true;
		    } else{
		    	return super.onOptionsItemSelected(item);
		    }
		}
		
		private void openSearch() {
		    // start or show the search activity/fragment
			Toast.makeText(this, "Search button pressed Pankaj", Toast.LENGTH_SHORT).show();
		}
		
/*		private void openSettings() {
		    Toast.makeText(this, "Settings button pressed Shubha", Toast.LENGTH_SHORT).show();
		}*/
		
		private void  openSettings(){
			 startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
			}
		
	  // Will be called via the onClick attribute
	  // of the buttons in main.xml
	  public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter<Schools> adapter = (ArrayAdapter<Schools>) getListAdapter();
	    Schools schools = new Schools();
	    //Intent intent = new Intent(this, TestDatabaseActivity.class);
	    //EditText schoolName = (EditText) findViewById(R.id.school_name);
	    //EditText averageTotal = (EditText) findViewById(R.id.average_total);
	    //EditText sector = (EditText) findViewById(R.id.sector);
	    //EditText state = (EditText) findViewById(R.id.state);
	    //EditText suburb = (EditText) findViewById(R.id.suburb);
	    //String message = schoolName.getText().toString();
	    //schools.setSchoolName(schoolName.getText().toString());
	   // schools.setAverageTotal(averageTotal.getText().toString());
	    //schools.setSector(sector.getText().toString());
	    //schools.setState(state.getText().toString());
	    //schools.setSuburb(suburb.getText().toString()); 
	    
	    //System.out.println("message===>"+message);
	    //intent.putExtra(EXTRA_MESSAGE, message);
	    //switch (view.getId()) {
	    //case R.id.add:
	      //String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
	      //int nextInt = new Random().nextInt(3);
	      // save the new comment to the database
	      //schools = datasource.createComment(comments[nextInt]);
	      //schools = datasource.createComment(schools);
	      //adapter.add(schools);
	      //break;
	    //case R.id.delete:
	      //if (getListAdapter().getCount() > 0) {
	    	//  schools = (Schools) getListAdapter().getItem(0);
	      //  datasource.deleteComment(schools);
	       // adapter.remove(schools);
	      //}
	      //break;
	    //}
	    adapter.notifyDataSetChanged();
	  }

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			// Inflate the menu; this adds items to the action bar if it is present.
		    //MenuInflater inflater = getMenuInflater();
		    //inflater.inflate(R.menu.test_database, menu);
		    //return super.onCreateOptionsMenu(menu);
			getMenuInflater().inflate(R.menu.test_database, menu);
		    //MenuItem searchItem = menu.findItem(R.id.action_search);
		    //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		    SearchManager searchManager =
		            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		     SearchView searchView =
		             (SearchView) menu.findItem(R.id.action_search).getActionView();
		     searchView.setSearchableInfo(
		             searchManager.getSearchableInfo(getComponentName()));
		    return super.onCreateOptionsMenu(menu);
		}
		
		//--- Implement the event handler for the first button.
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
	  @Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }

	} 


