package ca.germuth.rubiks.openGL.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import ca.germuth.rubiks.openGL.GLColour;
import ca.germuth.rubiks.openGL.GLVertex;
import ca.germuth.rubiks.openGL.MyRenderer;

import android.opengl.GLES20;

public class Square {
	private final String vertexShaderCode =
	// This matrix member variable provides a hook to manipulate
	// the coordinates of the objects that use this vertex shader
	"uniform mat4 uMVPMatrix;" +

	"attribute vec4 vPosition;" + "void main() {" +
	// the matrix must be included as a modifier of gl_Position
			"  gl_Position = vPosition * uMVPMatrix;" + "}";

	private final String fragmentShaderCode = "precision mediump float;"
			+ "uniform vec4 vColor;" + "void main() {"
			+ "  gl_FragColor = vColor;" + "}";

	private FloatBuffer vertexBuffer;
	private ShortBuffer drawListBuffer;
	private int mProgram;
	private int mPositionHandle;
	private int mColorHandle;
	private int mMVPMatrixHandle;
	private GLColour mColour;
	
	
	/**
	 * The List of verticies for this square
	 */
	private ArrayList<GLVertex> verticies;
	
	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 3;
//	static float squareCoords[] = { -0.5f, 0.5f, 0.5f, // top left
//			-0.5f, -0.5f, 0.5f, // bottom left
//			0.5f, -0.5f, 0.0f, // bottom right
//			0.5f, 0.5f, 0.0f }; // top right

	private final short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw
															// vertices

	private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per
															// vertex

	/**
	 * Top Left, Bottom Left, Bottom Right, Top Right
	 * 
	 * Positive z is far away
	 * @param one
	 * @param two
	 * @param three
	 * @param four
	 */
	public Square(GLVertex one, GLVertex two, GLVertex three, GLVertex four) {
		
		this.verticies = new ArrayList<GLVertex>();
		this.verticies.add(one);
		this.verticies.add(two);
		this.verticies.add(three);
		this.verticies.add(four);
			
	}
	
	public void finalize() {
		float[] squareCoords = new float[12];
		squareCoords[0] = this.verticies.get(0).getX();
		squareCoords[1] = this.verticies.get(0).getY();
		squareCoords[2] = this.verticies.get(0).getZ();
		squareCoords[3] = this.verticies.get(1).getX();
		squareCoords[4] = this.verticies.get(1).getY();
		squareCoords[5] = this.verticies.get(1).getZ();
		squareCoords[6] = this.verticies.get(2).getX();
		squareCoords[7] = this.verticies.get(2).getY();
		squareCoords[8] = this.verticies.get(2).getZ();
		squareCoords[9] = this.verticies.get(3).getX();
		squareCoords[10] = this.verticies.get(3).getY();
		squareCoords[11] = this.verticies.get(3).getZ();
		
		// initialize vertex byte buffer for shape coordinates
		ByteBuffer bb = ByteBuffer.allocateDirect(
		// (# of coordinate values * 4 bytes per float)
		// verticies * coordinates * bytes per float
				this.verticies.size() * 3 * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(squareCoords);
		vertexBuffer.position(0);

		// initialize byte buffer for the draw list
		ByteBuffer dlb = ByteBuffer.allocateDirect(
		// (# of coordinate values * 2 bytes per short)
				drawOrder.length * 2);
		dlb.order(ByteOrder.nativeOrder());
		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);

		// prepare shaders and OpenGL program
		int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
				vertexShaderCode);
		int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
				fragmentShaderCode);

		mProgram = GLES20.glCreateProgram(); // create empty OpenGL Program
		GLES20.glAttachShader(mProgram, vertexShader); // add the vertex shader
														// to program
		GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment
															// shader to program
		GLES20.glLinkProgram(mProgram); // create OpenGL program executables
	}

	public void draw(float[] mvpMatrix) {
		// Add program to OpenGL environment
		GLES20.glUseProgram(mProgram);

		// get handle to vertex shader's vPosition member
		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

		// Enable a handle to the triangle vertices
		GLES20.glEnableVertexAttribArray(mPositionHandle);

		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
				GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

		// get handle to fragment shader's vColor member
		mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

		// Set color for drawing the triangle
		float color[] = new float[3];
		color[0] = this.mColour.getRed();
		color[1] = this.mColour.getGreen();
		color[2] = this.mColour.getBlue();
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);
		//GLES20.glUniform3fv(mColorHandle, 1, color, 0);
		
		// get handle to shape's transformation matrix
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		MyRenderer.checkGlError("glGetUniformLocation");

		// Apply the projection and view transformation
		GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
		MyRenderer.checkGlError("glUniformMatrix4fv");

		// Draw the square
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
				GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		// Disable vertex array
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
	
	/**
	 * Rotates this square around either the x y or z axis, by an amount in radians
	 * @param axis, the axis you are rotating around, may be 'X', 'Y', or 'Z'
	 * @param radians, the degree in radians you want to rotate around it
	 */
	public void rotate(char axis, float radians){
		for(int i = 0; i < this.verticies.size(); i++){
			GLVertex current = this.verticies.get(i);
			current.rotate(axis, radians);
		}	
	}
	
	public void translate(char axis, double distance){
		for(int i = 0; i < this.verticies.size(); i++){
			this.verticies.get(i).translate(axis, distance);
		}
	}
	
	public static void translateAll(ArrayList<Square> face, char axis, double distance){
		for(int i = 0; i < face.size(); i++){
			Square s = face.get(i);
			s.translate( axis, distance );
		}
	}
	
	
	public static void rotateAll(ArrayList<Square> face, char axis, float radians){
		for(int i = 0; i < face.size(); i++){
			Square s = face.get(i);
			s.rotate(axis, radians);
		}
	}
	
	public static void finalizeAll(ArrayList<Square> face){
		for(int i = 0; i < face.size(); i++){
			Square s = face.get(i);
			s.finalize();
		}
	}

	/**
	 * @return the mColour
	 */
	public GLColour getmColour() {
		return mColour;
	}

	/**
	 * @param mColour the mColour to set
	 */
	public void setmColour(GLColour mColour) {
		this.mColour = mColour;
	}
}
