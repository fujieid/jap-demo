package com.fujieid.jap.demo.controller;

import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.demo.config.JapConfigContext;
import com.fujieid.jap.demo.util.ViewUtil;
import com.fujieid.jap.http.jakarta.JakartaRequestAdapter;
import com.fujieid.jap.http.jakarta.JakartaResponseAdapter;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2ResponseType;
import com.fujieid.jap.oidc.OidcConfig;
import com.fujieid.jap.oidc.OidcStrategy;
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
 * 需要依赖 jap-oidc 模块
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:07
 * @since 1.0.0
 */
@RestController
@RequestMapping("/oidc")
public class OidcController implements InitializingBean {

    @Resource(name = "oauth2")
    private JapUserService japUserService;
    private OidcStrategy oidcStrategy;

    @RequestMapping("/login/jai")
    public ModelAndView renderAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JapConfigContext.strategy = "oidc";
        OidcConfig config = new OidcConfig();
        // 配置 OIDC 的 Issue 链接
        config.setIssuer("xx")
                .setPlatform("jai")
                .setState(UuidUtils.getUUID())
                .setClientId("xx")
                .setClientSecret("xx")
                .setCallbackUrl("http://sso.jap.com:8443/oidc/login/jai")
                .setScopes(new String[]{"read", "write"})
                .setResponseType(Oauth2ResponseType.CODE)
                .setGrantType(Oauth2GrantType.AUTHORIZATION_CODE);
        JapResponse japResponse = oidcStrategy.authenticate(config, new JakartaRequestAdapter(request), new JakartaResponseAdapter(response));
        return ViewUtil.getView(japResponse);
    }

    /**
     * 初始化 bean 时对 SimpleStrategy 进行初始化，适用于启用了 SSO 的情况，如果没有启用 SSO，则非强制使用该方式初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        oidcStrategy = new OidcStrategy(japUserService, JapConfigContext.getConfig());
    }
}
