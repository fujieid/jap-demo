package com.fujieid.jap.demo;

import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.simple.SimpleConfig;
import com.fujieid.jap.simple.SimpleStrategy;
import com.fujieid.jap.sso.config.JapSsoConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 需要依赖 jap-simple 模块
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:07
 * @since 1.0.0
 */
@Controller
@RequestMapping("/simple")
public class SimpleController implements InitializingBean {

    @Resource(name = "simple")
    private JapUserService japUserService;
    private SimpleStrategy simpleStrategy;

    @GetMapping("/login")
    public String toLogin(HttpServletRequest request) {
        request.getSession().setAttribute("strategy", "simple");
        return "login";
    }

    @PostMapping("/login")
    public void renderAuth(HttpServletRequest request, HttpServletResponse response) {
        simpleStrategy.authenticate(new SimpleConfig(), request, response);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        simpleStrategy = new SimpleStrategy(japUserService, new JapConfig()
                .setSso(true)
                .setSsoConfig(new JapSsoConfig()
                                      /*
                                          将 domain 设置为 .jap.com 报错：
                                          java.lang.IllegalArgumentException: An invalid domain [.jap.com] was specified for this cookie
                                          参考解决方案：
                                          https://gitee.com/baomidou/kisso/wikis/java.lang.IllegalArgumentException:-An-invalid-domain-%5B.x.com%5D-was-specified-for-this-cookie?sort_id=12454
                                          高版本 8.5版本 + tomcat 对 cookie 处理机制变更，原来设置 .x.com 应该修改为 x.com
                                       */
                                      .setCookieDomain("jap.com")));

    }
}
