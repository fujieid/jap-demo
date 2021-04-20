package com.fujieid.jap.demo.controller;

import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.demo.config.JapConfigContext;
import com.fujieid.jap.demo.util.ViewUtil;
import com.fujieid.jap.oauth2.*;
import me.zhyd.oauth.utils.UuidUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 需要依赖 jap-oauth2 模块
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:07
 * @since 1.0.0
 */
@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller implements InitializingBean {

    @Resource(name = "oauth2")
    private JapUserService japUserService;
    private Oauth2Strategy oauth2Strategy;


    @RequestMapping("/login/jai")
    public ModelAndView renderAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JapConfigContext.strategy = "oauth2";
        OAuthConfig config = new OAuthConfig();
        config.setPlatform("jai")
                .setState(UuidUtils.getUUID())
                .setClientId("xx")
                .setClientSecret("xx")
                .setCallbackUrl("http://sso.jap.com:8443/oauth2/login/jai")
                .setAuthorizationUrl("xx")
                .setTokenUrl("xx")
                .setUserinfoUrl("xx")
                .setScopes(new String[]{"read", "write"})
                .setResponseType(Oauth2ResponseType.code)
                .setGrantType(Oauth2GrantType.authorization_code);
        JapResponse japResponse = oauth2Strategy.authenticate(config, request, response);
        return ViewUtil.getView(japResponse);
    }

    /**
     * 针对部分平台的获取用户的api不是post请求方式的处理方案：通过 setUserInfoEndpointMethodType 指定 请求类型
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/login/gitlab")
    public ModelAndView renderGitlab(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JapConfigContext.strategy = "oauth2";
        OAuthConfig config = new OAuthConfig();
        config.setPlatform("gitlab")
                .setState(UuidUtils.getUUID())
                .setClientId("6a1a65a1ecf704e86d59cc86f56cd614de47d2ebc5e3ca0e0d339022a3616578")
                .setClientSecret("127898bfa5e1f5f599b78d2033c1bcd39a21f9de00588ee1337146df670b93c8")
                .setCallbackUrl("http://sso.jap.com:8443/oauth2/login/gitlab")
                .setAuthorizationUrl("https://gitlab.com/oauth/authorize")
                .setTokenUrl("https://gitlab.com/oauth/token")
                .setUserinfoUrl("https://gitlab.com/api/v4/user")
                .setScopes(new String[]{"read_user", "openid", "profile", "email"})
                .setResponseType(Oauth2ResponseType.code)
                .setGrantType(Oauth2GrantType.authorization_code)
                .setUserInfoEndpointMethodType(Oauth2EndpointMethodType.GET);
        JapResponse japResponse = oauth2Strategy.authenticate(config, request, response);
        return ViewUtil.getView(japResponse);
    }

    /**
     * 初始化 bean 时对 SimpleStrategy 进行初始化，适用于启用了 SSO 的情况，如果没有启用 SSO，则非强制使用该方式初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        oauth2Strategy = new Oauth2Strategy(japUserService, JapConfigContext.getConfig());
    }
}
