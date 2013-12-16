package ca.germuth.rubiks.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import ca.germuth.rubiks.cube.GenericCube;
import ca.germuth.rubiks.cubeViews.HiGamesView;
import ca.germuth.rubiks.openGL.MyGLSurfaceView;
import ca.germuth.rubiks.util.Chronometer;

import android.view.View;
import android.widget.Button;

/**
 * movelistener
 * TODO this doesn't even close to working anymore
 * 
 * Also records every move of the solve
 * @author Administrator
 *
 */
public class MoveListener implements View.OnClickListener{
	private Button myButton;
	
	private static boolean recording;
	//private static String moves = "";
	private static ArrayList<String> moves = new ArrayList<String>();
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
		//moves += chrono.getText() + " " + this.myButton.getText() + "; ";
		//moves.add(this.chrono);
    	view.checkSolved();
	}
	public static String getScramble(){
		return scramble;
	}
	public static void setScramble(String s){
		scramble = s;
	}
	
//	public static void reset(){
//		moves = "";
//	}
//	
//	public static String getMoves(){
//		return moves;
//	}
	
	public static void setChronometer(Chronometer c){
		chrono = c;
	}
	
	public static void startRecording(){
		MoveListener.recording = true;
		//reset();
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