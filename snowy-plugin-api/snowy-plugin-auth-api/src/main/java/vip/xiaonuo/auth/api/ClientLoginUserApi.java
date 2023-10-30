package vip.xiaonuo.auth.api;

import cn.hutool.json.JSONObject;
import vip.xiaonuo.auth.core.pojo.SaBaseClientLoginUser;
import vip.xiaonuo.auth.core.pojo.SaBaseRegisterUser;

import java.util.List;

/**
 * @author 黄志源大魔王
 * @date 2023-10-30 17:23
 * @project snowy
 * @company 智影科技
 * @description
 */
public interface ClientLoginUserApi {

	/**
	 * 根据id获取C端用户信息，查不到则返回null
	 *
	 * @author xuyuxiang
	 * @date 2022/3/10 16:14
	 **/
	SaBaseClientLoginUser getClientUserById(String id);

	/**
	 * 根据账号获取C端用户信息，查不到则返回null
	 *
	 * @author xuyuxiang
	 * @date 2022/3/10 16:14
	 **/
	SaBaseClientLoginUser getClientUserByAccount(String account);

	/**
	 * 根据手机号获取C端用户信息，查不到则返回null
	 *
	 * @author xuyuxiang
	 * @date 2022/3/10 16:14
	 **/
	SaBaseClientLoginUser getClientUserByPhone(String phone);

	/**
	 * 根据用户id获取用户集合
	 *
	 * @author xuyuxiang
	 * @date 2022/4/27 22:53
	 */
	List<JSONObject> listUserByUserIdList(List<String> userIdList);

	/**
	 * 更新用户的登录时间和登录ip等信息
	 *
	 * @author xuyuxiang
	 * @date 2022/4/27 22:57
	 */
	void updateUserLoginInfo(String userId, String device);

	/**
	 * 根据图片验证码注册用户
	 *
	 * @param buildSaBaseRegisterUser
	 */
	void createUserValidCode(SaBaseRegisterUser buildSaBaseRegisterUser);

}
