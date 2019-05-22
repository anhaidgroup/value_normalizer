package edu.wisc.entity.normalizer.services;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private static String WINDOWS_LOCATION="src/main/resources/csv/";
    private static String LINUX_LOCATION="/normalizer/csv/";
    public static String RESOURCE_LOCATION;
    public static String CDRIVE_DOWNLOAD_URL="http://a7648f6f5702911e98ea412ac368fc7a-1169430973.us-east-1.elb.amazonaws.com/download/?file_name=";
    public static String CDRIVE_UPLOAD_URL="http://a7648f6f5702911e98ea412ac368fc7a-1169430973.us-east-1.elb.amazonaws.com/upload/";

    /* Start Development Variables
    * The variables defined below are only used for development
    * They might change during production or need to be requested via an API
    */
    public static String CLIENT_ID = "RGp7dOIKaZl2vxA7g6aObpl6SD9nZX2skq1yiE69";
    public static String CLIENT_SECRET = "5HcdLddsZdTgVLqNmicSGGlSbnLiv6nKtCTznGheGtTfppphdo8nglhJoHiWm7X6zY4M2LSW1K4H5EWsoR9hdN1yUsp0dps5O3oAW9rAbM9e9q6EO9ZOltGJ3uhGn12a";
    public static String REDIRECT_URI = "http://localhost:4200";
    public static String AUTH_URL = "http://a250afd7c6eba11e98ea412ac368fc7a-312971903.us-east-1.elb.amazonaws.com/o/token/";
    /* End Development Variables
    */
    public ConfigurationService() {
        if (SystemUtils.IS_OS_LINUX) {
            RESOURCE_LOCATION = LINUX_LOCATION;
        } else if (SystemUtils.IS_OS_WINDOWS) {
            RESOURCE_LOCATION = WINDOWS_LOCATION;
        } else {
            RESOURCE_LOCATION = LINUX_LOCATION;
        }
        System.out.println(WINDOWS_LOCATION);
        System.out.println(LINUX_LOCATION);
        System.out.println(RESOURCE_LOCATION);
    }
}
