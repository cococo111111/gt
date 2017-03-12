package com.fu.bom.controller;

import com.fu.bom.service.CategoryService;
import com.fu.bom.service.ProductService;
import com.fu.database.entity.Category;
import com.fu.database.entity.Product;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Administrator on 08/11/2016.
 */
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    private final ProductService productService;

    private static final Logger LOG = Logger.getLogger(CategoryController.class);

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @RequestMapping(value = "/viewAddCategory", method = RequestMethod.GET)
    public ModelAndView viewAddCategory(@ModelAttribute(name = "txtName") String txtName) {
        LOG.info("[viewAddCategy] Start: txtName = " + txtName);
        ModelAndView model = new ModelAndView("insertCategory");
        model.addObject("txtName", txtName);
        LOG.info("[viewAddCategy] End");
        return model;
    }

    @RequestMapping(value = "addCategory", method = RequestMethod.POST)
    public ModelAndView addCategory(@RequestParam(name = "txtName") String txtName,
                                    RedirectAttributes redirectAttributes) {
        LOG.info("[addCategory] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = categoryService.addCategory(txtName);
        if (result) {
            model = new ModelAndView("redirect:viewCategory");
            productService.addNameNaturalLanguage(txtName);
        } else {
            model = new ModelAndView("redirect:viewAddCategory");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtName", txtName);
        }
        LOG.info("[addCategory] End");
        return model;
    }

    @RequestMapping(value = "/viewCategory", method = RequestMethod.GET)
    public ModelAndView viewCategory(@ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewCategory] Start");
        ModelAndView model = new ModelAndView("viewCategory");
        List<Category> listCategory = categoryService.getAll();
        model.addObject("resultAction", resultAction);
        model.addObject("listCategory", listCategory);
        LOG.info("[viewCategory] End");
        return model;
    }

    @RequestMapping(value = "/viewUpdateCategory", method = RequestMethod.GET)
    public ModelAndView viewUpdateCategory(@ModelAttribute(name = "txtId") String txtId,
                                           @ModelAttribute(name = "txtName") String txtName) {
        LOG.info("[viewUpdateCategory] Start: txtName = " + txtName);
        ModelAndView model = new ModelAndView("updateCategory");
        model.addObject("txtId", txtId);
        model.addObject("txtName", txtName);
        LOG.info("[viewUpdateCategory] End");
        return model;
    }

    @RequestMapping(value = "updateCategory", method = RequestMethod.POST)
    public ModelAndView updateCategory(@RequestParam(name = "txtId") String txtId,
                                       @RequestParam(name = "txtName") String txtName,
                                       RedirectAttributes redirectAttributes) {
        LOG.info("[updateCategory] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = categoryService.updateCategory(Integer.parseInt(txtId), txtName);
        if (result) {
            model = new ModelAndView("redirect:viewCategory");
        } else {
            model = new ModelAndView("redirect:viewUpdateCategory");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtId", txtId);
            redirectAttributes.addFlashAttribute("txtName", txtName);
        }
        LOG.info("[updateCategory] End");
        return model;
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    public ModelAndView deleteCategory(@RequestParam(name = "txtId") String txtId) {
        LOG.info("[deleteCategory] Start:");
        ModelAndView model = new ModelAndView("redirect:viewCategory");
        model.addObject("txtId", txtId);
        categoryService.deleteCategory(Integer.parseInt(txtId));
        LOG.info("[deleteCategory] End");
        return model;
    }

    @RequestMapping(value = "/viewItemInCate", method = RequestMethod.GET)
    public ModelAndView viewItemInCate(@ModelAttribute(name = "txtId") String txtId,
                                       @ModelAttribute(name = "txtName") String txtName,
                                       @ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewItemInCate] Start:");
        ModelAndView model = new ModelAndView("viewItemInCate");
        List<Product> listProductInCate = categoryService.getProductInCate(Integer.parseInt(txtId));
        List<Product> listProduct = productService.getAllProduct();
        model.addObject("resultAction", resultAction);
        model.addObject("txtId", txtId);
        model.addObject("txtName", txtName);
        model.addObject("listProductInCate", listProductInCate);
        model.addObject("listProduct", listProduct);
        LOG.info("[viewItemInCate] End");
        return model;
    }

    @RequestMapping(value = "/addProductInCate", method = RequestMethod.POST)
    public ModelAndView addProductInCate(@RequestParam(name = "txtId") String txtId,
                                         @RequestParam(name = "txtName") String txtName,
                                         @RequestParam(name = "listAddProduct") long[] listAddProduct) {
        LOG.info("[addProductInCate] Start:");
        ModelAndView model = new ModelAndView("viewItemInCate");
        categoryService.addProductInCate(Integer.parseInt(txtId), listAddProduct);
        List<Product> listProductInCate = categoryService.getProductInCate(Integer.parseInt(txtId));
        List<Product> listProduct = productService.getAllProduct();
        model.addObject("txtId", txtId);
        model.addObject("txtName", txtName);
        model.addObject("listProductInCate", listProductInCate);
        model.addObject("listProduct", listProduct);
        LOG.info("[addProductInCate] End");
        return model;
    }

    @RequestMapping(value = "/deteleProductInCate", method = RequestMethod.POST)
    public ModelAndView deleteProductInCate(@RequestParam(name = "txtId") String txtId,
                                            @RequestParam(name = "txtName") String txtName,
                                            @RequestParam(name = "txtProductId") String txtProductId) {
        LOG.info("[deleteProductInCate] Start:");
        ModelAndView model = new ModelAndView("viewItemInCate");
        categoryService.deleteProductInCate(Integer.parseInt(txtId), Long.parseLong(txtProductId));
        List<Product> listProductInCate = categoryService.getProductInCate(Integer.parseInt(txtId));
        List<Product> listProduct = productService.getAllProduct();
        model.addObject("txtId", txtId);
        model.addObject("txtName", txtName);
        model.addObject("listProductInCate", listProductInCate);
        model.addObject("listProduct", listProduct);
        LOG.info("[deleteProductInCate] End");
        return model;
    }
}
