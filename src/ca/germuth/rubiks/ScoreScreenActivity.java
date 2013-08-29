package ca.germuth.rubiks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import ca.germuth.rubiks.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import database.SolveTime;

public class ScoreScreenActivity extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);

			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);

			setContentView(R.layout.old_activity_score_screen);

			File solvesFile = new File(this.getFilesDir(), "data.txt");
			solvesFile.createNewFile();
			
			System.out.println( solvesFile.canWrite() );
			SharedPreferences shared = this.getSharedPreferences("Records", Context.MODE_PRIVATE);
			Editor editor = shared.edit();
			
			//append this solve to file
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(solvesFile.getAbsolutePath(), true)));
			
			out.println( getIntent().getIntExtra(
					"ca.germuth.rubiks.Time", 0) );
			out.close();

			//MainActivity.data.open();
			//List<SolveTime> times = MainActivity.data.getAllSolveTimes();

			Scanner solveScanner = new Scanner( solvesFile ); 
			
			ArrayList<Integer> last50Solves = new ArrayList<Integer>();
			for(int i = 0; i < 50; i++){
				if( solveScanner.hasNext()){
					int time = Integer.parseInt( solveScanner.next() );
					last50Solves.add(time);
				} else{
					break;
				}
			}
			int solves = last50Solves.size();
			
			int[] averages = new int[]{
					1, 5, 12, 50
			};
			int[] theAverageTimes = new int[4];
			
			for (int i = 0; i < averages.length; i++) {
				int j = averages[i];
				if (solves >= j) {
					int avOfj = getAverage(last50Solves, j);
					
					theAverageTimes[i] = avOfj;
					
					int record = shared.getInt(j+"", 0);

					if (avOfj > record) {
						editor.putInt(j+"", avOfj);
						
						int finalIndex = solves - j;
						long total = 0l; 
						
						for(int k = 0; k < j; k++){
							int solve = last50Solves.get( k );
							editor.putInt(j + "_" + k, solve);
						}
					}
				}
			}
			
			TextView av5t = (TextView) findViewById(R.id.av5t);
			av5t.setText( Utils.milliToTime( theAverageTimes[1] ) );
			
			TextView av12t = (TextView) findViewById(R.id.av12t);
			av12t.setText( Utils.milliToTime( theAverageTimes[2] ) );
			
			TextView av50t = (TextView) findViewById(R.id.av50t);
			av50t.setText( Utils.milliToTime( theAverageTimes[3] ) );
			
			editor.commit();
			
			LinearLayout av5 = (LinearLayout) findViewById(R.id.av5actual);
			LinearLayout av12 = (LinearLayout) findViewById(R.id.av12actual);
			LinearLayout av50 = (LinearLayout) findViewById(R.id.av50actual);
			
			LinearLayout[] lists = new LinearLayout[]{
					av5, av12, av50
			};
			int[] number = new int[]{
					5, 12, 50
			};
			
			for(int i = 0; i < lists.length; i++){
				LinearLayout list = lists[i];
				
				if (solves >= number[i]) {
					for (int j = solves - 1; j > solves - number[i]; j--) {
						TextView c = new TextView(this);
						c.setTextColor(Color.BLACK);
						c.setText(Utils.milliToTime(last50Solves.get(j)));
						list.addView(c);
					}
				}
			}	
			
			TextView PBav5t = (TextView) findViewById(R.id.PBav5t);
			PBav5t.setText( Utils.milliToTime( shared.getInt("5", 0) ) );
			
			TextView PBav12t = (TextView) findViewById(R.id.PBav12t);
			PBav12t.setText( Utils.milliToTime( shared.getInt("12", 0) ) );
			
			TextView PBav50t = (TextView) findViewById(R.id.PBav50t);
			PBav50t.setText( Utils.milliToTime( shared.getInt("50", 0) ) );
			
			LinearLayout PBav5 = (LinearLayout) findViewById(R.id.PBav5actual);
			LinearLayout PBav12 = (LinearLayout) findViewById(R.id.PBav12actual);
			LinearLayout PBav50 = (LinearLayout) findViewById(R.id.PBav50actual);
			
			lists = new LinearLayout[]{
					PBav5, PBav12, PBav50
			};
			
			for(int i = 0; i < lists.length; i++){
				LinearLayout list = lists[i];

					for (int j = 0; j < number[i]; j++) {
						TextView c = new TextView(this);
						c.setTextColor(Color.BLACK);
						int solve = shared.getInt(number[i] + "_" + j, 0);
						c.setText( Utils.milliToTime(solve) );
						list.addView(c);
					}
			}	
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Button closeButton = (Button) findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ScoreScreenActivity.this.finish();
			}
		});

		Button leaderboardButton = (Button) findViewById(R.id.leaderboardButton);
		leaderboardButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast toast = Toast.makeText(ScoreScreenActivity.this,
						"Feature currently not available",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		});

		TextView yourTime = (TextView) findViewById(R.id.yourTime);
		int time = getIntent().getIntExtra("ca.germuth.rubiks.Time", 0);
		yourTime.setText( Utils.milliToTime(time) );

	}
	
	private int getAverage(ArrayList<Integer> solves, int amount){
		
		int finalIndex = solves.size() - amount;
		long total = 0l; 
		
		for(int i = solves.size() - 1; i >= finalIndex; i--){
			total += solves.get(i) ;
		}
		
		int average = (int) (total / amount);
		return average;
	}

	@Override
	protected void onResume() {
		if( MainActivity.data != null){
			MainActivity.data.open();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		MainActivity.data.close();
		super.onPause();
	}
}
