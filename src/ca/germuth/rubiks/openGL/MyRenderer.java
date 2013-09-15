package ca.germuth.rubiks.openGL;

import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ca.germuth.rubiks.cube.Colour;
import ca.germuth.rubiks.cube.GenericCube;
import ca.germuth.rubiks.openGL.shape.Square;
import ca.germuth.rubiks.openGL.shape.Triangle;

import android.opengl.*;
import android.os.SystemClock;
import android.util.Log;

/**
 * Vertex Shader - OpenGL ES graphics code for rendering the vertices of a
 * shape. Fragment Shader - OpenGL ES code for rendering the face of a shape
 * with colors or textures. Program - An OpenGL ES object that contains the
 * shaders you want to use for drawing one or more shapes.
 * 
 * @author Administrator
 * 
 */
public class MyRenderer implements GLSurfaceView.Renderer {

	private static final String TAG = "MyGLRenderer";

	private final float[] mMVPMatrix = new float[16];
	private final float[] mProjMatrix = new float[16];
	private final float[] mVMatrix = new float[16];
	private final float[] mRotationMatrix = new float[16];
	
	private ArrayList<Square> myFaces;
	
	private ArrayList<Square> mTop;
	private ArrayList<Square> mBack;
	private ArrayList<Square> mFront;
	private ArrayList<Square> mRight;
	private ArrayList<Square> mLeft;
	private ArrayList<Square> mBottom;
	

	// Declare as volatile because we are updating it from another thread
	public volatile float mAngle;
	public volatile float mXAngle;
	public volatile float mYAngle;

	public GenericCube mCube;
	
	public MyRenderer(GenericCube cube){
		this.mCube = cube;
	}
	
	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		
		GLES20.glEnable( GLES20.GL_DEPTH_TEST );
		GLES20.glDepthFunc( GLES20.GL_LEQUAL );
		GLES20.glDepthMask( true );
		
		// Set the background frame color
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Position the eye behind the origin.
	    final float eyeX = 0.0f;
	    final float eyeY = 0.0f;
	    final float eyeZ = 2.5f;
	    // WAS 0, 0, 2.5
	 
	    // We are looking toward the distance
	    final float lookX = 0.0f;
	    final float lookY = 0.0f;
	    final float lookZ = 0.0f;
	 
	    // Set our up vector. This is where our head would be pointing were we holding the camera.
	    final float upX = 0.0f;
	    final float upY = 0.5f;
	    final float upZ = 0.5f;
	 
	    // Set the view matrix. This matrix can be said to represent the camera position.
	    Matrix.setLookAtM(mVMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
		
	    drawCube();
	}
	
	private void drawCube(){
		myFaces = new ArrayList<Square>();

		mTop = drawFace(this.mCube.getTop());
		Square.finalizeAll(mTop);
		myFaces.addAll(mTop);

		mFront = drawFace(this.mCube.getFront());
		Square.rotateAll(mFront, 'X', (float) Math.PI / 2);
		Square.finalizeAll(mFront);
		myFaces.addAll(mFront);

//		mBottom = drawFace(this.mCube.getBottom());
//		Square.rotateAll(mBottom, 'X', (float) (Math.PI));
//		Square.finalizeAll(mBottom);
//		myFaces.addAll(mBottom);

		mBack = drawFace(this.mCube.getBack());
		Square.rotateAll(mBack, 'X', (float) -Math.PI / 2);
		Square.finalizeAll(mBack);
		myFaces.addAll(mBack);

		mLeft = drawFace(this.mCube.getLeft());
		Square.rotateAll(mLeft, 'Y', (float) -(Math.PI / 2));
		Square.finalizeAll(mLeft);
		myFaces.addAll(mLeft);

		mRight = drawFace(this.mCube.getRight());
		Square.rotateAll(mRight, 'Y', (float) (Math.PI / 2));
		Square.finalizeAll(mRight);
		myFaces.addAll(mRight);
	}
	private static GLColour colourToGLColour( Colour c){
		float one = 1f;
		float half = 0.5f;
		
		switch(c){
		case WHITE:
			return new GLColour(one, one, one);
		case BLUE:
			return new GLColour(0, 0, one);
		case RED:
			return new GLColour(one, 0, 0);
		case GREEN:
			return new GLColour(0, one, 0);
		case YELLOW:
			return new GLColour(one, one, 0);
		case ORANGE:
			return new GLColour(one, half, 0);
		default:
			return new GLColour(one ,one, one);
		}
	}
	
