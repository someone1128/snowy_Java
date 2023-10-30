package vip.xiaonuo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 黄志源大魔王
 * @date 2023/7/12 21:20
 * @project aigc-java-server
 * @company 智影科技
 * @description
 */
@AllArgsConstructor
@Getter
public enum WeChatEnum {

	未扫码("未扫码"),
	已扫码("已扫码"),
	已关注("已关注"),
	取消关注("取消关注"),
	已过期("已过期"),
	;
	String value;

}
