package com.itranswarp.learnjava;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {

	//创建客户端http连接实例
	static HttpClient httpClient = HttpClient.newBuilder().build();

	public static void main(String[] args) throws Exception {
		httpGet("https://www.sina.com.cn/");
		httpPost("https://accounts.douban.com/j/mobile/login/basic",
				"name=bob%40example.com&password=12345678&remember=false&ck=&ticket=");
		httpGetImage("https://img.t.sinajs.cn/t6/style/images/global_nav/WB_logo.png");
//		httpGetImage("https://picgo-tangg-chengdu.oss-cn-chengdu.aliyuncs.com/picgo-chengdu/image-20200517103006764.png");
	}

	static void httpGet(String url) throws Exception {
		//GET请求没有HTTP Body

		//设置http请求的信息

		//- 建立request对象的时候传入host信息（域名）
		HttpRequest request = HttpRequest.newBuilder(new URI(url))
				// 设置Header:
				//- 标识客户端自身信息；客户端能处理的http响应格式
				.header("User-Agent", "Java HttpClient").header("Accept", "*/*")
				// 设置超时:
				.timeout(Duration.ofSeconds(5))
				// 设置版本:
				.version(Version.HTTP_2).build();

		//向服务器端发送一个请求后会得到一个响应
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body().substring(0, 1024) + "...");

//		//打印出相应的具体信息
//		System.out.println("打印出响应的具体的信息：");
//		Map<String, List<String>> headers = response.headers().map();
//		for (String header : headers.keySet()) {
//			System.out.println(header + ": " + headers.get(header).get(0));
//		}
//		System.out.println(response.body().substring(0, 1024) + "...");
	}

	static void httpGetImage(String url) throws Exception {
		HttpRequest request = HttpRequest.newBuilder(new URI(url))
				// 设置Header:
				.header("User-Agent", "Java HttpClient").header("Accept", "*/*")
				// 设置超时:
				.timeout(Duration.ofSeconds(5))
				// 设置版本:
				.version(Version.HTTP_2).build();

		HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

		// 显示Http返回的图片:
		BufferedImage img = ImageIO.read(response.body());
		ImageIcon icon = new ImageIcon(img);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(200, 100);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	static void httpPost(String url, String body) throws Exception {
		HttpRequest request = HttpRequest.newBuilder(new URI(url))
				// 设置Header:
				.header("User-Agent", "Mozilla/5.0 (compatible, MSIE 11, Windows NT 6.3; Trident/7.0) like Gecko")
				.header("Accept", "*/*").header("Content-Type", "application/x-www-form-urlencoded")
				// 设置超时:
				.timeout(Duration.ofSeconds(5))
				// 设置版本:
				.version(Version.HTTP_2)
				// 使用POST并设置Body:
				.POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
				.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
	}
}
