package com.example.sqlitedatabaseapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLngBounds;

public class MainDatabaseActivity extends Activity {
	private TextView enteredText;
	private static final String API_KEY = "AIzaSyCdvCg0R-_rxj8biH7LPXcMHVT52xoCTHw";
	private String TAG = "MainDatabaseActivity";
	private CommentsDataSource datasource;

	// private Button btnSubmit;
	/*
	 * public void doSmall(View v) {
	 * 
	 * Intent intent = new Intent(this, SettingPageActivity.class);
	 * startActivity(intent);
	 * 
	 * }(non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main_database);
		
		/*AsyncCallWS task = new AsyncCallWS();
		task.execute();*/
		
		TextView allNearby = (TextView) findViewById(R.id.AllNearby);
		TextView primary = (TextView) findViewById(R.id.Primary);
		TextView secondary = (TextView) findViewById(R.id.Secondary);
		Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
		enteredText = (TextView) findViewById(R.id.myautocomplete);

		/*
		 * btnSubmit.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * Intent intent = new
		 * Intent(MainDatabaseActivity.this,SearchResultsActivity.class);
		 * intent.setAction("Settings"); intent.putExtra("enteredText",
		 * String.valueOf(enteredText.getText())); startActivity(intent);
		 * Toast.makeText(MainDatabaseActivity.this, "OnClickListener : " +
		 * "\nenteredText : "+ String.valueOf(enteredText.getText()) ,
		 * Toast.LENGTH_SHORT).show(); //Intent intent = new Intent();
		 * 
		 * }
		 * 
		 * });
		 */

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
		 * if (savedInstanceState == null) {
		 * getFragmentManager().beginTransaction() .add(R.id.container, new
		 * PlaceholderFragment()).commit(); }
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_database, menu);
		return true;
	}

	public List<Place> findPlaces(String schoolName) {
		String urlString = makeUrl(schoolName);

		try {
			// JSONObject json =
			// JSONFunction.getJSONfromURL("https://maps.googleapis.com/maps/api/place/search/json?location=-33.8670522,151.1957362&radius=1000&types=resturants&sensor=false&key=0bBgLl42nWwl7TQHrAFpY89v2FeLlijIGTLJ1AA");
			String json = getJSON(urlString);

			// System.out.println(json);
			JSONObject object = new JSONObject(json);
			JSONArray array = object.getJSONArray("results");

			ArrayList<Place> arrayList = new ArrayList<Place>();
			for (int i = 0; i < array.length(); i++) {
				try {
					Place place = Place
							.jsonToPontoReferencia((JSONObject) array.get(i));

					// Log.v("Places Services ", "" + place);

					arrayList.add(place);
				} catch (Exception e) {
				}
			}
			return arrayList;
		} catch (JSONException ex) {
			Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return null;
	}

	private String makeUrl(String schoolName) {

		StringBuilder urlString = new StringBuilder(
				"https://maps.googleapis.com/maps/api/place/textsearch/json?");
		urlString.append("query=" + schoolName);
		urlString.append("&radius=10000"); // take care of spaces here
		urlString.append("&types=school"); //
		urlString.append("&sensor=true"); //
		urlString.append("&key=" + API_KEY); //

		return urlString.toString();
	}

	protected String getJSON(String url) {
		return getUrlContents(url);
	}

	private String getUrlContents(String theUrl) {
		StringBuilder content = new StringBuilder();

		try {
			URL url = new URL(theUrl);

			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()), 8);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}

			bufferedReader.close();
		}

		catch (Exception e) {

			System.out.println(e);
			// e.printStackTrace();

		}

		return content.toString();
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

	public void doAllNearby(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("allNearBy", "allNearBy");
		startActivity(intent);
	}

	public void doAllNearbyPrimary(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("allNearByPrimary", "allNearByPrimary");
		startActivity(intent);
	}

	public void doAllNearbySecondary(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("allNearBySecondary", "allNearBySecondary");
		startActivity(intent);
	}

	private class AsyncCallWS extends
			AsyncTask<List<Schools>, List<Schools>, List<Schools>> {
		@Override
		protected List<Schools> doInBackground(List... params) {
			Log.i(TAG, "doInBackground");
			List<Schools> findPlaces = calculate();
			return findPlaces;
		}

		@Override
		protected void onPostExecute(List<Schools> findPlaces) {
			Log.i(TAG, "onPostExecute");
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			if (null != findPlaces) {

				System.out.println("Schol name is same onPostExecute");
			} 
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
		}

	}

	public List<Schools> calculate() {

		datasource = new CommentsDataSource(this);
		datasource.open();
		List<Place> findPlacesList = null;
		List<Schools> values = datasource.getSchoolByStateAndLocality("NSW","Horsley Park");
		for (int i = 0; i < values.size(); i++) {
			Schools placeDetail = values.get(i);
			System.out.println("placeDetail=====>"
					+ placeDetail.getSchoolName());
			findPlacesList = findPlaces(placeDetail.getSchoolName());
			if (null != findPlacesList) {
				for (int k = 0; k < findPlacesList.size(); k++) {

					Place placeDetails = findPlacesList.get(k);
					System.out.println("placeDetails.getName()===>"	+ placeDetails.getName());
					if(placeDetail.getSchoolName().equalsIgnoreCase(placeDetails.getName())){
						System.out.println("Schol name is same");
					}
				}
			}
		}
		return values;
	}
}
