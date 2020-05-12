# java_notes
用于存放学习java相关内容的笔记

## 仓库结构

仓库的目录根据**程序羊**建议的学习路线整理[文章](https://mp.weixin.qq.com/s?__biz=MzU4ODI1MjA3NQ==&mid=2247485282&idx=1&sn=1ab5c0722538bbe9d80dfee2bf30621d&scene=19#wechat_redirect)

![image-20200507115711242](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200507115711242.png)

高清大图

![](https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/Java后端开发学习路线-高清版.jpg)

## 学习路线

### **基础知识** 

该部分是整个路线里面最最重要的部分，也是后续进行框架学习、能看懂开源项目的前置基础，这部分是最不能忽视的！

**一、语言本身**

如下一些重点必须掌握：

- 语法基础
- OO编程思想：类、对象、封装、继承、多态、接口
- 容器
- 异常
- 泛型
- I/O
- 注解
- 反射
- ~~图形化界面，如 `swing`的东西跳过不看~~

**二、数据结构和算法**

- 几大基础数据结构类型得烂熟于心，比如：**字符串**、**链表**、**二叉树**、**栈**、**队列**等等；基本的几大算法要了如指掌，比如**查找**、**排序**、**动态规划**等等。

**三、TCP/IP协议栈**

- TCP/IP协议栈可以说是当下互联网通信的基石，无论如何一定要对TCP/IP的协议栈了如指掌，包括：**ARP协议**、**IP协议**、**ICMP协议**、**TCP和UDP协议**、**DNS协议**、**HTTP协议**、**HTTPS协议**等等

**四、设计模式**

- 倒不需要23种设计模式全部很熟悉，常见的几个，比如：**单例模式**、**工厂模式**、**代理模式**、**策略模式**、**模板方法模式**等几个熟练于心即可

**五、数据库和SQL**

- 数据库基本原理了解，SQL语句熟练书写，常见的优化方式熟悉

**六、操作系统**

- 该部分重点包括：进程和线程的相关原理（原子性、并发、锁）、内存相关原理（内存分布、内存调度）等

------

###  **项目工具** 

**`Linux`系统**基本命令会使用， `Linux`系统上常用的服务会部署 

**代码管理**：`SVN`或 `Git` 二选一，持续练习，熟练使用 

基于 `Maven`或 `Gradle`的 **`Java`项目管****理**二选一，熟练使用

------

###  **应用框架** 

**一、Spring全家桶**

了解Spring、Mybatis等框架的基本原理 

Spring Boot框架会熟练使用、开发业务、掌握基本原理 

SSM组合框架会上手搭建项目、开发业务、掌握基本原理

**二、中间件技术**

**消息队列**，主流的如 `RabbitMQ`、 `Kafka`等 

**RPC通信**框架，主流的如 `gRPC`、 `Thrift`、 `Dubbo`等 

**NoSQL数据库**：主流的如 `Redis`、 `memcached`、 `ElasticSearch`等 

**NIO网络通信**框架，主流的如 `Netty`等

这些成熟的中间件框架在企业级产品里应用得广泛而深远，建议先是要会**熟练使用**，要是能了解**底层原理**实现那就更加分了！

**三、分布式微服务**

当下微服务盛行，是个公司都说自己在搞微服务，所以诸如 `SpringCloud`这样的微服务框架怎么能不学，要求也是先会使用，再尝试搞懂原理

**四、虚拟化/容器化**

虚拟化、容器化平台是未来发展的大趋势，很多规模化企业都在搭建各SaaS/PaaS/IaaS平台，在此过程中诞生的一系列关于该方面的技术在学有余力的情况下最好也涉猎一下，典型的比如：`Docker`容器、 `kubernetes`编排技术。

------

###  **源码/性能** 

在如今这个流量极其密集的互联网时代，关注到**源码和性能**层面的程序员才是顶级好码农，所以：

- 关注JDK源码和设计思想
- 关注Java并发编程原理和实践
- 关注JVM细节原理与调优
- 关注上述应用框架的核心思想和内部源码
- 关注数据库深度优化
- 等等...

则成了走向顶级程序员之路的必备技能。