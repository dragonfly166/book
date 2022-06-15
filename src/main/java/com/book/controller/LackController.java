package com.book.controller;

import com.book.domain.Lack;
import com.book.domain.LackResult;
import com.book.mapper.LackMapper;
import com.book.result.ApiResult;
import com.book.service.LackService;
import com.book.util.UserUtil;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        var lackResults = lackService.getList(containSolved);
        return ApiResult.success(lackResults);
    }

    /**
     * 完成缺书请求
     */
    @PostMapping("/{id}/finish")
    public ApiResult<?> finish(@PathVariable("id") Integer id) {
        Lack lack = lackMapper.queryLackById(id);
        if (lack == null) {
            return ApiResult.fail("求购信息不存在");
        }
        if (lack.getIsSolved()) {
            return ApiResult.fail("求购信息已完成");
        }
        if (!"admin".equals(UserUtil.getIdentity()) && lack.getPublisherId() != UserUtil.getUserId()) {
            return ApiResult.fail("不是自己发布的求购信息");
        }

        lackMapper.updateSolved(true, id);
        return ApiResult.success();
    }

    /**
     * 删除求购信息
     */
    @PostMapping("/{id}/delete")
    public ApiResult<?> delete(@PathVariable("id") Integer id) {
        if (!"admin".equals(UserUtil.getIdentity())) {
            return ApiResult.fail("非管理员，无权限");
        }
        lackMapper.deleteLack(id);
        return ApiResult.success();
    }
}
