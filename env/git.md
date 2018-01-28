## git
### 安装
```sh
sudo wget <get-url>

# 安装依赖
sudo yum -y install zlib-devel openssl-devel cpio expat-devel gettext-devel curl-devel perl-ExtUtils-CBuilder perl-ExtUtils-MakeMaker
# 解压安装 git
sudo tar -zxvf git.tar.gz
cd git/
sudo make prefix=/usr/local/git all
sudo make prefix=/usr/local/git install
```

### 配置
```sh
git config --global user.name "pain"
git config --global user.email "pain@163.com"
git config --global core.autocrlf false
git config --global core.quotepath off
git config --global gui.encoding utf-8

ssh-keygen -t rsa -C "email"

ssh-add ~/.ssh/id_rsa

# 若上面的命令执行出错
eval `ssh-agent`

# 添加公钥
```