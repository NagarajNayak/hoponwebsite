package com.hopon.dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.LoggerSingleton;
import com.hopon.utils.QueryExecuter;
import com.hopon.dto.CircleDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.mysql.jdbc.Statement;

public class CircleDAO {
	public List<CircleDTO> findAllCircle(Connection con , final String userid )throws SQLException{
		
		StringBuilder query = new StringBuilder();
		query.append(" SELECT  Circle_Id,  Name, Description,Date_of_creation,CircleOwner_Member_Id_P,AutoEnroll_YN,Affiliations, circle_type FROM circles where CircleOwner_Member_Id_P = '" + userid + "' and  status='1' ");
		
		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleDTO dto = new CircleDTO();
			dto.setCircleID(Integer.valueOf(rs.getString(1)));
			dto.setName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern1);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern7);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setDate_of_creation(formatter1.format(date));
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
			}
			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(5)));
			dto.setAutoEnroll_YN(Integer.valueOf(rs.getString(6)));
			dto.setAffiliations(rs.getString(7));
			dto.setCircleType(rs.getString(8));
			circleDTO.add(dto);
		}
		rs.close();
		pstmt.close();
		return circleDTO;
	}
	public List<CircleDTO> findAllRegisteredCircle(Connection con)throws SQLException{
		
		StringBuilder query = new StringBuilder();
		query.append(" SELECT  circles.Circle_Id,  circles.Name, circles.Description,circles.CircleOwner_Member_Id_P,circles.AutoEnroll_YN,circles.Affiliations, users.travel, circles.circle_type FROM circles left OUTER JOIN users ON circles.CircleOwner_Member_Id_P = users.id WHERE circles.status='1'");
		
		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleDTO dto = new CircleDTO();
			dto.setCircleID(Integer.valueOf(rs.getString(1)));
			dto.setName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			//dto.setDate_of_creation(Date.valueOf(rs.getString(4)));
			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(4)));
			dto.setAutoEnroll_YN(Integer.valueOf(rs.getString(5)));
			dto.setAffiliations(rs.getString(6));
			dto.setCircleType(rs.getString(8));
			dto.setTaxiCircle(false);
			if(rs.getString(7) != null && rs.getString(7).equalsIgnoreCase("T")) {
				dto.setTaxiCircle(true);
			}
			
			circleDTO.add(dto);
		}
		rs.close();
		pstmt.close();
        return circleDTO;
	}
	public List<CircleDTO> findAllStatusRegisteredCircle(Connection con)throws SQLException{
		
		StringBuilder query = new StringBuilder();
		query.append(" SELECT  circles.Circle_Id,  circles.Name, circles.Description,circles.CircleOwner_Member_Id_P,circles.AutoEnroll_YN,circles.Affiliations, users.travel, circles.status, circles.circle_type FROM circles left OUTER JOIN users ON circles.CircleOwner_Member_Id_P = users.id WHERE 1");
		
		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleDTO dto = new CircleDTO();
			dto.setCircleID(Integer.valueOf(rs.getString(1)));
			dto.setName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			//dto.setDate_of_creation(Date.valueOf(rs.getString(4)));
			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(4)));
			dto.setAutoEnroll_YN(Integer.valueOf(rs.getString(5)));
			dto.setAffiliations(rs.getString(6));
			dto.setTaxiCircle(false);
			if(rs.getString(7) != null && rs.getString(7).equalsIgnoreCase("T")) {
				dto.setTaxiCircle(true);
			}
			dto.setStatus(rs.getString(8));
			dto.setCircleType(rs.getString(9));
			circleDTO.add(dto);
		}
		rs.close();
		pstmt.close();
        return circleDTO;
	}
	public List<CircleDTO> findAllRegisteredNonTaxiCircle(Connection con, String prefix)throws SQLException{
		
		StringBuilder query = new StringBuilder();
		query.append(" SELECT  circles.Circle_Id,  circles.Name, circles.Description,circles.CircleOwner_Member_Id_P,circles.AutoEnroll_YN,circles.Affiliations, users.travel, circles.circle_type FROM circles left OUTER JOIN users ON circles.CircleOwner_Member_Id_P = users.id WHERE circles.status='1' AND users.travel != 'T' AND circles.Name like '%"+ prefix +"%'");
		
		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleDTO dto = new CircleDTO();
			dto.setCircleID(Integer.valueOf(rs.getString(1)));
			dto.setName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			dto.setCircleType(rs.getString(8));
			circleDTO.add(dto);
		}
		rs.close();
		pstmt.close();
        return circleDTO;
	}
	public List<CircleDTO> findAllRegisteredTaxiCircle(Connection con, String prefix)throws SQLException{
	
	StringBuilder query = new StringBuilder();
	query.append(" SELECT  circles.Circle_Id,  circles.Name, circles.Description,circles.CircleOwner_Member_Id_P,circles.AutoEnroll_YN,circles.Affiliations, users.travel, circles.circle_type FROM circles left OUTER JOIN users ON circles.CircleOwner_Member_Id_P = users.id WHERE circles.status='1' AND users.travel = 'T' AND circles.Name like '%"+ prefix +"%'");
	
	List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
	PreparedStatement pstmt = con.prepareStatement(query.toString());
	ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
	while(rs.next()) {
		CircleDTO dto = new CircleDTO();
		dto.setCircleID(Integer.valueOf(rs.getString(1)));
		dto.setName(rs.getString(2));
		dto.setDescription(rs.getString(3));
		//dto.setDate_of_creation(Date.valueOf(rs.getString(4)));
		dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(4)));
		dto.setAutoEnroll_YN(Integer.valueOf(rs.getString(5)));
		dto.setAffiliations(rs.getString(6));
		dto.setCircleType(rs.getString(8));
		dto.setTaxiCircle(false);
		if(rs.getString(7) != null && rs.getString(7).equalsIgnoreCase("T")) {
			dto.setTaxiCircle(true);
		}
		
		circleDTO.add(dto);
	}
	rs.close();
	pstmt.close();
    return circleDTO;
}
	public List<CircleDTO> findAllRegisteredCorpCircle(Connection con)throws SQLException{
		
		StringBuilder query = new StringBuilder();
		query.append(" SELECT c.Circle_Id, c.Name, c.Description,c.CircleOwner_Member_Id_P,c.AutoEnroll_YN,c.Affiliations, c.circle_type FROM circles c left OUTER JOIN users u ON c.CircleOwner_Member_Id_P = u.id WHERE c.status='1' AND c.circle_type IN('C', 'T')");
		
		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleDTO dto = new CircleDTO();
			dto.setCircleID(Integer.valueOf(rs.getString(1)));
			dto.setName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			//dto.setDate_of_creation(Date.valueOf(rs.getString(4)));
			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(4)));
			dto.setAutoEnroll_YN(Integer.valueOf(rs.getString(5)));
			dto.setAffiliations(rs.getString(6));
			dto.setCircleType(rs.getString(7));
			dto.setTaxiCircle(false);
			if(rs.getString(7) != null && rs.getString(7).equalsIgnoreCase("T")) {
				dto.setTaxiCircle(true);
			}
			
			circleDTO.add(dto);
		}
		rs.close();
		pstmt.close();
        return circleDTO;
	}
	public CircleDTO registerCircle(Connection con , CircleDTO circleDTO )throws SQLException{
    	  
    	  StringBuilder query = new StringBuilder();
  		query.append("INSERT INTO circles (Circle_Id,Name, Description,Date_of_creation,CircleOwner_Member_Id_P,AutoEnroll_YN,Affiliations,companyid,status, circle_type) VALUES (?,?,?,?,?,?,?,?,?,?) ");
  		
  		PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
  		pstmt.setString(1, null);
  		pstmt.setString(2, circleDTO.getName());
  		pstmt.setString(3, circleDTO.getDescription());
  		pstmt.setString(4, circleDTO.getDate_of_creation().toString());
  		pstmt.setString(5, String.valueOf(circleDTO.getCircleOwner_Member_Id_P()));
  		pstmt.setString(6, String.valueOf(circleDTO.getAutoEnroll_YN()));
  		pstmt.setString(7, circleDTO.getAffiliations());
  		pstmt.setString(8, circleDTO.getCompanyID());
  		pstmt.setString(9, "1");
  		pstmt.setString(10, circleDTO.getCircleType());
  		pstmt.executeUpdate();
  		ResultSet tableKeys = pstmt.getGeneratedKeys();
  		tableKeys.next();
  		int autoGeneratedID = tableKeys.getInt(1);
  		pstmt.close();
  		circleDTO.setCircleID(autoGeneratedID);
  		tableKeys.close();
		pstmt.close();
    	  return circleDTO;

      }
      public CircleDTO declineCircle(Connection con ,CircleDTO circleDTO, String userId)throws SQLException {
  		
  		StringBuilder query = new StringBuilder();
  		query.append("UPDATE circles SET  status = '2' where Circle_Id ='" + circleDTO.getCircleID() + "' AND CircleOwner_Member_Id_P != '"+ userId +"'");
  		
  		final PreparedStatement pstmt = con.prepareStatement(query.toString());
  		pstmt.executeUpdate();
  		pstmt.close();
  		circleDTO.setStatus("2");
  		return circleDTO;
  	}
      
      public CircleDTO deactivateCircle(Connection con ,CircleDTO circleDTO)throws SQLException {
    		
    		StringBuilder query = new StringBuilder();
    		query.append("UPDATE circles SET  status = '2' where Circle_Id ='" + circleDTO.getCircleID() + "' ");
    		
    		final PreparedStatement pstmt = con.prepareStatement(query.toString());
    		pstmt.executeUpdate();
    		pstmt.close();
    		circleDTO.setStatus("2");
    		return circleDTO;
    	}
      public CircleDTO activateCircle(Connection con ,CircleDTO circleDTO)throws SQLException {
  		
  		StringBuilder query = new StringBuilder();
  		query.append("UPDATE circles SET  status = '1' where Circle_Id ='" + circleDTO.getCircleID() + "' ");
  		
  		final PreparedStatement pstmt = con.prepareStatement(query.toString());
  		pstmt.executeUpdate();
  		pstmt.close();
  		circleDTO.setStatus("2");
  		return circleDTO;
  	}
      
      public List<CircleDTO> findAllRegisteredCircleByName(Connection con , String circleName, String userId )throws SQLException{
  		
  		StringBuilder query = new StringBuilder();
  		//query.append(" SELECT  Circle_Id,  Name, Description,Date_of_creation,CircleOwner_Member_Id_P,AutoEnroll_YN,Affiliations FROM circles where Name like'%"+circleName+"%'");
  		
  		query.append("select c1.Circle_Id,  c1.Name ,c1.Description,c1.Date_of_creation,c1.CircleOwner_Member_Id_P, c1.AutoEnroll_YN,c1.Affiliations, c2.Status, u.first_name, c1.circle_type FROM circles c1 left outer join circle_members c2 ON c1.Circle_Id = c2.CircleId AND c2.MemberId = '" + userId + "' INNER JOIN users u ON c1.CircleOwner_Member_Id_P = u.id WHERE c1.Name like '%"+ circleName + "%'");
  		
  		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
  		PreparedStatement pstmt = con.prepareStatement(query.toString());
  		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
  		while(rs.next()) {
  			CircleDTO dto = new CircleDTO();
			dto.setCircleID(Integer.valueOf(rs.getString(1)));
			dto.setName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern1);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern7);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setDate_of_creation(formatter1.format(date));
			}
			catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
			} 
			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(5)));
			dto.setAutoEnroll_YN(Integer.valueOf(rs.getString(6)));
			dto.setAffiliations(rs.getString(7));
			dto.setStatus(rs.getString(8));
			dto.setOwnerName(rs.getString(9));
			dto.setCircleType(rs.getString(10));
			circleDTO.add(dto);
  		}
  		rs.close();
		pstmt.close();
        return circleDTO;
  	}

      public List<Integer> findAllUsersInCircle(Connection con, int circleId) throws SQLException {
    	  StringBuilder query = new StringBuilder();
    	  if(circleId == -1) {
    		  query.append("select circle_members.MemberId from circle_members WHERE circle_members.Status = '1' group by circle_members.MemberId");
    	  } else {
    		  query.append("select circle_members.MemberId from circle_members WHERE circle_members.Status = '1' AND circle_members.CircleId = " + circleId);
    	  }
    	  PreparedStatement pstmt = con.prepareStatement(query.toString());
    	  ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
    	  List<Integer> users = new ArrayList<Integer>();
    	  while(rs.next()) {
    		  users.add(rs.getInt(1));
    	  }
    	  rs.close();
    	  pstmt.close();
    	  return users;
      }
      public List<Integer> findAllStatusUsersInCircle(Connection con, int circleId) throws SQLException {
    	  StringBuilder query = new StringBuilder();
    	  if(circleId == -1) {
    		  query.append("select circle_members.MemberId from circle_members WHERE 1 group by circle_members.MemberId");
    	  } else {
    		  query.append("select circle_members.MemberId from circle_members WHERE circle_members.CircleId = " + circleId);
    	  }
    	  PreparedStatement pstmt = con.prepareStatement(query.toString());
    	  ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
    	  List<Integer> users = new ArrayList<Integer>();
    	  while(rs.next()) {
    		  users.add(rs.getInt(1));
    	  }
    	  rs.close();
    	  pstmt.close();
    	  return users;
      }
      public List<CircleDTO> findAllMemberCircle(Connection con , String userid )throws SQLException{
  		
  		StringBuilder query = new StringBuilder();
  		//query.append(" SELECT  Circle_Id,  Name, Description,Date_of_creation,CircleOwner_Member_Id_P,AutoEnroll_YN,Affiliations FROM circles where Circle_Id IN (select CircleId from circle_members where MemberId = '" + userid + "') and  status='1' ");
  		query.append("SELECT c.Circle_Id,  c.Name, c.Description,c.Date_of_creation,c.CircleOwner_Member_Id_P,c.AutoEnroll_YN,c.Affiliations,h.first_name, c.circle_type FROM circles c left outer join users h on c.CircleOwner_Member_Id_P = h.id where c.CircleOwner_Member_Id_P != " + userid + " AND c.Circle_Id IN (select CircleId from circle_members where MemberId = '" + userid + "' AND Status = '1') and c.status='1'");
  		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
  		PreparedStatement pstmt = con.prepareStatement(query.toString());
  		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
  		while(rs.next()) {
  			CircleDTO dto = new CircleDTO();
  			dto.setCircleID(Integer.valueOf(rs.getString(1)));
  			dto.setName(rs.getString(2));
  			dto.setDescription(rs.getString(3));
  			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern1);
  			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern7);
  			try {
  				Date date = formatter.parse(rs.getString(4));
  				dto.setDate_of_creation(formatter1.format(date));
  			} catch (ParseException e) {
  				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
  			}
  			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(5)));
  			dto.setAutoEnroll_YN(Integer.valueOf(rs.getString(6)));
  			dto.setAffiliations(rs.getString(7));
  			dto.setOwnerName(rs.getString(8));
  			dto.setCircleType(rs.getString(9));
  			circleDTO.add(dto);  			
  		}
  		rs.close();
		pstmt.close();
            return circleDTO;
  	}   
      public List<CircleDTO> findAllPendingMemberCircle(Connection con , String userid )throws SQLException{
    		
    		StringBuilder query = new StringBuilder();
    		query.append(" SELECT  c.Circle_Id,  c.Name, c.Description, c.Date_of_creation, c.CircleOwner_Member_Id_P, u.first_name, c.circle_type FROM circles c LEFT OUTER JOIN users u ON c.CircleOwner_Member_Id_P = u.id where c.Circle_Id IN (select c2.CircleId from circle_members c2 where c2.MemberId = '" + userid + "' and c2.requestUserId != '" + userid + "' and c2.Status = '0') and  c.status='1' ");
    			
    		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
    		PreparedStatement pstmt = con.prepareStatement(query.toString());
    		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
    		while(rs.next()) {
    			CircleDTO dto = new CircleDTO();
    			dto.setCircleID(Integer.valueOf(rs.getString(1)));
    			dto.setName(rs.getString(2));
    			dto.setDescription(rs.getString(3));
    			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern1);
    			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern7);
    			try {
    				Date date = formatter.parse(rs.getString(4));
    				dto.setDate_of_creation(formatter1.format(date));
    			} catch (ParseException e) {
    				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
    			}
    			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(5)));
				dto.setOwnerName(rs.getString(6));
				dto.setCircleType(rs.getString(7));
				circleDTO.add(dto);
    		}
    		rs.close();
    		pstmt.close();
            return circleDTO;
    	}
	public List<CircleDTO> findAllAffiliatedTaxiCircle(Connection con, String prefix) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT  Circle_Id,  Name, Description,Date_of_creation,CircleOwner_Member_Id_P,AutoEnroll_YN,Affiliations, circle_type FROM circles where CircleOwner_Member_Id_P IN (select users.id from users where users.travel= 'T') and  status='1' and Name like '%"+ prefix +"%' ");
		
		List<CircleDTO> circleDTO = new ArrayList<CircleDTO>();
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			CircleDTO dto = new CircleDTO();
			dto.setCircleID(Integer.valueOf(rs.getString(1)));
			dto.setName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern1);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern7);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setDate_of_creation(formatter1.format(date));
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
			}
			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(5)));
			dto.setAutoEnroll_YN(Integer.valueOf(rs.getString(6)));
			dto.setAffiliations(rs.getString(7));
			dto.setCircleType(rs.getString(8));
			circleDTO.add(dto);
		}
		rs.close();
		pstmt.close();
          return circleDTO;
	} 
	public CircleDTO findCircleByCircleId(Connection con, int circleId) throws SQLException {
		CircleDTO dto = new CircleDTO();
		StringBuilder query = new StringBuilder();
		query.append("Select Circle_Id, Name, Description, Date_of_creation, CircleOwner_Member_Id_P, status, circle_type from circles where Circle_Id = " + circleId);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			dto.setCircleID(Integer.valueOf(rs.getString(1)));
			dto.setName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern1);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern7);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setDate_of_creation(formatter1.format(date));
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
			}
			dto.setCircleOwner_Member_Id_P(Integer.valueOf(rs.getString(5)));
			dto.setStatus(rs.getString(6));
			dto.setCircleType(rs.getString(7));
		}
		rs.close();
		pstmt.close();
		return dto;
	}
	public void makeCircleInactiveForUser(Connection con, UserRegistrationDTO userRegistrationDto) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE circles SET status = '2' WHERE CircleOwner_Member_Id_P = " + userRegistrationDto.getId());
		
		PreparedStatement	pstmt = con.prepareStatement(query.toString());
		pstmt.executeUpdate();
		pstmt.close();
	}
}