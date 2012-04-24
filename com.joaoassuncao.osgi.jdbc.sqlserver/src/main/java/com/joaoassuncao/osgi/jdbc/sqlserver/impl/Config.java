package com.joaoassuncao.osgi.jdbc.sqlserver.impl;
import org.osgi.service.jdbc.DataSourceFactory;

import aQute.bnd.annotation.metatype.*;

@Meta.OCD(
		id=ManagedDataSourceFactory.PID,
		name="SQLServer DataSource Service Factory", 
		description="SQLServer DataSource Service Factory",
		factory=true
) 
interface Config {
	@Meta.AD(id=DataSourceFactory.JDBC_DATASOURCE_NAME, name="DataSource Name", required=true)
	String dataSourceName();
	
	@Meta.AD(id=DataSourceFactory.JDBC_DATABASE_NAME, name="Database Name", required=true)
	String databaseName();
	
	@Meta.AD(id=DataSourceFactory.JDBC_SERVER_NAME, name="Server Name", required=false)
	String serverName();
	
	@Meta.AD(id=DataSourceFactory.JDBC_PORT_NUMBER, name="Port Number", required=false)
	int portNumber();
	
	@Meta.AD(id=DataSourceFactory.JDBC_USER, name="Username", required=false)
	String user();
	
	@Meta.AD(id=DataSourceFactory.JDBC_PASSWORD, name="Password", required=false)
	String password();
		
}
