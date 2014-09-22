package com.hopon.utils;

import java.util.Date;
import java.util.regex.Pattern;

public class Validator {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		String doubleValue = "13453456465464645645632545345325";
		////System.out.println(Pattern.matches("[0-9]+[\\.]?([0-9]{1,2})?", doubleValue));
	}

	public static boolean isEmpty(Object string) {
		return string == null || string.toString().trim().equalsIgnoreCase("");
	}
	public static boolean isEmptyOnlyNull(Object string) {
		return string == null ;
	}

	public static boolean isNotEmpty(Object string) {
		return !isEmpty(string);
	}
	public static Object getNullIfEmpty(Object object) {
		return isEmpty(object)?null:object;
	}

	public static boolean isNumber(String number) {
		return isEmpty(number)?false:Pattern.matches("[0-9]+", number);
	}
	public static boolean isNotNumber(String number) {
		return !isNumber(number);
	}

	public static boolean isYear(String number) {
		return (isEmpty(number)?false:Pattern.matches("[0-9]{4,4}", number))?new Integer(number)>1600:false;
	}
	public static boolean isNotYear(String number) {
		return !isYear(number);
	}
	public static boolean isMoreThanCurrentYear(String number){
		@SuppressWarnings("deprecation")
		int currentYear = new Date().getYear()+1900;
		return isYear(number) ? new Integer(number) > currentYear : false;
	}
	public static boolean isPhone(String number) {
		return isEmpty(number)?false:Pattern.matches("[0-9+()-]+", number);
	}
	public static boolean isNotPhone(String number) {
		return !isPhone(number);
	}
	public static boolean isNotInsideTheRange(String str,int min,int max) {
		return !isInsideTheRange(str, min, max);
	}

	public static boolean isName(String string) {
		return isEmpty(string)?false:Pattern.matches("[^#}{%$@~?\\^\\+<>`]+", string);
	}
	public static boolean isNotName(String string) {
		return !isName(string);
	}

	public static boolean isSpecialName(String string) {
		return isEmpty(string)?false:Pattern.matches("[a-zA-Z0-9\\-._&\\s()/]*", string);
	}
	public static boolean isNotSpecialName(String string) {
		return !isSpecialName(string);
	}
	public static boolean isAlphaNumeric(String string) {
		return isEmpty(string)?false:Pattern.matches("[a-zA-Z0-9\\s]+", string);
	}
	public static boolean isFieldName(String string) {
		return isEmpty(string)?false:Pattern.matches("[a-zA-Z0-9\\s]+", string);
	}
	public static boolean isNotFieldName(String string) {
		return !isSpecialName(string);
	}

	public static boolean isIDField(String string) {
		return isEmpty(string)?false:Pattern.matches("[a-zA-Z0-9\\-._]+", string);
	}
	public static boolean isNotIDField(String string) {
		return !isIDField(string);
	}

	public static boolean isRole(String role) {
		return isEmpty(role)?false:Pattern.matches("[0-9a-zA-Z\\s/]+", role);
	}
	public static boolean isNotRole(String role) {
		return !isRole(role);
	}

	public static boolean isLocation(String location) {
		return isEmpty(location)?false:Pattern.matches("[0-9a-zA-Z\\s\\.]+",location);
	}
	public static boolean isNotLocation(String location) {
		return !isLocation(location);
	}


	public static boolean isInsideTheRange(String str,int min,int max) {
		int length = str.length();
		boolean result = true;
		if(length<2) {
			result = false;
		}else if(length>10) {
			result = false;
		}
		return result;
	}
	public static boolean isEmail(String email) {
		return isEmpty(email)?false:Pattern.matches("[a-zA-Z0-9\\._%-]+@[a-zA-Z0-9\\._%-]+\\.[a-zA-Z]{2,4}", email);
	}
	public static boolean isNotEmail(String email) {
		return !isEmail(email);
	}
	public static boolean isCurrency(String value){
		return isFloat(value,2);
	}
	public static boolean isFloat(String value,int scale){
		return isEmpty(value)?false:Pattern.matches("[0-9]+[\\.]?([0-9]{0," + scale + "})?", value);
	}
	public static boolean isNotFloat(String value,int scale){
		return !isFloat(value,scale);
	}
	public static boolean isFloat(String value){
		return isFloat(value,2);
	}
	public static boolean isNotFloat(String value){
		return !isFloat(value);
	}

	public static boolean isDate(String date){
		return DateHelper.isValideDate(date);
	}
	public static boolean isNumberZeroNotAlloed(String number) {
		if (isEmpty(number)?false:Pattern.matches("[0-9]+", number)){
			if (Integer.parseInt(number)==0) {
				return false;
			}else
				return true;	
		}
		return false;
	}
	public static boolean isNotNumberZeroNotAlloed(String number) {
		return !isNumberZeroNotAlloed(number);
	}

}
