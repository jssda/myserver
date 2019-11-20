package pers.jssd.servlet;

import pers.jssd.server.Request;
import pers.jssd.server.Response;

/**
 * @author jssd
 * Create 2019-07-21 22:51
 */
public class RegisterServlet extends Servlet {

	@Override
	public void doGet(Request req, Response resp) {
		System.out.println("RegisterServlet.doGet");
	}

	@Override
	public void doPost(Request req, Response resp) {
		System.out.println("RegisterServlet.doPost");
	}
}
