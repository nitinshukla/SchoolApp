package com.example.sqlitedatabaseapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommentsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String latitude_not_null = " and latitude is not null ";
  private String longitude_not_null = " and longitude is not null ";
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.AVERAGE_TOTAL,
      MySQLiteHelper.SCHOOL_NAME,
      MySQLiteHelper.SECTOR,
      MySQLiteHelper.STATE,
      MySQLiteHelper.SUBURB,
      MySQLiteHelper.LATITUDE,
      MySQLiteHelper.LONGITUDE,
      MySQLiteHelper.ICSEA,
      MySQLiteHelper.PRIMARYSECONDARY,
      MySQLiteHelper.EMAIL,
      MySQLiteHelper.FAX,
      MySQLiteHelper.PHONE,
      MySQLiteHelper.POSTCODE,
      MySQLiteHelper.STREETADDRESS};

  private String[] allbSuburbColumns = { MySQLiteHelper.SUBURB};

  public CommentsDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Schools createComment(Schools schools) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.AVERAGE_TOTAL, schools.getAverageTotal());
	    values.put(MySQLiteHelper.SCHOOL_NAME, schools.getSchoolName());
	    values.put(MySQLiteHelper.SECTOR, schools.getSector());
	    values.put(MySQLiteHelper.STATE, schools.getState());
	    values.put(MySQLiteHelper.SUBURB, schools.getSuburb());
	    long insertId = database.insert(MySQLiteHelper.GREAT_SCHOOLS, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Schools school = cursorToComment(cursor);
	    cursor.close();
	    return school;
	  }

  public void deleteComment(Schools schools) {
    long id = schools.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(MySQLiteHelper.GREAT_SCHOOLS, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<Schools> getSearchedSchool(String query) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.SCHOOL_NAME + " like ? " + latitude_not_null + longitude_not_null, new String[] {"%"+ query + "%" }, null, null, null);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = new Schools();
	        	school.setId(Integer.parseInt(cursor.getString(0)));
	        	school.setAverageTotal(cursor.getString(1));
	        	school.setSchoolName(cursor.getString(2));
	        	school.setSector(cursor.getString(3));
	        	school.setState(cursor.getString(4));
	        	school.setSuburb(cursor.getString(5));
	        	school.setLatitude(cursor.getString(6));
	        	school.setLongitude(cursor.getString(7));
	        	school.setIcsea(cursor.getString(8));
	        	school.setPrimarySecondary(cursor.getString(9));
	        	school.setEmail(cursor.getString(10));
	        	school.setFax(cursor.getString(11));
	        	school.setPhone(cursor.getString(12));
	        	school.setPostcode(cursor.getString(13));
	        	school.setStreetAddress(cursor.getString(14));
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    //Log.d("getSearchedSchool()", school.toString());
	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public List<Schools> getSchoolByState(String state) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.STATE + " = ? "+ latitude_not_null + longitude_not_null, new String[] { state }, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = new Schools();
	        	school.setId(Integer.parseInt(cursor.getString(0)));
	        	school.setAverageTotal(cursor.getString(1));
	        	school.setSchoolName(cursor.getString(2));
	        	school.setSector(cursor.getString(3));
	        	school.setState(cursor.getString(4));
	        	school.setSuburb(cursor.getString(5));
	        	school.setLatitude(cursor.getString(6));
	        	school.setLongitude(cursor.getString(7));
	        	school.setIcsea(cursor.getString(8));
	        	school.setPrimarySecondary(cursor.getString(9));
	        	school.setEmail(cursor.getString(10));
	        	school.setFax(cursor.getString(11));
	        	school.setPhone(cursor.getString(12));
	        	school.setPostcode(cursor.getString(13));
	        	school.setStreetAddress(cursor.getString(14));
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    //Log.d("getSearchedSchool()", school.toString());
	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  public <E> List<String> getSuburbByState(String state) {
	    //List<Schools> schools = new ArrayList<Schools>();
	  List<String> suburbList = (List<String>) new ArrayList<E>();

	    Cursor cursor = database.query(true,MySQLiteHelper.GREAT_SCHOOLS,
	    		allbSuburbColumns, MySQLiteHelper.STATE + " = ? "+ latitude_not_null + longitude_not_null, new String[] { state }, null, null, MySQLiteHelper.COLUMN_ID, null);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	//school = new Schools();
	        	suburbList.add(cursor.getString(0));
	        } while (cursor.moveToNext());
	    }

	    //Log.d("getSearchedSchool()", school.toString());
	    // make sure to close the cursor
	    cursor.close();
	    return suburbList;
	  }
  
  public List<Schools> getSchoolByState(String state,String type) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.STATE + " = ? "+ latitude_not_null + longitude_not_null + " and " + MySQLiteHelper.PRIMARYSECONDARY + " = ?" , new String[] { state,type }, null, null, MySQLiteHelper.COLUMN_ID);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = new Schools();
	        	school.setId(Integer.parseInt(cursor.getString(0)));
	        	school.setAverageTotal(cursor.getString(1));
	        	school.setSchoolName(cursor.getString(2));
	        	school.setSector(cursor.getString(3));
	        	school.setState(cursor.getString(4));
	        	school.setSuburb(cursor.getString(5));
	        	school.setLatitude(cursor.getString(6));
	        	school.setLongitude(cursor.getString(7));
	        	school.setIcsea(cursor.getString(8));
	        	school.setPrimarySecondary(cursor.getString(9));
	        	school.setEmail(cursor.getString(10));
	        	school.setFax(cursor.getString(11));
	        	school.setPhone(cursor.getString(12));
	        	school.setPostcode(cursor.getString(13));
	        	school.setStreetAddress(cursor.getString(14));
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    //Log.d("getSearchedSchool()", school.toString());
	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  public List<Schools> getSchoolByEnteredText(String state) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.STATE + " like ? "+ latitude_not_null + longitude_not_null, new String[] { state }, null, null, MySQLiteHelper.COLUMN_ID);
	    System.out.println("cursor count====>"+cursor.getCount());
	    
	    if(cursor.getCount() == 0){
	    cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
		        allColumns, MySQLiteHelper.SUBURB + " like ? "+ latitude_not_null + longitude_not_null, new String[] { state }, null, null, MySQLiteHelper.COLUMN_ID);
	    }
	    
	    if(cursor.getCount() == 0){
		    cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
			        allColumns, MySQLiteHelper.SCHOOL_NAME + " like ? "+ latitude_not_null + longitude_not_null, new String[] { state }, null, null, MySQLiteHelper.COLUMN_ID);
		    }
	    cursor.moveToFirst();
	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = new Schools();
	        	school.setId(Integer.parseInt(cursor.getString(0)));
	        	school.setAverageTotal(cursor.getString(1));
	        	school.setSchoolName(cursor.getString(2));
	        	school.setSector(cursor.getString(3));
	        	school.setState(cursor.getString(4));
	        	school.setSuburb(cursor.getString(5));
	        	school.setLatitude(cursor.getString(6));
	        	school.setLongitude(cursor.getString(7));
	        	school.setIcsea(cursor.getString(8));
	        	school.setPrimarySecondary(cursor.getString(9));
	        	school.setEmail(cursor.getString(10));
	        	school.setFax(cursor.getString(11));
	        	school.setPhone(cursor.getString(12));
	        	school.setPostcode(cursor.getString(13));
	        	school.setStreetAddress(cursor.getString(14));
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    //Log.d("getSearchedSchool()", school.toString());
	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public List<Schools> getSchoolByStateAndLocality(String state,String locality) {
	    List<Schools> schools = new ArrayList<Schools>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, MySQLiteHelper.STATE + " = ? and " + MySQLiteHelper.SUBURB + " = ? " + latitude_not_null + longitude_not_null, new String[] { state,locality }, null, null, MySQLiteHelper.SCHOOL_NAME);

	    cursor.moveToFirst();

	    Schools school = null;
	    if (cursor.moveToFirst()) {
	        do {
	        	school = new Schools();
	        	school.setId(Integer.parseInt(cursor.getString(0)));
	        	school.setAverageTotal(cursor.getString(1));
	        	school.setSchoolName(cursor.getString(2));
	        	school.setSector(cursor.getString(3));
	        	school.setState(cursor.getString(4));
	        	school.setSuburb(cursor.getString(5));
	        	school.setLatitude(cursor.getString(6));
	        	school.setLongitude(cursor.getString(7));
	        	school.setIcsea(cursor.getString(8));
	        	school.setPrimarySecondary(cursor.getString(9));
	        	school.setEmail(cursor.getString(10));
	        	school.setFax(cursor.getString(11));
	        	school.setPhone(cursor.getString(12));
	        	school.setPostcode(cursor.getString(13));
	        	school.setStreetAddress(cursor.getString(14));
	        	schools.add(school);
	        } while (cursor.moveToNext());
	    }

	    //Log.d("getSearchedSchool()", school.toString());
	    // make sure to close the cursor
	    cursor.close();
	    return schools;
	  }
  
  public List<Schools> getAllComments() {
    List<Schools> schools = new ArrayList<Schools>();

    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
/*    while (!cursor.isAfterLast()) {
    	Schools school = cursorToComment(cursor);
    	schools.add(school);
      cursor.moveToNext();
    }*/
    Schools school = null;
    if (cursor.moveToFirst()) {
        do {
        	school = new Schools();
        	school.setId(Integer.parseInt(cursor.getString(0)));
        	school.setAverageTotal(cursor.getString(1));
        	school.setSchoolName(cursor.getString(2));
        	school.setSector(cursor.getString(3));
        	school.setState(cursor.getString(4));
        	school.setSuburb(cursor.getString(5));
        	school.setLatitude(cursor.getString(6));
        	school.setLongitude(cursor.getString(7));
        	school.setIcsea(cursor.getString(8));
        	school.setPrimarySecondary(cursor.getString(9));
        	school.setEmail(cursor.getString(10));
        	school.setFax(cursor.getString(11));
        	school.setPhone(cursor.getString(12));
        	school.setPostcode(cursor.getString(13));
        	school.setStreetAddress(cursor.getString(14));
            // Add book to books
        	schools.add(school);
        } while (cursor.moveToNext());
    }

   // Log.d("getAllBooks()", books.toString());
    // make sure to close the cursor
    cursor.close();
    return schools;
  }
