### 匿名用户
是否允许匿名用户登入
```
anonymous_enable=YES/NO（YES）
```
匿名登入是否询问密码
```
no_anon_password=YES/NO（NO）
```
匿名登入的使用者名称
```
ftp_username=ftp
```
匿名登入的目录，匿名用户的家目录不能有 777 权限
```
anon_root=/var/ftp
```
匿名登入者上传文件的权限，只有在 write_enable=YES 时才有效
```
anon_upload_enable=YES/NO（NO）
```
是否允许匿名登入者下载可阅读的档案
```
anon_world_readable_only=YES/NO（YES）
```
是否允许匿名登入者有新增目录的权限，只有在 write_enable=YES 时才有效
```
anon_mkdir_write_enable=YES/NO（NO）
```
是否允许匿名登入者更多于上传或者建立目录之外的权限，譬如删除或者重命名
```
anon_other_write_enable=YES/NO（NO）
```
是否改变匿名用户上传文件的属主
```
chown_uploads=YES/NO（NO）
```
设置匿名用户上传文件的属主名，建议不要设置为 root
```
chown_username=username
```
匿名登入者新增或上传档案时的 umask 值
```
anon_umask=077
```
若是启动这项功能，则必须提供一个档案 /etc/vsftpd/banner_emails，内容为 email address。若是使用匿名登入，则会要求输入email address，若输入的email address 在此档案内，则不允许进入。默认值为NO
```
deny_email_enable=YES/NO（NO）
```
只在 deny_email_enable=YES 时才使用到此档案
```
banned_email_file=/etc/vsftpd/banner_emails
```
是否允许登陆用户有写权限，属于全局设置
```
write_enable=YES/NO（YES）
```

### 本地用户
是否允许本地用户登入
```
local_enable=YES/NO（YES）
```
本地用户登入后被更换到定义的目录下
```
local_root=/home/username
```
是否允许登录用户有写权限，属于全局设置
```
write_enable=YES/NO（YES）
```
本地用户新增档案时的 umask 值，。默认值为 077
```
local_umask=022
```
本地用户上传档案后的档案权限
```
file_open_mode=0755
```

### 欢迎语
使用者第一次进入一个目录时，会检查该目录下是否有 .message 档案，若有则会出现此档案的内容
```
dirmessage_enable=YES/NO（YES）
```
设置目录消息文件，可将要显示的信息写入该文件
```
message_file=.message
```
当使用者登入时，显示此设定所在的档案内容
```
banner_file=/etc/vsftpd/banner
```
定义欢迎话语的字符串，banner_file 是档案的形式，ftpd_banner 是字符串的形式
```
ftpd_banner=Welcome to BOB's FTP server
```

### 目录切换
是否启用 chroot_list_file 配置项指定的用户列表文件
```
chroot_list_enable=YES/NO（NO）
```
指定用户列表文件，该文件指定可以切换到家目录上级的用户
```
chroot_list_file=/etc/vsftpd.chroot_list
```
指定用户列表文件中的用户是否允许切换到上级目录
```
chroot_local_user=YES/NO（NO）
```

/etc/vsftpd.chroot_list 中列出的用户可以切换到其他目录，未列出的用户不能切换
```
chroot_list_enable=YES
chroot_local_user=YES
```
/etc/vsftpd.chroot_list 中列出的用户不能切换到其他目录，未列出的用户可以切换
```
chroot_list_enable=YES
chroot_local_user=NO
```
所有的用户均不能切换到其他目录
```
chroot_list_enable=NO
chroot_local_user=YES
```
所有的用户均可以切换到其他目录
```
chroot_list_enable=NO
chroot_local_user=NO
```

### 数据传输模式
是否启用 ASCII 模式上传数据
```
ascii_upload_enable=YES/NO（NO）
```
是否启用 ASCII 模式下载数据
```
ascii_download_enable=YES/NO（NO）
```

### 访问控制设置
#### 控制主机访问方式
vsftpd 是否与 tcp wrapper 相结合来进行主机的访问控制。若启用，vsftpd 会检查 /etc/hosts.allow 和 /etc/hosts.deny 中的设置来决定请求连接的主机是否允许访问该 FTP 服务器
```
tcp_wrappers=YES/NO（YES）
```
若仅允许 192.168.0.1—192.168.0.254 的用户可以连接 FTP 服务器，则在/etc/hosts.allow文件中添加以下内容：
```
vsftpd:192.168.0. :allow
all:all :deny
```
#### 控制用户访问方式
通过 /etc 目录下的 vsftpd.user_list 和 ftpusers 文件实现
控制用户访问 FTP 的文件，一个用户名一行
```
userlist_file=/etc/vsftpd.user_list
```
是否启用 vsftpd.user_list 文件
```
userlist_enable=YES/NO（NO）
```
vsftpd.user_list 文件中的用户是否能访问 FTP 服务器
```
userlist_deny=YES/NO（YES）
```
/etc/vsftpd/ftpusers 文件专门用于定义不允许访问FTP服务器的用户列表

