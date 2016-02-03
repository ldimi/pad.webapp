package be.ovam.art46.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class GlobalBindingInitializer {
	
	private static String dashFormat = "dd-MM-yyyy";
	private static String dotFormat = "dd.MM.yyyy";

	private int autoGrowCollectionLimit = 2024;

	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(autoGrowCollectionLimit);
	}

	@InitBinder
	public void registerCustomEditors(WebDataBinder binder, WebRequest request) {
		// lege Strings worden als null ingevuld:
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				if (StringUtils.isBlank(value)) {
					setValue(null);
					return;
				}
				String format;
				if (value.contains("-")) {
					format = dashFormat;
				} else {
					format = dotFormat;
				}
				try {
					setValue(new SimpleDateFormat(format).parse(value));
				} catch (ParseException e) {
					throw new IllegalArgumentException("Ongeldige datum : " + value);
				}
			}

		    public String getAsText() {
		    	Date theDate = (Date) getValue();
				if (theDate == null) {
					return "";
				}
		        return new SimpleDateFormat(dotFormat).format(theDate);
		    }        


		});
	}

}