package com.hopon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hopon.dto.PageStoreDTO;
import com.hopon.utils.QueryExecuter;

public class PageStoreDAO {
	public void insertPageStore(Connection con, PageStoreDTO dto) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO pagestore(pageId, pageCode, pageURL, description) VALUES(?, ?, ?, ?)");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, null);
		pstmt.setString(2, dto.getPageCode());
		pstmt.setString(3, dto.getPageUrl());
		pstmt.setString(4, dto.getDescription());
		pstmt.executeUpdate();
		pstmt.close();
	}
	public PageStoreDTO getPageStoreById(Connection con, int userId) throws SQLException {
		PageStoreDTO dto = new PageStoreDTO();
		StringBuilder query = new StringBuilder();
		query.append("SELECT p.pageCode, p.pageURL, p.description from pagestore p WHERE p.pageId = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		if(rs.next()) {
			dto.setPageId(userId);
			dto.setPageCode(rs.getString(1));
			dto.setPageUrl(rs.getString(2));
			dto.setDescription(rs.getString(3));
		}
		rs.close();
		pstmt.close();
		return dto;
	}
	public PageStoreDTO getPageStoreByCode(Connection con, String code) throws SQLException {
		PageStoreDTO dto = new PageStoreDTO();
		StringBuilder query = new StringBuilder();
		query.append("SELECT p.pageId, p.pageURL, p.description from pagestore p WHERE p.pageCode = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, code.trim());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		if(rs.next()) {
			dto.setPageId(rs.getInt(1));
			dto.setPageCode(code);
			dto.setPageUrl(rs.getString(2));
			dto.setDescription(rs.getString(3));
		}
		rs.close();
		pstmt.close();
		return dto;
	}
	public List<PageStoreDTO> getAllPageStore(Connection con) throws SQLException {
		List<PageStoreDTO> dtos = new ArrayList<PageStoreDTO>();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT p.pageCode, p.pageURL, p.description, p.pageId from pagestore p WHERE 1");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString());
		while(rs.next()) {
			PageStoreDTO dto = new PageStoreDTO();
			dto.setPageCode(rs.getString(1));
			dto.setPageUrl(rs.getString(2));
			dto.setDescription(rs.getString(3));
			dto.setPageId(rs.getInt(4));
			dtos.add(dto);
		}
		rs.close();
		pstmt.close();
		return dtos;
	}
	public void removePageStoreById(Connection con, int pageId) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("delete from pagestore p WHERE p.pageId = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setInt(1, pageId);
		pstmt.executeUpdate();
		pstmt.close();
	}
	public void updatePageStoreById(Connection con, PageStoreDTO dto) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE pagestore p SET p.pageCode = ?, p.pageURL = ?, p.description = ? WHERE p.pageId = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		pstmt.setString(1, dto.getPageCode());
		pstmt.setString(2, dto.getPageUrl());
		pstmt.setString(3, dto.getDescription());
		pstmt.setInt(4, dto.getPageId());
		pstmt.executeUpdate();
		pstmt.close();
	}
}
