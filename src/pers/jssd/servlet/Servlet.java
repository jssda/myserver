package pers.jssd.servlet;

import pers.jssd.server.Request;
import pers.jssd.server.Response;

/**
 * @author jssd
 * Create 2019-07-21 20:39
 */
public abstract class Servlet {

	public void service(Request req, Response resp) {
		doGet(req, resp);
	}

	public abstract void doGet(Request req, Response resp);

	public abstract void doPost(Request req, Response resp);

}
