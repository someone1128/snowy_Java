package vip.xiaonuo.auth.modular.login.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 黄志源大魔王
 * @date 2023/5/9 15:46
 * @project snowy-master
 * @company 智影科技
 * @description 验证码传参
 */
@Data
public class ValidCodeParam {

	/**
	 * 验证码
	 */
	@ApiModelProperty(value = "验证码", required = true, position = 2)
	@NotBlank(message = "验证码不能为空")
	private String validCode;

	/**
	 * 验证码请求号
	 */
	@ApiModelProperty(value = "验证码请求号", required = true, position = 3)
	@NotBlank(message = "验证码请求号不能为空")
	private String validCodeReqNo;

}
