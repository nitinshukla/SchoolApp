package com.example.sqlitedatabaseapp;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class ShowLocationActivity extends MapActivity  implements LocationListener{
	 private TextView latituteField;
	  private TextView longitudeField;
	  private LocationManager locationManager;
	  private String provider;
	  MapView mapView;
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.location_test_database);
	    mapView = (MapView) findViewById(R.id.map);
        mapView.setBuiltInZoomControls(true);
        /*GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();*/

        LatLng sydney = new LatLng(-33.867, 151.206);

        //mapView.setMyLocationEnabled(true);
        //mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

      //  mapView.addMarker(new MarkerOptions()
        //        .title("Sydney")
        //        .snippet("The most populous city in Australia.")
        //        .position(sydney));
        
/*	    latituteField = (TextView) findViewById(R.id.TextView02);
	    longitudeField = (TextView) findViewById(R.id.TextView04);

	    // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    boolean statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		   System.out.println("statusOfGPS===>"+statusOfGPS);
		   if (!statusOfGPS) {
			   Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			   startActivity(intent);
			 } 
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    // Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
	      latituteField.setText("Location not available");
	      longitudeField.setText("Location not available");
	    }*/
	  }

	  /* Request updates at startup */
/*	  @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }*/

	  /* Remove the locationlistener updates when Activity is paused */
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }

	  @Override
	  public void onLocationChanged(Location location) {
	    int lat = (int) (location.getLatitude());
	    int lng = (int) (location.getLongitude());
	    latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }

	  @Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	} 
