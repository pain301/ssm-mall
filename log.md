## LogBack
[参考](http://aub.iteye.com/blog/1101260)
### 结构
#### logback-core
提供 `LogBack` 核心功能，是另外两个组件的基础
#### logback-classic
实现 slf4j API，当配合 slf4j 使用时需要引入 `logback-classic`
#### logback-access
集成 `servlet` 环境而准备的，提供 HTTP-access 的日志接口

### configuration
scan: `true` 表示配置文件若发生改变，将会被重新加载
scanPeriod: 监测配置文件是否有修改的时间间隔，默认单位是毫秒。当 scan 为 `true` 时属性生效，默认的时间间隔为 1 分钟
debug: `true` 表示打印 logback 内部日志信息，实时查看 logback 运行状态，默认值为 `false`
```xml
<configuration scan="true" scanPeriod="60 seconds" debug="false">
  <!-- 其他配置省略-->
</configuration>
```

#### contextName 
设置日志上下文名称
```xml
<configuration scan="true" scanPeriod="60 seconds" debug="false">
  <!-- 其他配置省略-->
  <contextName>app</contextName>
</configuration>
```

#### logger
设置某一个包或者具体的某一个类的日志打印级别，可以包含零个或多个 `appender-ref` 元素
name: 指定 loger 约束的包或者具体类
level: 设置打印级别，TRACE, DEBUG, INFO, WARN, ERROR, ALL, OFF
addtivity: 是否向上级 logger 传递打印信息，默认是 `true`

#### root
根 loger，只有 level 属性，可以包含零个或多个 `appender-ref` 元素

#### ConsoleAppender
name: `appender` 名称
class: `appender` 全限定名
##### encoder
对日志进行格式化
##### target
`System.out` 或者 `System.err`, 默认 `System.out`

#### FileAppender
##### file
写入的文件名，可以是相对目录，也可以是绝对目录，若上级目录不存在会自动创建
##### append
`true` 表示日志被追加到文件结尾，`false` 表示清空现存文件，默认 `true`
##### encoder
对记录事件进行格式化
##### prudent
`true` 表示日志会被安全的写入文件，即使其他 `FileAppender` 也向此文件写入，效率低，默认 `false`

#### RollingFileAppender
滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件
##### file
写入的文件名，可以是相对目录，也可以是绝对目录，若上级目录不存在会自动创建
##### append
`true` 表示日志被追加到文件结尾，`false` 表示清空现存文件，默认是 `true`
##### encoder
对记录事件进行格式化
##### rollingPolicy
日志回滚策略
TimeBasedRollingPolicy: 基于时间的回滚策略，既负责滚动也负责触发滚动
FixedWindowRollingPolicy: 固定窗口算法重命名文件的滚动策略
##### triggeringPolicy
何时激活滚动
SizeAndTimeBasedRollingPolicy: 通过 `maxFileSize` 控制单个日志文件大小
##### prudent

### 具体配置
#### root
将级别为 `INFO` 及大于 `INFO` 的日志交给名为 `console` 的 `appender` 处理
```xml
<root level="INFO">
  <appender-ref ref="console" />
</root>
```
#### 单个 logger
`logger` 将控制 logback 包下的所有类的日志打印，由于没有设置打印级别，所以继承上级 `root` 日志级别 `DEBUG`; 由于没有设置 `addtivity`, 默认为 `true`, `logger` 的打印信息将向上级传递; 由于没有设置 `appender`, `logger` 本身不打印任何信息。`root` 接到下级传递的信息, 交给配置的 `appender` 处理
```xml
<logger name="logback" />

<root level="DEBUG">
  <appender-ref ref="console" />
</root>
```

#### 多个 logger
```xml
<logger name="logback" />

<logger name="logback.demo" level="INFO" additivity="false">
  <appender-ref ref="console" />
</logger>

<root level="ERROR">
  <appender-ref ref="console" />
</root>
```

