package com.superbschools.mobile;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class CommentsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String latitude_not_null = " and lat is not null ";
  private String longitude_not_null = " and long is not null ";
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
		  MySQLiteHelper.SCHOOL_NAME,
		  MySQLiteHelper.AVERAGE_TOTAL,
	      MySQLiteHelper.LATITUDE,
	      MySQLiteHelper.LONGITUDE,
	      MySQLiteHelper.STREETADDRESS,
	      MySQLiteHelper.SUBURB,
          MySQLiteHelper.STATE,
          MySQLiteHelper.POSTCODE,
          MySQLiteHelper.PHONE,
          MySQLiteHelper.EMAIL,
          MySQLiteHelper.FAX,
          MySQLiteHelper.WEBSITE,
          MySQLiteHelper.SECTOR,
          MySQLiteHelper.PRIMARYSECONDARY,
          MySQLiteHelper.MALEFEMALE,
          MySQLiteHelper.SINGLECOED,
          MySQLiteHelper.RANKPRIMARY,
          MySQLiteHelper.RANKSECONDARY
      };

  private String[] schoolDetailColumns = { MySQLiteHelper.COLUMN_ID,
		  MySQLiteHelper.SCHOOL_NAME,
		  MySQLiteHelper.AVERAGE_TOTAL,
	      MySQLiteHelper.LATITUDE,
	      MySQLiteHelper.LONGITUDE,
	      MySQLiteHelper.STREETADDRESS,
	      MySQLiteHelper.SUBURB,
          MySQLiteHelper.STATE,
          MySQLiteHelper.POSTCODE,
          MySQLiteHelper.PHONE,
          MySQLiteHelper.EMAIL,
          MySQLiteHelper.FAX,
          MySQLiteHelper.WEBSITE,
          MySQLiteHelper.SECTOR,
          MySQLiteHelper.PRIMARYSECONDARY,
          MySQLiteHelper.MALEFEMALE,
          MySQLiteHelper.SINGLECOED,
          MySQLiteHelper.RANKPRIMARY,
          MySQLiteHelper.RANKSECONDARY,
          MySQLiteHelper.READING,
          MySQLiteHelper.GRAMPUNC,
          MySQLiteHelper.NUMERACY,
          MySQLiteHelper.WRITING,
          MySQLiteHelper.SPELLING
      };
  private String[] allbSuburbColumns = { MySQLiteHelper.SUBURB,MySQLiteHelper.POSTCODE};
  private String[] postcodeColumns = { MySQLiteHelper.POSTCODE};
  private String[] allSchoolsNameColumns = { MySQLiteHelper.SCHOOL_NAME,MySQLiteHelper.SUBURB,MySQLiteHelper.POSTCODE};
  private String[] stateColumns = {MySQLiteHelper.STATE};
  
  private String TAG = "CommentsDataSource";

  public CommentsDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

 
  public List<Schools> getSchoolByState(String state) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.STATE + " = ? "+ latitude_not_null + longitude_not_null, new String[] { state }, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = cursorToComment(cursor);
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
    public List<Schools> getSchoolById(String primsec ,String id) {
	    List<Schools> schools = new ArrayList<Schools>();
	    Cursor cursor = null;
	    if(primsec.equalsIgnoreCase("Rank Secondary")){
		     cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
		    		schoolDetailColumns, MySQLiteHelper.RANKSECONDARY + " = ? "+ latitude_not_null + longitude_not_null, new String[] { id }, null, null, MySQLiteHelper.COLUMN_ID);
	    }

	    if(primsec.equalsIgnoreCase("Rank Primary")){
		     cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
		    		schoolDetailColumns, MySQLiteHelper.RANKPRIMARY + " = ? "+ latitude_not_null + longitude_not_null, new String[] { id }, null, null, MySQLiteHelper.COLUMN_ID);
	    }
	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = cursorToCommentSchool(cursor);
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public <E> List<String> getSuburbByState() {

	  List<String> suburbList = (List<String>) new ArrayList<E>();
	  Cursor cursor = null;
	   
			cursor = database.query(true,MySQLiteHelper.GREAT_SCHOOLS,
					allbSuburbColumns, null, null, null, null, MySQLiteHelper.SUBURB, null);

			cursor.moveToFirst();

			if (cursor.moveToFirst()) {
			    do {
			    	suburbList.add(cursor.getString(0)+"-"+cursor.getString(1));
			    	//suburbList.add(cursor.getString(0) +"-"+cursor.getString(1));
			    } while (cursor.moveToNext());
			}

			// make sure to close the cursor
			//cursor.close();
			cursor.close();
		
	    return suburbList;
	  }

  public <E> List<String> getPostcode() {

	  List<String> postcodeList = (List<String>) new ArrayList<E>();

	    Cursor cursor = database.query(true,MySQLiteHelper.GREAT_SCHOOLS,
	    		postcodeColumns, null, null, null, null, MySQLiteHelper.POSTCODE, null);

	    cursor.moveToFirst();

	    if (cursor.moveToFirst()) {
	        do {
	        	postcodeList.add(cursor.getString(0));
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return postcodeList;
	  }

  public <E> List<String> getStates() {

	  List<String> stateList = (List<String>) new ArrayList<E>();

	    Cursor cursor = database.query(true,MySQLiteHelper.GREAT_SCHOOLS,
	    		stateColumns, null, null, null, null, MySQLiteHelper.STATE, null);

	    cursor.moveToFirst();

	    if (cursor.moveToFirst()) {
	        do {
	        	stateList.add(cursor.getString(0));
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return stateList;
	  }
  
  public <E> List<String> getSchoolsName() {

	  List<String> schoolNameList = (List<String>) new ArrayList<E>();

	    Cursor cursor = database.query(true,MySQLiteHelper.GREAT_SCHOOLS,
	    		allSchoolsNameColumns, null, null, null, null, MySQLiteHelper.SCHOOL_NAME, null);

	    cursor.moveToFirst();

	    if (cursor.moveToFirst()) {
	        do {
	        	schoolNameList.add(cursor.getString(0)+"-"+cursor.getString(1)+"-"+cursor.getString(2));
	        	//suburbList.add(cursor.getString(0) +"-"+cursor.getString(1));
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schoolNameList;
	  }
  public List<Schools> getSchoolByState(String state,String type) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.STATE + " = ? "+ latitude_not_null + longitude_not_null + " and " + MySQLiteHelper.PRIMARYSECONDARY + " = ?" , new String[] { state,type }, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school =cursorToComment(cursor);
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public List<Schools> getSchoolBySuburb(String suburb) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.SUBURB + " like ? "+ latitude_not_null + longitude_not_null, new String[] { suburb }, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = cursorToComment(cursor);
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public List<Schools> getSchools( ) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, null, null, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = cursorToComment(cursor);
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public List<Schools> getSchoolByPostCode(String postcode) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.POSTCODE + " = ? "+ latitude_not_null + longitude_not_null, new String[] { postcode }, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = cursorToComment(cursor);
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public List<Schools> getSchoolBySettingCriteria(String sector,String state, String primsec,String rating) {
	    List<Schools> schools = new ArrayList<Schools>();
	    
	    String rank = null;
	    if(sector.equalsIgnoreCase("Government")){
	    	sector="G";
	    }else if (sector.equalsIgnoreCase("Non Government")){
	    	sector="N";
	    }

	    if(primsec.equalsIgnoreCase("Secondary")){
	    	primsec="S";
	    	if(rating.equalsIgnoreCase("1")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 0 and 250" ;
	    	}else if(rating.equalsIgnoreCase("2")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 251 and 500" ;
	    	}else if(rating.equalsIgnoreCase("3")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 501 and 750" ;
	    	}else if(rating.equalsIgnoreCase("4")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 751 and 1000" ;
	    	}else if(rating.equalsIgnoreCase("5")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 1001 and 1250" ;
	    	}else if(rating.equalsIgnoreCase("6")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 1251 and 1500" ;
	    	}else if(rating.equalsIgnoreCase("7")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 1501 and 1750" ;
	    	}else if(rating.equalsIgnoreCase("8")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 1751 and 2000" ;
	    	}else if(rating.equalsIgnoreCase("9")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" BETWEEN 2001 and 2250" ;
	    	}else if(rating.equalsIgnoreCase("10")){
	    		rank = MySQLiteHelper.RANKSECONDARY +" >= 2251 " ;
	    	}
	    	
	    }else if (primsec.equalsIgnoreCase("Primary")){
	    	primsec="P";
	    	if(rating.equalsIgnoreCase("1")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 0 and 650" ;
	    	}else if(rating.equalsIgnoreCase("2")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 651 and 1300" ;
	    	}else if(rating.equalsIgnoreCase("3")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 1301 and 1950" ;
	    	}else if(rating.equalsIgnoreCase("4")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 1951 and 2600" ;
	    	}else if(rating.equalsIgnoreCase("5")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 2601 and 3250" ;
	    	}else if(rating.equalsIgnoreCase("6")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 3251 and 3900" ;
	    	}else if(rating.equalsIgnoreCase("7")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 3901 and 4550" ;
	    	}else if(rating.equalsIgnoreCase("8")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 4551 and 5200" ;
	    	}else if(rating.equalsIgnoreCase("9")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" BETWEEN 5201 and 5850" ;
	    	}else if(rating.equalsIgnoreCase("10")){
	    		rank = MySQLiteHelper.RANKPRIMARY +" >= 5851 " ;
	    	}
	    }

    
	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.SECTOR + " = ? "+ latitude_not_null + longitude_not_null +" and " + MySQLiteHelper.STATE + " = ? " + " and " + MySQLiteHelper.PRIMARYSECONDARY + " = ? " + " and " + rank , new String[] { sector,state,primsec }, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = cursorToComment(cursor);
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public List<Schools> getSchoolBySuburb(String suburb,String postCode) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.SUBURB + " = ? "+ latitude_not_null + longitude_not_null + " and " + MySQLiteHelper.POSTCODE + " = ?", new String[] { suburb,postCode }, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = cursorToComment(cursor);
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  public List<Schools> getSchoolNameBySuburb(String schoolName,String suburb,String postCode) {
	    List<Schools> schools = new ArrayList<Schools>();

	    try {
			Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
			    allColumns, MySQLiteHelper.SCHOOL_NAME + " = ? "+ latitude_not_null + longitude_not_null + " and " + MySQLiteHelper.SUBURB + " = ?" + " and " + MySQLiteHelper.POSTCODE + " = ?", new String[] { schoolName,suburb,postCode }, null, null, MySQLiteHelper.COLUMN_ID);

			cursor.moveToFirst();

			Schools school = null;
			if (cursor.moveToFirst()) {
			    do {
			    	school = cursorToComment(cursor);
			    	schools.add(school);
			    } while (cursor.moveToNext());
			}

			// make sure to close the cursor
			cursor.close();
		} catch (SQLiteException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG,"Error while fetching getSchoolNameBySuburb",e);
		}
	    return schools;
	  }  
  private Schools cursorToComment(Cursor cursor) {
	  	Schools schools = new Schools();
	  	schools.setId(cursor.getLong(0));
	  	schools.setSchoolName(cursor.getString(1));
	  	schools.setAverageTotal(cursor.getString(2));
	  	schools.setLatitude(cursor.getString(3));
	  	schools.setLongitude(cursor.getString(4));
	  	schools.setStreetAddress(cursor.getString(5));
	  	schools.setSuburb(cursor.getString(6));
	  	schools.setState(cursor.getString(7));
	  	schools.setPostcode(cursor.getString(8));
	  	schools.setPhone(cursor.getString(9));
	  	schools.setEmail(cursor.getString(10));
	  	schools.setFax(cursor.getString(11));
	  	schools.setWebsite(cursor.getString(12));
	  	schools.setSector(cursor.getString(13));
	  	schools.setPrimarySecondary(cursor.getString(14));
	  	schools.setMalefemale(cursor.getString(15));
	  	schools.setSinglecoed(cursor.getString(16));
	  	schools.setRankPrimary(cursor.getInt(17));
	  	schools.setRankSecondary(cursor.getInt(18));
	    return schools;
	  }

  private Schools cursorToCommentSchool(Cursor cursor) {
	  	Schools schools = new Schools();
	  	schools.setId(cursor.getLong(0));
	  	schools.setSchoolName(cursor.getString(1));
	  	schools.setAverageTotal(cursor.getString(2));
	  	schools.setLatitude(cursor.getString(3));
	  	schools.setLongitude(cursor.getString(4));
	  	schools.setStreetAddress(cursor.getString(5));
	  	schools.setSuburb(cursor.getString(6));
	  	schools.setState(cursor.getString(7));
	  	schools.setPostcode(cursor.getString(8));
	  	schools.setPhone(cursor.getString(9));
	  	schools.setEmail(cursor.getString(10));
	  	schools.setFax(cursor.getString(11));
	  	schools.setWebsite(cursor.getString(12));
	  	schools.setSector(cursor.getString(13));
	  	schools.setPrimarySecondary(cursor.getString(14));
	  	schools.setMalefemale(cursor.getString(15));
	  	schools.setSinglecoed(cursor.getString(16));
	  	schools.setRankPrimary(cursor.getInt(17));
	  	schools.setRankSecondary(cursor.getInt(18));
	  	schools.setReading(cursor.getString(19));
	  	schools.setGrampunc(cursor.getString(20));
	  	schools.setNumeracy(cursor.getString(21));
	  	schools.setWriting(cursor.getString(22));
	  	schools.setSpelling(cursor.getString(23));
	    return schools;
	  }
} 
