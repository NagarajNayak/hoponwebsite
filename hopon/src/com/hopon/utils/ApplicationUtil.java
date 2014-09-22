package com.hopon.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
public class ApplicationUtil{

	public static String datePattern1 = "yyyy-MM-dd";
	public static String datePattern2 = "dd-MMM-yyyy HH:mm";
	public static String datePattern3 = "yyyy-MM-dd HH:mm:ss";
	public static String datePattern4 = "dd MMM yy, hh.mm a";
	public static String datePattern5 = "E MMM dd HH:mm:ss Z yyyy";
	public static String datePattern6 = "MM/dd/yyyy";
	public static String datePattern7 = "dd-MMM-yyyy";
	public static String datePattern8 = "yyyy-MM-dd HH:mm";
	public static String datePattern9 = "dd-MMM-yyyy hh:mm a";
	public static String datePattern10 = "E MMM dd H:m:s zzz yyyy";
	public static String datePattern11 = "yyyy/MM/dd";
	public static String datePattern12 = "dd MMM yy";
	public static String datePattern13 = "hh:mm a";
	public static String datePattern14 = "HH";
	public static String datePattern15 = "mm";
	public static String datePattern16 = "HH:mm";
	public static String datePattern17 = "HH:mm:ss";
	public static String datePattern19 = "MMMM";
	public static String datePattern20 = "M/d/yyyy HH:mm:ss a";
	
	public static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
	public static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateFormat4 = new SimpleDateFormat("dd MMM yy, hh.mm a");
	public static SimpleDateFormat dateFormat5 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
	public static SimpleDateFormat dateFormat6 = new SimpleDateFormat("MM/dd/yyyy");
	public static SimpleDateFormat dateFormat7 = new SimpleDateFormat("dd-MMM-yyyy");
	public static SimpleDateFormat dateFormat8 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat dateFormat9 = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
	public static SimpleDateFormat dateFormat10 = new SimpleDateFormat("E MMM dd H:m:s zzz yyyy");
	public static SimpleDateFormat dateFormat11 = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat dateFormat12 = new SimpleDateFormat("dd MMM yy");
	public static SimpleDateFormat dateFormat13 = new SimpleDateFormat("hh:mm a");
	public static SimpleDateFormat dateFormat14 = new SimpleDateFormat("HH");
	public static SimpleDateFormat dateFormat15 = new SimpleDateFormat("mm");
	public static SimpleDateFormat dateFormat16 = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat dateFormat17 = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat dateFormat18 = new SimpleDateFormat("E");
	public static SimpleDateFormat dateFormat19 = new SimpleDateFormat("MMMM");
	public static SimpleDateFormat dateFormat20 = new SimpleDateFormat("M/d/yyyy HH:mm:ss a");

	public static String currentTimeStamp() { return dateFormat3.format(new Date()); }
	public static String currentDate() { return dateFormat1.format(new Date()); }
	
	public static String seatAbsent = "NO SEAT";
	public static String rideStatus = "NEW";
	public static String catalinaDirectoryPath = System.getProperty("catalina.home");
	public static String fileSeperator = File.separator;
	public static String vehicleDirectoryPath = catalinaDirectoryPath + fileSeperator + "webapps" + fileSeperator + "vehicles" + fileSeperator;
	public static String userDirectoryPath = catalinaDirectoryPath + fileSeperator + "webapps" + fileSeperator + "users" + fileSeperator;
	public static String demoDirectoryPath = catalinaDirectoryPath + fileSeperator + "webapps" + fileSeperator + "demo" + fileSeperator;
	public static String vehicleDemoFile = "VehicleBulkUploadFormat.xls";
	public static String userDemoFile = "UserBulkUploadFormat.xls";

	public static String vehicleDownloadFile = "VehicleBulkUploadFormat.xls";
	public static String userDownloadFile = "UserBulkUploadFormat.xls";

