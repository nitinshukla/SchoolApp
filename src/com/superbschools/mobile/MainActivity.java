package com.superbschools.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.StandardExceptionParser;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
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
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.superbschools.mobile.AnalyticsSuperbSchoolsApp.TrackerName;

public class MainActivity extends FragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener, LocationListener,
		OnItemSelectedListener {

	private CommentsDataSource datasource;
	private GoogleMap mMap;
	private LocationManager locationManager;
	private Location location = null;
	Geocoder geoCoder;
	GeoPoint point;
	Context context;
	Address addressDetails;
	List<Address> addressess;
	String state;
	List<String> listSuburb;
	List<String> listState;
	List<String> schoolNameList;
	List<String> listPostcode;
	List<String> newList;
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
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private Button btnSubmit;
	private TextView enteredText;
	private String doSearchedText;
	private String spinnerText;
	private String spinnerText1;
	private String spinnerText2;
	private String switchtext;
	private String doSearchedTextPostCode;
	private String doSearchedTextPostCodeSuburb;
	private String enteredTextSuburb;
	TextView redoSearchText;
	ImageView redoSearch;
	ImageView settings;
	TextView settingsText;
	ImageView nearby;
	TextView nearbyText;
	ImageView searchImg;
	TextView searchText;
	private AutoCompleteTextView tv;
	ProgressDialog progressDialog;

	public void doSmall(View v) {

		Intent intent = new Intent(this, SettingPageActivity.class);
		startActivity(intent);

	}

	public void doSettings(View v) {

		Intent intent = new Intent(this, SettingPageActivity.class);
		startActivity(intent);

	}

	public void nearby(View v) {

		Intent intent = new Intent(this, MainDatabaseActivity.class);
		startActivity(intent);

	}

	public void doSearch(View v) {

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void timerDelayRemoveDialog(long time, final ProgressDialog d) {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				d.dismiss();
			}
		}, time);
	}

	public void redoSearch(View v) {

		LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
		LatLngBounds.Builder builder = LatLngBounds.builder();
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Getting Schools.., please wait...");
			progressDialog.show();
			progressDialog.setCanceledOnTouchOutside(true);
			progressDialog.setCancelable(true);
			timerDelayRemoveDialog(10000, progressDialog);
			// progressDialog.setMax(10);
		}
		List<Schools> values = null;
		int counter = 0;
		datasource = new CommentsDataSource(this);
		datasource.open();
		try {
			values = datasource.getSchools();
		} catch (SQLiteException e) {
			Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
					.getTracker(TrackerName.APP_TRACKER);

			t.send(new HitBuilders.ExceptionBuilder()
					.setDescription(
							new StandardExceptionParser(this, null)
									.getDescription(Thread.currentThread()
											.getName(), e)).setFatal(false)
					.build());
		} catch (Exception ex) {
			Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
					.getTracker(TrackerName.APP_TRACKER);

			t.send(new HitBuilders.ExceptionBuilder()
					.setDescription(
							new StandardExceptionParser(this, null)
									.getDescription(Thread.currentThread()
											.getName(), ex)).setFatal(false)
					.build());
		}
		if (null != values) {
			for (int i = 0; i < values.size(); i++) {
				Schools placeDetail = values.get(i);
				if (bounds.contains(new LatLng(Double.parseDouble(placeDetail
						.getLatitude()), Double.parseDouble(placeDetail
						.getLongitude())))) {
					if (null != placeDetail.getLatitude()
							&& null != placeDetail.getLongitude()) {
						setAddMarker(placeDetail, counter, builder);
						counter++;
					}
				}

			}
			CameraUpdate cameraUpdate = null;
			if (counter > 0) {
				if (counter <= 5) {
					setNewBounds(builder, cameraUpdate);
					cameraUpdate = CameraUpdateFactory.newLatLngBounds(
							builder.build(), 40);
					doAnimateCamera(cameraUpdate);
				} else {
					cameraUpdate = CameraUpdateFactory.newLatLngBounds(
							builder.build(), 40);
					doAnimateCamera(cameraUpdate);
				}

			} else {
				setNoNearbySchoolFoundCameraUpdate(cameraUpdate);
			}
		} else {
			if (null != location) {
				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
						(new LatLng(location.getLatitude(), location
								.getLongitude())), 10);
				doAnimateCamera(cameraUpdate);
				doNoNearbySchoolFoundToast();
			} else {
				doNoNearbySchoolFoundToast();
			}
		}
		datasource.close();
	}

	private static final double EARTHRADIUS = 6366198;

	private static LatLng move(LatLng startLL, double toNorth, double toEast) {
		double lonDiff = meterToLongitude(toEast, startLL.latitude);
		double latDiff = meterToLatitude(toNorth);
		return new LatLng(startLL.latitude + latDiff, startLL.longitude
				+ lonDiff);
	}

	private static double meterToLongitude(double meterToEast, double latitude) {
		double latArc = Math.toRadians(latitude);
		double radius = Math.cos(latArc) * EARTHRADIUS;
		double rad = meterToEast / radius;
		return Math.toDegrees(rad);
	}

	private static double meterToLatitude(double meterToNorth) {
		double rad = meterToNorth / EARTHRADIUS;
		return Math.toDegrees(rad);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.information) {
			Intent intent = new Intent(this, InformationActivity.class);
			intent.putExtra("Information", "Information");
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int orientation = getResources().getConfiguration().orientation;
		if (orientation == 2)
			setContentView(R.layout.location_test_database_land);
		else
			setContentView(R.layout.location_test_database_port);

		context = this;
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		int RQS_GooglePlayServices = 0;
		if (resultCode == ConnectionResult.SUCCESS) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.mapview)).getMap();

			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.setMyLocationEnabled(true);

		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices).show();
		}

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

		tv = (AutoCompleteTextView) findViewById(R.id.myautocomplete);

		settings = (ImageView) findViewById(R.id.settings);
		settingsText = (TextView) findViewById(R.id.settingsText);
		redoSearch = (ImageView) findViewById(R.id.redoSearch);
		redoSearchText = (TextView) findViewById(R.id.redoSearchText);
		nearby = (ImageView) findViewById(R.id.nearby);
		nearbyText = (TextView) findViewById(R.id.nearbyText);
		if (null != redoSearch) {
			redoSearch.setVisibility(View.VISIBLE);
			redoSearchText.setVisibility(View.VISIBLE);
		}

		tv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				String selection = (String) parent.getItemAtPosition(position);
				doSearchedText(selection);
			}
		});
		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doSettings(v);
			}
		});
		settingsText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doSettings(v);
			}
		});
		redoSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				redoSearch(v);
			}
		});
		redoSearchText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				redoSearch(v);
			}
		});

		nearby.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goMainPage(v);
			}
		});
		nearbyText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goMainPage(v);
			}
		});
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				enteredText = (TextView) findViewById(R.id.myautocomplete);
				doSearchedText(v);
			}
		});
	}

	public void goMainPage(View v) {
		Intent intent = new Intent(this, MainDatabaseActivity.class);
		startActivity(intent);
	}

	public void doSearchedText(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("doSearchedText", "doSearchedText");
		intent.putExtra("enteredText", String.valueOf(enteredText.getText()));
		startActivity(intent);
	}

	public void doSearchedText(String v) {
		String s1 = null;
		String s2 = null;
		String s3;
		String s4;
		Pattern p = Pattern.compile("-");
		Matcher m = p.matcher(v);
		int count = 0;
		while (m.find()) {

			count += 1;
		}
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("doSearchedText", "doSearchedText");
		StringTokenizer st = new StringTokenizer(v, "-");

		while (st.hasMoreTokens()) {
			if (count == 1) {
				s1 = st.nextToken();

				s2 = st.nextToken();
				intent.putExtra("enteredText", s1);
				intent.putExtra("enteredTextPostCode", s2);
			} else if (count == 2) {
				s1 = st.nextToken();
				s2 = st.nextToken();
				s3 = st.nextToken();
				intent.putExtra("enteredText", s1);
				intent.putExtra("enteredTextSuburb", s2);
				intent.putExtra("enteredTextPostCodeSuburb", s3);
			} else if (count == 3) {
				s1 = st.nextToken();
				s2 = st.nextToken();
				s3 = st.nextToken();
				s4 = st.nextToken();
				intent.putExtra("enteredText", s1 + "-" + s2);
				intent.putExtra("enteredTextSuburb", s3);
				intent.putExtra("enteredTextPostCodeSuburb", s4);
			} else {
				s1 = st.nextToken();
				intent.putExtra("enteredText", s1);
			}
		}

		startActivity(intent);
	}

	private class AsyncCallWS extends
			AsyncTask<List<Schools>, List<Schools>, List<Schools>> {

		ProgressDialog progressDialog;

		@Override
		protected List<Schools> doInBackground(List... params) {
			List<Schools> findPlaces = calculate();

			return findPlaces;
		}

		@Override
		protected void onPostExecute(List<Schools> findPlaces) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}

			int counter = 0;
			LatLngBounds.Builder builder = new LatLngBounds.Builder();

			if (null != findPlaces) {
				for (int i = 0; i < findPlaces.size(); i++) {

					Schools placeDetail = findPlaces.get(i);
					if (null != placeDetail.getLatitude()
							&& null != placeDetail.getLongitude()) {
						setAddMarker(placeDetail, counter, builder);
						counter++;

					}
				}

				CameraUpdate cameraUpdate = null;
				if (counter > 0) {
					if (counter <= 5) {
						setNewBounds(builder, cameraUpdate);
						cameraUpdate = CameraUpdateFactory.newLatLngBounds(
								builder.build(), 40);
						doAnimateCamera(cameraUpdate);
					} else {
						cameraUpdate = CameraUpdateFactory.newLatLngBounds(
								builder.build(), 40);
						doAnimateCamera(cameraUpdate);
					}

				} else {
					setNoNearbySchoolFoundCameraUpdate(cameraUpdate);
				}
			} else {
				if (null != location) {
					CameraUpdate cameraUpdate = CameraUpdateFactory
							.newLatLngZoom((new LatLng(location.getLatitude(),
									location.getLongitude())), 10);
					doAnimateCamera(cameraUpdate);
					doNoNearbySchoolFoundToast();
				} else {
					doNoNearbySchoolFoundToast();
				}
			}
		}

		@Override
		protected void onPreExecute() {
			if (progressDialog == null) {
				progressDialog = new ProgressDialog(MainActivity.this);
				progressDialog.setMessage("Getting Schools.., please wait...");
				progressDialog.show();
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setCancelable(false);
				progressDialog.setMax(100);
			}

		}

	}

	public List<Schools> calculate() {
		location = getCurrentLocation();
		if (null == location) {
			return null;
		}

		datasource = new CommentsDataSource(this);
		datasource.open();
		if (null == point) {
			point = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
			addressess = getAddressFromLocation(point);
		} else {
			point = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
			addressess = getAddressFromLocation(point);
		}

		if (null != addressess) {
			for (int a = 0; a < addressess.size(); a++) {
				addressDetails = addressess.get(a);
			}
		}

		enteredText = (TextView) findViewById(R.id.myautocomplete);

		Intent intent = getIntent();
		spinnerText = intent.getStringExtra("spinner");
		spinnerText1 = intent.getStringExtra("spinner1");
		spinnerText2 = intent.getStringExtra("spinner2");
		switchtext = intent.getStringExtra("switchStatus");
		doSearchedText = intent.getStringExtra("enteredText");
		doSearchedTextPostCode = intent.getStringExtra("enteredTextPostCode");
		doSearchedTextPostCodeSuburb = intent
				.getStringExtra("enteredTextPostCodeSuburb");
		enteredTextSuburb = intent.getStringExtra("enteredTextSuburb");
		if (null != addressDetails) {
			if (null == state) {
				state = getState(addressDetails.getAdminArea());
			}
		}

		List<Schools> values = null;
		if (null != state) {
			if (null != intent.getStringExtra("allNearBy")
					&& !"".equalsIgnoreCase(intent.getStringExtra("allNearBy"))) {
				try {
					values = datasource.getSchoolByState(state);
				} catch (SQLiteException e) {
					Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
							.getTracker(TrackerName.APP_TRACKER);

					t.send(new HitBuilders.ExceptionBuilder()
							.setDescription(
									new StandardExceptionParser(this, null)
											.getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
				} catch (Exception ex) {
					Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
							.getTracker(TrackerName.APP_TRACKER);

					t.send(new HitBuilders.ExceptionBuilder()
							.setDescription(
									new StandardExceptionParser(this, null)
											.getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
							.build());
				}
			} else if (null != intent.getStringExtra("allNearByPrimary")
					&& !"".equalsIgnoreCase(intent
							.getStringExtra("allNearByPrimary"))) {
				try {
					values = datasource.getSchoolByState(state, PRIMARY);
				} catch (SQLiteException e) {
					Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
							.getTracker(TrackerName.APP_TRACKER);

					t.send(new HitBuilders.ExceptionBuilder()
							.setDescription(
									new StandardExceptionParser(this, null)
											.getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
				} catch (Exception ex) {
					Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
							.getTracker(TrackerName.APP_TRACKER);

					t.send(new HitBuilders.ExceptionBuilder()
							.setDescription(
									new StandardExceptionParser(this, null)
											.getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
							.build());
				}
			} else if (null != intent.getStringExtra("allNearBySecondary")
					&& !"".equalsIgnoreCase(intent
							.getStringExtra("allNearBySecondary"))) {
				try {
					values = datasource.getSchoolByState(state, SECONDARY);
				} catch (SQLiteException e) {
					Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
							.getTracker(TrackerName.APP_TRACKER);

					t.send(new HitBuilders.ExceptionBuilder()
							.setDescription(
									new StandardExceptionParser(this, null)
											.getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
				} catch (Exception ex) {
					Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
							.getTracker(TrackerName.APP_TRACKER);

					t.send(new HitBuilders.ExceptionBuilder()
							.setDescription(
									new StandardExceptionParser(this, null)
											.getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
							.build());
				}
			} else if (null != intent.getStringExtra("enteredText")
					&& !("".equalsIgnoreCase(intent
							.getStringExtra("enteredText")))) {
				if (null != intent.getStringExtra("doSearchedText")
						&& !("".equalsIgnoreCase(intent
								.getStringExtra("doSearchedText")))) {

					state = getState(intent.getStringExtra("enteredText"));
					if (null != state) {
						try {
							values = datasource.getSchoolByState(state);
						} catch (SQLiteException e) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
						} catch (Exception ex) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
									.build());
						}
					} else {
						boolean isNum = isInteger(doSearchedText);
						if (isNum) {
							try {
								values = datasource
										.getSchoolByPostCode(doSearchedText);
							} catch (SQLiteException e) {
								Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
										.getTracker(TrackerName.APP_TRACKER);

								t.send(new HitBuilders.ExceptionBuilder()
										.setDescription(
												new StandardExceptionParser(
														this, null)
														.getDescription(
																Thread.currentThread()
																		.getName(),
																e))
										.setFatal(false).build());
							} catch (Exception ex) {
								Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
										.getTracker(TrackerName.APP_TRACKER);

								t.send(new HitBuilders.ExceptionBuilder()
										.setDescription(
												new StandardExceptionParser(
														this, null)
														.getDescription(
																Thread.currentThread()
																		.getName(),
																ex))
										.setFatal(false).build());
							}
						} else if (null != doSearchedTextPostCode) {
							try {
								values = datasource.getSchoolBySuburb(
										doSearchedText, doSearchedTextPostCode);
							} catch (SQLiteException e) {
								Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
										.getTracker(TrackerName.APP_TRACKER);

								t.send(new HitBuilders.ExceptionBuilder()
										.setDescription(
												new StandardExceptionParser(
														this, null)
														.getDescription(
																Thread.currentThread()
																		.getName(),
																e))
										.setFatal(false).build());
							} catch (Exception ex) {
								Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
										.getTracker(TrackerName.APP_TRACKER);

								t.send(new HitBuilders.ExceptionBuilder()
										.setDescription(
												new StandardExceptionParser(
														this, null)
														.getDescription(
																Thread.currentThread()
																		.getName(),
																ex))
										.setFatal(false).build());
							}

						} else if (null != doSearchedTextPostCodeSuburb) {
							try {
								values = datasource.getSchoolNameBySuburb(
										doSearchedText, doSearchedTextPostCode,
										enteredTextSuburb);
								new AlertDialog.Builder(this).setTitle("Argh")
										.setMessage("Watch out!")
										.setNeutralButton("Close", null).show();
							} catch (SQLiteException e) {
								Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
										.getTracker(TrackerName.APP_TRACKER);

								t.send(new HitBuilders.ExceptionBuilder()
										.setDescription(
												new StandardExceptionParser(
														this, null)
														.getDescription(
																Thread.currentThread()
																		.getName(),
																e))
										.setFatal(false).build());
								Toast.makeText(getBaseContext(),
										e.getMessage(), Toast.LENGTH_SHORT)
										.show();
							} catch (Exception ex) {
								Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
										.getTracker(TrackerName.APP_TRACKER);

								t.send(new HitBuilders.ExceptionBuilder()
										.setDescription(
												new StandardExceptionParser(
														this, null)
														.getDescription(
																Thread.currentThread()
																		.getName(),
																ex))
										.setFatal(false).build());
								Toast.makeText(getBaseContext(),
										ex.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							try {
								values = datasource
										.getSchoolBySuburb(doSearchedText);
							} catch (SQLiteException e) {
								Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
										.getTracker(TrackerName.APP_TRACKER);

								t.send(new HitBuilders.ExceptionBuilder()
										.setDescription(
												new StandardExceptionParser(
														this, null)
														.getDescription(
																Thread.currentThread()
																		.getName(),
																e))
										.setFatal(false).build());
							} catch (Exception ex) {
								Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
										.getTracker(TrackerName.APP_TRACKER);

								t.send(new HitBuilders.ExceptionBuilder()
										.setDescription(
												new StandardExceptionParser(
														this, null)
														.getDescription(
																Thread.currentThread()
																		.getName(),
																ex))
										.setFatal(false).build());
							}
						}
					}
				}
			} else if (null != intent.getStringExtra("spinner")
					&& !("".equalsIgnoreCase(intent.getStringExtra("spinner")))) {
				try {
					values = datasource.getSchoolBySettingCriteria(spinnerText,
							spinnerText1, switchtext, spinnerText2);
				} catch (SQLiteException e) {
					Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
							.getTracker(TrackerName.APP_TRACKER);

					t.send(new HitBuilders.ExceptionBuilder()
							.setDescription(
									new StandardExceptionParser(this, null)
											.getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
				} catch (Exception ex) {
					Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
							.getTracker(TrackerName.APP_TRACKER);

					t.send(new HitBuilders.ExceptionBuilder()
							.setDescription(
									new StandardExceptionParser(this, null)
											.getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
							.build());
				}
			}
		} else if (null != intent.getStringExtra("enteredText")
				&& !("".equalsIgnoreCase(intent.getStringExtra("enteredText")))) {
			if (null != intent.getStringExtra("doSearchedText")
					&& !("".equalsIgnoreCase(intent
							.getStringExtra("doSearchedText")))) {
				state = getState(intent.getStringExtra("enteredText"));
				if (null != state) {
					try {
						values = datasource.getSchoolByState(state);
					} catch (SQLiteException e) {
						Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
								.getTracker(TrackerName.APP_TRACKER);

						t.send(new HitBuilders.ExceptionBuilder()
								.setDescription(
										new StandardExceptionParser(this, null)
												.getDescription(Thread
														.currentThread()
														.getName(), e))
								.setFatal(false).build());
					} catch (Exception ex) {
						Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
								.getTracker(TrackerName.APP_TRACKER);

						t.send(new HitBuilders.ExceptionBuilder()
								.setDescription(
										new StandardExceptionParser(this, null)
												.getDescription(Thread
														.currentThread()
														.getName(), ex))
								.setFatal(false).build());
					}
				} else {
					boolean isNum = isInteger(doSearchedText);
					if (isNum) {
						try {
							values = datasource
									.getSchoolByPostCode(doSearchedText);
						} catch (SQLiteException e) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
						} catch (Exception ex) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
									.build());
						}
					} else if (null != enteredTextSuburb) {
						try {
							values = datasource.getSchoolNameBySuburb(
									doSearchedText, enteredTextSuburb,
									doSearchedTextPostCodeSuburb);
							
						} catch (SQLiteException e) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
						} catch (Exception ex) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
									.build());
						}
					} else if (null != doSearchedTextPostCode) {
						try {
							values = datasource.getSchoolBySuburb(
									doSearchedText, doSearchedTextPostCode);
						} catch (SQLiteException e) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
						} catch (Exception ex) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
									.build());
						}
					} else {
						try {
							values = datasource
									.getSchoolBySuburb(doSearchedText);
						} catch (SQLiteException e) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													e)).setFatal(false).build());
						} catch (Exception ex) {
							Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
									.getTracker(TrackerName.APP_TRACKER);

							t.send(new HitBuilders.ExceptionBuilder()
									.setDescription(
											new StandardExceptionParser(this,
													null).getDescription(Thread
													.currentThread().getName(),
													ex)).setFatal(false)
									.build());
						}
					}
				}
			}
		} else if (null != intent.getStringExtra("spinner")
				&& !("".equalsIgnoreCase(intent.getStringExtra("spinner")))) {
			try {
				values = datasource.getSchoolBySettingCriteria(spinnerText,
						spinnerText1, switchtext, spinnerText2);
			} catch (SQLiteException e) {
				Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
						.getTracker(TrackerName.APP_TRACKER);

				t.send(new HitBuilders.ExceptionBuilder()
						.setDescription(
								new StandardExceptionParser(this, null)
										.getDescription(Thread.currentThread()
												.getName(), e)).setFatal(false)
						.build());
			} catch (Exception ex) {
				Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
						.getTracker(TrackerName.APP_TRACKER);

				t.send(new HitBuilders.ExceptionBuilder()
						.setDescription(
								new StandardExceptionParser(this, null)
										.getDescription(Thread.currentThread()
												.getName(), ex))
						.setFatal(false).build());
			}
		}

		if (null != values) {

			Iterator<Schools> i = values.iterator();
			while (i.hasNext()) {
				Schools placeDetail = i.next();
				if (null != placeDetail.getLatitude()) {
					Location plc = new Location("");
					plc.setLatitude(Double.parseDouble(placeDetail
							.getLatitude()));
					plc.setLongitude(Double.parseDouble(placeDetail
							.getLongitude()));
					float distance = location.distanceTo(plc);
					if ((null != intent.getStringExtra("allNearBy") && !""
							.equalsIgnoreCase(intent
									.getStringExtra("allNearBy")))
							|| (null != intent
									.getStringExtra("allNearByPrimary") && !""
									.equalsIgnoreCase(intent
											.getStringExtra("allNearByPrimary")))
							|| (null != intent
									.getStringExtra("allNearBySecondary") && !""
									.equalsIgnoreCase(intent
											.getStringExtra("allNearBySecondary")))) {
						if (distance > 5000) {

							i.remove();
						}
					}
				}
			}
		}
		datasource.close();
		return values;
	}

	private String getState(String adminArea) {
		// TODO Auto-generated method stub
		if (adminArea.equalsIgnoreCase("New South Wales")
				|| adminArea.equalsIgnoreCase("NSW")) {
			return "NSW";
		} else if (adminArea.equalsIgnoreCase("Australian Capital territory")
				|| adminArea.equalsIgnoreCase("ACT")) {
			return "ACT";
		} else if (adminArea.equalsIgnoreCase("Queensland")
				|| adminArea.equalsIgnoreCase("QLD")) {
			return "QLD";
		} else if (adminArea.equalsIgnoreCase("South Australia")
				|| adminArea.equalsIgnoreCase("SA")) {
			return "SA";
		} else if (adminArea.equalsIgnoreCase("Northern Territory")
				|| adminArea.equalsIgnoreCase("NT")) {
			return "NT";
		} else if (adminArea.equalsIgnoreCase("Western Australia")
				|| adminArea.equalsIgnoreCase("WA")) {
			return "WA";
		} else if (adminArea.equalsIgnoreCase("Tasmania")
				|| adminArea.equalsIgnoreCase("TAS")) {
			return "TAS";
		} else if (adminArea.equalsIgnoreCase("Victoria")
				|| adminArea.equalsIgnoreCase("VIC")) {
			return "VIC";
		} else {
			return null;
		}
	}

	public String ConvertPointToLocation(GeoPoint point) {
		String address = "";
		geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
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
		geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
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
		// Get tracker.
		Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
				.getTracker(TrackerName.APP_TRACKER);

		// Enable Display Features.
		t.enableAdvertisingIdCollection(true);
		// Set screen name.
		t.setScreenName("MainActivityScreen");

		// Send a screen view.
		t.send(new HitBuilders.AppViewBuilder().build());
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

	public void addMarker(LatLng position, Schools schoolDetail, long id) {
		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker arg0) {
				return null;

			}

			// Defines the contents of the InfoWindow
			@Override
			public View getInfoContents(Marker arg0) {

				// Getting view from the layout file info_window_layout
				View v = getLayoutInflater().inflate(
						R.layout.info_window_layout, null);

				// Getting reference to the TextView to set latitude
				TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);

				// Getting reference to the TextView to set longitude
				TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

				SpannableString titleText = new SpannableString(arg0.getTitle());
				titleText.setSpan(
						new ForegroundColorSpan(Color.rgb(184, 134, 11)), 0,
						titleText.length(), 0);
				tvLat.setText(titleText);
				SpannableString snippetText = new SpannableString(arg0
						.getSnippet());
				snippetText.setSpan(
						new ForegroundColorSpan(Color.rgb(178, 34, 34)), 0,
						snippetText.length(), 0);
				tvLng.setText(snippetText);

				return v;

			}
		});

		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				Intent in = new Intent(getApplicationContext(),
						SchoolDetailsActivity.class);
				in.putExtra("markerTitle", marker.getTitle());
				in.putExtra("markerSnippet", marker.getSnippet());
				startActivity(in);
			}
		});

		if (schoolDetail.getPrimarySecondary().equalsIgnoreCase("P")) {
			if (0 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 250) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_1);

			} else if (251 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 500) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_2);

			} else if (501 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 750) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_3);

			} else if (751 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 1000) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_4);

			} else if (1001 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 1250) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_5);
			} else if (1251 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 1500) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_6);
			} else if (1501 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 1750) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_7);
			} else if (1751 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 2000) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_8);
			} else if (2001 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 2250) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_9);
			} else if (2251 < (schoolDetail.getRankPrimary())
					&& (schoolDetail.getRankPrimary()) < 2500) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_10);
			} else if (schoolDetail.getId() > 2500) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_10);
			}
		} else if (schoolDetail.getPrimarySecondary().equalsIgnoreCase("S")) {
			if (0 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 650) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_1s);
			} else if (651 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 1300) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_2s);
			} else if (1301 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 1950) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_3s);
			} else if (1951 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 2600) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_4s);
			} else if (2601 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 3250) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_5s);
			} else if (3251 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 3900) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_6s);
			} else if (3901 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 4550) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_7s);
			} else if (4551 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 5200) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_8s);
			} else if (5201 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 5850) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_9s);
			} else if (5851 < (schoolDetail.getRankSecondary())
					&& (schoolDetail.getRankSecondary()) < 6500) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_10s);
			} else if ((schoolDetail.getRankSecondary()) > 6501) {
				domMapAddMarker(position, schoolDetail.getSchoolName(),
						schoolDetail.getSnippet(), R.drawable.number_10s);
			}
		}

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

		mLocationClient.requestLocationUpdates(mLocationRequest, this);
		location = getCurrentLocation();

		if (null == location) {
			// Toast.makeText(this, "Location is null",
			// Toast.LENGTH_SHORT).show();
		} else {
			if (null == point) {
				point = new GeoPoint((int) (location.getLatitude() * 1E6),
						(int) (location.getLongitude() * 1E6));
			}
			if (null == addressess) {
				addressess = getAddressFromLocation(point);
			}
			if (null != addressess) {
				for (int a = 0; a < addressess.size(); a++) {
					addressDetails = addressess.get(a);
				}
			}

			if (null != addressDetails) {
				datasource = new CommentsDataSource(this);
				datasource.open();
				state = getState(addressDetails.getAdminArea());
				if (null == listSuburb) {
					try {
						listSuburb = datasource.getSuburbByState();
						listPostcode = datasource.getPostcode();
						schoolNameList = datasource.getSchoolsName();
						listState = datasource.getStates();
						newList = new ArrayList<String>(listSuburb);
						newList.addAll(schoolNameList);
						newList.addAll(listPostcode);
						newList.addAll(listState);
					} catch (SQLiteException e) {
						Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
								.getTracker(TrackerName.APP_TRACKER);

						t.send(new HitBuilders.ExceptionBuilder()
								.setDescription(
										new StandardExceptionParser(this, null)
												.getDescription(Thread
														.currentThread()
														.getName(), e))
								.setFatal(false).build());
					} catch (Exception ex) {
						Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
								.getTracker(TrackerName.APP_TRACKER);

						t.send(new HitBuilders.ExceptionBuilder()
								.setDescription(
										new StandardExceptionParser(this, null)
												.getDescription(Thread
														.currentThread()
														.getName(), ex))
								.setFatal(false).build());
					}
				}
				datasource.close();
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_dropdown_item_1line, newList);
				AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.myautocomplete);
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_database, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
		}
		return false;
	}

	public void setProgressBar(int i) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Getting Schools.., please wait...");
			progressDialog.show();
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
			progressDialog.setMax(i);
		}
	}

	public void setNoNearbySchoolFoundCameraUpdate(CameraUpdate cameraUpdate) {
		cameraUpdate = CameraUpdateFactory.newLatLngZoom(
				(new LatLng(location.getLatitude(), location.getLongitude())),
				10);
		mMap.setMyLocationEnabled(true);
		mMap.moveCamera(cameraUpdate);
		mMap.animateCamera(cameraUpdate);
		doNoNearbySchoolFoundToast();
	}

	public void setNewBounds(LatLngBounds.Builder builder,
			CameraUpdate cameraUpdate) {
		LatLng center = builder.build().getCenter();
		LatLng northEast = move(center, 709, 709);
		LatLng southWest = move(center, -709, -709);
		builder.include(southWest);
		builder.include(northEast);
		cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), 40);
	}

	public void setAddMarker(Schools placeDetail, int counter,
			LatLngBounds.Builder builder) {

		addMarker(new LatLng(Double.parseDouble(placeDetail.getLatitude()),
				Double.parseDouble(placeDetail.getLongitude())), placeDetail,
				placeDetail.getId());

		builder.include(new LatLng(
				Double.parseDouble(placeDetail.getLatitude()), Double
						.parseDouble(placeDetail.getLongitude())));

	}

	public void doAnimateCamera(CameraUpdate cameraUpdate) {
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.

			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.mapview)).getMap();
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.setMyLocationEnabled(true);
			mMap.moveCamera(cameraUpdate);
			mMap.animateCamera(cameraUpdate);
		} else {
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.setMyLocationEnabled(true);
			mMap.moveCamera(cameraUpdate);
			mMap.animateCamera(cameraUpdate);
		}
	}

	public void doNoNearbySchoolFoundToast() {
		Toast.makeText(context, "No Nearby School Found", Toast.LENGTH_SHORT)
				.show();

	}

	public void domMapAddMarker(LatLng position, String schoolName,
			String snippet, int iconName) {
		mMap.addMarker(new MarkerOptions().position(position).title(schoolName)
				.snippet(snippet)
				.icon(BitmapDescriptorFactory.fromResource(iconName)));
	}
}