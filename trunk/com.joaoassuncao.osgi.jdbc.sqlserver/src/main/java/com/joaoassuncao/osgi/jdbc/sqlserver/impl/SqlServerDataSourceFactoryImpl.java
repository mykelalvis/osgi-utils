/**
 * 
 */
package com.joaoassuncao.osgi.jdbc.sqlserver.impl;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import com.microsoft.sqlserver.jdbc.*;

/**
 * @author jassuncao
 *
 */
public class SqlServerDataSourceFactoryImpl implements DataSourceFactory {

	public DataSource createDataSource(Properties props) throws SQLException {				
		SQLServerDataSource ds = new SQLServerDataSource();	
		configureDataSource(ds, props);
		return ds;		
	}

	public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
		SQLServerConnectionPoolDataSource ds = new SQLServerConnectionPoolDataSource();	
		configureDataSource(ds,props);
		return ds;		
	}

	public XADataSource createXADataSource(Properties props) throws SQLException {		
		SQLServerXADataSource ds = new SQLServerXADataSource();
		configureDataSource(ds, props);
		return ds;		
	}

	public Driver createDriver(Properties props) throws SQLException {
		//SQLServerDriver driver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();		
		return new com.microsoft.sqlserver.jdbc.SQLServerDriver();
	}
	
	private void configureDataSource(SQLServerDataSource ds, Properties props) throws SQLException{
		String url = props.getProperty(DataSourceFactory.JDBC_URL);
		if(url!=null && url.length()>0) 
			throw new IllegalArgumentException("DataSourceFactory.JDBC_URL is not supported");
		String databaseName = props.getProperty(DataSourceFactory.JDBC_DATABASE_NAME);
		String password = props.getProperty(DataSourceFactory.JDBC_PASSWORD);
		String port = props.getProperty(DataSourceFactory.JDBC_PORT_NUMBER);
		String serverName = props.getProperty(DataSourceFactory.JDBC_SERVER_NAME);
		String user = props.getProperty(DataSourceFactory.JDBC_USER);
		String description = props.getProperty(DataSourceFactory.JDBC_DESCRIPTION);
		
		ds.setServerName(serverName);
		ds.setUser(user);
		ds.setPassword(password);
		
		if(databaseName!=null) {
			ds.setDatabaseName(databaseName);
		}			
		if(port!=null){
			int portNumber = Integer.parseInt(port);
			ds.setPortNumber(portNumber);
		}				
		if(description!=null) {
			ds.setDescription(description);
		}
	}

}