	public static final String smsExotelSid = "hopon";
	public static final String smsExotelToken = "0af2b4d0b9efc2867ef4b1ecd209f7c728fffe5d";
	public static final String smsExotelSenderId = "08030752414";//08071727370
	public static final String exotelSmsSendUrl = "https://"+smsExotelSid+":"+smsExotelToken+"@twilix.exotel.in/v1/Accounts/"+smsExotelSid+"/Sms/send";
	
	public static boolean compareDates(String date1, String date2, String someRandomTime) {
		if(Validator.isEmpty(date1) || Validator.isEmpty(date2) || Validator.isEmpty(someRandomTime)) return false;
		String[] arr1 = date1.split(":");
		String[] arr2 = date2.split(":");
		String[] arr3 = someRandomTime.split(":");

		if(arr1.length <= 2 || arr2.length <= 2 || arr3.length <= 2) return false;
		
		int a = Integer.parseInt(arr1[0]) * 60 + Integer.parseInt(arr1[1]);
		int b = Integer.parseInt(arr2[0]) * 60 + Integer.parseInt(arr2[1]); 
		int c = Integer.parseInt(arr3[0]) * 60 + Integer.parseInt(arr3[1]);
		if(c > b && c < a) {
			return false;
		} else {
			return true;
		}	}

	public static String passwordEncrypt(String str) {
		return passwordEncrypt(str, 16);
	}
	
	public static String md5String(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			
			 byte byteData[] = md.digest();
			 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
		 
	        return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		
	} 

	public static String passwordEncrypt(String str, int length) {
		if(str.length() > length) str = str.substring(0, length - 1);
		str = String.format("%1$-" + length + "s", str); 
		String retstr = "";
		for(int i=0;i<length;i++) {
			String sch = str.substring(i, i+1);
			int iasc = (int)sch.charAt(0);
			iasc = iasc + 2 * i + 30;
			if(iasc > 255) {
				iasc = iasc - 255;     
			}
			retstr = retstr + (char)iasc ;
		}
		String newstr = "";
		for(int i=0;i<retstr.length();i++) {
			newstr += ((int)(retstr.charAt(i))) + "*";
		}
		return newstr.substring(0, newstr.length() - 1);
	}

