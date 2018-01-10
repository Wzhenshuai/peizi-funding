package com.icaopan.web.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    //安全加解密文件
    public static Properties securityProperties;
    private static Logger logger = Logger.getLogger(PropertyReader.class);

    public static Properties getSecurityProperties() {
        securityProperties = readPropertyFiles("security.properties");
        return securityProperties;
    }


    public static Properties readPropertyFiles(String propertyFilePath) {
        Properties prop = new Properties();
        InputStream in = PropertyReader.class.getClassLoader()
                .getResourceAsStream(propertyFilePath);
        try {
            prop.load(in);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return prop;
    }

    public static String getSecretKey() {
        return getSecurityProperties().getProperty("secretKey");
    }

    public static String getPasswordSecretKey() {
        return getSecurityProperties().getProperty("passWordSecretKey");
    }

}
