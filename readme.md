将该项目置于/root目录下，可以直接到三个子项目中编译运行

## 依赖

jdk 1.8.0_362
maven 3.6.3

## 编译及运行命令

### my-exporter

maven exporter demo

```java
cd /root/my-exporter
mvn clean package
java -cp target/my-exporter-1.0.0-jar-with-dependencies.jar MyExporter
```

https 协议启动

```java
cd /root/https-demo
mvn clean package
java -cp target/my-exporter-1.0.0-jar-with-dependencies.jar MyExporter
```

自定义TLS协议版本与加密套件

```java
cd /root/https-demo-plus
mvn clean package
java -cp target/my-exporter-1.0.0-jar-with-dependencies.jar MyExporter
```
