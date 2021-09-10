package com.daughter.qinghan.pojo.response;

import com.daughter.qinghan.utils.JacksonUtils;
import com.daughter.qinghan.utils.OkHttpUtils;
import lombok.Data;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangkezhen
 * @email 528525313@qq.com
 * @time 2021/9/10 9:53
 */
@Data
public class UserCenterResponse<T>{
    private Integer code;
    private String message;
    private T data;


    @Data
    class PageResult{
        private Integer current;
        private T records;
        private Integer size;
        private Integer total;
    }


    public static void main(String[] args) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("pageNum",1);
        requestMap.put("pageSize",10);
        //头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MzEyNjI3NDgsInVzZXJfbmFtZSI6InpoYW5na2V6aGVuIiwiYXV0aG9yaXRpZXMiOlsiMjMiXSwianRpIjoiMjc2M2MwOGUtYjAwZS00MjlhLTg5NTctYTZjNjE3NTlkMDg0IiwiY2xpZW50X2lkIjoiNDViM2VmODlkZmY0NDY5NThlOGNlZGI3YTg1YWQ4NTUiLCJzY29wZSI6WyJhbGwiXX0.qRT7xDm2g1lJ3olXJ6gLAfhoiFWTWOI7mu4QTWGe3cY");
        String post = OkHttpUtils.post("http://172.29.30.164:5557/fawde/jackfish-user/user/queryUserList", JacksonUtils.serialize(requestMap), headers);
        System.out.println(post);
        String s = OkHttpUtils.get("http://172.29.30.164:5557/fawde/jackfish-permission/permission/getAppPageElemTree/1",headers);
        UserCenterResponse parse = JacksonUtils.parse(s, UserCenterResponse.class);
        System.out.println(parse.getData());



    }
}
