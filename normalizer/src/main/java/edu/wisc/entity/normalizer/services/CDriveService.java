package edu.wisc.entity.normalizer.services;

import edu.wisc.entity.normalizer.model.CdriveDownload;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CDriveService {
    public String getDownloadLink(String file, String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String cdriveUrl = ConfigurationService.CDRIVE_DOWNLOAD_URL + file;
            System.out.println("Cdrive URL = " + cdriveUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + token);
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<CdriveDownload> response = restTemplate.exchange(cdriveUrl, HttpMethod.GET, request, CdriveDownload.class);
            CdriveDownload downloadLink = response.getBody();
            return downloadLink.getDownload_url();
        } catch (Exception e){
            return e.toString();
        }
    }
}
