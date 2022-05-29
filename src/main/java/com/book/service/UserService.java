package com.book.service;

import com.book.domain.User;
import com.book.domain.UserCoreInfo;
import com.book.domain.UserProfile;
import com.book.mapper.UserMapper;
import com.book.util.JwtUtil;
import com.book.util.UserUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

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
    private ImageService imageService;

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
        String key = "user:core.info:" + DigestUtils.md5DigestAsHex(("username:" + username + "password:" + password).getBytes(
            StandardCharsets.UTF_8));
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

    /**
     * 更新用户信息
     */
    public void update(User user, MultipartFile avatarImg) throws IOException {
        String url = null;
        if (avatarImg != null) {
            url = imageService.saveImage(avatarImg);
        }
        user.setAvatar(url);
        userMapper.updateUser(user, UserUtil.getUserId());

        //更新redis
        if (user.getUsername() != null || user.getAvatar() != null) {
            var updatedUser = userMapper.queryUserProfileById(UserUtil.getUserId());
            redisTemplate.opsForValue().set("user:profile:" + UserUtil.getUserId() + ":string",
                gson.toJson(updatedUser), 3, TimeUnit.DAYS);
        }
    }
}
