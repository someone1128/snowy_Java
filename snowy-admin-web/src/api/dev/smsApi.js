import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/dev/sms/${url}`, ...arg)
/**
 * 短信
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 获取短信分页
	smsPage(data) {
		return request('page', data, 'get')
	},
	// 发送短信——阿里云
	smsSendAliyun(data) {
		return request('sendAliyun', data)
	},
	// 发送短信——腾讯云
	smsSendTencent(data) {
		return request('sendTencent', data)
	},
	// 删除短信
	smsDelete(data) {
		return request('delete', data)
	},
	// 获取短信详情
	smsDetail(data) {
		return request('detail', data, 'get')
	}
}
