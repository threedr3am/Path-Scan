package com.xyh.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author met3d
 * @create 2018-12-02 21:17
 **/
public class ExportFile {
    private static Logger log = Logger.getLogger(PathScan.class.toString());

    static {
        log.setLevel(Level.INFO);
    }

    private static final String DEFAULT_RESULT_PATH = "export";
    private static final String DEFAULT_RESULT_FILE = "/results-%s.txt";

    public static void exportFile(String host, List<ScanResult> scanResults) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD_HH:mm:ss");
        try {
            String currentPath = LoadConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            int endIndex = currentPath.lastIndexOf("/");
            currentPath = currentPath.substring(0, endIndex+1);
            File file = new File(currentPath+DEFAULT_RESULT_PATH);
            if (!file.exists())
                file.mkdir();

            String path = file.getPath() + String.format(DEFAULT_RESULT_FILE, simpleDateFormat.format(new Date()));
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            StringBuilder topRes = new StringBuilder();
            topRes.append("扫描站点：").append(host).append("\n");
            topRes.append("可达路径：").append(scanResults.size());
            bufferedWriter.write(topRes.toString());
            bufferedWriter.newLine();
            for (ScanResult scanResult : scanResults) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[ ").append(scanResult.getResponseCode()).append(" ] ").append("地址：").append(scanResult.getUrl());
                String export = stringBuilder.toString();
                bufferedWriter.write(export);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
