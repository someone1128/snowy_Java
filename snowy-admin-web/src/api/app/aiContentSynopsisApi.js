import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/app/aicontentsynopsis/` + url, ...arg)

/**
 * AI 内容摘要Api接口管理器
 *
 * @author 黄志源大魔王
 * @date  2023/11/05 15:44
 **/
export default {
	// 获取AI 内容摘要分页
	aiContentSynopsisPage(data) {
		return request('page', data, 'get')
	},
	// 提交AI 内容摘要表单 edit为true时为编辑，默认为新增
	aiContentSynopsisSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除AI 内容摘要
	aiContentSynopsisDelete(data) {
		return request('delete', data)
	},
	// 获取AI 内容摘要详情
	aiContentSynopsisDetail(data) {
		return request('detail', data, 'get')
	}
}
