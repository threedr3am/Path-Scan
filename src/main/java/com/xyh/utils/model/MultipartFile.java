package com.xyh.utils.model;

import okhttp3.MediaType;

import java.io.File;

/**
 * Created by met3d on 2017/10/30.
 */
public class MultipartFile {

    private String type = null;//名称
    private String name = null;//文件名称
    private File file = null;//文件
    private MediaType mediaType = null;//文件类型

    public MultipartFile(String type, String name, File file, MediaType mediaType) {
        this.type = type;
        this.name = name;
        this.file = file;
        this.mediaType = mediaType;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public String getType() {
        return type;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public static class Builder{

        private String type = null;//名称
        private String name = null;//文件名称
        private File file = null;//文件
        private MediaType mediaType = null;//文件类型

        public MultipartFile build() {
            return new MultipartFile(this.type,this.name,this.file,this.mediaType);
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public Builder media(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

    }
}
