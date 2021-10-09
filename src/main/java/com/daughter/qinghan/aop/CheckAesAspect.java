/*
package com.daughter.qinghan.aop;


import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.daughter.qinghan.annontation.AesCrypt;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class CheckAesAspect {

    @NacosValue(value = "${synchron.aes.sKey}", autoRefreshed = true)
    private String sKey;

    @NacosValue(value = "${synchron.aes.offset}", autoRefreshed = true)
    private String offset;

    @Pointcut("@annotation(com.yk.friends.manage.annontation.AesCrypt)")
    public void pointcut() {
    }

    */
/**
     * 切面验签
     *
     * @param joinPoint
     *//*

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取appId
        String appId = request.getHeader("appId");

        //获取sign
        String sign = request.getHeader("sign");

        //获取timestamp
        String timestamp = request.getHeader("timestamp");

        if (Objects.isNull(appId)) {
            throw new ExceptionYk("appId不能为空");
        }

        if (Objects.isNull(timestamp)) {
            throw new ExceptionYk("时间戳不能为空");
        }

        if (Objects.isNull(sign)) {
            throw new ExceptionYk("签名不能为空");
        }

        if (!Objects.equals("modular-yk-truck-friend", appId)) {
            throw new ExceptionYk("appId不合法");
        }

        try {
            long differMin = getDifferMinute(Long.valueOf(timestamp));
            if (differMin < -5 || differMin > 5) {
                throw new ExceptionYk("时间戳不合法,超过5分钟");
            }
        } catch (Exception e) {
            throw new ExceptionYk("时间戳不合法");
        }

        try {
            AESUtil.Decrypt(sign, sKey, offset);
        } catch (Exception e) {
            throw new ExceptionYk("签名解密失败");
        }
        log.info("验证通过, url: {}", request.getRequestURI());
        //验签结束放行
        return joinPoint.proceed();
    }

    private long getDifferMinute(Long timestamp) {
        long diff = System.currentTimeMillis() - timestamp;
        return diff / 1000 / 60;
    }
}
*/
