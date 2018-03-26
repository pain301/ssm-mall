cd /developer/git-repository/mall

git checkout mall-v1.0

git fetch
git pull

mvn clean package -Dmaven.test.skip=true

rm /developer/apache-tomcat-7.0.73/webapps/ROOT.war
cp /developer/git-repository/mall/target/mall.war  /developer/apache-tomcat-7.0.73/webapps/ROOT.war
rm -rf /developer/apache-tomcat-7.0.73/webapps/ROOT

/developer/apache-tomcat-7.0.73/bin/shutdown.sh

echo "================sleep 10s========================="
for i in {1..10}
do
  echo $i"s"
  sleep 1s
done

/developer/apache-tomcat-7.0.73/bin/startup.sh