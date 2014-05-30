package com.example.sqlitedatabaseapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

public class MainActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener {

	private CommentsDataSource datasource;
	private GoogleMap mMap;
	private LocationManager locationManager;
	private Location location = null;
	Context context;
	private LocationClient mLocationClient = null;
	private LocationRequest mLocationRequest = null;
	private static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS;
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
			* FASTEST_INTERVAL_IN_SECONDS;
	private TextView latituteField;
	private TextView longitudeField;

	private static final String API_KEY = "AIzaSyCdvCg0R-_rxj8biH7LPXcMHVT52xoCTHw";
	private String TAG = "Vik";
	//private String[] placeName;
	//private String[] imageUrl;
	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private static final String school = "school";
	//private AdView adView;
	//private static final String AD_UNIT_ID = "INSERT_YOUR_AD_UNIT_ID_HERE";
	//private static final String TEST_DEVICE_ID = "INSERT_YOUR_TEST_DEVICE_ID_HERE";
	//private Button btnSubmit;
	//private TextView enteredText;
	
	public void doSmall(View v)  {
	
	    	Intent intent = new Intent(this, SettingPageActivity.class);
	    	startActivity(intent);

	   }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_test_database);
		context = this;
		//Intent intent =getIntent();
		//System.out.println("intent.getStringExtra() allNearby===>"+intent.getStringExtra("allNearBy"));
		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapview)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		latituteField = (TextView) findViewById(R.id.TextView02);
		longitudeField = (TextView) findViewById(R.id.TextView01);
		mLocationClient = new LocationClient(this, this, this);
		if (servicesConnected()) {
			mLocationRequest = LocationRequest.create();
			mLocationRequest
					.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			mLocationRequest.setInterval(UPDATE_INTERVAL);
			mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		} else {
			Toast.makeText(context, "Position Unavailable", Toast.LENGTH_SHORT)
					.show();
		}
		//btnSubmit = (Button) findViewById(R.id.btnSubmit);
		//enteredText = (TextView) findViewById(R.id.searchSchool);

	}

	private String makeUrl(double latitude, double longitude, String place) {
		//StringBuilder urlString = new StringBuilder(
		//		"https://maps.googleapis.com/maps/api/place/search/json?");
		StringBuilder urlString = new StringBuilder(
				"https://maps.googleapis.com/maps/api/place/nearbysearch/xml?");

	
/*			urlString.append("location=" + latitude + "," + longitude);
			urlString.append("&radius=8000"); // take care of spaces here
			urlString.append("&types=" + school); //
			urlString.append("&sensor=true"); //
			urlString.append("&key=" + API_KEY); //
*/			
			urlString.append("location="+latitude+","+longitude);
			urlString.append("&radius=8000"); // take care of spaces here
			urlString.append("&types="+school); //
			urlString.append("&name="+place); //
			urlString.append("&sensor=true"); //
			urlString.append("&key="+API_KEY); //			
	

		return urlString.toString();
	}

	protected String getJSON(String url) {
		return getUrlContents(url);
	}

	private String getUrlContents(String theUrl) {
		StringBuilder content = new StringBuilder();

		try {
			URL url = new URL(theUrl);
			 //url = new URL("http://www.android.com/");
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 8);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}

			bufferedReader.close();
		}

		catch (Exception e) {

			System.out.println(e);
			//e.printStackTrace();

		}

		return content.toString();
	}

	public List<Place> findPlaces(double latitude, double longitude,
			String placeSpacification) {
		String urlString = makeUrl(latitude, longitude, placeSpacification);

		try {
			String json = getJSON(urlString);

			//System.out.println(json);
			JSONObject object = new JSONObject(json);
			JSONArray array = object.getJSONArray("results");

			ArrayList<Place> arrayList = new ArrayList<Place>();
			for (int i = 0; i < array.length(); i++) {
				try {
					Place place = Place
							.jsonToPontoReferencia((JSONObject) array.get(i));

					//Log.v("Places Services ", "" + place);

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

	
	private class AsyncCallWS extends
			AsyncTask<List<Place>, List<Place>, List<Place>> {
		@Override
		protected List<Place> doInBackground(List... params) {
			Log.i(TAG, "doInBackground");
			List<Place> findPlaces = calculate();
			return findPlaces;
		}


		@Override
		protected void onPostExecute(List<Place> findPlaces) {
			Log.i(TAG, "onPostExecute");
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			if(null != findPlaces){
			for (int i = 0; i < findPlaces.size(); i++) {

				Place placeDetail = findPlaces.get(i);
				addMarker(
						new LatLng(placeDetail.getLatitude(),
								placeDetail.getLongitude()),
						placeDetail.getName());
				builder.include(new LatLng(placeDetail.getLatitude(),
						placeDetail.getLongitude()));

			}
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
					builder.build(), 10);
			mMap.moveCamera(cameraUpdate);
			}else{
				Toast.makeText(context, "No nearyby schools found",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
		}

	}

	public List<Place> calculate() {
		location = getCurrentLocation();
	    datasource = new CommentsDataSource(this);
	    datasource.open();
	    final MySQLiteHelper dbHelper = new MySQLiteHelper(getBaseContext());
	    try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
				(int) (location.getLongitude() * 1E6));
		List<Address> addressess = getAddressFromLocation(point);
		String address = ConvertPointToLocation(point);
		Address addressDetails = null;
		for (int a = 0; a < addressess.size(); a++) {
			addressDetails = addressess.get(a);
		}
		System.out.println("addressess===>"+addressess.size());
		System.out.println("addressDetails===>"+addressDetails.getLocality());
		System.out.println("address===>"+address);
		String query = "ACT";
		List<Place> findPlacesList = null;
		List<Schools> values = datasource.getSchoolByState(query,addressDetails.getLocality());
		for (int i = 0; i < values.size(); i++) {
			Schools placeDetail = values.get(i);
			System.out.println("placeDetail=====>"+placeDetail.getSchoolName());
			findPlacesList = findPlaces(location.getLatitude(),
					location.getLongitude(), placeDetail.getSchoolName());
			if(null != findPlacesList){
			for (int k = 0; k < findPlacesList.size(); k++) {

				Place placeDetails = findPlacesList.get(k);

				System.out.println("placeDetails.getName()===>"+placeDetails.getName());
				if(placeDetail.getSchoolName().equalsIgnoreCase(placeDetails.getName())){
					System.out.println("Place .getName()==>"+placeDetails.getName());
					System.out.println("Schools .getSchoolName()==>"+placeDetail.getSchoolName());
					System.out.println("Place .getVicinity()==>"+placeDetails.getVicinity());
					System.out.println("Place .getLatitude()==>"+placeDetails.getLatitude());
					System.out.println("Place .getLongitude()==>"+placeDetails.getLongitude());
				}
			}
			}
		}
/*		System.out.println("getSchoolByState=====>"+values.size());
		List<Place> findPlaces = findPlaces(location.getLatitude(),
				location.getLongitude(), school);
		placeName = new String[findPlaces.size()];
		imageUrl = new String[findPlaces.size()];*/

/*		for (int j = 0; j < findPlaces.size(); j++) {

			Place placeDetail = findPlaces.get(j);
			placeDetail.getIcon();
			System.out.println(placeDetail.getName());
			placeName[j] = placeDetail.getName();

			imageUrl[j] = placeDetail.getIcon();

		}*/
		return findPlacesList;
	}

	public String ConvertPointToLocation(GeoPoint point) {
		String address = "";
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocation(
					point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6,
					1);

			if (addresses.size() > 0) {
				for (int index = 0; index < addresses.get(0)
						.getMaxAddressLineIndex(); index++)
					address += addresses.get(0).getAddressLine(index) + " ";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return address;
	}

	public List<Address> getAddressFromLocation(GeoPoint point) {
		//String address = "";
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		List<Address> addresses = null;
		try {
			addresses = geoCoder.getFromLocation(
					point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6,
					1);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return addresses;
	}
	
	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {

		if (mLocationClient.isConnected()) {
			mLocationClient.removeLocationUpdates(this);
		}
		mLocationClient.disconnect();
		super.onStop();
	}

	public void addMarker(LatLng position, String title) {
		mMap.addMarker(new MarkerOptions().position(position).title(title));
		latituteField.setText(String.valueOf(position.latitude));
		longitudeField.setText(String.valueOf(position.longitude));
	}

	/**
	 * gets the current location details
	 * 
	 * @return
	 */
	private Location getCurrentLocation() {
		Location location = mLocationClient.getLastLocation();
		if (location != null) {
			return location;
		} else {
			Toast.makeText(context, "Current Location Unavailable",
					Toast.LENGTH_SHORT).show();
			checkforGPSAndPromtOpen();
			return null;
		}
	}

	/**
	 * Check if enabled and if not send user to the GSP settings Better solution
	 * would be to display a dialog and suggesting to go to the settings
	 */
	private void checkforGPSAndPromtOpen() {
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
	}

	/**
	 * animates and takes to the location passed as paramter
	 * 
	 * @param toBeLocationLatLang
	 */
	public void takeToLocation(LatLng toBeLocationLatLang) {
		if (toBeLocationLatLang != null) {
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
					toBeLocationLatLang, 16);

			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
					toBeLocationLatLang, 10));
			mMap.animateCamera(update);
			latituteField.setText(String.valueOf(toBeLocationLatLang.latitude));
			longitudeField.setText(String
					.valueOf(toBeLocationLatLang.longitude));
	
			GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
			String address = ConvertPointToLocation(point);
			Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(context, "Position Unavailable", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * converts location to lat lang
	 * 
	 * @param location
	 * @return
	 */
	private LatLng convertLocationtoLatLang(Location location) {
		LatLng currentLatLang = new LatLng(location.getLatitude(),
				location.getLongitude());

		return currentLatLang;

	}

	/**
	 * Handle results returned to the FragmentActivity by Google Play services
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			switch (resultCode) {
			case Activity.RESULT_OK:
				break;
			}
		}
	}

	/**
	 * Check that Google Play services is available
	 * 
	 * @return
	 */
	private boolean servicesConnected() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS == resultCode) {
			Log.d("Location Updates", "Google Play services is available.");
			return true;
		} else {
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
			if (errorDialog != null) {
				errorDialog.show();
			}
			return false;
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
		location = getCurrentLocation();
		if (location != null) {
			takeToLocation(convertLocationtoLatLang(location));
		}

		AsyncCallWS task = new AsyncCallWS();
		task.execute();

	}

	@Override
	public void onDisconnected() {

	}

	/**
	 * Define the callback method that receives location updates
	 * 
	 * @param location
	 */
	@Override
	public void onLocationChanged(Location location) {
		// Report to the UI that the location was updated
		String msg = "Updated Location: "
				+ Double.toString(location.getLatitude()) + ","
				+ Double.toString(location.getLongitude());
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}