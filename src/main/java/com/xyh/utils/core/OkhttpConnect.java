package com.xyh.utils.core;

import com.xyh.utils.common.OkHttpEnums;
import com.xyh.utils.model.MultipartFile;
import com.xyh.utils.model.OkHttpRequest;
import com.xyh.utils.model.OkHttpResponse;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by met3d on 2017/10/18.
 */
public class OkhttpConnect {
    private static OkHttpClient.Builder okHttpClientBuild;

    public static OkHttpResponse send(OkHttpRequest request) throws IOException {
        //取出所有请求参数
        Map<String, String> body = request.getBody();
        Map<String, String> headers = request.getHeaders();
        String url = request.getUrl();
        MediaType mediaType = request.getMediaType();
        OkHttpEnums.Method method = request.getMethod();
        long timeout = request.getTimeout();
        long readTimeout = request.getReadTimeout();
        TimeUnit timeUnit = request.getTimeUnit();
        File file = request.getFile();
        String content = request.getContent();
        byte[] bytes = request.getBytes();
        List<MultipartFile> multipartFiles = request.getMultipartFiles();
        Proxy proxy = request.getProxy();

        //设置连接和读取超时时间
        if (okHttpClientBuild == null) {
            synchronized (OkhttpConnect.class) {
                if (okHttpClientBuild == null) {
                    okHttpClientBuild = new OkHttpClient.Builder();
                    ConnectionPool connectionPool = new ConnectionPool(50,5,TimeUnit.MINUTES);
                    okHttpClientBuild.connectionPool(connectionPool);
                }
            }
        }
        okHttpClientBuild
                .connectTimeout(timeout, timeUnit)
                .readTimeout(readTimeout, timeUnit);

        //设置代理
        if (proxy != null) {
            okHttpClientBuild.proxy(proxy);
        }

        OkHttpClient okHttpClient = okHttpClientBuild.build();
        Request.Builder okRequestBuilder = new Request.Builder();
        //构建请求头
        if (headers != null) {
            Headers.Builder headersBuilder = new Headers.Builder();
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> e : entrySet) {
                headersBuilder.add(e.getKey(), e.getValue());
            }
            okRequestBuilder.headers(headersBuilder.build());
        }
        //根据method构建请求体
        if (method == OkHttpEnums.Method.POST) {
            if (mediaType.toString() == MediaType.parse("application/x-www-form-urlencoded").toString()) {
                //表单方式
                if (body != null) {
                    FormBody.Builder bodyBuild = new FormBody.Builder();
                    Set<Map.Entry<String, String>> entrySet = body.entrySet();
                    for (Map.Entry<String, String> e : entrySet) {
                        bodyBuild.add(e.getKey(), e.getValue());
                    }
                    okRequestBuilder.post(bodyBuild.build());
                }
            } else {
                //文件方式
                if (file != null) {
                    okRequestBuilder.post(RequestBody.create(mediaType, file));
                } else if (content != null){
                    okRequestBuilder.post(RequestBody.create(mediaType, content));
                } else {
                    okRequestBuilder.post(RequestBody.create(mediaType, bytes));
                }
            }
        } else if (method == OkHttpEnums.Method.MULTIPART_POST) {
            //multipart复杂类型请求体构造
            MultipartBody.Builder BodyBuild = new MultipartBody.Builder();
            if (body != null) {
                Set<Map.Entry<String, String>> entrySet = body.entrySet();
                for (Map.Entry<String, String> e : entrySet) {
                    BodyBuild.addFormDataPart(e.getKey(), e.getValue());
                }
            }
            for (MultipartFile multipartFile : multipartFiles) {
                BodyBuild.addFormDataPart(multipartFile.getType(), multipartFile.getName(),
                        RequestBody.create(multipartFile.getMediaType(), multipartFile.getFile()));
            }
            okRequestBuilder.post(BodyBuild.build());
        } else if (method == OkHttpEnums.Method.GET) {
            //get请求
            if (body != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(url);
                sb.append("?");
                Set<Map.Entry<String, String>> entrySet = request.getBody().entrySet();
                for (Map.Entry<String, String> e : entrySet) {
                    sb.append(e.getKey() + "=" + e.getValue() + "&");
                }
                url = sb.substring(0, sb.length() - 1);
            }
        }
        okRequestBuilder.url(url);

        //生成请求对象
        Request okRequest = okRequestBuilder.build();
        Call call = okHttpClient.newCall(okRequest);
        //获取响应对象
        Response response = call.execute();
        //包装并返回响应
        return new OkHttpResponse.Builder().response(response).build();
    }

}
