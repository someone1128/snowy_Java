package vip.xiaonuo.common.pojo;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 黄志源大魔王
 * @date 2023/6/26 13:38
 * @project aigc-java-server
 * @company 智影科技
 * @description
 */
public class CloseableUtils {

	/**
	 * 关闭流
	 *
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