/*
  public List<Comment> getAllComments() {
	    List<Comment> comments = new ArrayList<Comment>();

	    Cursor cursor = database.query(MySQLiteHelper.GREAT_SCHOOLS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Comment comment = cursorToComment(cursor);
	      comments.add(comment);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return comments;
	  }
  */
/*  private Comment cursorToComment(Cursor cursor) {
    Comment comment = new Comment();
    comment.setId(cursor.getLong(0));
    comment.setComment(cursor.getString(1));
    return comment;
  }
  */
  private Schools cursorToComment(Cursor cursor) {
	  	Schools schools = new Schools();
	  	schools.setId(cursor.getLong(0));
	  	schools.setAverageTotal(cursor.getString(1));
	  	schools.setSchoolName(cursor.getString(2));
	  	schools.setSector(cursor.getString(3));
	  	schools.setState(cursor.getString(4));
	  	schools.setSuburb(cursor.getString(5));
	  	schools.setLatitude(cursor.getString(6));
	  	schools.setLongitude(cursor.getString(7));
	  	schools.setIcsea(cursor.getString(8));
	  	schools.setPrimarySecondary(cursor.getString(9));
	  	schools.setEmail(cursor.getString(10));
	  	schools.setFax(cursor.getString(11));
	  	schools.setPhone(cursor.getString(12));
	  	schools.setPostcode(cursor.getString(13));
	  	schools.setStreetAddress(cursor.getString(14));
	    return schools;
	  }
} 
