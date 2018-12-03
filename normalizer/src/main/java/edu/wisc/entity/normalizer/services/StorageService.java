package edu.wisc.entity.normalizer.services;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    private final Path rootLocation = Paths.get("src/main/resources/csv");
    private final String storageLocation = "src/main/resources/csv/";

    public void store(MultipartFile file) {
        try {
            File newFile = new File(storageLocation+file.getOriginalFilename());
            FileUtils.copyToFile(file.getInputStream(), newFile);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("FAIL!");
        }
    }

}
