package com.dayrain.utils;

import com.dayrain.component.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils{

    private static String configPath = getResourcePath("config.json");

    public static void saveConfig(Configuration configuration){
        saveConfig(configuration, new File(configPath));
    }

    public static void saveConfig(Configuration configuration, File file){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            String config = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(configuration);
            fileWriter.write(config);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Configuration load() {
        return load(new File(configPath));
    }

    public static Configuration load(File file) {
        BufferedReader bufferedReader = null;
        try {
            StringBuilder configStr = new StringBuilder();
            bufferedReader = new BufferedReader(new FileReader(file));
            String buf = null;
            while ((buf = bufferedReader.readLine()) != null) {
                configStr.append(buf);
            }
            return "".equals(configStr.toString()) ? new Configuration(1200, 800, 8, 8) : new ObjectMapper().readValue(configStr.toString(), Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getFromInputStream(InputStream inputStream) {
        try {
            byte[]buf = new byte[4096];
            int len = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((len = inputStream.read(buf)) != -1) {
                stringBuilder.append(new String(buf, 0, len));
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getResourcePath(String fileName) {
        String file = Thread.currentThread().getContextClassLoader().getResource("resources/" + fileName).getFile();
        return new File(file).toString();
    }

    public static String getResourcePathWithProtocol(String fileName) {
        String file = Thread.currentThread().getContextClassLoader().getResource("resources/" + fileName).getFile();
        return "file:" + File.separator + new File(file);
    }

}
