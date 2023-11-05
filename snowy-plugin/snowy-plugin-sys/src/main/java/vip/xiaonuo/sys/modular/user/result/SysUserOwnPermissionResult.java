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
package vip.xiaonuo.sys.modular.user.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户拥有的权限结果
 *
 * @author xuyuxiang
 * @date 2022/7/27 15:08
 **/
@Data
public class SysUserOwnPermissionResult {

    /** 用户id */
    @ApiModelProperty(value = "角色id", position = 1)
    private String id;

    /** 已授权权限信息 */
    @ApiModelProperty(value = "已授权权限信息", position = 2)
    private List<SysUserOwnPermission> grantInfoList;

    /**
     * 用户拥有资源类
     *
     * @author xuyuxiang
     * @date 2022/4/28 23:19
     */
    @Getter
    @Setter
    public static class SysUserOwnPermission {

        /** 菜单id */
        @ApiModelProperty(value = "接口地址", position = 1)
        private String apiUrl;

        /** 数据范围分类 */
        @ApiModelProperty(value = "数据范围分类", position = 1)
        private String scopeCategory;

        /** 自定义范围组织id集合 */
        @ApiModelProperty(value = "自定义范围组织id集合", position = 2)
        private List<String> scopeDefineOrgIdList;

    }
}
