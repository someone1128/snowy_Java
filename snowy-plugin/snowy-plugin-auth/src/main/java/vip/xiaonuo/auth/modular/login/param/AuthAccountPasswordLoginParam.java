/*
 * Copyright [2022] []
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为
 */
package vip.xiaonuo.auth.modular.login.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 账号密码登录参数
 *
 * @author xuyuxiang
 * @date 2022/7/7 16:46
 **/
@Data
public class AuthAccountPasswordLoginParam {

    /** 账号 */
    @ApiModelProperty(value = "账号", required = true, position = 1)
    @NotBlank(message = "账号不能为空")
    private String account;

    /** 密码 */
    @ApiModelProperty(value = "密码", required = true, position = 2)
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 设备 */
    @ApiModelProperty(value = "设备", position = 3)
    private String device;

    /** 验证码 */
    @ApiModelProperty(value = "验证码", position = 4)
    private String validCode;

    /** 验证码请求号 */
    @ApiModelProperty(value = "验证码请求号", position = 5)
    private String validCodeReqNo;
}
