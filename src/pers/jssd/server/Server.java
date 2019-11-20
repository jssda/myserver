package pers.jssd.server;

import pers.jssd.util.CloseUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器
 *
 * @author jssd
 * Create 2019-07-21 23:15
 */
public class Server {

	/**
	 * 服务器的socket
	 */
	private ServerSocket ss;

	// 添加一个线程池
	private ExecutorService service = Executors.newCachedThreadPool();

	private boolean isShutDown;

	public Server() {
		try {
			ss = new ServerSocket(9999);
		} catch (IOException e) {
			shutdown();
			e.printStackTrace();
		}
	}

	/**
	 * 启动服务器
	 */
	public void start() {
		Socket client;
		try {
			int count = 0;
			while (!isShutDown) {
				client = ss.accept();
				System.out.println("第" + (count++) + "次请求数据");

				Dispatcher dispatcher = new Dispatcher(client);
				service.execute(dispatcher);
			}
		} catch (IOException e) {
			shutdown();
		}
	}

	/**
	 * 关闭服务器的方法
	 */
	public void shutdown() {
		CloseUtil.close(ss);
		service.shutdown();
		isShutDown = true;
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}
