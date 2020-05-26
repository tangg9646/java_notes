package com.itranswarp.learnjava.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/")
public class IndexServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//从Session中取出用户名(当然，第一次调用的时候，可能是空的)
		//并且，第一次调用getSession方法的时候，会自动创建一个名为JSESSIONID的Cookie
		//在服务器发送Http响应的时候一并发送给浏览器（客户端）

		System.out.println("\n\n访问根目录，第一次访问将自动创建JSESSIONID的cookie，用于跟踪是否是同一个浏览器发送的请求\n\n");

		String user = (String) req.getSession().getAttribute("user");

		System.out.println("\n\n当前从浏览器发送的Http请求附带的Cookie中读取到的user名称为：" + user + "\n\n");

		String lang = parseLanguageFromCookie(req);

		System.out.println("\n\n当前从浏览器http请求中读取到的lang偏好Cookie");
		System.out.println("lang: " + lang + "\n\n" );

		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		PrintWriter pw = resp.getWriter();
		if (lang.equals("zh")) {
			pw.write("<h1>你好, " + (user != null ? user : "Guest") + "</h1>");
			if (user == null) {
				pw.write("<p><a href=\"/signin\">登录</a></p>");
			} else {
				pw.write("<p><a href=\"/signout\">登出</a></p>");
			}
		} else {
			pw.write("<h1>Welcome, " + (user != null ? user : "Guest") + "</h1>");
			if (user == null) {
				pw.write("<p><a href=\"/signin\">Sign In</a></p>");
			} else {
				pw.write("<p><a href=\"/signout\">Sign Out</a></p>");
			}
		}
		pw.write("<p><a href=\"/pref?lang=en\">English</a> | <a href=\"/pref?lang=zh\">中文</a>");
		pw.flush();
	}

	private String parseLanguageFromCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("lang")) {
					return cookie.getValue();
				}
			}
		}
		return "en";
	}
}
