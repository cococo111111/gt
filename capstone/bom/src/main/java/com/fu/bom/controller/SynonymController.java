package com.fu.bom.controller;

import com.fu.bom.service.SynonymService;
import com.fu.bom.utils.Constant;
import com.fu.database.entity.Synonym;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SynonymController {

    private final SynonymService synonymService;


    private static final Logger LOG = Logger.getLogger(SynonymController.class);

    @Autowired
    public SynonymController(SynonymService synonymService) {
        this.synonymService = synonymService;
    }


    @RequestMapping(value = "/viewAddSynonym", method = RequestMethod.GET)
    public ModelAndView viewAddSynonym(@ModelAttribute(name = "txtName") String txtName,
                                       @ModelAttribute(name = "txtKeyOriginalId") String txtKeyOriginalId,
                                       @ModelAttribute(name = "txtKeyOriginalName") String txtKeyOriginalName) {
        LOG.info("[viewAddSynonym] Start: txtName = " + txtName);
        ModelAndView model = new ModelAndView("insertSynonym");
        model.addObject("txtName", txtName);
        model.addObject("txtKeyOriginalId", txtKeyOriginalId);
        model.addObject("txtKeyOriginalName", txtKeyOriginalName);
        LOG.info("[viewAddSynonym] End");
        return model;
    }

    @RequestMapping(value = "addSynonym", method = RequestMethod.POST)
    public ModelAndView addSynonym(@RequestParam(name = "txtName") String txtName,
                                   @RequestParam(name = "txtKeyOriginalId") String txtKeyOriginalId,
                                   @RequestParam(name = "txtKeyOriginalName") String txtKeyOriginalName,
                                   RedirectAttributes redirectAttributes) {
        LOG.info("[addSynonym] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = synonymService.addSynonym(txtName, Integer.parseInt(txtKeyOriginalId));
        if (result) {
            model = new ModelAndView("redirect:viewSynonym");
            model.addObject("txtKeyOriginalId", txtKeyOriginalId);
            model.addObject("txtKeyOriginalName", txtKeyOriginalName);
        } else {
            model = new ModelAndView("redirect:viewAddSynonym");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtName", txtName);
            redirectAttributes.addFlashAttribute("txtSynonymId", txtKeyOriginalId);
        }
        LOG.info("[addSynonym] End");
        return model;
    }

    @RequestMapping(value = "/viewSynonym", method = RequestMethod.GET)
    public ModelAndView viewSynonym(@ModelAttribute(name = "txtKeyOriginalName") String txtKeyOriginalName,
                                    @ModelAttribute(name = "txtKeyOriginalId") String txtKeyOriginalId,
                                    @ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewSynonym] Start: txtKeyOriginalName = " + txtKeyOriginalName);
        ModelAndView model = new ModelAndView("viewSynonym");
        List<Synonym> listSynonym = synonymService.getSynonymByKeyOriginalId(Integer.parseInt(txtKeyOriginalId));
        model.addObject("resultAction", resultAction);
        model.addObject("listSynonym", listSynonym);
        model.addObject("txtKeyOriginalId", txtKeyOriginalId);
        model.addObject("txtKeyOriginalName", txtKeyOriginalName);
        LOG.info("[viewSynonym] End");
        return model;
    }

    @RequestMapping(value = "/viewUpdateSynonym", method = RequestMethod.GET)
    public ModelAndView viewUpdateSynonym(@ModelAttribute(name = "txtKeyOriginalName") String txtKeyOriginalName,
                                          @ModelAttribute(name = "txtKeyOriginalId") String txtKeyOriginalId,
                                          @ModelAttribute(name = "txtId") String txtId,
                                          @ModelAttribute(name = "txtName") String txtName) {
        LOG.info("[viewUpdateSynonym] Start: txtKeyOriginalName = " + txtKeyOriginalName);
        ModelAndView model = new ModelAndView("updateSynonym");
        model.addObject("txtId", txtId);
        model.addObject("txtName", txtName);
        model.addObject("txtKeyOriginalId", txtKeyOriginalId);
        model.addObject("txtKeyOriginalName", txtKeyOriginalName);
        LOG.info("[viewUpdateSynonym] End");
        return model;
    }

    @RequestMapping(value = "updateSynonym", method = RequestMethod.POST)
    public ModelAndView updateSynonym(@RequestParam(name = "txtId") String txtId,
                                      @RequestParam(name = "txtName") String txtName,
                                      @RequestParam(name = "txtKeyOriginalId") String txtKeyOriginalId,
                                      @RequestParam(name = "txtKeyOriginalName") String txtKeyOriginalName,
                                      RedirectAttributes redirectAttributes) {
        LOG.info("[updateSynonym] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = synonymService.updateSynonym(Integer.parseInt(txtId), txtName, Integer.parseInt(txtKeyOriginalId));
        if (result) {
            model = new ModelAndView("redirect:viewSynonym");
            model.addObject("txtKeyOriginalId", txtKeyOriginalId);
            model.addObject("txtKeyOriginalName", txtKeyOriginalName);
        } else {
            model = new ModelAndView("redirect:viewUpdateSynonym");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtId", txtId);
            redirectAttributes.addFlashAttribute("txtName", txtName);
        }
        LOG.info("[updateSynonym] End");
        return model;
    }

    @RequestMapping(value = "/viewAddKeyOriginal", method = RequestMethod.GET)
    public ModelAndView viewAddKeyOriginal(@ModelAttribute(name = "txtName") String txtName,
                                           @ModelAttribute(name = "txtKeyOriginalId") String txtKeyOriginalId) {
        LOG.info("[viewAddKeyOriginal] Start: txtName = " + txtName);
        ModelAndView model = new ModelAndView("insertKeyOriginal");
        model.addObject("txtName", txtName);
        model.addObject("txtKeyOriginalId", txtKeyOriginalId);
        LOG.info("[viewAddKeyOriginal] End");
        return model;
    }

    @RequestMapping(value = "addKeyOriginal", method = RequestMethod.POST)
    public ModelAndView addKeyOriginal(@RequestParam(name = "txtName") String txtName,
                                       RedirectAttributes redirectAttributes) {
        LOG.info("[addKeyOriginal] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = synonymService.addSynonym(txtName, Constant.KEY_ORIGINAL);
        if (result) {
            model = new ModelAndView("redirect:viewKeyOriginal");
        } else {
            model = new ModelAndView("redirect:viewAddKeyOriginal");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtName", txtName);
        }
        LOG.info("[addKeyOriginal] End");
        return model;
    }

    @RequestMapping(value = "/viewUpdateKeyOriginal", method = RequestMethod.GET)
    public ModelAndView viewUpdateFloor(@ModelAttribute(name = "txtId") String txtId,
                                        @ModelAttribute(name = "txtName") String txtName) {
        LOG.info("[viewUpdateKeyOriginal] Start: txtName = " + txtName);
        ModelAndView model = new ModelAndView("updateKeyOriginal");
        model.addObject("txtId", txtId);
        model.addObject("txtName", txtName);
        LOG.info("[viewUpdateKeyOriginal] End");
        return model;
    }

    @RequestMapping(value = "updateKeyOriginal", method = RequestMethod.POST)
    public ModelAndView updateFloor(@RequestParam(name = "txtId") String txtId,
                                    @RequestParam(name = "txtName") String txtName,
                                    RedirectAttributes redirectAttributes) {
        LOG.info("[updateKeyOriginal] Start: txtName = " + txtName);
        ModelAndView model;
        boolean result = synonymService.updateKeyOriginal(Integer.parseInt(txtId), txtName);
        if (result) {
            model = new ModelAndView("redirect:viewKeyOriginal");
        } else {
            model = new ModelAndView("redirect:viewUpdateKeyOriginal");
            redirectAttributes.addFlashAttribute("errorMessage", "Name existed!");
            redirectAttributes.addFlashAttribute("txtId", txtId);
            redirectAttributes.addFlashAttribute("txtName", txtName);
        }
        LOG.info("[updateKeyOriginal] End");
        return model;
    }

    @RequestMapping(value = "viewKeyOriginal", method = RequestMethod.GET)
    public ModelAndView viewKeyOriginal(@ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewKeyOriginal] Start");
        ModelAndView model = new ModelAndView("viewKeyOriginal");
        List<Synonym> listKeyOriginal = synonymService.getKeyOriginal();
        List<Synonym> listSynonym = synonymService.getSynonym();
        model.addObject("resultAction", resultAction);
        model.addObject("listKeyOriginal", listKeyOriginal);
        model.addObject("listSynonym", listSynonym);
        LOG.info("[viewKeyOriginal] End");
        return model;
    }

    @RequestMapping(value = "deleteSynonym", method = RequestMethod.POST)
    public ModelAndView deleteSynonym(@RequestParam(name = "txtId") String txtId,
                                      @RequestParam(name = "txtKeyOriginalId") String txtKeyOriginalId,
                                      @RequestParam(name = "txtKeyOriginalName") String txtKeyOriginalName) {
        LOG.info("[deleteSynonym] Start: txtId = " + txtId);
        ModelAndView model = new ModelAndView("redirect:viewSynonym");
        synonymService.deleteSynonym(Integer.parseInt(txtId));
        model.addObject("txtKeyOriginalId", txtKeyOriginalId);
        model.addObject("txtKeyOriginalName", txtKeyOriginalName);
        LOG.info("[deleteSynonym] End");
        return model;
    }

    @RequestMapping(value = "deleteOriginalWord", method = RequestMethod.POST)
    public ModelAndView deleteOriginalWord(@RequestParam(name = "txtId") String txtId) {
        LOG.info("[deleteOriginalWord] Start: txtId = " + txtId);
        ModelAndView model = new ModelAndView("redirect:viewKeyOriginal");
        synonymService.deleteOriginalWord(Integer.parseInt(txtId));
        LOG.info("[deleteOriginalWord] End");
        return model;
    }
}
