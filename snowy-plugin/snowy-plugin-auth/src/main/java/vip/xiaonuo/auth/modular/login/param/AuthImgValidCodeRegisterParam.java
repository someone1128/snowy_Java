package vip.xiaonuo.auth.modular.login.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.xiaonuo.auth.core.pojo.SaBaseRegisterUser;

import javax.validation.constraints.NotBlank;

/**
 * 手机验证码登录参数
 *
 * @author xuyuxiang
 * @date 2022/7/7 16:46
 **/
@Data
public class AuthImgValidCodeRegisterParam extends ValidCodeParam {

	/**
	 * 设备
	 */
	@ApiModelProperty(value = "设备")
	private String device;

	@ApiModelProperty(value = "邀请码")
	private String invitationCode;

	@NotBlank(message = "昵称不能为空")
	@ApiModelProperty(value = "昵称")
	private String nickname;

	@NotBlank(message = "密码不能为空")
	@ApiModelProperty(value = "密码")
	private String password;

	public SaBaseRegisterUser buildSaBaseRegisterUser() {
		SaBaseRegisterUser saBaseRegisterUser = new SaBaseRegisterUser(super.getValidCode(), invitationCode, nickname, password);
		return saBaseRegisterUser;
	}

}
