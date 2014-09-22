package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.QueryExecuter;
import com.hopon.dto.MessageDTO;
import com.mysql.jdbc.Statement;

public class MessageDAO {
	
	public void updateMessageSentStatus(Connection con, int[] messageId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE message set email_sent = 'Y' WHERE id IN (?)");
		String userIdStr = "";
		for(int x:messageId) {
			userIdStr += x + ",";
		}
		userIdStr = userIdStr.substring(0, userIdStr.length() - 1);
		
		PreparedStatement pstmt = con.prepareStatement(query.toString(),Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, userIdStr);
		pstmt.executeUpdate();
  		pstmt.getGeneratedKeys();
  		pstmt.close();
	}
	
	public List<MessageDTO> findEmailSendingMessage(Connection con) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("select id, content, sent_by, sent_user_id, receive_user_id, creation_time, read_time, status, sender_email_id, receiver_email_id, email_sent, subject  from message where email_sent = 'N' ");

		query.append(" ORDER BY creation_time DESC");
		PreparedStatement pstmt = con.prepareStatement(query.toString());		
		
		List<MessageDTO> message = new ArrayList<MessageDTO>();
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			MessageDTO msgTemp = new MessageDTO();
			msgTemp.setId(Integer.parseInt(rs.getString(1)));
			msgTemp.setContent(rs.getString(2));
			msgTemp.setSentBy(rs.getString(3));
			msgTemp.setSentUserId(rs.getString(4));
			msgTemp.setReceiveUserId(rs.getString(5));
			msgTemp.setCreationTime(rs.getString(6));
			msgTemp.setReadTime(rs.getString(7));
			msgTemp.setStatus(rs.getString(8));
			msgTemp.setSenderEmailId(rs.getString(9));
			msgTemp.setReceiverEmailId(rs.getString(10));
			msgTemp.setEmailSent(rs.getString(11).charAt(0));
			msgTemp.setSubject(rs.getString(12));
			message.add(msgTemp);
		}	
		rs.close();
		pstmt.close();
		return message;
	}
	
	public List<MessageDTO> findMessage(Connection con , String userid, String status, int slice, int limit) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("select id, content, sent_by, sent_user_id, receive_user_id, creation_time, read_time, status from message where receive_user_id = ? ");

		if(status != null) {
			query.append(" and status = '" +status+ "'");
		}
		query.append(" ORDER BY creation_time DESC limit " + limit + " OFFSET " + (limit * (slice - 1)));
		PreparedStatement pstmt = con.prepareStatement(query.toString());		
		pstmt.setString(1, userid);
		List<MessageDTO> message = new ArrayList<MessageDTO>();
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			MessageDTO msgTemp = new MessageDTO();
			msgTemp.setId(Integer.parseInt(rs.getString(1)));
			msgTemp.setContent(rs.getString(2));
			msgTemp.setSentBy(rs.getString(3));
			msgTemp.setSentUserId(rs.getString(4));
			msgTemp.setReceiveUserId(rs.getString(5));
			msgTemp.setCreationTime(rs.getString(6));
			msgTemp.setReadTime(rs.getString(7));
			msgTemp.setStatus(rs.getString(8));
			message.add(msgTemp);
		}	
		rs.close();
		pstmt.close();
		return message;
	}
	public List<MessageDTO> findAllMessage(Connection con , List<String> userid, String status, int slice, int limit) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("select id, content, sent_by, sent_user_id, receive_user_id, creation_time, read_time, status from message where id in (?) ");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		String useridstr = "0,";
		for(String i: userid) {
			useridstr += i + ",";
		}
		useridstr = useridstr.substring(0,useridstr.length() - 1);
		pstmt.setString(1, useridstr);
		if(status != null) {
			query.append(" and status = '" +status+ "'");
		}
		query.append(" ORDER BY creation_time DESC limit " + limit + " OFFSET " + (limit * (slice - 1)));
		List<MessageDTO> message = new ArrayList<MessageDTO>();
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			MessageDTO msgTemp = new MessageDTO();
			msgTemp.setId(Integer.parseInt(rs.getString(1)));
			msgTemp.setContent(rs.getString(2));
			msgTemp.setSentBy(rs.getString(3));
			msgTemp.setSentUserId(rs.getString(4));
			msgTemp.setReceiveUserId(rs.getString(5));
			msgTemp.setCreationTime(rs.getString(6));
			msgTemp.setReadTime(rs.getString(7));
			msgTemp.setStatus(rs.getString(8));
			message.add(msgTemp);
		}	
		rs.close();
		pstmt.close();
		return message;
	}
	
	public MessageDTO insertMessage(Connection con , MessageDTO dto) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("INSERT into message (id, content, sent_by, sent_user_id, receive_user_id, creation_time, read_time, status, sender_email_id, receiver_email_id, email_sent, subject) value(null, ?,?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement pstmt = con.prepareStatement(query.toString(),Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, dto.getContent());
		pstmt.setString(2, dto.getSentBy());
		pstmt.setString(3, dto.getSentUserId());
		pstmt.setString(4, dto.getReceiveUserId());
		pstmt.setString(5, dto.getCreationTime());
		pstmt.setString(6, dto.getReadTime());
		pstmt.setString(7, dto.getStatus());
		pstmt.setString(8, dto.getSenderEmailId());
		pstmt.setString(9, dto.getReceiverEmailId());
		pstmt.setString(10, String.valueOf(dto.getEmailSent()));
		pstmt.setString(11, dto.getSubject());
		pstmt.executeUpdate();
  		ResultSet tableKeys = pstmt.getGeneratedKeys();
  		tableKeys.next();
  		int autoGeneratedID = tableKeys.getInt(1);
  		tableKeys.close();
  		pstmt.close();
  		dto.setId(autoGeneratedID);
		return dto;
	}
	
	public MessageDTO updateMessageStatus(Connection con , MessageDTO dto) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE message set status = '"+ dto.getStatus() +"' WHERE id = " + dto.getId());
		PreparedStatement pstmt = con.prepareStatement(query.toString(),Statement.RETURN_GENERATED_KEYS);
		pstmt.executeUpdate();
  		pstmt.getGeneratedKeys();
  		pstmt.close();
		return dto;
	}
	public void updateMessageReadTime(Connection con , String messageId) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("select ISEMPTY(read_time) as test from message WHERE id = " + messageId);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		boolean test = false;
		while(rs.next()) {
			if(rs.getString(1).equalsIgnoreCase("0")) {
				test = true;
			} 
		}
		rs.close();
		pstmt.close();
		if(test) {
			query = new StringBuilder();
			query.append("UPDATE message set read_time = '" +ApplicationUtil.currentTimeStamp()+"' WHERE id = " + messageId);
			pstmt = con.prepareStatement(query.toString());
			pstmt.executeUpdate();
	  		pstmt.close();
		}		
	}
}
