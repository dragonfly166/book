package com.book.controller;

import com.book.domain.BookResult;
import com.book.domain.Data;
import com.book.domain.Order;
import com.book.domain.OrderResult;
import com.book.mapper.BookMapper;
import com.book.result.ApiResult;
import com.book.service.BookService;
import com.book.util.UserUtil;
import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunlongfei
 */
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    /**
     * 获取旧书列表
     */
    @GetMapping("/book/list")
    public ApiResult<List<BookResult>> list(String orderBy, String order, String search, Boolean containSold) {
        if (containSold != null) {
            if (!"admin".equals(UserUtil.getIdentity())) {
                return ApiResult.fail("非管理员，无权限");
            }
        }

        var result = bookService.getList(orderBy, order, search, containSold);
        return ApiResult.success(result);
    }

    /**
     * 查看旧书信息
     */
    @GetMapping("/book/{id}/watch")
    public ApiResult<BookResult> watch(@PathVariable("id") Integer id) {
        var bookResult = bookService.getBookInfo(id);
        return ApiResult.success(bookResult);
    }

    /**
     * 购买旧书
     */
    @PostMapping("/book/{id}/purchase")
    public ApiResult<Order> purchase(@PathVariable("id") Integer id,
        @NotBlank(message = "需要填写收货地址") String address) throws Exception {
        var currentOrder = bookService.purchase(id, address);
        return ApiResult.success(currentOrder);
    }

    /**
     * 发布旧书
     */
    @PostMapping("/book/publish")
    public ApiResult<?> publish(@NotBlank(message = "名称不能为空") String name,
        String description, MultipartFile image,
        @NotNull(message = "价格不能为空") Double price) throws IOException {
        bookService.publish(name, description, image, price);
        return ApiResult.success();
    }

    /**
     * 查看我的订单
     */
    @GetMapping("/order/me")
    public ApiResult<List<OrderResult>> me() {
        var myOrders = bookService.getMyOrders();
        return ApiResult.success(myOrders);
    }

    /**
     * 查看所有订单（管理）
     */
    @GetMapping("/order/all")
    public ApiResult<List<OrderResult>> all() {
        if (!"admin".equals(UserUtil.getIdentity())) {
            return ApiResult.fail("非管理员，无权限");
        }
        var orders = bookService.getAllOrders();
        return ApiResult.success(orders);
    }

    /**
     * 删除书籍信息
     */
    @PostMapping("/book/{id}/delete")
    public ApiResult<?> delete(@PathVariable("id") Integer id) {
        if (!"admin".equals(UserUtil.getIdentity())) {
            return ApiResult.fail("非管理员，无权限");
        }
        bookMapper.deleteBook(id);
        return ApiResult.success();
    }

    /**
     * 获取统计数据
     */
    @GetMapping("/data")
    public ApiResult<Data> data() {
        if (!"admin".equals(UserUtil.getIdentity())) {
            return ApiResult.fail("非管理员，无权限");
        }
        var data = bookService.getData();
        return ApiResult.success(data);
    }
}
