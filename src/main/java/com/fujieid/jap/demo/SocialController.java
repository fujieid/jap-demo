package com.fujieid.jap.demo;

import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.social.SocialConfig;
import com.fujieid.jap.social.SocialStrategy;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.utils.UuidUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SocialController {

    @Resource(name = "social")
    private JapUserService japUserService;

    @RequestMapping("/login/gitee")
    public void renderAuth(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("strategy", "social");
        SocialStrategy socialStrategy = new SocialStrategy(japUserService, JapConfigContext.getConfig()
                .setOptions(AuthConfig.builder()
                        .clientId("3d4df5b080492af847d4eb3aa2abdcaf11ae29b312beb46520fb7972553a9158")
                        .clientSecret("e4c0746139e4111460c2d477b62dabb511a8a9df3d562adcf036e567bd2184d4")
                        .redirectUri("http://sso.jap.com:8443/social/login/gitee")
                        .build()));
        SocialConfig config = new SocialConfig();
        // platform 参考 justauth#AuthDefaultSource
        // 如果包含通过 justauth 自定义的第三方平台，则该值为实现 AuthSource 后的 getName() 值
        config.setPlatform("gitee");
        config.setState(UuidUtils.getUUID());
        socialStrategy.authenticate(config, request, response);
    }
}
