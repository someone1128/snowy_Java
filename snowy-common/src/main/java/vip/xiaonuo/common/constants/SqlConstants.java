package vip.xiaonuo.common.constants;

/**
 * @author 黄志源大魔王
 * @date 2022/9/23 16:12
 * @project manager_system_server
 * @company 弘瑞创享
 * @description
 */
public class SqlConstants {

	/**
	 * 查询一条
	 */
	public static final String LIMIT_1 = " limit 1";

	public static String limit(int limit) {
		return " limit " + limit;
	}
}
