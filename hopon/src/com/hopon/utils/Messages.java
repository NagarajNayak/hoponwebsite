package com.hopon.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	public static ResourceBundle bundle = ResourceBundle.getBundle("resource.MessageResources");
	//errors
	private static final String REQUIRED = "error.required";
	private static final String INVALID = "error.invalid";
	private static final String EXCEEDED = "error.exceeded ";
	//labels
	private static final String STATUS = "label.status";
	private static final String UOM = "label.uom";
	
	//dropdownlist Common
	private static final String LOV = REQUIRED;
	
	private static String getValue(String key){
		return " "+bundle.getString(key);
	}
	public static String getValue(String key, Object... params  ) {
        try {
            return " "+MessageFormat.format(bundle.getString(key), params);
        } catch (MissingResourceException e) {
            
        }
		return "";
    }
	public String getRequired(){
		return getValue(REQUIRED);
	}
	public String getStatus(){
		return getValue(STATUS);
	}
	public String getUom(){
		return getValue(UOM);
	}
	public String getLov(){
		return getValue(LOV);
	}
	public String getInvalid(){
		return getValue(INVALID);
	}  

		public static String getExceeded() {
		return EXCEEDED;
	}
}
