package com.gmail.at.sichyuriyy.onlinestore.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Yuriy on 4/1/2017.
 */
public class ResourcesUtil {

    public static File getResourceFile(String fileName) {
        URL url = ResourcesUtil.class.getClassLoader().getResource(fileName);

        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Bad path" ,e);
        }

        if (file.exists()) {
            return file;
        } else {
            throw new IllegalArgumentException("Cannot load resource");
        }
    }
}
