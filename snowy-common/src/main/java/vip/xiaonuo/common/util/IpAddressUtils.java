package vip.xiaonuo.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @Description: ip记录
 * @Author: Naccl
 * @Date: 2020-08-18
 */
@Slf4j
@Component
public class IpAddressUtils {
	private static Searcher searcher;

	public static String getIp() {
		return getIp(RequestUtils.getHttpServletRequest());
	}

	//getIpAddress

	/**
	 * 在Nginx等代理之后获取用户真实IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIp(final HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		try {
			String ipAddress = request.getHeader("x-forwarded-for");
			if (StrUtil.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (StrUtil.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StrUtil.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StrUtil.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StrUtil.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (final UnknownHostException e) {
						log.error("获取用户真实IP地址失败!", e);
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
			return ipAddress;
		} catch (final Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static boolean isIpaddress(final String ip) {
		return ip == null || ip.length() == 0;
	}

	/**
	 * 获取IP地址
	 *
	 * @return 本地IP地址
	 */
	public static String getHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		}
		return "127.0.0.1";
	}

	/**
	 * 获取主机名
	 *
	 * @return 本地主机名
	 */
	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		}
		return "未知";
	}

	public static void main(String[] args) {
		System.out.println(getCityInfo("27.148.112.165"));
	}

	/**
	 * 根据ip从 ip2region.db 中获取地理位置
	 *
	 * @param ip
	 * @return
	 */
	public static String getCityInfo(final String ip) {
		try {
			return searcher.search(ip);
		} catch (final Exception e) {
			return null;
		}
	}

	public static String getCityInfo() {
		try {
			return searcher.search(getIp());
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * 在服务启动时加载 ip2region.db 到内存中
	 * 解决打包jar后找不到 ip2region.db 的问题
	 *
	 * @throws Exception 出现异常应该直接抛出终止程序启动，避免后续invoke时出现更多错误
	 */
	@PostConstruct
	private static void initIp2regionResource() {
		try {
			final InputStream inputStream = new ClassPathResource("/ipdb/ip2region.xdb").getInputStream();
			final byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
			// 创建一个完全基于内存的查询对象
			searcher = Searcher.newWithBuffer(dbBinStr);
		} catch (final Exception e) {
			LogPrintUtils.info(log, "failed to create content cached searcher: %s\n", e);
		}
	}

	/**
	 * 获取一个随机IP
	 */
	public static String getRandomIp() {

		// 指定 IP 范围
		int[][] range = {
				{607649792, 608174079}, // 36.56.0.0-36.63.255.255
				{1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
				{1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
				{2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
				{2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
				{-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
				{-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
				{-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
				{-770113536, -768606209}, // 210.25.0.0-210.47.255.255
				{-569376768, -564133889}, // 222.16.0.0-222.95.255.255
		};

		Random random = new Random();
		int index = random.nextInt(10);
		String ip = num2ip(range[index][0] + random.nextInt(range[index][1] - range[index][0]));
		return ip;
	}

	/**
	 * 将十进制转换成IP地址
	 *
	 * @param ip
	 * @return
	 */
	public static String num2ip(int ip) {
		int[] b = new int[4];
		b[0] = (ip >> 24) & 0xff;
		b[1] = (ip >> 16) & 0xff;
		b[2] = (ip >> 8) & 0xff;
		b[3] = ip & 0xff;
		// 拼接 IP
		String x = b[0] + "." + b[1] + "." + b[2] + "." + b[3];
		return x;
	}
}