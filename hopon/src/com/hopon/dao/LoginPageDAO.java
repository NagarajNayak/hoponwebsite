package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hopon.dto.LoginPageDTO;
import com.hopon.utils.QueryExecuter;

public class LoginPageDAO {
	public void insertLoginPage(Connection con, LoginPageDTO dto) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO loginpage(userId, pageId, status) VALUES(?, ?, ?)");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, dto.getUserId());
		pstmt.setInt(2, dto.getPageId());
		pstmt.setString(3, dto.getStatus());
		pstmt.executeUpdate();
		pstmt.close();
	}
	public List<LoginPageDTO> getLoginPagesByUserId(Connection con, int userId) throws SQLException {
		List<LoginPageDTO> dtos = new ArrayList<LoginPageDTO>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT l.userId, l.pageId, l.status, p.pageCode, p.pageURL from loginpage l LEFT OUTER JOIN pagestore p ON l.pageId = p.pageId WHERE l.status = 'N' AND l.userId = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, userId);
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			LoginPageDTO dto = new LoginPageDTO();
			dto.setUserId(rs.getInt(1));
			dto.setPageId(rs.getInt(2));
			dto.setStatus(rs.getString(3));
			dto.setPageCode(rs.getString(4));
			dto.setPageURL(rs.getString(5));
			dtos.add(dto);
		}
		rs.close();
		pstmt.close();
		return dtos;
	}
	public void updateLoginPageByUserId(Connection con, LoginPageDTO dto) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE loginpage SET pageId = ?, status = ? WHERE userId = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, dto.getPageId());
		pstmt.setString(2, dto.getStatus());
		pstmt.setInt(3, dto.getUserId());
		pstmt.executeUpdate();
		pstmt.close();
	}
	public void inactiveLoginPageByUserId(Connection con, int userId, int pageId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE loginpage SET status = 'C' WHERE userId = ? AND pageId = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, userId);
		pstmt.setInt(2, pageId);
		pstmt.executeUpdate();
		pstmt.close();
	}
	public void activeLoginPageByUserId(Connection con, int userId, int pageId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE loginpage SET status = 'N' WHERE userId = ? AND pageId = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, userId);
		pstmt.setInt(2, pageId);
		pstmt.executeUpdate();
		pstmt.close();
	}
}
