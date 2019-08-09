package com.xyh.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author met3d
 * @create 2018-12-02 20:54
 **/
@Setter
@Getter
public class ScanResult {
    //地址
    private String url;

    //HTTP响应码
    private int responseCode;
}
