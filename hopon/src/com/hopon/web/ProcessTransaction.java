package com.hopon.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hopon.dto.MessageBoardDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.ListOfValuesManager;
import com.hopon.utils.Validator;

/**
 * Servlet implementation class ProcessTransaction
 */
@WebServlet("/processTransaction")
public class ProcessTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		if(session != null && !Validator.isEmpty(session.getAttribute("userId"))) {
			ArrayList<String> parameterNames = new ArrayList<String>();
			Enumeration enumeration = request.getParameterNames();
		    while (enumeration.hasMoreElements()) {
		        String parameterName = (String) enumeration.nextElement();
		        parameterNames.add(parameterName);
		    }
		    for(String s:parameterNames) {
		    	out.print(s + "=>" + request.getParameter(s)+"<br>");
		    }
		    
		    /*
		     * 
		     * Fetch ORDER_ID
		     * Now change status in payment request table.
		     * 
		     * Now if status is success then update balance in users table(users->totalCredits).
		     * Make this code but for now comment it.
		     * 
		     * There is slight change.
		     * If admin manually changes payment status "D" (done), then only
		     * update balance in users table(users->totalCredits).
		     * 
		     */		    
		    
		    if(parameterNames.size() == 0) {
		    	//retuen error
		    } else if(request.getParameter("STATUS") != null && request.getParameter("STATUS").equals("TXN_SUCCESS")) {
		    	//transaction success
		    	float amount = Float.parseFloat(request.getParameter("TXNAMOUNT"));		    	
		    	float amountNew = (float) ((amount * 2.5)/100);
		    	float credit = Math.round(amount - amountNew);
		    	
		    	String html  ="";	
				html += "<p>Hi There,</p>";
				html += "<p>Thank you for purchasing HopOn Credits. Your order details are mentioned below.</p>";
				html += "<br/>";	
				html += "<p>Order ID :"+request.getParameter("ORDERID")+"</p>";
				html += "<p>Payment Transaction ID : "+request.getParameter("TXNID")+"</p>";
				try {
					html += "<p>Date &amp; time of order : "+ ApplicationUtil.dateFormat4.format(ApplicationUtil.dateFormat20.parse(request.getParameter("TXNDATE")))+"</p>";
				} catch (ParseException e) {  }
				html += "<p>Order Amount : Rs. "+request.getParameter("TXNAMOUNT")+"/- Only</p>";
				html += "<p>HopOn Credits : "+credit+"</p>";
				html += "<p>Transaction status : "+request.getParameter("STATUS")+"</p>";
				html += "<br/>";
				html += "<p>In case of failure of the transaction the amount will be credited in your respective bank / credit card account within 7 working days from the date of transaction.</p>";
				html += "<br/>";
				html += "<p>Happy to help,</p>";
				html += "<p>Team HopOn</p>";
				html += "<p>Email: info@hopon.co.in</p>";
				html += "<p>WebSite: http://www.hopon.co.in</p>";
				
		    	MessageBoardDTO msgDto = new MessageBoardDTO();
		    	msgDto.setEmailSubject("HopOn order Notification "+request.getParameter("ORDERID"));
		    	msgDto.setMessage(html);
		    	msgDto.setToMember(Integer.parseInt((String) session.getAttribute("userId")));
		    	msgDto.setMessageChannel("E");
		    	msgDto = ListOfValuesManager.getInsertedMessage(msgDto);
		    	
		    	msgDto.setCreatedBy(Integer.parseInt((String) session.getAttribute("userId")));
		    	
		    	String htmlOpt = "<section><div class='center'><div id='primary_content'><div class='setting_page' id='transaction_settings'><div class=''><div class='transaction_success transaction_main'><div class='success_div inner_div'><div class='transaction_header'><h2>Thank You! Your payment transaction is successful.</h2><div class='spacer'></div><br/><hr><label style='font-size: 17px;'>Transaction ID : </label><label style='font-size: 15px;'>";
		    	htmlOpt += request.getParameter("TXNID");
		    	htmlOpt += "</label> <br><div class='spacer'></div><label style='font-size: 17px;'>Order ID : </label><label style='font-size: 15px;'>";
		    	htmlOpt += request.getParameter("ORDERID");
				htmlOpt += "</label><br><div class='spacer'></div><label style='font-size: 17px;'>Amount : </label> <label style='font-size: 15px;'>";
				htmlOpt += request.getParameter("TXNAMOUNT");
				htmlOpt += "</label><br/><div class='spacer'></div><label style='font-size: 17px;'>Date and Time : </label><label style='font-size: 15px;'>";
				try {
					htmlOpt += ApplicationUtil.dateFormat4.format(ApplicationUtil.dateFormat20.parse(request.getParameter("TXNDATE")));
				} catch (ParseException e) { }
				htmlOpt += "</label><br/><br/><hr><div class='spacer'></div><p style='font-size: 17px'> An email reciept has been sent to your email id <br/>For any further queries please note down your Transaction ID <a href = 'http://www.hopon.co.in/contactus' style='text-decoration: underline'>HopOn Care</a></p></div></div><p style='font-size: 17px;text-align: center;font-style: bold; padding: inherit;'> <b>Need help? Write to us at <a href = 'http://www.hopon.co.in/contactus' style='text-decoration: underline'>HopOn Care</a></b></p></div></div></div></div></div></div></section>";
		    	
		    	
		    } else {
		    	String errorMessage = "";
		    	if(request.getParameter("RESPCODE") != null) {
		    		if(request.getParameter("RESPCODE").equals("141")) errorMessage = "Sorry! Your payment transaction has been cancelled.";
		    		else errorMessage = "Sorry! Your payment transaction has failed.";
		    	}
		    }	
		} else {
			//not authenticated.
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
