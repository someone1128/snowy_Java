package vip.xiaonuo.auth.core.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 黄志源大魔王
 * @date 2023/5/4 15:58
 * @project snowy-master
 * @company 智影科技
 * @description
 */
@Data
public class SaBaseRegisterUser {

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号", required = true, position = 1)
	private String phone;

	/**
	 * 验证码
	 */
	@ApiModelProperty(value = "验证码", required = true, position = 2)
	private String validCode;

	@ApiModelProperty(value = "邀请码")
	private String invitationCode;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "密码")
	private String password;

	public SaBaseRegisterUser(String phone, String validCode) {
		this.phone = phone;
		this.validCode = validCode;
	}

	public SaBaseRegisterUser(String validCode, String invitationCode, String nickname, String password) {
		this.validCode = validCode;
		this.invitationCode = invitationCode;
		this.nickname = nickname;
		this.password = password;
	}

	public SaBaseRegisterUser(String phone, String validCode, String invitationCode, String nickname, String password) {
		this.phone = phone;
		this.validCode = validCode;
		this.invitationCode = invitationCode;
		this.nickname = nickname;
		this.password = password;
	}

}
