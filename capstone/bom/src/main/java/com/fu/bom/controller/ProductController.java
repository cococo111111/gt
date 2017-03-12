package com.fu.bom.controller;

import com.fu.bom.service.AreaService;
import com.fu.bom.service.CategoryService;
import com.fu.bom.service.ImportDataService;
import com.fu.bom.service.ProductService;
import com.fu.bom.utils.Constant;
import com.fu.database.entity.Area;
import com.fu.database.entity.Category;
import com.fu.database.entity.Product;
import com.fu.database.entity.Promotion;
import com.fu.database.model.RelatingModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Administrator on 11/10/2016.
 */
@Controller
public class ProductController {

    private final ProductService productService;

    private final AreaService areaService;

    private final ImportDataService importDataService;

    private final CategoryService categoryService;

    private static final Logger LOG = Logger.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService, AreaService areaService, ImportDataService importDataService, CategoryService categoryService) {
        this.productService = productService;
        this.areaService = areaService;
        this.importDataService = importDataService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/viewAddProduct", method = RequestMethod.GET)
    public ModelAndView viewAddProduct(@ModelAttribute(name = "txtCode") String txtCode,
                                       @ModelAttribute(name = "txtName") String txtName,
                                       @ModelAttribute(name = "txtPrice") String txtPrice,
                                       @ModelAttribute(name = "txtDetails") String txtDetails,
                                       @ModelAttribute(name = "txtAreaId") String txtAreaId,
                                       @ModelAttribute(name = "errorMessage") String errorMessage,
                                       @ModelAttribute(name = "fileImage") MultipartFile fileImage) {
        LOG.info("[viewAddProduct] Start: txtName = " + txtName);
        ModelAndView model = new ModelAndView("insertProduct");
        List<Area> listArea = areaService.getAllArea();
        List<Category> listCategory = productService.getAllCategory();
        model.addObject("txtCode", txtCode);
        model.addObject("txtName", txtName);
        model.addObject("txtPrice", txtPrice);
        model.addObject("txtDetails", txtDetails);
        model.addObject("txtAreaId", txtAreaId);
        model.addObject("listCategory", listCategory);
        model.addObject("errorMessage", errorMessage);
        model.addObject("listArea", listArea);
        model.addObject("fileImage", fileImage);
        LOG.info("[viewAddProduct] End");
        return model;
    }

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public ModelAndView addProduct(@RequestParam(name = "txtCode") String txtCode,
                                   @RequestParam(name = "txtName") String txtName,
                                   @RequestParam(name = "txtPrice") String txtPrice,
                                   @RequestParam(name = "txtDetails") String txtDetails,
                                   @RequestParam(name = "txtAreaId") String txtAreaId,
                                   @RequestParam(name = "listCategory", required = false) int[] listCategory,
                                   @RequestParam("fileImage") MultipartFile fileImage,
                                   RedirectAttributes redirectAttributes) {
        LOG.info("[addProduct] Start: txtName = " + txtName);
        ModelAndView model;
        String[] imageURL = productService.imgUrlInServer(fileImage, "", "", txtCode);
        boolean result = productService.addProduct(txtCode, txtName, Long.parseLong(txtPrice), imageURL[0], txtDetails, Integer.parseInt(txtAreaId), listCategory, imageURL[1]);
        if (result) {
            model = new ModelAndView("redirect:viewProduct");
            redirectAttributes.addFlashAttribute("resultAction", Constant.SUCCESS);

        } else {
            model = new ModelAndView("redirect:viewAddProduct");
            redirectAttributes.addFlashAttribute("errorMessage", "Code existed!");
            redirectAttributes.addFlashAttribute("txtCode", txtCode);
            redirectAttributes.addFlashAttribute("txtName", txtName);
            redirectAttributes.addFlashAttribute("txtPrice", txtPrice);
            redirectAttributes.addFlashAttribute("txtDetails", txtDetails);
            redirectAttributes.addFlashAttribute("txtAreaId", txtAreaId);
            redirectAttributes.addFlashAttribute("fileImage", fileImage);
        }
        LOG.info("[addProduct] End");
        return model;
    }

    @RequestMapping(value = "/viewProduct", method = RequestMethod.GET)
    public ModelAndView viewProduct(@ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewProduct] Start");
        ModelAndView model = new ModelAndView("viewProduct");
        List<Product> list = productService.getAllProduct();
        model.addObject("listproduct", list);
        model.addObject("resultAction", resultAction);
        LOG.info("[viewProduct] End");
        return model;
    }

    @RequestMapping(value = "/viewUpdateProduct", method = RequestMethod.GET)
    public ModelAndView viewUpdateProduct(@ModelAttribute(name = "txtId") String txtId,
                                          @ModelAttribute(name = "txtCode") String txtCode,
                                          @ModelAttribute(name = "txtName") String txtName,
                                          @ModelAttribute(name = "txtPrice") String txtPrice,
                                          @ModelAttribute(name = "txtDetails") String txtDetails,
                                          @ModelAttribute(name = "txtImgUrl") String txtImgUrl,
                                          @ModelAttribute(name = "txtImgPromotionUrl") String txtImgPromotionUrl,
                                          @ModelAttribute(name = "txtAreaId") String txtAreaId,
                                          @ModelAttribute(name = "errorMessage") String errorMessage,
                                          @ModelAttribute(name = "fileImage") MultipartFile fileImage) {
        LOG.info("[viewUpdateProduct] Start: Name Product = " + txtName);
        ModelAndView model = new ModelAndView("updateProduct");
        List<Area> listArea = areaService.getAllArea();
        List<Category> listCategory = productService.getAllCategory();
        List<Category> listCategoryOfProduct = categoryService.getCategoryOfProduct(Long.parseLong(txtId));
        model.addObject("txtId", txtId);
        model.addObject("txtCode", txtCode);
        model.addObject("txtName", txtName);
        model.addObject("txtPrice", txtPrice);
        model.addObject("txtDetails", txtDetails);
        model.addObject("txtAreaId", txtAreaId);
        model.addObject("txtImgUrl", txtImgUrl);
        model.addObject("txtImgPromotionUrl", txtImgPromotionUrl);
        model.addObject("errorMessage", errorMessage);
        model.addObject("listArea", listArea);
        model.addObject("listCategory", listCategory);
        model.addObject("listCategoryOfProduct", listCategoryOfProduct);
        model.addObject("fileImage", fileImage);
        LOG.info("[viewUpdateProduct] End");
        return model;
    }

    @RequestMapping(value = "updateProduct", method = RequestMethod.POST)
    public ModelAndView updateProduct(@RequestParam(name = "txtId") String txtId,
                                      @RequestParam(name = "txtCode") String txtCode,
                                      @RequestParam(name = "txtName") String txtName,
                                      @RequestParam(name = "txtPrice") String txtPrice,
                                      @RequestParam(name = "txtDetails") String txtDetails,
                                      @RequestParam(name = "txtAreaId") String txtAreaId,
                                      @RequestParam(name = "txtImgUrl") String txtImgUrl,
                                      @RequestParam(name = "txtImgPromotionUrl") String txtImgPromotionUrl,
                                      @RequestParam(name = "listCategory", required = false) int[] listCategory,
                                      @RequestParam("fileImage") MultipartFile fileImage,
                                      RedirectAttributes redirectAttributes) {
        LOG.info("[updateProduct] Start: Name Product = " + txtName);
        ModelAndView model;
        String[] imageURL = productService.imgUrlInServer(fileImage, txtImgUrl, txtImgPromotionUrl, txtCode);
        boolean result = productService.updateProduct(Long.parseLong(txtId), txtCode, txtName,
                Long.parseLong(txtPrice), imageURL[0], txtDetails,
                Integer.parseInt(txtAreaId), listCategory, imageURL[1]);
        if (result) {
            model = new ModelAndView("redirect:viewProduct");
            redirectAttributes.addFlashAttribute("resultAction", Constant.SUCCESS);
        } else {
            model = new ModelAndView("redirect:viewUpdateProduct");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtId", txtId);
            redirectAttributes.addFlashAttribute("txtCode", txtCode);
            redirectAttributes.addFlashAttribute("txtName", txtName);
            redirectAttributes.addFlashAttribute("txtPrice", txtPrice);
            redirectAttributes.addFlashAttribute("txtDetails", txtDetails);
            redirectAttributes.addFlashAttribute("txtImgUrl", txtImgUrl);
            redirectAttributes.addFlashAttribute("txtAreaId", txtAreaId);
            redirectAttributes.addFlashAttribute("fileImage", fileImage);
        }
        LOG.info("[updateProduct] End");
        return model;
    }

    @RequestMapping(value = "/viewDetailProduct", method = RequestMethod.GET)
    public ModelAndView viewDetailProduct(@RequestParam(name = "txtId") String id) {
        LOG.info("[viewDetailProduct] Start: id = " + id);
        ModelAndView model = new ModelAndView("detailProduct");
        Product product = productService.getProductById(Long.parseLong(id));
        List<Promotion> listPromotion = productService.getPromotionByProductId(Long.parseLong(id));
        List<Product> listSuggestionProduct = productService.getSuggestProductById(Long.parseLong(id));
        model.addObject("product", product);
        model.addObject("listPromotion", listPromotion);
        model.addObject("listSuggestion", listSuggestionProduct);
        LOG.info("[viewDetailProduct] End");
        return model;

    }

    @RequestMapping(value = "/insertData", method = RequestMethod.POST)
    public ModelAndView insertData(@RequestParam("fileExcel") MultipartFile fileExcel) {
        LOG.info("[insertData] Start");
        ModelAndView model = new ModelAndView("redirect:viewImportData");
        importDataService.importDataFromExcelFile(fileExcel);
        LOG.info("[insertData] End");
        return model;
    }

    @RequestMapping(value = "/viewImportData", method = RequestMethod.GET)
    public ModelAndView viewImportData() {
        LOG.info("[viewImportData] Start");
        ModelAndView model = new ModelAndView("importData");
        List<Product> list = productService.getNewProduct();
        model.addObject("listproduct", list);
        LOG.info("[viewImportData] End");
        return model;
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public ModelAndView deleteProduct(@RequestParam(name = "txtId") String id) {
        LOG.info("[deleteProduct] Start");
        ModelAndView model = new ModelAndView("redirect:viewProduct");
        productService.deleteProduct(Long.parseLong(id));
        LOG.info("[deleteProduct] End");
        return model;
    }

    @RequestMapping(value = "/viewRelation", method = RequestMethod.GET)
    public ModelAndView viewRelation(@RequestParam(name = "txtProductId") String id) {
        LOG.info("[viewRelation] Start: id = " + id);
        ModelAndView model = new ModelAndView("viewRelation");
        Product product = productService.getProductById(Long.parseLong(id));
        List<RelatingModel> listSuggestionProduct = productService.getSuggestProductAndWeight(Long.parseLong(id));
        List<Product> listNotSuggest = productService.getNotSuggestionById(Long.parseLong(id));
        model.addObject("product", product);
        model.addObject("listSuggest", listSuggestionProduct);
        model.addObject("listNotSuggest", listNotSuggest);
        LOG.info("[viewRelation] End");
        return model;

    }

    @RequestMapping(value = "/addRelation", method = RequestMethod.POST)
    public ModelAndView addRelation(@RequestParam(name = "txtProductId") String txtProductId,
                                    @RequestParam(name = "txtRelativeProductId") String txtRelativeProductId,
                                    @RequestParam(name = "txtWeight") String txtWeight) {
        LOG.info("[addRelation] Start: txtProductId = " + txtProductId);
        ModelAndView model = new ModelAndView("redirect:viewRelation");
        model.addObject("txtProductId", txtProductId);
        productService.addSuggest(Long.parseLong(txtProductId), Long.parseLong(txtRelativeProductId), Long.parseLong(txtWeight));
        LOG.info("[addRelation] End");
        return model;

    }

    @RequestMapping(value = "/deleteRelation", method = RequestMethod.POST)
    public ModelAndView deleteRelation(@RequestParam(name = "txtProductId") String txtProductId,
                                       @RequestParam(name = "txtRelativeProductId") String txtRelativeProductId) {
        LOG.info("[deleteRelation] Start: txtProductId = " + txtProductId);
        ModelAndView model = new ModelAndView("redirect:viewRelation");
        model.addObject("txtProductId", txtProductId);
        productService.deleteSuggest(Long.parseLong(txtProductId), Long.parseLong(txtRelativeProductId));
        LOG.info("[viewRelation] End");
        return model;

    }
}
