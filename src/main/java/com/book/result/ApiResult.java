package com.book.result;

import lombok.Data;

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

    public static ApiResult<?> success() {
        return new ApiResult<>("success", null);
    }

    public static <W> ApiResult<W> success(W data) {
        return new ApiResult<>("success", data);
    }

    public static ApiResult<?> fail(String msg) {
        return new ApiResult<>(msg, null);
    }
}
