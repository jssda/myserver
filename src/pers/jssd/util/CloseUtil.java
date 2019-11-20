package pers.jssd.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author jssd
 * Create 2019-07-21 20:39
 */
public class CloseUtil {
	public static void close(Closeable... closeables) {
		for (Closeable closeable : closeables) {
			if (closeable != null) {
				try {
					closeable.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
