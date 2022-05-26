package com.book.util;

import com.book.domain.UserCoreInfo;

/**
 * @author sunlongfei
 */
public class UserUtil {

    private static final ThreadLocal<UserCoreInfo> INFO = new ThreadLocal<>();

    public static void setInfo(UserCoreInfo info) {
        INFO.set(info);
    }

    public static void removeInfo() {
        INFO.remove();
    }

    /**
     * 获取用户id
     */
    public static int getUserId() {
        return INFO.get().getId();
    }

    /**
     * 获取用户身份
     */
    public static String getIdentity() {
        return INFO.get().getIdentity();
    }
}
