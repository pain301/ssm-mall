SSM project demo

##　IDEA
### 配置 JDK
configure => ProjectDefaults => Project Structure
configure => ProjectDefaults => Setting
configure => setting
### 创建 WEB 项目
选择 web-app 模板创建
Run/Debug configurations 配置本地 Tomcat 服务器，添加 Artifact

## Repo 初始化
### 添加 .gitignore
### 仓库初始化
```sh
git init
git status
git add .
git commit
```
### 添加到远程库
```sh
git remote add origin {repo_url}
git branch
git push -u origin master

# 从远程仓库拉取代码
git pull
# 强制推送到远程仓库
git push -u -f origin master
# 查看远程仓库分支
git branch -r
```
### 分支开发
```sh
git checkout -b v1.0 origin/master

# v1.0 分支推送到远程仓库
git push origin HEAD -u
```

## 项目结构
### controller
控制层
### service
服务层
### mapper
数据映射层
### vo
视图对象
### pojo
持久化对象
### utils
工具
### common
常量
### 配置资源
#### mappers
SQL 语句
#### 数据源
#### mapper xml 生成器
#### 日志配置文件
#### context
#### mvc
#### 公共 properties


http://mirrors.aliyun.com/
## 源配置
### 备份
```sh
sudo mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bakcup
```

### 下载
```sh
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/CentOS-6.repo
```

### 生成缓存
```sh
yum makecache
```

### 参考
[ali 镜像](http://mirrors.aliyun.com)
[ali 镜像帮助](mirrors.aliyun.com/help/centos)

## 用户
### 添加用户
```sh
useradd -d /usr/pain -m pain
passwd pain
```
### 新用户权限
```sh
sudo vim /etc/sudoers

pain ALL=(ALL) ALL
```

## 环境搭建
### JDK
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

# 安装，默认到 /usr/java/ 目录下
sudo chmod 777 jdk-linux.rpm
sudo rpm -ivh jdk-linux.rpm

# 配置环境变量
sudo vi /etc/profile
export JAVA_HOME=/usr/java/jdk1.7.0_80
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

# 添加 $JAVA_HOME/bin
export PATH=$PATH:$JAVA_HOME/bin


# 使配置生效
source /etc/profile
```

### Tomcat
```sh
# 下载 Tomcat
cd /developer
sudo wget <tomcat-url>
sudo tar -zxvf apache-tomcat-7.0.73.tar.gz

# 配置环境变量
sudo vi /etc/profile
export CATALINA_HOME=/usr/apache-tomcat-7.0.73

# 使配置生效
source /etc/profile

# 配置字符集
sudo vim conf/server.xml
<Connector port="8080" ... URIEncoding="UTF-8" />

# 启动 Tomcat
sudo ./startup.sh
```

### Maven
```sh
# 下载 Maven
sudo wget <Maven-url>
sudo tar -zxvf apache-maven-3.0.5-bin.tar.gz

# 配置环境变量
sudo vim /etc/profile
export MAVEN_HOME=/usr/apache-maven-3.0.5
export PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin

# 使配置生效
source /etc/profile
```

### Vsftpd
[参考](http://learning.happymmall.com/vsftpdconfig/vsftpd.conf.readme.html)
```sh
# 查看是否默认安装
rpm -qa | grep vsftpd

sudo yum -y install vsftpd

# 创建 ftp 用户
cd /
sudo mkdir product
cd product/
sudo mkdir ftpfile
useradd ftpuser -d /product/ftpfile/ -s /sbin/nologin
sudo chown -R ftpuser.ftpuser ./ftpfile/
sudo passwd ftpuser

# 添加 ftp 用户
cd /etc/vsftpd/
sudo vim chroot_list
# 添加 ftp 用户
ftpuser

# 检查 SELINUX 为 disable
sudo vim /etc/selinux/config
SELINUX=disabled

sudo setenforce 0

# 若遇到 550 错误执行
sudo setsebool -P ftp_home_dir 1

# ftp 配置文件
# ftp 配置文件默认在 /etc/vsftpd/vsftpd.conf
mv vsftpd.conf vsftpd.conf.bak
sudo wget <vsftpd.conf url>


# 重启 linux
```

### nginx
```sh
sudo wget <nginx-url>

# 安装 nginx 依赖
sudo yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel

sudo tar -zxvf nginx-1.10.2.tar.gz
cd nginx/
sudo ./configure
sudo make
sudo make install

# 配置文件
cd /usr/local/nginx/
cd /conf
sudo vim nginx.conf

# 在 HTTPS 节点上方
include vhost/*.conf;

sudo mkdir vhost
cd vhost

# host 文件修改
sudo vim /etc/hosts
# 添加对应域名及 ip
<ip> www.pain.com

# 启动
sudo ./nginx

# 查看进程
ps -ef | grep nginx
```

### mysql
```sh
# 检查是否安装
sudo rpm -qa | grep mysql-server

sudo yum -y install mysql-server

# 字符集配置，在 mysqld 节点下添加
sudo vim /etc/my.cnf
character-set-server=utf8
default-character-set=utf8

# 配置 mysql 自启动
sudo chkconfig mysqld on
# 查看 2-5 为 on
sudo chkconfig --list mysqld

# 启动 mysql
sudo service mysqld restart

# 登录 mysql
mysql -u root
select user,host,password from mysql.user;
# 为 root 重新设置密码
set password for root@localhost = password('rootpassword');
set password for root@127.0.0.1 = password('rootpassword');
set password for root@<machine name> = password('rootpassword');
# 删除匿名用户
delete from mysql.user where user='';
# 刷新
flush privileges;

insert into mysql.user(host,user,password) values ("localhost", "mall", password("mallpassword"));
CREATE DATABASE `mall` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
grant all privileges on mall.* to mall@localhost identified by 'mallpassword';
flush privileges;

# 开通外网所有权限
grant all privileges on mall.* to mall@'%' identified by 'mallpassword';
# 开通部分权限
grant select,insert,update on mall.* to mall'192.11.11.11' identified by 'mallpassword';
flush privileges;

# 导入 sql 文件
use mall;
source /developer/mall.sql;
```

### git
```sh
sudo wget <get-url>

# 安装 git 依赖
sudo yum -y install zlib-devel openssl-devel cpio expat-devel gettext-devel curl-devel perl-ExtUtils-CBuilder perl-ExtUtils-MakeMaker
# 解压安装 git
sudo tar -zxvf git.tar.gz
cd git/
sudo make prefix=/usr/local/git all
sudo make prefix=/usr/local/git install

git config --global user.name "pain"
git config --global user.email "pain@163.com"
git config --global core.autocrlf false
git config --global core.quotepath off
git config --global gui.encoding utf-8

ssh-keygen -t rsa -C "email"
eval `ssh-agent`
ssh-add ~/.ssh/id_rsa

# 添加公钥
```

### 防火墙
```sh
cd /etc/sysconfig/

sudo iptables -P OUTPUT ACCEPT
sudo service iptables save
sudo mv iptables iptables.bak

sudo wget <iptables-url>
# 关闭 3306 8080 5005 端口

# 重启
sudo service iptables restart

# 重启 vsftpd
sudo service vsftpd restart
# 启动 vsftpd
sudo service vsftpd start
# 停止 vsftpd
sudo service vsftpd stop
```

## 自动发布
```sh

```