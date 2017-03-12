package com.fu.bom.handler;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by manlm on 7/23/2016.
 */
@Component
public class LoginFailedHandler implements AuthenticationFailureHandler {

    private static final Logger LOG = Logger.getLogger(LoginFailedHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        LOG.info("[onAuthenticationFailure] Start: username = " + username);
        LOG.info("[onAuthenticationFailure] End");
        response.sendRedirect("login?error=error&loggedUsername=" + username);
    }
}
