import {moduleRequest} from '@/utils/request'

const request = moduleRequest(`/auth/b/`)
/**
 * 登录
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// B端获取图片验证码
	getPicCaptcha(data) {
		return request('getPicCaptcha', data, 'get')
	},
	// B端获取手机验证码
	getPhoneValidCode(data) {
		return request('getPhoneValidCode', data, 'get')
	},
	// B端账号密码登录
	login(data) {
		return request('doLogin', data, 'post', false)
	},
	// B端手机验证码登录
	loginByPhone(data) {
		return request('doLoginByPhone', data, 'post', false)
	},
	// 退出
	logout(data) {
		return request('doLogout', data, 'get')
	},
	// 获取用户信息
	getLoginUser(data) {
		return request('getLoginUser', data, 'get')
	}
}
