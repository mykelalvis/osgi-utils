
===== Requirements =====
This bundle requires the Microsoft JDBC Driver 4.0 for SQL Server. 

===== Installation ===== 
* Download and extract the JDBC driver from http://www.microsoft.com/download/en/details.aspx?displaylang=en&id=11774
* Encapsulate sqljdbc4.jar as a maven artifact :
	mvn install:install-file -Dfile=sqljdbc4.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0.0
* Build and install the project:
	mvn install:install
* Deploy:
	mvn -DaltDeploymentRepository=giatsi_thirdparty::default::http://giatsi.deetc.isel.pt/nexus/content/repositories/thirdparty/ package deploy	
 