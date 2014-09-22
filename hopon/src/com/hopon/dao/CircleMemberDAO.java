package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hopon.dto.CircleMemberDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.QueryExecuter;


public class CircleMemberDAO {

	public CircleMemberDTO createCircleMember(Connection con, CircleMemberDTO circleMemberDTO, String senderId)throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("select count(*) from circle_members WHERE CircleId = ? AND MemberId = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, circleMemberDTO.getCircleid());
		pstmt.setString(2, circleMemberDTO.getUserid());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		boolean addTest = true;
		while(rs.next()) {
			if(rs.getInt(1) > 0) {
				addTest = false;
			}
		}
		rs.close();
		pstmt.close();
		
		query = new StringBuilder();
		if(addTest) {			
			query.append("INSERT INTO circle_members (CircleId,MemberId, status, CreatedDT, requestUserId, CreatedBy) VALUES (?,?,?,'" +ApplicationUtil.currentTimeStamp()+"', ?, ?) ");	
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, circleMemberDTO.getCircleid());
			pstmt.setString(2, circleMemberDTO.getUserid());
			if(circleMemberDTO.getStatus()!=null){
				pstmt.setString(3,circleMemberDTO.getStatus());
			} else {
				pstmt.setString(3,"0");
			}
			if(circleMemberDTO.getRequestUserId() != null) {
				pstmt.setString(4,circleMemberDTO.getRequestUserId());
			} else {
				pstmt.setString(4, "0");
			}
			pstmt.setString(5, senderId);
			
		} else {			
			query.append("UPDATE circle_members set UpdatedDT = '" +ApplicationUtil.currentTimeStamp()+"', status = ?, requestUserId = ?, UpdatedBy = ? WHERE CircleId = ? AND MemberId = ? ");
			pstmt = con.prepareStatement(query.toString());
			if(circleMemberDTO.getStatus()!=null){
				pstmt.setString(1,circleMemberDTO.getStatus());
			} else {
				pstmt.setString(1,"0");
			}	
			if(circleMemberDTO.getRequestUserId() != null) {
				pstmt.setString(2,circleMemberDTO.getRequestUserId());
			} else {
				pstmt.setString(2, "0");
			}
			pstmt.setString(3, senderId);
			pstmt.setString(4, circleMemberDTO.getCircleid());
			pstmt.setString(5, circleMemberDTO.getUserid());
		}		
		pstmt.executeUpdate();
		pstmt.close();
		
		return circleMemberDTO;
	}
       public CircleMemberDTO confirmOrDeclineUser(Connection con ,CircleMemberDTO circledto, String senderId)throws SQLException {
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE circle_members SET UpdatedBy = '"+ senderId +"', UpdatedDT = '" +ApplicationUtil.currentTimeStamp()+"', status = '" + circledto.getStatus() + "' where CircleId ='" + circledto.getCircleid() + "' and MemberId= '" + circledto.getUserid() + "'");
		
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.executeUpdate();
		pstmt.close();
		
		return circledto;
	}
	public void makeCircleMemberInactiveForUser(Connection con, UserRegistrationDTO userRegistrationDto, String senderId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE circle_members SET UpdatedBy = '"+ senderId +"', Status = '2' WHERE MemberId = " + userRegistrationDto.getId());
		
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.executeUpdate();
		pstmt.close();
	}
	public boolean testSingleCircleMember(Connection con, CircleMemberDTO circleMemberDTO, String id, String ctype, String ttype) throws SQLException {
		StringBuilder query = new StringBuilder();
		if (ttype.equals("T")){
			query.append("select count(*) from circle_members cm LEFT OUTER JOIN circles c ON cm.CircleId = c.Circle_Id WHERE c.`status` = '1' AND cm.`Status` = '1' AND cm.MemberId = '"+circleMemberDTO.getUserid()+"' AND c.CircleOwner_Member_Id_P != '"+circleMemberDTO.getUserid()+"'");
			PreparedStatement pstmt = con.prepareStatement(query.toString());
			ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
			boolean addTest = true;
			while(rs.next()) {
				if((rs.getInt(1) == 0 && (ctype.equals("C")||ctype.equals("S")))||(rs.getInt(1) > 0)) {
					addTest = false;
				}
			}
			rs.close();
			pstmt.close();
			return addTest;
		} else {
			query.append("select count(*) from circle_members cm LEFT OUTER JOIN circles c ON cm.CircleId = c.Circle_Id WHERE c.circle_type='C' and c.`status` = '1' AND cm.`Status` = '1' AND cm.MemberId = '"+circleMemberDTO.getUserid()+"' AND c.CircleOwner_Member_Id_P != '"+circleMemberDTO.getUserid()+"'");
			PreparedStatement pstmt = con.prepareStatement(query.toString());
			ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
			boolean addTest = true;
			while(rs.next()) {
				if(rs.getInt(1) > 0 && (ctype.equals("C")||ctype.equals("T"))) {
					addTest = false;
				}
			}
			rs.close();
			pstmt.close();
			return addTest;
		}	
	}
}
