import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/sys/userCenter/${url}`, ...arg)
/**
 * 用户个人控制器
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 获取图片验证码
	userGetPicCaptcha(data) {
		return request('getPicCaptcha', data, 'get')
	},
	// 找回密码获取手机验证码
	userFindPasswordGetPhoneValidCode(data) {
		return request('findPasswordGetPhoneValidCode', data, 'get')
	},
	// 找回密码获取邮箱验证码
	userFindPasswordGetEmailValidCode(data) {
		return request('findPasswordGetEmailValidCode', data, 'get')
	},
	// 通过手机号找回用户密码
	userFindPasswordByPhone(data) {
		return request('findPasswordByPhone', data)
	},
	// 通过邮箱找回用户密码
	userFindPasswordByEmail(data) {
		return request('findPasswordByEmail', data)
	},
	// 修改用户密码
	userUpdatePassword(data) {
		return request('updatePassword', data)
	},
	// 修改用户头像
	userUpdateAvatar(data) {
		return request('updateAvatar', data)
	},
	// 修改用户签名图片
	userUpdateSignature(data) {
		return request('updateSignature', data)
	},
	// 获取登录用户的菜单
	userLoginMenu(data) {
		return request('loginMenu', data, 'get')
	},
	// 获取登录用户组织树
	userLoginOrgTree(data) {
		return request('loginOrgTree', data, 'get')
	},
	// 获取登录用户的职位信息
	userLoginPositionInfo(data) {
		return request('loginPositionInfo', data, 'get')
	},
	// 编辑个人信息
	userUpdateUserInfo(data) {
		return request('updateUserInfo', data)
	},
	// 编辑个人工作台
	userUpdateUserWorkbench(data) {
		return request('updateUserWorkbench', data)
	},
	// 获取登录用户的工作台
	userLoginWorkbench(data) {
		return request('loginWorkbench', data, 'get')
	},
	// 获取登录用户的站内信分页
	userLoginUnreadMessagePage(data) {
		return request('loginUnreadMessagePage', data, 'get')
	},
	// 读取登录用户站内信详情
	userLoginUnreadMessageDetail(data) {
		return request('loginUnreadMessageDetail', data, 'get')
	},
	// 根据id集合获取组织集合
	userCenterGetOrgListByIdList(data) {
		return request('getOrgListByIdList', data)
	},
	// 根据id集合获取用户集合
	userCenterGetUserListByIdList(data) {
		return request('getUserListByIdList', data)
	},
	// 根据id集合获取职位集合
	userCenterGetPositionListByIdList(data) {
		return request('getPositionListByIdList', data)
	},
	// 根据id集合获取角色集合
	userCenterGetRoleListByIdList(data) {
		return request('getRoleListByIdList', data)
	}
}
