package com.superbschools.mobile;


import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.superbschools.mobile.AnalyticsSuperbSchoolsApp.TrackerName;


public class InformationActivity extends Activity {

	private String TAG = "InformationActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int orientation= getResources().getConfiguration().orientation;
		if(orientation==2)
		    setContentView(R.layout.information_activity_land);
		else
		    setContentView(R.layout.information_activity_port);
		//Linkify.addLinks(schoolEmail, Linkify.EMAIL_ADDRESSES);
		TextView tvContactDetails = (TextView) findViewById(R.id.tvContactDetails); 
		TextView versionDetails = (TextView) findViewById(R.id.versionDetails); 
		String versionName = null ;
		try {
			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG,"Error while fetching app version",e);
		}
        String sEmail = "pank54@gmail.com";
        String contactUs = "Contact";
        String sHtmlEmail = null;
		sHtmlEmail = "Please <a href=\"mailto:" + sEmail + "\">" + contactUs + "</a> us";
		tvContactDetails.setText(Html.fromHtml(sHtmlEmail));
		tvContactDetails.setMovementMethod(LinkMovementMethod.getInstance());
		if(null != versionName){
			versionDetails.setText("Version : "+ versionName);
		}else{
			versionDetails.setText("Version : "+"0");
		}
		//setContentView(R.layout.information_activity);
	}

	/*
	 * Called when the Activity becomes visible.
	 */
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
		t.setScreenName("InformationActivityScreen");

		// Send a screen view.
		t.send(new HitBuilders.AppViewBuilder().build());
	}  
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        EasyTracker.getInstance(this).activityStop(this);  
    }
}