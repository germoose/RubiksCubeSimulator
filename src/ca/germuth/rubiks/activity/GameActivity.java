package ca.germuth.rubiks.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import ca.germuth.rubiks.R;
import ca.germuth.rubiks.R.id;
import ca.germuth.rubiks.R.layout;
import ca.germuth.rubiks.cube.GenericCube;
import ca.germuth.rubiks.cubeViews.HiGamesView;
import ca.germuth.rubiks.database.DatabaseAccessor;
import ca.germuth.rubiks.listener.MoveListener;
import ca.germuth.rubiks.listener.ShakeListener;
import ca.germuth.rubiks.listener.ShakeListener.OnShakeListener;
import ca.germuth.rubiks.util.Chronometer;
import ca.germuth.rubiks.util.MusicManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity extends Activity {
	
	private long startTime;
	private HiGamesView dv;
	private Chronometer chrono;
	private static Handler handler;
	private boolean continueMusic = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		continueMusic = false;
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_game_screen);
        
        //final QCubeView dv = (QCubeView) findViewById(R.id.drawView);
        this.setDv((HiGamesView) findViewById(R.id.drawView));
        this.chrono = (Chronometer) findViewById(R.id.chronometer);
        
        this.getDv().setSolvedHandler(new Handler(){
        	public void handleMessage(Message msg){
        		long currentTime = System.nanoTime();
				
        		GameActivity.this.getDv().setSolving( false );
        		GameActivity.this.chrono.stop();
        		
				long duration = currentTime - GameActivity.this.startTime;
				duration /= 10000;
				
				int milli = (int)((double)duration / 100.0);
				
				Intent myIntent = new Intent(GameActivity.this, ScoreScreenActivity.class);
				myIntent.putExtra("ca.germuth.rubiks.Time", milli); //Optional parameters
				continueMusic = true;
				GameActivity.this.startActivity(myIntent);
        	}
        });
        
        handler = new Handler(){
        	public void handleMessage(Message msg){
        		setButtons(true);
        		GameActivity.this.startTime = System.nanoTime();
        		GameActivity.this.getDv().setSolving( true );
				GameActivity.this.chrono.start();
				MoveListener.startRecording();
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
				//getDv().getMyCube().scrambleCube();
				getDv().invalidate();
				
				setButtons(false);
				
				new Thread(){
					private HiGamesView dv;
					public Thread initialise(HiGamesView dv){;
						this.dv = dv;
						return this;
					}
					public void run(){
						int inspection = 3;
						while(inspection > -1){
							this.dv.setInspection(inspection);
							inspection--;
							
							this.dv.postInvalidate();
							
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						GameActivity.this.handler.sendEmptyMessage(0);
					}
				}.initialise(getDv()).start();
				
			}
		});
	}
	
	/**
	 * Iterate through all buttons and add a new listener which applies their
	 * move, based on the text in each button
	 */
	private void setUpButtons() {
		//MoveListener.setView(this.dv);
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

	public Chronometer getChrono() {
		return chrono;
	}

	public void setChrono(Chronometer chrono) {
		this.chrono = chrono;
	}

	public HiGamesView getDv() {
		return dv;
	}

	public void setDv(HiGamesView dv) {
		this.dv = dv;
	}

}
