package be.ovam.art46.util;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Koen on 21/03/14.
 */
public class OvamCustomCalendarEditor extends CustomDateEditor {
    public static final String PATTERN_DATE = "dd-MM-yyyy";
    public static final DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE, new Locale("nl"));


    public OvamCustomCalendarEditor(boolean allowEmpty) {
        super(dateFormat, allowEmpty);
    }

    public String getAsText() {
        Object value = this.getValue();
        if (value instanceof Calendar) {
            this.setValue(((Calendar) value).getTime());
        }
        return super.getAsText();
    }

    public void setAsText(String text) {
        super.setAsText(text);
        Object value = super.getValue();
        if (value instanceof Date) {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) value);
            this.setValue(cal);
        }
    }
}
