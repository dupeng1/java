package com.example.demo.proxy;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileUtil {

    public static Set<File> getFilesForSuffixx(File parentFile, String suffix){
        Set<File> files = new HashSet<>();
        File[] allFiles = parentFile.listFiles();
        for(File file : allFiles) {
            if (!file.isDirectory()) {
                if(file.getName().endsWith(suffix)){
                    files.add(file);
                }
            } else {
                files.addAll( getFilesForSuffixx(file,suffix));
            }
        }
        return files;
    }
}
