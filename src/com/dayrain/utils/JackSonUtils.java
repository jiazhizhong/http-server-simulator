package com.dayrain.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JackSonUtils {

    public static String getFromInputStream(InputStream inputStream) {
        try {
            return new ObjectMapper().readValue(inputStream, String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
