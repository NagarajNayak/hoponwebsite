package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hopon.dto.CircleOwnerDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.QueryExecuter;

public class CircleOwnerDAO {

public CircleOwnerDTO registerCircleOwner(Connection con,CircleOwnerDTO circleOwnerDTO, String senderId) throws SQLException {
		
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO circle_owner(CircleId,UserId,Status, CreatedBy, CreatedDT) VALUES (?,?,?, ?, '" +ApplicationUtil.currentTimeStamp()+"') ");
		
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, circleOwnerDTO.getCircleID());
		pstmt.setString(2, circleOwnerDTO.getUserID());
	    pstmt.setString(3, "1");
	    pstmt.setString(4, senderId);
		
	    try{
			pstmt.executeUpdate();
	    } catch(SQLException e) {
	    	query = new StringBuilder();
			query.append("update circle_owner set Status = '1', UpdatedBy = ?, UpdatedDT = '" +ApplicationUtil.currentTimeStamp()+"' WHERE CircleId = ? AND UserId = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, senderId);
			pstmt.setString(2, circleOwnerDTO.getCircleID());
			pstmt.setString(3, circleOwnerDTO.getUserID());
		    pstmt.executeUpdate();
	    }
		
		pstmt.close();
		
	    return circleOwnerDTO;
    }

public CircleOwnerDTO updateCircleOwner(Connection con,CircleOwnerDTO circleOwnerDTO, String senderId) throws SQLException {
	
	StringBuilder query = new StringBuilder();
	query.append("SELECT CircleOwner_Member_Id_P from circles WHERE Circle_Id = ?");
	PreparedStatement pstmt = con.prepareStatement(query.toString());
	pstmt.setString(1, circleOwnerDTO.getCircleID());
	ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
	boolean circleTest = true;
	while(rs.next()) {
		if(circleOwnerDTO.getUserID().equals(rs.getString(1))) {
			circleTest = false;
		}
	}
	rs.close();
	pstmt.close();
	
	if(circleTest) {		
		query = new StringBuilder();
		//query.append("delete from circle_owner WHERE CircleId = ? AND UserId = ?");
		query.append("UPDATE circle_owner set status = '0', UpdatedBy = ?, UpdatedDT = '" +ApplicationUtil.currentTimeStamp()+"' WHERE CircleId = ? AND UserId = ?");
		
		pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, senderId);
		pstmt.setString(2, circleOwnerDTO.getCircleID());
		pstmt.setString(3, circleOwnerDTO.getUserID());
		
		pstmt.executeUpdate();
	}	
	pstmt.close();
	
    return circleOwnerDTO;
}

public void makeCircleOwnerForUser(Connection con, UserRegistrationDTO userRegistrationDto, String senderId) throws SQLException {
	StringBuilder query = new StringBuilder();
	query.append("UPDATE circle_owner SET Status = '2', UpdatedBy = '"+ senderId +"', UpdatedDT = '" +ApplicationUtil.currentTimeStamp()+"' WHERE UserId = " + userRegistrationDto.getId());
	
	PreparedStatement	pstmt = con.prepareStatement(query.toString());
	pstmt.executeUpdate();
	pstmt.close();
}

}
