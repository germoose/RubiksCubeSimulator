package ca.germuth.rubiks;
/*
 * This is the 3x3 Cube Class. 
 * 
 * @author Aaron Germuth
 * @version 1.0 April 13th 
 */
import java.util.Random; 

public class Cube {
	private Colour[][] top = new Colour[3][3];
	private Colour[][] front = new Colour[3][3];
	private Colour[][] right = new Colour[3][3];
	private Colour[][] back = new Colour[3][3];
	private Colour[][] left = new Colour[3][3];
	private Colour[][] bottom = new Colour[3][3];

	private Colour[][] topCopy = new Colour[3][3];
	private Colour[][] rightCopy = new Colour[3][3];
	private Colour[][] leftCopy = new Colour[3][3];
	private Colour[][] backCopy = new Colour[3][3];
	private Colour[][] frontCopy = new Colour[3][3];
	private Colour[][] bottomCopy = new Colour[3][3];
	
	private boolean isSolved;
	// TODO timer

	public Cube() {
		this.setSolved();
		this.isSolved = false;
	}

	public void rotateRight() {
		makeCopies();

		left[0][2] = frontCopy[0][0];
		left[1][2] = frontCopy[0][1];
		left[2][2] = frontCopy[0][2];
		left[0][1] = frontCopy[1][0];
		left[1][1] = frontCopy[1][1];
		left[2][1] = frontCopy[1][2];
		left[0][0] = frontCopy[2][0];
		left[1][0] = frontCopy[2][1];
		left[2][0] = frontCopy[2][2];

		front[0][0] = rightCopy[2][0];
		front[0][1] = rightCopy[1][0];
		front[0][2] = rightCopy[0][0];
		front[1][0] = rightCopy[2][1];
		front[1][1] = rightCopy[1][1];
		front[1][2] = rightCopy[0][1];
		front[2][0] = rightCopy[2][2];
		front[2][1] = rightCopy[1][2];
		front[2][2] = rightCopy[0][2];

		right[2][0] = backCopy[2][2];
		right[1][0] = backCopy[2][1];
		right[0][0] = backCopy[2][0];
		right[2][1] = backCopy[1][2];
		right[1][1] = backCopy[1][1];
		right[0][1] = backCopy[1][0];
		right[2][2] = backCopy[0][2];
		right[1][2] = backCopy[0][1];
		right[0][2] = backCopy[0][0];

		back[2][2] = leftCopy[0][2];
		back[2][1] = leftCopy[1][2];
		back[2][0] = leftCopy[2][2];

		back[1][2] = leftCopy[0][1];
		back[1][1] = leftCopy[1][1];
		back[1][0] = leftCopy[2][1];

		back[0][0] = leftCopy[2][0];
		back[0][1] = leftCopy[1][0];
		back[0][2] = leftCopy[0][0];

		top[0][0] = topCopy[2][0];
		top[0][1] = topCopy[1][0];
		top[0][2] = topCopy[0][0];
		top[1][0] = topCopy[2][1];
		top[1][1] = topCopy[1][1];
		top[1][2] = topCopy[0][1];
		top[2][0] = topCopy[2][2];
		top[2][1] = topCopy[1][2];
		top[2][2] = topCopy[0][2];

		bottom[0][0] = bottomCopy[0][2];
		bottom[0][1] = bottomCopy[1][2];
		bottom[0][2] = bottomCopy[2][2];

		bottom[1][0] = bottomCopy[0][1];
		bottom[1][1] = bottomCopy[1][1];
		bottom[1][2] = bottomCopy[2][1];

		bottom[2][0] = bottomCopy[0][0];
		bottom[2][1] = bottomCopy[1][0];
		bottom[2][2] = bottomCopy[2][0];
	}
	public void rotateLeft() {
		this.rotateRight();
		this.rotateRight();
		this.rotateRight();
	}

