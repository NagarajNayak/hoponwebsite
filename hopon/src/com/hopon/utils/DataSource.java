package com.hopon.utils;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
public class DataSource {
	private static DataSource     datasource;
	private BoneCP connectionPool;
	private DataSource() throws IOException, SQLException, PropertyVetoException {
		ResourceBundle bundle = ResourceBundle.getBundle("resource.Database");
		try {
			Class.forName(bundle.getString("driver").trim());
		} catch (Exception e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			return;
		}

		try {
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(bundle.getString("url").trim());
			config.setUsername(bundle.getString("user").trim());
			config.setPassword(bundle.getString("jdbcPassword").trim());
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config);
		} catch (Exception e) {
			LoggerSingleton.getInstance().error(e.getStackTrace()[0].getClassName()+"->"+e.getStackTrace()[0].getMethodName()+"() : "+e.getStackTrace()[0].getLineNumber()+" :: "+e.getMessage());
			return;
		}
	}

	public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
		if (datasource == null) {
			datasource = new DataSource();
			return datasource;
		} else {
			return datasource;
		}
	}

	public Connection getConnection() throws SQLException {
		return this.connectionPool.getConnection();
	}
}
