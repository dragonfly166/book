package com.book.service;

import com.book.domain.UserCoreInfo;
import com.book.mapper.UserMapper;
import com.book.util.JwtUtil;
import com.google.gson.Gson;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sunlongfei
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Gson gson;

    /**
     * 用户注册
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public void register(String username, String password) throws Exception {
        if (userMapper.hasExisted(username)) {
            throw new Exception("username has existed");
        }
        userMapper.addUser(username, password);
    }

    /**
     * 用户登录
     */
    public String login(String username, String password) throws Exception {
        String key = "user:core.info:username-" + username + "password-" + password;
        String value = redisTemplate.opsForValue().get(key);
        UserCoreInfo info;
        if (value == null) {
            info = userMapper.queryCoreInfo(username, password);
            if (info == null) {
                redisTemplate.opsForValue().set(key, "empty", 3, TimeUnit.DAYS);
                throw new Exception("用户或密码错误");
            }
            redisTemplate.opsForValue().set(key, gson.toJson(info), 3, TimeUnit.DAYS);
        } else if ("empty".equals(value)) {
            throw new Exception("用户或密码错误");
        } else {
            info = gson.fromJson(value, UserCoreInfo.class);
        }
        return JwtUtil.createJwt(info);
    }
}
