# ExamManagementSystem

Installation/Deployment instructions:

1. Go to project folder, (connect to Mysql database before run the script) run SQL script (Mysql) "exammanagesys_datascript_team3ft_final.sql" in datascript folder;
2. Go to Glassfish Admin Console, create one JDBC Connection Pool named "ExamSys", 
   then create one JDBC Resources named "ExamSysEJava" to map the JDBC Connection Pool "ExamSys" which just created.
3. Go to Glassfish Admin Console, Configurations, server-config, Security, Realms, create JDBC Realms named "JDBCRealms",
   fill with the form as follow, JAAS Context: jdbcRealm, JNDI: ExamSysEJava, User Table: USER, User Name Column: USER_ID,
   Password Column: PASSWORD, Group Table: GROUP_USER, Group Name Column: GROUP_ID, 
   Password Encryption Algorithm: digestrealm-password-enc-algorithm, Digest Algorithm: none; then just let other field empty.
4. Go back to project folder, make sure in persistence.xml, Data Source: ExamSysEJava, Table Generation Strategy: none, 
   'Include All Entity Classes in "ExamManagementSystem_Team3FT_final" Module' checked.
5. Go to project folder, Server Resources folder, sun-resources.xml, change User and Password to your database user name and password.