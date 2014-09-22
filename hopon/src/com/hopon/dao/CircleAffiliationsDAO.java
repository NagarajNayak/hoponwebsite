package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hopon.dto.CircleAffiliationsDTO;
import com.hopon.dto.CircleDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.QueryExecuter;
import com.mysql.jdbc.Statement;

public class CircleAffiliationsDAO {
	public void makeTaxiCircleAffiliated(Connection con, int parentCircleId, int childCircleId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("INSERT into circleaffiliations(CircleId, AffilicatedCircle, Status, CreatedDT, AffiliationType) value (?,?,?,?,?)");
		PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
		pstmt.setInt(1, parentCircleId);
		pstmt.setInt(2, childCircleId);
		pstmt.setString(3, "P"); //P(pending)/A(active)/I(inactive)
		SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
		pstmt.setString(4, formatter.format(new Date()));
		pstmt.setString(5, "T");
		try{
			pstmt.executeUpdate();
		} catch(SQLException s) {
			query = new StringBuilder();
  			query.append("UPDATE circleaffiliations set Status = 'P' WHERE CircleId = ? AND AffilicatedCircle = ?");
  			pstmt = con.prepareStatement(query.toString());
  			pstmt.setInt(1, parentCircleId);
  			pstmt.setInt(2, childCircleId);
  			pstmt.executeUpdate();  			
		} finally {
			pstmt.close();
		}
		
  		/*ResultSet tableKeys = pstmt.getGeneratedKeys();
  		tableKeys.next();
  		int autoGeneratedID = tableKeys.getInt(1);
  		pstmt.close();
  		if(autoGeneratedID <= 0) {
  			query = new StringBuilder();
  			query.append("UPDATE circleaffiliations set Status = 'P' WHERE CircleId = ? AND AffilicatedCircle = ?");
  			pstmt = con.prepareStatement(query.toString());
  			pstmt.setInt(1, parentCircleId);
  			pstmt.setInt(2, childCircleId);
  			pstmt.executeUpdate();
  			pstmt.close();
  		}*/
	}
	
	public List<CircleAffiliationsDTO> findPendingAffiliatedCircleByUserId(Connection con,final int usereId) throws SQLException {
		List<CircleAffiliationsDTO> dtoList = new ArrayList<CircleAffiliationsDTO>();
		StringBuilder query = new StringBuilder();
		query.append("select circleaffiliations.CircleId, circleaffiliations.AffilicatedCircle, circles.Name, " +
				"circleaffiliations.Status, circles.Description,  circles.CircleOwner_Member_Id_P, users.first_name " +
				"from circleaffiliations LEFT OUTER JOIN circles ON circleaffiliations.CircleId = circles.Circle_Id LEFT OUTER JOIN users ON circles.CircleOwner_Member_Id_P = users.id WHERE circleaffiliations.AffilicatedCircle IN (SELECT c.Circle_Id from circles c where c.CircleOwner_Member_Id_P = " + usereId + " AND circleaffiliations.Status = 'P')");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		List<Integer> list = new ArrayList<Integer>();
		
		while(rs.next()) {
			CircleAffiliationsDTO tempDAO = new CircleAffiliationsDTO();
			tempDAO.setCircleId(rs.getInt(1));
			tempDAO.setAffilicatedCircleId(rs.getInt(2));
			tempDAO.setCircleAffilicatedCircleId(rs.getInt(1) + "-" + rs.getInt(2));
			tempDAO.setCircleName(rs.getString(3));
			tempDAO.setCircleDescription(rs.getString(5));
			tempDAO.setCircleOwnerId(rs.getString(6));
			tempDAO.setCircleOwnerName(rs.getString(7));
			dtoList.add(tempDAO);
			if(!list.contains(rs.getInt(2))) {
				list.add(rs.getInt(2));
			}
		}
		rs.close();
		pstmt.close();
		if(list.size() > 0) {
			String inClause = "";
			for (int i : list) {
				inClause += i + ",";
			}
			inClause = inClause.substring(0, inClause.length() - 1);
			query = new StringBuilder();
			query.append("select circles.Circle_Id, circles.Name, circles.Description, users.id, users.first_name from circles LEFT OUTER JOIN users ON circles.CircleOwner_Member_Id_P = users.id WHERE circles.Circle_Id IN (" + inClause + ")");
			pstmt = con.prepareStatement(query.toString());
			ResultSet rs2 = QueryExecuter.getResultSet(pstmt, query.toString());
			
			while(rs2.next()) {				
				for(CircleAffiliationsDTO dto : dtoList) {
					if(rs2.getInt(1) == dto.getAffilicatedCircleId()) {
						dto.setAffilicatedCircleName(rs2.getString(2));
						dto.setAffilicatedCircleDescription(rs2.getString(3));
						dto.setAffilicatedCircleOwnerId(rs2.getInt(4));
						dto.setAffilicatedCircleOwnerName(rs2.getString(5));	
					}
				}
			}
			rs2.close();
			pstmt.close();
		}
		return dtoList;
	}
	
