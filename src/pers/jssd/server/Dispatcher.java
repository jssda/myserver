package pers.jssd.server;

import pers.jssd.servlet.Servlet;

import java.io.IOException;
import java.net.Socket;

/**
 * @author jssd
 * Create 2019-07-23 23:46
 */
public class Dispatcher implements Runnable {

	private Socket client;

	public Dispatcher(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			Request request = new Request(client.getInputStream());
			Response response = new Response(client.getOutputStream());
			Servlet servlet = WebApp.getServlet(request.getUri());
			if (servlet == null) {
				int code = 500;
				response.pushToClient(code);
			} else {
				servlet.doGet(request, response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
