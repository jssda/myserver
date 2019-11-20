package pers.jssd.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 向手写的服务器发送请求压力
 *
 * @author Kent
 * @time 2019-06-19
 */
public class RequestStress {

	// 请求总数设定为5000次
	public static int requestTotal = 5000;
	// 控制同时并发执行的线程数
	public static int threadTotal = 1000;
	// 用作记录并发线程数
	//	public static AtomicInteger a = new AtomicInteger();

	// 每执行一次请求，计数器加1
	public static AtomicInteger count = new AtomicInteger();

	// 计数器加1
	public static void add() {
		count.incrementAndGet();
	}

	public static void main(String[] args) throws InterruptedException {
		// 模拟登录提交的请求
		String str = "GET /log?name=jssd&pwd=123456&hobby=basketball HTTP/1.1\n" +
				"Referer: http://localhost:63342/myserver/index.html?_ijt=p7ccrr9k7oisuk4qumbh0ojl93\n" +
				"Cache-Control: max-age=0\n" +
				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
				"Accept-Language: zh-Hans-CN,zh-Hans;q=0.8,en-US;q=0.5,en;q=0.3\n" +
				"Upgrade-Insecure-Requests: 1\n" +
				"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/18.17763\n" +
				"Accept-Encoding: gzip, deflate\n" +
				"Host: localhost:8888\n" +
				"Connection: Keep-Alive\n" +
				"Cookie: Idea-84c3ee85=c7128707-3314-49ad-98b0-11f30447bb8d; Webstorm-c036245e=cabf3c1d-e081-4a63-86cb-5d806b344131";

		// 多个线程运行使用线程池
		ExecutorService pool = Executors.newCachedThreadPool();
		// 信号量，控制同时并发的线程数
		final Semaphore sem = new Semaphore(threadTotal);
		// 闭锁，可以实现计数器递减
		final CountDownLatch cdl = new CountDownLatch(requestTotal);
		// 开始执行5000次请求任务
		for (int i = 0; i < requestTotal; i++) {
			pool.execute(new Runnable() {
				OutputStream os;
				Socket client;
				@Override
				public void run() {
					try {
						// 获取许可，当总计线程数未达到200时，允许通行，否则线程阻塞，知道许可
						sem.acquire();
					} catch (InterruptedException e1) {
						System.out.println("获取许可异常");
						e1.printStackTrace();
					}
					// 创建Socket对象模拟浏览器提交
					try {
						client = new Socket("localhost", 9999);
					} catch (IOException e) {
						System.out.println("创建Socket异常");
//						System.out.println(a.get());
						e.printStackTrace();
						System.exit(0);
					}
					try {
						os = client.getOutputStream();
					} catch (IOException e) {
						System.out.println("获取socket输出流异常");
						e.printStackTrace();
						System.exit(0);
					}
					try {
						os.write(str.getBytes());
						os.flush();
						os.close();
						// 提交一次请求后计数器加1
						add();
						// 释放许可
						sem.release();
					} catch (IOException e) {
						System.out.println("写出请求异常");
//						System.out.println(a);
						System.exit(0);
						e.printStackTrace();
					}
					// 闭锁减一
					cdl.countDown();
				}
			});
		}
		// 线程阻塞，当闭锁到达0时，阻塞才释放，然后再继续往下执行
		cdl.await();
		pool.shutdown();
		System.out.println(count.get());
	}
}