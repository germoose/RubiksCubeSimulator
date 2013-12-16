package ca.germuth.rubiks.database;

public class SolveTime {
	private long rowID;
	private long solveTime;
	private int puzzleType;
	
	public SolveTime( long rId, long solve, int puzzle){
		this.rowID = rId;
		this.solveTime = solve;
		this.puzzleType = puzzle;
	}

	/**
	 * @return the rowID
	 */
	public long getRowID() {
		return rowID;
	}

	/**
	 * @param rowID the rowID to set
	 */
	public void setRowID(long rowID) {
		this.rowID = rowID;
	}

	/**
	 * @return the solveTime
	 */
	public long getSolveTime() {
		return solveTime;
	}

	/**
	 * @param solveTime the solveTime to set
	 */
	public void setSolveTime(long solveTime) {
		this.solveTime = solveTime;
	}

	/**
	 * @return the puzzleType
	 */
	public int getPuzzleType() {
		return puzzleType;
	}

	/**
	 * @param puzzleType the puzzleType to set
	 */
	public void setPuzzleType(int puzzleType) {
		this.puzzleType = puzzleType;
	}
	
	
}
