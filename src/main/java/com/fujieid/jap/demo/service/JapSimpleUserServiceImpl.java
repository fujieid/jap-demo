package com.fujieid.jap.demo.service;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用于 jap-simple 模块，实现 getByName 和 validPassword 方法，如果需要sso登录，则必须实现 getById 方法
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:09
 * @since 1.0.0
 */
@Service("simple")
public class JapSimpleUserServiceImpl implements JapUserService {

    /**
     * 模拟 DB 操作
     */
    private static final List<JapUser> userDatas = new ArrayList<>();

    static {
        // 模拟数据库中的数据
        userDatas.add(new JapUser().setUserId("1111").setUsername("jap").setPassword("jap"));
        for (int i = 0; i < 10; i++) {
            userDatas.add(new JapUser().setUserId(i + "").setUsername("jap" + i).setPassword("jap" + i));
        }
    }

    /**
     * 当启用 sso 功能时，该方法必须实现
     *
     * @param userId 用户id
     * @return JapUser
     */
    @Override
    public JapUser getById(String userId) {
        return userDatas.stream()
                .filter((user) -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public JapUser getByName(String username) {
        return userDatas.stream()
                .filter((user) -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean validPassword(String password, JapUser user) {
        return user.getPassword().equals(password);
    }
}
