package com.company.tests;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHelper {

    public final static String PROPERTY_FILE = "dataSource";
    public final static String PROPERTY_HOME = "AddUsersIP";

    /*
    Get the first part of the file full path. Without name of the file.
     */
    public static String getPropertyHome() {
        File file = new File("");
        //System.out.println("path: " + file.getAbsolutePath());
        String propertyHome = file.getAbsolutePath();
        //System.out.println("propertyHome: " + propertyHome);
        char fileSeparator = System.getProperty("file.separator").charAt(0);
        //System.out.println("fileSeparator: " + fileSeparator);
        //propertyHome = propertyHome.replace('\\', fileSeparator);
        //propertyHome = propertyHome.replace('/', fileSeparator);
        //System.out.println("propertyHome: " + propertyHome);
        if (!propertyHome.endsWith(String.valueOf(fileSeparator))) {
            propertyHome = propertyHome + fileSeparator;
        }
        //System.out.println(propertyHome);
        return propertyHome;
    }

    public static Properties loadPropertiesFromPropertyHome() throws Exception {
        return loadPropertiesFromPropertyHome(PROPERTY_FILE);
    }

    public static Properties loadPropertiesFromPropertyHome(String propertyFileName) throws Exception {
        Properties properties = new Properties();
        File file = new File(getPropertyHome() + propertyFileName);
        if (!file.exists()) {
            return properties;
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            return properties;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return properties;
    }
}
