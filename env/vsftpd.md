## vsftpd
[参考](http://learning.happymmall.com/vsftpdconfig/vsftpd.conf.readme.html)
### 安装
```sh
# 查看是否默认安装
rpm -qa | grep vsftpd

sudo yum -y install vsftpd
```

### 创建 ftp 用户
```sh
cd /
sudo mkdir ftpfile
sudo useradd ftpuser -d /ftpfile -s /sbin/nologin
sudo chown -R ftpuser.ftpuser /ftpfile
sudo passwd ftpuser
```

### 配置
```sh
# 添加 ftp 用户
cd /etc/vsftpd
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
```

### 防火墙
#### 初始化
```sh
sudo iptables -P OUTPUT ACCEPT
sudo service iptables save
```

#### 防火墙配置
```sh
sudo mv iptables iptables.bak
sudo wget <iptables-url>
sudo service iptables restart
```

```sh
# vsftpd
-A INPUT -p TCP --dport 61001:62000 -j ACCEPT
-A OUTPUT -p TCP --sport 61001:62000 -j ACCEPT

-A INPUT -p TCP --dport 20 -j ACCEPT
-A OUTPUT -p TCP --sport 20 -j ACCEPT
-A INPUT -p TCP --dport 21 -j ACCEPT
-A OUTPUT -p TCP --sport 21 -j ACCEPT
```