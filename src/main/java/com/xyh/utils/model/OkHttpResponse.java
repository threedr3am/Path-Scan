package com.xyh.utils.model;

import okhttp3.MediaType;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xuanyonghao on 2017/10/18.
 */
public class OkHttpResponse {

    private String strBody;//字符串响应体
    private InputStream inputStream;//字节输入流
    private byte[] bytesBody;//字节响应体
    private Map<String,List<String>> headers;//相应头集合
    private MediaType mediaType;//MIME
    private Response response;//OkHttp响应体
    private int code;

    public OkHttpResponse(Response response) {
        this.response = response;
    }

    public String getStrBody() throws IOException {
        if (strBody == null) {
            strBody = response.body().string();
        }
        return this.strBody;
    }

    public InputStream getInputStream() {
        if (inputStream == null) {
            inputStream = response.body().byteStream();
        }
        return inputStream;
    }

    public byte[] getBytesBody() throws IOException {
        if (bytesBody == null) {
            bytesBody = response.body().bytes();
        }
        return bytesBody;
    }

    public Map<String, List<String>> getHeaders() {
        if (headers == null) {
            headers = response.headers().toMultimap();
        }
        return headers;
    }

    public String getHeader(String key){
        if (headers == null) {
            headers = response.headers().toMultimap();
        }
        List<String> list = headers.get(key.toLowerCase());
        return list != null?(list.stream().collect(Collectors.joining(","))):null;
    }

    public MediaType getMediaType() {
        if (mediaType == null) {
            mediaType = response.body().contentType();
        }
        return mediaType;
    }

    public int getCode() {
        if (code == 0) {
            code = response.code();
        }
        return code;
    }

    public Response getResponse() {
        return response;
    }

    public static class Builder{

        private Response response;

        public OkHttpResponse build(){
            return new OkHttpResponse(this.response);
        }

        public Builder response(Response response){
            this.response = response;
            return this;
        }
    }
}
