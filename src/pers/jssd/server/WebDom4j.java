package pers.jssd.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.List;

/**
 * XML 解析头像
 *
 * @author jssd
 * Create 2019-07-21 20:41
 */
public class WebDom4j {

	/**
	 * Entity 容器, 存放从Web.xml中解析出来的Entity对象
	 */
	private List<Entity> entities;
	/**
	 * Mapping容器, 存放从Web.xml中解析出来的Mapping对象
	 */
	private List<Mapping> mappings;

	public WebDom4j() {
		entities = new ArrayList<>();
		mappings = new ArrayList<>();
	}

	/**
	 * 获取web.xml的文档
	 *
	 * @return web.xml 的dom文档
	 */
	public Document getDocument() {
		SAXReader saxReader = new SAXReader();
		Document read = null;
		try {
			read = saxReader.read("src/pers/jssd/WEB_INFO/web.xml");
			return read;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从文档中解析出Entity和Mapping并存放在容器中
	 *
	 * @param document 要解析的Document文档
	 */
	public void parseDocument(Document document) {
		// 获取根节点
		Element rootElement = document.getRootElement();
		// 从根节点获取servlet节点, 并解析存放在Entity容器中
		List servletList = rootElement.elements("servlet");
		for (Object o : servletList) {
			Entity entity = new Entity();
			Element servletName = ((Element) o).element("servlet-name");
			entity.setName(servletName.getTextTrim());
			Element servletClass = ((Element) o).element("servlet-class");
			entity.setClz(servletClass.getTextTrim());
			entities.add(entity);
		}
		// 从根节点获取mapping节点, 并解析存放在Mapping容器中
		List mappingList = rootElement.elements("mapping");
		for (Object o : mappingList) {
			Mapping mapping = new Mapping();
			Element servletName = ((Element) o).element("servlet-name");
			mapping.setName(servletName.getTextTrim());
			List urlList = ((Element) o).elements("url-pattern");
			for (Object url : urlList) {
				mapping.getUrl().add(((Element) url).getTextTrim());
			}
			mappings.add(mapping);
		}
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

	// 测试
	public static void main(String[] args) {
		WebDom4j webDom4j = new WebDom4j();
		webDom4j.parseDocument(webDom4j.getDocument());
		List<Entity> entities = webDom4j.getEntities();
		for (Entity entity : entities) {
			System.out.println(entity);
		}
		List<Mapping> mappings = webDom4j.getMappings();
		for (Mapping mapping : mappings) {
			System.out.println(mapping);
		}
	}

}
