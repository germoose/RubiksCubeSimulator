package ca.germuth.rubiks.util;

public class Utils {
	public static String milliToTime(int milli){
		String answer = "";
		
		//60 000 millis in minute
		//3 600 000 millis in hour
		int hours = milli / 360000;
		
		int mill2 = milli - (hours * 360000);
		int minutes = mill2 / 60000;
		
		int mill3 = mill2 - (minutes * 60000);
		int seconds = mill3 / 1000;
		
		int mill4 = mill3 - (seconds * 1000);
		
		if( hours > 0){
			answer = hours + ":";
		}
		
		String min = minutes + "";
		String sec = seconds + "";
		String mil = mill4 + "";
		
		if( min.length() == 1){
			min = "0" + min;
		}
		if( sec.length() == 1){
			sec = "0" + sec;
		}
		if( mil.length() == 1){
			mil = "00" + mil;;
		}
		if( mil.length() == 2){
			mil = "0" + mil;
		}
		answer = min + ":" + sec + "." + mil;
		
		return answer;
	}
}
