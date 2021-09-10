package com.daughter.qinghan.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.FormBody.Builder;

import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * The type Ok http utils.
 *
 * @author fangxi
 */
@Slf4j
public class OkHttpUtils {

    private static final String HTTP_JSON = "application/json; charset=utf-8";
    private static final String HTTP_FORM = "application/x-www-form-urlencoded; charset=utf-8";

    private static final int OK = 200;

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(6L, TimeUnit.SECONDS)
            .readTimeout(1L, TimeUnit.MINUTES)
            .writeTimeout(1L, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .connectionPool(new ConnectionPool(20, 300, TimeUnit.SECONDS))
            .build();


    /**
     * Gets ok http client.
     *
     * @return the ok http client
     */
    public static OkHttpClient getOkHttpClient() {
        return OK_HTTP_CLIENT;
    }

    /**
     * Get string.
     *
     * @param url     the url
     * @param headers the headers
     * @param params  the params
     * @return the string
     */
    public static String get(String url, Map<String, String> headers, Object params) {
        Map<String, Object> paramsMap = BeanUtils.describe(params);
        if (CollectionUtils.isEmpty(paramsMap)) {
            return get(url, headers);
        }
        Request.Builder builder = new Request.Builder();
        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(builder::header);
        }
        StringBuilder sb = new StringBuilder(url).append("?");
        paramsMap.forEach((key, value) -> {
            if (key != null && value != null) {
                sb.append(key).append("=").append(value).append("&");
            }
        });
        url = sb.substring(0, sb.length() - 1);
        Request request = builder.get().url(url).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.code() == OK) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return "";
    }

    /**
     * Get string.
     *
     * @param url    the url
     * @param params the params
     * @return the string
     */
    public static String get(String url, Object params) {
        return get(url, Collections.emptyMap(), params);
    }

    /**
     * get请求
     * 对于小文档，响应体上的string()方法非常方便和高效。
     * 但是，如果响应主体很大(大于1 MB)，则应避免string()，
     * 因为它会将整个文档加载到内存中。在这种情况下，将主体处理为流。
     *
     * @param url the url
     * @return string string
     */
    public static String get(String url) {
        return get(url, Collections.emptyMap());
    }

    /**
     * Get string.
     *
     * @param url     the url
     * @param headers the headers
     * @return the string
     */
    public static String get(String url, Map<String, String> headers) {
        Assert.hasLength(url, "url must not be null");
        Request.Builder builder = new Request.Builder();
        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(builder::header);
        }
        Request request = builder.get().url(url).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.code() == OK) {
                return Objects.requireNonNull(response.body()).string();
            } else {
                log.error("Http GET 请求失败; [error Code = {} , url={}]", response.code(), url);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return "";
    }

    /**
     * Get byte byte [ ].
     *
     * @param url the url
     * @return the byte [ ]
     */
    public static byte[] getByte(String url) {
        return getByte(url, Collections.emptyMap());
    }

    /**
     * Get byte byte [ ].
     *
     * @param url     the url
     * @param headers the headers
     * @return the byte [ ]
     */
    public static byte[] getByte(String url, Map<String, String> headers) {
        Assert.hasLength(url, "url must not be null");
        Request.Builder builder = new Request.Builder();
        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(builder::header);
        }
        Request request = builder.get().url(url).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.code() == OK) {
                return Objects.requireNonNull(response.body()).bytes();
            } else {
                log.error("Http GET 请求失败; [error Code = {} , url={}]", response.code(), url);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return null;
    }

    /**
     * 同步 POST调用 无Header
     *
     * @param url  the url
     * @param body the body
     * @return string string
     */
    public static String post(String url, String body) {
        return post(url, body, Collections.emptyMap());
    }

    /**
     * 同步 POST调用 有Header
     *
     * @param url     the url
     * @param content the content
     * @param headers the headers
     * @return string string
     */
    public static String post(String url, String content, Map<String, String> headers) {
        Assert.hasLength(url, "url must not be null");
        RequestBody requestBody = RequestBody.create(MediaType.parse(HTTP_JSON), content);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(requestBuilder::addHeader);
        }
        Request request = requestBuilder.post(requestBody).build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.code() == OK) {
                log.info("http Post 请求成功; url={}, requestContent={}", url, content);
            } else {
                log.error("Http POST 请求失败; [ errorCode = {}, url={}, param={}]", response.code(), url, content);
            }
            ResponseBody body = response.body();
            if (body == null) {
                return null;
            }
            return body.string();
        } catch (IOException e) {
            throw new RuntimeException("同步http请求失败,url:" + url, e);
        }
    }

    /**
     * Form string.
     *
     * @param url     the url
     * @param content the content
     * @return the string
     */
    public static String form(String url, Object content) {
        return form(url, BeanUtils.describe(content), Collections.emptyMap());
    }

    /**
     * Form string.
     *
     * @param url     the url
     * @param content the content
     * @param headers the headers
     * @return the string
     */
    public static String form(String url, Object content, Map<String, String> headers) {
        return form(url, BeanUtils.describe(content), headers);
    }

    /**
     * Form string.
     *
     * @param url     the url
     * @param content the content
     * @return the string
     */
    public static String form(String url, Map<String, Object> content) {
        return form(url, content, Collections.emptyMap());
    }

    /**
     * 提交表单
     *
     * @param url     the url
     * @param content the content
     * @param headers the headers
     * @return string string
     */
    public static String form(String url, Map<String, Object> content, Map<String, String> headers) {
        Assert.hasLength(url, "url must not be null");
        Builder fromBodyBuilder = new Builder();
        content.forEach((key, value) -> fromBodyBuilder.add(key, value.toString()));
        FormBody body = fromBodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(requestBuilder::addHeader);
        }
        Request request = requestBuilder
                .post(body)
                .build();

        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                log.error("Http Post Form请求失败,[url={}, param={}]", url, content);
                return null;
            }
            log.info("postDataByForm, [postUrl={}, requestBody: {}, response: {}]", url, content, response);
            return responseBody.string();
        } catch (IOException e) {
            log.error("Http Post Form请求失败,[url={}, param={}]", url, content, e);
        }
        return null;
    }

    /**
     * 提交表单
     *
     * @param url     the url
     * @param content the content
     * @return string string
     */
    public static String form(String url, String content) {
        return form(url, content, Collections.emptyMap());
    }

    /**
     * 异步Http调用参考模板：Get、Post、Put
     * 需要异步调用的接口一般情况下你需要定制一个专门的Http方法
     *
     * @param <T>          the type parameter
     * @param httpMethod   the http method
     * @param url          the url
     * @param headers      the headers
     * @param content      the content
     * @param respConsumer the resp consumer
     * @return async result
     */
    public static <T> AsyncResult<T> asyncHttpByJson(HttpMethod httpMethod,
                                                     String url,
                                                     Map<String, String> headers,
                                                     String content,
                                                     Consumer<Response> respConsumer) {
        RequestBody body = RequestBody.create(MediaType.parse(HTTP_JSON), content);

        Request.Builder requestBuilder = new Request.Builder()
                .url(url);

        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach(requestBuilder::header);
        }

        switch (httpMethod) {
            case GET:
                requestBuilder.get();
                break;
            case POST:
                requestBuilder.post(body);
                break;
            default:
        }

        Request request = requestBuilder.build();
        Call call = OK_HTTP_CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                log.error("异步http {} 请求失败,[url={}, param={}]", httpMethod.name(), url, content);
                throw new RuntimeException("异步http请求失败,url:" + url);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (response.code() == OK) {
                    respConsumer.accept(response);
                } else {
                    log.error("异步http {} 请求失败,错误码为{},请求参数为[url={}, param={}]", httpMethod.name(), response.code(),
                            url, content);
                }
            }
        });
        return new AsyncResult<>(null);
    }

    /**
     * lambda表达式异步调用http模板,不建议使用
     *
     * @param request          the request
     * @param failure          the failure
     * @param responseConsumer the response consumer
     */
    public static void asyncCall(Request request, Consumer<Exception> failure, Consumer<Response> responseConsumer) {
        OK_HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                failure.accept(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                responseConsumer.accept(response);
            }
        });
    }

}
