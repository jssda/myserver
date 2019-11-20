package pers.jssd.server;

import pers.jssd.util.CloseUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * 响应对象
 * @author jssd
 * Create 2019-07-21 20:42
 */
public class Response {
	/**
	 * 相应状态码
	 */
	private int code;

	/**
	 * 输出时候的流
	 */
	private BufferedWriter bw;

	/**
	 * 相应头信息
	 */
	private StringBuilder headInfo;

	/**
	 * 响应正文
	 */
	private StringBuilder content;

	/**
	 * 响应正文长度
	 */
	private int length;

	/**
	 * 空格常量
	 */
	private static final String BLANK = " ";
	/**
	 * 回车常量
	 */
	private static final String CRLF = "\r\n";

	public Response() {
		code = 200;
		headInfo = new StringBuilder();
		content = new StringBuilder();
	}

	public Response(OutputStream os) {
		this();
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}

	/**
	 * 创建响应头信息
	 *
	 * @param code 响应状态码
	 */
	private void createHeadInfo(int code) {
		this.code = code;
		headInfo.append("HTTP/1.1").append(BLANK);
		headInfo.append(code).append(BLANK);
		switch (code) {
			case 200:
				headInfo.append("OK");
				break;
			case 500:
				headInfo.append("SERVER ERROR");
				break;
			default:
				headInfo.append("NOT FOUND");
		}
		headInfo.append(CRLF);
		headInfo.append("content-type: text/html; charset=UTF-8").append(CRLF);
		headInfo.append("content-length: ").append(length).append(CRLF);
		headInfo.append(CRLF);
	}

	/**
	 * 输出不带换行的响应信息到响应内容
	 * @param info 输出信息
	 * @return 返回此对象
	 */
	public Response print(String info) {
		content.append(info);
		length += info.getBytes(StandardCharsets.UTF_8).length;
		return this;
	}

	/**
	 * 输出带换行的信息到响应内容
	 * @param info 输出信息
	 * @return 返回此对象
	 */
	public Response println(String info) {
		content.append(info).append(CRLF);
		length += (info + CRLF).getBytes(StandardCharsets.UTF_8).length;
		return this;
	}

	/**
	 * 将信息推送到客户端
	 * @param code 响应状态码
	 */
	public void pushToClient(int code) {
		if (headInfo == null) {
			code = 500;
		}
		try {
			createHeadInfo(code);
			bw.write(headInfo.toString());
			bw.write(content.toString());
			bw.flush();
			CloseUtil.close(bw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
