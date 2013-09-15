package ca.germuth.rubiks.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import ca.germuth.rubiks.R;
import ca.germuth.rubiks.R.layout;
import ca.germuth.rubiks.cube.GenericCube;
import ca.germuth.rubiks.cubeViews.HiGamesView;
import ca.germuth.rubiks.database.DatabaseAccessor;
import ca.germuth.rubiks.listener.MoveListener;
import ca.germuth.rubiks.openGL.MyGLSurfaceView;
import ca.germuth.rubiks.util.Chronometer;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity3d extends Activity {

	private MyGLSurfaceView mView;
	private Chronometer chrono;

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
		
		Bundle extras = getIntent().getExtras();
		int size = 3;
		if (extras != null) {
		    size = extras.getInt("cube");
		}
		
		this.mView.initializeRenderer( size );
		
		setUpButtons();
		
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
}
