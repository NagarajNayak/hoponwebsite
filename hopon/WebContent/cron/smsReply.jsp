<%@page import="com.hopon.action.UserAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setContentType("text/plain");
response.setContentLength(0);


new UserAction().smsReplyOperation(request.getParameter("SmsSid"), request.getParameter("From"), request.getParameter("To"), request.getParameter("Body"), request.getParameter("Date"));

//out.print(request.getParameter("Body"));
%>