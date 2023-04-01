cd
javac -d /home/megane/classes /home/megane/GitHub/FIRST_FRAMEWORK/framework/*.java
sudo cp -r /home/megane/classes/dataObject /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/WEB-INF/classes/
jar cf framework.jar -C /home/megane/classes/ .
sudo mv framework.jar /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/WEB-INF/lib
#cp -r /home/megane/FRAMEWORK/test_framework /home/megane/apache-tomcat-8.5.81/webapps/
jar cf launch_framework.war -C /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/ . 
sudo mv /home/megane/launch_framework.war /home/megane/apache-tomcat-8.5.81/webapps/
#cp -r launch_framework.war 
sudo sh /home/megane/apache-tomcat-8.5.81/bin/shutdown.sh
sudo sh /home/megane/apache-tomcat-8.5.81/bin/startup.sh
# javac -cp /home/megane/FRAMEWORK/test_framework/WEB-INF/lib/framework.jar -d . *.java
