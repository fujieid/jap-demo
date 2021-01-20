package com.fujieid.jap.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.store.SessionJapUserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@SpringBootApplication
@Controller
public class JapDemoApplication implements ApplicationRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(JapDemoApplication.class, args);
    }

    @RequestMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        JapUser japUser = new SessionJapUserStore().get(request, response);
        if (null != japUser) {
            model.addAttribute("userJson", claimsToJson(japUser));
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

    private String claimsToJson(JapUser japUser) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // null替换为""
            mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                @Override
                public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
                    arg1.writeString("");
                }
            });
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(japUser);
        } catch (JsonProcessingException jpe) {
            log.error("Error parsing claims to JSON", jpe);
        }
        return "Error parsing claims to JSON.";
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("http://localhost:" + port);
    }
}
