package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hopon.dto.RecordDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.QueryExecuter;
import com.mysql.jdbc.Statement;

public class RecordDAO {
	public RecordDTO addRecord(Connection con, RecordDTO dto) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO record(id, user_id, circle_id, member_id, status, date) VALUES(?, ?, ?, ?, ?, '" +ApplicationUtil.currentTimeStamp()+"')");
		PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, null);
		pstmt.setInt(2, dto.getUserId());
		pstmt.setInt(3, dto.getCircleId());
		pstmt.setInt(4, dto.getMemberId());
		pstmt.setString(5, dto.getStatus());
		pstmt.setString(6, ApplicationUtil.dateFormat1.format(dto.getDate()));
		pstmt.executeUpdate();
		return dto;
	}
	
	/*
	 *Here is code to fetch list of date range between 2 dates.
	 *For particular user, circle and circle member.
	 */
	public List<String> fetchUserRecord(Connection con, final java.util.Date startDate, final java.util.Date endDate, int userId) throws SQLException {
		List<String> dtos = new ArrayList<String>();
		String pre = "";
		StringBuilder query = new StringBuilder();
		query.append("Select id, user_id, circle_id, member_id, status, date from record WHERE date < '"+ApplicationUtil.dateFormat1+"' AND user_id = '"+userId+"' order by date DESC LIMIT 1");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		if(rs.next() && rs.getString(5).equalsIgnoreCase("A")) pre = startDate.toString();
			
		pstmt.close();
		rs.close();
		
		query = new StringBuilder();
		query.append("Select id, user_id, circle_id, member_id, status, date from record WHERE date between '"+startDate+"' AND '"+endDate+"' AND user_id = '"+userId+"' order by date ASC ");
		pstmt = con.prepareStatement(query.toString());
		rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			if(pre.length() > 0) {
				dtos.add(pre+"--"+rs.getString(6));
				pre = "";
			}
			if(rs.getString(5).equalsIgnoreCase("A")) {
				pre = rs.getString(6);
			}
		}
		pstmt.close();
		rs.close();
		if(pre.length() > 0) {
			dtos.add(pre+"--"+endDate.toString());
			pre = "";
		}
		return dtos;
	}
	public List<String> fetchCircleRecord(Connection con, final java.util.Date startDate, final java.util.Date endDate, int circleId) throws SQLException {
		List<String> dtos = new ArrayList<String>();
		String pre = "";
		StringBuilder query = new StringBuilder();
		query.append("Select id, user_id, circle_id, member_id, status, date from record WHERE date < '"+ApplicationUtil.dateFormat1+"' AND circle_id = '"+circleId+"' order by date DESC LIMIT 1");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		if(rs.next() && rs.getString(5).equalsIgnoreCase("A")) pre = startDate.toString();
			
		pstmt.close();
		rs.close();
		
		query = new StringBuilder();
		query.append("Select id, user_id, circle_id, member_id, status, date from record WHERE date between '"+startDate+"' AND '"+endDate+"' AND circle_id = '"+circleId+"' order by date ASC ");
		pstmt = con.prepareStatement(query.toString());
		rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			if(pre.length() > 0) {
				dtos.add(pre+"--"+rs.getString(6));
				pre = "";
			}
			if(rs.getString(5).equalsIgnoreCase("A")) {
				pre = rs.getString(6);
			}
		}
		pstmt.close();
		rs.close();
		if(pre.length() > 0) {
			dtos.add(pre+"--"+endDate.toString());
			pre = "";
		}
		return dtos;
	}
	public List<String> fetchCircleMemberRecord(Connection con, final java.util.Date startDate, final java.util.Date endDate, int circleId, int memberId) throws SQLException {
		List<String> dtos = new ArrayList<String>();
		String pre = "";
		StringBuilder query = new StringBuilder();
		query.append("Select id, user_id, circle_id, member_id, status, date from record WHERE date < '"+ApplicationUtil.dateFormat1+"' AND circle_id = '"+circleId+"' AND member_id = '"+memberId+"' order by date DESC LIMIT 1");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		if(rs.next() && rs.getString(5).equalsIgnoreCase("A")) pre = startDate.toString();
			
		pstmt.close();
		rs.close();
		
		query = new StringBuilder();
		query.append("Select id, user_id, circle_id, member_id, status, date from record WHERE date between '"+startDate+"' AND '"+endDate+"' AND circle_id = '"+circleId+"' AND member_id = '"+memberId+"' order by date ASC ");
		pstmt = con.prepareStatement(query.toString());
		rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			if(pre.length() > 0) {
				dtos.add(pre+"--"+rs.getString(6));
				pre = "";
			}
			if(rs.getString(5).equalsIgnoreCase("A")) {
				pre = rs.getString(6);
			}
		}
		pstmt.close();
		rs.close();
		if(pre.length() > 0) {
			dtos.add(pre+"--"+endDate.toString());
			pre = "";
		}
		return dtos;
	}
}
