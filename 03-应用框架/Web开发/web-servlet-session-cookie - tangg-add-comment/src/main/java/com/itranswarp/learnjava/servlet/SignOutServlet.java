package com.itranswarp.learnjava.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/signout")
public class SignOutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("\n\n执行signout 将用户名从session中移除");
		req.getSession().removeAttribute("user");
		System.out.println("即将跳转至根目录");
		resp.sendRedirect("/");
	}
}
