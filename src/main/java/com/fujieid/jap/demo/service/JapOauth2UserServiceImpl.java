package com.fujieid.jap.demo.service;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.oauth2.token.AccessToken;
import com.xkcoding.json.JsonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 适用于 jap-oauth2 模块，实现 getByPlatformAndUid 和 createAndGetSocialUser 方法，如果需要sso登录，则必须实现 getById 方法
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:09
 * @since 1.0.0
 */
@Service("oauth2")
public class JapOauth2UserServiceImpl implements JapUserService {

    /**
     * 模拟 DB 操作
     */
    private static final List<JapUser> userDatas = new ArrayList<>();

    /**
     * 当启用 sso 功能时，该方法必须实现
     *
     * @param userId 用户id
     * @return JapUser
     */
    @Override
    public JapUser getById(String userId) {
        return userDatas.stream()
                .filter((user) -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据第三方平台标识（platform）和第三方平台的用户 uid 查询数据库
     *
     * @param platform 第三方平台标识
     * @param uid      第三方平台的用户 uid
     * @return JapUser
     */
    @Override
    public JapUser getByPlatformAndUid(String platform, String uid) {
        // FIXME 注意：此处仅作演示用，并没有判断 platform，实际业务系统中需要根据 platform 和 uid 进行获取唯一用户
        return userDatas.stream().filter(user -> user.getUserId().equals(uid)).findFirst().orElse(null);
    }

    /**
     * 创建并获取第三方用户，相当于第三方登录成功后，将授权关系保存到数据库（开发者业务系统中 oauth2 user -> sys user 的绑定关系）
     *
     * @param platform 第三方平台标识
     * @param userInfo JustAuth 中的 AuthUser
     * @return JapUser
     */
    @Override
    public JapUser createAndGetOauth2User(String platform, Map<String, Object> userInfo, Object tokenInfo) {
        // FIXME 业务端可以对 tokenInfo 进行保存或其他操作
        AccessToken accessToken = (AccessToken) tokenInfo;
        System.out.println(JsonUtil.toJsonString(accessToken));
        // FIXME 注意：此处仅作演示用，不同的 oauth 平台用户id都不一样，此处需要开发者自己分析第三方平台的用户信息，提取出用户的唯一ID
        String uid = (String) userInfo.get("userId");
        // 查询绑定关系，确定当前用户是否已经登录过业务系统
        JapUser japUser = this.getByPlatformAndUid(platform, uid);
        if (null == japUser) {
            japUser = createJapUser();
            japUser.setAdditional(userInfo);
            userDatas.add(japUser);
        }
        return japUser;
    }

    private JapUser createJapUser() {
        JapUser user = new JapUser();
        user.setUserId("1");
        user.setUsername("justauth");
        user.setPassword("justauthpassword");
        return user;
    }
}
