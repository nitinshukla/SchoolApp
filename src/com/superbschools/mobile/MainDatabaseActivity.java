package com.superbschools.mobile;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.superbschools.mobile.AnalyticsSuperbSchoolsApp.TrackerName;

public class MainDatabaseActivity extends ActionBarActivity {

	private TextView enteredText;
	ProgressDialog progressDialog;
	//List<String> listSuburb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main_database);
		final MySQLiteHelper dbHelper = new MySQLiteHelper(getBaseContext());
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TextView allNearby = (TextView) findViewById(R.id.AllNearby);
		TextView primary = (TextView) findViewById(R.id.Primary);
		TextView secondary = (TextView) findViewById(R.id.Secondary);

		Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
		enteredText = (TextView) findViewById(R.id.myautocomplete);

		if (null != btnSubmit) {
			btnSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					enteredText = (TextView) findViewById(R.id.myautocomplete);
					doSearchedText(v);
				}
			});
		}
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.test_database, menu);
		return true;

	}

	@Override
	protected void onResume() {
	  super.onResume();

	  // Logs 'install' and 'app activate' App Events.
	  AppEventsLogger.activateApp(this);
	}
	
	@Override
	protected void onPause() {
	  super.onPause();

	  // Logs 'app deactivate' App Event.
	  AppEventsLogger.deactivateApp(this);
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

	/**
	 * A placeholder fragment containing a simple view. This fragment would
	 * include your content.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_ad, container,
					false);
			return rootView;
		}
	}

	/**
	 * This class makes the ad request and loads the ad.
	 */
	public static class AdFragment extends Fragment {

		private AdView mAdView;

		public AdFragment() {
		}

		@Override
		public void onActivityCreated(Bundle bundle) {
			super.onActivityCreated(bundle);

			// Gets the ad view defined in layout/ad_fragment.xml with ad unit
			// ID set in
			// values/strings.xml.
			mAdView = (AdView) getView().findViewById(R.id.adView);

			// Create an ad request. Check logcat output for the hashed device
			// ID to
			// get test ads on a physical device. e.g.
			// "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
			AdRequest adRequest = new AdRequest.Builder().addTestDevice(
					AdRequest.DEVICE_ID_EMULATOR).build();

			// Start loading the ad in the background.
			mAdView.loadAd(adRequest);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_ad, container, false);
		}

		/** Called when leaving the activity */
		@Override
		public void onPause() {
			if (mAdView != null) {
				mAdView.pause();
			}
			super.onPause();
		}

		/** Called when returning to the activity */
		@Override
		public void onResume() {
			super.onResume();
			if (mAdView != null) {
				mAdView.resume();
			}
		}

		/** Called before the activity is destroyed */
		@Override
		public void onDestroy() {
			if (mAdView != null) {
				mAdView.destroy();
			}
			super.onDestroy();
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
		// Get tracker.
		Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
				.getTracker(TrackerName.APP_TRACKER);

		// Enable Display Features.
		t.enableAdvertisingIdCollection(true);
		// Set screen name.
		t.setScreenName("MainDatabaseActivityScreen");

		// Send a screen view.
		t.send(new HitBuilders.AppViewBuilder().build());
		// The activity is about to become visible.
	}

	public void doSearchedText(String v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("doSearchedText", "doSearchedText");
		intent.putExtra("enteredText", v.substring(0, v.indexOf('-')));
		intent.putExtra("enteredTextPostCode",
				v.substring(v.indexOf('-') + 1, (v.length())));
		startActivity(intent);
	}

	public void doSearchedText(View v) {
		enteredText = (TextView) findViewById(R.id.myautocomplete);
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("doSearchedText", "doSearchedText");
		intent.putExtra("enteredText", String.valueOf(enteredText.getText()));
		startActivity(intent);
	}

	public void doSettings(View v) {

		Intent intent = new Intent(this, SettingPageActivity.class);
		startActivity(intent);

	}

	public void doSmall(View v) {

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);

	}

	public void doAllNearby(View v) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(MainDatabaseActivity.this);
			progressDialog
					.setMessage("Getting All Nearby Schools.., please wait...");
			progressDialog.show();
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
		}
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("allNearBy", "allNearBy");
		startActivity(intent);
	}

	public void goMainPage(View v) {

		setContentView(R.layout.fragment_main_database);
	}

	public void doAllNearbyPrimary(View v) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(MainDatabaseActivity.this);
			progressDialog
					.setMessage("Getting Nearby Primary Schools.., please wait...");
			progressDialog.show();
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
		}
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("allNearByPrimary", "allNearByPrimary");
		startActivity(intent);
	}

	public void doAllNearbySecondary(View v) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(MainDatabaseActivity.this);
			progressDialog
					.setMessage("Getting Nearby Secondary Schools.., please wait...");
			progressDialog.show();
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
		}
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("allNearBySecondary", "allNearBySecondary");
		startActivity(intent);
	}
}
