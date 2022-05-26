package com.book.result;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sunlongfei
 */
@Data
public class ApiResult<T> {

    private String msg;

    private T data;

    public ApiResult(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public static <W> ApiResult<W> success() {
        return new ApiResult<>("success", null);
    }

    public static <W> ApiResult<W> success(W data) {
        return new ApiResult<>("success", data);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static <W> ApiResult<W> fail(String msg) {
        return new ApiResult<>(msg, null);
    }
}
