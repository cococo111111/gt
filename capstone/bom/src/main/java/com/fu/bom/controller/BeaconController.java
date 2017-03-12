package com.fu.bom.controller;

import com.fu.bom.service.BeaconService;
import com.fu.database.entity.Beacon;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by PhucNT on 10/27/2016.
 */
@Controller
public class BeaconController {
    private final BeaconService beaconService;
    private static final Logger LOG = Logger.getLogger(BeaconController.class);

    @Autowired
    public BeaconController(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    @RequestMapping(value = "/viewBeacon", method = RequestMethod.GET)
    public ModelAndView viewBeacon() {
        LOG.info("[viewBeacon] Start");
        ModelAndView model = new ModelAndView("viewBeacon");
        List<Beacon> beaconList = beaconService.getAllBeacon();
        model.addObject("beaconList", beaconList);
        LOG.info("[viewBeacon] End");
        return model;
    }

    @RequestMapping(value = "/deleteBeacon", method = RequestMethod.POST)
    public ModelAndView changeStatus(@RequestParam(name = "txtId") String txtId,
                                     RedirectAttributes redirectAttributes) {
        LOG.info("[deleteBeacon] Start: ID = " + txtId);
        ModelAndView model = new ModelAndView("redirect:viewBeacon");
        boolean result = beaconService.deleteBeacon(Integer.parseInt(txtId));
        if (!result) {
            redirectAttributes.addFlashAttribute("errorMessage", "error");
        }
        redirectAttributes.addFlashAttribute("deleteResult", result);
        LOG.info("[deleteBeacon] End");
        return model;
    }

}
