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
public class WebUntil {
    public static Map<String,Object>headMap(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader("Authorization");
        Map<String,Object>head=new HashMap<>();
        head.put("Authorization",authorization);
        return head;
    }
}
