
package vip.xiaonuo.auth.core.util;

import cn.dev33.satoken.session.SaSession;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.pojo.SaBaseClientLoginUser;
import vip.xiaonuo.common.util.RequestUtils;
import vip.xiaonuo.dev.api.DevConfigApi;

import java.util.List;

/**
 * C端登录用户工具类
 *
 * @author xuyuxiang
 * @date 2022/7/8 10:40
 **/
@Component
public class StpClientLoginUserUtil {

    public static DevConfigApi devConfigApi;

    @Autowired
    public StpClientLoginUserUtil(DevConfigApi devConfigApi) {
        StpClientLoginUserUtil.devConfigApi = devConfigApi;
    }

    public static String getHeaderUserUuid() {
        String userUuid = RequestUtils.getHeader("user-uuid");
        if (StrUtil.isBlank(userUuid)) {
            //拉入黑名单
            //IpRequestFilterUtil.addIpWhiteList();
            // 请求必须带上 userUuid
            //throw new BusinessException("非法请求！" + RequestUtils.getUri(), ExceptionCodeEnum.VERIFY);
            return "";
        }
        // uuid 中必须包含分隔符，免得被别人偷换成 userId 获取信息
        if (userUuid.contains("-")) {
            return userUuid;
        }
        return null;
    }

    /**
     * 获取当前C端登录用户
     *
     * @author xuyuxiang
     * @date 2022/7/8 10:41
     **/
    public static SaBaseClientLoginUser getClientLoginUser() {
        SaSession tokenSession = StpClientUtil.getTokenSession();
        return tokenSession == null ? null : (SaBaseClientLoginUser) tokenSession.get("loginUser");
    }

    public static String getUserId() {
        SaBaseClientLoginUser clientLoginUser = getClientLoginUser();
        return clientLoginUser == null ? null : clientLoginUser.getId();
    }

    public static String getUserIdDefaultVal() {
        String val = getHeaderUserUuid();
        try {
            String id = getClientLoginUser().getId();
            return StrUtil.isBlank(id) ? val : id;
        } catch (Exception e) {
            return val;
        }
    }

    /**
     * 判断是否为会员
     *
     * @return
     */
    public static boolean isMember() {
        try {
            String memberLevel = getClientLoginUser().getMemberLevel();
            return memberLevel.contains("会员");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取当前C端登录用户的当前请求接口的数据范围（暂无数据范围）
     *
     * @author xuyuxiang
     * @date 2022/7/8 10:41
     **/
    public static List<String> getLoginUserDataScope() {
        return CollectionUtil.newArrayList();
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        try {
            SaBaseClientLoginUser clientLoginUser = getClientLoginUser();
            return clientLoginUser != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isNotLogin() {
        return !isLogin();
    }
}
