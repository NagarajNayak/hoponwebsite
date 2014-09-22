package com.hopon.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

public class ServerUtility {
	private static String ipAddress = "";
	private static String logoURL="";
	private static String siteType = "";
	private static String helpURL = "";
	private static String serverURL = "";
	static final String strPossible = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ%*";
	static Random rnd = new Random();
	
	public static String getServerURL() {
		return serverURL;
	}

	public static void setServerURL(String serverURL) {
		ServerUtility.serverURL = serverURL;
	}
	public String getHelpURL() {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession();
		siteType = (String)session.getAttribute("siteType");
		if (siteType==null) siteType="";
		if (siteType.equalsIgnoreCase("sto")){
			helpURL = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/help/helps.html";
		}else{
			helpURL = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/help/help.html";
		}
		return helpURL;
	}
	public static String getSiteType() {
		siteType = "ORL";
		return siteType.toUpperCase();
	}
	public static void setSiteType(String siteType) {
		ServerUtility.siteType = siteType;
	}

	@SuppressWarnings("static-access")
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}	
	
	public static String getIpAddress() {
		try {
			InetAddress ipaddress = InetAddress.getLocalHost();
			ipAddress = ipaddress.getHostAddress();
		} catch (UnknownHostException e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
		}
		
		return ipAddress;
	}
	public static long getIPNumber(){
		String ip = getIpAddress();
		String[] ipnumerals = ip.split("\\.");
		return 16777216*(Integer.parseInt(ipnumerals[0])) + 65536*(Integer.parseInt(ipnumerals[1])) + 256*(Integer.parseInt(ipnumerals[2])) + (Integer.parseInt(ipnumerals[3]));	
	}
	@SuppressWarnings("static-access")
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public static String getHTTPBasePath() {
		return getHTTPBasePath(getRequest());
	} 
	public static String getHTTPBasePath(HttpServletRequest request) {
/*		String path = request.getContextPath();
		String _basePath  = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
*/		
		String servletPath = request.getServletPath();
		String requestURL = request.getRequestURL().toString();
		String _basePath = requestURL.substring(0,requestURL.indexOf(servletPath));
		return _basePath;
	}
	public static String getJNDIBasePath() {

		return getJNDIBasePath(getRequest());
	}
	public static String getJNDIBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String _basePath = "jndi:/"+request.getServerName()+path;

		return _basePath;
	}
	public static HttpServletRequest getRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request;
	}
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	public static ServletContext getApplication() {
		return getSession().getServletContext();
	}
	public String getDateFormat() {
		return DateHelper.DATEFORMAT;
	}
	

	public static String getExtension(UploadedFile item)
	{
		String extension = "";
		int i = item.getFileName().lastIndexOf('.');
		if (i > 0) {
			extension = (item.getFileName().substring(i+1)).toLowerCase();
		}

		return extension;
	}

	
	public static String constructTargetFileName(String placeId, String extension, String lookupFolder) {
		int baseFileName = 1;
		String targetFilePath = placeId + "_" + baseFileName + "." +extension;
		
		while ((new java.io.File(lookupFolder + targetFilePath)).exists()) {
			baseFileName++;
			targetFilePath = placeId + "_" + baseFileName + "." + extension;
		}
		return targetFilePath;
	}
	
	public static String randomString( int len )  {
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( strPossible.charAt( rnd.nextInt(strPossible.length()) ) );
	   return sb.toString();
	}

	public static void main(String[] args) {
   


		//System.out.println(result);
	}
	
}
