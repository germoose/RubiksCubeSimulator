package ca.germuth.rubiks.cube;

import java.util.Random;

import ca.germuth.rubiks.listener.MoveListener;

/**
 * GenericCube
 * 
 * A cube of size n is represented by this class.
 * x = 0, left
 * x = end, right
 * y = 0, top
 * y = end, bottom
 * z = 0, front
 * z = end, back
 * 
 * 
 * 


             00  01  02
             10  11  12
             20  21  22

00  01  02   00  01  02   00  01  02
10  11  12   10  11  12   10  11  12
20  21  22   20  21  22   20  21  22
  
             00  01  02
             10  11  12
             20  21  22

             00  01  02
             10  11  12
             20  21  22

   
   				Back
   				
   	 Left        Top         Right
   	 
   	            Front
   	            
   	            Bottom
   
   
   
   
 
 * @author Administrator
 */
public class GenericCube {
	private Colour[][] top;
	private Colour[][] front;
	private Colour[][] right;
	private Colour[][] back;
	private Colour[][] left;
	private Colour[][] bottom;
	private int size;
	private boolean isSolved;
	
	public GenericCube(int size){
		this.size = size;
		this.top = new Colour[size][size];
		this.front = new Colour[size][size];
		this.right = new Colour[size][size];
		this.back = new Colour[size][size];
		this.left = new Colour[size][size];
		this.bottom = new Colour[size][size];
		this.solve();
	}
	
