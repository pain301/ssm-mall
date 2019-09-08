## 防火墙
### 初始化
```sh
cd /etc/sysconfig/

sudo iptables -P OUTPUT ACCEPT
sudo service iptables save
sudo mv iptables iptables.bak

sudo wget <iptables-url>
```

### 配置
```sh
# 关闭 3306 8080 5005 端口
```

### 常用命令
```sh
# 重启
sudo service iptables restart

# 重启 vsftpd
sudo service vsftpd restart
# 启动 vsftpd
sudo service vsftpd start
# 停止 vsftpd
sudo service vsftpd stop
```