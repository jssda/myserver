package pers.jssd.test;

import pers.jssd.util.CloseUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 对服务器进行压力测试
 *
 * @author jssd
 * Create 2019-07-24 11:17
 */
public class PressTest {
	public static void main(String[] args) {
		// 线程池
		ExecutorService service = Executors.newCachedThreadPool();
		// 先将所有线程锁住
		CountDownLatch countDownLatch = new CountDownLatch(5000);
		Semaphore sem = new Semaphore(50);
		long start = System.nanoTime();
		for (int i = 0; i <= 5000; i++) {
			service.execute(() -> {
				Socket client = null;
				BufferedWriter bw = null;
				try {
					sem.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					// 创建5000个客户端
					client = new Socket("localhost", 9999);
					// 模拟发送HTTP请求
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
					bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
					bw.write(str);
					bw.flush();
				} catch (IOException e) {
					System.out.println("IO异常");
					e.printStackTrace();
				} finally {
					sem.release();
					CloseUtil.close(bw, client);
					countDownLatch.countDown();
				}
			});
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.nanoTime();
		System.out.println("共耗时" + (end - start) / 1000000 + "ms");
	}
}
