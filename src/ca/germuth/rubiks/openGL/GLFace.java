package ca.germuth.rubiks.openGL;

import java.util.ArrayList;

/**
 * Represents a collection of GLVerticies.
 * Designates a face
 * 
 * @author Administrator
 *
 */
public class GLFace {
	private ArrayList<GLVertex> mVertexList;
	private GLColour mColour;
	
	public GLFace(){
		this.mVertexList = new ArrayList<GLVertex>();
		mColour = null;
	}
	
	public GLFace(GLVertex one, GLVertex two, GLVertex three){
		this.mVertexList = new ArrayList<GLVertex>();
		this.mVertexList.add(one);
		this.mVertexList.add(two);
		this.mVertexList.add(three);
		
		this.mColour = null;
	}
	
	public GLFace(GLVertex one, GLVertex two, GLVertex three, GLVertex four){
		this.mVertexList = new ArrayList<GLVertex>();
		this.mVertexList.add(one);
		this.mVertexList.add(two);
		this.mVertexList.add(three);
		this.mVertexList.add(four);
		
		this.mColour = null;
	}
	
	public GLFace(GLVertex one, GLVertex two, GLVertex three, GLVertex four, GLColour colour){
		this.mVertexList = new ArrayList<GLVertex>();
		this.mVertexList.add(one);
		this.mVertexList.add(two);
		this.mVertexList.add(three);
		this.mVertexList.add(four);
		
		this.mColour = colour;
	}

	/**
	 * @return the mVertexList
	 */
	public ArrayList<GLVertex> getmVertexList() {
		return mVertexList;
	}

	/**
	 * @param mVertexList the mVertexList to set
	 */
	public void setmVertexList(ArrayList<GLVertex> mVertexList) {
		this.mVertexList = mVertexList;
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
