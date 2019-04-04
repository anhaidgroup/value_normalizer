package edu.wisc.entity.normalizer.services;

import edu.wisc.entity.normalizer.model.CdriveDownload;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    public String uploadFile(String file, String token) {
        try {
            String cdriveUrl = ConfigurationService.CDRIVE_UPLOAD_URL;
            FileSystemResource fs = new FileSystemResource(ConfigurationService.RESOURCE_LOCATION + file);
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + token);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            map.add("file", fs);

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(cdriveUrl, request, String.class);
            
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e){
            return e.toString();
        }
    }
}
