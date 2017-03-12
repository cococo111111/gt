package com.fu.bom.handler;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by manlm on 7/24/2016.
 */
@Component
public class LogoutHandler implements LogoutSuccessHandler {

    private static final Logger LOG = Logger.getLogger(LogoutHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        LOG.info("[onLogoutSuccess] Start: username = " + username);
        LOG.info("[onLogoutSuccess] End");
        response.sendRedirect("login?loggedUsername=" + username);
    }
}
