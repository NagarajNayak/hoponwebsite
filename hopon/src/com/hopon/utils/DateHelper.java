package com.hopon.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Nagamohan
 *
 */
public class DateHelper {
	public final static int FROM_DATE_EMPTY = -3;
	public final static int TO_DATE_EMPTY = -2;
	public final static int FROM_DATE_LESS = -1;
	public final static int DATES_EQUAL = 0;
	public final static int FROM_DATE_GREATER = 1;
	public static final String DATEFORMAT = "dd-MMM-yyyy";
	
	public String getDefualtDateFormate() {
		return DATEFORMAT;
	}
	
	public static void main(String[] args) {
		Date fromDate = DateHelper.getDate("Aug 8, 2009");
		Date currentDate = DateHelper.getDate("Sep 07, 2009");
		@SuppressWarnings("unused")
		Date toDate = DateHelper.getDate("Mar 12, 2010");
		@SuppressWarnings("unused")
		int i = calculateDateDifference(fromDate , currentDate);
		////System.out.println(i);
		//String date= DateHelper.getFormatedDate(null);
        
		////System.out.println(getDateDifference(null,currentDate));
		////System.out.println(getDateDifference(currentDate,currentDate));
		////System.out.println(getDateDifference(toDate,currentDate));
	}

	@SuppressWarnings("deprecation")
	public static int getDateDifference(Date fromDate,Date toDate) {
		if(Validator.isEmpty(fromDate) && Validator.isEmpty(toDate)) {
			return DATES_EQUAL;
		}
		if(Validator.isEmpty(fromDate)) {
			return FROM_DATE_EMPTY;
		}
		if(Validator.isEmpty(toDate)) {
			return TO_DATE_EMPTY;
		}
		int fromYear = fromDate.getYear();
		int fromMonth = fromDate.getMonth();
		int fromDay = fromDate.getDate();

		int toYear = toDate.getYear();
		int toMonth = toDate.getMonth();
		int toDay = toDate.getDate();

		if(fromYear == toYear) {
			if(fromMonth < toMonth) {
				return FROM_DATE_LESS;
			}else if(fromMonth == toMonth) {
				if(fromDay < toDay) {
					return FROM_DATE_LESS;
				}else if(fromDay == toDay) {
					return DATES_EQUAL;
				}else {
					return FROM_DATE_GREATER;
				}
			}else {
				return FROM_DATE_GREATER;
			}

		}else if(fromYear < toYear) {
			return FROM_DATE_LESS;
		}else {
			return FROM_DATE_GREATER;
		}
	}
	
	public static int daysBetween(Calendar startDate, Calendar endDate) {
		  Calendar date = (Calendar) startDate.clone();
		  int daysBetween = 0;
		  while (date.before(endDate)) {
		    date.add(Calendar.DAY_OF_MONTH, 1);
		    daysBetween++;
		  }
		  return daysBetween;
	}
	
	/**
	 * coverting Date type value to Timestamp value.
	 * @param date
	 * @return Timestamp
	 */
	public static Timestamp getTimestamp(Date date) {
		return date!=null?new Timestamp(date.getTime()):null;
	}
	/**
	 * coverting Timestamp type value to Date value.
	 * @param timestamp
	 * @return server current date
	 */
	public static Date getDate(Timestamp timestamp){
		Date date = new Date(timestamp.getTime());
		return date;
	}
	/**
	 * server current date
	 * @return date
	 */
	public static Date getCurrentDate(){
		return new Date();
	}
	/**
	 * default date formatted Date value into String value
	 * @return String
	 */
	public String getFormatedCurrentDate(){
		return getFormatedCurrentDate(DATEFORMAT);
	}
	/**
	 * custom date formatted Date value into String value
	 * @param dateformat
	 * @return String
	 */
	public String getFormatedCurrentDate(String dateformat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		return sdf.format(getCurrentDate());
	}
	/**
	 *
	 * @param date
	 * @param dateformat
	 * @return String
	 */
	public static String getFormatedDate(Date date,String dateformat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		return sdf.format(date);
	}
	/**
	 *
	 * @param date
	 * @return String
	 */
	public static String getFormatedDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		return sdf.format(date);
	}
	/**
	 * custom date formatted Date value
	 * @param sdate
	 * @param dateformat
	 * @return
	 */
	public static Date getDate(String sdate, String dateformat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Date date = null;
		try {
			date = sdf.parse(sdate.trim());
		} catch (ParseException e) {
		}
		return date;
	}
	/**
	 * default date formatted Date value
	 * @param sdate
	 * @return
	 */
	public static Date getDate(String sdate) {
		return getDate(sdate,DATEFORMAT);
	}
	/**
	 * default format date Validation check
	 * @param date
	 * @return
	 */
	public static boolean isValideDate(String date) {
		return isValideDate(date,DATEFORMAT);
	}
	/**
	 * customer format date Validation check
	 * @param date
	 * @param dateformat
	 * @return
	 */
	public static boolean isValideDate(String date,String dateformat) {
		if(Validator.isEmpty(date)){
			return false;
		}
		if (getDate(date, dateformat) == null) {
			return false;
		}
		return true;
	}
	/**
	 * customer format date Validation check
	 * @param date
	 * @param dateformat
	 * @return
	 */
	public static int calculateDateDifference(Date enteredDate, Date currentDate) {
		int result = 0;
		
        Calendar mrDate = Calendar.getInstance();
        mrDate.setTime(enteredDate);
        ////System.out.println(mrDate.get(Calendar.DATE)+"-"+(mrDate.get(Calendar.MONTH)+1)+"-"+mrDate.get(Calendar.YEAR));
        mrDate.add(Calendar.DATE, 30);
        ////System.out.println(mrDate.get(Calendar.DATE)+"-"+(mrDate.get(Calendar.MONTH)+1)+"-"+mrDate.get(Calendar.YEAR));
        
        Calendar today = Calendar.getInstance();       
        today.setTime(currentDate);
        ////System.out.println(today.get(Calendar.DATE)+"-"+(today.get(Calendar.MONTH)+1)+"-"+today.get(Calendar.YEAR));
        
        if(mrDate.getTime().before(today.getTime()) || enteredDate.after(currentDate)){
        	result = -1;
        }else{
        	result = 1;
        }
        ////System.out.println(result);
        return result;
	}
	@SuppressWarnings("deprecation")
	public static int calculateDifferenceYears(Date a, Date b){
		int aY = a.getYear();
		int bY = b.getYear();
		
		if (aY == bY) return 0;
		else return aY < bY ? 0 : aY - bY;
	}
}
