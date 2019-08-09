package com.xyh.utils;

import com.xyh.utils.common.OkHttpEnums;
import com.xyh.utils.core.OkhttpConnect;
import com.xyh.utils.model.OkHttpRequest;
import com.xyh.utils.model.OkHttpResponse;
import junit.framework.TestCase;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by met3d on 2017/10/24.
 */
public class Test extends TestCase{
    public void test(){
        OkHttpRequest okHttpRequest = new OkHttpRequest.Builder()
                .url("http://www.baidu.com")
                .method(OkHttpEnums.Method.GET)
                .build();
        try {
            OkHttpResponse okHttpResponse = OkhttpConnect.send(okHttpRequest);
            String cookies = okHttpResponse.getHeader("Set-Cookie");
            System.out.println(cookies);
            Map<String,List<String>> headers = okHttpResponse.getHeaders();
            Set<Map.Entry<String,List<String>>> entrySet = headers.entrySet();
            for (Map.Entry<String,List<String>> entry:entrySet){
                System.out.println(entry.getKey()+":"+entry.getValue().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void testDevelop() throws IOException {
        File file = new File("README.md");
        OkHttpRequest okHttpRequest = new OkHttpRequest.Builder()
                .url("http://www.baidu.com")
                .method(OkHttpEnums.Method.MULTIPART_POST)
                .content(file)
                .media(MediaType.parse("text/x-markdown; charset=utf-8"))
                .build();
        OkHttpResponse response = OkhttpConnect.send(okHttpRequest);
        System.out.println(response.getStrBody());
    }
}
