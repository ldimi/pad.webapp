package be.ovam.art46.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatArt46 {
	
	private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>();
	
	public static String formatDate(Date date) {
		return getFormatter().format(date);
	}
	
	public static Date getDate(String date) throws ParseException {
		if (date != null && date.length()>0) {
			return getFormatter().parse(date);
		}
		return null;
	}
	
	public static java.sql.Date getSQLDate(String date) throws ParseException {
		if (date != null && date.length()>0) {
			return new java.sql.Date(getFormatter().parse(date).getTime());
		}
		return null;
	}
	
	public static String getYear() {		
		Calendar today = Calendar.getInstance();		
		return ("" + today.get(Calendar.YEAR)).substring(2);
	}
	
	public static int getYear(String datum) throws ParseException {		
		Calendar date = Calendar.getInstance();	
		date.setTime(getDate(datum));
		return date.get(Calendar.YEAR);
	}
	
	
	public static String geMonth() {		
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH) + 1;
		if (month < 10) {
			return "0" + month;
		}
		return ("" + month);
	}
	
	public static boolean  isDate(String date) {	 
	    if (date == null || date.length() == 0) {
	        return true;
	    }
	    try
        {
            getFormatter().parse(date);
            return true;
        }
        catch (ParseException e)
        {
            return false;
        }	    
	}

	private static SimpleDateFormat getFormatter() {
	    SimpleDateFormat sdf = tl.get();
	    if(sdf == null) {
	        sdf = new SimpleDateFormat("dd.MM.yyyy");
	        tl.set(sdf);
	    }
	    return sdf;
	}
	
}
