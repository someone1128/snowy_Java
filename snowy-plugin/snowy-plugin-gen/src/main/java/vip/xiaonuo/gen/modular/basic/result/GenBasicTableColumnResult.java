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
package vip.xiaonuo.gen.modular.basic.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库表字段结果
 *
 * @author xuyuxiang
 * @date 2022/7/19 19:06
 **/
@Getter
@Setter
public class GenBasicTableColumnResult {

    /** 字段名称 */
    @ApiModelProperty(value = "字段名称", position = 1)
    private String columnName;

    /** 字段类型 */
    @ApiModelProperty(value = "字段类型", position = 2)
    private String typeName;

    /** 字段注释 */
    @ApiModelProperty(value = "字段注释", position = 3)
    private String columnRemark;
}
