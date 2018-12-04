package com.xyh.utils.model;

import com.xyh.utils.common.OkHttpEnums;
import okhttp3.MediaType;

import java.io.File;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuanyonghao on 2017/10/18.
 */
public class OkHttpRequest {

    private Map<String,String> body;//请求体
    private Map<String,String> headers;//请求头
    private String url;//地址url
    private MediaType mediaType;//请求体内容类型
    private OkHttpEnums.Method method;//请求方式
    private long timeout;//连接超时时间
    private long readTimeout;//读取超时时间
    private TimeUnit timeUnit;//超时时间单位
    private File file;//请求体单文件
    private String content;
    private byte[] bytes;
    private List<MultipartFile> multipartFiles = null;//multipart上传体
    private Proxy proxy = null;//代理

    public OkHttpRequest(Map<String, String> body, Map<String, String> headers, String url, MediaType mediaType, OkHttpEnums.Method method, long timeout, long readTimeout, TimeUnit timeUnit, File file, String content, byte[] bytes, List<MultipartFile> multipartFiles, Proxy proxy) {
        this.body = body;
        this.headers = headers;
        this.url = url;
        this.mediaType = mediaType;
        this.method = method;
        this.timeout = timeout;
        this.readTimeout = readTimeout;
        this.timeUnit = timeUnit;
        this.file = file;
        this.content = content;
        this.bytes = bytes;
        this.multipartFiles = multipartFiles;
        this.proxy = proxy;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public OkHttpEnums.Method getMethod() {
        return method;
    }

    public long getTimeout() {
        return timeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public File getFile() {
        return file;
    }

    public String getContent() {
        return content;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public List<MultipartFile> getMultipartFiles() {
        return multipartFiles;
    }

    public static class Builder{

        private Map<String,String> body;//请求体
        private Map<String,String> headers;//请求头
        private String url;//地址url
        private final String DEFAULT_POST_MEDIA = "application/x-www-form-urlencoded";//默认mime
        private MediaType mediaType = MediaType.parse(DEFAULT_POST_MEDIA);
        private OkHttpEnums.Method method = DEFAULT_METHOD;
        private long timeout = DEFAULT_TIMEOUT;
        private long readTimeout = DEFAULT_READ_TIMEOUT;
        private TimeUnit timeUnit = DEFAULT_TIMEUNIT;
        private File file;
        private String content;
        private byte[] bytes;
        private List<MultipartFile> multipartFiles = null;
        private Proxy proxy = null;
        private static final long DEFAULT_TIMEOUT = 4000;
        private static final long DEFAULT_READ_TIMEOUT = 4000;
        private static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MILLISECONDS;
        private static final OkHttpEnums.Method DEFAULT_METHOD = OkHttpEnums.Method.GET;

        public OkHttpRequest build(){
            return new OkHttpRequest(this.body,this.headers,this.url,this.mediaType,this.method,this.timeout,this.readTimeout,this.timeUnit,this.file,this.content,this.bytes,this.multipartFiles,this.proxy);
        }

        public Builder body(Map<String,String> body){
            this.body = body;
            return this;
        }

        public Builder headers(Map<String,String> headers){
            this.headers = headers;
            return this;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder media(MediaType mediaType){
            this.mediaType = mediaType;
            return this;
        }

        public Builder method(OkHttpEnums.Method method){
            this.method = method;
            return this;
        }

        public Builder timeout(long timeout,long readTimeout,TimeUnit timeUnit){
            this.timeout = timeout;
            this.readTimeout = readTimeout;
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder content(File file) {
            if (content != null) {
                content = null;
            }
            this.file = file;
            return this;
        }

        public Builder content(String content) {
            if (file != null) {
                file = null;
            }
            this.content = content;
            return this;
        }

        public Builder content(byte[] bytes) {
            if (file != null) {
                file = null;
            }
            this.bytes = bytes;
            return this;
        }

        public Builder multipartFile(MultipartFile multipartFile) {
            if (multipartFiles == null) {
                multipartFiles = new ArrayList<>();
            }
            multipartFiles.add(multipartFile);
            return this;
        }

        public Builder proxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }
    }
}
