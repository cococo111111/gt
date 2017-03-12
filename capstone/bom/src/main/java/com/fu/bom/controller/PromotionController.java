package com.fu.bom.controller;

import com.fu.bom.service.ProductService;
import com.fu.bom.service.PromotionService;
import com.fu.database.entity.Product;
import com.fu.database.entity.Promotion;
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
 * Created by Administrator on 03/11/2016.
 */
@Controller
public class PromotionController {

    private final PromotionService promotionService;

    private static final Logger LOG = Logger.getLogger(PromotionController.class);

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @RequestMapping(value = "/viewPromotion", method = RequestMethod.GET)
    public ModelAndView viewProduct(@ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewPromotion] Start");
        ModelAndView model = new ModelAndView("viewPromotion");
        List<Promotion> list = promotionService.getAllPromotion();
        model.addObject("listpromotion", list);
        LOG.info("[viewPromotion] End");
        return model;
    }

    @RequestMapping(value = "/viewAddPromotion", method = RequestMethod.GET)
    public ModelAndView viewAddPromotion() {
        LOG.info("[viewAddPromotion] Start");
        ModelAndView model = new ModelAndView("insertPromotion");
        LOG.info("[viewAddPromotion] End");
        return model;
    }

    @RequestMapping(value = "/AddPromotion", method = RequestMethod.POST)
    public ModelAndView addPromotion(@RequestParam(name = "txtName") String txtName,
                                     @RequestParam(name = "txtDetails") String txtDetails,
                                     @RequestParam(name = "txtDiscountRate") String txtDiscountRate,
                                     @RequestParam(name = "txtStartDate") String txtStartDate,
                                     @RequestParam(name = "txtEndDate") String txtEndDate,
                                     RedirectAttributes redirectAttributes) {
        LOG.info("[addPromotion] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = promotionService.addPromotion(txtName, txtDetails, txtDiscountRate, txtStartDate, txtEndDate);
        if (result) {
            model = new ModelAndView("redirect:viewPromotion");
        } else {
            model = new ModelAndView("viewAddPromotion");
            redirectAttributes.addFlashAttribute("txtName", txtName);
            redirectAttributes.addFlashAttribute("txtDetails", txtDetails);
            redirectAttributes.addFlashAttribute("txtDiscountRate", txtDiscountRate);
            redirectAttributes.addFlashAttribute("txtStartDate", txtStartDate);
            redirectAttributes.addFlashAttribute("txtEndDate", txtEndDate);
        }
        LOG.info("[addPromotion] End");
        return model;
    }

    @RequestMapping(value = "/viewItem", method = RequestMethod.GET)
    public ModelAndView viewAddItem(@RequestParam(name = "txtId") String txtId) {
        LOG.info("[viewAddItem] Start: txtId = " + txtId);
        ModelAndView model = new ModelAndView("viewItem");
        Promotion promotion = promotionService.getPromotionById(Long.parseLong(txtId));
        List<Product> listAddedProduct = promotionService.getProductByPromotionId(Long.parseLong(txtId));
        List<Product> listNotAddProduct = promotionService.getProductNotInByPromotionId(Long.parseLong(txtId));
        model.addObject("promotion", promotion);
        model.addObject("listAdded", listAddedProduct);
        model.addObject("listNotAdd", listNotAddProduct);
        LOG.info("[viewAddItem] End");
        return model;
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public ModelAndView addItem(@RequestParam(name = "txtProductId") String txtProductId,
                                @RequestParam(name = "txtPromotionId") String txtPromotionId) {
        LOG.info("[addItem] Start: txtProductId = " + txtProductId);
        promotionService.insertPromotionProduct(Long.parseLong(txtPromotionId), Long.parseLong(txtProductId));
        ModelAndView model = new ModelAndView("redirect:viewItem");
        model.addObject("txtId", txtPromotionId);
        LOG.info("[addItem] End");
        return model;
    }

    @RequestMapping(value = "/deteleItem", method = RequestMethod.POST)
    public ModelAndView deteleItem(@RequestParam(name = "txtProductId") String txtProductId,
                                   @RequestParam(name = "txtPromotionId") String txtPromotionId) {
        LOG.info("[deteleItem] Start: txtProductId = " + txtProductId);
        ModelAndView model = new ModelAndView("redirect:viewItem");
        promotionService.deletePromotionProduct(Long.parseLong(txtPromotionId), Long.parseLong(txtProductId));
        model.addObject("txtId", txtPromotionId);
        LOG.info("[deteleItem] End");
        return model;
    }

    @RequestMapping(value = "/deteleAllItem", method = RequestMethod.POST)
    public ModelAndView deteleAllItem(@RequestParam(name = "txtPromotionId") String txtPromotionId) {
        LOG.info("[deteleAllItem] Start: txtPromotionId = " + txtPromotionId);
        ModelAndView model = new ModelAndView("redirect:viewItem");
        promotionService.deleteAllProductByPromotionId(Long.parseLong(txtPromotionId));
        model.addObject("txtId", txtPromotionId);
        LOG.info("[deteleAllItem] End");
        return model;
    }

    @RequestMapping(value = "/addAllItem", method = RequestMethod.POST)
    public ModelAndView addAllItem(@RequestParam(name = "txtPromotionId") String txtPromotionId) {
        LOG.info("[addAllItem] Start: txtPromotionId = " + txtPromotionId);
        ModelAndView model = new ModelAndView("redirect:viewItem");
        promotionService.addAllProductByPromotionId(Long.parseLong(txtPromotionId));
        model.addObject("txtId", txtPromotionId);
        LOG.info("[addAllItem] End");
        return model;
    }

    @RequestMapping(value = "/viewUpdatePromotion", method = RequestMethod.GET)
    public ModelAndView viewUpdatePromotion(@ModelAttribute(name = "txtId") String txtId,
                                            @ModelAttribute(name = "txtName") String txtName,
                                            @ModelAttribute(name = "txtDetails") String txtDetails,
                                            @ModelAttribute(name = "txtDiscountRate") String txtDiscountRate,
                                            @ModelAttribute(name = "txtStartDate") String txtStartDate,
                                            @ModelAttribute(name = "txtEndDate") String txtEndDate) {
        LOG.info("[viewUpdatePromotion] Start: txtName = " + txtName);
        ModelAndView model = new ModelAndView("updatePromotion");
        model.addObject("txtId", txtId);
        model.addObject("txtName", txtName);
        model.addObject("txtDetails", txtDetails);
        model.addObject("txtDiscountRate", txtDiscountRate);
        model.addObject("txtStartDate", txtStartDate);
        model.addObject("txtEndDate", txtEndDate);
        LOG.info("[viewUpdatePromotion] End");
        return model;
    }

    @RequestMapping(value = "/updatePromotion", method = RequestMethod.POST)
    public ModelAndView updatePromotion(@ModelAttribute(name = "txtId") String txtId,
                                        @ModelAttribute(name = "txtName") String txtName,
                                        @ModelAttribute(name = "txtDetails") String txtDetails,
                                        @ModelAttribute(name = "txtDiscountRate") String txtDiscountRate,
                                        @ModelAttribute(name = "txtStartDate") String txtStartDate,
                                        @ModelAttribute(name = "txtEndDate") String txtEndDate,
                                        RedirectAttributes redirectAttributes) {
        LOG.info("[updatePromotion] Start: txtName = " + txtName);
        boolean result = promotionService.updatePromotion(txtId, txtName, txtDetails, txtDiscountRate, txtStartDate, txtEndDate);
        ModelAndView model;
        if (result) {
            model = new ModelAndView("redirect:viewPromotion");
        } else {
            model = new ModelAndView("redirect:viewUpdateProduct");
            redirectAttributes.addFlashAttribute("txtId", txtId);
            redirectAttributes.addFlashAttribute("txtName", txtName);
            redirectAttributes.addFlashAttribute("txtDetails", txtDetails);
            redirectAttributes.addFlashAttribute("txtDiscountRate", txtDiscountRate);
            redirectAttributes.addFlashAttribute("txtStartDate", txtStartDate);
            redirectAttributes.addFlashAttribute("txtEndDate", txtEndDate);
        }
        LOG.info("[updatePromotion] End");
        return model;
    }

    @RequestMapping(value = "/deletePromotion", method = RequestMethod.POST)
    public ModelAndView detelePromotion(@RequestParam(name = "txtId") String txtId) {
        LOG.info("[deletePromotion] Start: txtId = " + txtId);
        ModelAndView model = new ModelAndView("redirect:viewPromotion");
        promotionService.detelePromotion(Long.parseLong(txtId));
        LOG.info("[deletePromotion] End");
        return model;
    }

    @RequestMapping(value = "/addItemsIntoPromotion", method = RequestMethod.POST)
    public ModelAndView addItem(@RequestParam(name = "listAddProduct", required = false) long[] listProductId,
                                @RequestParam(name = "txtPromotionId") String txtPromotionId) {
        LOG.info("[addItem] Start: txtPromotionId = " + txtPromotionId);
        promotionService.addProductIntoPromotion(Long.parseLong(txtPromotionId), listProductId);
        ModelAndView model = new ModelAndView("redirect:viewItem");
        model.addObject("txtId", txtPromotionId);
        LOG.info("[addItem] End");
        return model;
    }
}
