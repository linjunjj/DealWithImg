package com.linjun.utils;

import java.io.File;
import java.util.Vector;


/**
 * @author eguid
 *
 */
public class Util {

    /**
     * get all files under the directory path
     * 
     * @param
     * @param
     */
    public static String SnakeToCamel(String src) {
        if (src == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = src.toLowerCase().split("_");
        for (String string : strings) {
            if (string.length() < 1)
                continue;
            stringBuilder.append(string.substring(0, 1).toUpperCase()).append(string.substring(1));
        }

        return stringBuilder.toString();
    }


    public static void getFiles(final String path, Vector<String> files) {
        getFiles(new File(path), files);
    }

    /**
     * delete and create a new directory with the same name
     * 
     * @param dir
     */
    public static void recreateDir(final String dir) {
        new File(dir).delete();
        new File(dir).mkdir();
    }
    
    private static void getFiles(final File dir, Vector<String> files) {
        File[] filelist = dir.listFiles();
        for (File file : filelist) {
            if (file.isDirectory()) {
                getFiles(file, files);
            } else {
                files.add(file.getAbsolutePath());
            }
        }
    }
}
