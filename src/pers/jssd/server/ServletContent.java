package pers.jssd.server;

import java.util.HashMap;
import java.util.Map;

/**
 * servlet上下文类, 存放Entity的映射map和
 *
 * @author jssd
 * Create 2019-07-21 21:59
 */
public class ServletContent {
	/**
	 * 存放entity信息的map
	 * key为servlet-name
	 * value为servlet-class
	 */
	private Map<String, String> entityMap;
	/**
	 * 存放mapping信息的map
	 * key为url-pattern
	 * value为servlet-name
	 */
	private Map<String, String> mappingMap;

	public ServletContent() {
		entityMap = new HashMap<>();
		mappingMap = new HashMap<>();
	}

	public ServletContent(Map<String, String> entityMap, Map<String, String> mappingMap) {
		this.entityMap = entityMap;
		this.mappingMap = mappingMap;
	}

	public Map<String, String> getEntityMap() {
		return entityMap;
	}

	public Map<String, String> getMappingMap() {
		return mappingMap;
	}
}
