package edu.wisc.entity.normalizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class NormalizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NormalizerApplication.class, args);
        System.out.println("TESTING APPLICATION!");
    }
}
