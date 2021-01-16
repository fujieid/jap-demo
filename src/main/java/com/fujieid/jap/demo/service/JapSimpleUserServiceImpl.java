package com.fujieid.jap.demo.service;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 适用于 jap-simple 模块，实现 getByName 和 validPassword 方法
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
    private static List<JapUser> userDatas = Lists.newArrayList();

    @Override
    public JapUser getByName(String username) {
        List<JapUser> list = userDatas.stream().filter((user) -> user.getUsername().equals(username)).collect(Collectors.toList());
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public boolean validPassword(String password, JapUser user) {
        return true;
    }
}
