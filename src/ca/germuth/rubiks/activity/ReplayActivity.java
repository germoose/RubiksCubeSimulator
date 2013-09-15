package ca.germuth.rubiks.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import ca.germuth.rubiks.R;
import ca.germuth.rubiks.R.id;
import ca.germuth.rubiks.R.layout;
import ca.germuth.rubiks.cube.Cube;
import ca.germuth.rubiks.cube.GenericCube;
import ca.germuth.rubiks.cubeViews.HiGamesView;
import ca.germuth.rubiks.listener.MoveListener;
import ca.germuth.rubiks.util.Chronometer;
import ca.germuth.rubiks.util.MusicManager;

public class ReplayActivity extends Activity {
	
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
        
        this.dv.getMyCube().solve();
        MusicManager.start(this, MusicManager.MUSIC_GAME);
        
        //apply scramble
        String scramble = MoveListener.getScramble();
        String[] s2 = scramble.split(";");
        for(int i = 0; i < s2.length; i++){
        	String m = s2[i];
        	m = m.replace("'", "Prime");
        	m = m.trim();
        	m += "Turn";
        	Method m1 = null;
        	try {
				m1 = GenericCube.class.getMethod(m, null);
				try {
					m1.invoke(ReplayActivity.this.dv.getMyCube(), null);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
        }
        
        String solve = MoveListener.getMoves();
        final String[] moves = solve.split(";");
        
        final String[] times = new String[moves.length];
        final Method[] turns = new Method[moves.length];
        
        Scanner s = null;
        for(int i = 0; i < moves.length - 1; i++){
        	
        	s = new Scanner(moves[i]);
        	String time = s.next();
        	times[i] = time;
        	
        	String move = s.next();
        	move = move.replace("'", "Prime") + "Turn";
        	Method m = null;
        	try {
				m = Cube.class.getMethod(move, null);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
        	turns[i] = m;
        }
        
		new Thread(){
			public void run() {
				
				int currentIndex = 0;
				ReplayActivity.this.chrono.start();
				while (currentIndex != moves.length - 1) {
					if ( ReplayActivity.this.chrono.getText().equals(times[currentIndex])) {
						try {
							turns[currentIndex].invoke(ReplayActivity.this.dv.getMyCube(), null);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						currentIndex++;
						ReplayActivity.this.dv.postInvalidate();
					}
				}
			}
        }.start();
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
