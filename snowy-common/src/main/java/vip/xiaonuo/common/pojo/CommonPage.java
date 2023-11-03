package vip.xiaonuo.common.pojo;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import vip.xiaonuo.common.constants.PageConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共分页对象
 *
 * @author as
 */
@Data
public class CommonPage<T> {
	/**
	 * 页码
	 */
	private Integer page = PageConstants.DEFAULT_PAGE;
	/**
	 * 页容量
	 */
	private Integer limit = PageConstants.DEFAULT_LIMIT;
	private Integer totalPage = 0;
	private Long total = 0L;
	private List<T> list = new ArrayList<>();
	/**
	 * 预留对象
	 */
	private Object reservedObject;

	/**
	 * 将MyBatis Plus 分页结果转化为通用结果
	 */
	public static <T> CommonPage<T> restPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> pageResult) {
		CommonPage<T> result = new CommonPage<>();
		result.setPage(Convert.toInt(pageResult.getCurrent()));
		result.setLimit(Convert.toInt(pageResult.getSize()));
		result.setTotal(pageResult.getTotal());
		result.setTotalPage(Convert.toInt(pageResult.getTotal() / pageResult.getSize() + 1));
		result.setList(pageResult.getRecords());
		return result;
	}

	/**
	 * 将PageHelper分页后的list转为分页信息
	 */
	public static <T> CommonPage<T> restPage(List<T> list) {
		CommonPage<T> result = new CommonPage<T>();
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		result.setTotalPage(pageInfo.getPages());
		result.setPage(pageInfo.getPageNum());
		result.setLimit(pageInfo.getPageSize());
		result.setTotal(pageInfo.getTotal());
		result.setList(pageInfo.getList());
		return result;
	}

	/**
	 * 将SpringData分页后的list转为分页信息
	 */
	public static <T> CommonPage<T> restPage(Page<T> pageInfo) {
		CommonPage<T> result = new CommonPage<T>();
		result.setTotalPage(pageInfo.getTotalPages());
		result.setPage(pageInfo.getNumber());
		result.setLimit(pageInfo.getSize());
		result.setTotal(pageInfo.getTotalElements());
		result.setList(pageInfo.getContent());
		return result;
	}

	/**
	 * 将PageHelper分页后的 PageInfo 转为分页信息
	 *
	 * @return
	 */
	public static <T> CommonPage<T> restPage(PageInfo<T> pageInfo) {
		CommonPage<T> result = new CommonPage<T>();
		result.setTotalPage(pageInfo.getPages());
		result.setPage(pageInfo.getPageNum());
		result.setLimit(pageInfo.getPageSize());
		result.setTotal(pageInfo.getTotal());
		result.setList(pageInfo.getList());
		return result;
	}

	/**
	 * 对象A复制对象B的分页信息 // 多次数据查询导致分页数据异常解决办法
	 */
	public static <T> PageInfo<T> copyPageInfo(com.github.pagehelper.Page originPageInfo, List<T> list) {
		PageInfo<T> pageInfo = new PageInfo<>(list);
		BeanUtils.copyProperties(originPageInfo, pageInfo, "list");
		return pageInfo;
	}

	/**
	 * 对象A复制对象B的分页信息 // 多次数据查询导致分页数据异常解决办法
	 */
	public static <T> PageInfo<T> copyPageInfo(PageInfo<?> originPageInfo, List<T> list) {
		PageInfo<T> pageInfo = new PageInfo<>(list);
		pageInfo.setPages(originPageInfo.getPages());
		pageInfo.setPageNum(originPageInfo.getPageNum());
		pageInfo.setPageSize(originPageInfo.getPageSize());
		pageInfo.setTotal(originPageInfo.getTotal());
		pageInfo.setList(list);
		return pageInfo;
	}
}