	public List<CircleAffiliationsDTO> findPendingAffiliatedCircle(Connection con,final int circleId) throws SQLException {
		List<CircleAffiliationsDTO> dtoList = new ArrayList<CircleAffiliationsDTO>();
		StringBuilder query = new StringBuilder();
		/*query.append("SELECT c2.Circle_Id, c2.Name, c2.Description, c2.Date_of_creation, c2.CircleOwner_Member_Id_P, " +
				"c2.AutoEnroll_YN, c2.Affiliations, u.first_name " +
				"from circleaffiliations c1 ,circles c2, users u WHERE " +
				"c1.Status = 'P' AND c1.AffilicatedCircle = c2.Circle_Id AND c2.CircleOwner_Member_Id_P =u.id AND c1.AffilicatedCircle = " + userId);*/
		query.append("Select c1.CircleId, (select c2.Name as circleName from circles c2 where c2.Circle_Id = c1.CircleId ), c1.AffilicatedCircle, (select c3.Name as affiliatedCircleName from circles c3 where c3.Circle_Id  = c1.AffilicatedCircle ) from circleaffiliations c1 where c1.Status = 'P' AND c1.CircleId=" + circleId);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleAffiliationsDTO tempDAO = new CircleAffiliationsDTO();
			tempDAO.setCircleId(rs.getInt(1));
			tempDAO.setCircleName(rs.getString(2));
			tempDAO.setAffilicatedCircleId(rs.getInt(3));
			tempDAO.setAffilicatedCircleName(rs.getString(4));
			tempDAO.setCircleAffilicatedCircleId(rs.getInt(1) + "-" + rs.getInt(3));
			dtoList.add(tempDAO);
		}		
		rs.close();
		pstmt.close();
		return dtoList;
	}
	public List<CircleAffiliationsDTO> findActiveAffiliatedCircle(Connection con,final int circleId) throws SQLException {
		List<CircleAffiliationsDTO> dtoList = new ArrayList<CircleAffiliationsDTO>();
		StringBuilder query = new StringBuilder();
		/*query.append("SELECT c2.Circle_Id, c2.Name, c2.Description, c2.Date_of_creation, c2.CircleOwner_Member_Id_P, " +
				"c2.AutoEnroll_YN, c2.Affiliations, u.first_name " +
				"from circleaffiliations c1 ,circles c2, users u WHERE " +
				"c1.Status = 'A' AND c1.AffilicatedCircle = c2.Circle_Id AND c2.CircleOwner_Member_Id_P =u.id AND c1.AffilicatedCircle = " + userId);*/
		
		query.append("Select c1.CircleId, (select c2.Name as circleName from circles c2 where c2.Circle_Id = c1.CircleId ), c1.AffilicatedCircle, (select c3.Name as affiliatedCircleName from circles c3 where c3.Circle_Id  = c1.AffilicatedCircle ) from circleaffiliations c1 where c1.Status = 'A' AND c1.CircleId=" + circleId);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleAffiliationsDTO tempDAO = new CircleAffiliationsDTO();
			tempDAO.setCircleId(rs.getInt(1));
			tempDAO.setCircleName(rs.getString(2));
			tempDAO.setAffilicatedCircleId(rs.getInt(3));
			tempDAO.setAffilicatedCircleName(rs.getString(4));
			tempDAO.setCircleAffilicatedCircleId(rs.getInt(1) + "-" + rs.getInt(3));
			dtoList.add(tempDAO);
		}	
		rs.close();
		pstmt.close();
		return dtoList;
	}
	public void makeTaxiCircleAffiliatedActive(Connection con, int parentCircleId, int childCircleId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE circleaffiliations set Status = 'A' WHERE CircleId = ? AND AffilicatedCircle = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, parentCircleId);
		pstmt.setInt(2, childCircleId);
		pstmt.executeUpdate();  	
		pstmt.close();
	}
	public void makeTaxiCircleAffiliatedInactive(Connection con, int parentCircleId, int childCircleId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE circleaffiliations set Status = 'I' WHERE CircleId = ? AND AffilicatedCircle = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, parentCircleId);
		pstmt.setInt(2, childCircleId);
		pstmt.executeUpdate();  	
		pstmt.close();
	}
	public List<CircleDTO> getAffiliateCircleForTaxiUser(Connection con, final int userId) throws SQLException {
		List<CircleDTO> dtoList = new ArrayList<CircleDTO>();
		StringBuilder query = new StringBuilder();
		query.append("select circleaffiliations.CircleId, (select c1.Name from circles c1 WHERE c1.Circle_Id = circleaffiliations.CircleId) from circles JOIN circleaffiliations ON circles.Circle_Id = circleaffiliations.AffilicatedCircle where circleaffiliations.Status = 'A' AND circles.CircleOwner_Member_Id_P = " + userId);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleDTO tempDAO = new CircleDTO();
			tempDAO.setCircleID(rs.getInt(1));
			tempDAO.setName(rs.getString(2));
			dtoList.add(tempDAO);
		}
		rs.close();
		pstmt.close();
		return dtoList;
	}
	
}
