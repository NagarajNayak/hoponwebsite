package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hopon.dto.PaymentPlanDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.QueryExecuter;
import com.mysql.jdbc.Statement;

public class PaymentPlanDAO {
	public List<PaymentPlanDTO> fetchAllPlanListForUser(Connection con, String userId) throws SQLException {
		List<PaymentPlanDTO> dtos = new ArrayList<PaymentPlanDTO>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT p.plan_id, p.circle_id, p.user_id, p.type, p.plan, p.key1, p.value1, p.key2, p.value2, p.key3, p.value3, p.key4, p.value4, p.key5, p.value5, c.Name, u.first_name from paymentplan p LEFT OUTER JOIN users u ON p.user_id = u.id LEFT OUTER JOIN circles c ON p.circle_id = c.Circle_Id WHERE u.id = '"+userId+"'");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			PaymentPlanDTO dto = new PaymentPlanDTO();
			dto.setPlanId(rs.getInt(1));
			dto.setCircleId(rs.getInt(2));
			dto.setUserId(rs.getInt(3));
			dto.setType(rs.getString(4));
			dto.setPlan(rs.getString(5));
			dto.setKey1(rs.getString(6));
			dto.setValue1(rs.getFloat(7));
			dto.setKey2(rs.getString(8));
			dto.setValue2(rs.getFloat(9));
			dto.setKey3(rs.getString(10));
			dto.setValue3(rs.getFloat(11));
			dto.setKey4(rs.getString(12));
			dto.setValue4(rs.getFloat(13));
			dto.setKey5(rs.getString(14));
			dto.setValue5(rs.getFloat(15));
			dto.setCircleName(rs.getString(16));
			dto.setUserName(rs.getString(17));
			Map<String, Float> charges = new HashMap<String, Float>();
			if(dto.getKey1() != null && dto.getKey1().length() > 0) charges.put(dto.getKey1(), dto.getValue1());
			if(dto.getKey2() != null && dto.getKey2().length() > 0) charges.put(dto.getKey2(), dto.getValue2());
			if(dto.getKey3() != null && dto.getKey3().length() > 0) charges.put(dto.getKey3(), dto.getValue3());
			if(dto.getKey4() != null && dto.getKey4().length() > 0) charges.put(dto.getKey4(), dto.getValue4());
			if(dto.getKey5() != null && dto.getKey5().length() > 0) charges.put(dto.getKey5(), dto.getValue5());
			dto.setCharges(charges);
			dtos.add(dto);
		}
		rs.close();
  		pstmt.close();
		return dtos;
	}
	public List<PaymentPlanDTO> fetchAllPlanListForCircle(Connection con, int circleId) throws SQLException {
		List<PaymentPlanDTO> dtos = new ArrayList<PaymentPlanDTO>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT p.plan_id, p.circle_id, p.user_id, p.type, p.plan, p.key1, p.value1, p.key2, p.value2, p.key3, p.value3, p.key4, p.value4, p.key5, p.value5, c.Name, u.first_name from paymentplan p LEFT OUTER JOIN users u ON p.user_id = u.id LEFT OUTER JOIN circles c ON p.circle_id = c.Circle_Id WHERE WHERE c.Circle_Id = '"+circleId+"'");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			PaymentPlanDTO dto = new PaymentPlanDTO();
			dto.setPlanId(rs.getInt(1));
			dto.setCircleId(rs.getInt(2));
			dto.setUserId(rs.getInt(3));
			dto.setType(rs.getString(4));
			dto.setPlan(rs.getString(5));
			dto.setKey1(rs.getString(6));
			dto.setValue1(rs.getFloat(7));
			dto.setKey2(rs.getString(8));
			dto.setValue2(rs.getFloat(9));
			dto.setKey3(rs.getString(10));
			dto.setValue3(rs.getFloat(11));
			dto.setKey4(rs.getString(12));
			dto.setValue4(rs.getFloat(13));
			dto.setKey5(rs.getString(14));
			dto.setValue5(rs.getFloat(15));
			dto.setCircleName(rs.getString(16));
			dto.setUserName(rs.getString(17));
			Map<String, Float> charges = new HashMap<String, Float>();
			if(dto.getKey1() != null && dto.getKey1().length() > 0) charges.put(dto.getKey1(), dto.getValue1());
			if(dto.getKey2() != null && dto.getKey2().length() > 0) charges.put(dto.getKey2(), dto.getValue2());
			if(dto.getKey3() != null && dto.getKey3().length() > 0) charges.put(dto.getKey3(), dto.getValue3());
			if(dto.getKey4() != null && dto.getKey4().length() > 0) charges.put(dto.getKey4(), dto.getValue4());
			if(dto.getKey5() != null && dto.getKey5().length() > 0) charges.put(dto.getKey5(), dto.getValue5());
			dto.setCharges(charges);
			dtos.add(dto);
		}
		rs.close();
  		pstmt.close();
		return dtos;
	}
	public List<PaymentPlanDTO> fetchAllPlanList(Connection con) throws SQLException {
		List<PaymentPlanDTO> dtos = new ArrayList<PaymentPlanDTO>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT p.plan_id, p.circle_id, p.user_id, p.type, p.plan, p.key1, p.value1, p.key2, p.value2, p.key3, p.value3, p.key4, p.value4, p.key5, p.value5, c.Name, u.first_name from paymentplan p LEFT OUTER JOIN users u ON p.user_id = u.id LEFT OUTER JOIN circles c ON p.circle_id = c.Circle_Id");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			PaymentPlanDTO dto = new PaymentPlanDTO();
			dto.setPlanId(rs.getInt(1));
			dto.setCircleId(rs.getInt(2));
			dto.setUserId(rs.getInt(3));
			dto.setType(rs.getString(4));
			dto.setPlan(rs.getString(5));
			dto.setKey1(rs.getString(6));
			dto.setValue1(rs.getFloat(7));
			dto.setKey2(rs.getString(8));
			dto.setValue2(rs.getFloat(9));
			dto.setKey3(rs.getString(10));
			dto.setValue3(rs.getFloat(11));
			dto.setKey4(rs.getString(12));
			dto.setValue4(rs.getFloat(13));
			dto.setKey5(rs.getString(14));
			dto.setValue5(rs.getFloat(15));
			dto.setCircleName(rs.getString(16));
			dto.setUserName(rs.getString(17));
			Map<String, Float> charges = new HashMap<String, Float>();
			if(dto.getKey1() != null && dto.getKey1().length() > 0) charges.put(dto.getKey1(), dto.getValue1());
			if(dto.getKey2() != null && dto.getKey2().length() > 0) charges.put(dto.getKey2(), dto.getValue2());
			if(dto.getKey3() != null && dto.getKey3().length() > 0) charges.put(dto.getKey3(), dto.getValue3());
			if(dto.getKey4() != null && dto.getKey4().length() > 0) charges.put(dto.getKey4(), dto.getValue4());
			if(dto.getKey5() != null && dto.getKey5().length() > 0) charges.put(dto.getKey5(), dto.getValue5());
			dto.setCharges(charges);
			dtos.add(dto);
		}
		rs.close();
  		pstmt.close();
		return dtos;
	}
	public PaymentPlanDTO insertPaymentPlan(Connection con, PaymentPlanDTO dto) throws SQLException {

		StringBuilder query = new StringBuilder();
		boolean addTest = true;
		query.append(" SELECT plan_id from paymentplan WHERE circle_id = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, dto.getCircleId());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		if(rs.next() && rs.getInt(1) > 0) {
			addTest = false;
			dto.setPlanId(rs.getInt(1));
		}
		rs.close();
  		pstmt.close();
		
  		if(addTest) {
	  		query = new StringBuilder();
			query.append(" INSERT INTO paymentplan(plan_id, circle_id, user_id, type, plan, key1, value1, key2, value2, key3, value3, key4, value4, key5, value5, created_by, created_dt) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			
			pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
	  		pstmt.setInt(1, dto.getPlanId());
			pstmt.setInt(2, dto.getCircleId());
			pstmt.setInt(3, dto.getUserId());
			pstmt.setString(4, dto.getType());
			pstmt.setString(5, dto.getPlan());
			pstmt.setString(6, dto.getKey1());
			pstmt.setFloat(7, dto.getValue1());
			pstmt.setString(8, dto.getKey2());
			pstmt.setFloat(9, dto.getValue2());
			pstmt.setString(10, dto.getKey3());
			pstmt.setFloat(11, dto.getValue3());
			pstmt.setString(12, dto.getKey4());
			pstmt.setFloat(13, dto.getValue4());
			pstmt.setString(14, dto.getKey5());
			pstmt.setFloat(15, dto.getValue5());
			pstmt.setInt(16, 1);
			pstmt.setString(17, ApplicationUtil.currentTimeStamp());
			pstmt.executeUpdate();
	  		ResultSet tableKeys = pstmt.getGeneratedKeys();
	  		tableKeys.next();
	  		int autoGeneratedID = tableKeys.getInt(1);
	  		dto.setPlanId(autoGeneratedID);
	  		pstmt.close();
  		} else {
  			query = new StringBuilder();
			query.append(" UPDATE paymentplan set circle_id = ?, type = ?, plan = ?, key1 = ?, value1 = ?, key2 = ?, value2 = ?, key3 = ?, value3 = ?, key4 = ?, value4 = ?, key5 = ?, value5 = ?, update_by =?, update_dt = ? WHERE plan_id = ? ");
			
			pstmt = con.prepareStatement(query.toString());
			pstmt.setInt(1, dto.getCircleId());
			pstmt.setString(2, dto.getType());
			pstmt.setString(3, dto.getPlan());
			pstmt.setString(4, dto.getKey1());
			pstmt.setFloat(5, dto.getValue1());
			pstmt.setString(6, dto.getKey2());
			pstmt.setFloat(7, dto.getValue2());
			pstmt.setString(8, dto.getKey3());
			pstmt.setFloat(9, dto.getValue3());
			pstmt.setString(10, dto.getKey4());
			pstmt.setFloat(11, dto.getValue4());
			pstmt.setString(12, dto.getKey5());
			pstmt.setFloat(13, dto.getValue5());
			pstmt.setInt(14, 1);
			pstmt.setString(15, ApplicationUtil.currentTimeStamp());
	  		pstmt.setInt(16, dto.getPlanId());
			pstmt.executeUpdate();
	  		pstmt.close();
  		}
		return dto;
	}
}
