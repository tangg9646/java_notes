package com.itranswarp.learnjava.server2;

import com.itranswarp.learnjava.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class Server2 {
	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(8090); // 监听指定端口
		System.out.println("server is running...");

		//使用死循环，处理来自客户端的连接
		for (;;) {
			Socket sock = ss.accept();
			System.out.println("connected from " + sock.getRemoteSocketAddress());
			Thread t = new Handler(sock);
			t.start();
		}
	}
}

class Handler extends Thread {
	Socket sock;

	public Handler(Socket sock) {
		this.sock = sock;
	}

	@Override
	public void run() {
		try (InputStream input = this.sock.getInputStream()) {
			try (OutputStream output = this.sock.getOutputStream()) {
				handle(input, output);
			}
		} catch (Exception e) {
			try {
				this.sock.close();
			} catch (IOException ioe) {
			}
			System.out.println("client disconnected.");
		}
	}

	private void handle(InputStream input, OutputStream output) throws IOException {
		System.out.println("Process new http request...");
		var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));

		// 读取HTTP请求:
		int requestFlag = -1;

		String first = reader.readLine();
		System.out.println("\n\n客户端Http连接请求的第一行内容为：");
		System.out.println(first);

		//根据请求头的内容，判断是什么状态
		if (first.startsWith("GET / HTTP/1.")) {
			requestFlag = 1;
		}else if (first.startsWith("GET /favicon.ico HTTP/1.")) requestFlag = 2;

		System.out.println("即将打印来自客户端连接的Http请求的header部分");
		for (;;) {
			String header = reader.readLine();
			if (header.isEmpty()) { // 读取到空行时, HTTP Header读取完毕
				break;
			}
			System.out.println(header);
		}
		System.out.println("requestFlag: " + requestFlag);

		//根据requestFlag状态的不同，执行不同的操作
		switch (requestFlag){
			case -1:
				// 发送错误响应:
				writer.write("404 Not Found\r\n");
				writer.write("Content-Length: 0\r\n");
				writer.write("\r\n");
				writer.flush();
				break;
			case 1:
				// 发送成功响应:
				//即将向客户端连接发送Http响应
				String data = "<html><body><h1>Hello, world!</h1></body></html>";
				int length = data.getBytes(StandardCharsets.UTF_8).length;
				writer.write("HTTP/1.0 200 OK\r\n");
				writer.write("Connection: close\r\n");
				writer.write("Content-Type: text/html\r\n");
				writer.write("Content-Length: " + length + "\r\n");
				writer.write("\r\n"); // 空行标识Header和Body的分隔
				writer.write(data);
				writer.flush();
				break;
			case 2:
//				//向客户端发送请求的图片
//				byte[] b = Server.class.getResourceAsStream("/favicon.ico").readAllBytes();
//				writer.write("HTTP/1.0 200 OK\r\n");
//				writer.write("Connection: close\r\n");
//				writer.write("Content-Type: text/html\r\n");
//				writer.write("Content-Length: " + b.length + "\r\n");
//				writer.write("\r\n"); // 空行标识Header和Body的分隔
//				writer.write(b.toString());
//				writer.flush();

				//向客户端发送请求的图片
				byte[] b = Server.class.getResourceAsStream("/favicon.ico").readAllBytes();
				writer.write("HTTP/1.0 200 OK\r\n");
				writer.write("Connection: close\r\n");
				writer.write("Content-Type: text/html\r\n");
				writer.write("Content-Length: " + b.length + "\r\n");
				writer.write("\r\n"); // 空行标识Header和Body的分隔
				writer.flush();

				output.write(b);
				output.flush();
				break;
		}

	}
}
