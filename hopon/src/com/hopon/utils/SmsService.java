package com.hopon.utils;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
 
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

@SuppressWarnings("deprecation")
public class SmsService {
	
	
	
	public static void sendSms(String toNumber, String message) throws Exception {
		 
        SSLContext sslContext = SSLContext.getInstance("SSL");
 
        // set up a TrustManager that trusts everything
        sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                } 
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
 
                public void checkServerTrusted(X509Certificate[] certs, String authType) { } 
                public boolean isClientTrusted(X509Certificate[] arg0) {
                    return false;
                } 
                public boolean isServerTrusted(X509Certificate[] arg0) {
                    return false;
                }
            } }, new SecureRandom());
 
        SSLSocketFactory sf = new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme httpsScheme = new Scheme("https", sf, 443);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(httpsScheme);
        
        HttpParams params = new BasicHttpParams();
        ClientConnectionManager cm = new SingleClientConnManager(params, schemeRegistry);
 
        DefaultHttpClient client = new DefaultHttpClient(cm, params);
 
        client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),new UsernamePasswordCredentials(ApplicationUtil.smsExotelSid, ApplicationUtil.smsExotelToken));
        HttpPost post = new HttpPost(ApplicationUtil.exotelSmsSendUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
 
        nameValuePairs.add(new BasicNameValuePair("From", ApplicationUtil.smsExotelSenderId));
        
        nameValuePairs.add(new BasicNameValuePair("To", toNumber));
        nameValuePairs.add(new BasicNameValuePair("Body", message));
 
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String response = client.execute(post, responseHandler);
        System.out.println(response);
    }

}
