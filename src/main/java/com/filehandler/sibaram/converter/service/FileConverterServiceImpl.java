package com.filehandler.sibaram.converter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileConverterServiceImpl implements FileConverterService {
    /**
     *
     * @param multipartFile XML file  which we want tp convert to JSON file
     * @param savedLocation Location which we place in application.properties
     * @return saved file name
     */
    @Override
    public String generateJsonFromExcel(MultipartFile multipartFile, String savedLocation) throws IOException {

        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(multipartFile.getInputStream());

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

        String jsonFileName = savedLocation + multipartFile.getOriginalFilename().replace(".xml", ".json");
        Files.createDirectories(Paths.get(savedLocation));
        Files.write(Paths.get(jsonFileName), jsonString.getBytes());

        return multipartFile.getOriginalFilename().replace(".xml", ".json");
    }
}
