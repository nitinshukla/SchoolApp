package com.example.sqlitedatabaseapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.GestureDetectorCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
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
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

import android.view.*;
//import android.view.GestureDetector.OnDoubleTapListener;

public class MainActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener/*,GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener*/ {

	private CommentsDataSource datasource;
	private GoogleMap mMap;
	private LocationManager locationManager;
	private Location location = null;
	Context context;
	private LocationClient mLocationClient = null;
	private LocationRequest mLocationRequest = null;
	private static final String PRIMARY = "P";
	private static final String SECONDARY = "S";
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
	private String TAG = "MainActivity";
	// private String[] placeName;
	// private String[] imageUrl;
	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private static final String school = "school";
	// private AdView adView;
	// private static final String AD_UNIT_ID = "INSERT_YOUR_AD_UNIT_ID_HERE";
	// private static final String TEST_DEVICE_ID =
	// "INSERT_YOUR_TEST_DEVICE_ID_HERE";
	private Button btnSubmit;
	private TextView enteredText;
	private static final String DEBUG_TAG = "Gestures";
    /*private GestureDetectorCompat mDetector;
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, 
            float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }
    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString()); 
    }
    
    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }
    
    @Override
    public boolean onDown(MotionEvent event) { 
        Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
        return true;
    }
    
    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }
    
    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }
    
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }*/
	public void doSmall(View v) {

		Intent intent = new Intent(this, SettingPageActivity.class);
		startActivity(intent);

	}

