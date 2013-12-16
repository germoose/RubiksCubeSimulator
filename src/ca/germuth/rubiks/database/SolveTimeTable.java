package ca.germuth.rubiks.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SolveTimeTable extends SQLiteOpenHelper {

  public static final String TABLE_SOLVE_TIMES = "solve_times_";
  public static final String COLUMN_ID = "id";
  public static final String COLUMN_TIME = "time";
  //public static final String COLUMN_DATE = "date";
  public static final String COLUMN_PUZZLE = "puzzle";

  private static final String DATABASE_NAME = "commments.db";
  private static final int DATABASE_VERSION = 1;
  
  // Database creation sql statement
  private static final String CREATE_TABLE = 
		  "create table " + TABLE_SOLVE_TIMES + "(" + 
		  COLUMN_ID + " integer primary key autoincrement, " +
		  COLUMN_TIME + " BIGINT, " + 
		  //COLUMN_DATE + " DATETIME, " + 
		  COLUMN_PUZZLE + " INTEGER" + ");";

  public SolveTimeTable(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(SolveTimeTable.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOLVE_TIMES);
    onCreate(db);
  }

} 
