package com.filehandler.sibaram.processer.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcesserServiceImpl implements FileProcesserService {
    /**
     * Here we are checking the file type and based on the type we are applying reading logics.
     *
     * @param multipartFile File which we have uploaded
     * @param startRow From which row we want to start displaying the rows in the UI page
     * @param model As we have used thymeleaf here to display the details, we have to retrun the data in this object type.
     *              The values will be fetched and rendered using these details
     */
    @Override
    public void processFile(MultipartFile multipartFile, int startRow, Model model) {
        String fileName = multipartFile.getOriginalFilename();
        if (fileName.endsWith(".csv")) {
            System.out.println("CSV file uploaded");
            List<List<String>> data = processCsvFile(multipartFile, startRow);
            model.addAttribute("data", data);
            model.addAttribute("fileName", fileName);
        } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            System.out.println("Excel file uploaded");
            List<List<String>> data = processExcelFile(multipartFile, startRow);
            model.addAttribute("data", data);
            model.addAttribute("fileName", fileName);
        } else {
            model.addAttribute("error", "Invalid file format. Upload CSV or Excel !!!");
        }
    }

    private List<List<String>> processExcelFile(MultipartFile multipartFile, int startingRow) {
        List<List<String>> data = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = startingRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell : row) {
                        rowData.add(cell.toString());
                    }
                    data.add(rowData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private List<List<String>> processCsvFile(MultipartFile multipartFile, int startingRow) {
        List<List<String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
             CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT)) {

            int rowIndex = 0;
            for (CSVRecord record : csvParser) {
                if (rowIndex >= startingRow) {
                    data.add(new ArrayList<>(record.toList()));
                }
                rowIndex++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
