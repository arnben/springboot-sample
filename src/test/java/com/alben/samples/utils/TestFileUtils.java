package com.alben.samples.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TestFileUtils {
    public static <T> T getContent(String fileName, Class<T> returnType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(
                    new File(TestFileUtils.class.getClassLoader().getResource(fileName).getFile()),
                    returnType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringContent(String fileName) {
        try {
            return FileUtils.readFileToString( new File(TestFileUtils.class.getClassLoader().getResource(fileName).getFile()), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
