package com.fu.bom.controller;

import com.fu.bom.service.FloorService;
import com.fu.database.entity.Floor;
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
 * Created by Administrator on 07/11/2016.
 */
@Controller
public class FloorController {

    private final FloorService floorService;

    private static final Logger LOG = Logger.getLogger(FloorController.class);

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @RequestMapping(value = "/viewAddFloor", method = RequestMethod.GET)
    public ModelAndView viewAddFloor(@ModelAttribute(name = "txtName") String txtName) {
        LOG.info("[viewAddFloor] Start: txtName = " + txtName);
        ModelAndView model = new ModelAndView("insertFloor");
        model.addObject("txtName", txtName);
        LOG.info("[viewAddFloor] End");
        return model;
    }

    @RequestMapping(value = "addFloor", method = RequestMethod.POST)
    public ModelAndView addFloor(@RequestParam(name = "txtName") String txtName,
                                 RedirectAttributes redirectAttributes) {
        LOG.info("[addFloor] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = floorService.addFloor(txtName);
        if (result) {
            model = new ModelAndView("redirect:viewFloor");
        } else {
            model = new ModelAndView("redirect:viewAddFloor");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtName", txtName);
        }
        LOG.info("[addFloor] End");
        return model;
    }

    @RequestMapping(value = "/viewFloor", method = RequestMethod.GET)
    public ModelAndView viewFloor(@ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewFloor] Start");
        ModelAndView model = new ModelAndView("viewFloor");
        List<Floor> listFloor = floorService.getAll();
        model.addObject("resultAction", resultAction);
        model.addObject("listFloor", listFloor);
        LOG.info("[viewFloor] End");
        return model;
    }

    @RequestMapping(value = "/viewUpdateFloor", method = RequestMethod.GET)
    public ModelAndView viewUpdateFloor(@ModelAttribute(name = "txtId") String txtId,
                                        @ModelAttribute(name = "txtName") String txtName) {
        LOG.info("[viewUpdateFloor] Start: txtName " + txtName);
        ModelAndView model = new ModelAndView("updateFloor");
        model.addObject("txtId", txtId);
        model.addObject("txtName", txtName);
        LOG.info("[viewUpdateFloor] End");
        return model;
    }

    @RequestMapping(value = "updateFloor", method = RequestMethod.POST)
    public ModelAndView updateFloor(@RequestParam(name = "txtId") String txtId,
                                    @RequestParam(name = "txtName") String txtName,
                                    RedirectAttributes redirectAttributes) {
        LOG.info("[updateFloor] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = floorService.updateFloor(Integer.parseInt(txtId), txtName);
        if (result) {
            model = new ModelAndView("redirect:viewFloor");
        } else {
            model = new ModelAndView("redirect:viewUpdateFloor");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtId", txtId);
            redirectAttributes.addFlashAttribute("txtName", txtName);
        }
        LOG.info("[updateFloor] End");
        return model;
    }

    @RequestMapping(value = "/deleteFloor", method = RequestMethod.POST)
    public ModelAndView deleteFloor(@RequestParam(name = "txtId") String txtId,
                                    RedirectAttributes redirectAttributes) {
        LOG.info("[deleteFloor] Start: txtId = " + txtId);
        ModelAndView model = new ModelAndView("redirect:viewFloor");
        model.addObject("txtId", txtId);
        if(!floorService.deleteFloor(Integer.parseInt(txtId))){
            redirectAttributes.addFlashAttribute("errorMessage", "error");
        }
        LOG.info("[deleteFloor] End");
        return model;
    }
}
