package com.itranswarp.learnjava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */

//服务器端：实现对指定端口的接听
//我们没有指定IP地址，表示在计算机的所有网络接口上进行监听


public class Server {
	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(6666); // 监听指定端口
		System.out.println("server is running...");


		//使用死循环来处理客户端的连接
		for (;;) {
			Socket sock = ss.accept();//每当有新的客户端连接进来，就返回一个Sockets实例
			System.out.println("connected from " + sock.getRemoteSocketAddress());

			Thread t = new Handler(sock);	//为每一个新的连接，创建一个新线程进行处理
											//因此，类Handler是继承（extends）自Thread类
			t.start();
		}
	}
}

class Handler extends Thread {

	//类的Socket连接实例sock
	Socket sock;

	//构造方法
	public Handler(Socket sock) {
		this.sock = sock;
	}

	@Override
	public void run() {
		//服务器端，获取socket连接之后，尝试获得针对服务器来说的socket的输入输出流
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

	//获得对服务器来说的socket的输入输出流
	private void handle(InputStream input, OutputStream output) throws IOException {
		//将服务器的输出流，转封为BufferedWriter
		var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
		//将服务器端的输入流，封装为BufferedReader
		var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

		//向客户端发送hello
		writer.write("hello\n");
		writer.flush();

		//保持连接，等待接收客户端发送的数据，并进行处理
		for (;;) {
			String s = reader.readLine();
			if (s.equals("bye")) {
				writer.write("bye\n");
				writer.flush();
				break;
			}
			writer.write("ok: received " + s + "\n");
			writer.flush();
		}
	}
}
