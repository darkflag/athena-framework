
Set Up and Run the Application:

A: Database Setup - if you have access to a MySQL, DB2 or Oracle database:
A1. Create a new database instance, give it a name like 'employeedir';
A2. Config the database in WebContent/WEB-INF/eo-config.xml
     <database-type>MySQL</database-type> 	<!-- MySQL, DB2 or Oracle -->
     <host>localhost</host> 				<!-- the actual host if not local -->
     <username>root</username> 				<!-- the actual username -->
     <password></password> 					<!-- the actual password -->
     <db>employeedir</db>					<!-- the actual db name -->
	 <property name="java-source-local-dir" value="C:\EmployeeDir\src"/> <!-- absolute path -->

A: Database Setup - use the embedded database Derby if you do not have access to any MySQL, DB2 or Oracle database:
A1. Config the database in WebContent/WEB-INF/eo-config.xml
     <database-type>Derby</database-type>
     <host>localhost</host>
     <username>APP</username> 				<!-- must be APP -->
     <password></password> 					<!-- must be empty -->
     <db>C:\EmployeeDir\db\derby_employeedir</db>		<!-- absolute path -->
	 <property name="java-source-local-dir" value="C:\EmployeeDir\src"/> <!-- absolute path -->

B: Metadata Initialization - create tables to hold metadata
B1. Open Athena Console; File -> Open EO Config -> Browse -> WebContent/WEB-INF/eo-config.xml;
B2. Click 'Database Setup' button on the navigation panel, then click 'Initialize metadata' button to initialize metadata;

C. Create Entities - by importing entities directly
C1. Click 'Metadata I/E' button on the navigation panel, browse the db/employeedir-metadata.xml file and click 'Import' to import entities.

D. Run the web application using any web container (If you are using Derby, please close Athena Console first.).


Customize the Application:

1. Create/Modify Entities - create entities for the application: Metadata Workbench -> Launch Metadata Workbench;
2. Generate Source - press 'Generate classes' button under 'Code Generation' in Athena Console;
3. Modify JSP code and run.
