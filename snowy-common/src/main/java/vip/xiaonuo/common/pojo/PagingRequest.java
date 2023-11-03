package vip.xiaonuo.common.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import vip.xiaonuo.common.constants.PageConstants;

/**
 * 分页公共请求对象
 *
 * @author hzy
 */
@Data
@NoArgsConstructor
public class PagingRequest {

	@ApiModelProperty(value = "页码", example = PageConstants.DEFAULT_PAGE + "")
	private int page = PageConstants.DEFAULT_PAGE;

	@ApiModelProperty(value = "每页数量", example = PageConstants.DEFAULT_LIMIT + "")
	private int limit = PageConstants.DEFAULT_LIMIT;

	public PagingRequest(int page, int limit) {
		this.page = page;
		this.limit = limit;
	}

}
