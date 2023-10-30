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
package vip.xiaonuo.sys.modular.index.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 日程列表结果
 *
 * @author xuyuxiang
 * @date 2022/9/2 11:21
 */
@Getter
@Setter
public class SysIndexScheduleListResult {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 2)
    private String scheduleUserId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 3)
    private String scheduleUserName;

    /** 日程日期 */
    @ApiModelProperty(value = "日程日期", position = 4)
    private String scheduleDate;

    /** 日程时间 */
    @ApiModelProperty(value = "日程时间", position = 5)
    private String scheduleTime;

    /** 日程内容 */
    @ApiModelProperty(value = "日程内容", position = 6)
    private String scheduleContent;
}
