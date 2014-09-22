package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hopon.dto.CombineRideDTO;
import com.hopon.dto.ManageRideDTO;
import com.hopon.dto.ManageRideFormDTO;
import com.hopon.dto.MatchedTripDTO;
import com.hopon.dto.RideManagementDTO;
import com.hopon.dto.RideSeekerDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.LoggerSingleton;
import com.hopon.utils.QueryExecuter;

public class MatchTripDAO {
	public List<MatchedTripDTO> findAllMatchedTripByCondition(final Connection con , final String startPoint,final String endPoint ,final String rideDate, final int circleId )throws SQLException{
		final List<MatchedTripDTO>  tripList = new ArrayList<MatchedTripDTO>();
		final StringBuilder query = new StringBuilder();	   
		query.append("select ride_seeker_details.seeker_id, ride_seeker_details.start_point,ride_seeker_details.destination_point,ride_seeker_details.start_tw_early,ride_seeker_details.user_id,users.first_name,trip_frequency.Days, ride_seeker_details.group_id, ride_seeker_details.recurring, ride_seeker_details.fullDay from users,trip_frequency");
		if(circleId > 0) {
			query.append(",circle_members ");
		}
		query.append(", ride_seeker_details LEFT OUTER JOIN approver a ON ride_seeker_details.approverID = a.id LEFT OUTER JOIN company c ON a.createdBy = c.userID ");
		//query.append(" WHERE TIMESTAMPDIFF(MINUTE, '" +ApplicationUtil.currentTimeStamp()+"', ride_seeker_details.start_tw_early) > 0 AND ride_seeker_details.`status`= 'A' AND is_result = 'N' AND isSharedTaxi = '0' ");
		//query.append(" WHERE TIMESTAMPDIFF(MINUTE, '" +ApplicationUtil.currentTimeStamp()+"', ride_seeker_details.start_tw_early) > 0 AND (ride_seeker_details.`status`= 'A' OR (HOUR(ride_seeker_details.start_tw_early) BETWEEN 20 AND 24 OR HOUR(ride_seeker_details.start_tw_early) BETWEEN 0 AND 6 AND ride_seeker_details.`status` IN('O', 'T'))) AND is_result = 'N' AND isSharedTaxi = '0' ");
		query.append(" WHERE TIMESTAMPDIFF(MINUTE, '" +ApplicationUtil.currentTimeStamp()+"', ride_seeker_details.start_tw_early) > 0 AND (ride_seeker_details.`status`= 'A' OR (ride_seeker_details.`status` IN('O', 'T') AND ((HOUR(ride_seeker_details.start_tw_early) * 60 + MINUTE(ride_seeker_details.start_tw_early)) >= (HOUR(c.fromNight) * 60 + MINUTE(c.fromNight)) AND (HOUR(ride_seeker_details.start_tw_early) * 60 + MINUTE(ride_seeker_details.start_tw_early)) <= 1439) OR ((HOUR(ride_seeker_details.start_tw_early) * 60 + MINUTE(ride_seeker_details.start_tw_early)) >= 0 AND (HOUR(ride_seeker_details.start_tw_early) * 60 + MINUTE(ride_seeker_details.start_tw_early)) <= (HOUR(c.toNight) * 60 + MINUTE(c.toNight))))) AND is_result = 'N' AND isSharedTaxi = '0' ");
		query.append(" AND ride_seeker_details.user_id = users.id AND  users.status = 'A' AND ride_seeker_details.recurring != 'Y' ");
		query.append(" AND trip_frequency.ride_seeker_id=ride_seeker_details.seeker_id ");
		//query.append(" AND ride_seeker_details.seeker_id not in (select pool_requests.rideseeker_id from pool_requests) ");

		if(startPoint != null && !startPoint.trim().equals("")) query.append(" AND ride_seeker_details.start_point like '%"+startPoint+"%' ");
		if(rideDate != null && !rideDate.trim().equals("")) query.append(" AND datediff('"+rideDate+"',ride_seeker_details.start_tw_early) =0 ");
		if(endPoint != null && !endPoint.trim().equals("")) query.append(" AND ride_seeker_details.destination_point like '%"+endPoint+"%' ");
		if(circleId > 0) {
			query.append(" AND circle_members.Status = '1' AND ride_seeker_details.user_id=circle_members.MemberId AND circle_members.CircleId='"+circleId+"' ");
		}
		query.append(" ORDER BY ride_seeker_details.group_id");


		final PreparedStatement pstmt = con.prepareStatement(query.toString());
		final ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {				
			MatchedTripDTO dto = new MatchedTripDTO();
			dto.setSeekerID(rs.getString(1));
			dto.setStartPoint(rs.getString(2));
			dto.setEndPoint(rs.getString(3));
			if(rs.getString(4)!=null){
				SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern8);
				SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
				try {
					Date date = formatter.parse(rs.getString(4).substring(0, rs.getString(4).length()-5));
					dto.setStartDate(formatter1.format(date));
				}
				catch (ParseException e) {
					LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
				}
				dto.setUserName(rs.getString(6));
				dto.setCircleName("");
				dto.setCompanyName("");
				dto.setFrequency(rs.getString(7).substring(1,rs.getString(7).length()-1));
				dto.setGroupId(rs.getString(8));
				dto.setRecurring(false);dto.setFullDay(false);
				if(rs.getString(9).equalsIgnoreCase("Y")) dto.setRecurring(true);
				if(rs.getString(10).equalsIgnoreCase("Y")) dto.setFullDay(true);
				tripList.add(dto);
			}
		}
		rs.close();
		pstmt.close();
		return tripList;		
	}
	
	public List<CombineRideDTO> getAllCombineVehicleList(final Connection con , final String startPoint,final String endPoint ,final String rideDate, final int circleId )throws SQLException{
		final List<CombineRideDTO>  tripList = new ArrayList<CombineRideDTO>();
		final StringBuilder query = new StringBuilder();	   
		
		if(circleId > 0) {
			query.append("select p.ride_id, r1.start_point, r1.destination_point, r1.start_time, u.id, u.first_name, v.id, v.registration_no, v.Capacity, count(*) as capacity_used from pool_requests p INNER JOIN rides_management r1 ON p.ride_id = r1.ride_id INNER JOIN users u ON r1.user_id = u.id INNER JOIN vehicles_master v ON r1.vehicleID = v.id INNER JOIN circle_members cm ON r1.user_id = cm.MemberId WHERE r1.`status` IN('A', 'T') AND r1.start_time > '"+ApplicationUtil.currentTimeStamp()+"' AND p.request_status = 'A' ");
		} else {
			query.append("select p.ride_id, r1.start_point, r1.destination_point, r1.start_time, u.id, u.first_name, v.id, v.registration_no, v.Capacity, count(*) as capacity_used from pool_requests p INNER JOIN rides_management r1 ON p.ride_id = r1.ride_id INNER JOIN users u ON r1.user_id = u.id INNER JOIN vehicles_master v ON r1.vehicleID = v.id WHERE r1.`status` IN('A', 'T') AND r1.start_time > '"+ApplicationUtil.currentTimeStamp()+"' AND p.request_status = 'A' ");
		}
		
		if(startPoint != null && !startPoint.trim().equals("")) query.append(" AND r1.start_point like '%"+startPoint+"%' ");
		if(rideDate != null && !rideDate.trim().equals("")) query.append(" AND datediff('"+rideDate+"',r1.start_time) =0 ");
		if(endPoint != null && !endPoint.trim().equals("")) query.append(" AND r1.destination_point like '%"+endPoint+"%' ");
		if(circleId > 0) {
			query.append(" AND cm.Status = '1' AND cm.CircleId='"+circleId+"' ");
		}
		query.append(" group by r1.ride_id, v.id ORDER BY r1.start_time ASC");

		final PreparedStatement pstmt = con.prepareStatement(query.toString());
		final ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {				
			CombineRideDTO dto = new CombineRideDTO();
			dto.setRideId(rs.getString(1));
			dto.setStartPoint(rs.getString(2));
			dto.setEndPoint(rs.getString(3));
			if(rs.getString(4)!=null){
				try {
					dto.setRideTime(ApplicationUtil.dateFormat4.format(ApplicationUtil.dateFormat3.parse(rs.getString(4))));
				} catch (ParseException e) { }
			}
			dto.setUserId(rs.getInt(5));
			dto.setUserName(rs.getString(6));
			dto.setVehicleID(rs.getInt(7));
			dto.setVehicleRegNo(rs.getString(8));
			dto.setCapacity(rs.getInt(9));
			dto.setUsedCapacity(rs.getInt(10));
			tripList.add(dto);
		}
		rs.close();
		pstmt.close();
		return tripList;		
	}
	
	public MatchedTripDTO findAllMatchedTrip(Connection con , String vehicleID,String startTime ,String company )throws SQLException{
		 MatchedTripDTO dto = new MatchedTripDTO();
		 List<String>  rideId = new ArrayList<String>();
		 StringBuilder query = new StringBuilder();
	 	 StringBuilder query1 = new StringBuilder();
	 	 //query.append("select ride_id,vehicleID from rides_management where datediff('"+ApplicationUtil.currentDate+"',start_time) <10 and vehicleID ='"+vehicleID+"'");
	 	 query.append("select ride_id,vehicleID from rides_management where TIMESTAMPDIFF(MINUTE, '"+startTime+"',start_time) BETWEEN -30 AND 30 and vehicleID ='"+vehicleID+"'");
	 	 
	 	 PreparedStatement pstmt = con.prepareStatement(query.toString());
	 	 ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
	 
	 	 while(rs.next()) {
	 		 String id= rs.getString(1);
	 		 rideId.add(id);
	 	 }
	 	 if(rideId.size()!=0){
	 		 query1.append("select count(ride_id),ride_id from pool_requests where ride_id in ("+rideId.toString().substring(1,rideId.toString().length()-1)+") group by ride_id ");
	 		 PreparedStatement pstmt1 = con.prepareStatement(query1.toString());
	 		 ResultSet rs1 = QueryExecuter.getResultSet(pstmt1, query1.toString());
	 		 while(rs1.next()) {	 
	 			 dto.setCount(rs1.getString(1));
	 			 dto.setSeekerID(rs1.getString(1));	 	
	 		 }
	 	 }
	 	rs.close();
		pstmt.close();
		 /* query2.append("select ride_id, start_point, destination_point, time(start_time)  from rides_management where datediff('"+start_time+"',start_time) =0  and vehicleID='"+vehicleID+"';");
		  PreparedStatement pstmt2 = con.prepareStatement(query2.toString());
		  ResultSet rs2 = QueryExecuter.getResultSet(pstmt2, query2.toString());
		  while(rs2.next()) {
			 
			 //dto.setCount(rs2.getString(1));
		  }*/
	 	 return dto;
	  
}
	public List<RideManagementDTO> methodForPopupOne(Connection con ,String vehicleID,String starTime)throws SQLException{
		List<RideManagementDTO> rideList = new ArrayList<RideManagementDTO>();
		  StringBuilder query = new StringBuilder();
		  StringBuilder query2 = new StringBuilder();
		  query2.append("select ride_id, start_point, destination_point, time(start_time)  from rides_management where datediff('"+starTime+"',start_time) =0  and vehicleID='"+vehicleID+"'");
		  query.append("select count(ride_id) from pool_requests where ride_id in(select ride_id from rides_management where datediff('"+starTime+"',start_time) =0  and vehicleID='"+vehicleID+"')group by ride_id ");
		  PreparedStatement pstmt = con.prepareStatement(query.toString());
		  ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		  PreparedStatement pstmt2 = con.prepareStatement(query2.toString());
		  ResultSet rs2 = QueryExecuter.getResultSet(pstmt2, query2.toString());
		  while(rs2.next()) {
			  RideManagementDTO dto= new RideManagementDTO(); 
			  
			  dto.setRideID(rs2.getString(1));
			  dto.setFromAddress1(rs2.getString(2));
			  dto.setToAddress1(rs2.getString(3));
			  dto.setStartdateValue(rs2.getString(4));
			  rideList.add(dto);
		  }
		  while(rs.next()) {
			  int i=0;
			  rideList.get(i).setSeatUsed(rs.getString(1));
			 
		  }
		  rs.close();
			pstmt.close();
			rs2.close();
			pstmt2.close();
		  return rideList;
	}
	public List<RideSeekerDTO> allRideSeekerForAGivenRide(Connection con ,String rideID)throws SQLException{
		List<RideSeekerDTO> seekerList = new ArrayList<RideSeekerDTO>();
		StringBuilder query = new StringBuilder();
		query.append("select first_name, start_point, destination_point, time(start_tw_early), seeker_id, ride_seeker_details.status, ride_seeker_details.ride_match_rideid, ride_seeker_details.user_id from pool_requests, ride_seeker_details left join (users) on  (ride_seeker_details.user_id = users.id) where pool_requests.ride_id='"+rideID+"' and ride_seeker_details.seeker_id =pool_requests.rideseeker_id and (ride_seeker_details.`status`= 'A' OR (HOUR(ride_seeker_details.start_tw_early) BETWEEN 20 AND 24 OR HOUR(ride_seeker_details.start_tw_early) BETWEEN 0 AND 6 AND ride_seeker_details.`status` IN('O', 'T'))) AND ride_seeker_details.recurring != 'Y'");

		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			RideSeekerDTO dto = new RideSeekerDTO();
			dto.setUserName(rs.getString(1));
			dto.setFromAddress1(rs.getString(2));
			dto.setToAddress1(rs.getString(3));
			dto.setStartdateValue(rs.getString(4));
			dto.setSeekerID(rs.getString(5));
			dto.setStatus(rs.getString(6));
			dto.setRideMatchRideId(rs.getString(7));
			dto.setUserID(rs.getString(8));
			seekerList.add(dto);
		}
		rs.close();
		pstmt.close();
		return seekerList;
	}
	public List<MatchedTripDTO> findAllMatchedTripForCircle(Connection con , String circleId)throws SQLException{
		List<MatchedTripDTO>  tripList = new ArrayList<MatchedTripDTO>();
		StringBuilder query = new StringBuilder();
		query.append("select ride_seeker_details.seeker_id, ride_seeker_details.start_point,ride_seeker_details.destination_point,ride_seeker_details.start_tw_early,ride_seeker_details.user_id,users.first_name,trip_frequency.Days from circle_members,users,ride_seeker_details,trip_frequency where circle_members.CircleId='"+circleId+"' AND circle_members.Status = '1' and ride_seeker_details.user_id=circle_members.MemberId and ride_seeker_details.`status`= 'A' and ride_seeker_details.user_id = users.id and trip_frequency.ride_seeker_id=ride_seeker_details.seeker_id AND DATEDIFF(ride_seeker_details.start_tw_early, '"+ApplicationUtil.currentDate()+"') >= 0 AND ride_seeker_details.recurring != 'Y'");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			MatchedTripDTO dto = new MatchedTripDTO();
			dto.setSeekerID(rs.getString(1));
			dto.setStartPoint(rs.getString(2));
			dto.setEndPoint(rs.getString(3));
			if(rs.getString(4)!=null){
				SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern8);
				SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
				try {
					Date date = formatter.parse(rs.getString(4).substring(0, (rs.getString(4).length()-5)));
					dto.setStartDate(formatter1.format(date));
				}
				catch (ParseException e) {
					LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
				}
				dto.setUserName(rs.getString(6));
				dto.setCircleName("");
				dto.setCompanyName("");
				dto.setFrequency(rs.getString(7).substring(1,rs.getString(7).length()-1));
				tripList.add(dto);
			}
		}
		rs.close();
		pstmt.close();
		return tripList;
	 }

	public List<ManageRideDTO> findPendingManageRide(final Connection con, ManageRideFormDTO manageRideFormDTO) throws SQLException {
		final List<ManageRideDTO>  rideList = new ArrayList<ManageRideDTO>();
		StringBuilder query = new StringBuilder();
		
		String sql1 = "select ride_seeker_details.seeker_id, ride_seeker_details.start_point,ride_seeker_details.destination_point,ride_seeker_details.start_tw_early,ride_seeker_details.user_id,users.first_name, users.gender, ratingandnotes.ridegiverrating from ride_seeker_details left outer join users ON ride_seeker_details.user_id = users.id left outer join pool_requests ON ride_seeker_details.seeker_id = pool_requests.rideseeker_id left outer join ratingandnotes ON pool_requests.id = ratingandnotes.poolrequestid WHERE ride_seeker_details.isSharedTaxi = '0' AND ride_seeker_details.is_result = 'N' AND ride_seeker_details.status = 'A' AND ride_seeker_details.start_tw_early > '" +ApplicationUtil.currentTimeStamp()+"' ";

		String sql2 = "select rides_management.ride_id, rides_management.start_point, rides_management.destination_point, rides_management.start_time, rides_management.user_id, users.first_name, users.gender, ratingandnotes.ridetakerrating from rides_management left outer join users ON rides_management.user_id = users.id left outer join pool_requests ON rides_management.ride_id = pool_requests.ride_id left outer join ratingandnotes ON pool_requests.id = ratingandnotes.poolrequestid where rides_management.status ='A' AND rides_management.start_time > '" +ApplicationUtil.currentTimeStamp()+"' ";

		if(manageRideFormDTO.getFrom() != null && !manageRideFormDTO.getFrom().trim().equals("")) {
			sql1 += " AND ride_seeker_details.start_point like '%"+ manageRideFormDTO.getFrom().trim() +"%' ";
			sql2 += " AND rides_management.start_point like '%"+ manageRideFormDTO.getFrom().trim() +"%' ";
		}
		if(manageRideFormDTO.getTo() != null && !manageRideFormDTO.getTo().trim().equals("")) {
			sql1 += " AND ride_seeker_details.destination_point like '%"+ manageRideFormDTO.getTo().trim() +"%' ";
			sql2 += " AND rides_management.destination_point like '%"+ manageRideFormDTO.getTo().trim() +"%' ";
		}
		if(manageRideFormDTO.getRideDate() != null && !manageRideFormDTO.getRideDate().equals("")) {
			sql1 += " AND DATE(ride_seeker_details.start_tw_early) = '"+ manageRideFormDTO.getRideDate() +"' ";
			sql2 += " AND DATE(rides_management.start_time) = '"+ manageRideFormDTO.getRideDate() +"' ";
		}
		if(manageRideFormDTO.getMyCircleId() > 0 ) {
			sql1 += " AND users.id IN (select circle_members.MemberId from circle_members, circle_owner where circle_members.CircleId = circle_owner.CircleId AND circle_owner.Status = '1' AND circle_members.Status = '1' AND circle_owner.CircleId = "+manageRideFormDTO.getMyCircleId()+") ";
			sql2 += " AND users.id IN (select circle_members.MemberId from circle_members, circle_owner where circle_members.CircleId = circle_owner.CircleId AND circle_owner.Status = '1' AND circle_members.Status = '1' AND circle_owner.CircleId = "+manageRideFormDTO.getMyCircleId()+") ";
		}
		if(manageRideFormDTO.getAffiliatedCircleId() > 0) {
			sql1 += " AND users.id IN (select circles.CircleOwner_Member_Id_P from circleaffiliations LEFT outer join circles on circleaffiliations.CircleId = circles.Circle_Id WHERE circleaffiliations.AffilicatedCircle = "+ manageRideFormDTO.getAffiliatedCircleId() +") ";
			sql2 += " AND users.id IN (select circles.CircleOwner_Member_Id_P from circleaffiliations LEFT outer join circles on circleaffiliations.CircleId = circles.Circle_Id WHERE circleaffiliations.AffilicatedCircle = "+ manageRideFormDTO.getAffiliatedCircleId() +") ";
		}
		if(manageRideFormDTO.getUserId() > 0) {
			sql1 += " AND users.id = " + manageRideFormDTO.getUserId() + " ";
			sql2 += " AND users.id = " + manageRideFormDTO.getUserId() + " ";
		}
		
		sql1 += " ORDER BY ride_seeker_details.start_tw_early DESC ";
		sql2 += " ORDER BY rides_management.start_time DESC ";
		
		query.append(sql1);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			ManageRideDTO dto = new ManageRideDTO();
			dto.setRequestId(rs.getInt(1));
			dto.setFrom(rs.getString(2));
			dto.setTo(rs.getString(3));
			dto.setRideTime(rs.getString(4));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setRideTime(formatter1.format(date));
			}
			catch (ParseException e) { }
			dto.setName(rs.getString(6));
			dto.setGender(rs.getString(7));
			dto.setRideRating(rs.getString(8));
			dto.setRole("taker");
			dto.setRideOption("pending");
			rideList.add(dto);
		}
		rs.close();
		pstmt.close();
		
		query = new StringBuilder();
		query.append(sql2);
		pstmt = con.prepareStatement(query.toString());
		rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			ManageRideDTO dto = new ManageRideDTO();
			dto.setRideId(rs.getInt(1));
			dto.setFrom(rs.getString(2));
			dto.setTo(rs.getString(3));
			dto.setRideTime(rs.getString(4));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setRideTime(formatter1.format(date));
			}
			catch (ParseException e) { }
			dto.setName(rs.getString(6));
			dto.setGender(rs.getString(7));
			dto.setRideRating(rs.getString(8));
			dto.setRole("giver");
			dto.setRideOption("pending");
			rideList.add(dto);
		}
		rs.close();
		pstmt.close();
		
		
		return rideList;
	}

	public List<ManageRideDTO> findCompletedManageRide(final Connection con, ManageRideFormDTO manageRideFormDTO) throws SQLException {
		final List<ManageRideDTO>  rideList = new ArrayList<ManageRideDTO>();
		StringBuilder query = new StringBuilder();
		
		String sql1 = "select ride_seeker_details.seeker_id, ride_seeker_details.start_point,ride_seeker_details.destination_point, ride_seeker_details.start_tw_early,ride_seeker_details.user_id,users.first_name, users.gender, ratingandnotes.ridegiverrating, pool_requests.ride_id from ride_seeker_details left outer join users ON ride_seeker_details.user_id = users.id LEFT OUTER JOIN pool_requests ON pool_requests.rideseeker_id = ride_seeker_details.seeker_id left outer join ratingandnotes ON pool_requests.id = ratingandnotes.poolrequestid WHERE ride_seeker_details.isSharedTaxi = '0' AND ride_seeker_details.is_result = 'Y' AND ride_seeker_details.status = 'A' AND ride_seeker_details.start_tw_early < '" +ApplicationUtil.currentTimeStamp()+"' AND DATEDIFF(ride_seeker_details.start_tw_early, '"+ApplicationUtil.currentDate()+"') > -30";
		String sql2 = "select pool_requests.ride_id, rides_management.start_point, rides_management.destination_point, rides_management.start_time, rides_management.user_id, users.first_name, users.gender, ratingandnotes.ridetakerrating from pool_requests left outer join rides_management ON pool_requests.ride_id = rides_management.ride_id left outer join users ON rides_management.user_id = users.id left outer join ratingandnotes ON pool_requests.id = ratingandnotes.poolrequestid  where rides_management.status ='A' AND rides_management.start_time < '" +ApplicationUtil.currentTimeStamp()+"' AND DATEDIFF(rides_management.TWstart_early, '"+ApplicationUtil.currentDate()+"') > -30";

		if(manageRideFormDTO.getFrom() != null && !manageRideFormDTO.getFrom().trim().equals("")) {
			sql1 += " AND ride_seeker_details.start_point like '%"+ manageRideFormDTO.getFrom().trim() +"%' ";
			sql2 += " AND rides_management.start_point like '%"+ manageRideFormDTO.getFrom().trim() +"%' ";
		}
		if(manageRideFormDTO.getTo() != null && !manageRideFormDTO.getTo().trim().equals("")) {
			sql1 += " AND ride_seeker_details.destination_point like '%"+ manageRideFormDTO.getTo().trim() +"%' ";
			sql2 += " AND rides_management.destination_point like '%"+ manageRideFormDTO.getTo().trim() +"%' ";
		}
		if(manageRideFormDTO.getRideDate() != null && !manageRideFormDTO.getRideDate().equals("")) {
			sql1 += " AND DATE(ride_seeker_details.start_tw_early) = '"+ manageRideFormDTO.getRideDate() +"' ";
			sql2 += " AND DATE(rides_management.start_time) = '"+ manageRideFormDTO.getRideDate() +"' ";
		}
		if(manageRideFormDTO.getMyCircleId() > 0 ) {
			sql1 += " AND users.id IN (select circle_members.MemberId from circle_members, circle_owner where circle_members.CircleId = circle_owner.CircleId AND circle_owner.Status = '1' AND circle_members.Status = '1' AND circle_owner.CircleId = "+manageRideFormDTO.getMyCircleId()+") ";
			sql2 += " AND users.id IN (select circle_members.MemberId from circle_members, circle_owner where circle_members.CircleId = circle_owner.CircleId AND circle_owner.Status = '1' AND circle_members.Status = '1' AND circle_owner.CircleId = "+manageRideFormDTO.getMyCircleId()+") ";
		}
		if(manageRideFormDTO.getAffiliatedCircleId() > 0) {
			sql1 += " AND users.id IN (select circles.CircleOwner_Member_Id_P from circleaffiliations LEFT outer join circles on circleaffiliations.CircleId = circles.Circle_Id WHERE circleaffiliations.AffilicatedCircle = "+ manageRideFormDTO.getAffiliatedCircleId() +") ";
			sql2 += " AND users.id IN (select circles.CircleOwner_Member_Id_P from circleaffiliations LEFT outer join circles on circleaffiliations.CircleId = circles.Circle_Id WHERE circleaffiliations.AffilicatedCircle = "+ manageRideFormDTO.getAffiliatedCircleId() +") ";
		}
		if(manageRideFormDTO.getUserId() > 0) {
			sql1 += " AND users.id = " + manageRideFormDTO.getUserId() + " ";
			sql2 += " AND users.id = " + manageRideFormDTO.getUserId() + " ";
		}
		//sql1 += " FOR UPDATE";
		
		sql1 += " ORDER BY ride_seeker_details.start_tw_early DESC ";
		sql2 += " ORDER BY rides_management.start_time DESC ";
		
		query.append(sql1);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			ManageRideDTO dto = new ManageRideDTO();
			dto.setRequestId(rs.getInt(1));
			dto.setRideId(rs.getInt(9));
			dto.setFrom(rs.getString(2));
			dto.setTo(rs.getString(3));
			dto.setRideTime(rs.getString(4));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setRideTime(formatter1.format(date));
			}
			catch (ParseException e) { }
			dto.setName(rs.getString(6));
			dto.setGender(rs.getString(7));
			dto.setRideRating(rs.getString(8));
			dto.setRole("taker");
			dto.setRideOption("completed");
			rideList.add(dto);
		}
		rs.close();
		pstmt.close();
		
		query = new StringBuilder();
		query.append(sql2);
		pstmt = con.prepareStatement(query.toString());
		rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			ManageRideDTO dto = new ManageRideDTO();
			dto.setRideId(rs.getInt(1));
			dto.setFrom(rs.getString(2));
			dto.setTo(rs.getString(3));
			dto.setRideTime(rs.getString(4));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setRideTime(formatter1.format(date));
			}
			catch (ParseException e) { }
			dto.setName(rs.getString(6));
			dto.setGender(rs.getString(7));
			dto.setRideRating(rs.getString(8));
			dto.setRole("giver");
			dto.setRideOption("completed");
			rideList.add(dto);
		}
		rs.close();
		pstmt.close();
		
		
		return rideList;
	}
	public List<ManageRideDTO> findPendingMatchedManageRide(final Connection con, ManageRideFormDTO manageRideFormDTO) throws SQLException {
		final List<ManageRideDTO>  rideList = new ArrayList<ManageRideDTO>();
		StringBuilder query = new StringBuilder();
		
		String sql1 = "select ride_seeker_details.seeker_id, ride_seeker_details.start_point,ride_seeker_details.destination_point, ride_seeker_details.start_tw_early,ride_seeker_details.user_id,users.first_name, users.gender, ratingandnotes.ridegiverrating, pool_requests.ride_id from ride_seeker_details left outer join users ON ride_seeker_details.user_id = users.id LEFT OUTER JOIN pool_requests ON pool_requests.rideseeker_id = ride_seeker_details.seeker_id left outer join ratingandnotes ON pool_requests.id = ratingandnotes.poolrequestid WHERE ride_seeker_details.isSharedTaxi = '0' AND ride_seeker_details.is_result = 'Y' AND ride_seeker_details.status = 'A' AND ride_seeker_details.start_tw_early > '" +ApplicationUtil.currentTimeStamp()+"' ";
		String sql2 = "select pool_requests.ride_id, rides_management.start_point, rides_management.destination_point, rides_management.start_time, rides_management.user_id, users.first_name, users.gender, ratingandnotes.ridetakerrating from pool_requests left outer join rides_management ON pool_requests.ride_id = rides_management.ride_id left outer join users ON rides_management.user_id = users.id left outer join ratingandnotes ON pool_requests.id = ratingandnotes.poolrequestid  where rides_management.status ='A' AND rides_management.start_time > '" +ApplicationUtil.currentTimeStamp()+"' ";

		if(manageRideFormDTO.getFrom() != null && !manageRideFormDTO.getFrom().trim().equals("")) {
			sql1 += " AND ride_seeker_details.start_point like '%"+ manageRideFormDTO.getFrom().trim() +"%' ";
			sql2 += " AND rides_management.start_point like '%"+ manageRideFormDTO.getFrom().trim() +"%' ";
		}
		if(manageRideFormDTO.getTo() != null && !manageRideFormDTO.getTo().trim().equals("")) {
			sql1 += " AND ride_seeker_details.destination_point like '%"+ manageRideFormDTO.getTo().trim() +"%' ";
			sql2 += " AND rides_management.destination_point like '%"+ manageRideFormDTO.getTo().trim() +"%' ";
		}
		if(manageRideFormDTO.getRideDate() != null && !manageRideFormDTO.getRideDate().equals("")) {
			sql1 += " AND DATE(ride_seeker_details.start_tw_early) = '"+ manageRideFormDTO.getRideDate() +"' ";
			sql2 += " AND DATE(rides_management.start_time) = '"+ manageRideFormDTO.getRideDate() +"' ";
		}
		if(manageRideFormDTO.getMyCircleId() > 0 ) {
			sql1 += " AND users.id IN (select circle_members.MemberId from circle_members, circle_owner where circle_members.CircleId = circle_owner.CircleId AND circle_owner.Status = '1' AND circle_members.Status = '1' AND circle_owner.CircleId = "+manageRideFormDTO.getMyCircleId()+") ";
			sql2 += " AND users.id IN (select circle_members.MemberId from circle_members, circle_owner where circle_members.CircleId = circle_owner.CircleId AND circle_owner.Status = '1' AND circle_members.Status = '1' AND circle_owner.CircleId = "+manageRideFormDTO.getMyCircleId()+") ";
		}
		if(manageRideFormDTO.getAffiliatedCircleId() > 0) {
			sql1 += " AND users.id IN (select circles.CircleOwner_Member_Id_P from circleaffiliations LEFT outer join circles on circleaffiliations.CircleId = circles.Circle_Id WHERE circleaffiliations.AffilicatedCircle = "+ manageRideFormDTO.getAffiliatedCircleId() +") ";
			sql2 += " AND users.id IN (select circles.CircleOwner_Member_Id_P from circleaffiliations LEFT outer join circles on circleaffiliations.CircleId = circles.Circle_Id WHERE circleaffiliations.AffilicatedCircle = "+ manageRideFormDTO.getAffiliatedCircleId() +") ";
		}
		if(manageRideFormDTO.getUserId() > 0) {
			sql1 += " AND users.id = " + manageRideFormDTO.getUserId() + " ";
			sql2 += " AND users.id = " + manageRideFormDTO.getUserId() + " ";
		}
		//sql1 += " FOR UPDATE";
	
		sql1 += " ORDER BY ride_seeker_details.start_tw_early DESC ";
		sql2 += " ORDER BY rides_management.start_time DESC ";
		
		query.append(sql1);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			ManageRideDTO dto = new ManageRideDTO();
			dto.setRequestId(rs.getInt(1));
			dto.setRideId(rs.getInt(9));
			dto.setFrom(rs.getString(2));
			dto.setTo(rs.getString(3));
			dto.setRideTime(rs.getString(4));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setRideTime(formatter1.format(date));
			}
			catch (ParseException e) { }
			dto.setName(rs.getString(6));
			dto.setGender(rs.getString(7));
			dto.setRideRating(rs.getString(8));
			dto.setRole("taker");
			dto.setRideOption("matchedPending");
			rideList.add(dto);
		}
		rs.close();
		pstmt.close();
		
		query = new StringBuilder();
		query.append(sql2);
		pstmt = con.prepareStatement(query.toString());
		rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			ManageRideDTO dto = new ManageRideDTO();
			dto.setRideId(rs.getInt(1));
			dto.setFrom(rs.getString(2));
			dto.setTo(rs.getString(3));
			dto.setRideTime(rs.getString(4));
			SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern3);
			SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
			try {
				Date date = formatter.parse(rs.getString(4));
				dto.setRideTime(formatter1.format(date));
			}
			catch (ParseException e) { }
			dto.setName(rs.getString(6));
			dto.setGender(rs.getString(7));
			dto.setRideRating(rs.getString(8));
			dto.setRole("giver");
			dto.setRideOption("matchedPending");
			rideList.add(dto);
		}
		rs.close();
		pstmt.close();
		return rideList;
	}

	public List<MatchedTripDTO> findMatchTripByGroupId(final Connection con , final List<String> groupId) throws SQLException {
		final List<MatchedTripDTO>  tripList = new ArrayList<MatchedTripDTO>();
		final StringBuilder query = new StringBuilder();	   
		query.append("select ride_seeker_details.seeker_id, ride_seeker_details.start_point,ride_seeker_details.destination_point,ride_seeker_details.start_tw_early,ride_seeker_details.user_id,users.first_name,trip_frequency.Days, ride_seeker_details.group_id from users,ride_seeker_details,trip_frequency");
		query.append(" WHERE DATEDIFF(ride_seeker_details.start_tw_early, '"+ApplicationUtil.currentDate()+"') >= 0 AND ride_seeker_details.`status`= 'A' ");
		query.append(" AND ride_seeker_details.user_id = users.id AND  users.status = 'A' ");
		query.append(" AND trip_frequency.ride_seeker_id=ride_seeker_details.seeker_id ");
		
		String groupIdStr = "";
		for(String a:groupId) {
			groupIdStr += a + "', '";
		}
		query.append(" AND ride_seeker_details.group_id IN('"+ groupIdStr + "') AND ride_seeker_details.group_id != '' AND ride_seeker_details.recurring != 'Y'");
		query.append(" ORDER BY ride_seeker_details.group_id");
		final PreparedStatement pstmt = con.prepareStatement(query.toString());
		final ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {				
			MatchedTripDTO dto = new MatchedTripDTO();
			dto.setSeekerID(rs.getString(1));
			dto.setStartPoint(rs.getString(2));
			dto.setEndPoint(rs.getString(3));
			if(rs.getString(4)!=null){
				SimpleDateFormat formatter = new SimpleDateFormat(ApplicationUtil.datePattern8);
				SimpleDateFormat formatter1 = new SimpleDateFormat(ApplicationUtil.datePattern9);
				try {
					Date date = formatter.parse(rs.getString(4).substring(0, rs.getString(4).length()-5));
					dto.setStartDate(formatter1.format(date));
				} catch (ParseException e) {
					LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+"Date is : "+rs.getString(4)+"."+e.getMessage());
				}
				dto.setUserName(rs.getString(6));
				dto.setCircleName("");
				dto.setCompanyName("");
				dto.setFrequency(rs.getString(7).substring(1,rs.getString(7).length()-1));
				dto.setGroupId(rs.getString(8));
				tripList.add(dto);
			}
		}
		rs.close();
		pstmt.close();
		return tripList;
	}
	
	public Map<Integer, List<RideSeekerDTO>> getunAssignedTaxi(Connection con) throws SQLException {
		Map<Integer, List<RideSeekerDTO>> maps = new HashMap<Integer, List<RideSeekerDTO>>();
		final StringBuilder query = new StringBuilder();
		//query.append("select r.seeker_id, r.start_point,r.destination_point,r.start_tw_early,r.user_id,users.first_name,trip_frequency.Days, r.group_id from users,ride_seeker_details r,trip_frequency WHERE TIMESTAMPDIFF(HOUR, '" +ApplicationUtil.currentTimeStamp()+"', r.start_tw_early) >= 10 AND r.`status`= 'A' AND r.is_result = 'N' AND r.isSharedTaxi = '0' AND r.user_id = users.id AND  users.status = 'A' AND  trip_frequency.ride_seeker_id=r.seeker_id ORDER BY r.user_id");
		query.append("SELECT u.id from users u where u.status = 'A' AND u.travel = 'T'");
		final PreparedStatement pstmt = con.prepareStatement(query.toString());
		final ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {				
			List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
			StringBuilder query1 = new StringBuilder();
			query1.append("select r.seeker_id,r.user_id, r.start_point,r.destination_point,r.start_tw_early,users.first_name from users,ride_seeker_details r,circle_members WHERE TIMESTAMPDIFF(HOUR, '" +ApplicationUtil.currentTimeStamp()+"', r.start_tw_early) BETWEEN 0 AND 10 AND r.`status`= 'A' AND r.is_result = 'N' AND r.isSharedTaxi = '0' AND r.user_id = users.id AND  users.status = 'A' AND circle_members.Status = '1' AND r.user_id=circle_members.MemberId AND circle_members.CircleId IN ( select circleaffiliations.CircleId from circles JOIN circleaffiliations ON circles.Circle_Id = circleaffiliations.AffilicatedCircle where circleaffiliations.Status = 'A' AND r.recurring != 'Y' AND circles.CircleOwner_Member_Id_P = '"+rs.getInt(1)+"')");
			PreparedStatement pstmt1 = con.prepareStatement(query1.toString());
			ResultSet rs1 = QueryExecuter.getResultSet(pstmt1, query1.toString());
			while(rs1.next()) {
				RideSeekerDTO dto = new RideSeekerDTO();
				dto.setSeekerID(rs1.getString(1));
				dto.setUserID(rs1.getString(2));
				dto.setFromAddress1(rs1.getString(3));
				dto.setToAddress1(rs1.getString(4));
				try {
					Date date = ApplicationUtil.dateFormat3.parse(rs1.getString(5));
					dto.setStartdateValue(ApplicationUtil.dateFormat9.format(date));
				} catch(ParseException e) {}
				dto.setUserName(rs1.getString(6));
				
				dtos.add(dto);
			}
			if(dtos.size() > 0) {
				maps.put(rs.getInt(1), dtos);
			}
			rs1.close();
			pstmt1.close();
		}
		rs.close();
		pstmt.close();
		return maps;
	}
	

	public Map<Integer, List<RideSeekerDTO>> getunAssignedTaxiForOwner(Connection con) throws SQLException {
		Map<Integer, List<RideSeekerDTO>> maps = new HashMap<Integer, List<RideSeekerDTO>>();
		final StringBuilder query = new StringBuilder();
		//query.append("select r.seeker_id, r.start_point,r.destination_point,r.start_tw_early,r.user_id,users.first_name,trip_frequency.Days, r.group_id from users,ride_seeker_details r,trip_frequency WHERE TIMESTAMPDIFF(HOUR, '" +ApplicationUtil.currentTimeStamp()+"', r.start_tw_early) >= 10 AND r.`status`= 'A' AND r.is_result = 'N' AND r.isSharedTaxi = '0' AND r.user_id = users.id AND  users.status = 'A' AND  trip_frequency.ride_seeker_id=r.seeker_id ORDER BY r.user_id");
		query.append("SELECT u.id from users u where u.status = 'A' AND u.travel != 'T'");
		final PreparedStatement pstmt = con.prepareStatement(query.toString());
		final ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {				
			List<RideSeekerDTO> dtos = new ArrayList<RideSeekerDTO>();
			StringBuilder query1 = new StringBuilder();
			query1.append("select r.seeker_id,r.user_id, r.start_point,r.destination_point,r.start_tw_early,users.first_name from users,ride_seeker_details r,circle_members WHERE TIMESTAMPDIFF(HOUR, '" +ApplicationUtil.currentTimeStamp()+"', r.start_tw_early) BETWEEN 0 AND 12 AND r.`status`= 'A' AND r.is_result = 'N' AND r.isSharedTaxi = '0' AND r.recurring != 'Y' AND r.user_id = users.id AND  users.status = 'A' AND circle_members.Status = '1' AND r.user_id=circle_members.MemberId AND circle_members.CircleId IN ( SELECT c1.Circle_Id from circles c1 where c1.CircleOwner_Member_Id_P = '"+rs.getInt(1)+"')");
			PreparedStatement pstmt1 = con.prepareStatement(query1.toString());
			ResultSet rs1 = QueryExecuter.getResultSet(pstmt1, query1.toString());
			while(rs1.next()) {
				RideSeekerDTO dto = new RideSeekerDTO();
				dto.setSeekerID(rs1.getString(1));
				dto.setUserID(rs1.getString(2));
				dto.setFromAddress1(rs1.getString(3));
				dto.setToAddress1(rs1.getString(4));
				try {
					Date date = ApplicationUtil.dateFormat3.parse(rs1.getString(5));
					dto.setStartdateValue(ApplicationUtil.dateFormat9.format(date));
				} catch(ParseException e) {}
				dto.setUserName(rs1.getString(6));
				
				dtos.add(dto);
			}
			if(dtos.size() > 0) {
				maps.put(rs.getInt(1), dtos);
			}
			rs1.close();
			pstmt1.close();
		}
		rs.close();
		pstmt.close();
		return maps;
	}
}
