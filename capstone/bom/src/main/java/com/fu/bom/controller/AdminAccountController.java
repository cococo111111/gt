package com.fu.bom.controller;

import com.fu.bom.service.AdminAccountService;
import com.fu.database.entity.Employee;
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
 * Created by manlm on 9/10/2016.
 */
@Controller
public class AdminAccountController {

    private static final Logger LOG = Logger.getLogger(IndexController.class);

    private final AdminAccountService adminAccountService;

    @Autowired
    public AdminAccountController(AdminAccountService adminAccountService) {
        this.adminAccountService = adminAccountService;
    }

    @RequestMapping(value = "/viewAccount", method = RequestMethod.GET)
    public ModelAndView viewAccount() {
        LOG.info("[viewAccount] Start");
        ModelAndView model = new ModelAndView("viewAccount");
        List<Employee> employeeList = adminAccountService.getAll();
        model.addObject("adminAccountList", employeeList);
        LOG.info("[viewAccount] End");
        return model;
    }

    @RequestMapping(value = "/viewEditAccount", method = RequestMethod.GET)
    public ModelAndView viewEditAccount(@ModelAttribute(name = "username") String username) {
        LOG.info("[viewEditAccount] Start: username = " + username);
        ModelAndView model = new ModelAndView("editAccount");
        Employee employee = adminAccountService.getAccountByUsername(username);
        model.addObject("AccountEmployee", employee);
        LOG.info("[viewEditAccount] End");
        return model;
    }

    @RequestMapping(value = "/editAccount", method = RequestMethod.POST)
    public ModelAndView editAccount(@ModelAttribute(name = "username") String username,
                                    @ModelAttribute(name = "firstName") String firstName,
                                    @ModelAttribute(name = "lastName") String lastName,
                                    @ModelAttribute(name = "phone") String phone) {
        LOG.info("[editAccount] Start: username = " + username);
        ModelAndView model = new ModelAndView("redirect:viewAccount");
        adminAccountService.updateAccount(username, firstName, lastName, phone);
        LOG.info("[viewEditAccount] End");
        return model;
    }

    @RequestMapping(value = "/viewAddAccount", method = RequestMethod.GET)
    public ModelAndView viewAddAccount(@ModelAttribute(name = "addResult") String addResult,
                                       @ModelAttribute(name = "username") String username,
                                       @ModelAttribute(name = "firstName") String firstName,
                                       @ModelAttribute(name = "lastName") String lastName,
                                       @ModelAttribute(name = "phone") String phone,
                                       @ModelAttribute(name = "chkAdmin") String chkAdmin) {
        LOG.info("[viewAddAccount] Start: username = " + username);
        ModelAndView model = new ModelAndView("addAccount");
        model.addObject("addResult", addResult);
        model.addObject("username", username);
        model.addObject("firstName", firstName);
        model.addObject("lastName", lastName);
        model.addObject("phone", phone);
        model.addObject("chkAdmin", chkAdmin);
        LOG.info("[viewAddAccount] End");
        return model;
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
    public ModelAndView addAccount(@RequestParam(name = "username") String username,
                                   @RequestParam(name = "firstName") String firstName,
                                   @RequestParam(name = "lastName") String lastName,
                                   @RequestParam(name = "phone") String phone,
                                   @RequestParam(name = "chkAdmin") String chkAdmin,
                                   RedirectAttributes redirectAttributes) {
        LOG.info("[addAccount] Start: username = " + username);
        ModelAndView model = new ModelAndView("redirect:viewAddAccount");
        boolean result = adminAccountService.addAccount(username, firstName, lastName, phone, chkAdmin);
        if (result) {
            model = new ModelAndView("redirect:viewAccount");
            LOG.info("[addAccount] End");
            return model;
        }
        redirectAttributes.addFlashAttribute("addResult", result);
        redirectAttributes.addFlashAttribute("username", username);
        redirectAttributes.addFlashAttribute("firstName", firstName);
        redirectAttributes.addFlashAttribute("lastName", lastName);
        redirectAttributes.addFlashAttribute("phone", phone);
        redirectAttributes.addFlashAttribute("chkAdmin", chkAdmin);
        LOG.info("[addAccount] End");
        return model;
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public ModelAndView changeStatus(@RequestParam(name = "username") String username) {
        LOG.info("[changeStatus] Start: username = " + username);
        ModelAndView model = new ModelAndView("redirect:viewAccount");
        adminAccountService.changeStatus(username);
        LOG.info("[changeStatus] End");
        return model;
    }
}
