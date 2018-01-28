## 资源
### 资源访问
- FileSystemResource: 文件系统绝对路径访问
- ClassPathResource: 类路径方式访问
- ServletContextResource: 相对 Web 应用根目录的方式进行访问

### 资源加载
根据资源地址前缀识别不同的资源类型并加载
```java
(new DefaultResourceLoader()).getResource("classpath:/conf/sys.properties").getInputStream();
```