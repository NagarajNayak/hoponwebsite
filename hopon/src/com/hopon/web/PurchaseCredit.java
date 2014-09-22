package com.hopon.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hopon.utils.Validator;

/**
 * Servlet implementation class PurchaseCredit
 */
@WebServlet("/PurchaseCredit")
public class PurchaseCredit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		if(session != null && !Validator.isEmpty(session.getAttribute("userId"))) {
			float amountDue = (Float) request.getAttribute("amount");
			if(amountDue > 0) {
				
				String orderId = "ORDS"+Math.round((Math.random()*100000000));
				//Add record into payment request table.
				
				
				com.paytm.merchant.CheckSumServiceHelper checkSumServiceHelper = com.paytm.merchant.CheckSumServiceHelper.getCheckSumServiceHelper();
				
				ResourceBundle bundle = ResourceBundle.getBundle("resource.paytm");
				
				TreeMap<String,String> parameters = new TreeMap<String,String>();
				String merchantKey = bundle.getString("paytm.merchantKey"); //Key provided by Paytm
				parameters.put("MID", bundle.getString("paytm.MID")); // Merchant ID (MID) provided by Paytm
				parameters.put("ORDER_ID", orderId); // Merchant’s order id
				parameters.put("CUST_ID", "CUST"+session.getAttribute("userId")); // Customer ID registered with merchant
				parameters.put("TXN_AMOUNT", String.valueOf(amountDue));
				parameters.put("CHANNEL_ID", bundle.getString("paytm.CHANNEL_ID"));
				parameters.put("INDUSTRY_TYPE_ID", bundle.getString("paytm.INDUSTRY_TYPE_ID")); //Provided by Paytm
				parameters.put("WEBSITE", bundle.getString("paytm.WEBSITE")); //Provided by Paytm
		
				String checkSum = "";
				//Note: Above mentioned parameters are not complete list of parameters. Please refer integration document for additional parameters which need to be passed.
				try {
					checkSum = checkSumServiceHelper.genrateCheckSum(merchantKey, parameters);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				out.print("<html><head><title>Merchant Check Out Page</title></head><body><center><h1>Please do not refresh this page...</h1></center>");
				out.print("<form method='post' action='https://secure.paytm.in/oltp-web/processTransaction' name='f1'>");
				//out.print("<form method='post' action='http://localhost/hopdwld/captureImage/pgRedirect' name='f1'>");
				out.print("<table><tbody>");
		
				for(Entry<String, String> entry : parameters.entrySet()) {
					  String key = entry.getKey();
					  String value = entry.getValue();
		
					  System.out.println(key + " => " + value);
				
					out.print("<input type='hidden' name='"+key+"' value='"+value+"'>");
				}
				out.print("<input type='hidden' name='CHECKSUMHASH' value='"+checkSum+"'>");
				out.print("</tbody></table><script type='text/javascript'>document.f1.submit();</script></form></body></html>");
			}
		} else {
			response.sendRedirect("/");
		}
	}

}
