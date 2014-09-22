package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hopon.dto.ContactusDTO;
import com.hopon.utils.ApplicationUtil;
import com.hopon.utils.LoggerSingleton;
import com.hopon.utils.QueryExecuter;
/**
 * 
 * @author:Nagaraja 
 * 
 * This<code>ContactUs</code>Method contains
 *
 */

public class ContactUsDAO {
	public void insertContactInfo(Connection con, ContactusDTO dto)
			throws SQLException {
		System.out.println("from main contactus dao is:" + dto);
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO contactus(first_name, last_name, mobile_no, email_id, comment) VALUES(?, ?, ?, ?, ?)");
		PreparedStatement pstmt = null;
		try {

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, dto.getFirst_name());
			pstmt.setString(2, dto.getLast_name());
			pstmt.setString(3, dto.getMobile_no());
			pstmt.setString(4, dto.getEmail_id());
			pstmt.setString(5, dto.getComment());
			int i = pstmt.executeUpdate();
			System.out.println("value of i is:" + i);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			pstmt.close();
		}
		System.out.println("Data Added Successfully");

	}

}
