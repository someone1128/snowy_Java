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
package vip.xiaonuo.biz.modular.dict.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.dict.entity.BizDict;
import vip.xiaonuo.biz.modular.dict.param.BizDictEditParam;
import vip.xiaonuo.biz.modular.dict.param.BizDictPageParam;
import vip.xiaonuo.biz.modular.dict.service.BizDictService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 业务字典控制器
 *
 * @author xuyuxiang
 * @date 2022/6/21 14:58
 **/
@Api(tags = "业务字典控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 4)
@RestController
@Validated
public class BizDictController {

    @Resource
    private BizDictService bizDictService;

    /**
     * 获取业务字典分页
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务字典分页")
    @SaCheckPermission("/biz/dict/page")
    @GetMapping("/biz/dict/page")
    public CommonResult<Page<BizDict>> page(BizDictPageParam bizDictPageParam) {
        return CommonResult.data(bizDictService.page(bizDictPageParam));
    }

    /**
     * 获取业务字典树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("获取业务字典树")
    @SaCheckPermission("/biz/dict/tree")
    @GetMapping("/biz/dict/tree")
    public CommonResult<List<Tree<String>>> tree() {
        return CommonResult.data(bizDictService.tree());
    }

    /**
     * 获取所有字典树
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("获取所有字典树")
    @GetMapping("/biz/dict/treeAll")
    public CommonResult<List<Tree<String>>> treeAll() {
        return CommonResult.data(bizDictService.treeAll());
    }

    /**
     * 编辑业务字典
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("编辑业务字典")
    @CommonLog("编辑业务字典")
    @SaCheckPermission("/biz/dict/edit")
    @PostMapping("/biz/dict/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizDictEditParam bizDictEditParam) {
        bizDictService.edit(bizDictEditParam);
        return CommonResult.ok();
    }
}
