package ca.germuth.rubiks.database;

public class SolveTime {
	private int id;
//	private String hour;
//	private String minute;
//	private String seconds;
//	private String milli;
	private String time;
	
	public SolveTime(){
		this.id = -1;
		this.time = null;
	}
	
	public SolveTime(int id, String time){
		this.id = id;
		this.time = time;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the hour
	 */
//	public String getHour() {
//		return hour;
//	}
//
//	/**
//	 * @param hour the hour to set
//	 */
//	public void setHour(String hour) {
//		this.hour = hour;
//	}
//
//	/**
//	 * @return the minute
//	 */
//	public String getMinute() {
//		return minute;
//	}
//
//	/**
//	 * @param minute the minute to set
//	 */
//	public void setMinute(String minute) {
//		this.minute = minute;
//	}
//
//	/**
//	 * @return the seconds
//	 */
//	public String getSeconds() {
//		return seconds;
//	}
//
//	/**
//	 * @param seconds the seconds to set
//	 */
//	public void setSeconds(String seconds) {
//		this.seconds = seconds;
//	}
//
//	/**
//	 * @return the milli
//	 */
//	public String getMilli() {
//		return milli;
//	}
//
//	/**
//	 * @param milli the milli to set
//	 */
//	public void setMilli(String milli) {
//		this.milli = milli;
//	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
