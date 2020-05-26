package com.itranswarp.learnjava.servlet;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/pref")
public class LanguageServlet extends HttpServlet {

	private static final Set<String> LANGUAGES = Set.of("en", "zh");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//从http请求中读取偏爱的语言 参数
		String lang = req.getParameter("lang");

		//如果用户偏爱的语言，存在于可选的语言中，LANGUAGES
		if (LANGUAGES.contains(lang)) {
			//使用另外的Cookie来存储用户的偏爱语言信息
			//新建Cookie的方式及其参数设置
			Cookie cookie = new Cookie("lang", lang);
			cookie.setPath("/");
			cookie.setMaxAge(8640000); // 100 days
			//如果访问的是https网页，还需要调用setSecure(true)
 			//cookie.setSecure(true);

			//Cookie建立完毕后，通过Http响应发送给客户端（客户端将会把此Cookie保存起来）
			resp.addCookie(cookie);
		}
		resp.sendRedirect("/");
	}
}
