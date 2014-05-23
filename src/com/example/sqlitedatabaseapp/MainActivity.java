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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends Activity implements
		ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

	private GoogleMap mMap;
	private LocationManager locationManager;
	private Location location = null;
	//private Button btnStoreCarLocation = null;
	private Button findCar = null;
	Context context;
	private LatLng carPosition = null;
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
	  private static final String LOG_TAG = "ExampleApp";

	  private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	  private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	  private static final String OUT_JSON = "/json";

	  private static final String API_KEY = "AIzaSyCdvCg0R-_rxj8biH7LPXcMHVT52xoCTHw";
	  private String TAG ="Vik";
	  private String[] placeName;
	    private String[] imageUrl;
	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private static final String school = "school";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.mycarlocator);
		setContentView(R.layout.location_test_database);
		context = this;
		//btnStoreCarLocation = (Button) findViewById(R.id.storeCarLocation);
		//findCar = (Button) findViewById(R.id.findmycar);

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
/*		location = mLocationClient.getLastLocation();
		location = getCurrentLocation();
		if (location != null) {
			carPosition = convertLocationtoLatLang(location);
			addMarker(carPosition, "CAR");
		}*/

		// currentlatlang = new LatLng(29.528522, -98.57837);
		//AsyncCallWS task = new AsyncCallWS();
        //task.execute(); 
        

/*		btnStoreCarLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				location = getCurrentLocation();
				if (location != null) {
					carPosition = convertLocationtoLatLang(location);
					addMarker(carPosition, "Meri Position");
					LatLng fromPosition = new LatLng(33.3000,111.8333);
					addMarker(fromPosition, "Chandler"); //33.3000° N, 111.8333° 
					//carPosition.showInfoWindow();
					mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carPosition, 10));
					mMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000, null);  
				    latituteField.setText(String.valueOf(carPosition.latitude));
				    longitudeField.setText(String.valueOf(carPosition.longitude));
				    GeoPoint point = new GeoPoint(
				            (int) (location.getLatitude() * 1E6), 
				            (int) (location.getLongitude() * 1E6));				    
				      String address = ConvertPointToLocation(point);
				      Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT).show();

				}
				
			}
		});*/

