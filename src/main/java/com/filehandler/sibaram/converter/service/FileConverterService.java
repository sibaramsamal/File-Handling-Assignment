package com.filehandler.sibaram.converter.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileConverterService {
    String generateJsonFromExcel(MultipartFile multipartFile, String savedLocation) throws IOException;
}
