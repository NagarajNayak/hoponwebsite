package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hopon.dto.CircleOwnerManagerDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.QueryExecuter;

public class CircleOwnerManagerDAO {


	public List<CircleOwnerManagerDTO> findAllCircleForLoginUser(Connection con , String userID )throws SQLException{

		List<CircleOwnerManagerDTO>  circleManagerList = new ArrayList<CircleOwnerManagerDTO>();
		StringBuilder query = new StringBuilder();
		query.append(" select circles.Circle_Id,circles.Name,circles.Description from circle_owner,circles where circle_owner.UserId='"+userID+"' and circle_owner.CircleId = circles.Circle_Id and circles.status='1'  ");

		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
			dto.setCircleID(rs.getString(1));
			dto.setCircleName(rs.getString(2));
			dto.setCircleDescription(rs.getString(3));
			circleManagerList.add(dto);
		}
		rs.close();
		pstmt.close();
		return circleManagerList;
	}
	public List<CircleOwnerManagerDTO> findAllCorpCircleForLoginUser(Connection con , String userID )throws SQLException{

		List<CircleOwnerManagerDTO>  circleManagerList = new ArrayList<CircleOwnerManagerDTO>();
		StringBuilder query = new StringBuilder();
		query.append(" select circles.Circle_Id,circles.Name,circles.Description from circle_owner,circles where circles.circle_type='C' and circle_owner.UserId='"+userID+"' and circle_owner.CircleId = circles.Circle_Id and circles.status='1'  ");

		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
			dto.setCircleID(rs.getString(1));
			dto.setCircleName(rs.getString(2));
			dto.setCircleDescription(rs.getString(3));
			circleManagerList.add(dto);
		}
		rs.close();
		pstmt.close();
		return circleManagerList;
	}
	public List<CircleOwnerManagerDTO> findAllTaxiCircleForLoginUser(Connection con , String userID )throws SQLException{

		List<CircleOwnerManagerDTO>  circleManagerList = new ArrayList<CircleOwnerManagerDTO>();
		StringBuilder query = new StringBuilder();
		query.append(" select circles.Circle_Id,circles.Name,circles.Description from circle_owner,circles where circles.circle_type='T' and circle_owner.UserId='"+userID+"' and circle_owner.CircleId = circles.Circle_Id and circles.status='1'  ");

		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();
			dto.setCircleID(rs.getString(1));
			dto.setCircleName(rs.getString(2));
			dto.setCircleDescription(rs.getString(3));
			circleManagerList.add(dto);
		}
		rs.close();
		pstmt.close();
		return circleManagerList;
	}

	public List<CircleOwnerManagerDTO> findAllCircleMemberForLoginUser(Connection con , String userID )throws SQLException{

		List<CircleOwnerManagerDTO>  circleManagerList = new ArrayList<CircleOwnerManagerDTO>();
		StringBuilder query = new StringBuilder();
		//query.append("select u.id, u.first_name, c.Name, c.Description, c.Circle_Id, co.UserId, floor(DATEDIFF('"+ApplicationUtil.currentDate+"',u.birthdate) / 365), u.address, c.CircleOwner_Member_Id_P, co.Status from circle_members cm  LEFT OUTER JOIN circles c ON cm.CircleId = c.Circle_Id LEFT OUTER JOIN circle_owner co ON co.CircleId = c.Circle_Id LEFT OUTER JOIN users u ON cm.MemberId = u.id where cm.Status='1' and cm.MemberId = '"+userID+"' ");
		query.append("select u.id, u.first_name, c.Name, c.Description, c.Circle_Id, cm.MemberId, floor(DATEDIFF('"+ApplicationUtil.currentDate()+"',u.birthdate) / 365), u.address, c.CircleOwner_Member_Id_P, co2.Status from circle_owner co LEFT OUTER JOIN circle_members cm ON co.CircleId = cm.CircleId LEFT OUTER JOIN circles c ON cm.CircleId = c.Circle_Id LEFT OUTER JOIN users u ON cm.MemberId = u.id LEFT OUTER JOIN circle_owner co2 ON cm.MemberId = co2.UserId AND c.Circle_Id = co2.CircleId where cm.Status='1' and co.UserId = '"+userID+"' ");
		//" where hu.ID != "+userID+" and cm.Status='1' and co.UserId = "+userID+" ");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();

			dto.setUserid(rs.getString(1));
			dto.setMemberName(rs.getString(2));
			dto.setCircleName(rs.getString(3));
			dto.setCircleDescription(rs.getString(4));
			dto.setCircleID(rs.getString(5));
			if(rs.getString(10)!=null && rs.getString(10).equals("1")){
				dto.setAdminInfo("admin");
			} else{
				dto.setAdminInfo("");
			}
			dto.setAge(rs.getString(7));
			dto.setAddress(rs.getString(8));
			dto.setSuperAdmin(rs.getString(9));
			circleManagerList.add(dto);
		}
		rs.close();
		pstmt.close();
		return circleManagerList;
	}

	public List<CircleOwnerManagerDTO> findAllPendingCircleMemberForLoginUser(Connection con , String userID )throws SQLException{

		List<CircleOwnerManagerDTO>  circleManagerList = new ArrayList<CircleOwnerManagerDTO>();
		StringBuilder query = new StringBuilder();
		query.append(" select hu.id ,hu.first_name,c.Name,c.Description,c.Circle_Id,ck.UserId, floor(DATEDIFF('"+ApplicationUtil.currentDate()+"',hu.birthdate) / 365), hu.address, c.CircleOwner_Member_Id_P, ck.Status from circle_owner co"+
				" INNER JOIN circles c ON c.Circle_Id = co.CircleId"+
				" INNER JOIN circle_members cm ON cm.CircleId = co.CircleId"+
				" INNER JOIN users hu ON hu.id = cm.MemberId" +
				" LEFT OUTER JOIN circle_owner ck ON ck.UserId = hu.id"+
				" AND ck.CircleId = cm.CircleId"+
				" where cm.Status='0' and co.UserId = "+userID+" ");
		//" where hu.ID != "+userID+" and cm.Status='1' and co.UserId = "+userID+" ");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {

			CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();

			dto.setUserid(rs.getString(1));
			dto.setMemberName(rs.getString(2));
			dto.setCircleName(rs.getString(3));
			dto.setCircleDescription(rs.getString(4));
			dto.setCircleID(rs.getString(5));
			if(rs.getString(6)!=null && rs.getString(10) .equals("1")){
				dto.setAdminInfo("admin");
			} else{
				dto.setAdminInfo("");
			}
			dto.setAge(rs.getString(7));
			dto.setAddress(rs.getString(8));
			dto.setSuperAdmin(rs.getString(9));
			circleManagerList.add(dto);
		}
		rs.close();
		pstmt.close();
		return circleManagerList;
	}

	public List<CircleOwnerManagerDTO> findAllLoginUserMessage(Connection con , String userID )throws SQLException{

		List<CircleOwnerManagerDTO>  circleManagerList = new ArrayList<CircleOwnerManagerDTO>();
		StringBuilder query = new StringBuilder();
		query.append("select users.id,users.first_name,circles.Name,circles.Description,circles.Circle_Id, users.address " +
				"from circle_owner,circles,users,circle_members " +
				"where circle_owner.UserId= "+userID+" " +
				"and circles.Circle_Id=circle_owner.CircleId  " +
				"and circle_members.CircleId = circle_owner.CircleId " +
				"and users.id = circle_members.MemberId " +
				"and users.id != "+userID+" " +
		"and circle_members.Status='0' AND circle_members.requestUserId > 0 ");

		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {

			CircleOwnerManagerDTO dto = new CircleOwnerManagerDTO();

			dto.setUserid(rs.getString(1));
			dto.setMemberName(rs.getString(2));
			dto.setCircleName(rs.getString(3));
			dto.setCircleDescription(rs.getString(4));
			dto.setCircleID(rs.getString(5));
			if(rs.getString(6) != null) {
				dto.setAddress(rs.getString(6));
			} else {
				dto.setAddress("");
			}
			circleManagerList.add(dto);
		}
		rs.close();
		pstmt.close();
		return circleManagerList;
	}
	/*
	 * select circle_members.CircleId,circles.Name,circles.Description,circles.CircleOwner_Member_Id_P,hp_users.userName,hp_users.address from circle_members,circles,hp_users where 
       circle_members.MemberId='12' and 
       circles.Circle_Id=circle_members.CircleId and  hp_users.ID=circles.CircleOwner_Member_Id_P
	 */
}
