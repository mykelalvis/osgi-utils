/**
 * 
 */
package com.joaoassuncao.osgi.jdbc.postgresql.impl;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * @author jassuncao
 *
 */
public class PostgresqlDataSourceFactoryImpl implements DataSourceFactory {
	
	public DataSource createDataSource(Properties props) throws SQLException {
		String url = props.getProperty(DataSourceFactory.JDBC_URL);
		if(url!=null && url.length()>0) 
			throw new IllegalArgumentException("DataSourceFactory.JDBC_URL is not supported");
		String databaseName = props.getProperty(DataSourceFactory.JDBC_DATABASE_NAME);
		String password = props.getProperty(DataSourceFactory.JDBC_PASSWORD);
		String port = props.getProperty(DataSourceFactory.JDBC_PORT_NUMBER);
		String serverName = props.getProperty(DataSourceFactory.JDBC_SERVER_NAME);
		String user = props.getProperty(DataSourceFactory.JDBC_USER);
		PGSimpleDataSource ds = new PGSimpleDataSource();	
		if(databaseName!=null)
			ds.setDatabaseName(databaseName);
		if(port!=null){
			int portNumber = Integer.parseInt(port);
			ds.setPortNumber(portNumber);
		}		
		ds.setServerName(serverName);
		ds.setUser(user);
		ds.setPassword(password);
		return ds;		
	}

	public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
		throw new SQLException("ConnectionPoolDataSource implementation is not available for this service");
	}

	public XADataSource createXADataSource(Properties props) throws SQLException {
		throw new SQLException("XADataSource implementation is not available for this service");
	}

	public Driver createDriver(Properties props) throws SQLException {		
		return new org.postgresql.Driver();
	}

}
