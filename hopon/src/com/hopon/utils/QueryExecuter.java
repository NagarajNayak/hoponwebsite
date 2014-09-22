package com.hopon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Blob;

public class QueryExecuter {
	public static boolean executePreparedStatement(Connection con,String query,Object ...dtos) throws SQLException {
			PreparedStatement pstmt = con.prepareStatement(query);
			setColumnValuesForUpdate(pstmt,dtos);
			boolean flag=pstmt.execute();
			pstmt.close();
			return flag;
	}
	public static int executeUpdatePreparedStatement(Connection con,String query,Object ...dtos) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(query);
		setColumnValuesForUpdate(pstmt,dtos);
		int numberOfRows=pstmt.executeUpdate();
		pstmt.close();
		return numberOfRows;
	}
	private static void setColumnValuesForUpdate(PreparedStatement pstmt,Object ...dtos) throws SQLException {
		for (int i = 0; i < dtos.length; i++) {
			if(Validator.isEmpty(dtos[i])){
				dtos[i] = null;
			}
			pstmt.setObject(i+1, dtos[i]);
		}	
	}
	private static void setColumnValuesForLoad(PreparedStatement pstmt,Object ...dtos) throws SQLException {
		for (int i = 0; i < dtos.length; i++) {
			pstmt.setObject(i+1, dtos[i]);
		}
	}
	public synchronized static String nextId(Connection con,String column,String table,String prefix) throws SQLException {
		int prefixLength = prefix.length();
		String query = "select max(CONVERT(substring("+column+","+(prefixLength+1)+",LENGTH("+column+")),decimal)) from "+table;
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = getResultSet(pstmt,query);
		String nextId = null;
		if(rs.next()) {
			nextId = rs.getString(1);
		}
		pstmt.close();
		if(nextId == null || nextId.trim().equalsIgnoreCase("")) {
			nextId = "101";
		}else {
			int id = Integer.parseInt(nextId)+1;
			if(id<100) {
				id = 101;
			}
			nextId = id+"";
		}
		return prefix+nextId;
	}
	public static ResultSet getResultSet(PreparedStatement pstmt,String query,Object ...dtos) throws SQLException {
		//PreparedStatement pstmt = con.prepareStatement(query);
		setColumnValuesForLoad(pstmt,dtos);
		ResultSet rs = pstmt.executeQuery();
		//pstmt.close();
		return rs;
	}
	public static boolean isExistingName(Connection con,String columnName,String value,String tableName) throws SQLException {
		String query = "SELECT "+columnName+"	FROM "+tableName+"  WHERE "+columnName+" = ?";	
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = getResultSet(pstmt, query, value);
		boolean result = rs.next();
		pstmt.close();
		return result;
	}
	public static boolean isExistingName(Connection con,String columnName,String value,String tableName, String andClauseColumn, String andClauseValue) throws SQLException {
		String query = "SELECT "+columnName+"	FROM "+tableName+"  WHERE "+columnName+" = ? and " + andClauseColumn + " = ?";	
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = getResultSet(pstmt, query, value, andClauseValue);
		boolean result = rs.next();
		pstmt.close();
		return result;
	}

	public static boolean isExistingName(Connection con,String columnName,String value,String tableName, String staticClause) throws SQLException {
		String query = "SELECT "+columnName+"	FROM "+tableName+"  WHERE "+columnName+" = ? and " + staticClause;	
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = getResultSet(pstmt, query, value);
		boolean result = rs.next();
		pstmt.close();
		return result;
	}
	public static boolean isExistingId(Connection con,String columnName,String value,String tableName) throws SQLException {
		return isExistingName(con, columnName, value, tableName);
	}
	public static String lookupvaluefromtable(Connection con, String table_name, String lookup_col,
					String lookup_filter_col, String lookup_filter_value) throws SQLException {
		StringBuilder query = new StringBuilder();
		String lookedupvalue = "";
		
		query.append(" SELECT " + lookup_col + " FROM " +  table_name);
		query.append(" where " + lookup_filter_col + " = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString(),lookup_filter_value);
		if(rs.next()) {
			lookedupvalue = (rs.getString(1));
		}
		pstmt.close();
		return lookedupvalue;
	}
	
	public static  String lookupvaluefromtable1(Connection con, String table_name, String lookup_col1,String lookup_col2,
			String lookup_filter_col, String lookup_filter_value) throws SQLException {
StringBuilder query = new StringBuilder();
String lookedupvalue = "";

query.append(" SELECT " + lookup_col1 +" " + lookup_col2 + " FROM " +  table_name);
query.append(" where " + lookup_filter_col + " = ?");
PreparedStatement pstmt = con.prepareStatement(query.toString());
ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString(),lookup_filter_value);
if(rs.next()) {
	lookedupvalue = (rs.getString(1));
}
pstmt.close();
return lookedupvalue;
}
	public static byte[]  lookupvaluefromtable2(Connection con, String table_name, String lookup_col,
			String lookup_filter_col, String lookup_filter_value) throws SQLException {
StringBuilder query = new StringBuilder();
InputStream sImage;
byte[] bytearray =null;
query.append(" SELECT " + lookup_col + " FROM " +  table_name);
query.append(" where " + lookup_filter_col + " = ?");
PreparedStatement pstmt = con.prepareStatement(query.toString());
ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString(),lookup_filter_value);




if(rs.next()) {
	 bytearray = new byte[1048576];
	
	sImage = rs.getBinaryStream(1);
	
	} 
	
	//byte[] binaryStream = imageBlob.getBytes(1, (int) imageBlob.length());
	;

pstmt.close();
return bytearray;
}
	public static String lookupvaluefromtabletwofilter(Connection con, String table_name, String lookup_col,
			String lookup_filter_col, String lookup_filter_value,
			String lookup_filter_col2, String lookup_filter_value2) throws SQLException {
		StringBuilder query = new StringBuilder();
		String lookedupvalue = "";
		
		query.append(" SELECT " + lookup_col + " FROM " +  table_name);
		query.append(" where " + lookup_filter_col + " = ?");
		query.append(" AND " + lookup_filter_col2 + " = ?");
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString(),lookup_filter_value, lookup_filter_value2);
		if(rs.next()) {
			lookedupvalue = (rs.getString(1));
		}
		pstmt.close();
		return lookedupvalue;
		}
	public static String lookupvaluefromtablefilterandWhere(Connection con, String table_name, String lookup_col,
			String lookup_filter_col, String lookup_filter_value,
			String lookup_filter_where) throws SQLException {
		StringBuilder query = new StringBuilder();
		String lookedupvalue = "";
		
		query.append(" SELECT " + lookup_col + " FROM " +  table_name);
		query.append(" where " + lookup_filter_col + " = ?");
		query.append(" AND " + lookup_filter_where );
		PreparedStatement pstmt = con.prepareStatement(query.toString());
		ResultSet rs = QueryExecuter.getResultSet(pstmt, query.toString(),lookup_filter_value);
		if(rs.next()) {
			lookedupvalue = (rs.getString(1));
		}
		pstmt.close();
		return lookedupvalue;
		}
	public static boolean isExistingAssetName(Connection con,String columnName,String value,String tableName) throws SQLException {
		String query = "SELECT "+columnName+"	FROM "+tableName+"  WHERE ActiveStatusFlag='Active' and "+columnName+" = ?";	
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = getResultSet(pstmt, query, value);
		boolean result = rs.next();
		pstmt.close();
		return result;
	}	
}
