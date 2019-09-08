## JDK
### 安装 jdk
```sh
# 查看是否默认安装
rpm -qa | grep jdk

# 清除默认安装的 jdk
sudo yum remove <查询到的结果>

# 下载 JDK
cd /
sudo mkdir developer
cd developer
sudo mkdir setup
cd setup
sudo wget <jdk-url>

# 默认安装到 /usr/java/ 目录下
sudo chmod 777 jdk-linux.rpm
sudo rpm -ivh jdk-linux.rpm
```

### 添加环境变量
```sh
sudo vi /etc/profile
export JAVA_HOME=/usr/java/jdk1.7.0_80
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

# 添加 $JAVA_HOME/bin
export PATH=$PATH:$JAVA_HOME/bin

# 使配置生效
source /etc/profile
```