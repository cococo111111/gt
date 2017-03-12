package com.fu.bom.controller;

import com.fu.common.util.ImageUtil;
import com.fu.storage.service.S3Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by manlm on 7/22/2016.
 */
@Controller
public class IndexController {

    private static final Logger LOG = Logger.getLogger(IndexController.class);

    private final S3Service s3Service;

    @Autowired
    public IndexController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        LOG.info("[login] Start");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            LOG.info("[login] End");
            return new ModelAndView("redirect:main");
        }

        LOG.info("[login] End");
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request) {
        LOG.info("[main] Start");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = String.valueOf(user.getAuthorities().iterator().next());
        HttpSession session = request.getSession();
        session.setAttribute("role", role);
        LOG.info("[main] End");
        return "redirect:home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        LOG.info("This is home");
        HttpSession session = request.getSession();
        String role=(String)session.getAttribute("role");
        String redirect="redirect:viewAccount";
        String isAdmin="ADMIN";
        if (!role.equals(isAdmin)){
            redirect="redirect:viewProduct";
        }
        return redirect;
    }

    @RequestMapping(value = "/catchError", method = RequestMethod.GET)
    public ModelAndView catchError() {
        LOG.info("[catchError] Start");
        ModelAndView model = new ModelAndView("redirect:error");
        LOG.info("[catchError] End");
        return model;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView showError() {
        LOG.info("[showError] Start");
        ModelAndView model = new ModelAndView("error");
        LOG.info("[showError] End");
        return model;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        LOG.info("[test] Start");
        byte[] b = ImageUtil.generateThumbNail("https://manlm.s3.amazonaws.com/botle7up.png");
        if (b.length != 0) {
            s3Service.uploadObject("s3://manlm/" + "testkm", b, "testkm");
        }
        LOG.info("[test] End");
        return "";
    }
}