package be.ovam.art46.util;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.StringUtils;

/**
 * Created by Koen Corstjens on 28-8-13.
 */
public class OvamCustomNumberEditor extends CustomNumberEditor {

    private final Class<? extends Number> ovamNumberClass;

    private final boolean ovamAllowEmpty;


    public OvamCustomNumberEditor(Class<? extends Number> numberClass,  boolean allowEmpty) throws IllegalArgumentException {
        super(numberClass, allowEmpty);
        this.ovamNumberClass = numberClass;
        ovamAllowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        text = StringUtils.replace(text, ".", "");
        text = StringUtils.replace(text, ",", ".");
        super.setAsText(text);
    }


}
