package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hopon.dto.FrequencyDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.QueryExecuter;
import com.mysql.jdbc.Statement;

public class FrequencyDAO {

	public FrequencyDTO insertFrequency(Connection con ,FrequencyDTO frequencyDTO)throws SQLException {


		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO trip_frequency (Trip_Freq_P,Days, ride_management_id, ride_seeker_id, Time, Start_date, End_date) VALUES (?,?,?,?,?,?,?) ");

		PreparedStatement pstmt = con.prepareStatement(query.toString() ,Statement.RETURN_GENERATED_KEYS);

		pstmt.setString(1,frequencyDTO.getFrequencyID());
		pstmt.setString(2, frequencyDTO.getFrequency().toString());
		pstmt.setString(3, frequencyDTO.getRideManagementId());
		pstmt.setString(4, frequencyDTO.getRideSeekerId());

		pstmt.setString(5, frequencyDTO.getTime().toString());
		pstmt.setString(6, frequencyDTO.getStartDate());
		pstmt.setString(7, frequencyDTO.getEndDate());

		pstmt.executeUpdate();
		//rideManagementDTO.setRideID(pstmt.getGeneratedKeys().getString(1));
		ResultSet tableKeys = pstmt.getGeneratedKeys();
		tableKeys.next();

		tableKeys.close();
		pstmt.close();

		return frequencyDTO;
	}

	public List<FrequencyDTO> fetchFrequencyListForRideSeeker(Connection con, String rideSeekerId) throws SQLException {
		List<FrequencyDTO> dtos = new ArrayList<FrequencyDTO>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT Trip_Freq_P,Days, ride_management_id, Time, Start_date, End_date from trip_frequency where ride_seeker_id = " + rideSeekerId);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			FrequencyDTO dto = new FrequencyDTO();
			dto.setFrequencyID(rs.getString(1));
			String str[] = rs.getString(2).replace("[", "").replace("]", "").split(",");
			for(int i=0;i<str.length;i++) str[i] = str[i].trim();
			
			dto.setFrequency(Arrays.asList(str));
			dto.setRideManagementId(rs.getString(3));
			dto.setRideSeekerId(rideSeekerId);
			try {
				dto.setTime(ApplicationUtil.dateFormat5.parse(rs.getString(4)));
			} catch (ParseException e) { }
			dto.setStartDate(rs.getString(5));
			dto.setEndDate(rs.getString(6));
			dtos.add(dto);
		}
		rs.close(); pstmt.close();
		return dtos;
	}
	public List<FrequencyDTO> fetchFrequencyListForRideManager(Connection con, String rideManagerId) throws SQLException {
		List<FrequencyDTO> dtos = new ArrayList<FrequencyDTO>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT Trip_Freq_P,Days, ride_seeker_id, Time, Start_date, End_date from trip_frequency where ride_management_id = " + rideManagerId);
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			FrequencyDTO dto = new FrequencyDTO();
			dto.setFrequencyID(rs.getString(1));
			String str[] = rs.getString(2).replace("[", "").replace("]", "").split(",");
			for(int i=0;i<str.length;i++) str[i] = str[i].trim();
			
			dto.setFrequency(Arrays.asList(str));
			dto.setRideSeekerId(rs.getString(3));
			dto.setRideManagementId(rideManagerId);
			try {
				dto.setTime(ApplicationUtil.dateFormat5.parse(rs.getString(4)));
			} catch (ParseException e) { }
			dto.setStartDate(rs.getString(5));
			dto.setEndDate(rs.getString(6));
			dtos.add(dto);
		}
		rs.close(); pstmt.close();
		return dtos;
	}
}
