package be.ovam.art46.util;

import java.util.Calendar;
import java.util.Date;

public final class OvamCalenderUtils {
    public static Calendar dateToCalendar(Date date){
        if(date==null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;

    }
}
