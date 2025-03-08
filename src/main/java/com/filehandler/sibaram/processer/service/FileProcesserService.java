package com.filehandler.sibaram.processer.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface FileProcesserService {
    void processFile(MultipartFile multipartFile, int startRow, int columnNumber, Model model);
}
