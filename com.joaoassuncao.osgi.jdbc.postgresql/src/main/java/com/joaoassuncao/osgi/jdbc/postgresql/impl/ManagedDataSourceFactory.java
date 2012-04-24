/**
 * 
 */
package com.joaoassuncao.osgi.jdbc.postgresql.impl;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.jdbc.DataSourceFactory;

/**
 * @author jassuncao
 *
 */
public class ManagedDataSourceFactory implements ManagedServiceFactory {

	public static String PID = "com.joaoassuncao.osgi.jdbc.postgresql";
	private HashMap<String, DataSourceEntry> dataSources = new HashMap<String, DataSourceEntry>();
	private BundleContext bundleContext;
	private DataSourceFactory datasourceFactory; 
	
	ManagedDataSourceFactory(BundleContext context, DataSourceFactory datasourceFactory) {
		this.bundleContext = context;
		this.datasourceFactory = datasourceFactory;
	}
	
	public String getName() {
		return PID;
	}

	@SuppressWarnings("rawtypes")
	public void updated(String pid, Dictionary properties) throws ConfigurationException {
		DataSourceEntry dsEntry = dataSources.get(pid);
		if(dsEntry!=null)
		{
			dsEntry.getServiceRegistration().unregister();
			dataSources.remove(pid);
		}		
						
		try {
		
			Properties dsProperties = filterDataSourceProperties(properties);
			DataSource ds = datasourceFactory.createDataSource(dsProperties);			
			
			Dictionary<String, String> registrationProperties = new Hashtable<String, String>();
			String databaseName = (String) properties.get(DataSourceFactory.JDBC_DATABASE_NAME);
			String datasourceName = (String) properties.get(DataSourceFactory.JDBC_DATASOURCE_NAME);
			if(datasourceName!=null)
				registrationProperties.put(DataSourceFactory.JDBC_DATASOURCE_NAME, datasourceName);
			registrationProperties.put(DataSourceFactory.JDBC_SERVER_NAME, databaseName);
			ServiceRegistration serviceRegistration = bundleContext.registerService(
					DataSource.class.getName(), ds, registrationProperties);
			dsEntry = new DataSourceEntry(ds, serviceRegistration);
			dataSources.put(pid, dsEntry);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	@SuppressWarnings("rawtypes")
	private static Properties filterDataSourceProperties(Dictionary properties){
		Properties dsProperties = new Properties();
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_DATABASE_NAME);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_DATASOURCE_NAME);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_DESCRIPTION);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_INITIAL_POOL_SIZE);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_MAX_IDLE_TIME);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_MAX_POOL_SIZE);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_MAX_STATEMENTS);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_MIN_POOL_SIZE);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_NETWORK_PROTOCOL);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_PASSWORD);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_PORT_NUMBER);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_PROPERTY_CYCLE);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_ROLE_NAME);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_SERVER_NAME);						
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_URL);
		copyProperty(properties, dsProperties, DataSourceFactory.JDBC_USER);		
		return dsProperties;
	}
	
	@SuppressWarnings("rawtypes")
	private static void copyProperty(Dictionary source, Properties target, String propertyName){
		Object value = source.get(propertyName);
		if(value!=null)
			target.put(propertyName, value.toString());
	}

	public void deleted(String pid) {
		DataSourceEntry dsEntry = dataSources.get(pid);
		if(dsEntry!=null){
			dsEntry.getServiceRegistration().unregister();
		}
		dataSources.remove(pid);									
	}
	
	private static class DataSourceEntry{
		private final DataSource dataSource;
		private final ServiceRegistration serviceRegistration;
		
		public DataSourceEntry(DataSource dataSource, ServiceRegistration serviceRegistration) {
			this.dataSource = dataSource;
			this.serviceRegistration = serviceRegistration;
		}
		
		public DataSource getDataSource() {
			return dataSource;
		}
		
		public ServiceRegistration getServiceRegistration() {
			return serviceRegistration;
		}		
	}

}
