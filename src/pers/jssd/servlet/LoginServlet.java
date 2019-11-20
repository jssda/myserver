package pers.jssd.servlet;

import pers.jssd.server.Request;
import pers.jssd.server.Response;

/**
 * @author jssd
 * Create 2019-07-21 20:39
 */
public class LoginServlet extends Servlet{

	@Override
	public void doGet(Request req, Response resp) {
		String name = req.getParameterValue("name");
		String pwd = req.getParameterValue("pwd");
		if ("王京京".equals(name) && "123456".equals(pwd)) {
			resp.print("登录成功");
		} else {
			resp.print("登录失败");
		}
		resp.pushToClient(200);
	}

	@Override
	public void doPost(Request req, Response resp) {
		System.out.println("LoginServlet.doPost");
	}
}