	/**
	 * Takes bottom left corner of bottom left face
	 * @param bl
	 */
	private ArrayList<Square> drawFace(Colour[][] side){
		
		ArrayList<Square> face = new ArrayList<Square>();
		Square a = new Square(new GLVertex(-0.425f, 0.425f, 0.4750f), 
				new GLVertex(-0.425f, 0.175f, 0.475f), 
				new GLVertex(-0.175f, 0.175f, 0.475f), 
				new GLVertex(-0.175f, 0.425f, 0.475f));
		a.setmColour( colourToGLColour(side[0][0]) );
		
		Square b = new Square(new GLVertex(-0.125f, 0.425f, 0.475f), 
				new GLVertex(-0.125f, 0.175f, 0.4750f), 
				new GLVertex( 0.125f, 0.175f, 0.4750f), 
				new GLVertex( 0.125f, 0.425f, 0.4750f));
		b.setmColour( colourToGLColour( side[0][1]) );
		
		Square c = new Square(new GLVertex(0.175f, 0.425f, 0.4750f),
				new GLVertex(0.175f, 0.175f, 0.475f),
				new GLVertex(0.425f, 0.175f, 0.475f),
				new GLVertex(0.425f, 0.425f, 0.4750f));
		c.setmColour( colourToGLColour( side[0][2]) );
		
		Square a1 = new Square(new GLVertex(-0.425f, 0.125f, 0.4750f), 
				new GLVertex(-0.425f, -0.125f, 0.475f), 
				new GLVertex(-0.175f, -0.125f, 0.475f), 
				new GLVertex(-0.175f, 0.125f, 0.4750f));
		a1.setmColour( colourToGLColour( side[1][0]) );
		
		Square b1 = new Square(new GLVertex(-0.125f, 0.125f, 0.4750f), 
				new GLVertex(-0.125f, -0.125f, 0.475f), 
				new GLVertex( 0.125f, -0.125f, 0.475f), 
				new GLVertex( 0.125f, 0.125f, 0.4750f));
		b1.setmColour( colourToGLColour( side[1][1]) );
		
		Square c1 = new Square(new GLVertex(0.175f, 0.125f, 0.4750f),
				new GLVertex(0.175f, -0.125f, 0.475f),
				new GLVertex(0.425f, -0.125f, 0.475f),
				new GLVertex(0.425f, 0.125f, 0.475f));
		c1.setmColour( colourToGLColour( side[1][2]) );
		
		Square a2 = new Square(new GLVertex(-0.425f, -0.175f, 0.4750f), 
				new GLVertex(-0.425f, -0.425f, 0.475f), 
				new GLVertex(-0.175f, -0.425f, 0.475f), 
				new GLVertex(-0.175f, -0.175f, 0.4750f));
		a2.setmColour( colourToGLColour( side[2][0]) );
		
		Square b2 = new Square(new GLVertex(-0.125f, -0.175f, 0.4750f), 
				new GLVertex(-0.125f, -0.425f, 0.475f), 
				new GLVertex( 0.125f, -0.425f, 0.475f), 
				new GLVertex( 0.125f, -0.175f, 0.4750f));
		b2.setmColour( colourToGLColour( side[2][1]) );
		
		Square c2 = new Square(new GLVertex(0.175f, -0.175f, 0.4750f),
				new GLVertex(0.175f, -0.425f, 0.475f),
				new GLVertex(0.425f, -0.425f, 0.475f),
				new GLVertex(0.425f, -0.175f, 0.4750f));
		c2.setmColour( colourToGLColour( side[2][2]) );
		
		face.add(a);
		face.add(b);
		face.add(c);
		face.add(a1);
		face.add(b1);
		face.add(c1);
		face.add(a2);
		face.add(b2);
		face.add(c2);
		
		return face;
	}
	
