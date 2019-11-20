package pers.jssd.server;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放servlet-name和url-pattern映射的实体类
 *
 * @author jssd
 * Create 2019-07-21 20:46
 */
public class Mapping {
	private String name;
	private List<String> url;

	@Override
	public String toString() {
		return "Mapping{" +
				"name='" + name + '\'' +
				", url=" + url +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}

	public Mapping(String name, List<String> url) {
		this.name = name;
		this.url = url;
	}

	public Mapping() {
		url = new ArrayList<>();
	}
}
