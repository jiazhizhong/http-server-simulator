package com.dayrain.utils;

import com.dayrain.entity.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils{

    private static String configPath = Thread.currentThread().getContextClassLoader().getResource("resources/config.json").getFile();

    public static void saveConfig(Configuration configuration){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(configPath);
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
        BufferedReader bufferedReader = null;
        try {
            StringBuilder configStr = new StringBuilder();
            bufferedReader = new BufferedReader(new FileReader(configPath));
            String buf = null;
            while ((buf = bufferedReader.readLine()) != null) {
                configStr.append(buf);
            }
            return new ObjectMapper().readValue(configStr.toString(), Configuration.class);
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
}
