package com.fujieid.jap.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.store.SessionJapUserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@SpringBootApplication
@Controller
public class JapDemoApplication {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(JapDemoApplication.class, args);
    }

    @RequestMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        JapUser japUser = new SessionJapUserStore().get(request, response);
        if (null != japUser) {
            model.addAttribute("userJson", claimsToJson(JSONObject.parseObject(JSON.toJSONString(japUser))));
        }
        Object strategy = request.getSession().getAttribute("strategy");
        model.addAttribute("strategy", strategy);
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        new SessionJapUserStore().remove(request);
        return "index";
    }

    private String claimsToJson(Map<String, Object> claims) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(claims);
        } catch (JsonProcessingException jpe) {
            log.error("Error parsing claims to JSON", jpe);
        }
        return "Error parsing claims to JSON.";
    }
}
