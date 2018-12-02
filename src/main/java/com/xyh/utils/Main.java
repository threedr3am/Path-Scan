package com.xyh.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author xuanyh
 * @create 2018-12-02 20:32
 **/
public class Main {
    private static Logger log = Logger.getLogger(Main.class.toString());

    static {
        log.setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        if (args.length != 1 || args[0].length() == 0)
            log.log(Level.SEVERE, "参数错误，正确使用方法，例：java -jar path-scan.jar http://www.baidu.com");
        PathScan pathScan = new PathScan();
        pathScan.init();
        pathScan.scan(args[0]);
        System.exit(1);
    }
}
