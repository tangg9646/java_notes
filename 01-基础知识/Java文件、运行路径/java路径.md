java中文件路径的相关问题

文章用到的IDEA工程链接为：[git](https://github.com/tangg9646/file_share/tree/master/maven-hello-pathtest), 

[本文文章地址](https://www.cnblogs.com/tangg/p/12820503.html)

工程的项目结构如下：

![image-20200503010444684](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200503010444684.png)

# 1. 基础概念

## 1.2 linux和win下的路径分隔符

Linux下：`/`

Window下：`\`

Java中通用：`System.getProperty(“file.separator”);`



## 1.2 路径分类

- 绝对路径
- 相对路径
- 相对classpath的路径
- 相对于当前用户目录的路径（JVM的运行路径）

# 2. jvm的运行路径

通常，jvm启动的路径为项目文件夹，比如一个名叫`testpath`的标准maven项目结构的java项目，jvm在`testpath`这个文件夹下启动，查看下文 测试代码1。

默认情况下，java.io 包中的类总是根据当前用户目录来分析相对路径名。
此目录由系统属性 user.dir 指定，通常是 Java 虚拟机的调用目录。

**关于相对路径：注意点**

- 在使用java.io包中的类时，最好不要使用相对路径。
- 对于JavaEE服务器，这可能是服务器的某个路径。这个并没有统一的规范！
- 在J2SE应用程序中可能还算正常，但是到了J2EE程序中，一定会出问题！而且这个路径，在不同的服务器中都是不同的！



# 3. 获取资源的路径

Java中取资源时，经常用到`Class.getResource()`和`ClassLoader.getResource()`，

在取资源文件时候可能的路径问题：

## 3.1 `Class.getResource(String path)`

**==通过类名==**

- path  不以’/'开头时，默认是从此`类所在的包`下取资源；

- path  以’/'开头时，则是从ClassPath根下获取；

查看测试代码 4 5

注意，不同的IDE可能不同，网上资料说eclipse会将项目src文件夹下的文件，经过编译，放置在`Test\bin`目录下，如下图中的1.properities  2.properities  3.properities文件

![image-20200503004045093](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200503004045093.png)

但是，我自己的实测，IDEA开发环境，不会在编译后，将 `src\main\java`目录下的文件，放置到 `target\classes`路径下

## 3.2 `Class.getClassLoader().getResource(String path)`

**通过类的实例**

- path不能以’/'开头时；
- path是从ClassPath根下获取；

`Class.getClassLoader().getResource(String path)`

从结果来看

`TestMain.class.getResource("/")` == `t.getClass().getClassLoader().getResource("")`



**其他：**

Class.getClassLoader().getResource和Class.getClassLoader().getResourceAsStream在使用时，根据类名和类的实例来确定路径，路径选择上也是一样的。



# 4.推荐使用的路径方式

推荐使用相对于当前classpath的路径,因为此路径不会因为时J2SE 或者 J2EE 而变化或者出错

# 供测试的基础代码

### 1. 当前运行的jvm路径

```java
//查看jvm正在运行的当前路径，后续的File类的相对路径都是以这个路径为基础
System.out.println("当前jvm运行的路径：");
System.out.println(System.getProperty("user.dir"));
```

比如，我的一个 `maven-hello-pathtest`IDEA项目，上述代码运行结果

```shell
当前jvm运行的路径：
C:\IDEA_WorkSpace\maven-hello-pathtest
```

说明，这个项目的jvm是在`maven-hello-pathtest`这个文件夹下启动的

### 2. 在当前目录创建文件

`.\`表示当前目录，但是在java中 `\`表示转义，因此，代码中，需要使用个 `\\`   (两个)

也可以直接给出文件名，则会在当前jvm的路径下创建文件

```java
//在当前目录创建
File file1 = new File(".\\当前目录创建1.properties");
file1.createNewFile();
File file11 = new File("当前目录创建2.properties");
file11.createNewFile();
```

### 3. 在指定文件夹创建文件

- 指定向下位置目录

同理，从当前目录开始，可以在项目的指定文件夹下创建文件（下面的代码默认此文件夹已经存在）

```java
File file2 = new File(".\\target\\generated-sources\\在指定目录创建文件1.properties");
file2.createNewFile();
File file22 = new File("target\\generated-sources\\在指定目录创建文件2.properties");
file22.createNewFile();
```

- 上级目录





### 4. 获取类所在的包的路径

```java
System.out.println("当前类所在的包的路径：");
System.out.println(Main.class.getResource(""));
```

比如，

```shell
当前类所在的包的路径：
file:/C:/IDEA_WorkSpace/maven-hello-pathtest/target/classes/com/itranswarp/learnjava/
```

### 5. 获取类的ClassPath路径

也就是，当前类，编译完成后，所处的绝对路径 (classpath: 就是项目中存放.class文件的路径。)

```java
System.out.println("当前类的ClassPath路径（不同的IDE可能目录结构不同）");
System.out.println(Main.class.getResource("/"));
```

运行结果：

```shell
当前类的ClassPath路径（不同的IDE可能目录结构不同）
file:/C:/IDEA_WorkSpace/maven-hello-pathtest/target/classes/
```

能实现类似效果的代码：

```java
Thread.currentThread().getContextClassLoader().getResource("")
```

```java
FileTest.class.getClassLoader().getResource("")
```

```java
ClassLoader.getSystemResource("")
```



### 6. 通过类的实例，获取路径

- 查看类名

```java
System.out.println(t.getClass());
```

- 获取类的 ClassPath路径（编译后的类的 `.class`文件的路径）

  （类似于方法5，通过类名事项的）

```
System.out.println(t.getClass().getClassLoader().getResource(""));
```

# 参考链接

[关于java 路径的一点总结](https://mp.weixin.qq.com/s/P_hBGcpndENmqJcO40TRXA)

https://blog.csdn.net/u011983531/article/details/48443195

[getResourceAsStream的几种路径配置](https://blog.csdn.net/liu911025/article/details/80415001?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2)

[深入解析Java绝对路径与相对路径](https://juejin.im/post/5c6e50c251882562eb50fd42)