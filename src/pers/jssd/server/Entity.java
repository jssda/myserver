package pers.jssd.server;

/**
 * 存放web.xml中配置的servlet-name和servlet-class信息实体类
 *
 * @author jssd
 * Create 2019-07-21 20:42
 */
public class Entity {
	private String name;
	private String clz;

	@Override
	public String toString() {
		return "Entity{" +
				"name='" + name + '\'' +
				", clz='" + clz + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClz() {
		return clz;
	}

	public void setClz(String clz) {
		this.clz = clz;
	}

	public Entity(String name, String clz) {
		this.name = name;
		this.clz = clz;
	}

	public Entity() {
	}
}

