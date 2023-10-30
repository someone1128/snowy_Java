package vip.xiaonuo.common.constants;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author 黄志源大魔王
 * @date 2023/5/7 14:53
 * @project snowy-master
 * @company 智影科技
 * @description
 */
public class RedisKeyUtils {
	/**
	 * http://flagstudio.baai.ac.cn/document#23c9a48f3c54bd309851fdf466a212fc 的 token
	 */
	public static final String FLAG_STUDIO_TOKEN = "FLAG_STUDIO_TOKEN";
	/**
	 * 聊天锁
	 */
	private static final String CHAT_LOCK = "CHAT_LOCK_{}";

	/**
	 * 限流
	 */
	private static final String ACCESS_LIMIT = "CHAT_LOCK_{}_{}_{}"; //

	/**
	 * 聊天加锁Key
	 *
	 * @param userId
	 * @return
	 */
	public static String getChatLock(String userId) {
		return StrUtil.format(CHAT_LOCK, userId);
	}

	/**
	 * 获取限流 Key
	 *
	 * @param ip
	 * @param method
	 * @param uri
	 * @param userUuid
	 * @return
	 */
	public static String getAccessLimitKey(String ip, String method, String uri, String userUuid) {
		if (StrUtil.isBlank(userUuid)) {
			return StrUtil.format(ACCESS_LIMIT, uri, method, ip);
		}
		return StrUtil.format(ACCESS_LIMIT + "_{}", uri, method, ip, userUuid);
	}

	// 生成点赞记录的 Redis Key
	public static String getDialogueLikedKey(String likedUserId, String dialogueId) {
		return "dialogue_liked:" + likedUserId + ":" + dialogueId;
	}

	// 生成收藏记录的 Redis Key
	public static String getDialogueFavoriteKey(String favoriteUserId, String dialogueId) {
		return "dialogue_favoriteUserId:" + favoriteUserId + ":" + dialogueId;
	}

	public static String getDialogueReadKey(String favoredUserId, String dialogueId) {
		return "dialogue_read:" + favoredUserId + ":" + dialogueId;
	}

	public static String getGpt16kAuthKey() {
		return "Gpt16kAuth";
	}

	/**
	 * 今日是否已签到
	 *
	 * @return
	 */
	public static String getHasSignedIn(String userId) {
		return CharSequenceUtil.format("HasSignedIn_{}_{}", DateUtil.today(), userId);
	}

	/**
	 * 场景值
	 *
	 * @param sceneId
	 * @return
	 */
	public static String getWxQrCode(String sceneId) {
		return CharSequenceUtil.format("WxQrCode_{}", sceneId);
	}


	public static String getStreamGptMessage(String userId) {
		return CharSequenceUtil.format("stream_gpt_message_{}", userId);
	}

	public static String getMemberDiscounts(String userId) {
		return StrUtil.format("MemberDiscounts_{}", userId);
	}

	public static String getRecentChatDialog(String userId) {
		return StrUtil.format("RecentChatDialog_{}", userId);
	}
}
