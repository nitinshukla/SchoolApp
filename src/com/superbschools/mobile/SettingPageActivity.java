package com.superbschools.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.superbschools.mobile.AnalyticsSuperbSchoolsApp.TrackerName;

public class SettingPageActivity extends Activity {
	private TextView switchStatus;
	private Spinner spinner1, spinner2, spinner, spinner3;
	private Button setSettingSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity_database);
		switchStatus = (TextView) findViewById(R.id.labelSpinner4);
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		spinner = (Spinner) findViewById(R.id.spinner);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		setSettingSubmit = (Button) findViewById(R.id.setSettingSubmit);

		setSettingSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(SettingPageActivity.this,
						MainActivity.class);
				intent.setAction("Settings");
				intent.putExtra("spinner",
						String.valueOf(spinner.getSelectedItem()));
				intent.putExtra("spinner1",
						String.valueOf(spinner1.getSelectedItem()));
				intent.putExtra("switchStatus",
						String.valueOf(spinner2.getSelectedItem()));
				intent.putExtra("spinner2",
						String.valueOf(spinner3.getSelectedItem()));
				startActivity(intent);

			}

		});
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

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (isChecked) {
			switchStatus.setText("Primary");
		} else {
			switchStatus.setText("Secondary");
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
		t.setScreenName("SettingPageActivityScreen");

		// Send a screen view.
		t.send(new HitBuilders.AppViewBuilder().build());
		// The activity is about to become visible.
	}

	@Override
	protected void onResume() {
		super.onResume();
		// The activity has become visible (it is now "resumed").
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Another activity is taking focus (this activity is about to be
		// "paused").
	}

	@Override
	protected void onStop() {
		super.onStop();
		// The activity is no longer visible (it is now "stopped")
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// The activity is about to be destroyed.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_database, menu);
		return true;
	}
}