### 访问速率
匿名登入者的最大传输速度，0 表示不限制速度
```
anon_max_rate=0
```
本地用户使用的最大传输速度，0 表示不限制速度
```
local_max_rate=0
```

### 超时时间
建立 FTP 连接的超时时间
```
accept_timeout=60
```
PORT 方式下建立数据连接的超时时间
```
connect_timeout=60
```
建立 FTP 数据连接的超时时间
```
data_connection_timeout=120
```
设置多长时间不对 FTP 服务器进行任何操作，则断开该 FTP 连接
```
idle_session_timeout=300
```

### 日志文件
是否启用上传/下载日志记录
```
xferlog_enable= YES/NO（YES）
```
设置日志文件名和路径
```
xferlog_file=/var/log/vsftpd.log
```
是否启用 xferlog 标准格式
```
xferlog_std_format=YES/NO（NO）
```
是否记录所有 FTP 请求和响应
```
log_ftp_protocol=YES|NO（NO）
```

### 用户配置文件
设置用户配置文件所在的目录，用户登陆服务器后，系统就会到该目录下读取与当前用户名相同的文件，并根据文件中的配置命令，对当前用户进行更进一步的配置
```
user_config_dir=/etc/vsftpd/userconf
```

### 工作方式与端口设置
FTP 服务器建立连接所监听的端口
```
listen_port=21
```
FTP 使用 20 端口进行数据传输
```
connect_from_port_20=YES/NO
```
PORT 方式下，FTP 数据连接使用的端口
```
ftp_data_port=20
```
使用 PASV\PORT 工作模式
```
pasv_enable=YES/NO（YES）
```
PASV 工作模式下，数据连接可以使用的端口范围的最大端口，0 表示任意端口
```
pasv_max_port=0
```
PASV 工作模式下，数据连接可以使用的端口范围的最小端口，0 表示任意端口
```
pasv_min_port=0
```

### 连接相关的设置
vsftpd 服务器是否以 standalone 模式运行，推荐 standalone 模式
```
listen=YES/NO（YES）
```
vsftpd 允许的最大连接数，0 表示不受限制，只有在standalone模式运行才有效
```
max_clients=0
```
每个 IP 允许与 FTP 服务器同时建立连接的数目，0 表示不受限制，只有在 standalone 模式运行才有效
```
max_per_ip=0
```
FTP 服务器在指定的 IP 地址上侦听用户的 FTP 请求。若不设置，则对服务器绑定的所有 IP 地址进行侦听。只有在 standalone 模式运行才有效
```
listen_address=IP地址
```
每个与 FTP 服务器的连接，是否以不同的进程表现出来
```
setproctitle_enable=YES/NO（NO）
```

### 完整配置
当本地用户登入时，将被更换到定义的目录下，默认值为各用户的家目录
```
local_root=/ftpfile
```
使用匿名登入时，所登入的目录
```
anon_root=/ftpfile
```
默认是 GMT 时间，改成使用本机系统时间
```
use_localtime=YES
```
不允许匿名用户登录
```
anonymous_enable=NO
```
允许本地用户登录
```
local_enable=YES
```
本地用户可以在自己家目录中进行读写操作
```
write_enable=YES
```
本地用户新增档案时的 umask 值
```
local_umask=022
```
欢迎话语
```
dirmessage_enable=YES
```
启用上传/下载日志记录
```
xferlog_enable=YES
```
指定 FTP 使用 20 端口进行数据传输
```
connect_from_port_20=YES
```
日志文件将会写成 xferlog 的标准格式
```
xferlog_std_format=YES
```
定义欢迎话语的字符串
```
ftpd_banner=Welcome to pain FTP Server
```
指定用户列表文件中的用户是否允许切换到上级目录
```
chroot_local_user=NO
```
是否启用 chroot_list_file 配置项指定的用户列表文件
```
chroot_list_enable=YES
```
用于指定用户列表文件
```
chroot_list_file=/etc/vsftpd/chroot_list
```
设置 vsftpd 服务器以 standalone 模式运行
```
listen=YES
```
虚拟用户使用 PAM 认证方式
```
pam_service_name=vsftpd
```
启用 vsftpd.user_list 文件
```
userlist_enable=YES
```
被动模式使用端口范围最小值
```
pasv_min_port=61001
```
被动模式使用端口范围最大值
```
pasv_max_port=62000
```
使用 PASV 工作模式
```
pasv_enable=YES
```

### PORT（主动）方式
1. 客户端向服务器的 FTP 端口（默认 21）发送连接请求，服务器接受连接，建立一条命令链路
2. 当需要传送数据时，客户端在命令链路上用 PORT 命令告诉服务器打开的端口，服务器从 20 端口向客户端对应端口发送连接请求，建立一条数据链路来传送数据

### PASV（被动）方式
1. 客户端向服务器的 FTP 端口（默认是21）发送连接请求，服务器接受连接，建立一条命令链路
2. 当需要传送数据时，服务器在命令链路上用 PASV 命令告诉客户端打开了的端口，客户端向服务器对应端口发送连接请求，建立一条数据链路来传送数据