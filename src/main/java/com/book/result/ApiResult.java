package com.book.result;

import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    public static <W> ApiResult<W> fail(String msg) {
        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return new ApiResult<>(msg, null);
    }
}
