package com.fu.bom.service.impl;

import com.fu.bom.service.ImportDataService;
import com.fu.bom.service.ProductService;
import com.fu.database.dao.*;
import com.fu.database.entity.Area;
import com.fu.database.entity.Category;
import com.fu.database.entity.CategoryProduct;
import com.fu.database.entity.Product;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 02/11/2016.
 */
@Service
public class ImportDataServiceImpl implements ImportDataService {

    private final ProductService productService;

    private final CategoryDao categoryDao;

    private final ProductDao productDao;

    private final CategoryProductDao categoryProductDao;

    private final AreaDao areaDao;

    private final FloorDao floorDao;

    private static final Logger LOG = Logger.getLogger(ImportDataServiceImpl.class);

    @Autowired
    public ImportDataServiceImpl(ProductService productService, CategoryDao categoryDao, ProductDao productDao, CategoryProductDao categoryProductDao, AreaDao areaDao, FloorDao floorDao) {
        this.productService = productService;
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.categoryProductDao = categoryProductDao;
        this.areaDao = areaDao;
        this.floorDao = floorDao;
    }

    @Override
    public void importDataFromExcelFile(MultipartFile fileExcel) {
        LOG.info("[importDataFromExcelFile] Start:");
        try {
            byte[] byteArr = fileExcel.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            POIFSFileSystem fs = new POIFSFileSystem(inputStream);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            //ignore header
            rows.next();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                Product product = new Product();
                //column A Code
                HSSFCell cell = (HSSFCell) cells.next();
                product.setCode(cell.getStringCellValue());
                //column B Name
                cell = (HSSFCell) cells.next();
                product.setName(cell.getStringCellValue());
                //column C Details
                cell = (HSSFCell) cells.next();
                product.setDetails(cell.getStringCellValue());
                //column D Price
                cell = (HSSFCell) cells.next();
                product.setPrice((long) (cell.getNumericCellValue()));
                //column E Category
                cell = (HSSFCell) cells.next();
                String[] listCategoryName = cell.getStringCellValue().split(";");
                //column F Area
                cell = (HSSFCell) cells.next();
                product.setAreaName(cell.getStringCellValue());
                Area area = areaDao.getAreaByName(cell.getStringCellValue());
                product.setAreaId(area.getId());
                product.setAreaLocation(floorDao.getById(area.getFloorId()).getName());
                product.setStatus(true);
                product.setLastUpdate(System.currentTimeMillis());
                //add product
                product.setImgUrl("");
                List<Product> list = productDao.checkDuplicateCode(product.getCode());
                if (list.isEmpty()) {
                    product.setStatus(true);
                    product.setLastUpdate(System.currentTimeMillis());
                    productService.addProduct(product);
                    productService.addNameNaturalLanguage(product.getName());
                    addCateNewProduct(product.getCode(), listCategoryName);
                } else {
                    Product productExist = list.get(0);
                    product.setId(productExist.getId());
                    product.setImgUrl(productExist.getImgUrl());
                    productService.updateProduct(product);
                    if (!productExist.getName().equals(product.getName())) {
                        productService.updateNameNaturalLanguage(productExist.getName(), product.getName());
                    }
                    addCateUpdateProduct(productExist.getId(), listCategoryName);
                }
            }
        } catch (IOException | RuntimeException ex) {
            LOG.error("[importDataFromExcelFile] Error:", ex);
        }
        LOG.info("[importDataFromExcelFile] End");
    }

    private void addCateNewProduct(String productCode, String[] cateName) {
        LOG.info("[addCateNewProduct] Start:");
        for (int i = 0; i < cateName.length; i++) {
            long productId = productDao.getIdByCode(productCode);
            List<Category> category = categoryDao.getCategoryByName(cateName[i]);
            CategoryProduct categoryProduct = new CategoryProduct();
            categoryProduct.setCategoryProductKey(category.get(0).getId(), productId);
            List<CategoryProduct> check = categoryProductDao.checkExistProductInCate(category.get(0).getId(), productId);
            if (check.isEmpty()) {
                categoryProductDao.insert(categoryProduct);
            }
        }
        LOG.info("[addCateNewProduct] End");
    }

    private void addCateUpdateProduct(long productId, String[] cateName) {
        LOG.info("[addCateUpdateProduct] Start:");
        for (String aCateName : cateName) {
            List<Category> category = categoryDao.getCategoryByName(aCateName);
            CategoryProduct categoryProduct = new CategoryProduct();
            categoryProduct.setCategoryProductKey(category.get(0).getId(), productId);
            List<CategoryProduct> check = categoryProductDao.checkExistProductInCate(category.get(0).getId(), productId);
            if (check.isEmpty()) {
                categoryProductDao.insert(categoryProduct);
            }
        }
        LOG.info("[addCateUpdateProduct] End");
    }
}
