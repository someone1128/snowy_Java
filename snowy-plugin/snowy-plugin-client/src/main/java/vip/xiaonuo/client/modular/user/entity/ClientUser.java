
package vip.xiaonuo.client.modular.user.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import vip.xiaonuo.common.handler.CommonSm4CbcTypeHandler;
import vip.xiaonuo.common.pojo.CommonEntity;

import java.util.Date;

/**
 * C端用户实体
 *
 * @author xuyuxiang
 * @date 2022/4/21 16:13
 **/
@Data
@TableName(value = "client_user", autoResultMap = true)
public class ClientUser extends CommonEntity implements TransPojo {

    @ApiModelProperty(value = "id", position = 1)
    private String id;

    @ApiModelProperty(value = "头像，图片base64", position = 2)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String avatar;

    @ApiModelProperty(value = "签名，图片base64", position = 3)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String signature;

    @ApiModelProperty(value = "账号", position = 4)
    private String account;

    @ApiModelProperty(value = "密码", position = 5)
    private String password;

    @ApiModelProperty(value = "姓名", position = 6)
    private String name;

    @ApiModelProperty(value = "昵称", position = 7)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String nickname;

    @ApiModelProperty(value = "手机", position = 23)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED, typeHandler = CommonSm4CbcTypeHandler.class)
    private String phone;

    @ApiModelProperty(value = "邮箱", position = 24)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String email;

    @ApiModelProperty(value = "注册IP")
    private String registerIp;

    @ApiModelProperty(value = "上次登录ip", position = 30)
    private String lastLoginIp;

    @ApiModelProperty(value = "上次登录地点", position = 31)
    private String lastLoginAddress;

    @ApiModelProperty(value = "上次登录时间", position = 32)
    private Date lastLoginTime;

    @ApiModelProperty(value = "上次登录设备", position = 33)
    private String lastLoginDevice;

    @ApiModelProperty(value = "最新登录ip", position = 34)
    private String latestLoginIp;

    @ApiModelProperty(value = "最新登录地点", position = 35)
    private String latestLoginAddress;

    @ApiModelProperty(value = "最新登录时间", position = 36)
    private Date latestLoginTime;

    @ApiModelProperty(value = "最新登录设备", position = 37)
    private String latestLoginDevice;

    @ApiModelProperty(value = "用户状态", position = 38)
    private String userStatus;

    @ApiModelProperty(value = "排序码", position = 39)
    private Integer sortCode;

    @ApiModelProperty(value = "扩展信息", position = 40)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String extJson;

    @ApiModelProperty(value = "消耗魔力")
    private Integer consumeMagic;

    @ApiModelProperty(value = "剩余魔力")
    private Integer residualMagic;

    @ApiModelProperty("每日签到魔力")
    private Integer dayMagic;

    @ApiModelProperty(value = "邀请码")
    private String invitationCode;

    @ApiModelProperty(value = "会员等级")
    private String memberLevel;

    @ApiModelProperty(value = "过期时间")
    private Date memberExpirationTime;

}
