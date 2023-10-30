import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/dev/monitor/${url}`, ...arg)
/**
 * 监控
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 获取服务器监控信息
	monitorServerInfo(data) {
		return request('serverInfo', data, 'get')
	},
	// 获取服务器网络情况
	monitorNetworkInfo(data) {
		return request('networkInfo', data, 'get')
	}
}
