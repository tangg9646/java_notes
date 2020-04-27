本文主要整理记录来自：[廖雪峰的官方网站](https://www.liaoxuefeng.com/wiki/1252599548343744/1255945359327200)

# 13. Maven基础

## 13.1 Maven介绍

**一个Java项目需要的东西**

- 依赖包的管理
  确定引入哪些依赖包，把相关的jar包都放到classpath中

- 项目的目录结构
  `src`目录存放Java源码，`resources`目录存放配置文件，`bin`目录存放编译生成的`.class`文件

- 配置环境
  JDK的版本，编译打包的流程，当前代码的版本号
- 命令行工具进行编译
  让项目在一个独立的服务器上编译、测试、部署

**Maven**是Java项目的管理和构建工具，它的主要功能有：

- 提供了一套标准化的项目结构；
- 提供了一套标准化的构建流程（编译，测试，打包，发布……）；
- 提供了一套依赖管理机制。

### Maven项目结构

一个使用Maven管理的普通的Java项目，它的目录结构默认如下：

```ascii
a-maven-project
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   └── test
│       ├── java
│       └── resources
└── target
```

项目的根目录`a-maven-project`是项目名，

- 一个项目描述文件`pom.xml`，
- 存放Java源码的目录是`src/main/java`，
- 存放资源文件的目录是`src/main/resources`，
- 存放测试源码的目录是`src/test/java`，
- 存放测试资源的目录是`src/test/resources`，
- 所有编译、打包生成的文件都放在`target`目录里。

### 项目描述文件`pom.xml`

```xml
<project ...>
	<modelVersion>4.0.0</modelVersion>
    
	<groupId>com.itranswarp.learnjava</groupId>
	<artifactId>hello</artifactId>
	<version>1.0</version>
    
	<packaging>jar</packaging>
	<properties>
        ...
	</properties>
	<dependencies>
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.2</version>
        </dependency>
	</dependencies>
</project>
```

- `groupId`类似于Java的包名，通常是公司或组织名称，
- `artifactId`类似于Java的类名，通常是项目名称，
- 再加上`version`

上述三个字段作为maven确定依赖，也就是唯一的引用第三方库的字段

如，依赖`commons-logging`：

```xml
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.2</version>
</dependency>
```

使用``声明一个依赖后，Maven就会自动下载这个依赖包并把它放到classpath中。

## 13.2 依赖管理

例如，项目依赖`abc`这个jar包，而`abc`又依赖`xyz`这个jar包

![image-20200426231215750](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200426231215750.png)

### 自动解析依赖

当我们声明了`abc`的依赖时，Maven自动把`abc`和`xyz`都加入了我们的项目依赖，不需要我们自己去研究`abc`是否需要依赖`xyz`。

复杂依赖示例：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>1.4.2.RELEASE</version>
</dependency>
```

声明一个`spring-boot-starter-web`依赖时，Maven会自动解析并判断最终需要大概二三十个其他依赖：

```ascii
spring-boot-starter-web
  spring-boot-starter
    spring-boot
    sprint-boot-autoconfigure
    spring-boot-starter-logging
      logback-classic
        logback-core
        slf4j-api
      jcl-over-slf4j
        slf4j-api
      jul-to-slf4j
        slf4j-api
      log4j-over-slf4j
        slf4j-api
    spring-core
    snakeyaml
  spring-boot-starter-tomcat
    tomcat-embed-core
    tomcat-embed-el
    tomcat-embed-websocket
      tomcat-embed-core
  jackson-databind
  ...
```

### 依赖关系

Maven定义了几种依赖关	系，分别是`compile`、`test`、`runtime`和`provided`：

| scope    | 说明                                                         | 示例            |
| :------- | :----------------------------------------------------------- | :-------------- |
| compile  | 编译时需要用到该jar包（默认）                                | commons-logging |
| test     | 编译Test时需要用到该jar包<br>仅在测试时使用，正常运行时并不需要 | junit           |
| runtime  | 编译时不需要，但运行时需要用到                               | mysql           |
| provided | 编译时需要用到，但运行时由JDK或某个服务器提供                | servlet-api     |

其中，默认的`compile`是最常用的，Maven会把这种类型的依赖直接放入classpath。

### 从何处下载所需依赖

Maven如何知道从何处下载所需的依赖？也就是相关的jar包？
答案是Maven维护了一个中央仓库（[repo1.maven.org](https://repo1.maven.org/)），所有第三方库将自身的jar以及相关信息上传至中央仓库，Maven就可以从中央仓库把所需依赖下载到本地。

Maven并不会每次都从中央仓库下载jar包。一个jar包一旦被下载过，就会被Maven自动缓存在本地目录（用户主目录的`.m2`目录），所以，除了第一次编译时因为下载需要时间会比较慢，后续过程因为有本地缓存，并不会重复下载相同的jar包。

### 在IDEA中使用maven

配置为国内的Maven源（阿里源）[博客](https://www.miaoroom.com/code/note/maven-mirrors-settings.html)

### 搜索第三方组件

如果我们要引用一个第三方组件，比如`okhttp`，如何确切地获得它的`groupId`、`artifactId`和`version`？

方法是通过网站：[search.maven.org](https://search.maven.org/)搜索关键字，找到对应的组件后，直接复制：

![image-20200426233712586](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200426233712586.png)

### 命令行编译

在命令中，进入到`pom.xml`所在目录，输入以下命令：

```
$ mvn clean package
```

如果一切顺利，即可在`target`目录下获得编译后自动打包的jar。

## 13.3 构建流程

Maven不但有标准化的项目结构，而且还有一套标准化的构建流程，可以自动化实现编译，打包，发布，等等。

### Lifecycle和Phase

使用Maven时，我们首先要了解什么是Maven的==生命周期==（lifecycle）。

**`default lifecycle`**

Maven的生命周期由一系列阶段（phase）构成，以内置的生命周期`default`为例，它包含以下phase：

- validate
- initialize
- generate-sources
- process-sources
- generate-resources
- process-resources
- compile
- process-classes
- generate-test-sources
- process-test-sources
- generate-test-resources
- process-test-resources
- test-compile
- process-test-classes
- test
- prepare-package
- package
- pre-integration-test
- integration-test
- post-integration-test
- verify
- install
- deploy

运行`mvn package`，Maven就会执行`default`生命周期，它会从开始一直运行到`package`这个phase

运行`mvn compile`，Maven也会执行`default`生命周期，但这次它只会运行到`compile`

**`clean lifecycle`**

它会执行3个phase：

- pre-clean
- clean （注意这个clean不是lifecycle而是phase）
- post-clean

### 常用命令

- `mvn clean`：清理所有生成的class和jar；
- `mvn clean compile`：先清理，再执行到`compile`；
- `mvn clean test`：先清理，再执行到`test`，因为执行`test`前必须执行`compile`，所以这里不必指定`compile`；
- `mvn clean package`：先清理，再执行到`package`。

### Goal

执行一个phase又会触发一个或多个goal：

| 执行的Phase | 对应执行的Goal                     |
| :---------- | :--------------------------------- |
| compile     | compiler:compile                   |
| test        | compiler:testCompile surefile:test |

goal的命名总是`abc:xyz`这种形式。

大多数情况，我们只要指定phase，就默认执行这些phase默认绑定的goal，
只有少数情况，我们可以直接指定运行一个goal，例如，启动Tomcat服务器：

```
mvn tomcat:run
```

## 13.4 使用插件

maven执行每个phase，都是通过某个插件（plugin）来执行的,
它只是负责找到对应的插件，然后执行默认的goal来完成编译。

所以，使用Maven，实际上就是配置好需要使用的插件，然后通过phase调用它们。

### 使用自定义插件

创建可执行的jar包，参看下文中的踩坑笔记，第二点

Maven自带的标准插件例如`compiler`是无需声明的，只有引入其它的插件才需要声明。

下面列举了一些常用的插件：

- maven-shade-plugin：打包所有依赖包并生成可执行jar；
- cobertura-maven-plugin：生成单元测试覆盖率报告；
- findbugs-maven-plugin：对Java源码进行静态分析以找出潜在问题。

## 13.5 模块管理

在软件开发中，把一个大项目分拆为多个模块是降低软件复杂度的有效方法：

<img src="https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200427105529408.png" alt="image-20200427105529408" style="zoom:80%;" />

对Maven工程来说，原来的大项目

![image-20200427105740554](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200427105740554.png)

拆分成三个模块

![image-20200427105654892](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200427105654892.png)

**说明：**

### parent中的pom.xml

提出模块a b c中相同的部分，作为parent

注意：这里的 `<packaging>pom</packaging>`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.itranswarp.learnjava</groupId>
    <artifactId>parent</artifactId>
    <version>1.0</version>
    <packaging>pom</packaging>

    <name>parent</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <java.version>11</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.28</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.5.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

### 模块a的pom.xml

  类似的，模块b c 都可以从parent继承，大大简化pom.xml的编写

`<packaging>jar</packaging>`

  ```xml
  <project xmlns="http://maven.apache.org/POM/4.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <parent>
          <groupId>com.itranswarp.learnjava</groupId>
          <artifactId>parent</artifactId>
          <version>1.0</version>
          <relativePath>../parent/pom.xml</relativePath>
      </parent>
  
      <artifactId>module-a</artifactId>
      <packaging>jar</packaging>
      <name>module-a</name>
  </project>
  ```

**如果模块a依赖模块b**

如果模块A依赖模块B，则模块A需要模块B的jar包才能正常编译，我们需要在模块A中引入模块B：

```xml
    ...
    <dependencies>
        <dependency>
            <groupId>com.itranswarp.learnjava</groupId>
            <artifactId>module-b</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
```

### 根目录pom.xml

最后，在编译的时候，需要在根目录创建一个`pom.xml`统一编译：

`<packaging>pom</packaging>`

根目录执行`mvn clean package`时，Maven根据根目录的`pom.xml`找到包括`parent`在内的共4个`<module>`，一次性全部编译。

```java
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.itranswarp.learnjava</groupId>
    <artifactId>build</artifactId>
    <version>1.0</version>
    <packaging>pom</packaging>
    <name>build</name>

    <modules>
        <module>parent</module>
        <module>module-a</module>
        <module>module-b</module>
        <module>module-c</module>
    </modules>
</project>
```

## 13.6 使用mvnw

mvnw即Maven Wrapper，用于给一个特定项目提供一个独立的，指定版本的Maven。

### 安装Maven Wrapper

安装Maven Wrapper最简单的方式是在项目的根目录（即`pom.xml`所在的目录）下运行安装命令：

```
mvn -N io.takari:maven:0.7.6:wrapper
```

它会自动使用最新版本的Maven。注意`0.7.6`是Maven Wrapper的版本。最新的Maven Wrapper版本可以去[官方网站](https://github.com/takari/maven-wrapper)查看。

如果要指定使用的Maven版本，使用下面的安装命令指定版本，例如`3.3.3`：

```
mvn -N io.takari:maven:0.7.6:wrapper -Dmaven=3.3.3
```

安装后，查看项目结构：（安装完成后，mvnw被安装在当前项目的目录下）

![image-20200427111139295](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200427111139295.png)

### 使用

只需要把`mvn`命令改成`mvnw`就可以使用跟项目关联的Maven。例如：

```
mvnw clean package
```

在Linux或macOS下运行时需要加上`./`：

```
./mvnw clean package
```

Maven Wrapper的另一个作用是把项目的`mvnw`、`mvnw.cmd`和`.mvn`提交到版本库中，可以使所有开发人员使用统一的Maven版本。

## 踩坑笔记

### 1. 运行jar包

一、如果java定义了jar包的main class入口，则使用如下命令

```
$ java -jar myjar.jar
```

二、如果Java没有定义jar的main class入口，则使用如下命令

```
$ java -cp myjar.jar com.example.MainClass
```

### 2.创建可执行的jar包

maven默认的package插件并不能创建可执行的jar包，

需要使用另外的自定义插件 `maven-shade-plugin`

使用这个插件，

- 声明这个插件

需要在 pom.xml 文件中声明，在如下目录下添加插件。

- 配置插件

比如，需要指定 Main-Class

```xml
<build>
   <plugins>
```

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>1.4</version>
    <configuration>
        <createDependencyReducedPom>true</createDependencyReducedPom>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <transformers>
                    <transformer
                            implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>Main.Main</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### 3.执行mvn clean失败

可能是在某个 terminal 打开了target目录，或者 此项目下的其他目录

导致，mvn clean失败，关闭这些termial， 即可成功运行

### 4.更改maven源为国内源

配置为国内的Maven源（阿里源）[博客](https://www.miaoroom.com/code/note/maven-mirrors-settings.html)