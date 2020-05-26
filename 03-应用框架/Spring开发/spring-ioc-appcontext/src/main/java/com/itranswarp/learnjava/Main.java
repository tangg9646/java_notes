package com.itranswarp.learnjava;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.itranswarp.learnjava.service.User;
import com.itranswarp.learnjava.service.UserService;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//创建一个Spring的IoC容器实例
		//并加载配置文件
		//让Spring容器为我们创建并装配好配置文件中指定的所有Bean（一次性创建所有的Bean）
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

		//获取Bean
		//这里使用的方法是  根据Bean的类型获取Bean的引用
		UserService userService = context.getBean(UserService.class);
		//正常使用
		User user = userService.login("bob@example.com", "password");

		System.out.println(user.getName());
	}
}
