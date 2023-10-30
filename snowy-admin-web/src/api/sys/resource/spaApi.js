import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/sys/spa/${url}`, ...arg)
/**
 * 单页
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 获取菜单分页
	spaPage(data) {
		return request('page', data, 'get')
	},
	// 提交表单 edit为true时为编辑，默认为新增
	submitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除菜单
	spaDelete(data) {
		return request('delete', data)
	},
	// 获取菜单详情
	spaDetail(data) {
		return request('detail', data, 'get')
	}
}
