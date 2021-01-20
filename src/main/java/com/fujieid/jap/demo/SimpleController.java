package com.fujieid.jap.demo;

import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.simple.SimpleConfig;
import com.fujieid.jap.simple.SimpleStrategy;
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
public class SimpleController {

    @Resource(name = "simple")
    private JapUserService japUserService;

    @GetMapping("/login")
    public String toLogin(HttpServletRequest request) {
        request.getSession().setAttribute("strategy", "simple");
        return "login";
    }

    @PostMapping("/login")
    public void renderAuth(HttpServletRequest request, HttpServletResponse response) {
        SimpleStrategy simpleStrategy = new SimpleStrategy(japUserService, new JapConfig());
        simpleStrategy.authenticate(new SimpleConfig(), request, response);
    }
}
