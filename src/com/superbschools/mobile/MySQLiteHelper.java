package com.superbschools.mobile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.superbschools.mobile/databases/";
	 
    private static String DB_NAME = "AustralianSchools";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
    

  public static final String GREAT_SCHOOLS = "Schools";
  public static final String COLUMN_ID = "_id";

  public static final String AVERAGE_TOTAL = "TOTALAVG";
  public static final String SCHOOL_NAME = "school_name";
  public static final String SECTOR = "sector";
  public static final String SUBURB = "suburb";
  public static final String STATE = "state";
  public static final String LONGITUDE = "long";
  public static final String LATITUDE = "lat";
  public static final String REGION = "region";
  public static final String ICSEA = "icsea";
  public static final String PRIMARYSECONDARY = "primSec";
  public static final String STREETADDRESS = "STREETNAME";
  public static final String FAX = "fax";
  public static final String PHONE = "phone";
  public static final String EMAIL = "email";
  public static final String POSTCODE = "postcode";
  public static final String WEBSITE = "WEBSITE";
  public static final String MALEFEMALE = "MALEFEMALE";
  public static final String SINGLECOED = "SINGLECOED";
  public static final String RANKPRIMARY = "RANKPRIMARY";
  public static final String RANKSECONDARY = "RANKSECONDARY";
  public static final String READING = "READING";
  public static final String GRAMPUNC = "GRAMPUNC";
  public static final String NUMERACY = "NUMERACY";
  public static final String WRITING = "WRITING";
  public static final String SPELLING = "SPELLING";


  private static final String DATABASE_NAME = "superbschools.db";
  private static final int DATABASE_VERSION = 1;

   /**
   * Constructor
   * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
   * @param context
   */
  public MySQLiteHelper(Context context) {

  	super(context, DB_NAME, null, 1);
      this.myContext = context;
  }
  /**
   * Creates a empty database on the system and rewrites it with your own database.
   * */
  public void createDataBase() throws IOException{

  	boolean dbExist = checkDataBase();
  	SQLiteDatabase db_Read = null;
  	if(!dbExist){
  		//do nothing - database already exist
  	}else{

  		//By calling this method and empty database will be created into the default system path
             //of your application so we are gonna be able to overwrite that database with our database.
  		db_Read = this.getReadableDatabase();
  		db_Read.close();
      	try {

  			copyDataBase();

  		} catch (IOException e) {

      		throw new Error("Error copying database");

      	}
  	}

  }
  
  /**
   * Check if the database already exist to avoid re-copying the file each time you open the application.
   * @return true if it exists, false if it doesn't
   */
  private boolean checkDataBase(){

  	SQLiteDatabase checkDB = null;

  	try{
  		String myPath = DB_PATH + DB_NAME;
  		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

  	}catch(SQLiteException e){

  		e.printStackTrace();
  		//database does't exist yet.

  	}

  	if(checkDB != null){

  		checkDB.close();

  	}

  	return checkDB != null ? true : false;
  }
  
  /**
   * Copies your database from your local assets-folder to the just created empty database in the
   * system folder, from where it can be accessed and handled.
   * This is done by transfering bytestream.
   * */
  private void copyDataBase() throws IOException{

  	//Open your local db as the input stream
  	InputStream myInput = myContext.getAssets().open(DB_NAME);

  	// Path to the just created empty db
  	String outFileName = DB_PATH + DB_NAME;

  	//Open the empty db as the output stream
  	OutputStream myOutput = new FileOutputStream(outFileName);

  	//transfer bytes from the inputfile to the outputfile
  	byte[] buffer = new byte[1024];
  	int length;
  	while ((length = myInput.read(buffer))>0){
  		myOutput.write(buffer, 0, length);
  	}

  	//Close the streams
  	myOutput.flush();
  	myOutput.close();
  	myInput.close();

  }
  
  public void openDataBase() throws SQLException{
	  
  	//Open the database
      String myPath = DB_PATH + DB_NAME;
  	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

  }
  
  @Override
	public synchronized void close() {

  	    if(myDataBase != null)
  		    myDataBase.close();

  	    super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	} 
} 
