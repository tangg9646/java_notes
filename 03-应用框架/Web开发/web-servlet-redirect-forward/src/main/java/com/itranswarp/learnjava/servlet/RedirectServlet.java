package com.itranswarp.learnjava.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/hi")
public class RedirectServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String redirectToUrl = "/hello" + (name == null ? "" : "?name=" + name);
		//302临时重定向
		resp.sendRedirect(redirectToUrl);

		//如果要永久重定向，代码为
//		resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY); // 301
//		resp.setHeader("Location", "/hello");
	}
}
