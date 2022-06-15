package com.book.interceptor;

import com.book.domain.UserCoreInfo;
import com.book.util.JwtUtil;
import com.book.util.UserUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author sunlongfei
 */
@Component
public class Identity implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String authentication = request.getHeader("authorization");
        UserCoreInfo info = JwtUtil.parseJwt(authentication);
        UserUtil.setInfo(info);
        return true;
    }
}
