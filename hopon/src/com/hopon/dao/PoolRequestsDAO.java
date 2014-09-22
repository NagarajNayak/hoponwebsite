package com.hopon.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hopon.dto.PoolRequestsDTO;
import com.hopon.dto.RideBillingReportDTO;
import com.hopon.dto.RideManagementDTO;
import com.hopon.dto.SmsBillingReportDTO;
import com.hopon.dto.UserRegistrationDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.LoggerSingleton;
import com.hopon.utils.QueryExecuter;
import com.hopon.utils.Validator;
import com.mysql.jdbc.Statement;
public class PoolRequestsDAO {
	public List<RideBillingReportDTO> gatherRideBillingData(final Connection con, final Date startDate, final Date endDate, int ...circleId) throws SQLException {
		final List<RideBillingReportDTO> list = new ArrayList<RideBillingReportDTO>();
		final StringBuilder query = new StringBuilder();
		//query.append("select cm.CircleId, c.Name, cou.id, cou.email_id, cou.first_name, p.request_by, p.user_id, p.rideseeker_id, rs.start_point, rs.destination_point, rs.ride_cost, rs.ride_distance, rs.start_time, rs.created_dt, rs.fullDay from circle_members cm RIGHT OUTER JOIN pool_requests p ON cm.MemberId = p.request_by LEFT OUTER JOIN ride_seeker_details rs ON p.rideseeker_id = rs.seeker_id LEFT OUTER JOIN circles c ON cm.CircleId = c.Circle_Id LEFT OUTER JOIN users cou ON c.CircleOwner_Member_Id_P = cou.id Where rs.`status` = 'A' AND p.user_id IN (SELECT cm1.MemberId from circle_members cm1 WHERE cm1.CircleId = cm.CircleId) AND rs.start_time BETWEEN '"+ApplicationUtil.dateFormat1.format(startDate)+"' AND '"+ApplicationUtil.dateFormat1.format(endDate)+"' ");
		query.append("select c.Circle_Id, c.Name, cou.id, cou.email_id, cou.first_name, p.request_by, p.user_id, p.rideseeker_id, rs.start_point, rs.destination_point, rs.ride_cost, rs.ride_distance, rs.start_time, rs.created_dt, rs.fullDay from ride_seeker_details rs LEFT OUTER JOIN circles c ON rs.circle_id = c.Circle_Id LEFT OUTER JOIN pool_requests p ON rs.seeker_id = p.rideseeker_id LEFT OUTER JOIN users cou ON c.CircleOwner_Member_Id_P = cou.id Where rs.`status` = 'A' AND p.user_id IN (SELECT cm1.MemberId from circle_members cm1 WHERE cm1.CircleId = c.Circle_Id) AND rs.start_time BETWEEN '"+ApplicationUtil.dateFormat1.format(startDate)+"' AND '"+ApplicationUtil.dateFormat1.format(endDate)+"' ");
		
		String subQuery = "";
		for (int i = 0; i < circleId.length; i++) {
			subQuery += circleId[i]+ ",";
		}
		if(subQuery.length() > 0) query.append(" AND c.Circle_Id IN("+subQuery+"0) ");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			RideBillingReportDTO dto = new RideBillingReportDTO();
			dto.setCircleId(rs.getInt(1));
			dto.setCircleName(rs.getString(2));
			dto.setCircleOwnerUserId(rs.getInt(3));
			dto.setCircleOwnerUserEmailId(rs.getString(4));
			dto.setCircleOwnerUserName(rs.getString(5));
			dto.setRequestUserId(rs.getInt(6));
			dto.setGiverUserId(rs.getInt(7));
			dto.setRideSeekerId(rs.getInt(8));
			dto.setStartPoint(rs.getString(9));
			dto.setEndPoint(rs.getString(10));
			dto.setRideCost(rs.getFloat(11));
			dto.setRideDistance(rs.getFloat(12));
			try {
				dto.setRideDate(ApplicationUtil.dateFormat3.parse(rs.getString(13)));
			} catch (ParseException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(13)+"."+e.getMessage()); }
			try {
				dto.setRequestDate(ApplicationUtil.dateFormat5.parse(rs.getString(14)));
			} catch (ParseException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(14)+"."+e.getMessage()); }
			dto.setFullDay(false);
			if(rs.getString(14).equalsIgnoreCase("Y")) dto.setFullDay(true);
			list.add(dto);
		}
		pstmt.close();
		rs.close();
		return list;
	}
	public List<SmsBillingReportDTO> gatherInboundSmsBillingData(final Connection con, final Date startDate, final Date endDate, int ...circleId) throws SQLException {
		final List<SmsBillingReportDTO> list = new ArrayList<SmsBillingReportDTO>();
		StringBuilder query = new StringBuilder();
		query.append("select c.Circle_Id, c.Name, cou.id as ownerUserId, cou.email_id, cou.first_name, um.id, um.email_id, um.first_name, mb.MessageId, mu1.mobile_no, mb.CreatedbyDT from circles c LEFT OUTER JOIN users cou ON c.CircleOwner_Member_Id_P = cou.id LEFT OUTER JOIN circle_members cm ON c.Circle_Id = cm.CircleId LEFT OUTER JOIN users um ON cm.MemberId = um.id LEFT OUTER JOIN messageboard mb ON um.id = mb.CreatedBy LEFT OUTER JOIN users mu1 ON mb.CreatedBy = mu1.id WHERE mb.MessageChannel = 'S' AND mb.CreatedbyDT BETWEEN '"+ApplicationUtil.dateFormat1.format(startDate)+"' AND '"+ApplicationUtil.dateFormat1.format(endDate)+"'");
		String subQuery = "";
		for (int i = 0; i < circleId.length; i++) {
			subQuery += circleId[i]+ ",";
		}
		if(subQuery.length() > 0) query.append(" AND c.Circle_Id IN("+subQuery+"0) ");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			SmsBillingReportDTO dto = new SmsBillingReportDTO();
			dto.setCircleId(rs.getInt(1));
			dto.setCircleName(rs.getString(2));
			dto.setCircleOwnerUserId(rs.getInt(3));
			dto.setCircleOwnerUserEmailId(rs.getString(4));
			dto.setCircleOwnerUserName(rs.getString(5));
			dto.setRequestUserId(rs.getInt(6));
			dto.setRequestUserEmailId(rs.getString(7));
			dto.setRequestUserName(rs.getString(8));
			dto.setMessageId(rs.getInt(9));
			dto.setMessageToNumber(rs.getString(10));
			try {
				dto.setSendDate(ApplicationUtil.dateFormat3.parse(rs.getString(11)));
			} catch (ParseException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(11)+"."+e.getMessage()); }
			list.add(dto);
		}
		pstmt.close();
		rs.close();
		return list;
	}
	public List<SmsBillingReportDTO> gatherOutboundSmsBillingData(final Connection con, final Date startDate, final Date endDate, int ...circleId) throws SQLException {
		final List<SmsBillingReportDTO> list = new ArrayList<SmsBillingReportDTO>();
		StringBuilder query = new StringBuilder();
		query.append("select c.Circle_Id, c.Name, cou.id as ownerUserId, cou.email_id, cou.first_name, um.id, um.email_id, um.first_name, s.id, s.toNumber, s.createdDate from circles c LEFT OUTER JOIN users cou ON c.CircleOwner_Member_Id_P = cou.id LEFT OUTER JOIN circle_members cm ON c.Circle_Id = cm.CircleId LEFT OUTER JOIN users um ON cm.MemberId = um.id LEFT OUTER JOIN smsreply s ON SUBSTR(um.mobile_no, -10) = SUBSTR(s.fromNumber, -10) WHERE !ISNULL(s.smsSid) AND s.createdDate BETWEEN '"+ApplicationUtil.dateFormat1.format(startDate)+"' AND '"+ApplicationUtil.dateFormat1.format(endDate)+"'");
		String subQuery = "";
		for (int i = 0; i < circleId.length; i++) {
			subQuery += circleId[i]+ ",";
		}
		if(subQuery.length() > 0) query.append(" AND c.Circle_Id IN("+subQuery+"0) ");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			SmsBillingReportDTO dto = new SmsBillingReportDTO();
			dto.setCircleId(rs.getInt(1));
			dto.setCircleName(rs.getString(2));
			dto.setCircleOwnerUserId(rs.getInt(3));
			dto.setCircleOwnerUserEmailId(rs.getString(4));
			dto.setCircleOwnerUserName(rs.getString(5));
			dto.setRequestUserId(rs.getInt(6));
			dto.setRequestUserEmailId(rs.getString(7));
			dto.setRequestUserName(rs.getString(8));
			dto.setSmsId(rs.getInt(9));
			dto.setMessageToNumber(rs.getString(10));
			try {
				dto.setSendDate(ApplicationUtil.dateFormat3.parse(rs.getString(11)));
			} catch (ParseException e) { LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(11)+"."+e.getMessage()); }
			list.add(dto);
		}
		pstmt.close();
		rs.close();
		return list;
	}
	public List<PoolRequestsDTO> findAllPoolRequests(final Connection con , final String userID )throws SQLException{

		final List<PoolRequestsDTO> poolList = new ArrayList<PoolRequestsDTO>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT  pr.id, rs.user_id,u1.first_name,u.travel,rm.start_point,rm.destination_point,rm.start_time,tf.Days,rt.ridegivernotes,rt.ridegiverrating, rt.ridetakernotes, rt.ridetakerrating, rm.`status` FROM pool_requests pr " +
				" INNER JOIN rides_management rm ON rm.ride_id = pr.ride_id"+
				" INNER JOIN ride_seeker_details rs  ON rs.seeker_id = pr.rideseeker_id"+
				" INNER JOIN trip_frequency tf ON tf.ride_management_id = pr.ride_id"+
				" LEFT OUTER JOIN ratingandnotes rt ON rt.poolrequestid = pr.id " +
				" INNER JOIN users u ON u.id = rm.user_id" +
				" INNER JOIN users u1 ON rs.user_id = u1.id"+
				//" where u.id = '"+userID+"' ");
				" where rm.status IN('A', 'T') AND u.id = '"+userID+"' AND TIMESTAMPDIFF(SECOND,rm.start_time,'" +ApplicationUtil.currentTimeStamp()+"') > 0  "); //FOR UPDATE

		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			PoolRequestsDTO dto = new PoolRequestsDTO();
			dto.setPoolID(rs.getString(1));
			dto.setUser_id(rs.getString(2));
			dto.setRideWith(rs.getString(3));
			dto.setRole(rs.getString(4));
			dto.setRideManagementFrom(rs.getString(5));
			dto.setRideManagementTO(rs.getString(6));
			
			if(rs.getString(7) !=null){
				try {
					Date date = ApplicationUtil.dateFormat3.parse(rs.getString(7));
					dto.setRideManagementStartDate(ApplicationUtil.dateFormat9.format(date));
				} catch (ParseException e) {
					LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(7)+"."+e.getMessage());
				}
			}
			dto.setRideManagementFrequency(rs.getString(8).substring(1, rs.getString(8).length()-1));
			dto.setRideGiverNotes(rs.getString(9));
			dto.setRateRideGiver(rs.getString(10));

			dto.setRideTakerNotes(rs.getString(11));
			dto.setRateRideTaker(rs.getString(12));
			dto.setRequestStatus(rs.getString(13));
			poolList.add(dto);
		}
		rs.close();
		pstmt.close();
		return poolList;
	}

	public List<PoolRequestsDTO> findAllPoolRequestsSeekers(Connection con , String userID )throws SQLException{

		List<PoolRequestsDTO> poolList = new ArrayList<PoolRequestsDTO>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT  pr.id, rm.user_id ,u1.first_name,u.travel,rs.start_point,rs.destination_point,rs.start_tw_early,tf.Days,rt.ridetakerrating,rt.ridetakernotes, rt.ridegivernotes, rt.ridegiverrating, u2.first_name FROM pool_requests pr " +
				" INNER JOIN ride_seeker_details rs  ON rs.seeker_id = pr.rideseeker_id"+
				" INNER JOIN rides_management rm ON rm.ride_id = pr.ride_id"+
				" INNER JOIN trip_frequency tf  ON tf.ride_seeker_id = pr.rideseeker_id"+
				" LEFT OUTER JOIN ratingandnotes rt ON rt.poolrequestid = pr.id " +
				" INNER JOIN users u ON rs.user_id = u.id"+
				" INNER JOIN vehicles_master v ON rm.vehicleID = v.id"+
				" LEFT OUTER JOIN users u1 ON v.driverid = u1.id"+
				" LEFT OUTER JOIN users u2 ON v.user_id = u2.id"+
				//" where u.id = '"+userID+"' ");
				" where rs.status = 'A' AND rs.is_result = 'Y' AND u.id = '"+userID+"' AND TIMESTAMPDIFF(SECOND,rs.start_tw_early,'" +ApplicationUtil.currentTimeStamp()+"') > 0 ");//FOR UPDATE
		//" LEFT OUTER JOIN ratingandnotes rt ON rt.poolrequestid = pr.id INNER JOIN users u ON rm.user_id = u.id"
		//LEFT OUTER JOIN ratingandnotes rt ON rt.poolrequestid = pr.id INNER JOIN users u ON rm.seeker_id = u.id
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			PoolRequestsDTO dto = new PoolRequestsDTO();
			dto.setPoolID(rs.getString(1));
			dto.setUser_id(rs.getString(2));
			dto.setRideWith(rs.getString(3));
			if(Validator.isEmpty(rs.getString(3))) {
				dto.setRideWith(rs.getString(13));
			}
			dto.setRole(rs.getString(4));
			dto.setRideSeekerFrom(rs.getString(5));
			dto.setRideSeekerTo(rs.getString(6));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
			try {
				Date date = formatter.parse(rs.getString(7));
				dto.setRideSeekerStartDate(formatter1.format(date));
			} catch (ParseException e) {
				LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(7)+"."+e.getMessage());
			}
			dto.setRideSeekerFrequency(rs.getString(8).substring(1, rs.getString(8).length()-1));
			dto.setRateRideTaker(rs.getString(9));
			dto.setRideTakerNotes(rs.getString(10));
			dto.setRideGiverNotes(rs.getString(11));
			dto.setRateRideGiver(rs.getString(12));
			poolList.add(dto);
		}
		rs.close();
		pstmt.close();
		return poolList;
	}
	public PoolRequestsDTO updaterateAndWriteNotes(Connection con ,PoolRequestsDTO pooldto)throws SQLException {

		StringBuilder query = new StringBuilder();
		//query.append("UPDATE pool_requests SET  ridetakerrating = '" + pooldto.getRateRideTaker() + "', ridegiverrating = '" + pooldto.getRateRideSeeker() + "',rideseekernotes = '" + pooldto.getRideSeekerNotes() + "',ridetakernotes = '" + pooldto.getRideTakerNotes() + "' where ID ='" + pooldto.getPoolID() + "'");

		PreparedStatement	pstmt = con.prepareStatement(query.toString());
		pstmt.executeUpdate();
		pstmt.close();

		return pooldto;
	}
	public PoolRequestsDTO rateAndWriteNotes(Connection con ,PoolRequestsDTO pooldto)throws SQLException {

		StringBuilder query = new StringBuilder();
		if(pooldto.getMasterControl().equalsIgnoreCase("giver")){

			query.append("SELECT count(*) from ratingandnotes WHERE poolrequestid = " + pooldto.getPoolID());
			PreparedStatement pstmt1 = con.prepareStatement(query.toString());
			ResultSet rs1 = QueryExecuter.getResultSet(pstmt1, query.toString());
			boolean test = true;
			while(rs1.next()) {
				if(rs1.getInt(1) > 0) {
					test = false;
				}
			}
			rs1.close();
			pstmt1.close();

			if(test) {
				//If pool request id not exist.
				query = new StringBuilder();
				query.append("INSERT INTO ratingandnotes (id,poolrequestid,ridegiverrating,ridegivernotes) VALUES (?,?,?,?) ");

				PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, pooldto.getRatingID());
				pstmt.setString(2, pooldto.getPoolID());
				if(pooldto.getRateRideGiver() == null || pooldto.getRateRideGiver() == "") {
					pstmt.setString(3, "0");
				} else {
					pstmt.setString(3, pooldto.getRateRideGiver());			
				}
				pstmt.setString(4, pooldto.getRideGiverNotes());
				pstmt.executeUpdate();
				ResultSet tableKeys = pstmt.getGeneratedKeys();
				tableKeys.next();
				int autoGeneratedID = tableKeys.getInt(1);
				tableKeys.close();
				pstmt.close();
				pooldto.setRatingID(String.valueOf(autoGeneratedID));
			} else {
				query = new StringBuilder();
				query.append("UPDATE ratingandnotes set ridegiverrating = ?, ridegivernotes = ? WHERE poolrequestid = ? ");

				PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(3, pooldto.getPoolID());
				if(pooldto.getRateRideGiver() == null || pooldto.getRateRideGiver() == "") {
					pstmt.setString(1, "0");
				} else {
					pstmt.setString(1, pooldto.getRateRideGiver());			
				}
				pstmt.setString(2, pooldto.getRideGiverNotes());
				pstmt.executeUpdate();
				ResultSet tableKeys = pstmt.getGeneratedKeys();
				tableKeys.next();
				int autoGeneratedID = tableKeys.getInt(1);
				tableKeys.close();
				pstmt.close();
				pooldto.setRatingID(String.valueOf(autoGeneratedID));
			}



		}
		if(pooldto.getMasterControl().equalsIgnoreCase("taker")){

			query.append("SELECT count(*) from ratingandnotes WHERE poolrequestid = " + pooldto.getPoolID());
			PreparedStatement pstmt1 = con.prepareStatement(query.toString());
			ResultSet rs1 = QueryExecuter.getResultSet(pstmt1, query.toString());
			boolean test = true;
			while(rs1.next()) {
				if(rs1.getInt(1) > 0) {
					test = false;
				}
			}
			rs1.close();
			pstmt1.close();
			if(test) {
				query = new StringBuilder();
				query.append("INSERT INTO ratingandnotes (id,poolrequestid,ridetakerrating,ridetakernotes) VALUES (?,?,?,?) ");

				PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, pooldto.getRatingID());
				pstmt.setString(2, pooldto.getPoolID());
				if(pooldto.getRateRideTaker() == null || pooldto.getRateRideTaker() == "") {
					pstmt.setString(3, "0");
				} else {
					pstmt.setString(3, pooldto.getRateRideTaker());			
				}
				pstmt.setString(4, pooldto.getRideTakerNotes());
				pstmt.executeUpdate();
				ResultSet tableKeys = pstmt.getGeneratedKeys();
				tableKeys.next();
				int autoGeneratedID = tableKeys.getInt(1);
				tableKeys.close();
				pstmt.close();
				pooldto.setRatingID(String.valueOf(autoGeneratedID));
			} else {
				query = new StringBuilder();
				query.append("UPDATE ratingandnotes SET ridetakerrating = ?,ridetakernotes = ? WHERE poolrequestid = ? ");

				PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(3, pooldto.getPoolID());
				if(pooldto.getRateRideTaker() == null || pooldto.getRateRideTaker() == "") {
					pstmt.setString(1, "0");
				} else {
					pstmt.setString(1, pooldto.getRateRideTaker());			
				}
				pstmt.setString(2, pooldto.getRideTakerNotes());
				pstmt.executeUpdate();
				ResultSet tableKeys = pstmt.getGeneratedKeys();
				tableKeys.next();
				int autoGeneratedID = tableKeys.getInt(1);
				tableKeys.close();
				pstmt.close();
				pooldto.setRatingID(String.valueOf(autoGeneratedID));
			}


		}

		return pooldto;
	}

	public UserRegistrationDTO getAverageRatingForUser(Connection con, UserRegistrationDTO dto) throws SQLException {

		float takerRating = (float) 0.0;
		float giverRating = (float) 0.0;
		float avgRating = (float) 0.0;
		StringBuilder query = new StringBuilder();
		//query.append("select round(AVG(IFNULL(ratingandnotes.ridegiverrating, 0) + IFNULL(ratingandnotes.ridetakerrating, 0)), 1) as average_rating from ratingandnotes join pool_requests on ratingandnotes.poolrequestid = pool_requests.id join ride_seeker_details ON ride_seeker_details.seeker_id = pool_requests.rideseeker_id WHERE ride_seeker_details.user_id = '"+ dto.getId() +"'");
		query.append("select IFNULL(round(AVG(IFNULL(ratingandnotes.ridetakerrating, 0) ), 1), 0) as avg from ratingandnotes join pool_requests on ratingandnotes.poolrequestid = pool_requests.id LEFT OUTER JOIN rides_management r1 ON pool_requests.ride_id = r1.ride_id LEFT OUTER JOIN users u1 ON r1.user_id = u1.id WHERE u1.id = '"+ dto.getId() +"'  AND ! ISNULL(ratingandnotes.ridetakerrating)");//FOR UPDATE
/*select IFNULL(round(AVG(IFNULL(ratingandnotes.ridetakerrating, 0) ), 1), 0) as avg 
from ratingandnotes join pool_requests on ratingandnotes.poolrequestid = pool_requests.id 
LEFT OUTER JOIN rides_management r1 ON pool_requests.ride_id = r1.ride_id
LEFT OUTER JOIN users u1 ON r1.user_id = u1.id
LEFT OUTER JOIN ride_seeker_details r2 ON pool_requests.rideseeker_id = r2.seeker_id
LEFT OUTER JOIN users u2 ON r2.user_id = u2.id
WHERE u1.id = '"+dto.getId()+"'*/
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		if(rs.next()) {
			//dto.setAverageRating(rs.getFloat(1)); 
			takerRating = rs.getFloat(1);
		}
		rs.close();
		pstmt.close();

		query = new StringBuilder();
		//query.append("select round(AVG(IFNULL(ratingandnotes.ridegiverrating, 0) + IFNULL(ratingandnotes.ridetakerrating, 0)), 1) as average_rating from ratingandnotes join pool_requests on ratingandnotes.poolrequestid = pool_requests.id join ride_seeker_details ON ride_seeker_details.seeker_id = pool_requests.rideseeker_id WHERE ride_seeker_details.user_id = '"+ dto.getId() +"'");
		query.append("select IFNULL(round(AVG(IFNULL(ratingandnotes.ridegiverrating, 0) ), 1), 0) as avg from ratingandnotes join pool_requests on ratingandnotes.poolrequestid = pool_requests.id LEFT OUTER JOIN rides_management r1 ON pool_requests.ride_id = r1.ride_id LEFT OUTER JOIN users u1 ON r1.user_id = u1.id LEFT OUTER JOIN ride_seeker_details r2 ON pool_requests.rideseeker_id = r2.seeker_id LEFT OUTER JOIN users u2 ON r2.user_id = u2.id where u2.id = '"+ dto.getId() +"' AND ! ISNULL(ratingandnotes.ridegiverrating)");

		pstmt = con.prepareStatement(query.toString());
		rs = QueryExecuter.getResultSet(pstmt, query.toString());
		if(rs.next()) {
			//dto.setAverageRating(rs.getFloat(1)); 
			giverRating = rs.getFloat(1);
		}
		rs.close();
		pstmt.close();
		if(takerRating > 0 && giverRating > 0) {
			avgRating = (takerRating + giverRating) / 2;
		} else if(takerRating > 0 && giverRating <= 0) {
			avgRating = takerRating;
		} else if(takerRating <= 0 && giverRating > 0) {
			avgRating = giverRating;
		}

		dto.setAverageRaing(avgRating);
		return dto;
	}

	public PoolRequestsDTO insertInPool(Connection con ,PoolRequestsDTO pooldto)throws SQLException {

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO pool_requests (id,ride_id,rideseeker_id, created_dt, request_time, request_status, user_id, ride_type, request_by, startRReplayresult, endRReplayresult) VALUES (?,?,?, '" +ApplicationUtil.currentTimeStamp()+"', '" +ApplicationUtil.currentTimeStamp()+"', ?, ?, ?, ?, ?, ?) ");

		PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, pooldto.getPoolID());

		pstmt.setString(2, pooldto.getRidemanagerID());
		pstmt.setString(3, pooldto.getRideSeekerID());
		if(Validator.isEmpty(pooldto.getRequestStatus())) {
			pstmt.setString(4, "A");
		} else {
			pstmt.setString(4, pooldto.getRequestStatus());
		}
		pstmt.setInt(5, pooldto.getPoolRequestToId()); pstmt.setInt(6, pooldto.getRideType()); pstmt.setInt(7, pooldto.getPoolRequestBy());
		pstmt.setString(8, ""); pstmt.setString(9, "");
		pstmt.executeUpdate();
		ResultSet tableKeys = pstmt.getGeneratedKeys();
		tableKeys.next();
		int autoGeneratedID = tableKeys.getInt(1);
		tableKeys.close();
		pstmt.close();
		pooldto.setPoolID(String.valueOf(autoGeneratedID));
		return pooldto;
	}

	public void UpdatePoolForIsResult(Connection con) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("select ride_seeker_details.seeker_id, ride_seeker_details.ride_match_rideid from ride_seeker_details where ride_seeker_details.is_result = 'Y' AND concat(ride_seeker_details.seeker_id,'-',ride_seeker_details.ride_match_rideid) NOT IN (select concat(pool_requests.rideseeker_id, '-', pool_requests.ride_id) from pool_requests) ;");//FOR UPDATE

		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			//dto.setAverageRating(rs.getFloat(1)); 
			PoolRequestsDTO pooldto = new PoolRequestsDTO();
			pooldto.setRideSeekerID(rs.getString(1));
			pooldto.setRidemanagerID(rs.getString(2));
			insertInPool(con, pooldto);
		}
		rs.close();
		pstmt.close();
	}
	public void changePoolRequestStatusForRideGiver(Connection con, int rideId) throws SQLException {
		final StringBuilder query = new StringBuilder();
		query.append("UPDATE pool_requests set request_status = ? WHERE rideseeker_id = ?");
		final PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, "C");
		pstmt.setInt(2, rideId);
		pstmt.executeUpdate();
		pstmt.close();
	}
	public void changePoolRequestStatusForRideTaker(Connection con, int rideSeekerId) throws SQLException {
		final StringBuilder query = new StringBuilder();
		query.append("UPDATE pool_requests set request_status = ? WHERE ride_id = ?");
		final PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, "C");
		pstmt.setInt(2, rideSeekerId);
		pstmt.executeUpdate();
		pstmt.close();
	}

	public void updateRideIdDropTake(Connection con, RideManagementDTO rideToDrop, RideManagementDTO rideToTake) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE pool_requests set ride_id = ? WHERE ride_id = ? ");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, rideToTake.getRideID());
		pstmt.setString(2, rideToDrop.getRideID());
		pstmt.executeUpdate();
		pstmt.close();
		
		query = new StringBuilder();
		query.append("update ride_seeker_details r set r.ride_match_rideid = ?, r.updated_dt = '"+ApplicationUtil.currentTimeStamp()+"' WHERE r.ride_match_rideid = ? AND TIMESTAMP(r.start_time) > '"+ApplicationUtil.currentTimeStamp()+"' ");
		pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, rideToTake.getRideID());
		pstmt.setString(2, rideToDrop.getRideID());
		pstmt.executeUpdate();
		pstmt.close();
	}
}