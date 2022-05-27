package com.book.service;

import com.book.domain.Lack;
import com.book.domain.LackResult;
import com.book.domain.UserProfile;
import com.book.mapper.LackMapper;
import com.book.mapper.UserMapper;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author sunlongfei
 */
@Service
public class LackService {

    @Autowired
    private LackMapper lackMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户信息
     */
    private List<LackResult> addUsers(List<Lack> lacks) {
        if (lacks == null) {
            return null;
        }

        List<LackResult> result = new ArrayList<>(lacks.size());
        for (Lack lack: lacks) {
            UserProfile user;
            String userStr = redisTemplate.opsForValue().get("user:profile:" + lack.getPublisherId() + ":string");
            if (userStr == null) {
                user = userMapper.queryUserProfileById(lack.getPublisherId());
                redisTemplate.opsForValue().set("user:profile:" + lack.getPublisherId() + ":string",
                    gson.toJson(user), 3, TimeUnit.DAYS);
            } else {
                user = gson.fromJson(userStr, UserProfile.class);
            }
            result.add(new LackResult(lack.getId(), lack.getBookName(), user,
                lack.getIsSolved(), lack.getCreateTime()));
        }
        return result;
    }

    /**
     * 获取缺书登记列表
     */
    public List<LackResult> getList(Boolean containSolved) {
        var lacks = lackMapper.queryLacks(containSolved);
        return addUsers(lacks);
    }
}
