package application.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import application.c4c.C4CCons;

public class Utils {
	
	
	
	private static DateFormat checkForTimeZone(String format){
		if(format.matches(C4CCons.C4CTIMEFORMAT)){
			DateFormat formatter = new SimpleDateFormat(format);
			TimeZone timeZone = TimeZone.getTimeZone(C4CCons.C4CTIMEZONEID);
			formatter.setTimeZone(timeZone);
			return formatter;
		}else{
			return new SimpleDateFormat(format);
		}
	}
	
	public static String getCurrentTimeAsString(String format){
		DateFormat formatter = checkForTimeZone(format);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(Calendar.getInstance().getTime()); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, -2);
		return formatter.format(cal.getTime());
	}
	
	public static Date getCurrentTime(){
		return Calendar.getInstance().getTime();
	}
	
	public static long calculateMillisBetweenTwoDates(Date endDate, Date startDate){
		return endDate.getTime() - startDate.getTime();
	}
	
	public static String formatDateToNewFormat(String oldFormat, String newFormat, String time, String timeZoneID) throws ParseException{
		Date date = new SimpleDateFormat(oldFormat).parse(time);
		DateFormat formatter = new SimpleDateFormat(newFormat);
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);
		formatter.setTimeZone(timeZone);
		return formatter.format(date);
	}
	
	public static String formatDateToNewFormat(String oldFormat, String newFormat, String time) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		Date date = new SimpleDateFormat(oldFormat).parse(time);
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		DateFormat formatter = new SimpleDateFormat(newFormat);
		return formatter.format(date);
	}
	
	public static String addMinutesToTimeString(String time, String timeFormat, int minutes) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		Date date = new SimpleDateFormat(timeFormat).parse(time);
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, minutes * 60 * 1000);
		DateFormat formatter = new SimpleDateFormat(timeFormat);
		return formatter.format(calendar.getTime());
	}
	
	public static Date formatStringToDate(String format, String time) throws ParseException{
		return new SimpleDateFormat(format).parse(time);
	}
	
	public static String getTimeOfMillis(String format, Date date){
		return new SimpleDateFormat(format).format(date.getTime());
	}
	
	public static String getStartOfDay(String format){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Calendar.getInstance().getTime());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    calendar.add(Calendar.HOUR_OF_DAY, -2);
	    return new SimpleDateFormat(format).format(calendar.getTime());
	}
	
	public static String getHoursOfToday(long millis, int hours){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Calendar.getInstance().getTime());
		TimeZone timeZone = TimeZone.getTimeZone("CET");
		calendar.setTimeZone(timeZone);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    calendar.add(Calendar.MILLISECOND, (int) millis);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return new SimpleDateFormat(C4CCons.C4CTIMEFORMAT).format(calendar.getTime());
	}
	
	public static String getEndOfDay(String format){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Calendar.getInstance().getTime());
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 0);
	    calendar.add(Calendar.HOUR_OF_DAY, -2);
	    return new SimpleDateFormat(format).format(calendar.getTime());
	}
	
	public static String convertSysTimeToToday(String time, String format) throws ParseException{
		Date date = new SimpleDateFormat("HH:mm:ss").parse(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//TimeZone timeZone = TimeZone.getTimeZone("CET");
		//cal.setTimeZone(timeZone);
		long millis = date.getTime();
		return getHoursOfToday(millis, -1);
		
	}
	
	public static String changeFromGMTToGivenTimeZone(String time, String timeZoneID) throws ParseException{
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);
		TimeZone gmtZone = TimeZone.getTimeZone("GMT");
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTimeZone(gmtZone);
	    cal.setTime(formatStringToDate(C4CCons.C4CTIMEFORMAT, time)); // sets calendar time/date
	    cal.setTimeZone(timeZone);
	    if(timeZoneID.matches("CET")){
	    	cal.add(Calendar.HOUR_OF_DAY, 2);
	    }
	    cal.getTime();
	    DateFormat formatter = new SimpleDateFormat(C4CCons.C4CTIMEFORMAT);		
		return formatter.format(cal.getTime());	
	}
}
