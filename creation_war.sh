./run.sh
cd
jar cf launch_framework.war -C /home/megane/temporary/ . 
sudo mv /home/megane/launch_framework.war /home/megane/apache-tomcat-8.5.81/webapps/
# sudo mv /home/megane/temporary /home/megane/apache-tomcat-8.5.81/webapps/
sudo sh /home/megane/apache-tomcat-8.5.81/bin/shutdown.sh
sudo sh /home/megane/apache-tomcat-8.5.81/bin/startup.sh







# CHEMIN_AKO="/home/megane"
# cd
# javac -parameters -d /home/megane/classes /home/megane/GitHub/FIRST_FRAMEWORK/framework/*.java
# sudo cp -r /home/megane/classes/dataObject /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/WEB-INF/classes/
# jar cf framework.jar -C /home/megane/classes/ .
# sudo mv framework.jar /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/WEB-INF/lib

# # ./script.sh
# cd
# sudo rm -rf $CHEMIN_AKO/temporary
# sudo rm -rf /home/megane/temporary
# mkdir /home/megane/temporary
# mkdir /home/megane/temporary/WEB-INF
# mkdir /home/megane/temporary/WEB-INF/classes
# mkdir /home/megane/temporary/WEB-INF/lib
# mkdir /home/megane/temporary/META-INF
# sudo cp -r /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/META-INF/* /home/megane/temporary/META-INF/
# sudo cp -r /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/WEB-INF/classes/* /home/megane/temporary/WEB-INF/classes/
# sudo cp -r /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/WEB-INF/lib/* /home/megane/temporary/WEB-INF/lib/
# sudo cp /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/WEB-INF/web.xml /home/megane/temporary/WEB-INF/
# sudo cp /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/*.jsp /home/megane/temporary/


# # creation_war.sh
# cd
# jar cf launch_framework.war -C /home/megane/temporary/ . 
# sudo mv /home/megane/launch_framework.war /home/megane/apache-tomcat-8.5.81/webapps/
# # sudo mv /home/megane/temporary /home/megane/apache-tomcat-8.5.81/webapps/
# sudo sh /home/megane/apache-tomcat-8.5.81/bin/shutdown.sh
# sudo sh /home/megane/apache-tomcat-8.5.81/bin/startup.sh



# sudo cp -r /home/megane/temporary /home/megane/apache-tomcat-8.5.81/webapps/
# sudo cp -r /home/megane/GitHub/FIRST_FRAMEWORK/test_framework /home/megane/apache-tomcat-8.5.81/webapps/
# cp -r /home/megane/temporary /home/megane/apache-tomcat-8.5.81/webapps/
#cp -r /home/megane/FRAMEWORK/test_framework /home/megane/apache-tomcat-8.5.81/webapps/
# jar cf launch_framework.war -C /home/megane/GitHub/FIRST_FRAMEWORK/test_framework/ . 
# sudo mv /home/megane/launch_framework.war /home/megane/apache-tomcat-8.5.81/webapps/
#cp -r launch_framework.war 
# sudo sh /home/megane/apache-tomcat-8.5.81/bin/shutdown.sh
# sudo sh /home/megane/apache-tomcat-8.5.81/bin/startup.sh
# javac -cp /home/megane/FRAMEWORK/test_framework/WEB-INF/lib/framework.jar -d . *.java


