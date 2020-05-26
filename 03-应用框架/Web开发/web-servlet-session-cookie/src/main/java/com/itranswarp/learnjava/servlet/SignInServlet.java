package com.itranswarp.learnjava.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/signin")
public class SignInServlet extends HttpServlet {

	private Map<String, String> users = Map.of("bob", "bob123", "alice", "alice123", "tom", "tomcat");

	//Get请求时，显示登陆界面
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.write("<h1>Sign In</h1>");
		pw.write("<form action=\"/signin\" method=\"post\">");
		pw.write("<p>Username: <input name=\"username\"></p>");
		pw.write("<p>Password: <input name=\"password\" type=\"password\"></p>");
		pw.write("<p><button type=\"submit\">Sign In</button> <a href=\"/\">Cancel</a></p>");
		pw.write("</form>");
		pw.flush();
	}

	//Post请求时，处理用户登录
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//从http请求中读取提交的用户名和用户密码
		String name = req.getParameter("username");
		String password = req.getParameter("password");

		//根据提交的用户名从数据库中找到正确的密码
		String expectedPassword = users.get(name.toLowerCase());

		//判断密码是否是正确的
		if (expectedPassword != null && expectedPassword.equals(password)) {
			//如果密码是正确的，则将此用户名放入Session中
			//第一次调用getSession()的时候，Servlet容器会自动创建一个SessionID
			//通过一个名为JSESSIONID的Cookie发送给浏览器（即发送Http响应的时候发送回去的）
			req.getSession().setAttribute("user", name);
			//重定向到指定目录下，在这里，会跳转到根目录下IndexServlet
			resp.sendRedirect("/");
		} else {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}
}