/*		findCar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				takeToLocation(carPosition);
			}
		});*/

	}
	
	private String makeUrl(double latitude, double longitude,String place) {
        StringBuilder urlString = new StringBuilder("https://maps.googleapis.com/maps/api/place/search/json?");

       if (place.equals("")) {
    	   //StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
    	   urlString.append("location=" + latitude + "," + longitude);
           //sb.append("location=" + -33.7770 + "," + 151.0480);
    	   urlString.append("&radius=8000"); // take care of spaces here 
    	   urlString.append("&types="+school); //
    	   urlString.append("&sensor=true"); //
    	   urlString.append("&key="+API_KEY); //
       } else {
    	   //StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
    	   urlString.append("location=" + latitude + "," + longitude);
    	   urlString.append("&radius=5000"); // take care of spaces here 
    	   urlString.append("&types="+school); //
    	   urlString.append("&sensor=true"); //
    	   urlString.append("&key="+API_KEY); //
       }


       return urlString.toString();
   }
	protected String getJSON(String url) {
        return getUrlContents(url);
    }
	
	private String getUrlContents(String theUrl) 
    {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 8);
            String line;
            while ((line = bufferedReader.readLine()) != null) 
            {
                content.append(line + "\n");
            }

            bufferedReader.close();
        }

        catch (Exception e)
        {

            e.printStackTrace();

        }

        return content.toString();
    }
	
	public List<Place> findPlaces(double latitude, double longitude,String placeSpacification) 
    {
		String urlString = makeUrl(latitude, longitude,placeSpacification);
       
        try {
            String json = getJSON(urlString);

            System.out.println(json);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("results");


            ArrayList<Place> arrayList = new ArrayList<Place>();
            for (int i = 0; i < array.length(); i++) {
                try {
                    Place place = Place.jsonToPontoReferencia((JSONObject) array.get(i));

                    Log.v("Places Services ", ""+place);


                    arrayList.add(place);
                } catch (Exception e) {
                }
            }
            return arrayList;
        } catch (JSONException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
	
	private class AsyncCallWS extends AsyncTask<List<Place>, List<Place>, List<Place>> {
        @Override
        protected List<Place> doInBackground(List... params) {
            Log.i(TAG, "doInBackground");
            List<Place> findPlaces =calculate();
            //addLocation(findPlaces);
            return findPlaces;
        }

  /*      private void addLocation(List<Place> findPlaces) {
			// TODO Auto-generated method stub
        	for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                addMarker(new LatLng(placeDetail.getLatitude(), placeDetail.getLongitude()), placeDetail.getName());

          }
        	
		}*/

		@Override
        protected void onPostExecute(List<Place> findPlaces) {
            Log.i(TAG, "onPostExecute");
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
        	for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                addMarker(new LatLng(placeDetail.getLatitude(), placeDetail.getLongitude()), placeDetail.getName());
                builder.include(new LatLng(placeDetail.getLatitude(), placeDetail.getLongitude()));

          }
        	CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), 10);
        	mMap.moveCamera(cameraUpdate);
   
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }



    }
	
	
	public List<Place> calculate() 
    {
		location = getCurrentLocation();
		List<Place> findPlaces = findPlaces(location.getLatitude(),location.getLongitude(),school);
		placeName = new String[findPlaces.size()];
        imageUrl = new String[findPlaces.size()];

      for (int i = 0; i < findPlaces.size(); i++) {

          Place placeDetail = findPlaces.get(i);
          placeDetail.getIcon();
          //Marker pos_Marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(placeDetail.getLatitude(),placeDetail.getLongitude())).title(placeDetail.getName()).draggable(false));
          //pos_Marker.showInfoWindow();
        System.out.println(  placeDetail.getName());
        placeName[i] =placeDetail.getName();

        imageUrl[i] =placeDetail.getIcon();

    }
      return findPlaces;

		//HttpURLConnection conn = null;
		//StringBuilder jsonResults = new StringBuilder();
		//String lat = "";
		//ArrayList<Place> resultList = null;
		// StringBuilder content = new StringBuilder();
//		try {
//			StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//            sb.append("location=" + -33.7770 + "," + 151.0480);
//            sb.append("&radius=5000"); // take care of spaces here 
//            sb.append("&types="+school); //
//            sb.append("&sensor=true"); //
//            sb.append("&key="+API_KEY); //
//
//	        URL url = new URL(sb.toString());
//	        conn = (HttpURLConnection) url.openConnection();
//	        //InputStreamReader in = new InputStreamReader(conn.getInputStream());
//	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()), 8);
//	        String line;
//	       
//	        // Load the results into a StringBuilder
//	        /*int read;
//	        char[] buff = new char[1024];
//	        while ((read = in.read(buff)) != -1) {
//	            jsonResults.append(buff, 0, read);
//	            System.out.println("jsonResults==>"+jsonResults);
//	        }*/
//	        while ((line = bufferedReader.readLine()) != null) 
//            {
//                content.append(line + "\n");
//            }
//
//            bufferedReader.close();
//	    } catch (MalformedURLException e) {
/*	        Log.e(LOG_TAG, "Error processing Places API URL", e);
	        //return resultList;
	    } catch (IOException e) {
	        Log.e(LOG_TAG, "Error connecting to Places API", e);
	        // return resultList;
	    } finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }*/
		//return content.toString();
    }
	public String ConvertPointToLocation(GeoPoint point) {   
	    String address = "";
	    Geocoder geoCoder = new Geocoder(
	        getBaseContext(), Locale.getDefault());
	    try {
	      List<Address> addresses = geoCoder.getFromLocation(
	        point.getLatitudeE6()  / 1E6, 
	        point.getLongitudeE6() / 1E6, 1);
	 
	      if (addresses.size() > 0) {
	        for (int index = 0; 
		index < addresses.get(0).getMaxAddressLineIndex(); index++)
	          address += addresses.get(0).getAddressLine(index) + " ";
	      }
	    }
	    catch (IOException e) {        
	      e.printStackTrace();
	    }   
	    
	    return address;
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
		//pos_Marker.showInfoWindow();
		//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 10));
		//mMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000, null);
	    latituteField.setText(String.valueOf(position.latitude));
	    longitudeField.setText(String.valueOf(position.longitude));
	    //Marker pos_Marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(33.3000,-111.8333)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).title("Chandler").draggable(false));
	    //Marker pos_Marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(33.3000,-111.8333)).title("Chandler").draggable(false));
		//map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
		//pos_Marker.showInfoWindow();
		/*GeoPoint point = new GeoPoint(
	            (int) (location.getLatitude() * 1E6), 
	            (int) (location.getLongitude() * 1E6));				    
	      String address = ConvertPointToLocation(point);
	      Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT).show();*/
		
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

			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toBeLocationLatLang, 10));
			mMap.animateCamera(update);
		    latituteField.setText(String.valueOf(toBeLocationLatLang.latitude));
		    longitudeField.setText(String.valueOf(toBeLocationLatLang.longitude));
		    //LatLng fromPosition = new LatLng(33.3000,111.8333);
			//addMarker(fromPosition, "Chandler"); //33.3000° N, 111.8333° 
			//Marker pos_Marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(33.3000,-111.8333)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).title("Chandler").draggable(false));
			//Marker pos_Marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(33.3000,-111.8333)).title("Chandler").draggable(false));
			//map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
			//pos_Marker.showInfoWindow();
			GeoPoint point = new GeoPoint(
		            (int) (location.getLatitude() * 1E6), 
		            (int) (location.getLongitude() * 1E6));				    
		      String address = ConvertPointToLocation(point);
		      Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT).show();
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
//		calculate();
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

