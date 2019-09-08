## Tomcat
### 安装
```sh
# 下载 Tomcat
cd /developer
sudo wget <tomcat-url>
sudo tar -zxvf apache-tomcat-7.0.73.tar.gz
```

### 环境变量
```sh
# 配置环境变量
sudo vi /etc/profile
export CATALINA_HOME=/usr/apache-tomcat-7.0.73

# 使配置生效
source /etc/profile
```

### 字符集
```sh
# 配置字符集
sudo vim conf/server.xml
<Connector port="8080" ... URIEncoding="UTF-8" />

# 启动 Tomcat
sudo ./startup.sh
```