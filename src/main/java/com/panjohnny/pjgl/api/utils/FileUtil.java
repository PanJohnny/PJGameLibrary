package com.panjohnny.pjgl.api.utils;

import java.io.*;

@SuppressWarnings("unused")
public final class FileUtil {
    public static String readFile(String fileName) {
        try {
            return readStream(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static String readResource(String resourceName) {
        return readStream(FileUtil.class.getResourceAsStream(resourceName));
    }

    public static String readStream(InputStream stream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}
