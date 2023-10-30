import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/sys/index/${url}`, ...arg)
/**
 * 系统首页控制器
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 添加当前用户日程
	indexScheduleAdd(data) {
		return request('schedule/add', data)
	},
	// 删除日程
	indexScheduleDeleteSchedule(data) {
		return request('schedule/deleteSchedule', data)
	},
	// 获取当前用户日程列表
	indexScheduleList(data) {
		return request('schedule/list', data, 'get')
	},
	// 获取当前用户站内信列表
	indexMessageList(data) {
		return request('message/list', data, 'get')
	},
	// 获取站内信详情
	indexMessageDetail(data) {
		return request('message/detail', data, 'get')
	},
	//站内信全部标记已读
	indexMessageAllMarkRead(data) {
		return request('message/allMessageMarkRead', data)
	},
	// 获取当前用户访问日志列表
	indexVisLogList(data) {
		return request('visLog/list', data, 'get')
	},
	// 获取当前用户操作日志列表
	indexOpLogList(data) {
		return request('opLog/list', data, 'get')
	}
}