/*package com.example.sqlitedatabaseapp;

import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationSource, android.location.LocationListener,GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	private LocationManager locationManager;
	private String provider;
	private TextView output;
	private String bestProvider;
	private Location location;
	private LocationClient mLocationClient;
	LocationRequest mLocationRequest;
	//The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	//The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	private static final int MILLISECONDS_PER_SECOND = 1000;

	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	private static final long UPDATE_INTERVAL =
	          MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;

	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	private static final long FASTEST_INTERVAL =
	          MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleMap map = ((MapFragment) getFragmentManager()
        .findFragmentById(R.id.map)).getMap();

//LatLng sydney = new LatLng(-33.867, 151.206);

map.setMyLocationEnabled(true);
map.setLocationSource(this);
double latitude = 0;
double langitude = 0;

locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//Get the location manager
	boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

// getting network status
	boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	if (!isGPSEnabled && !isNetworkEnabled) {
        // no network provider is enabled
    } else {
        //this.canGetLocation = true;
        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            //Log.d("Network", "Network Enabled");
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    langitude = location.getLongitude();
                }
            }
        }
        if (isGPSEnabled) {
            if (location == null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
               // Log.d("GPS", "GPS Enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        langitude = location.getLongitude();
                    }else{
                    	 int isGooglePlayServiceAvilable = GooglePlayServicesUtil
                                 .isGooglePlayServicesAvailable(getApplicationContext());
                         if (isGooglePlayServiceAvilable == ConnectionResult.SUCCESS) {
                             *//** thorws shitty expectiosn **//*
                        	   mLocationRequest = LocationRequest.create();
                        	    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        	    mLocationRequest.setInterval(UPDATE_INTERVAL);
                        	    mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
                        	 mLocationClient = new LocationClient(this, this, this);
                        	 onConnected(savedInstanceState);
                             mLocationClient.connect();
                             //mLocationClient.requestLocationUpdates(mLocationRequest, this);
                             location = mLocationClient.getLastLocation();
                         }
                    	//mLocationClient = new LocationClient(this, this, this);
                    	//mLocationClient.connect();
                    	//location = mLocationClient.getLastLocation();
                    }
                }
            }     
        }
    }
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManager != null) {
			Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                langitude = location.getLongitude();
            }
        }
		// List all providers:
				List<String> providers = locationManager.getAllProviders();
				for (String provider : providers) {
					//printProvider(provider);
				}

				Criteria criteria = new Criteria();
				bestProvider = locationManager.getBestProvider(criteria, false);
				//output.append("\n\nBEST Provider:\n");
				//printProvider(bestProvider);
				//output.append("\n\nLocations (starting with last known):");
				Location location = locationManager.getLastKnownLocation(bestProvider);
				//printLocation(location);
boolean statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
System.out.println("statusOfGPS===>"+statusOfGPS);
if (!statusOfGPS) {
	   Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	   startActivity(intent);
	 } 
//Criteria criteria = new Criteria();
//provider = locationManager.getBestProvider(criteria, false);
//Location location = locationManager.getLastKnownLocation(provider);

// Initialize the location fields
if (location != null) {
	System.out.println("Provider " + provider + " has been selected.");
	 latitude = location.getLatitude();
     langitude = location.getLongitude();
} else {
	Toast.makeText(getApplicationContext(),
            "Sorry! unable to create maps", Toast.LENGTH_SHORT)
            .show();
}
if(locationManager!=null){
    double latitude = locationManager.getLatitude();
    double langitude = locationManager.getLongitude();
}
Marker pos_Marker =  map.addMarker(new MarkerOptions().position(new LatLng(latitude,langitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)).title("Starting Location").draggable(false));
//map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
pos_Marker.showInfoWindow();
map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,langitude), 10));
map.animateCamera(CameraUpdateFactory.zoomTo(15),2000, null);  


  map.addMarker(new MarkerOptions()
        .title("Sydney")
        .snippet("The most populous city in Australia.")
        .position(sydney));
    }
}
    
    
     * Called when the Activity becomes visible.
     
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
    }
    
     * Called when the Activity is no longer visible.
     
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnected(Bundle dataBundle) {
    	mLocationClient.connect();
    	mLocationClient.isConnected();
    	if(mLocationClient.isConnected()){
    		mLocationClient.requestLocationUpdates(mLocationRequest, this);
    	}else{
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }


    }
	private void printLocation(Location location) {
		if (location == null)
			output.append("\nLocation[unknown]\n\n");
		else
			output.append("\n\n" + location.toString());
	}
	
	private void printProvider(String provider) {
		LocationProvider info = locationManager.getProvider(provider);
		output.append(info.toString() + "\n\n");
	}
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}


    
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }
}
public class MainActivity extends FragmentActivity implements LocationListener, LocationSource
{
    *//**
     * Note that this may be null if the Google Play services APK is not available.
     *//*
    private GoogleMap mMap;
     
    private OnLocationChangedListener mListener;
    private LocationManager locationManager;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.location_test_database);
         
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
 
            if(locationManager != null)
            {
                boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                 
                if(gpsIsEnabled)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10F, this);
                }
                else if(networkIsEnabled)
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
                }
                else
                {
                    //Show an error dialog that GPS is disabled...
                }
            }
            else
            {
                //Show some generic error dialog because something must have gone wrong with location manager.
            }
         
        setUpMapIfNeeded();
    }
 
    @Override
    public void onPause()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(this);
        }
         
        super.onPause();
    }
     
    @Override
    public void onResume()
    {
        super.onResume();
         
        setUpMapIfNeeded();
         
        if(locationManager != null)
        {
            mMap.setMyLocationEnabled(true);
        }
    }
     
 
    *//**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView
     * MapView}) will show a prompt for the user to install/update the Google Play services APK on
     * their device.
     * <p>
     * A user can return to this Activity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the Activity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
     *//*
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) 
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.basicMap)).getMap();
            // Check if we were successful in obtaining the map.
            
            if (mMap != null) 
            {
                setUpMap();
            }
 
            //This is how you register the LocationSource
            mMap.setLocationSource(this);
        }
    }
     
    *//**
     * This is where we can add markers or lines, add listeners or move the camera.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     *//*
    private void setUpMap() 
    {
        mMap.setMyLocationEnabled(true);
    }
     
     
    @Override
    public void deactivate() 
    {
        mListener = null;
    }
 
    @Override
    public void onLocationChanged(Location location) 
    {
        if( mListener != null )
        {
            mListener.onLocationChanged( location );
 
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }
 
    @Override
    public void onProviderDisabled(String provider) 
    {
        // TODO Auto-generated method stub
        Toast.makeText(this, "provider disabled", Toast.LENGTH_SHORT).show();
    }
 
    @Override
    public void onProviderEnabled(String provider) 
    {
        // TODO Auto-generated method stub
        Toast.makeText(this, "provider enabled", Toast.LENGTH_SHORT).show();
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) 
    {
        // TODO Auto-generated method stub
        Toast.makeText(this, "status changed", Toast.LENGTH_SHORT).show();
    }

	@Override
	public void activate(OnLocationChangedListener arg0) {
		// TODO Auto-generated method stub
		
	}

//}
*/