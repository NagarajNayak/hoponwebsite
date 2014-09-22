package com.hopon.filters;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet Filter implementation class AuthorizationFilter
 */
public class AuthorizationFilter implements Filter {

    /**
     * Default constructor.
     */
    public AuthorizationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
   		
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        @SuppressWarnings("unused")
		HttpSession session = request.getSession(false);
        
        @SuppressWarnings("unused")
		ServletContext application =  request.getSession().getServletContext();
    	String scheme = request.getScheme();
    	String serverName = request.getServerName();
    	String serverPort = request.getServerPort()+"";
    	//@SuppressWarnings("unused")
		//String serverPath = scheme+"://"+serverName+":"+serverPort;
        //@SuppressWarnings("unused")
		String currentURL = request.getRequestURI();
        //serverPath += currentURL.substring(0, currentURL.indexOf("/", 1));
    	String serverPath = request.getContextPath();
        //session.setAttribute("url", serverPath + currentURL.substring(0, currentURL.substring(1).indexOf('/') + 2));
        //req.setAttribute("url", serverPath + currentURL.substring(0, currentURL.substring(1).indexOf('/') + 2));
        String extension = currentURL;
		if(extension.indexOf("?") >= 0) extension = extension.substring(0, extension.indexOf("?"));
		if(extension.indexOf(".") >= 0) 
			extension = extension.substring(extension.lastIndexOf(".") + 1);
		else
			extension = "";
		
		List<String> extensionsAllowed = new ArrayList<String>(Arrays.asList("css", "js", "gif", "png", "jpg", "eot", "svg", "ttf", "woff", "jspx", "ico"));
		
        String url;
        if(currentURL.contains("/pages/verification-failure.xhtml") || currentURL.contains("/pages/verification-success.xhtml") || currentURL.contains("/pages/signup.xhtml") || currentURL.contains("/pages/faq.xhtml") ||  currentURL.contains("/pages/contact.xhtml") || currentURL.contains("/emailCron.xhtml")){
        	 chain.doFilter(request, response);
        	 return;
        }
        //System.out.println("msgggg"+currentURL);
        //if(currentURL.contains("/pages/provider/") ||currentURL.contains("/pages/consumer/") || currentURL.contains("/pages/taxiowner/")){
        if(currentURL.contains("/pages/settings/") || currentURL.contains("/pages/rides/") || currentURL.contains("/pages/taxi/") || currentURL.contains("/pages/provider/") || currentURL.contains("/pages/consumer/") || currentURL.contains("/pages/taxiowner/")){
    		url = serverPath+"/pages/signin.xhtml";
    		if(session != null && session.getAttribute("LoggedIn") != null) {
    			session.setAttribute("uri", currentURL);
    		}
    	} else {
        	url = serverPath+"/pages/signin.xhtml";
        }
       // System.out.println("msgggg"+url);
        if (!request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip JSF resources (CSS/JS/Images/etc)
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.
        }		
		if(extensionsAllowed.contains(extension)) {
			chain.doFilter(request, response);
            return;
		}
       // ((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=EmulateIE7");
       if((session != null && session.getAttribute("LoggedIn") != null) || currentURL.indexOf("/cron/") >= 0 || currentURL.indexOf("/emailCron.xhtml") >= 0 || currentURL.indexOf("/signin.xhtml") >= 0 || currentURL.indexOf("/public/") >= 0 || currentURL.contains("javax.faces.resource")) {
    	   chain.doFilter(request, response);
       } else {
    	   response.sendRedirect(url);
    	   
       }
    
     }
		// pass the request along the filter chain
        //response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
