## 反射
```java
ClassLoader loader = Thread.currentThread().getContextClassLoader();
Class clazz = loader.loadClass("com.pain.model.Car");
Constructor cons = clazz.getDeclaredConstructor((Class[])null);
Car car = (Car)cons.newInstance();
```
### 类装载器
#### 工作机制
- 装载：查找和导入 Class 文件
- 链接
  - 校验：检查载入 Class 文件数据的正确性
  - 准备：为类静态变量分配存储空间
  - 解析：符号引用转换为直接引用
- 初始化：类的静态变量、静态代码块执行初始化工作

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

## BeanFactory
```java
DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
reader.loadBeanDefinitions(res);
Car car = factory.getBean("car", Car.class);
```
### Bean 生命周期
1. 容器级生命周期接口方法：InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation();
2. Bean 自身方法：Bean 实例化
3. 容器级生命周期接口方法：InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation();
4. 容器级生命周期接口方法：InstantiationAwareBeanPostProcessor#postProcessPropertyValues();
5. Bean 自身方法：Bean 设置属性值
6. Bean 级生命周期接口方法：BeanNameAware#setBeanName();
7. Bean 级生命周期接口方法：BeanFactoryAware#setBeanFactory();
8. 容器级生命周期接口方法：BeanPostProcessor#postProcessBeforeInitialization();
9. Bean 级生命周期接口方法：InitializingBean#afterPropertiesSet();
10. Bean 自身方法：init-method 属性配置的初始化方法调用
11. 容器级生命周期接口方法：BeanPostProcessor#postProcessAfterInitialization();
12. Bean 级生命周期接口方法：DisposableBean#destroy();
13. Bean 自身方法：destroy-method 属性配置的销毁方法调用
InstantiationAwareBeanPostProcessor 为 BeanPostProcessor 接口的子接口，Spring 提供适配器类
InstantiationAwareBeanPostProcessorAdapter 去扩展
### ApplicationContext
BeanFactory 初始化容器时并未实例化 Bean, 直到第一次访问某个 Bean 时才实例化，
而 ApplicationContext 则在初始化应用上下文使就实例化所有单实例的 Bean
ApplicationContext 会利用 java 反射机制识别出配置文件中的 BeanPostProcessor、InstantiationAwareBeanPostProcessor 和
BeanFactoryPostProcessor 注册到应用上下文中，而 BeanFactory 需要调用 addBeanPostProcessor 方法进行注册
```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("com/pain/context/beans.xml");
```
### Bean 后处理器
如果在配置文件中声明了工厂后处理器接口 BeanFactoryPostProcessor 的实现类，则应用上下文在装载配置文件后、初始化
Bean 实例之前将调用这些 BeanFactoryPostProcessor 对配置信息进行加工处理