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

## 域名
https://wanwang.aliyun.com/