	/**
	 * Takes bottom left corner of bottom left face
	 * @param bl
	 */
	private void drawFace(ArrayList<Square> face, Colour[][] side){

		face.get(0).setmColour( colourToGLColour(side[0][0]) );

		face.get(1).setmColour( colourToGLColour( side[0][1]) );

		face.get(2).setmColour( colourToGLColour( side[0][2]) );

		face.get(3).setmColour( colourToGLColour( side[1][0]) );

		face.get(4).setmColour( colourToGLColour( side[1][1]) );

		face.get(5).setmColour( colourToGLColour( side[1][2]) );

		face.get(6).setmColour( colourToGLColour( side[2][0]) );
		
		face.get(7).setmColour( colourToGLColour( side[2][1]) );
		
		face.get(8).setmColour( colourToGLColour( side[2][2]) );
	}

	@Override
	public void onDrawFrame(GL10 unused) {

		// GLES20.glClearDepthf(1.0f);
		// Draw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		drawFace( this.mTop, this.mCube.getTop() );
		drawFace( this.mFront, this.mCube.getFront() );
		//drawFace( this.mBottom, this.mCube.getBottom() );
		drawFace( this.mBack, this.mCube.getBack() );
		drawFace( this.mLeft, this.mCube.getLeft() );
		drawFace( this.mRight, this.mCube.getRight() );

		// Calculate the projection and view transformation
		Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);

		float angleInDegrees = 45f;

		// Draw the triangle facing straight on.
		Matrix.setIdentityM(mRotationMatrix, 0);
		Matrix.setRotateM(mRotationMatrix, 0, angleInDegrees, 1.0f, 0.0f, .0f);

		// Combine the rotation matrix with the projection and camera view
		Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);

		// Create a rotation for the triangle
		// long time = SystemClock.uptimeMillis() % 4000L;
		// float angle = 0.090f * ((int) time);
		// Matrix.setRotateM(mRotationMatrix, 0, mAngle, mYAngle, 0, mXAngle);
		
		
		// Do a complete rotation every 10 seconds.
		// long time = SystemClock.uptimeMillis() % 30000L;
//        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
// 
//        // Draw the triangle facing straight on.
//        Matrix.setIdentityM(mRotationMatrix, 0);
//        Matrix.setRotateM(mRotationMatrix, 0, angleInDegrees, 1.0f, 0.0f, .0f);
//
//		// Combine the rotation matrix with the projection and camera view
//		Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);

		// Draw squares
		for (int i = 0; i < this.myFaces.size(); i++) {
			this.myFaces.get(i).draw(mMVPMatrix);
		}
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		// Adjust the viewport based on geometry changes,
		// such as screen rotation
		GLES20.glViewport(0, 0, width, height);

		// Create a new perspective projection matrix. The height will stay the same
	    // while the width will vary as per aspect ratio.
	    final float ratio = (float) width / height;
	    final float left = -ratio;
	    final float right = ratio;
	    final float bottom = -1.0f;
	    final float top = 1.0f;
	    final float near = 0.5f;
	    final float far = 1.5f;
	    
	    //Matrix.frustumM(mProjMatrix, 0, -0.6f, 0.6f, -0.7f, 0.7f, near, far);
	    Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1f, 1f, 1.9f, 100.5f);
	}

	public static int loadShader(int type, String shaderCode) {

		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);

		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}

	/**
	 * Utility method for debugging OpenGL calls. Provide the name of the call
	 * just after making it:
	 * 
	 * <pre>
	 * mColorHandle = GLES20.glGetUniformLocation(mProgram, &quot;vColor&quot;);
	 * MyGLRenderer.checkGlError(&quot;glGetUniformLocation&quot;);
	 * </pre>
	 * 
	 * If the operation is not successful, the check throws an error.
	 * 
	 * @param glOperation
	 *            - Name of the OpenGL call to check.
	 */
	public static void checkGlError(String glOperation) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.e(TAG, glOperation + ": glError " + error);
			throw new RuntimeException(glOperation + ": glError " + error);
		}
	}
}
