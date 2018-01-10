call mvn clean package -DskipTests

ssh root@10.10.113.115 "sh /usr/local/kill_tomcat.sh"

ssh root@10.10.113.115 "sh /usr/local/kill_tomcat.sh"

ssh root@10.10.113.115 "sh /usr/local/rmTomcatWar.sh"

ssh root@10.10.113.115 "sh /usr/local/kill_tomcat.sh"

scp.exe icaopan-rebuld-manage-web/target/icaopan-rebuld-manage-web-0.0.1-SNAPSHOT.war root@10.10.113.115:/usr/local/tomcat7/webapps/lever2-manage.war

scp.exe icaopan-rebuld-web/target/icaopan-rebuld-web-0.0.1-SNAPSHOT.war root@10.10.113.115:/usr/local/tomcat7-web/webapps/lever2.war

ssh root@10.10.113.115 "sh /usr/local/restart_tomcat_trading.sh"

ssh root@10.10.113.115 "ps -ef | grep tomcat"


