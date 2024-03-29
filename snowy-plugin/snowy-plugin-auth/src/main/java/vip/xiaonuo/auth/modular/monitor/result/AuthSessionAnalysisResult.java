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
package vip.xiaonuo.auth.modular.monitor.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会话统计结果
 *
 * @author xuyuxiang
 * @date 2022/7/19 9:29
 **/
@Data
public class AuthSessionAnalysisResult {

    /** 当前会话总数量 */
    @ApiModelProperty(value = "当前会话总数量", position = 1)
    private String currentSessionTotalCount;

    /** 最大签发令牌数 */
    @ApiModelProperty(value = "最大签发令牌数", position = 2)
    private String maxTokenCount;

    /** 最近1小时会话数 */
    @ApiModelProperty(value = "最近1小时会话数", position = 3)
    private String oneHourNewlyAdded;

    /** BC端会话比例 */
    @ApiModelProperty(value = "BC端会话比例", position = 4)
    private String proportionOfBAndC;
}
