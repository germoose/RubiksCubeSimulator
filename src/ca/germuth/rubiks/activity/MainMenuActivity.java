package ca.germuth.rubiks.activity;

import ca.germuth.rubiks.R;
import ca.germuth.rubiks.R.drawable;
import ca.germuth.rubiks.R.id;
import ca.germuth.rubiks.R.layout;
import ca.germuth.rubiks.util.MusicManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainMenuActivity extends Activity{
	private boolean continueMusic = false;
	private AnimationDrawable ad;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main_menu);

		continueMusic = false;
		MusicManager.start(this, MusicManager.MUSIC_MENU);
		
		ImageView animatedCube = (ImageView) findViewById(R.id.cubeGIF);
		animatedCube.setBackgroundResource(R.drawable.cube_animation);
		ad = (AnimationDrawable) animatedCube.getBackground();
		
		Button gameButton = (Button) findViewById(R.id.gameButton);
		gameButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(MainMenuActivity.this, GameActivity3d.class);
				myIntent.putExtra("cube", 2);
				continueMusic = false;
				MainMenuActivity.this.startActivity(myIntent);
			}
		});
		
		Button scoreScreenButton = (Button) findViewById(R.id.scoreButton);
		scoreScreenButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(MainMenuActivity.this, ScoreScreenActivity.class);
				continueMusic = true;
				MainMenuActivity.this.startActivity(myIntent);
			}
		});
		
		Button settingsButton = (Button) findViewById(R.id.settingsButton);
		settingsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(MainMenuActivity.this, SettingsActivity.class);
				continueMusic = false;
				MainMenuActivity.this.startActivity(myIntent);
			}
		});
		
		Button quitButton = (Button) findViewById(R.id.quitButton);
		quitButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				MainMenuActivity.this.finish();
				System.exit(0);
				continueMusic = false;
			}
		});
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
		ad.start();
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
