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
    public static String CDRIVE_DOWNLOAD_URL="http://ec2-3-91-133-41.compute-1.amazonaws.com:8080/cdrive/download/?file_name=";
    public static String CDRIVE_UPLOAD_URL="http://ec2-3-91-133-41.compute-1.amazonaws.com:8080/cdrive/upload/";

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
