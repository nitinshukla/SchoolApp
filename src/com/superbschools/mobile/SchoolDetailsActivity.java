package com.superbschools.mobile;


import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.StandardExceptionParser;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.superbschools.mobile.AnalyticsSuperbSchoolsApp.TrackerName;


public class SchoolDetailsActivity extends Activity {
	private CommentsDataSource datasource;
	private String titleText;
	private String titleTextSnippet;
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.school_details_page);
	        List<Schools> values = null;
			Intent intent = getIntent();
			titleText = intent.getStringExtra("markerTitle");
			titleTextSnippet = intent.getStringExtra("markerSnippet");
	
			String s1 = null;
			String s2 = null;
			String s3 = null;
			int count = 0;
			String[] lines = titleTextSnippet.split("\n");
			//s1 = lines[1] ;
			for (int i = 0; i < lines.length; i++)
		     {
		        s3 = lines[1] ;
	
		     }
			Pattern p = Pattern.compile(":");
			Matcher m = p.matcher(s3);
			while (m.find()) {

				count += 1;
			}
			StringTokenizer st = new StringTokenizer(s3, ":");
			while (st.hasMoreTokens()) {
				if (count == 1) {
					s1 = st.nextToken();
					s2 = st.nextToken();
				}
			}
			datasource = new CommentsDataSource(this);
			datasource.open();
			try{
				values = datasource.getSchoolById(s1,s2);
			}catch (SQLiteException e) {
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
	        
	        TextView schoolname = (TextView) findViewById(R.id.schoolName);
	        TextView schoolAddress = (TextView) findViewById(R.id.schoolAddress);
	        TextView schoolPhone = (TextView) findViewById(R.id.schoolPhone);
	        TextView schoolFax = (TextView) findViewById(R.id.schoolFax);
	        TextView schoolEmail = (TextView) findViewById(R.id.schoolEmail);
	        TextView schoolSector = (TextView) findViewById(R.id.schoolSector);
	        TextView schoolPrimSec = (TextView) findViewById(R.id.schoolPrimSec);
	        TextView schoolRank = (TextView) findViewById(R.id.schoolRank);
	        TextView schoolWebsite = (TextView) findViewById(R.id.schoolWebsite);
	        TextView reading = (TextView) findViewById(R.id.reading);
	        TextView numeracy = (TextView) findViewById(R.id.Numeracy);
	        TextView grampunc = (TextView) findViewById(R.id.Grampunc);
	        TextView spelling = (TextView) findViewById(R.id.Spelling);
	        TextView writing = (TextView) findViewById(R.id.Writing);
	       
	        String sEmail = "pank54@gmail.com";
	        String contactUs = "Contact";
	        TextView tvContactDetails = (TextView) findViewById(R.id.tvContactDetails); 
	        Schools placeDetail;
	        String sHtmlEmail = null;
	        if (null != values) {

				Iterator<Schools> i = values.iterator();
				while (i.hasNext()) {
					placeDetail = i.next();
					if (null != placeDetail.getLatitude()) {

						schoolAddress.setText(placeDetail.getStreetAddress() +", "+placeDetail.getSuburb()+", "+placeDetail.getState()+", "+placeDetail.getPostcode());
						schoolname.setText(placeDetail.getSchoolName());
						schoolPhone.setText(placeDetail.getPhone());
						schoolFax.setText(placeDetail.getFax());
						schoolEmail.setText(placeDetail.getEmail());
						schoolSector.setText(placeDetail.getSectorName());
						schoolPrimSec.setText(placeDetail.getPrimarySecondaryName());
						if(0 != placeDetail.getRankPrimary()){
							schoolRank.setText(placeDetail.getRankPrimary() + " out of 6644 Primary Schools");
						}else if(0 != placeDetail.getRankSecondary()){
							schoolRank.setText(placeDetail.getRankSecondary() + " out of 2518 Secondary Schools");
						}
						
						
						schoolWebsite.setText(placeDetail.getWebsite());
						reading.setText(placeDetail.getReading());
						writing.setText(placeDetail.getWriting());
						numeracy.setText(placeDetail.getNumeracy());
						grampunc.setText(placeDetail.getGrampunc());
						spelling.setText(placeDetail.getSpelling());
						Linkify.addLinks(schoolPhone, Linkify.PHONE_NUMBERS);
						Linkify.addLinks(schoolWebsite, Linkify.WEB_URLS);
						
						Linkify.addLinks(schoolAddress, Linkify.ALL);
						Linkify.addLinks(schoolEmail, Linkify.EMAIL_ADDRESSES);
						sHtmlEmail = "Please <a href=\"mailto:" + sEmail + "?subject="+placeDetail.getSchoolName()+"&body=The below information about "+placeDetail.getSchoolName()+ " is not correct: \">" + contactUs + "</a> us";
		        		tvContactDetails.setText(Html.fromHtml(sHtmlEmail));
		        		tvContactDetails.setMovementMethod(LinkMovementMethod.getInstance());
					}
				}
			}
	        		

	
	        datasource.close();

	    }
	   @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			
			MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.menu.test_database, menu);
	        return super.onCreateOptionsMenu(menu);
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
		protected void onStart() {
			super.onStart();
			EasyTracker.getInstance(this).activityStart(this);
			// Get tracker.
			Tracker t = ((AnalyticsSuperbSchoolsApp) getApplication())
					.getTracker(TrackerName.APP_TRACKER);

			// Enable Display Features.
			t.enableAdvertisingIdCollection(true);
			// Set screen name.
			t.setScreenName("SchoolDetailsActivityScreen");

			// Send a screen view.
			t.send(new HitBuilders.AppViewBuilder().build());
			// The activity is about to become visible.
		}		
	}