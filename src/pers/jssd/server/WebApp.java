package pers.jssd.server;

import pers.jssd.servlet.Servlet;

import java.util.List;
import java.util.Map;

/**
 * 存放一个网站的整体信息, 服务器会通过此对象调用取得servlet
 *
 * @author jssd
 * Create 2019-07-21 22:11
 */
public class WebApp {
	/**
	 * servlet 上下文对象
	 */
	private static ServletContent content;

	static {
		// 初始化
		content = new ServletContent();
		Map<String, String> entityMap = content.getEntityMap();
		Map<String, String> mappingMap = content.getMappingMap();
		// 解析XML文档, 取得entities容器和mappings容器
		WebDom4j webDom4j = new WebDom4j();
		webDom4j.parseDocument(webDom4j.getDocument());
		List<Entity> entities = webDom4j.getEntities();
		List<Mapping> mappings = webDom4j.getMappings();

		// 初始化entityMap中的数据
		for (Entity entity : entities) {
			String name = entity.getName();
			String clz = entity.getClz();
			entityMap.put(name, clz);
		}
		// 初始化mappingMap中的信息
		for (Mapping mapping : mappings) {
			String name = mapping.getName();
			List<String> url = mapping.getUrl();
			for (String s : url) {
				mappingMap.put(s, name);
			}
		}
	}

	/**
	 * 通过一个uri, 获取servlet对象
	 * @param uri 一个客户端传过来的uri
	 * @return 返回一个servlet对象
	 */
	public static Servlet getServlet(String uri) {
		String clz = content.getEntityMap().get(content.getMappingMap().get(uri));
		Servlet servlet = null;
		try {
			Class<?> aClass = Class.forName(clz);
			servlet = (Servlet) aClass.newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		return servlet;
	}

	// 测试
	public static void main(String[] args) {
		String url = "/log";
		Servlet servlet = getServlet(url);
		System.out.println(servlet);

		String uri = "/register";
		System.out.println(getServlet(uri));
	}
}
