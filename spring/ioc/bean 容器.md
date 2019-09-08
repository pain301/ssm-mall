## 容器
### AbstractApplicationContext
`AbstractApplicationContext` 是 `ApplicationContext` 的抽象实现类，该抽象实现类的 `refresh()` 方法定义了 Spring 容器在加载配置文件后的处理过程

#### refresh 流程
##### 初始化 BeanFactory
`ResourceLoader` 加载配置信息，并使用 `Resource` 表示配置文件资源，`BeanDefinitionReader` 读取 `Resource` 所指向的配置文件资源并解析，最终配置文件中的每个 `<bean>` 解析成为一个 `BeanDefinition` 并保存到 `BeanDefinitionRegistry` 中
```java
// refreshBeanFactory() -> getBeanFactory()
ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
```

##### 调用工厂后处理器
容器根据反射机制扫描 `BeanDefinitionRegistry` 中的 `BeanDefinition` 识别出 Bean 工厂后处理器并调用这些后处理器的 `postProcessBeanFactory()` 方法对 `BeanDefinitionRegistry` 中的 BeanDefinition 进行加工
  1. 对使用占位符的 <bean> 元素进行解析得到最终配置值
  2. 对 BeanDefinitionRegistry 中的 BeanDefinition 进行扫描，通过反射机制找出所有属性编辑器的 Bean 并将它们注册到 Spring 容器的属性编辑器注册表中
```java
invokeBeanFactoryPostProcessors();
```

##### 注册 Bean 后处理器
根据反射机制从 `BeanDefinitionRegistry` 中找出所有实现 `BeanPostProcessor` 接口的 Bean 并将它们注册到容器 Bean 后处理器的注册表中
```java
registerBeanPostProcessors();
```

##### 初始化消息源
```java
initMessageSource();
```

##### 初始化事件广播器
```java
initApplicationEventMulticaster();
```

##### 初始化其他特殊 Bean
```java
onRefresh();
```

##### 注册事件监听
```java
registerListeners();
```

##### 初始化单例 Bean
1. 容器从 `BeanDefinitionRegistry` 中取出加工后的 `BeanDefinition` 并调用 `InstantiationStrategy` 进行实例化操作，容器使用 `BeanWrapper` 对 Bean 进行封装实例化，`BeanWrapper` 结合 `BeanDefinition` 及容器中的属性编辑器完成 Bean 属性注入工作
2. 容器使用注册的 Bean 后处理器对已经完成属性设置的 Bean 继续加工
```java
finishBeanFactoryInitialization(beanFactory);
```

##### 完成刷新
完成刷新并发布容器刷新事件
```java
finishRefresh();
```

### BeanDefinition
`BeanDefinition` 是配置文件 `<bean>` 元素标签在容器中的内部表示，`RootBeanDefinition` 是最常用的实现类，它对应一般性的 <bean> 元素标签。一般情况下，`BeanDefinition` 只在容器启动时期加载并解析，除非容器刷新或者重启
#### BeanDefinition 的创建
1. 利用 `BeanDefinitionReader` 读取承载配置信息的 `Resource`，为每个 `<bean>` 生成对应的 `BeanDefinition` 对象
2. 利用容器中注册的 `BeanFactoryPostProcessor` 对半成品 `BeanDefinition` 加工处理，将以占位符表示的配置解析为最终的实际值

### BeanWrapper
`InstantiationStrategy` 仅负责实例化 Bean 操作不进行 Bean 属性设置工作，`BeanWrapper` 完成 Bean 属性的填充工作，通过调用 `BeanWrapper#setWrappedInstance(Object obj)` 完成

#### BeanWrapper 接口
`BeanWrapper` 有两个顶级接口，分别为 `PropertyAccessor` 和 `PropertyEditorRegistry`，`PropertyAccessor` 接口定义各种访问 Bean 属性的方法，`PropertyEditorRegistry` 为属性编辑器注册表
##### 属性编辑器
属性编辑器的主要功能是将外部设置值转换为 JVM 内部的对应类型，`PropertyEditorSupport` 实现了 `PropertyEditor` 接口并提供了默认实现，通常可以通过扩展这个类设计自定义属性编辑器
```java
public class Car {
  private int maxSpeed;
  public String brand;
  private double price;
}

public class Boss {
  private String name;
  private Car car = new Car();
}

public class CustomCarEditor extends PropertyEditorSupport {
  // 字面值转换为属性类型对象
  public void setAsText(String text) {
    if (null == text || text.indexOf(",") == -1) {
      throw new IllegalArgumentException("设置字符串格式错误");
    }
    String[] infos = text.split(",");
    Car car = new Car();
    car.setBrand(infos[0]);
    car.setMaxSpeed(Integer.parseInt(infos[1]));
    car.setPrice(Double.parseDouble(infos[2]));

    // 调用父类方法设置转换后的属性对象
    setValue(car);
  }
}
```
若使用 `BeanFactory` 需要手工调用 `registerCustomEditor` 方法注册自定义的属性编辑器，若使用 `ApplicationContext` 只需要在配置文件中通过 `CustomEditorConfigurer` 注册，这是因为 `CustomEditorConfigurer` 实现了 `BeanFactoryPostProcessor` 接口
```xml
<bean class="org.springframework.beans.factory.config.CustomEditorConfigurer">
  <property name="customEditors">
    <map>
      <entry key="com.pain.editor.car" value="com.pain.editor.CustomCarEditor">
    </map>
  </property>
</bean>

<bean id="boss" class="com.pain.editor.Boss">
  <property name="name" value="jack" />
  <property name="car" value="BENZ,200,20000.99" />
</bean>
```
JavaBean 基础设施会在 JavaBean 的相同类包下查找是否存在 <JavaBean>Editor 的规范类，若存在则自动使用 <JavaBean>Editor 作为 JavaBean 的 `PropertyEditor` 而无需显式在 `CustomEditorConfigurer` 中注册

#### BeanWrapperImpl
`BeanWrapperImpl` 是 `BeanWrapper` 实现类，作为 Bean 包裹器，属性访问器，也是属性编辑器注册表，`BeanWrapperImpl` 实例中封装了待处理的 Bean 与一套用于设置 Bean 属性的属性编辑器






