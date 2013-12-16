package ca.germuth.rubiks.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ca.germuth.rubiks.R;
import ca.germuth.rubiks.R.layout;
import ca.germuth.rubiks.cube.GenericCube;
import ca.germuth.rubiks.cubeViews.HiGamesView;
import ca.germuth.rubiks.database.DatabaseAccessor;
import ca.germuth.rubiks.listener.MoveListener;
import ca.germuth.rubiks.listener.ShakeListener;
import ca.germuth.rubiks.openGL.MyGLSurfaceView;
import ca.germuth.rubiks.util.Chronometer;
import ca.germuth.rubiks.util.MusicManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity3d extends Activity {

	private MyGLSurfaceView mView;
	private Chronometer chrono;
	private boolean continueMusic;
	private long startTime;
	private Handler handler;
	private TextView inspectionTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity.
		//mView = new MyGLSurfaceView(this);
		//setContentView(mView);
		
		setContentView(R.layout.activity_game_screen_3d);
		
		this.chrono = (Chronometer)findViewById(R.id.chronometer3d);
		this.mView = (MyGLSurfaceView) findViewById( R.id.threedDrawView );
		
		this.inspectionTime = (TextView) findViewById(R.id.inspectionTime);
		this.mView.setTextField( this.inspectionTime );
		
		Bundle extras = getIntent().getExtras();
		int size = 3;
		if (extras != null) {
			size = extras.getInt("cube");
		}

		this.mView.initializeRenderer(size);

		this.mView.setSolvedHandler(new Handler() {
			public void handleMessage(Message msg) {
				long currentTime = System.nanoTime();

				GameActivity3d.this.mView.setSolving(false);
				GameActivity3d.this.chrono.stop();

				long duration = currentTime - GameActivity3d.this.startTime;
				duration /= 10000;

				int milli = (int) ((double) duration / 100.0);

				Intent myIntent = new Intent(GameActivity3d.this,
						ScoreScreenActivity.class);
				myIntent.putExtra("ca.germuth.rubiks.Time", milli); // Optional
																	// parameters
				continueMusic = true;
				GameActivity3d.this.startActivity(myIntent);
			}
		});

		handler = new Handler() {
			public void handleMessage(Message msg) {
				final int time = msg.getData().getInt("inspection_time");
			
				if ( time == 0 ) {
					GameActivity3d.this.runOnUiThread(new Runnable(){
						public void run(){
							GameActivity3d.this.inspectionTime.setText("");
							setButtons(true);
							GameActivity3d.this.startTime = System.nanoTime();
							GameActivity3d.this.mView.setSolving(true);
							GameActivity3d.this.chrono.start();
							MoveListener.startRecording();
						}
					});
				} else {
					GameActivity3d.this.runOnUiThread(new Runnable(){
						public void run(){
							GameActivity3d.this.inspectionTime.setText( time + "" );
						}
					});
				}
			}
		};

		MusicManager.start(this, MusicManager.MUSIC_GAME);

		setUpButtons();

		setUpShakeListener();

	}
	
	private void setUpShakeListener(){
		ShakeListener mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
			@Override
			public void onShake() {
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(GameActivity3d.this);
				int length = settings.getInt("scramble_length", 25);
				mView.getmCube().scrambleCube(length);
				mView.requestRender();
				
				setButtons(false);
				
				new Thread(){
					private MyGLSurfaceView dv;
					public Thread initialise(MyGLSurfaceView dv){;
						this.dv = dv;
						return this;
					}
					public void run(){
						SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(GameActivity3d.this);
						int inspection = settings.getInt("inspection_time", 15);
						
						Message msg = new Message();
						Bundle bun = new Bundle();
						
						while(inspection > -1){
							bun.putInt("inspection_time", inspection);
							msg.setData(bun);
							GameActivity3d.this.handler.dispatchMessage(msg);
							
							inspection--;
							
							//this.dv.postInvalidate();
							
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						GameActivity3d.this.handler.sendEmptyMessage(0);
					}
				}.initialise(mView).start();
				
			}
		});
	}

	/**
	 * Iterate through all buttons and add a new listener which applies their
	 * move, based on the text in each button
	 */
	private void setUpButtons() {
		MoveListener.setView(this.mView);
		MoveListener.setChronometer(this.chrono);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout);
		int children = rl.getChildCount();
		for (int i = 0; i < children; i++) {
			try {
				View v = rl.getChildAt(i);
				if (v instanceof Button) {
					Button b = (Button) v;
					String methodName = b.getText() + "Turn";
					methodName = methodName.replace("'", "Prime");
					MoveListener.buttonToTurn.put(b,
							GenericCube.class.getMethod(methodName, null));
					b.setOnClickListener(new MoveListener(b));
				}
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
				continue;
			}
		}
	}
	
	private void setButtons(Boolean enabled){
		Button ubutton = (Button) findViewById(R.id.uButton);
        ubutton.setEnabled(enabled);
        
        Button Rbutton = (Button) findViewById(R.id.RButton);
        Rbutton.setEnabled(enabled);
        
        Button RPbutton = (Button) findViewById(R.id.RPButton);
        RPbutton.setEnabled(enabled);
        
        Button Ubutton = (Button) findViewById(R.id.UButton);
        Ubutton.setEnabled(enabled);
        
        Button UPbutton = (Button) findViewById(R.id.UPButton);
        UPbutton.setEnabled(enabled);
       
        Button Lbutton = (Button) findViewById(R.id.LButton);
        Lbutton.setEnabled(enabled);
        
        Button LPbutton = (Button) findViewById(R.id.LPButton);
        LPbutton.setEnabled(enabled);
        
        Button Fbutton = (Button) findViewById(R.id.FButton);
        Fbutton.setEnabled(enabled);
        
        Button FPbutton = (Button) findViewById(R.id.FPButton);
        FPbutton.setEnabled(enabled);
        
      
        Button rbutton = (Button) findViewById(R.id.rButton);
        rbutton.setEnabled(enabled);
        
        Button rPbutton = (Button) findViewById(R.id.rPButton);
        rPbutton.setEnabled(enabled);
        
        Button uPbutton = (Button) findViewById(R.id.uPButton);
        uPbutton.setEnabled(enabled);
       
        Button lbutton = (Button) findViewById(R.id.lButton);
        lbutton.setEnabled(enabled);
        
        Button lPbutton = (Button) findViewById(R.id.lPButton);
        lPbutton.setEnabled(enabled);
        
        Button fbutton = (Button) findViewById(R.id.fButton);
        fbutton.setEnabled(enabled);
        
        Button fPbutton = (Button) findViewById(R.id.fPButton);
        fPbutton.setEnabled(enabled);
        
        Button dbutton = (Button) findViewById(R.id.dButton);
        dbutton.setEnabled(enabled);
        
        Button dPbutton = (Button) findViewById(R.id.dPButton);
        dPbutton.setEnabled(enabled);
        
        Button Dbutton = (Button) findViewById(R.id.DButton);
        Dbutton.setEnabled(enabled);
        
        Button DPbutton = (Button) findViewById(R.id.DPButton);
        DPbutton.setEnabled(enabled);
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
