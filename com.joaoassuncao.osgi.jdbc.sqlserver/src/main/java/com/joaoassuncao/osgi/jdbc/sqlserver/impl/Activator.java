/**
 * 
 */
package com.joaoassuncao.osgi.jdbc.sqlserver.impl;

import java.sql.Driver;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.jdbc.DataSourceFactory;

/**
 * @author jassuncao
 * 
 */
public class Activator implements BundleActivator {

	private SqlServerDataSourceFactoryImpl factoryImpl = new SqlServerDataSourceFactoryImpl();
	private ManagedDataSourceFactory managedDataSourceFactory;

	public void start(BundleContext context) throws Exception {
		managedDataSourceFactory = new ManagedDataSourceFactory(context, factoryImpl);
		Driver driver = factoryImpl.createDriver(null);
		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, driver.getClass().getName());
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, driver.getMajorVersion() + "." + driver.getMinorVersion());
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, "Microsoft SqlServer driver");
		context.registerService(DataSourceFactory.class.getName(), factoryImpl, props);

		props = new Hashtable<String, String>();
		props.put(Constants.SERVICE_PID, managedDataSourceFactory.getName());
		context.registerService(ManagedServiceFactory.class.getName(), managedDataSourceFactory, props);
	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
	}

}
