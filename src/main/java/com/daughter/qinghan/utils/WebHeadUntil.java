package com.daughter.qinghan.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangkezhen
 * @email 528525313@qq.com
 * @time 2021/9/10 15:47
 */

public class WebHeadUntil {
    private static final String TOKEN = "Authorization";
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();


    public static void set(String token) {
        threadLocal.set(token);
    }

    public static String get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }


    /**
     * 获取登录token
     *
     * @return
     */


}
