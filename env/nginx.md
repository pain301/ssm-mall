## nginx
### 安装
```sh
sudo wget <nginx-url>

# 安装依赖
sudo yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel

sudo tar -zxvf nginx-1.10.2.tar.gz
cd nginx
sudo ./configure
sudo make
sudo make install
```

### 配置
```sh
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
```

```sh
# 启动
sudo ./nginx

# 查看进程
ps -ef | grep nginx

# 平滑重启
kill -HUP <pid>

# 关闭自动索引
autoindex off;
```