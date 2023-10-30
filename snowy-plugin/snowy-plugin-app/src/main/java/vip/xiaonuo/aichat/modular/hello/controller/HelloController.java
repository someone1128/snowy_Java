package vip.xiaonuo.aichat.modular.hello.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.pojo.CommonResult;

/**
 * @author 黄志源大魔王
 * @date 2023-10-30 15:43
 * @project snowy
 * @company 智影科技
 * @description
 */
@Component
@RestController
public class HelloController {

	public HelloController() {
		System.out.println("HelloController");
	}

	@ApiOperation("你好，世界")
	@GetMapping("/hello")
	public CommonResult hello() {
		return CommonResult.data("helloWorld");
	}

}
