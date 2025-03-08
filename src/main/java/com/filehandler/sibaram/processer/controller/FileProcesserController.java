package com.filehandler.sibaram.processer.controller;

import com.filehandler.sibaram.processer.service.FileProcesserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("file-processer")
@Slf4j
public class FileProcesserController {

    @Autowired
    private FileProcesserService fileProcesserService;

    @GetMapping
    public ModelAndView indexPageLoader() {
        return new ModelAndView("upload");
    }

    @PostMapping("/upload")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                   @RequestParam int startRow,
                                   @RequestParam int columnNumber,
                                   Model model) {
        log.info("Uploading file for processing");
        try {
            fileProcesserService.processFile(multipartFile, startRow, columnNumber, model);
            return new ModelAndView("upload");
        } catch (Exception e) {
            model.addAttribute("error", "Error processing file: " + e.getMessage());
            return new ModelAndView("upload");
        }
    }
}
