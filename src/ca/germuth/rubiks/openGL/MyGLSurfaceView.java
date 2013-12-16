package ca.germuth.rubiks.openGL;

import ca.germuth.rubiks.R;
import ca.germuth.rubiks.cube.GenericCube;
import ca.germuth.rubiks.util.Chronometer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyGLSurfaceView extends GLSurfaceView {

	private MyRenderer mRenderer;
	private GenericCube mCube;
	private int inspection;
	private Chronometer chrono;
	private Handler solvedHandler;
	private boolean solving;
	private TextView inspectionTime;
	
	public MyGLSurfaceView(Context context, AttributeSet attrs){
		super(context, attrs);

		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);
		setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		
	}

	public void initializeRenderer(int cubeSize) {
		// Set the Renderer for drawing on the GLSurfaceView
		this.mCube = new GenericCube( cubeSize );
		mRenderer = new MyRenderer(this.mCube);
		setRenderer(mRenderer);

		// Render the view only when there is a change in the drawing data
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		
	}
	
	public void checkSolved(){
		if( mCube.isSolved() && this.solving ){
    		this.solvedHandler.sendEmptyMessage(0);
    	} 
	}

	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private float mPreviousX;
	private float mPreviousY;

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// MotionEvent reports input details from the touch screen
		// and other input controls. In this case, you are only
		// interested in events where the touch position changed.

		float x = e.getX();
		float y = e.getY();

		switch (e.getAction()) {
		case MotionEvent.ACTION_POINTER_UP:
			Activity thisOne = (Activity) this.getContext();
			Intent intent = thisOne.getIntent();
			intent.putExtra("cube", this.mCube.getSize() + 1 );
			thisOne.finish();
			thisOne.startActivity(intent);
			break;
		case MotionEvent.ACTION_MOVE:

			float dx = x - mPreviousX;
			float dy = y - mPreviousY;

//			if( dx > 50 && dy < 50){
//				dx = dx * -1;
//				mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR; // = 180.0f /
//				mRenderer.mXAngle = -1;
//				mRenderer.mYAngle = 0;
//			}
//			
//			if( dy > 50 && dx < 50){
//				dx = dx * -1;
//				mRenderer.mYAngle = -1;
//				mRenderer.mXAngle = 0;
//				mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR; // = 180.0f /
//			}
			// reverse direction of rotation above the mid-line
			if (y > getHeight() / 2) {
				dx = dx * -1;
			}

			// reverse direction of rotation to left of the mid-line
			if (x < getWidth() / 2) {
				dy = dy * -1;
			}

																// 320
			requestRender();
		}

		mPreviousX = x;
		mPreviousY = y;
		return true;
	}

	/**
	 * @return the mCube
	 */
	public GenericCube getmCube() {
		return mCube;
	}

	/**
	 * @param mCube the mCube to set
	 */
	public void setmCube(GenericCube mCube) {
		this.mCube = mCube;
	}

	/**
	 * @return the mRenderer
	 */
	public MyRenderer getmRenderer() {
		return mRenderer;
	}

	/**
	 * @param mRenderer the mRenderer to set
	 */
	public void setmRenderer(MyRenderer mRenderer) {
		this.mRenderer = mRenderer;
	}

	/**
	 * @return the inspection
	 */
	public int getInspection() {
		return inspection;
	}

	/**
	 * @param inspection the inspection to set
	 */
	public void setInspection(int inspection) {
		this.inspection = inspection;
		//this.inspectionTime.setText(inspection + "");
		//this.invalidate();
	}

	public void setTextField(TextView t){
		this.inspectionTime = t;
	}
	
	/**
	 * @return the chrono
	 */
	public Chronometer getChrono() {
		return chrono;
	}

	/**
	 * @param chrono the chrono to set
	 */
	public void setChrono(Chronometer chrono) {
		this.chrono = chrono;
	}

	/**
	 * @return the solvedHandler
	 */
	public Handler getSolvedHandler() {
		return solvedHandler;
	}

	/**
	 * @param solvedHandler the solvedHandler to set
	 */
	public void setSolvedHandler(Handler solvedHandler) {
		this.solvedHandler = solvedHandler;
	}

	/**
	 * @return the solving
	 */
	public boolean isSolving() {
		return solving;
	}

	/**
	 * @param solving the solving to set
	 */
	public void setSolving(boolean solving) {
		this.solving = solving;
	}

	/**
	 * @return the mPreviousX
	 */
	public float getmPreviousX() {
		return mPreviousX;
	}

	/**
	 * @param mPreviousX the mPreviousX to set
	 */
	public void setmPreviousX(float mPreviousX) {
		this.mPreviousX = mPreviousX;
	}

	/**
	 * @return the mPreviousY
	 */
	public float getmPreviousY() {
		return mPreviousY;
	}

	/**
	 * @param mPreviousY the mPreviousY to set
	 */
	public void setmPreviousY(float mPreviousY) {
		this.mPreviousY = mPreviousY;
	}

	/**
	 * @return the tOUCH_SCALE_FACTOR
	 */
	public float getTOUCH_SCALE_FACTOR() {
		return TOUCH_SCALE_FACTOR;
	}
	
}