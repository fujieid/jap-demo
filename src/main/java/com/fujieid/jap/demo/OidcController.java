package com.fujieid.jap.demo;

import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2ResponseType;
import com.fujieid.jap.oidc.OidcConfig;
import com.fujieid.jap.oidc.OidcStrategy;
import me.zhyd.oauth.utils.UuidUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void renderAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("strategy", "oidc");
        OidcConfig config = new OidcConfig();
        config.setIssuer("https://xxx")
                .setPlatform("jai")
                .setState(UuidUtils.getUUID())
                .setClientId("xxx")
                .setClientSecret("xxx")
                .setCallbackUrl("http://localhost:8443/oidc/login/jai")
                .setScopes(new String[]{"read", "write"})
                .setResponseType(Oauth2ResponseType.code)
                .setGrantType(Oauth2GrantType.authorization_code);
        oidcStrategy.authenticate(config, request, response);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        oidcStrategy = new OidcStrategy(japUserService, JapConfigContext.getConfig());
    }
}
