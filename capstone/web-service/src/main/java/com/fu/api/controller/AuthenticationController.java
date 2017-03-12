package com.fu.api.controller;

import com.fu.api.model.Account;
import com.fu.api.service.AuthenticationService;
import com.fu.cache.client.JedisClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by manlm on 11/11/2016.
 */
@RestController
public class AuthenticationController {

    private static final Logger LOG = Logger.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    private final JedisClient jedisClient;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JedisClient jedisClient) {
        this.authenticationService = authenticationService;
        this.jedisClient = jedisClient;
    }

    @RequestMapping(value = "/staffLogin", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> staffLogin(@RequestBody Account account) {
        LOG.info("[staffLogin] Start");
        String token = authenticationService.login(account);
        if (!"".equals(token)) {
            jedisClient.set(account.getUsername(), token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        LOG.info("[staffLogin] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/reauthorize", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reauthorize(@RequestHeader(value = "Device-Token") String deviceToken,
                                              @RequestBody Account account) {
        LOG.info("[reauthorize] Start");
        String savedToken = (String) jedisClient.get(account.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                jedisClient.set(account.getUsername(), savedToken);
                LOG.info("[reauthorize] End");
                return new ResponseEntity<>("", HttpStatus.OK);
            }
        }
        LOG.info("[reauthorize] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }
}
