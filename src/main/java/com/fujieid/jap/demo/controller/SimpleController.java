package com.fujieid.jap.demo.controller;

import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.demo.config.JapConfigContext;
import com.fujieid.jap.demo.util.ViewUtil;
import com.fujieid.jap.simple.SimpleConfig;
import com.fujieid.jap.simple.SimpleStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String toLogin(HttpServletRequest request, HttpServletResponse response) {
        JapConfigContext.strategy = "simple";
        if (JapAuthentication.checkUser(request, response).isSuccess()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView renderAuth(HttpServletRequest request, HttpServletResponse response) {
        JapResponse japResponse = simpleStrategy.authenticate(new SimpleConfig()
                .setRememberMeCookieDomain("jap.com"), request, response);
        return ViewUtil.getView(japResponse);
    }

    /**
     * 初始化 bean 时对 SimpleStrategy 进行初始化，适用于启用了 SSO 的情况，如果没有启用 SSO，则非强制使用该方式初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        simpleStrategy = new SimpleStrategy(japUserService, JapConfigContext.getConfig());
    }
}
