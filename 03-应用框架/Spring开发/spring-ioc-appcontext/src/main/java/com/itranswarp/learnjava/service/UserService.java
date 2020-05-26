package com.itranswarp.learnjava.service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/*
* 实现用户注册和登录
* */

public class UserService {

	private MailService mailService;

	//通过setMailService注入一个mailService
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	//构建User列表 users
	private List<User> users = new ArrayList<>(List.of( // users:
			new User(1, "bob@example.com", "password", "Bob"), // bob
			new User(2, "alice@example.com", "password", "Alice"), // alice
			new User(3, "tom@example.com", "password", "Tom"))); // tom

	//根据email和密码 进行登录
	public User login(String email, String password) {
		for (User user : users) {
			//如果登录成功，发送登录成功的邮件
			if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
				mailService.sendLoginMail(user);
				return user;
			}
		}
		throw new RuntimeException("login failed.");
	}

	public User getUser(long id) {
		return this.users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
	}

	//进行注册
	public User register(String email, String password, String name) {
		users.forEach((user) -> {
			if (user.getEmail().equalsIgnoreCase(email)) {
				throw new RuntimeException("email exist.");
			}
		});
		User user = new User(users.stream().mapToLong(u -> u.getId()).max().getAsLong(), email, password, name);
		users.add(user);
		mailService.sendRegistrationMail(user);
		return user;
	}
}