	private void makeCopies() {
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < 3; i++) {
				topCopy[k][i] = top[k][i];
				rightCopy[k][i] = right[k][i];
				leftCopy[k][i] = left[k][i];
				backCopy[k][i] = back[k][i];
				frontCopy[k][i] = front[k][i];
				bottomCopy[k][i] = bottom[k][i];
			}
		}
	}

	public void setSolved() {
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < 3; i++) {
				top[k][i] = Colour.WHITE;
				front[k][i] = Colour.GREEN;
				right[k][i] = Colour.RED;
				left[k][i] = Colour.ORANGE;
				back[k][i] = Colour.BLUE;
				bottom[k][i] = Colour.YELLOW;
			}
		}
	}
	
	public void UTurn() {
		makeCopies();

		top[0][0] = topCopy[2][0];
		top[0][1] = topCopy[1][0];
		top[0][2] = topCopy[0][0];
		top[1][0] = topCopy[2][1];
		top[1][1] = topCopy[1][1];
		top[1][2] = topCopy[0][1];
		top[2][0] = topCopy[2][2];
		top[2][1] = topCopy[1][2];
		top[2][2] = topCopy[0][2];

		left[0][2] = front[0][0];
		left[1][2] = front[0][1];
		left[2][2] = front[0][2];

		front[0][0] = rightCopy[2][0];
		front[0][1] = rightCopy[1][0];
		front[0][2] = rightCopy[0][0];

		right[2][0] = backCopy[2][2];
		right[1][0] = backCopy[2][1];
		right[0][0] = backCopy[2][0];

		back[2][2] = leftCopy[0][2];
		back[2][1] = leftCopy[1][2];
		back[2][0] = leftCopy[2][2];

	}
	public void UPrimeTurn() {
		this.UTurn();
		this.UTurn();
		this.UTurn();
	}
	
	public void RTurn() {
		makeCopies();

		right[0][0] = rightCopy[2][0];
		right[0][1] = rightCopy[1][0];
		right[0][2] = rightCopy[0][0];
		right[1][0] = rightCopy[2][1];
		right[1][1] = rightCopy[1][1];
		right[1][2] = rightCopy[0][1];
		right[2][0] = rightCopy[2][2];
		right[2][1] = rightCopy[1][2];
		right[2][2] = rightCopy[0][2];

		top[2][2] = frontCopy[2][2];
		top[1][2] = frontCopy[1][2];
		top[0][2] = frontCopy[0][2];

		front[2][2] = bottomCopy[2][2];
		front[1][2] = bottomCopy[1][2];
		front[0][2] = bottomCopy[0][2];

		bottom[2][2] = backCopy[2][2];
		bottom[1][2] = backCopy[1][2];
		bottom[0][2] = backCopy[0][2];

		back[2][2] = topCopy[2][2];
		back[1][2] = topCopy[1][2];
		back[0][2] = topCopy[0][2];
	}
	public void RPrimeTurn() {
		this.RTurn();
		this.RTurn();
		this.RTurn();
	}

	public void LTurn() {
		makeCopies();

		left[0][0] = leftCopy[2][0];
		left[0][1] = leftCopy[1][0];
		left[0][2] = leftCopy[0][0];
		left[1][0] = leftCopy[2][1];
		left[1][1] = leftCopy[1][1];
		left[1][2] = leftCopy[0][1];
		left[2][0] = leftCopy[2][2];
		left[2][1] = leftCopy[1][2];
		left[2][2] = leftCopy[0][2];

		top[0][0] = backCopy[0][0];
		top[1][0] = backCopy[1][0];
		top[2][0] = backCopy[2][0];

		front[0][0] = topCopy[0][0];
		front[1][0] = topCopy[1][0];
		front[2][0] = topCopy[2][0];

		bottom[2][0] = frontCopy[2][0];
		bottom[1][0] = frontCopy[1][0];
		bottom[0][0] = frontCopy[0][0];

		back[2][0] = bottomCopy[2][0];
		back[1][0] = bottomCopy[1][0];
		back[0][0] = bottomCopy[0][0];

	}
	public void LPrimeTurn() {
		this.LTurn();
		this.LTurn();
		this.LTurn();
	}
	
	public void FTurn() {
		makeCopies();

		front[0][0] = frontCopy[2][0];
		front[0][1] = frontCopy[1][0];
		front[0][2] = frontCopy[0][0];
		front[1][0] = frontCopy[2][1];
		front[1][1] = frontCopy[1][1];
		front[1][2] = frontCopy[0][1];
		front[2][0] = frontCopy[2][2];
		front[2][1] = frontCopy[1][2];
		front[2][2] = frontCopy[0][2];

		left[2][0] = bottom[0][2];
		left[2][1] = bottom[0][1];
		left[2][2] = bottom[0][0];

		bottom[0][0] = rightCopy[2][2];
		bottom[0][1] = rightCopy[2][1];
		bottom[0][2] = rightCopy[2][0];

		right[2][0] = topCopy[2][0];
		right[2][1] = topCopy[2][1];
		right[2][2] = topCopy[2][2];

		top[2][2] = leftCopy[2][2];
		top[2][1] = leftCopy[2][1];
		top[2][0] = leftCopy[2][0];
	}
	public void FPrimeTurn() {
		this.FTurn();
		this.FTurn();
		this.FTurn();
	}

	public void DTurn() {
		this.DPrimeTurn();
		this.DPrimeTurn();
		this.DPrimeTurn();

	}
	public void DPrimeTurn() {
		makeCopies();

		bottom[0][0] = bottomCopy[0][2];
		bottom[0][1] = bottomCopy[1][2];
		bottom[0][2] = bottomCopy[2][2];
		bottom[1][0] = bottomCopy[0][1];
		bottom[1][1] = bottomCopy[1][1];
		bottom[1][2] = bottomCopy[2][1];
		bottom[2][0] = bottomCopy[0][0];
		bottom[2][1] = bottomCopy[1][0];
		bottom[2][2] = bottomCopy[2][0];

		left[0][0] = front[2][0];
		left[1][0] = front[2][1];
		left[2][0] = front[2][2];

		front[2][0] = rightCopy[2][2];
		front[2][1] = rightCopy[1][2];
		front[2][2] = rightCopy[0][2];

		right[0][2] = backCopy[0][0];
		right[1][2] = backCopy[0][1];
		right[2][2] = backCopy[0][2];

		back[0][2] = leftCopy[0][0];
		back[0][1] = leftCopy[1][0];
		back[0][0] = leftCopy[2][0];
	}

	public void BTurn() {
		makeCopies();

		back[0][0] = backCopy[2][0];
		back[0][1] = backCopy[1][0];
		back[0][2] = backCopy[0][0];
		back[1][0] = backCopy[2][1];
		back[1][1] = backCopy[1][1];
		back[1][2] = backCopy[0][1];
		back[2][0] = backCopy[2][2];
		back[2][1] = backCopy[1][2];
		back[2][2] = backCopy[0][2];

		left[0][0] = topCopy[0][0];
		left[0][1] = topCopy[0][1];
		left[0][2] = topCopy[0][2];

		top[0][0] = rightCopy[0][0];
		top[0][1] = rightCopy[0][1];
		top[0][2] = rightCopy[0][2];

		right[0][0] = bottomCopy[2][2];
		right[0][1] = bottomCopy[2][1];
		right[0][2] = bottomCopy[2][0];

		bottom[2][0] = leftCopy[0][2];
		bottom[2][1] = leftCopy[0][1];
		bottom[2][2] = leftCopy[0][0];

	}
	public void BPrimeTurn() {
		this.BTurn();
		this.BTurn();
		this.BTurn();
	}

	public void rotateUp() {
		makeCopies();
		
		right[0][0] = rightCopy[2][0];
		right[0][1] = rightCopy[1][0];
		right[0][2] = rightCopy[0][0];
		right[1][0] = rightCopy[2][1];
		right[1][1] = rightCopy[1][1];
		right[1][2] = rightCopy[0][1];
		right[2][0] = rightCopy[2][2];
		right[2][1] = rightCopy[1][2];
		right[2][2] = rightCopy[0][2];
		
		left[0][0] = leftCopy[0][2];
		left[0][1] = leftCopy[1][2];
		left[0][2] = leftCopy[2][2];
		left[1][0] = leftCopy[0][1];
		left[1][1] = leftCopy[1][1];
		left[1][2] = leftCopy[2][1];
		left[2][0] = leftCopy[0][0];
		left[2][1] = leftCopy[1][0];
		left[2][2] = leftCopy[2][0];
		
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < 3; i++) {
				top[k][i] = frontCopy[k][i];
				front[k][i] = bottomCopy[k][i];
				back[k][i] = topCopy[k][i];
				bottom[k][i] = backCopy[k][i];
			}
		}
	}
	public void rotateDown() {
		this.rotateUp();
		this.rotateUp();
		this.rotateUp();
	}
	
	public void rotateRollLeft() {
		makeCopies();
		//check
		front[0][0] = frontCopy[2][0];
		front[0][1] = frontCopy[1][0];
		front[0][2] = frontCopy[0][0];
		front[1][0] = frontCopy[2][1];
		front[1][1] = frontCopy[1][1];
		front[1][2] = frontCopy[0][1];
		front[2][0] = frontCopy[2][2];
		front[2][1] = frontCopy[1][2];
		front[2][2] = frontCopy[0][2];
		
		//check
		back[0][0] = backCopy[0][2];
		back[0][1] = backCopy[1][2];
		back[0][2] = backCopy[2][2];
		back[1][0] = backCopy[0][1];
		back[1][1] = backCopy[1][1];
		back[1][2] = backCopy[2][1];
		back[2][0] = backCopy[0][0];
		back[2][1] = backCopy[1][0];
		back[2][2] = backCopy[2][0];
		
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < 3; i++) {
				top[k][i] = leftCopy[k][i]; //check
				right[k][i] = topCopy[k][i]; //check
			}
		}
		
		bottom[0][0] = rightCopy[2][2];
		bottom[0][1] = rightCopy[2][1];
		bottom[0][2] = rightCopy[2][0];
		
		bottom[1][0] = rightCopy[1][2];
		bottom[1][1] = rightCopy[1][1];
		bottom[1][2] = rightCopy[1][0];
		
		bottom[2][0] = rightCopy[0][2];
		bottom[2][1] = rightCopy[0][1];
		bottom[2][2] = rightCopy[0][0];
		
		
		left[2][0] = bottomCopy[0][2];
		left[2][1] = bottomCopy[0][1];
		left[2][2] = bottomCopy[0][0];
		
		left[1][0] = bottomCopy[1][2];
		left[1][1] = bottomCopy[1][1];
		left[1][2] = bottomCopy[1][0];
		
		left[0][0] = bottomCopy[2][2];
		left[0][1] = bottomCopy[2][1];
		left[0][2] = bottomCopy[2][0];
	}
	public void rotateRollRight() {
		this.rotateRollLeft();
		this.rotateRollLeft();
		this.rotateRollLeft();
	}
	
	public void scrambleCube() {
		Random randomGenerator = new Random();
		for(int i = 0; i<2; i++) {
			int r = randomGenerator.nextInt(12);
			
			switch(r) {
			case 0: this.UTurn(); System.out.print("U "); break;
			case 1: this.UPrimeTurn(); System.out.print("U' "); break;
			case 2: this.RTurn(); System.out.print("R "); break;
			case 3: this.RPrimeTurn(); System.out.print("R' "); break;
			case 4: this.LTurn(); System.out.print("L "); break;
			case 5: this.LPrimeTurn(); System.out.print("L' "); break;
			case 6: this.FTurn(); System.out.print("F "); break;
			case 7: this.FPrimeTurn(); System.out.print("F' "); break;
			case 8: this.DTurn(); System.out.print("D "); break;
			case 9: this.DPrimeTurn(); System.out.print("D' "); break;
			case 10: this.BTurn(); System.out.print("B "); break;
			case 11: this.BPrimeTurn(); System.out.print("B' "); break;
			}
		}
		System.out.println("");
	}
	
	public void rTurn() {
		this.rotateUp();
		this.LTurn();
	}
	public void rPrimeTurn() {
		this.rotateDown();
		this.LPrimeTurn();
	}
	
	public void uPrimeTurn() {
		this.rotateLeft();
		this.DPrimeTurn();
	}
	public void uTurn() {
		this.rotateRight();
		this.DTurn();
	}
	
	public void fTurn() {
		this.rotateRollLeft();
		this.BTurn();
	}
	public void fPrimeTurn() {
		this.rotateRollRight();
		this.BPrimeTurn();
	}
	
	public void lTurn() {
		this.rotateDown();
		this.RTurn();
	}
	public void lPrimeTurn() {
		this.rotateUp();
		this.RPrimeTurn();
	}
	
	public void dTurn(){
		this.UPrimeTurn();
		this.rotateLeft();
	}
	
	public void dPrimeTurn(){
		this.UTurn();
		this.rotateRight();
	}
	
	// GETTERS AND SETTERS !!

	public Colour[][] getTop() {
		return top;
	}
	public void setTop(Colour[][] top) {
		this.top = top;
	}

	public Colour[][] getFront() {
		return front;
	}
	public void setFront(Colour[][] front) {
		this.front = front;
	}

	public Colour[][] getRight() {
		return right;
	}
	public void setRight(Colour[][] right) {
		this.right = right;
	}

	public Colour[][] getBack() {
		return back;
	}
	public void setBack(Colour[][] back) {
		this.back = back;
	}

	public Colour[][] getLeft() {
		return left;
	}
	public void setLeft(Colour[][] left) {
		this.left = left;
	}

	public Colour[][] getBottom() {
		return bottom;
	}
	public void setBottom(Colour[][] bottom) {
		this.bottom = bottom;
	}

	public void checkSolved() {
		int temp = 0; 
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < 3; i++) {
				if(
					top[0][0] == top[k][i] &&
					front[0][0] == front[k][i] &&
					right [0][0] == right[k][i] &&
					left [0][0] == left[k][i] &&
					back [0][0] == back[k][i] &&
					bottom [0][0] == bottom[k][i] ) 
				{
					temp++; 
				}
			}
		}
		
		if(temp == 9) 
		{
			this.isSolved = true;
			
		}
		else 
		{ 
			this.isSolved = false;  
		}
	}
	
	public boolean getIsSolved() {
		return this.isSolved;
	}
	public void setIsSolved(boolean solved) {
		this.isSolved = solved;
	}
}
