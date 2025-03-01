package com.filehandler.sibaram.converter.controller;


import com.filehandler.sibaram.converter.service.FileConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file-converter")
@Slf4j
public class FileConverterController {

    @Autowired
    private FileConverterService fileConverterService;

    @Value("${file.save.location}")
    private String savedLocation;

    @PostMapping("/upload")
    public ResponseEntity<String> xmlToJsonConvertorController(@RequestParam("file") MultipartFile multipartFile) {
        log.info("Generating JSON file from XML file");
        try {
            if (multipartFile != null && multipartFile.getOriginalFilename().endsWith(".xml")) {
                String generatedJsonFile = fileConverterService.generateJsonFromExcel(multipartFile, savedLocation);
                return new ResponseEntity<>("File generatedd successfully at " + savedLocation + " with name : " +
                        generatedJsonFile, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Please upload XML file only", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            log.error("Error generating JSON file", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
