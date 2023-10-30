/*
 * Copyright [2022] []
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为
 */
package vip.xiaonuo.client.modular.user.provider;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xiaonuo.auth.api.ClientLoginUserApi;
import vip.xiaonuo.auth.core.pojo.SaBaseClientLoginUser;
import vip.xiaonuo.auth.core.pojo.SaBaseRegisterUser;
import vip.xiaonuo.client.modular.user.entity.ClientUser;
import vip.xiaonuo.client.modular.user.result.ClientLoginUser;
import vip.xiaonuo.client.modular.user.service.ClientUserService;
import vip.xiaonuo.common.constants.ImgConstants;
import vip.xiaonuo.common.util.CommonCryptogramUtil;
import vip.xiaonuo.common.util.IpAddressUtils;
import vip.xiaonuo.dev.api.DevConfigApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * C端登录用户API接口实现类
 *
 * @author xuyuxiang
 * @date 2022/4/29 13:36
 **/
@Service("clientLoginUserApi")
public class ClientLoginUserApiProvider implements ClientLoginUserApi {

    @Resource
    private ClientUserService clientUserService;

    @Autowired
    private DevConfigApi devConfigApi;

    /**
     * 根据id获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2021/12/28 15:35
     **/
    @Override
    public SaBaseClientLoginUser getClientUserById(String id) {
        return clientUserService.getUserById(id);
    }

    /**
     * 根据账号获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2021/12/28 15:35
     **/
    @Override
    public ClientLoginUser getClientUserByAccount(String account) {
        return clientUserService.getUserByAccount(account);
    }

    /**
     * 根据手机号获取C端用户信息，查不到则返回null
     *
     * @author xuyuxiang
     * @date 2022/8/25 14:08
     **/
    @Override
    public SaBaseClientLoginUser getClientUserByPhone(String phone) {
        return clientUserService.getUserByPhone(phone);
    }

    /**
     * 根据用户id获取用户集合
     *
     * @author xuyuxiang
     * @date 2022/4/27 22:53
     */
    @Override
    public List<JSONObject> listUserByUserIdList(List<String> userIdList) {
        return clientUserService.listByIds(userIdList).stream().map(JSONUtil::parseObj).collect(Collectors.toList());
    }

    @Override
    public void updateUserLoginInfo(String userId, String device) {
        clientUserService.updateUserLoginInfo(userId, device);
    }

    @Override
    public void createUserValidCode(SaBaseRegisterUser saBaseRegisterUser) {
        // TODO 具体根据自己的业务进行修改
        // 注册用户信息
        ClientUser clientUser = new ClientUser();
        clientUser.setPhone(saBaseRegisterUser.getPhone());
        clientUser.setAccount(saBaseRegisterUser.getNickname());
        clientUser.setNickname(saBaseRegisterUser.getNickname());
        clientUser.setPassword(CommonCryptogramUtil.doHashValue(saBaseRegisterUser.getPassword()));
        clientUser.setAvatar(ImgConstants.getRandomImg());
        clientUser.setRegisterIp(IpAddressUtils.getIpAddress());
        clientUserService.save(clientUser);
    }
}