CHEMIN_AKO="/home/megane"
CHEMIN_TEST_FRAMEWORK="/home/megane/GitHub/FIRST_FRAMEWORK/test_framework"
CHEMIN_FRAMEWORK="/home/megane/GitHub/FIRST_FRAMEWORK/framework"

cd
javac -parameters -d $CHEMIN_AKO/classes $CHEMIN_FRAMEWORK/*.java
sudo cp -r /home/megane/classes/dataObject $CHEMIN_TEST_FRAMEWORK/WEB-INF/classes/
jar cf framework.jar -C $CHEMIN_AKO/classes/ .
sudo mv framework.jar $CHEMIN_TEST_FRAMEWORK/WEB-INF/lib


# ./script.sh
cd
sudo rm -rf $CHEMIN_AKO/temporary
sudo rm -rf $CHEMIN_AKO/temporary
mkdir $CHEMIN_AKO/temporary
mkdir $CHEMIN_AKO/temporary/WEB-INF
mkdir $CHEMIN_AKO/temporary/WEB-INF/classes
mkdir $CHEMIN_AKO/temporary/WEB-INF/lib
mkdir $CHEMIN_AKO/temporary/META-INF
sudo cp -r $CHEMIN_TEST_FRAMEWORK/META-INF/* $CHEMIN_AKO/temporary/META-INF/
sudo cp -r $CHEMIN_TEST_FRAMEWORK/WEB-INF/classes/* $CHEMIN_AKO/temporary/WEB-INF/classes/
sudo cp -r $CHEMIN_TEST_FRAMEWORK/WEB-INF/lib/* $CHEMIN_AKO/temporary/WEB-INF/lib/
sudo cp $CHEMIN_TEST_FRAMEWORK/WEB-INF/web.xml $CHEMIN_AKO/temporary/WEB-INF/
sudo cp $CHEMIN_TEST_FRAMEWORK/*.jsp $CHEMIN_AKO/temporary/



# creation_war.sh
cd
jar cf launch_framework.war -C $CHEMIN_AKO/temporary/ . 
sudo mv $CHEMIN_AKO/launch_framework.war $CHEMIN_AKO/apache-tomcat-8.5.81/webapps/
# sudo mv $CHEMIN_AKO/temporary $CHEMIN_AKO/apache-tomcat-8.5.81/webapps/
sudo sh $CHEMIN_AKO/apache-tomcat-8.5.81/bin/shutdown.sh
sudo sh $CHEMIN_AKO/apache-tomcat-8.5.81/bin/startup.sh