	public void doSettings(View v) {

		Intent intent = new Intent(this, SettingPageActivity.class);
		startActivity(intent);

	}
	private static final String[] COUNTRIES = new String[] {
        "Belgium", "France", "Italy", "Germany", "Spain"
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_test_database);
		//mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        //mDetector.setOnDoubleTapListener(this);
		context = this;
		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapview)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);

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
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		enteredText = (TextView) findViewById(R.id.myautocomplete);
		/*Log.i(TAG,
				"enteredText======1111>"
						+ String.valueOf(enteredText.getText()));*/
		TextView settings = (TextView) findViewById(R.id.Settings);
		TextView redoSearch = (TextView) findViewById(R.id.redoSearch);
		if(null != redoSearch){
			redoSearch.setVisibility(View.GONE);
		}

		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doSettings(v);
			}
		});

		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enteredText = (TextView) findViewById(R.id.myautocomplete);
				Log.i(TAG,
						"enteredText======2222>"
								+ String.valueOf(enteredText.getText()));
				doSearchedText(v);
			}
		});
	}

	public void doSearchedText(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("doSearchedText", "doSearchedText");
		intent.putExtra("enteredText", String.valueOf(enteredText.getText()));
		startActivity(intent);
	}

	private String makeUrl(double latitude, double longitude, String place) {
		// StringBuilder urlString = new StringBuilder(
		// "https://maps.googleapis.com/maps/api/place/search/json?");
		StringBuilder urlString = new StringBuilder(
				"https://maps.googleapis.com/maps/api/place/nearbysearch/json?");

		/*
		 * urlString.append("location=" + latitude + "," + longitude);
		 * urlString.append("&radius=8000"); // take care of spaces here
		 * urlString.append("&types=" + school); //
		 * urlString.append("&sensor=true"); // urlString.append("&key=" +
		 * API_KEY); //
		 */
		urlString.append("location=" + latitude + "," + longitude);
		urlString.append("&radius=8000"); // take care of spaces here
		urlString.append("&types=" + school); //
		// urlString.append("&name="+place); //
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
			// url = new URL("http://www.android.com/");
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

	public List<Place> findPlaces(double latitude, double longitude,
			String placeSpacification) {
		String urlString = makeUrl(latitude, longitude, placeSpacification);

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

	private class AsyncCallWS extends
			AsyncTask<List<Schools>, List<Schools>, List<Schools>> {
		@Override
		protected List<Schools> doInBackground(List... params) {
			Log.i(TAG, "doInBackground");
			
			List<Schools> findPlaces = calculate();
			if(null != findPlaces){
				Log.i(TAG,
					"doInBackground findPlaces size is =======================================>"
							+ findPlaces.size());
			}
			return findPlaces;
		}

		@Override
		protected void onPostExecute(List<Schools> findPlaces) {
			Log.i(TAG, "onPostExecute");
			int counter = 0;
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			//Log.i(TAG, "findPlaces size==>" + findPlaces.size());

			if (null != findPlaces) {
				for (int i = 0; i < findPlaces.size(); i++) {
					Log.i(TAG, "findPlaces size==>" + i);
					Schools placeDetail = findPlaces.get(i);
					if (null != placeDetail.getLatitude()
							&& null != placeDetail.getLongitude()) {
						addMarker(
								new LatLng(Double.parseDouble(placeDetail
										.getLatitude()),
										Double.parseDouble(placeDetail
												.getLongitude())),
								placeDetail	,placeDetail.getId()	);
	
							counter++;
						builder.include(new LatLng(Double
								.parseDouble(placeDetail.getLatitude()), Double
								.parseDouble(placeDetail.getLongitude())));
			

					}
				}
				CameraUpdate cameraUpdate;
				if (counter > 0){
					 cameraUpdate = CameraUpdateFactory
						.newLatLngBounds(builder.build(), 45);
				}else{
					 cameraUpdate = CameraUpdateFactory.newLatLngZoom(
							(new LatLng(location.getLatitude(), location
									.getLongitude())), 10);
						Toast.makeText(context, "No Nearby School Found",
								Toast.LENGTH_SHORT).show();
					 
				}

				mMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.mapview)).getMap();
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				mMap.setMyLocationEnabled(true);
				mMap.moveCamera(cameraUpdate);
				mMap.animateCamera(cameraUpdate);
			} else {
				if(null != location){
					CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
							(new LatLng(location.getLatitude(), location
									.getLongitude())), 10);
					mMap = ((MapFragment) getFragmentManager().findFragmentById(
							R.id.mapview)).getMap();
					mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					mMap.setMyLocationEnabled(true);
					mMap.moveCamera(cameraUpdate);
					mMap.animateCamera(cameraUpdate);
					Toast.makeText(context, "No Nearby School Found",
							Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(context, "No Nearby School Found",
							Toast.LENGTH_SHORT).show();
				}
			}

		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
		}

	}

	public List<Schools> calculate() {
		location = getCurrentLocation();
		if(null == location){
			return null;
		}

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
		//String address = ConvertPointToLocation(point);
		Address addressDetails = null;
		if(null != addressess){
			for (int a = 0; a < addressess.size(); a++) {
				addressDetails = addressess.get(a);
			}
		}
		

		enteredText = (TextView) findViewById(R.id.myautocomplete);
		Intent intent = getIntent();
		Log.i(TAG,
				"intent.getStringExtra() enteredText===>"
						+ intent.getStringExtra("enteredText"));
		Log.i(TAG,
				"intent.getStringExtra() allNearBy===>"
						+ intent.getStringExtra("allNearBy"));
		Log.i(TAG,
				"enteredText======3333>"
						+ String.valueOf(enteredText.getText()));
		//Log.i(TAG, "addressess===>" + addressess.size());
		//Log.i(TAG, "addressDetails===>" + addressDetails.getLocality());
		//Log.i(TAG, "address===>" + address);
		String query = null;
		if(null != addressDetails){
			query = getState(addressDetails.getAdminArea());
		}
		// Location add = new Location("");
		// add.setLatitude(addressDetails.getLatitude());
		// add.setLongitude(addressDetails.getLongitude());
		List<Schools> values = null;
		// List<Schools> fnlLst = new ArrayList<Schools>();

		// Log.i(TAG, "values size 11 ====>" + values.size());
		/*
		 * if (null != intent.getStringExtra("allNearBy") &&
		 * !"".equalsIgnoreCase(intent.getStringExtra("allNearBy"))) { // float
		 * distance = location.distanceTo(add);
		 * System.out.println("distance===>" + distance); values =
		 * datasource.getSchoolByState(query); }
		 */
		// List<Object> toRemove = new ArrayList<Object>();
		/*if (null != intent.getStringExtra("allNearBy")
				&& !"".equalsIgnoreCase(intent.getStringExtra("allNearBy"))) {*/
			if (null != query) {
				if (null != intent.getStringExtra("allNearBy")
						&& !"".equalsIgnoreCase(intent.getStringExtra("allNearBy"))) {
					values = datasource.getSchoolByState(query);
				}else if (null != intent.getStringExtra("allNearByPrimary")
						&& !"".equalsIgnoreCase(intent
								.getStringExtra("allNearByPrimary"))) {
					values = datasource.getSchoolByState(query,PRIMARY);
				}else if (null != intent.getStringExtra("allNearBySecondary")
						&& !"".equalsIgnoreCase(intent
								.getStringExtra("allNearBySecondary"))) {
					values = datasource.getSchoolByState(query,SECONDARY);
				}else if (null != intent.getStringExtra("enteredText")
						&& !("".equalsIgnoreCase(intent.getStringExtra("enteredText")))) {
					query = getState(intent.getStringExtra("enteredText"));
					values = datasource.getSchoolByEnteredText(intent
							.getStringExtra("enteredText"));
				}
			}

			if (null != values) {

				// for (int i = 0; i < values.size(); i++) {
				Iterator<Schools> i = values.iterator();
				while (i.hasNext()) {
					// Schools placeDetail = values.get(i);
					Schools placeDetail = i.next();
					if (null != placeDetail.getLatitude()) {
						Location plc = new Location("");
						plc.setLatitude(Double.parseDouble(placeDetail
								.getLatitude()));
						plc.setLongitude(Double.parseDouble(placeDetail
								.getLongitude()));
						float distance = location.distanceTo(plc);
						if((null != intent.getStringExtra("allNearBy")
								&& !"".equalsIgnoreCase(intent.getStringExtra("allNearBy"))) || (null != intent.getStringExtra("allNearByPrimary")
										&& !"".equalsIgnoreCase(intent.getStringExtra("allNearByPrimary")))){
								if (distance > 5000) {
									// values.remove(i);
									Log.i(TAG,
											"getSchoolName 11 ====>"
													+ placeDetail.getSchoolName());
									Log.i(TAG, "distance 11 ====>" + distance);
									i.remove();
								}
						}
					}/*
					 * else{ //values.remove(i); toRemove.remove(i); }
					 */
				}
			}
			/*	} else if (null != intent.getStringExtra("allNearByPrimary")
				&& !"".equalsIgnoreCase(intent
						.getStringExtra("allNearByPrimary"))) {
			if (null != query) {
				values = datasource.getSchoolByState(query,PRIMARY);
			}
			values = datasource.getSchoolByState(query, "P");
		} else if (null != intent.getStringExtra("allNearBySecondary")
				&& !"".equalsIgnoreCase(intent
						.getStringExtra("allNearBySecondary"))) {
			if (null != query) {
				values = datasource.getSchoolByState(query,SECONDARY);
			}
			//values = datasource.getSchoolByState(query, "S");
		} else if (null != intent.getStringExtra("enteredText")
				&& !("".equalsIgnoreCase(intent.getStringExtra("enteredText")))) {
			query = getState(intent.getStringExtra("enteredText"));
			values = datasource.getSchoolByEnteredText(intent
					.getStringExtra("enteredText"));
		}*/
		// values.removeAll(toRemove);
		//Log.i(TAG, "values size 22 after remove ====>" + values.size());
		// String query = "ACT";

		// List<Place> findPlacesList = null;

		/*
		 * if (null != query) { values = datasource.getSchoolByState(query); }
		 * else if (null != intent.getStringExtra("enteredText") &&
		 * !("".equalsIgnoreCase(intent.getStringExtra("enteredText")))) {
		 * values = datasource.getSchoolByEnteredText(intent
		 * .getStringExtra("enteredText")); }
		 */
		// for (int i = 0; i < values.size(); i++) {
		// Schools placeDetail = values.get(i);
		// System.out.println("placeDetail=====>"+placeDetail.getSchoolName());
		// findPlacesList = findPlaces(location.getLatitude(),
		// location.getLongitude(), placeDetail.getSchoolName());
		// if(null != findPlacesList){
		// for (int k = 0; k < findPlacesList.size(); k++) {
		//
		// Place placeDetails = findPlacesList.get(k);
		//
		// System.out.println("placeDetails.getName()===>"+placeDetails.getName());
		// if(placeDetail.getSchoolName().equalsIgnoreCase(placeDetails.getName())){
		// System.out.println("Place .getName()==>"+placeDetails.getName());
		// System.out.println("Schools .getSchoolName()==>"+placeDetail.getSchoolName());
		// System.out.println("Place .getVicinity()==>"+placeDetails.getVicinity());
		// System.out.println("Place .getLatitude()==>"+placeDetails.getLatitude());
		// System.out.println("Place .getLongitude()==>"+placeDetails.getLongitude());
		// }
		// }
		// }
		// }
		/*
		 * System.out.println("getSchoolByState=====>"+values.size());
		 * List<Place> findPlaces = findPlaces(location.getLatitude(),
		 * location.getLongitude(), school); placeName = new
		 * String[findPlaces.size()]; imageUrl = new String[findPlaces.size()];
		 */

		/*
		 * for (int j = 0; j < findPlaces.size(); j++) {
		 * 
		 * Place placeDetail = findPlaces.get(j); placeDetail.getIcon();
		 * System.out.println(placeDetail.getName()); placeName[j] =
		 * placeDetail.getName();
		 * 
		 * imageUrl[j] = placeDetail.getIcon();
		 * 
		 * }
		 */
		return values;
	}

	private String getState(String adminArea) {
		// TODO Auto-generated method stub
		if (adminArea.equalsIgnoreCase("New South Wales")) {
			return "NSW";
		} else if (adminArea.equalsIgnoreCase("Australian Capital territory")) {
			return "ACT";
		} else if (adminArea.equalsIgnoreCase("Queensland")) {
			return "QLD";
		} else if (adminArea.equalsIgnoreCase("South Australia")) {
			return "SA";
		} else if (adminArea.equalsIgnoreCase("Northern Territory")) {
			return "NT";
		} else if (adminArea.equalsIgnoreCase("Western Australia")) {
			return "WA";
		} else if (adminArea.equalsIgnoreCase("Tasmania")) {
			return "TAS";
		} else if (adminArea.equalsIgnoreCase("Victoria")) {
			return "VIC";
		}else if (adminArea.equalsIgnoreCase("Ohio")) {
			return "NSW";
		}else if (adminArea.equalsIgnoreCase("Maharashtra")) {
			return "NSW";
		}else if (adminArea.equalsIgnoreCase("Jharkhand")) {
			return "NSW";
		}
		return null;
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
			/*Toast.makeText(context, "Timed out waiting for response from Google Play", Toast.LENGTH_SHORT)
			.show();*/
		}

		return address;
	}

	public List<Address> getAddressFromLocation(GeoPoint point) {
		// String address = "";
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		List<Address> addresses = null;
		try {
			addresses = geoCoder.getFromLocation(point.getLatitudeE6() / 1E6,
					point.getLongitudeE6() / 1E6, 1);

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
		EasyTracker.getInstance(this).activityStart(this);
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
		EasyTracker.getInstance(this).activityStop(this);
	}

	public void addMarker(LatLng position,  Schools schoolDetail,long id) {
/*		mMap.addMarker(new MarkerOptions()
				.position(position)
				.title(title)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));*/
		//.snippet("Population: 4,137,400")
		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
        	 
            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
            	//EventInfo eventInfo = mMap.get(arg0);
/*           	    String title = arg0.getTitle();
           	    View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
           	    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
            	             if (title != null) {
            	                 SpannableString titleText = new SpannableString(title);
            	                 titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
            	                 tvLat.setText(titleText);
            	             } else {
            	            	 tvLat.setText("");
            	             }
            	            
            	    return v;*/
            	
            	return null;

            }
 
            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {
 
                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
 
                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();
 
                // Getting reference to the TextView to set latitude
                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
 
                // Getting reference to the TextView to set longitude
                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
 
                // Setting the latitude
                //tvLat.setText("Latitude:" + latLng.latitude);
                SpannableString titleText = new SpannableString(arg0.getTitle());
                titleText.setSpan(new ForegroundColorSpan(Color.rgb(184,134,11)), 0, titleText.length(), 0);
                tvLat.setText(titleText);
                 SpannableString snippetText = new SpannableString(arg0.getSnippet());
                snippetText.setSpan(new ForegroundColorSpan(Color.rgb(178,34,34)), 0, snippetText.length(), 0);
                tvLng.setText(snippetText);
 
                // Returning the view containing InfoWindow contents
                return v;
 
            }
        });

	/*	mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			 
            @Override
            public boolean  onMarkerClick(Marker arg0) {
                // Clears any existing markers from the GoogleMap
            	//mMap.clear();
 
                // Creating an instance of MarkerOptions to set position
                MarkerOptions markerOptions = new MarkerOptions();
 
                // Setting position on the MarkerOptions
                markerOptions.position(arg0.getPosition());
                
    System.out.println("arg0.getId()====>"+arg0.getId());
   // System.out.println("id====>"+id);
    //schoolDetail.getEmail();
    //schoolDetail.getId()
                //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_10));
                markerOptions.title(arg0.getTitle());
                markerOptions.snippet(arg0.getSnippet());
                
                // Animating to the currently touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                mMap.addMarker(markerOptions).showInfoWindow();;
                
                // Adding marker on the GoogleMap
                Marker marker = mMap.addMarker(markerOptions);
 
                // Showing InfoWindow on the GoogleMap
                marker.showInfoWindow();
                return true;
            }
        });*/
		
		/*mMap.setOnMapClickListener(new OnMapClickListener() {
			 
            @Override
            public void onMapClick(LatLng arg0) {
                // Clears any existing markers from the GoogleMap
            	mMap.clear();
 
                // Creating an instance of MarkerOptions to set position
                MarkerOptions markerOptions = new MarkerOptions();
 
                // Setting position on the MarkerOptions
                markerOptions.position(arg0);
                
                	markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_));
                
                // Animating to the currently touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
 
                // Adding marker on the GoogleMap
                Marker marker = mMap.addMarker(markerOptions);
 
                // Showing InfoWindow on the GoogleMap
                marker.showInfoWindow();
 
            }
        });*/
		
		/*mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
		    @Override
		    public void onCameraChange(CameraPosition cameraPosition) {
		        // Make a web call for the locations
		       System.out.println("Inside onCameraChange");
		       MarkerOptions markerOptions = new MarkerOptions();

               markerOptions.title("Pankaj");
               markerOptions.snippet("Pankaj snippet");
               markerOptions.position(new LatLng(151.0480,-33.7770));
               markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.sprites));
               // Animating to the currently touched position
               mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(151.0480,-33.7770)));
               
               mMap.addMarker(markerOptions).showInfoWindow();;
               
               // Adding marker on the GoogleMap
               Marker marker = mMap.addMarker(markerOptions);

               // Showing InfoWindow on the GoogleMap
               marker.showInfoWindow();
               //return true;
		}
		 });*/
		
		mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
		    public void onMapLoaded() {
	/*	        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
		            public void onSnapshotReady(Bitmap bitmap) {
		                // Write image to disk
		            }
		        });*/
		    	System.out.println("inside onMapLoaded ");
		    	System.out.println("Inside onCameraChange");
			       /*MarkerOptions markerOptions = new MarkerOptions();

	               markerOptions.title("Pankaj");
	               markerOptions.snippet("Pankaj snippet");
	               markerOptions.position(new LatLng(-33.7770,151.0480));
	               markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.sprites));
	               // Animating to the currently touched position
	               mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(-33.7770,151.0480)));
	               
	               mMap.addMarker(markerOptions).showInfoWindow();;
	               
	               // Adding marker on the GoogleMap
	               Marker marker = mMap.addMarker(markerOptions);

	               // Showing InfoWindow on the GoogleMap
	               marker.showInfoWindow();*/
	               TextView redoSearch = (TextView) findViewById(R.id.redoSearch);
	               if(null != redoSearch){
	       			redoSearch.setVisibility(View.VISIBLE);
	       		}
	              /*View v = getLayoutInflater().inflate(R.layout.redo_search_button_layout, null);
	              
	                // Getting the position from the marker
	               // LatLng latLng = arg0.getPosition();
	 
	                // Getting reference to the TextView to set latitude
	                TextView tvLat = (TextView) v.findViewById(R.id.redo);
	 
	                // Getting reference to the TextView to set longitude
	                TextView tvLng = (TextView) v.findViewById(R.id.redo1);
	 
	                // Setting the latitude
	                //tvLat.setText("Latitude:" + latLng.latitude);
	                SpannableString titleText = new SpannableString("n");
	                titleText.setSpan(new ForegroundColorSpan(Color.rgb(184,134,11)), 0, titleText.length(), 0);
	                tvLat.setText(titleText);
	                 SpannableString snippetText = new SpannableString(arg0.getSnippet());
	                snippetText.setSpan(new ForegroundColorSpan(Color.rgb(178,34,34)), 0, snippetText.length(), 0);
	                tvLng.setText(snippetText);
	 
	                // Returning the view containing InfoWindow contents
	                return v;*/
		    }
		});
		if(1 < schoolDetail.getId() && schoolDetail.getId() < 1000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			//.snippet(schoolDetail.getSectorName()+"\n"+"Rank:"+schoolDetail.getId()+"\n"+schoolDetail.getStreetAddress()+" "+schoolDetail.getSuburb()+" "+schoolDetail.getPostcode()+" "+schoolDetail.getState()+"\n"+schoolDetail.getEmail()+" "+schoolDetail.getPhone()+" "+schoolDetail.getFax())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_10)));
		}else if(1001 < schoolDetail.getId() && schoolDetail.getId() < 2000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_9)));
		}else if(2001 < schoolDetail.getId() && schoolDetail.getId() < 3000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_8)));
		}else if(3001 < schoolDetail.getId() && schoolDetail.getId() < 4000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_7)));
		}else if(4001 < schoolDetail.getId() && schoolDetail.getId() < 5000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_6)));
		}else if(5001 < schoolDetail.getId() && schoolDetail.getId() < 6000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_5)));
		}else if(6001 < schoolDetail.getId() && schoolDetail.getId() < 7000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_4)));
		}else if(7001 < schoolDetail.getId() && schoolDetail.getId() < 8000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_3)));
		}else if(8001 < schoolDetail.getId() && schoolDetail.getId() < 9000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_2)));
		}else if(9001 < schoolDetail.getId() && schoolDetail.getId() < 10000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_1)));
		}else if(schoolDetail.getId() > 10000){
			mMap.addMarker(new MarkerOptions()
			.position(position)
			.title(schoolDetail.getSchoolName()+" "+schoolDetail.getPrimarySecondaryName())
			.snippet(schoolDetail.getSnippet())
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.number_0)));
		}
		
		Log.i(TAG, "findPlaces title==>" + schoolDetail.getSchoolName());
		Log.i(TAG, "findPlaces title==>" + schoolDetail.getId());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
				(new LatLng(location.getLatitude(), location.getLongitude())),
				5);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.mapview)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		mMap.moveCamera(cameraUpdate);
		mMap.animateCamera(cameraUpdate);
		/*
		 * (new Thread(new Runnable() {
		 * 
		 * @Override public void run() { try { URL myUrl = new URL(
		 * "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=2|FF0000|000000"
		 * ); java.net.URLConnection con = url.openConnection(); con.connect();
		 * java.io.BufferedReader in = new java.io.BufferedReader(new
		 * java.io.InputStreamReader(con.getInputStream())); String line; for (;
		 * (line = in.readLine()) != null; ) { Log.d("MainActivity",
		 * "read from web "+line); } Bitmap bmp =
		 * BitmapFactory.decodeStream(myUrl.openConnection().getInputStream());
		 * 
		 * mMap.addMarker(new
		 * MarkerOptions().position(position).title(title).icon
		 * (BitmapDescriptorFactory.fromBitmap(bmp))); } catch
		 * (MalformedURLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 * 
		 * } })).start();
		 */
		// ArrayList<E>[] myTaskParams = { position, title };
		/*
		 * URL myUrl = null; try { myUrl = new URL(
		 * "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + id
		 * + "|FF0000|000000"); } catch (MalformedURLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } DownloadImageTask
		 * task = new DownloadImageTask(position, title, id);//
		 * .execute(myUrl.toString()); task.execute(myUrl.toString());
		 */

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
			//Toast.makeText(context, "Current Location Unavailable",
			//		Toast.LENGTH_SHORT).show();
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
			// latituteField.setText(String.valueOf(toBeLocationLatLang.latitude));
			// longitudeField.setText(String.valueOf(toBeLocationLatLang.longitude));

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

		if(null == location){
			Toast.makeText(this, "Location is null", Toast.LENGTH_SHORT).show();
		}else {
			GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
			List<Address> addressess = getAddressFromLocation(point);
			//String address = ConvertPointToLocation(point);
			Address addressDetails = null;
			if(null != addressess){
				for (int a = 0; a < addressess.size(); a++) {
					addressDetails = addressess.get(a);
				}
			}

			if(null != addressDetails){
				datasource = new CommentsDataSource(this);
				datasource.open();
			String	state = getState(addressDetails.getAdminArea());
			List<String> listSuburb = datasource.getSuburbByState(state);
			 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                 android.R.layout.simple_dropdown_item_1line, listSuburb);
	         AutoCompleteTextView textView = (AutoCompleteTextView)
	                 findViewById(R.id.myautocomplete);
	         textView.setAdapter(adapter);
			}
		
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

	/*
	 * private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	 * 
	 * private ProgressDialog mDialog; // private ImageView bmImage; private
	 * LatLng position; private String title = ""; private long id;
	 * 
	 * public DownloadImageTask(LatLng position, String title, long id) {
	 * this.position = position; this.title = title; this.id = id; }
	 * 
	 * protected void onPreExecute() {
	 * 
	 * mDialog = ProgressDialog.show(MainActivity.this, "Please wait...",
	 * "Retrieving data ...", true); }
	 * 
	 * protected Bitmap doInBackground(String... urls) { String urldisplay =
	 * urls[0]; Bitmap mIcon11 = null; try { InputStream in = new
	 * java.net.URL(urldisplay).openStream(); mIcon11 =
	 * BitmapFactory.decodeStream(in); } catch (Exception e) { Log.e("Error",
	 * "image download error"); Log.e("Error", e.getMessage());
	 * e.printStackTrace(); } return mIcon11; }
	 * 
	 * protected void onPostExecute(Bitmap result) { // set image of your
	 * imageview // bmImage.setImageBitmap(result); // close mMap.addMarker(new
	 * MarkerOptions().position(position).title(title)
	 * .icon(BitmapDescriptorFactory.fromBitmap(result))); mDialog.dismiss(); }
	 * }
	 */
}