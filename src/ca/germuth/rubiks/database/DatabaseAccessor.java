package ca.germuth.rubiks.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAccessor {
	// Database fields
	  private SQLiteDatabase database;
	  private SolveTimeTable dbHelper;
	  private String[] allColumns = { SolveTimeTable.COLUMN_ID,
	      SolveTimeTable.COLUMN_TIME };

	  public DatabaseAccessor(Context context) {
	    dbHelper = new SolveTimeTable(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public SolveTime createSolveTime(String time) {
	    ContentValues values = new ContentValues();
	    values.put(SolveTimeTable.COLUMN_TIME, time);
	    long insertId = database.insert(SolveTimeTable.TABLE_SOLVE_TIMES, null,
	        values);
	    Cursor cursor = database.query(SolveTimeTable.TABLE_SOLVE_TIMES,
	        allColumns, SolveTimeTable.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    SolveTime newSolveTime = cursorToSolveTime(cursor);
	    cursor.close();
	    return newSolveTime;
	  }

	  public void deleteSolveTime(SolveTime time) {
	    long id = time.getId();
	    System.out.println("SolveTime deleted with id: " + id);
	    database.delete(SolveTimeTable.TABLE_SOLVE_TIMES, SolveTimeTable.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<SolveTime> getAllSolveTimes() {
	    List<SolveTime> times = new ArrayList<SolveTime>();

	    Cursor cursor = database.query(SolveTimeTable.TABLE_SOLVE_TIMES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      SolveTime time = cursorToSolveTime(cursor);
	      times.add(time);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return times;
	  }

	  private SolveTime cursorToSolveTime(Cursor cursor) {
	    SolveTime time = new SolveTime();
	    time.setId(cursor.getInt(0));
	    time.setTime(cursor.getString(1));
	    return time;
	  }
}
