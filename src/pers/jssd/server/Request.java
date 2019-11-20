package pers.jssd.server;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求对象
 *
 * @author jssd
 * Create 2019-07-21 20:41
 */
public class Request {

	/**
	 *  取得请求的第一行
	 */
	private String firstLine;

	/**
	 *  请求的servlet路径
	 */
	private String uri;

	/**
	 * 请求的方式
	 */
	private String method;

	/**
	 * 从uri中取得的parameterName和parameterValues, 封装成一个map
	 * key为parameterName, value为一个存放parameterValue的list容器
	 */
	private Map<String, List<String>> parameterMap;

	/**
	 * 客户端的输入流
	 */
	private BufferedReader br;

	/**
	 * 常量空格
	 */
	private static final String BLANK = " ";
	/**
	 * 常量换行
	 */
	private static final String CRLF = "\r\n";

	public Request(){
		method = "";
		firstLine = "";
		parameterMap = new HashMap<>();
	}

	public Request(InputStream is) {
		this();
		this.br = new BufferedReader(new InputStreamReader(is));
		String uri = getUrl();
		parseParameter(uri);
	}

	/**
	 * 取得请求的请求头第一行信息
	 * @return 返回请求的第一行
	 */
	public String getUrl() {
		StringBuilder sb = new StringBuilder();
		String str;
		try {
			while ((str = br.readLine()).length() != 0) {
				sb.append(str).append("\r\n");
			}
//			System.out.println(sb.toString());
			firstLine = sb.toString().substring(0, sb.toString().indexOf(CRLF));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return decode(firstLine);
	}

	/**
	 * 用于字符串解码
	 * @param str 需要解码的字符串
	 * @return 返回解码完毕的字符串
	 */
	private String decode(String str) {
		String decodeStr = str;
		try {
			decodeStr =  URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodeStr;
	}

	/**
	 * 解析参数列表
	 * @param url 解析的url
	 */
	private void parseParameter(String url) {
		method = url.substring(0, url.indexOf(BLANK));
		// /log?name=王京京&pwd=123456&hobby=basketball&hobby=tennis
		String parStr = url.substring(url.indexOf(BLANK), url.indexOf("HTTP/")).trim();
		uri = parStr.split("\\?")[0];
		parStr = parStr.split("\\?")[1];
		String[] keyValues = parStr.split("&");
		for (String keyValue : keyValues) {
			String key = keyValue.split("=")[0];
			String value = keyValue.split("=")[1];
			if (parameterMap.containsKey(key)) {
				List<String> list = parameterMap.get(key);
				list.add(value);
			} else {
				List<String> list = new ArrayList<>();
				list.add(value);
				parameterMap.put(key, list);
			}
		}
	}

	/**
	 * 通过参数的名字取得一个参数
	 * @param name 参数的名字
	 * @return 返回取得的参数
	 */
	public String getParameterValue(String name) {
		String value;
		value = parameterMap.get(name).get(0);
		return value;
	}

	/**
	 * 通过参数的名字, 取得所有对应的参数
	 * @param name 参数的名字
	 * @return String[] 返回参数的数组
	 */
	public String[] getParameterValues(String name) {
		List<String> strings = parameterMap.get(name);
		return strings == null ? null : strings.toArray(new String[0]);
	}

	public String getUri() {
		return uri;
	}
}
