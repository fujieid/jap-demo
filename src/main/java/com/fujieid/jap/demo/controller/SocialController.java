package com.fujieid.jap.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.demo.config.JapConfigContext;
import com.fujieid.jap.demo.util.ViewUtil;
import com.fujieid.jap.http.jakarta.JakartaRequestAdapter;
import com.fujieid.jap.http.jakarta.JakartaResponseAdapter;
import com.fujieid.jap.social.SocialConfig;
import com.fujieid.jap.social.SocialStrategy;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.utils.UuidUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 需要依赖 jap-social 模块
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:07
 * @since 1.0.0
 */
@RestController
@RequestMapping("/social")
public class SocialController implements InitializingBean {

    @Resource(name = "social")
    private JapUserService japUserService;
    private SocialStrategy socialStrategy;

    @RequestMapping("/login/gitee")
    public ModelAndView renderAuth(HttpServletRequest request, HttpServletResponse response) {
        JapConfigContext.strategy = "social";
        SocialConfig config = new SocialConfig();
        // platform 参考 justauth#AuthDefaultSource
        // 如果包含通过 justauth 自定义的第三方平台，则该值为实现 AuthSource 后的 getName() 值
        config.setPlatform("gitee");
        config.setState(UuidUtils.getUUID());
        config.setJustAuthConfig(AuthConfig.builder()
                .clientId("fda07d40917d6f040822d3fa01c8c75588c67d63132c3ddc5c66990342115ba9")
                .clientSecret("016f88fbff2d178263c4060c46168f4937153120a310adc21980e7838b76e833")
                .redirectUri("http://sso.jap.com:8443/social/login/gitee")
                .build());
        JapResponse japResponse = socialStrategy.authenticate(config, new JakartaRequestAdapter(request), new JakartaResponseAdapter(response));
        if (japResponse.isSuccess() && !japResponse.isRedirectUrl()) {
            JapUser japUser = (JapUser) japResponse.getData();
            AuthUser authUser = (AuthUser) japUser.getAdditional();
            AuthToken authToken = authUser.getToken();
            // 测试获取用户信息的接口
            try {
                JapResponse userInfoRes = socialStrategy.getUserInfo(config, authToken);
                System.out.println("通过 token 获取的用户信息：" + JSONObject.toJSONString(userInfoRes));
            } catch (Exception e) {
                System.err.println("通过 token 获取的用户信息出错：" + e.getMessage());
            }

            // 测试刷新令牌的接口
            try {
                JapResponse refreshRes = socialStrategy.refreshToken(config, authToken);
                System.out.println("refresh token ：" + JSONObject.toJSONString(refreshRes));
            } catch (Exception e) {
                System.err.println("refresh token 出错：" + e.getMessage());
            }

            // 测试回收令牌的接口
            try {
                JapResponse revokeRes = socialStrategy.revokeToken(config, authToken);
                System.out.println("revoke token：" + JSONObject.toJSONString(revokeRes));
            } catch (Exception e) {
                System.err.println("revoke token 出错：" + e.getMessage());
            }
        }
        return ViewUtil.getView(japResponse);
    }

    /**
     * 初始化 bean 时对 SimpleStrategy 进行初始化，适用于启用了 SSO 的情况，如果没有启用 SSO，则非强制使用该方式初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        socialStrategy = new SocialStrategy(japUserService, JapConfigContext.getConfig());

    }
}
