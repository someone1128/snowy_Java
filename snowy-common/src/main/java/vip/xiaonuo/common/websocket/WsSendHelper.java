package vip.xiaonuo.common.websocket;

import com.alibaba.fastjson2.JSON;
import vip.xiaonuo.common.pojo.CommonResult;

/**
 * @author 黄志源大魔王
 * @date 2023/7/15 22:12
 * @project aigc-java-server
 * @company 智影科技
 * @description Wesbcoekt 消息发送帮助
 */
public class WsSendHelper {

	/**
	 * 微信支付成功
	 *
	 * @param userId
	 */
	public static void sendWxPaySuccess(String userId) {
		WebSocketService.sendInfo(JSON.toJSONString(CommonResult.ok("支付成功")), userId, WebSocketConstant.WxPay);
	}

	public static void sendWxPayFail(String userId) {
		WebSocketService.sendInfo(JSON.toJSONString(CommonResult.error("系统处理异常")), userId, WebSocketConstant.WxPay);
	}

}
