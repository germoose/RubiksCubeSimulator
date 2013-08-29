package ca.germuth.rubiks;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import ca.germuth.rubiks.util.Chronometer;
import ca.germuth.rubiks.util.Utils;
import cubeViews.HiGamesView;
import database.DatabaseAccessor;
import database.SolveTimeTable;
import database.SolveTime;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
	
	private long startTime;
	private HiGamesView dv;
	private Chronometer chrono;
	private static Handler handler;
	private MediaPlayer mp;
	// static so other classes can access the database
	// must ensure other processes check whether null before use
	/**
	 * Android can kill your process if your application is in the background
	 * pretty much any time it wants to. When the user then returns to your
	 * application, Android will create a new process for your application and
	 * then it will recreate only the activity that was on the top of the
	 * activity stack (ie: the one that was showing). In that case, the newly
	 * created activity will not be able to get the iobject by accesing
	 * Globals.myObject because the process has been newly created and that
	 * member variable is null.
	 * 
	 * To get around this you can either:
	 * 
	 * Determine that your process has been killed and restarted (by checking
	 * Globals.myObject == null and react accordingly - Tell the user he needs
	 * to go back, or just go back yourself, or show a dialog or whatever) Save
	 * the object when Android calls onSaveInstanceState() in your activity
	 * (which Android will do before sending your app to the background) and
	 * restore the object in onCreate()
	 */
	public static DatabaseAccessor data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);
        
        //final QCubeView dv = (QCubeView) findViewById(R.id.drawView);
        this.dv = (HiGamesView) findViewById(R.id.drawView);
        this.chrono = (Chronometer) findViewById(R.id.chronometer);
        data = new DatabaseAccessor(this);
        data.open();
        
        this.dv.setSolvedHandler(new Handler(){
        	public void handleMessage(Message msg){
        		long currentTime = System.nanoTime();
				
        		MainActivity.this.dv.setSolving( false );
        		MainActivity.this.chrono.stop();
        		
				long duration = currentTime - MainActivity.this.startTime;
				duration /= 1000;
				
				duration /= 10;
				
				int milli = (int)((double)duration / 100.0);
				
				//String time = Utils.milliToTime(milli);
				//MainActivity.this.data.createSolveTime( time );
				
				Intent myIntent = new Intent(MainActivity.this, ScoreScreenActivity.class);
				myIntent.putExtra("ca.germuth.rubiks.Time", milli); //Optional parameters
				MainActivity.this.startActivity(myIntent);
				
//				milli /= 1000;
//				MainActivity.this.startTime = 0;
//				Toast toast = Toast.makeText(MainActivity.this, "You took " + milli + " seconds.", Toast.LENGTH_SHORT);
//				toast.show();
        	}
        });
        
        handler = new Handler(){
        	public void handleMessage(Message msg){
        		setButtons(true);
        		MainActivity.this.startTime = System.nanoTime();
        		MainActivity.this.dv.setSolving( true );
				MainActivity.this.chrono.start();
        	}
        };
        
        startMusic();
        
        Button ubutton = (Button) findViewById(R.id.uButton);
        ubutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().uTurn();
				dv.invalidate(); 
			}
		});
        
        Button Rbutton = (Button) findViewById(R.id.RButton);
        Rbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().RTurn();
				dv.invalidate(); 
			}
		});
        
        Button RPbutton = (Button) findViewById(R.id.RPButton);
        RPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().RPrimeTurn();
				dv.invalidate();
			}
		});
        
        Button Ubutton = (Button) findViewById(R.id.UButton);
        Ubutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().UTurn();
				dv.invalidate();
			}
		});
        
        Button UPbutton = (Button) findViewById(R.id.UPButton);
        UPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().UPrimeTurn();
				dv.invalidate();
			}
		});
       
        Button Lbutton = (Button) findViewById(R.id.LButton);
        Lbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().LTurn();
				dv.invalidate();
			}
		});
        
        Button LPbutton = (Button) findViewById(R.id.LPButton);
        LPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().LPrimeTurn();
				dv.invalidate();
			}
		});
        
        Button Fbutton = (Button) findViewById(R.id.FButton);
        Fbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().FTurn();
				dv.invalidate();
			}
		});
        
        Button FPbutton = (Button) findViewById(R.id.FPButton);
        FPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().FPrimeTurn();
				dv.invalidate();
			}
		});
        
        Button xbutton = (Button) findViewById(R.id.xButton);
        xbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().rotateUp();
				dv.invalidate();
			}
		});
        
        Button xPbutton = (Button) findViewById(R.id.xPButton);
        xPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().rotateDown();
				dv.invalidate();
			}
		});
        
        Button ybutton = (Button) findViewById(R.id.yButton);
        ybutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().rotateLeft();
				dv.invalidate();
			}
		});
        
        Button yPbutton = (Button) findViewById(R.id.yPButton);
        yPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().rotateRight();
				dv.invalidate();
			}
		});
        
        Button rbutton = (Button) findViewById(R.id.rButton);
        rbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().rTurn();
				dv.invalidate();
			}
		});
        
        Button rPbutton = (Button) findViewById(R.id.rPButton);
        rPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().rPrimeTurn();
				dv.invalidate();
			}
		});

        Button uPbutton = (Button) findViewById(R.id.uPButton);
        uPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().uPrimeTurn();
				dv.invalidate();
			}
		});
       
        Button lbutton = (Button) findViewById(R.id.lButton);
        lbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().lTurn();
				dv.invalidate();
			}
		});
        
        Button lPbutton = (Button) findViewById(R.id.lPButton);
        lPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().lPrimeTurn();
				dv.invalidate();
			}
		});
        
        Button fbutton = (Button) findViewById(R.id.fButton);
        fbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().fTurn();
				dv.invalidate();
			}
		});
        
        Button fPbutton = (Button) findViewById(R.id.fPButton);
        fPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().fPrimeTurn();
				dv.invalidate();
			}
		});
        
        Button dbutton = (Button) findViewById(R.id.dButton);
        dbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().dTurn();
				dv.invalidate();
			}
		});
        
        Button dPbutton = (Button) findViewById(R.id.dPButton);
        dPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().dPrimeTurn();
				dv.invalidate();
			}
		});
        
        Button Dbutton = (Button) findViewById(R.id.DButton);
        Dbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().DTurn();
				dv.invalidate();
			}
		});
        
        Button DPbutton = (Button) findViewById(R.id.DPButton);
        DPbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dv.getMyCube().DPrimeTurn();
				dv.invalidate();
			}
		});
        
        ShakeListener mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
			@Override
			public void onShake() {
				dv.getMyCube().scrambleCube();
				dv.invalidate();
				
				setButtons(false);
				
				new Thread(){
					private HiGamesView dv;
					public Thread initialise(HiGamesView dv){
						this.dv = dv;
						return this;
					}
					public void run(){
						int inspection = 2;
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
						MainActivity.this.handler.sendEmptyMessage(0);
					}
				}.initialise(dv).start();
				
			}
		});
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
	
	private void startMusic(){
		new Thread(){
        	public void run(){
        		MainActivity.this.mp = MediaPlayer.create(MainActivity.this, R.raw.song1);
        		mp.setLooping(true);
        		mp.start();	
        	}
        }.start();
	}
	
	@Override
	  protected void onResume() {
	    this.data.open();
	    startMusic();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    this.data.close();
	    this.mp.release();
	    super.onPause();
	  }

}
