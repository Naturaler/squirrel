# maven

## 1、maven 指定项目jdk版本
### 1.1、指定方式

```xml
<build>
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
```

### 1.2、备注
此方式仅对当前项目有效