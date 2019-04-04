package edu.wisc.entity.normalizer.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wisc.entity.normalizer.model.KeyValue;
import edu.wisc.entity.normalizer.services.CDriveService;
import edu.wisc.entity.normalizer.services.ConfigurationService;
import edu.wisc.entity.normalizer.services.StorageService;
import edu.wisc.entity.normalizer.util.CsvToJsonConverter;
import edu.wisc.entity.normalizer.util.CsvUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class FileViewController {

    @Autowired
    StorageService storageService;

    @Autowired
    CDriveService cDriveService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("In Upload Controller");

        String message = "";
        try {
            storageService.store(file);
            message = "{\"file\":\"" + file.getOriginalFilename() + "\"}";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            System.out.println(message);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @RequestMapping(value = "/cdrive/download", params = {"url", "token"},  method = RequestMethod.GET)
    public ResponseEntity<String> cdriveDownload(@RequestParam(value = "url") String file, @RequestParam(value = "token") String token) throws IOException, URISyntaxException {
        System.out.println("In Cdrive controller Controller");
        System.out.println(file);
        String message = "";
        URL s3Link = null;
        try {
            String download_link = cDriveService.getDownloadLink(file, token);
            System.out.println("Download Link " + download_link);
            s3Link = new URL(download_link);
            storageService.store(s3Link);
            message = "{\"file\":\"" + FilenameUtils.getName(s3Link.getPath()) + "\"}";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e){
            message = "FAIL to upload " + FilenameUtils.getName(s3Link.getPath()) + "!";
            System.out.println(message);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @RequestMapping(value = "/file/download/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("name") String fileName) {
        return new FileSystemResource(storageService.getFile(fileName));
    }

    @RequestMapping("/")
    public String index() {
        return "Welcome to Entity Normalizer Project!";
    }

    @RequestMapping(value = "/file", params = {"name"},  method = RequestMethod.GET, produces = "application/json")
    public String read(@RequestParam(value = "name") String name) throws IOException {
        System.out.println(name);
        File csvFile = new File(ConfigurationService.RESOURCE_LOCATION+name);
        return CsvToJsonConverter.readObjectsFromCsv(csvFile);
    }

    @RequestMapping(value = "/file/header", params = {"name"},  method = RequestMethod.GET, produces = "application/json")
    public String fileHeader(@RequestParam(value = "name") String name) throws IOException {
        System.out.println(name);
        File csvFile = new File(ConfigurationService.RESOURCE_LOCATION+name);
        String headers = CsvToJsonConverter.getHeaders(csvFile);
        return headers;
    }

    @RequestMapping(value = "/file/local", params = {"name", "column", "sort"},
            method = RequestMethod.GET, produces = "application/json")
    public String fileColumn(@RequestParam(value = "name") String name, @RequestParam(value = "column") String column,
                             @RequestParam(value = "sort") String sort ) {
        System.out.println(name);
        File csvFile = new File(ConfigurationService.RESOURCE_LOCATION+name);
        return CsvUtil.getCsvColumn(csvFile, Integer.valueOf(column), Integer.valueOf(sort));
    }

    @RequestMapping(value = "/file/global", params = {"name", "column"},
            method = RequestMethod.GET, produces = "application/json")
    public String fileColumn(@RequestParam(value = "name") String name, @RequestParam(value = "column") String column) {
        System.out.println(name);
        File csvFile = new File(ConfigurationService.RESOURCE_LOCATION+name);
        return CsvUtil.getGlobalColumn(csvFile, Integer.valueOf(column));
    }

    @PostMapping(value = "/local/diff/save", params = {"column", "name"})
    public ResponseEntity<String> saveLocalDiff(@RequestParam("keyval") String keyval, @RequestParam(value = "column") String column,
                                           @RequestParam(value = "name") String name) {
        System.out.println(keyval);
        String message = "";
        try {
            File csvFile = new File(ConfigurationService.RESOURCE_LOCATION+name);
            ObjectMapper mapper = new ObjectMapper();
            List<List<KeyValue>> keyValData = mapper.readValue(keyval, new TypeReference<List<List<KeyValue>>>(){});
            message = CsvUtil.writeCSVDiff(csvFile, Integer.valueOf(column), keyValData);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL save  ";
            System.out.println(message);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }

    }

    @PostMapping(value = "/global/diff/save", params = {"column", "name"})
    public ResponseEntity<String> saveGlobalDiff(@RequestParam("keyval") String keyval, @RequestParam(value = "column") String column,
                                           @RequestParam(value = "name") String name) {
//        System.out.println(keyval);
        String message = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            File csvFile = new File(ConfigurationService.RESOURCE_LOCATION+name);
            List<List<Integer>> keyValData = mapper.readValue(keyval, new TypeReference<List<List<Integer>>>(){});
//            keyValData.entrySet().forEach( (entry) -> {
//                System.out.print(entry.getKey() + " - ");
//                List<Integer> values = entry.getValue();
//                values.forEach(value -> {
//                    System.out.print(value + " - ");
//                });
//                System.out.println();
//            });
            System.out.println(keyValData);
            message = CsvUtil.writeGlobalDiff(csvFile, Integer.valueOf(column), keyValData);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to sace  ";
            System.out.println(message);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }

    }


}
