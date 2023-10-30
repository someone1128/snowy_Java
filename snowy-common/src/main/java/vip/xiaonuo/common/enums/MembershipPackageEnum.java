
package vip.xiaonuo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员套餐枚举
 *
 * @author 黄志源大魔王
 * @date 2023/07/10 13:05
 **/
@Getter
public class MembershipPackageEnum {

	@AllArgsConstructor
	@Getter
	public enum Type {
		充值会员("充值会员"),
		充值魔力("充值魔力"),
		;

		private String value;
	}

	@AllArgsConstructor
	@Getter
	public enum Level {
		普通用户("普通用户", 1),
		普通会员("普通会员", 2),
		超级会员("超级会员", 3),
		;

		private String value;

		private Integer intVal;

		public static Level getByValue(String value) {
			for (Level level : values()) {
				if (level.getValue().equals(value)) {
					return level;
				}
			}
			return Level.普通用户;
		}
	}

}
