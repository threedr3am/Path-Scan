package com.xyh.utils.Common;

/**
 * Created by xuanyonghao on 2017/10/18.
 */
public class OkHttpEnums {
    /**
     * 请求类型枚举
     */
    public enum Method{
        GET("GET",1),
        POST("POST",2),
        MULTIPART_POST("MULTIPART_POST",3),
        ;
        private String name;
        private int type;

        Method(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
