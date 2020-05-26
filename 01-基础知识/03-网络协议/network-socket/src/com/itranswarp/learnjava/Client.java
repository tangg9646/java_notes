package com.itranswarp.learnjava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException {

		//建立客户端的Socekt连接实例
		Socket sock = new Socket("localhost", 6666); // 连接指定服务器和端口
		System.out.println("Client started, established socket connection.");

		//建立连接后，尝试获取客户端的输入输出流
		try (InputStream input = sock.getInputStream()) {
			try (OutputStream output = sock.getOutputStream()) {
				handle(input, output);
			}
		}
		sock.close();
		System.out.println("disconnected.");
	}

	private static void handle(InputStream input, OutputStream output) throws IOException {
		//对客户端连接来说的输出和输入流
		var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
		var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

		Scanner scanner = new Scanner(System.in);
		//这里打印的是，刚刚建立服务器与客户端连接的时候，接收到的服务器发送过来的信息
		System.out.println("[server] " + reader.readLine());

		for (;;) {
			System.out.print(">>> "); // 打印提示
			String s = scanner.nextLine(); // 读取一行输入
			writer.write(s);
			writer.newLine();
			writer.flush();
			String resp = reader.readLine();
			System.out.println("<<< " + resp);
			if (resp.equals("bye")) {
				break;
			}
		}
	}
}
