package ca.germuth.rubiks.activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import ca.germuth.rubiks.R;
import ca.germuth.rubiks.R.id;
import ca.germuth.rubiks.R.layout;
import ca.germuth.rubiks.database.SolveTime;
import ca.germuth.rubiks.util.MusicManager;
import ca.germuth.rubiks.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreScreenActivity extends Activity{
	
	private boolean continueMusic = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);

			continueMusic = false; 
			
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);

			setContentView(R.layout.activity_score_screen);

			File solvesFile = new File(this.getFilesDir(), "data.txt");
			solvesFile.createNewFile();
			
			SharedPreferences shared = this.getSharedPreferences("Records", Context.MODE_PRIVATE);
			Editor editor = shared.edit();
			
			//append this solve to file
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(solvesFile.getAbsolutePath(), true)));
			
			out.println( getIntent().getIntExtra(
					"ca.germuth.rubiks.Time", 0) );
			out.close();

			Scanner solveScanner = new Scanner(new BufferedReader(new FileReader( solvesFile ))); 
			
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

					if (avOfj < record || record == 0) {
						editor.putInt(j+"", avOfj);
						
						int finalIndex = solves - j;
						
						for(int k = solves - 1; k < solves - j; k--){
							int solve = last50Solves.get( k );
							editor.putInt(j + "_" + k, solve);
						}
					}
				}
			}
			TextView av1t = (TextView) findViewById(R.id.av_of_1);
			av1t.setText( Utils.milliToTime( getIntent().getIntExtra("ca.germuth.rubiks.Time", 0) ) );
			
			TextView av5t = (TextView) findViewById(R.id.av_of_5);
			av5t.setText( Utils.milliToTime( theAverageTimes[1] ) );
			
			TextView av12t = (TextView) findViewById(R.id.av_of_12);
			av12t.setText( Utils.milliToTime( theAverageTimes[2] ) );
			
			TextView av50t = (TextView) findViewById(R.id.av_of_50);
			av50t.setText( Utils.milliToTime( theAverageTimes[3] ) );
			
			editor.commit();
			
			TextView PBav1t = (TextView) findViewById(R.id.pb_av_of_1);
			PBav1t.setText( Utils.milliToTime( shared.getInt("1", 0) ) );
			
			TextView PBav5t = (TextView) findViewById(R.id.pb_av_of_5);
			PBav5t.setText( Utils.milliToTime( shared.getInt("5", 0) ) );
			
			TextView PBav12t = (TextView) findViewById(R.id.pb_av_of_12);
			PBav12t.setText( Utils.milliToTime( shared.getInt("12", 0) ) );
			
			TextView PBav50t = (TextView) findViewById(R.id.pb_av_of_50);
			PBav50t.setText( Utils.milliToTime( shared.getInt("50", 0) ) );

			TextView yourTime = (TextView) findViewById(R.id.theTime);
			int time = getIntent().getIntExtra("ca.germuth.rubiks.Time", 0);
			yourTime.setText( Utils.milliToTime(time) );
			
			Button button = (Button) findViewById(R.id.ReplayButton);
			button.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					
				}
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Button closeButton = (Button) findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ScoreScreenActivity.this.continueMusic = true;
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
		
		Button replay = (Button) findViewById(R.id.ReplayButton);
		replay.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(ScoreScreenActivity.this, ReplayActivity.class);
				continueMusic = true;
				ScoreScreenActivity.this.startActivity(myIntent);
			}
		});

		TextView yourTime = (TextView) findViewById(R.id.theTime);
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
	
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if( keyCode == KeyEvent.KEYCODE_BACK){
			continueMusic = true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	  protected void onResume() {
	    super.onResume();
	    continueMusic = false;
	    MusicManager.start(this, MusicManager.MUSIC_MENU);
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    if (!continueMusic) {
	    	MusicManager.pause();
	    }
	  }
}
