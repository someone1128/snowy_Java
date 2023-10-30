package vip.xiaonuo.common.util;

import vip.xiaonuo.common.exception.BusinessException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 黄志源大魔王
 * @date 2023/5/5 21:36
 * @project snowy-master
 * @company 智影科技
 * @description
 */
public class EmojiUtils {

	/**
	 * @param str 待转换字符串
	 * @return 转换后字符串
	 * @throws UnsupportedEncodingException
	 * @Description emoji表情转换
	 */
	public static String emojiConvertToUtf(String str) {
		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(
						sb,
						"[[" + URLEncoder.encode(matcher.group(1),
								"UTF-8") + "]]");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * @param str 转换后的字符串
	 * @return 转换前的字符串
	 * @throws UnsupportedEncodingException
	 * @Description 还原emoji表情的字符串
	 */
	public static String utfemojiRecovery(String str) {
		String patternString = "\\[\\[(.*?)\\]\\]";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();
		try {
			while (matcher.find()) {
				String group1 = matcher.group(1);
				if (group1 != null) {
					matcher.appendReplacement(sb, URLDecoder.decode(group1, "UTF-8"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("表情包转换出现异常");
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
