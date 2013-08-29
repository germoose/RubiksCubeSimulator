package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SolveTimeTable extends SQLiteOpenHelper {

  public static final String TABLE_SOLVE_TIMES = "times";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TIME = "time";

  private static final String DATABASE_NAME = "commments.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_SOLVE_TIMES + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_TIME
      + " text not null);";

  public SolveTimeTable(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
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