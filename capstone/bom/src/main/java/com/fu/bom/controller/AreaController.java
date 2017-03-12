package com.fu.bom.controller;

import com.fu.bom.service.AreaService;
import com.fu.database.entity.Area;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 06/11/2016.
 */
@Controller
public class AreaController {

    private final AreaService areaService;

    private static final Logger LOG = Logger.getLogger(ProductController.class);

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @RequestMapping(value = "/viewArea", method = RequestMethod.GET)
    public ModelAndView viewArea(@ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewArea] Start:");
        ModelAndView model = new ModelAndView("viewArea");
        List<Area> listArea = areaService.getAllArea();
        model.addObject("listArea", listArea);
        model.addObject("resultAction", resultAction);
        LOG.info("[viewArea] End");
        return model;
    }

    @RequestMapping(value = "/deleteArea", method = RequestMethod.POST)
    public ModelAndView deleteArea(@RequestParam(name = "txtId") String txtId) {
        LOG.info("[deleteArea] Start:");
        ModelAndView model = new ModelAndView("redirect:viewArea");
        model.addObject("txtId", txtId);
        areaService.deleteArea(Integer.parseInt(txtId));
        LOG.info("[deleteArea] End");
        return model;
    }
}