	public void solve(){
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				this.top[i][j] = Colour.WHITE;
				this.front[i][j] = Colour.GREEN;
				this.bottom[i][j] = Colour.YELLOW;
				this.back[i][j] = Colour.BLUE;
				this.right[i][j] = Colour.RED;
				this.left[i][j] = Colour.ORANGE;
			}
		}
	}
	
	public void RTurn(){
		sideTurn(size - 1);
		rotateFace(this.right);
	}
	
	public void RPrimeTurn(){
		this.RTurn();
		this.RTurn();
		this.RTurn();
	}
	
	public void LPrimeTurn(){
		sideTurn(0);
		rotateFace(this.left);
		rotateFace(this.left);
		rotateFace(this.left);
	}
	
	public void LTurn(){
		this.LPrimeTurn();
		this.LPrimeTurn();
		this.LPrimeTurn();
	}
	public void UTurn(){
		topTurn(0);
		rotateFace(this.top);
	}
	
	public void UPrimeTurn(){
		this.UTurn();
		this.UTurn();
		this.UTurn();
	}
	
	public void DPrimeTurn(){
		topTurn(size - 1);
		rotateFace(this.bottom);
		rotateFace(this.bottom);
		rotateFace(this.bottom);
	}
	
	public void DTurn(){
		this.DPrimeTurn();
		this.DPrimeTurn();
		this.DPrimeTurn();
	}
	/**
	 * if i = size - 1
	 * F Turn
	 * 
	 * if i = 0
	 * B Prime Turn
	 * 
	 * @param i
	 */
	private void frontTurn(int i){
		Colour[] temp = new Colour[size];
		//saves the top
		for(int j = 0; j < size; j++){
			temp[j] = this.top[i][j];
		}
		
		for(int j = 0; j < size; j++){
			//replace top with left
			this.top[i][j] = this.left[i][j];
		}
		for(int j = 0; j < size; j++){
			//replace left with bottom
			this.left[i][j] = this.bottom[size - i - 1][size - j - 1];
		}
		for(int j = 0; j < size; j++){
			//replace bottom with right
			this.bottom[size - i - 1][size - j - 1] = this.right[i][j];
		}
		for(int j = 0; j < size; j++){
			//replace top
			this.right[i][j] = temp[j];
		}	
	}
	
	public void FTurn(){
		frontTurn(size - 1);
		rotateFace(this.front);
	}
	
	public void FPrimeTurn(){
		this.FTurn();
		this.FTurn();
		this.FTurn();
	}
	
	public void BPrimeTurn(){
		frontTurn(0);
		rotateFace(this.back);
		rotateFace(this.back);
		rotateFace(this.back);
	}
	
	public void BTurn(){
		BPrimeTurn();
		BPrimeTurn();
		BPrimeTurn();
	}
	/**
	 * If i = size - 1
	 * DPrimeTurn
	 * 
	 * If i = 0
	 * UTurn
	 * @param i
	 */
	private void topTurn(int i){
		Colour[] temp = new Colour[size];
		
		//saves the front
		for(int j = 0; j < size; j++){
			temp[j] = this.front[i][j];
		}
		
		for(int j = 0; j < size; j++){
			//replace front with right
			this.front[i][j] = this.right[size - j - 1][i];
		}
		for(int j = 0; j < size; j++){
			//replace right with back
			this.right[size - j - 1][i] = this.back[size - i - 1][size - j - 1];
		}
		for(int j = 0; j < size; j++){
			//replace back with left
			this.back[size - i - 1][j] = this.left[size - j - 1][size - i - 1];
		}
		for(int j = 0; j < size; j++){
			//replace left
			this.left[size - j - 1][size - i - 1] = temp[size - j - 1];
		}
		
	}
	
	/**
	 * If j = size - 1
	 * RTurn
	 * 
	 * If j = 0
	 * L'Turn
	 * @param j
	 */
	private void sideTurn(int j){
		Colour[] temp = new Colour[size];
		
		//saves the top for later
		for(int i = 0; i < size; i++){
			temp[i] = this.top[i][j];
		}
		
		for(int i = 0; i < size; i++){
			//replace top with front
			this.top[i][j] = this.front[i][j];
		}
		for(int i = 0; i < size; i++){
			//replace front with bottom
			this.front[i][j] = this.bottom[i][j];
		}
		for(int i = 0; i < size; i++){
			//replace bottom with back
			this.bottom[i][j] = this.back[i][j];
		}
		for(int i = 0; i < size; i++){
			//replace back with top
			this.back[i][j] = temp[i];
		}	
	}
	
	/**
	 * Rotates a face of the cube 90 degrees Uses matrix notation
	 * 
	 * @param face
	 */
	private void rotateFace(Colour[][] face) {
		// face is an M x N matrix
		final int M = face.length;
		final int N = face[0].length;
		
		Colour[][] faceCopy = new Colour[N][M];
		//create copy
		for (int i = 0; i < face.length; i++) {
			Colour[] row = face[i];
			int length = row.length;
			faceCopy[i] = new Colour[length];
			System.arraycopy(row, 0, faceCopy[i], 0, length);
		}

		// r designates row
		for (int r = 0; r < M; r++) {
			// c designates column
			for (int c = 0; c < N; c++) {
				face[c][M - 1 - r] = faceCopy[r][c];
			}
		}
	}
	
	//YTurn rotates the cube on U
	public void yTurn(){
		for(int i = 0; i < size; i++){
			topTurn(i);
		}
		this.rotateFace(this.top);
		this.rotateFace(this.bottom);
		this.rotateFace(this.bottom);
		this.rotateFace(this.bottom);
	}
	
	public void yPrimeTurn(){
		yTurn();
		yTurn();
		yTurn();
	}
	
	//rotates the cube on R
	public void xTurn(){
		for(int i = 0; i < size; i++){
			sideTurn(i);
		}
		this.rotateFace(this.right);
		this.rotateFace(this.left);
		this.rotateFace(this.left);
		this.rotateFace(this.left);
	}
	
	//rotates the cube on F
	public void zTurn(){
		for(int i = 0; i < size; i++){
			frontTurn(i);
		}
		this.rotateFace(this.front);
		this.rotateFace(this.back);
		this.rotateFace(this.back);
		this.rotateFace(this.back);
	}
	
	public void zPrimeTurn(){
		zTurn();
		zTurn();
		zTurn();
	}
	
	public void xPrimeTurn(){
		xTurn();
		xTurn();
		xTurn();
	}
	
	public void rTurn() {
		this.xTurn();
		this.LTurn();
	}
	public void rPrimeTurn() {
		this.xPrimeTurn();
		this.LPrimeTurn();
	}
	
	public void uPrimeTurn() {
		this.yPrimeTurn();
		this.DPrimeTurn();
	}
	public void uTurn() {
		this.yTurn();
		this.DTurn();
	}
	
	public void fTurn() {
		this.zTurn();
		this.BTurn();
	}
	
	public void fPrimeTurn() {
		this.zPrimeTurn();
		this.BPrimeTurn();
	}
	
	public void lTurn() {
		this.xPrimeTurn();
		this.RTurn();
	}
	public void lPrimeTurn() {
		this.xTurn();
		this.RPrimeTurn();
	}
	
	public void dTurn(){
		this.UTurn();
		this.yPrimeTurn();
	}
	
	public void dPrimeTurn(){
		this.UPrimeTurn();
		this.yTurn();
	}
	
	public void scrambleCube() {
		Random randomGenerator = new Random();
		String scramble = "";
		for(int i = 0; i<2; i++) {
			int r = randomGenerator.nextInt(10);
			switch(r) {
			case 0: this.UTurn(); scramble += ("U ;"); break;
			case 1: this.UPrimeTurn(); scramble += ("U' ;"); break;
			case 2: this.RTurn(); scramble += ("R ;"); break;
			case 3: this.RPrimeTurn(); scramble += ("R' ;"); break;
			case 4: this.LTurn(); scramble += ("L ;"); break;
			case 5: this.LPrimeTurn(); scramble += ("L' ;"); break;
			case 6: this.FTurn(); scramble += ("F ;"); break;
			case 7: this.FPrimeTurn(); scramble += ("F' ;"); break;
			case 8: this.DTurn(); scramble += ("D ;"); break;
			case 9: this.DPrimeTurn(); scramble += ("D' ;"); break;
			//case 10: this.BTurn(); scramble += ("B ;"); break;
			//case 11: this.BPrimeTurn(); scramble += ("B' ;"); break;
			}
		}
		MoveListener.setScramble(scramble);
		System.out.println("");
	}
	
	public void checkSolved() {
		int temp = 0; 
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
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
		
		if(temp == size*size) 
		{
			this.isSolved = true;
			
		}
		else 
		{ 
			this.isSolved = false;  
		}
	}

	/**
	 * @return the top
	 */
	public Colour[][] getTop() {
		return top;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(Colour[][] top) {
		this.top = top;
	}

	/**
	 * @return the front
	 */
	public Colour[][] getFront() {
		return front;
	}

	/**
	 * @param front the front to set
	 */
	public void setFront(Colour[][] front) {
		this.front = front;
	}

	/**
	 * @return the right
	 */
	public Colour[][] getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(Colour[][] right) {
		this.right = right;
	}

	/**
	 * @return the back
	 */
	public Colour[][] getBack() {
		return back;
	}

	/**
	 * @param back the back to set
	 */
	public void setBack(Colour[][] back) {
		this.back = back;
	}

	/**
	 * @return the left
	 */
	public Colour[][] getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(Colour[][] left) {
		this.left = left;
	}

	/**
	 * @return the bottom
	 */
	public Colour[][] getBottom() {
		return bottom;
	}

	/**
	 * @param bottom the bottom to set
	 */
	public void setBottom(Colour[][] bottom) {
		this.bottom = bottom;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the isSolved
	 */
	public boolean isSolved() {
		return isSolved;
	}

	/**
	 * @param isSolved the isSolved to set
	 */
	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}
	
	
}
