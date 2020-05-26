本文整理自：[link](https://www.liaoxuefeng.com/wiki/1252599548343744/1266263217140032)

# Spring开发

Spring Framework主要包括几个模块：

- 支持IoC和AOP的容器；
- 支持JDBC和ORM的数据访问模块；
- 支持声明式事务的模块；
- 支持基于Servlet的MVC开发；
- 支持基于Reactive的Web开发；
- 以及集成JMS、JavaMail、JMX、缓存等其他模块。

# 1. IoC容器

容器是一种为某种特定组件的运行提供必要支持的一个软件环境。

使用容器运行组件，除了提供一个组件运行环境之外，容器还提供了许多底层服务。
例如，Servlet容器底层实现了TCP连接，解析HTTP协议等非常复杂的服务。

IoC容器，它可以管理所有轻量级的JavaBean组件，提供的底层服务包括组件的生命周期管理、配置和组装服务、AOP支持，以及建立在AOP基础上的声明式事务服务等。

本章我们讨论的IoC容器，主要介绍Spring容器如何对组件进行生命周期管理和配置组装服务。

## 1.1 IoC原理

IoC全称Inversion of Control，直译为控制反转。