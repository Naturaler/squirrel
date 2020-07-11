# slf4j使用

## 1、maven依赖
### 1.1、pom.xml
```xml
<!-- log start -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
</dependency>

<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
</dependency>
<!-- log end -->
```

### 1.2、依赖说明
添加 lombok

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
</dependency>
```

后，可以使用注解 @slf4j
否则，只能手动 new 一个日志对象

```java
private static Logger logger = LoggerFactory.getLogger(Xxx.class);
```

## 2、配置

```properties
log4j.rootLogger=debug, console
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} [%p %t] %l %m%n
```

## 3、实际效果

> 2020-07-11 16:52:36 [INFO sub-thread-2] com.yrx.squirrel.nut.concurrent.java.CountDownLatchDemo.lambda$main$0(CountDownLatchDemo.java:29)  end sleep