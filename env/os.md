## 源配置
http://mirrors.aliyun.com/
### 备份
```sh
sudo mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bak
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

## 用户管理
### 添加用户
```sh
useradd -d /usr/pain -m pain
passwd pain
```
### 添加 sudo 权限
```sh
sudo vim /etc/sudoers

pain ALL=(ALL) ALL
```