	public static List calculateTimeWindowSettings(String start_pt,String via_pt,String end_pt,int maxWaitTime,String start_time) throws IOException, JSONException {
		String start_point = start_pt.replace(' ', ',');
		String end_point = end_pt.replace(' ', ',');
		String via_point = via_pt.replace(' ', ',');
		if(via_point == null || via_point.trim().length() == 0) {
			via_point = start_point;
		}
		URL apiurl;

        BufferedReader in;
        String content = "";
		apiurl = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + start_point + "&destination=" + end_point + "&waypoints=" + via_point + "&sensor=false");
		in = new BufferedReader(new InputStreamReader(apiurl.openStream()));
		String inputLine;
        while ((inputLine = in.readLine()) != null) {
        	content += inputLine;
        }
        in.close();
        JSONObject obj = new JSONObject(content);
		String Status = (String) obj.get("status");
		List result = new ArrayList();
		if(Status.equalsIgnoreCase("OVER_QUERY_LIMIT")){
			Map<String, String> response = new HashMap<String, String>();
			response.put("type", "fail");
			response.put("message", "Google API not responding.please try later");
			JSONObject obj2 = new JSONObject(response);
		}else{
			JSONArray obj1 = obj.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
			
			int length = 0;
			int totalSeconds = 0;
			for(int i=0;i<obj1.length();i++) {
				JSONObject obj2 = obj1.getJSONObject(i);
				int distance = (Integer) obj2.getJSONObject("distance").get("value");
				int duration = (Integer) obj2.getJSONObject("duration").get("value");
				length += distance;
				totalSeconds += duration;
			}
			double pool_slack_seconds = 0.3 * totalSeconds;
			//String pool_slack_time = TimeUnit.SECONDS.
			//pool_slack_seconds = 360000;
			int day = (int) TimeUnit.SECONDS.toDays((long) pool_slack_seconds);
		    //long hours = TimeUnit.SECONDS.toHours((long) pool_slack_seconds) - TimeUnit.DAYS.toHours(day);
		    long hours = TimeUnit.SECONDS.toHours((long) pool_slack_seconds);
		    long minute = TimeUnit.SECONDS.toMinutes((long) pool_slack_seconds) - 
		                  TimeUnit.DAYS.toMinutes(day) -
		                  TimeUnit.HOURS.toMinutes(hours);
		    long second = TimeUnit.SECONDS.toSeconds((long) pool_slack_seconds) -
		                  TimeUnit.DAYS.toSeconds(day) -
		                  TimeUnit.HOURS.toSeconds(hours) - 
		                  TimeUnit.MINUTES.toSeconds(minute);
		    
		    String pool_slack_time = hours+":"+minute+":"+second;
		    hours = TimeUnit.SECONDS.toHours((long) totalSeconds);
		    minute = TimeUnit.SECONDS.toMinutes((long) totalSeconds) - 
		                  TimeUnit.DAYS.toMinutes(day) -
		                  TimeUnit.HOURS.toMinutes(hours);
		    second = TimeUnit.SECONDS.toSeconds((long) totalSeconds) -
		                  TimeUnit.DAYS.toSeconds(day) -
		                  TimeUnit.HOURS.toSeconds(hours) - 
		                  TimeUnit.MINUTES.toSeconds(minute);
		    String direct_distance = hours+":"+minute+":"+second;
		    
		  //for calculating seperate time window use RideGiver&RideSeeker basis start time.
			
			//time window for start time early.
		    String twindow_start_time_e = start_time;
		    
		    //fetch from user_preferences
		    int max_waittime = maxWaitTime;
		    DateFormat dateFormat = new SimpleDateFormat(ApplicationUtil.datePattern3);
		    Date start = null;
		    try {
				start = dateFormat.parse(twindow_start_time_e);
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(start);
			cal.add(Calendar.MINUTE, max_waittime);
		    
		    String twindow_start_time_l = dateFormat.format(cal.getTime());
		    String x1[] = direct_distance.split(":");

		    int dir_hours = Integer.parseInt(x1[0]);
		    int dir_minutes = Integer.parseInt(x1[1]);
		    int dir_seconds = Integer.parseInt(x1[2]);
		    
		    Date start_tm = null;
		    try {
				start_tm = dateFormat.parse(twindow_start_time_e);
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}
			cal = Calendar.getInstance();
			cal.setTime(start_tm);
			cal.add(Calendar.HOUR, dir_hours);
			cal.add(Calendar.MINUTE, dir_minutes);
			cal.add(Calendar.SECOND, dir_seconds);
			String twindow_end_time_e = dateFormat.format(cal.getTime());
			
			Date start_l = null;
			try {
				start_l = dateFormat.parse(twindow_start_time_l);
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}
			cal = Calendar.getInstance();
			cal.setTime(start_l);
			cal.add(Calendar.HOUR, dir_hours);
			cal.add(Calendar.MINUTE, dir_minutes);
			cal.add(Calendar.SECOND, dir_seconds);
			String timewindow_destination = dateFormat.format(cal.getTime());
			
			String x2[] = pool_slack_time.split(":");

		    int hoursx = Integer.parseInt(x2[0]);
		    int minutes = Integer.parseInt(x2[1]);
		    int seconds = Integer.parseInt(x2[2]);

		    Date start_time_end = null;
			try {
				start_time_end = dateFormat.parse(timewindow_destination);
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			}
		    cal = Calendar.getInstance();
			cal.setTime(start_time_end);
			cal.add(Calendar.HOUR, hoursx);
			cal.add(Calendar.MINUTE, minutes);
			cal.add(Calendar.SECOND, seconds);
			String twindow_end_time_l = dateFormat.format(cal.getTime());
		    	
		    
		    result.add(direct_distance);
		    result.add(twindow_start_time_e);
		    result.add(twindow_start_time_l);
		    result.add(twindow_end_time_e);
		    result.add(twindow_end_time_l);
		    result.add(length);
		    
		}
		return result;
				
        
	}
}
