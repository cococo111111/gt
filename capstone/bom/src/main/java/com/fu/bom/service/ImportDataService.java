package com.fu.bom.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 02/11/2016.
 */
public interface ImportDataService {
    void importDataFromExcelFile(MultipartFile fileExcel);
}
