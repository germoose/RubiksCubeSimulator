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
	  private String[] allColumns = { 
			  SolveTimeTable.COLUMN_ID,
			  SolveTimeTable.COLUMN_TIME,
			  //SolveTimeTable.COLUMN_DATE,
			  SolveTimeTable.COLUMN_PUZZLE};

	  public DatabaseAccessor(Context context) {
	    dbHelper = new SolveTimeTable(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  public void addSolve(long time, int puzzle){
		  ContentValues values = new ContentValues();
		  values.put( SolveTimeTable.COLUMN_TIME, time);
		  values.put( SolveTimeTable.COLUMN_PUZZLE, puzzle);
		  
		  long rowID = this.database.insert( SolveTimeTable.TABLE_SOLVE_TIMES, null, values);
	  }

	  public SolveTime getFastestSolve(int puzzle){
		  SolveTime fastest = null;
		  
		
		  Cursor cursor = this.database.query(
				  SolveTimeTable.TABLE_SOLVE_TIMES, null, 
				  SolveTimeTable.COLUMN_PUZZLE + "=" + puzzle, new String[]{SolveTimeTable.COLUMN_PUZZLE}, 
				  null, null, SolveTimeTable.COLUMN_TIME + " ASC", "1");
		  
		  cursor.moveToFirst();
		  
		  while( !cursor.isAfterLast() ){
			  long rowID = cursor.getLong( cursor.getColumnIndex( SolveTimeTable.COLUMN_ID ) );
			  long solveTime = cursor.getLong( cursor.getColumnIndex( SolveTimeTable.COLUMN_TIME ) );
			  int puzz = cursor.getInt( cursor.getColumnIndex( SolveTimeTable.COLUMN_PUZZLE) );
			  fastest = new SolveTime(rowID, solveTime, puzz);
		  }
		  
		  return fastest;
		 
	  }
//	  public List<SolveTime> getAllSolveTimes() {
//	    List<SolveTime> times = new ArrayList<SolveTime>();
//
//	    Cursor cursor = database.query(SolveTimeTable.TABLE_SOLVE_TIMES,
//	        allColumns, null, null, null, null, null);
//
//	    cursor.moveToFirst();
//	    while (!cursor.isAfterLast()) {
//	      SolveTime time = cursorToSolveTime(cursor);
//	      times.add(time);
//	      cursor.moveToNext();
//	    }
//	    // Make sure to close the cursor
//	    cursor.close();
//	    return times;
//	  }
}
