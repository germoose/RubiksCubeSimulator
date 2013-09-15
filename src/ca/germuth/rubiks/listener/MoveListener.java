package ca.germuth.rubiks.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import ca.germuth.rubiks.cubeViews.HiGamesView;
import ca.germuth.rubiks.openGL.MyGLSurfaceView;
import ca.germuth.rubiks.util.Chronometer;

import android.view.View;
import android.widget.Button;

/**
 * movelistener
 * 
 * Also records every move of the solve
 * @author Administrator
 *
 */
public class MoveListener implements View.OnClickListener{
	private Button myButton;
	
	private static boolean recording;
	private static String moves = "";
	private static Chronometer chrono;
	public static final HashMap<Button, Method> buttonToTurn = new HashMap<Button, Method>();
	private static MyGLSurfaceView view;
	private static String scramble = "";
	
	public MoveListener(Button myButton){
		this.myButton = myButton;
	}
	
	@Override
	public void onClick(View v) {
		try {
			buttonToTurn.get(this.myButton).invoke( getView().getmCube(), new Object[0]);
		} catch (Exception e){
			e.printStackTrace();
		}
		getView().requestRender();
		moves += chrono.getText() + " " + this.myButton.getText() + "; ";
	}
	public static String getScramble(){
		return scramble;
	}
	public static void setScramble(String s){
		scramble = s;
	}
	
	public static void reset(){
		moves = "";
	}
	
	public static String getMoves(){
		return moves;
	}
	
	public static void setChronometer(Chronometer c){
		chrono = c;
	}
	
	public static void startRecording(){
		MoveListener.recording = true;
		reset();
	}
	
	public static void stopRecording(){
		MoveListener.recording = false;
	}
	
	public static MyGLSurfaceView getView() {
		return view;
	}

	public static void setView(MyGLSurfaceView view) {
		MoveListener.view = view;
	}
}