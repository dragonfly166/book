package com.book.controller;

import com.book.domain.LackResult;
import com.book.mapper.LackMapper;
import com.book.result.ApiResult;
import com.book.service.LackService;
import com.book.util.UserUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunlongfei
 */
@RestController
@RequestMapping("/lack")
public class LackController {

    @Autowired
    private LackMapper lackMapper;

    @Autowired
    private LackService lackService;

    /**
     * 发布缺书信息
     */
    @PostMapping("/publish")
    public ApiResult<?> publish(@NotBlank(message = "名称不能为空") String bookName) {
        lackMapper.addLackBook(bookName, UserUtil.getUserId());
        return ApiResult.success();
    }

    /**
     * 查看缺书信息
     */
    @GetMapping("/list")
    public ApiResult<List<LackResult>> list(Boolean containSolved) {
        if (containSolved != null && containSolved) {
            if (!"admin".equals(UserUtil.getIdentity())) {
                return ApiResult.fail("非管理员，无权限");
            }
        }

        var lackResults = lackService.getList(containSolved);
        return ApiResult.success(lackResults);
    }
}
