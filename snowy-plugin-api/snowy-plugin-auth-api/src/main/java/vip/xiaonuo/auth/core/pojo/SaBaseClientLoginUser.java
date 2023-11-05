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
package vip.xiaonuo.auth.core.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 基础的C端登录用户对象，可继承此类扩展更多属性
 *
 * @author xuyuxiang
 * @date 2021/12/23 21:49
 */
@Data
public abstract class SaBaseClientLoginUser {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 头像 */
    @ApiModelProperty(value = "头像，图片base64", position = 2)
    private String avatar;

    /** 签名 */
    @ApiModelProperty(value = "签名，图片base64", position = 3)
    private String signature;

    /** 账号 */
    @ApiModelProperty(value = "账号", position = 4)
    private String account;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String name;

    /** 昵称 */
    @ApiModelProperty(value = "昵称", position = 6)
    private String nickname;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 22)
    private String phone;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱", position = 23)
    private String email;

    /** 上次登录ip */
    @ApiModelProperty(value = "上次登录ip", position = 29)
    private String lastLoginIp;

    /** 上次登录地点 */
    @ApiModelProperty(value = "上次登录地点", position = 30)
    private String lastLoginAddress;

    /** 上次登录时间 */
    @ApiModelProperty(value = "上次登录时间", position = 31)
    private Date lastLoginTime;

    /** 上次登录设备 */
    @ApiModelProperty(value = "上次登录设备", position = 32)
    private String lastLoginDevice;

    /** 最新登录ip */
    @ApiModelProperty(value = "最新登录ip", position = 33)
    private String latestLoginIp;

    /** 最新登录地点 */
    @ApiModelProperty(value = "最新登录地点", position = 34)
    private String latestLoginAddress;

    /**
     * 最新登录时间
     */
    @ApiModelProperty(value = "最新登录时间", position = 35)
    private Date latestLoginTime;

    /**
     * 最新登录设备
     */
    @ApiModelProperty(value = "最新登录设备", position = 36)
    private String latestLoginDevice;

    @ApiModelProperty(value = "消耗魔力")
    private String consumeMagic;

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

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态", position = 37)
    private String userStatus;

    /**
     * 排序码
     */
    @ApiModelProperty(value = "排序码", position = 38)
    private Integer sortCode;

    /**
     * 扩展信息
     */
    @ApiModelProperty(value = "扩展信息", position = 39)
    private String extJson;

    /** 按钮码集合 */
    @ApiModelProperty(value = "按钮码集合", position = 40)
    private List<String> buttonCodeList;

    /** 移动端按钮码集合 */
    @ApiModelProperty(value = "移动端按钮码集合", position = 41)
    private List<String> mobileButtonCodeList;

    /** 权限码集合 */
    @ApiModelProperty(value = "权限码集合", position = 42, hidden = true)
    private List<String> permissionCodeList;

    /** 角色码集合 */
    @ApiModelProperty(value = "角色码集合", position = 43, hidden = true)
    private List<String> roleCodeList;

    /**
     * 数据范围集合
     */
    @ApiModelProperty(value = "数据范围集合", position = 44, hidden = true)
    private List<DataScope> dataScopeList;

    /**
     * 用户密码hash值
     */
    @ApiModelProperty(value = "用户密码hash值", position = 45)
    private String password;

    @ApiModelProperty(value = "今日是否已签到")
    private Boolean hasSignedIn;

    /**
     * 是否可登录，由继承类实现
     */
    public abstract Boolean getEnabled();


    /**
     * 数据范围类
     *
     * @author xuyuxiang
     * @date 2022/8/15 13:57
     **/
    @Data
    public static class DataScope {

        /** API接口 */
        @ApiModelProperty(value = "API接口", position = 1)
        private String apiUrl;

        /** 数据范围 */
        @ApiModelProperty(value = "数据范围", position = 2)
        private List<String> dataScope;

    }
}

