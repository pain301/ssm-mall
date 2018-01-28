## Bean
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

### Bean
#### BeanFactory
```java
DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
reader.loadBeanDefinitions(res);
Car car = factory.getBean("car", Car.class);
```

#### ApplicationContext
1. BeanFactory 初始化容器时并未实例化 Bean, 直到第一次访问某个 Bean 时才实例化，而 ApplicationContext 则在初始化应用上下文时就实例化所有单实例的 Bean
2. ApplicationContext 会利用 java 反射机制识别出配置文件中的 BeanPostProcessor、InstantiationAwareBeanPostProcessor 和
BeanFactoryPostProcessor 注册到应用上下文中，而 BeanFactory 需要调用 addBeanPostProcessor 方法进行注册
```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("com/pain/context/beans.xml");
```

#### WebApplicationContext
`WebApplicationContext` 需要 `ServletContext` 实例，必须在拥有 `Web` 容器的前提下才能完成启动工作

### 后处理器
#### BeanFactoryPostProcessor
如果在配置文件中声明了工厂后处理器接口 BeanFactoryPostProcessor 的实现类，则应用上下文在装载配置文件后、初始化
Bean 实例之前将调用这些 BeanFactoryPostProcessor 对配置信息进行加工处理

#### InstantiationAwareBeanPostProcessor
`InstantiationAwareBeanPostProcessor` 为 `BeanPostProcessor` 接口的子接口，`Spring` 提供适配器类
`InstantiationAwareBeanPostProcessorAdapter` 去扩展

### Bean 依赖
`sysInit` 为 `manager` 的前置依赖，`sysInit` 会先于 `manager` 创建
```xml
<bean id="manager" class="com.pain.CacheManager" depends-on="sysInit" />
<bean id="sysInit" class="com.pain.sysInit" />
```

### 组合配置文件
```xml
<import resource="classpath:com/pain/beans.xml" />
```