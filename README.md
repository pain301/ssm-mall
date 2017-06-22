SSM project demo

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

# 下载 JDK
cd /
sudo mkdir developer
cd developer
sudo mkdir setup
cd setup
sudo wget <jdk-url>

# 安装，默认到 /usr/java/ 目录下
chmod 777 jdk-linux.rpm
sudo rpm -ivh jdk-linux.rpm

# 配置环境变量
vi /etc/profile
export JAVA_HOME=/usr/java/jdk1.7.0_80
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
```
### Tomcat
```sh
# 下载 Tomcat
cd /developer
sudo wget <tomcat-url>
sudo tar -zxvf apache-tomcat-7.jar.gz

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
sudo tar -zxvf apache-maven


```
### Vsftpd
```sh
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
ftpuser

# 检查 SELINUX 为 disable
sudo vim /etc/selinux/config
SELINUX=disable

sudo setsebool -P ftp_home_dir 1

# ftp 配置文件
mv vsftpd.conf vsftpd.conf.bak
sudo wget <vsftpd.conf url>
```
### nginx
```sh
sudo wget <nginx-url>

# 安装 nginx 依赖
sudo yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel

tar -zxvf linux-nginx.tar.gz
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

# 启动
sudo ./nginx
```
### mysql
```sh
# 检查是否安装
sudo rpm -qa | grep mysql-server

sudo yum -y install mysql-server

# 配置文件
sudo vim /etc/my.cnf

# 在 mysqld 节点下添加
character-set-server=utf8
default-character-set=utf8

# 配置 mysql 自启动
sudo chkconfig mysqld on
# 查看
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
insert into mysql.user(host,user,password) values ("localhost","mall",password("mallpassword"));

create database `mall` default character set utf8 COLLATE utf8_general_ci;
flush privileges;
grant all privileges on mall.* to mall@localhost identified by 'mallpassword';

# 导入 sql 文件
use mall;
source /developer/mall.sql;
```
### git
```sh
sudo wget <get-url>

# 安装 git 依赖
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
```

## 自动发布
```sh

```