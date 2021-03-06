package com.book.controller;

import com.book.domain.User;
import com.book.mapper.UserMapper;
import com.book.result.ApiResult;
import com.book.service.ImageService;
import com.book.service.UserService;
import com.book.util.UserUtil;
import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunlongfei
 */
@Validated
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 查询我的信息
     */
    @GetMapping("/me")
    public ApiResult<User> me() {
        User me = userMapper.queryUserById(UserUtil.getUserId());
        return ApiResult.success(me);
    }

    /**
     * 查询所有信息（admin）
     */
    @GetMapping("/all")
    public ApiResult<List<User>> all() throws Exception {
        if (!"admin".equals(UserUtil.getIdentity())) {
            return ApiResult.fail("非管理员，无权限");
        }
        List<User> users = userMapper.queryAllUser();
        return ApiResult.success(users);
    }

    /**
     * 注册用户
     */
    @PostMapping("/register")
    public ApiResult<?> register(@NotBlank(message = "用户名不能为空") String username,
        @NotBlank(message = "密码不能为空") String password) throws Exception {
        userService.register(username, password);
        return ApiResult.success();
    }

    /**
     * 登录用户
     */
    @PostMapping("/login")
    public ApiResult<String> login(@NotBlank(message = "用户名不能为空") String username,
        @NotBlank(message = "密码不能为空") String password) throws Exception {
        String jwt = userService.login(username, password);
        return ApiResult.success(jwt);
    }

    /**
     * 修改本用户信息
     */
    @PostMapping("/update")
    public ApiResult<?> update(MultipartFile avatarImg, User user) throws IOException {
        if (user == null) {
            user = new User();
        }
        userService.update(user, avatarImg);
        return ApiResult.success();
    }

    /**
     * 删除用户信息
     */
    @PostMapping("/{id}/delete")
    public ApiResult<?> delete(@PathVariable("id") Integer id) {
        if (!"admin".equals(UserUtil.getIdentity())) {
            return ApiResult.fail("非管理员，无权限");
        }
        userMapper.deleteUser(id);
        return ApiResult.success();
    }
}
