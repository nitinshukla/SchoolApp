package com.example.sqlitedatabaseapp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.example.sqlitedatabaseapp/databases/";
	 
    private static String DB_NAME = "AustralianSchools";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
    
  //public static final String TABLE_COMMENTS = "comments";
  public static final String GREAT_SCHOOLS = "Schools";
  public static final String COLUMN_ID = "_id";
  //public static final String COLUMN_COMMENT = "comment";
  public static final String AVERAGE_TOTAL = "average_total";
  public static final String SCHOOL_NAME = "school_name";
  public static final String SECTOR = "sector";
  public static final String SUBURB = "suburb";
  public static final String STATE = "state";

  //private static final String DATABASE_NAME = "commments.db";
  private static final String DATABASE_NAME = "greatSchools.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
 /* private static final String DATABASE_CREATE = "create table "
      + TABLE_COMMENTS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_COMMENT
      + " text not null);";
*/
  // Database creation sql statement
  /*private static final String DATABASE_CREATE = "create table "
      + GREAT_SCHOOLS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + AVERAGE_TOTAL
      + " DECIMAL(10,5) not null , " + SCHOOL_NAME
      + " text not null , " + SECTOR
      + " int not null , " + SUBURB
      + " text not null , " + STATE
      + " text not null );";
  */
 /* public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
*/
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

  	if(!dbExist){
  		//do nothing - database already exist
  	}else{

  		//By calling this method and empty database will be created into the default system path
             //of your application so we are gonna be able to overwrite that database with our database.
      	this.getReadableDatabase();

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
	/*
  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }
  */
/*
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
    onCreate(db);
  }
*/
  /*
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + GREAT_SCHOOLS);
    onCreate(db);
  }*/
  
} 