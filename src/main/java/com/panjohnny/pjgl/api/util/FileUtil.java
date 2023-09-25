package com.panjohnny.pjgl.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class FileUtil {
    public static InputStream resolveFileOrResource(String path) throws FileNotFoundException{
        // attempt to load resource
        InputStream is = FileUtil.class.getResourceAsStream(path);
        if (is != null)
            return is;

        File f = new File(path);
        if (!f.exists()) {
            throw new FileNotFoundException("Failed to resolve file/resource: " + path);
        }

        return new FileInputStream(f);
    }
}
