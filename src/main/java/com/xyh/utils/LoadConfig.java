package com.xyh.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuanyh
 * @create 2018-12-02 20:33
 **/
@Setter
@Getter
public class LoadConfig {
    private List<String> paths;
    private static final String DEFAULT_PATH_CONFIG = "dict";

    private Map<String, String> config;
    private static final String DEFAULT_CONFIG = "config/CONFIG.ini";

    public static LoadConfig build() throws FileNotFoundException {
        LoadConfig loadConfig = new LoadConfig();
        //加载配置文件
        Map<String, String> config = loadConfigFile();
        loadConfig.setConfig(config);
        List<String> pathList = new ArrayList<>();

        //获取jar包所在目录路径
        String currentPath = LoadConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int endIndex = currentPath.lastIndexOf("/");
        currentPath = currentPath.substring(0, endIndex+1);
        File file = new File(currentPath+DEFAULT_PATH_CONFIG);

        if (config.containsKey(DEFAULT_PATH_CONFIG)) {
            String[] paths = config.get(DEFAULT_PATH_CONFIG).split(",");
            for (String path : paths) {
                if (file.exists()) {
                    pathList.addAll(loadPhpPathsFromConfigFile(new FileInputStream(file.getPath()+"/"+path)));
                } else {
                    InputStream inputStream = LoadConfig.class.getClassLoader().getResourceAsStream(DEFAULT_PATH_CONFIG);
                    pathList.addAll(loadPhpPathsFromConfigFile(inputStream));
                }
            }
        } else {
            for (File pathFile : file.listFiles()) {
                pathList.addAll(loadPhpPathsFromConfigFile(new FileInputStream(pathFile)));
            }
        }
        loadConfig.setPaths(pathList);
        return loadConfig;
    }

    private static List<String> loadPhpPathsFromConfigFile(InputStream inputStream) {
        return readFile(inputStream);
    }

    private static Map<String, String> loadConfigFile() throws FileNotFoundException {
        String currentPath = LoadConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int endIndex = currentPath.lastIndexOf("/");
        currentPath = currentPath.substring(0, endIndex+1);
        File file = new File(currentPath+DEFAULT_CONFIG);

        List<String> configs = readFile(file.exists() ? new FileInputStream(file) : LoadConfig.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG));
        Map<String, String> config = new HashMap<>();
        for (String line : configs) {
            String[] lines = line.split("=");
            config.put(lines[0], lines[1]);
        }
        return config;
    }

    /**
     * 文件读取，每行一个item
     *
     * @param inputStream
     * @return
     */
    private static List<String> readFile(InputStream inputStream) {
        List<String> shells = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                shells.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shells;
    }
}
