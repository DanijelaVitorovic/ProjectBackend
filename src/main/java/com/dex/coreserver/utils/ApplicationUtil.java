package com.dex.coreserver.utils;

import java.util.ResourceBundle;

public class ApplicationUtil {

    public static String getFilePath(){
        ResourceBundle applicationRB = ResourceBundle.getBundle("application");
        String defaultPath = null;
        try{
            defaultPath = applicationRB.getString("file.path");
        }catch (Exception e){
            defaultPath = "C:/DMS/";
        }
        return defaultPath;
    }
}
