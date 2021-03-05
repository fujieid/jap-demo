package com.fujieid.jap.demo.config;

import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.sso.config.JapSsoConfig;

/**
 * 公共配置
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-01-21 10:32
 * @since 1.0.0
 */
public class JapConfigContext {

    public static boolean sso = true;
    public static String strategy = "";

    public static JapConfig getConfig() {
        return new JapConfig()
                .setSso(sso)
                .setSsoConfig(new JapSsoConfig()
                        /*
                            将 domain 设置为 .jap.com 报错：
                            java.lang.IllegalArgumentException: An invalid domain [.jap.com] was specified for this cookie
                            参考解决方案：
                            https://gitee.com/baomidou/kisso/wikis/java.lang.IllegalArgumentException:-An-invalid-domain-%5B.x.com%5D-was-specified-for-this-cookie?sort_id=12454
                            高版本 8.5版本 + tomcat 对 cookie 处理机制变更，原来设置 .x.com 应该修改为 x.com
                         */
                        .setCookieDomain("jap.com"));
    }
}
