package com.fu.api.controller;

import com.fu.api.model.Account;
import com.fu.api.model.AreaObj;
import com.fu.api.model.NameObj;
import com.fu.api.service.AreaService;
import com.fu.cache.client.JedisClient;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by manlm on 11/16/2016.
 */
@RestController
public class ApiAreaController {

    private static final Logger LOG = Logger.getLogger(ApiBeaconController.class);

    private final JedisClient jedisClient;

    private final AreaService areaService;

    @Autowired
    public ApiAreaController(JedisClient jedisClient, AreaService areaService) {
        this.jedisClient = jedisClient;
        this.areaService = areaService;
    }

    @RequestMapping(value = "/getAreaStaff", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getAreaStaff(@RequestHeader(value = "Device-Token") String deviceToken,
                                               @RequestBody Account account) {
        LOG.info("[getArea] Start");
        String savedToken = (String) jedisClient.get(account.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                jedisClient.set(account.getUsername(), savedToken);
                LOG.info("[getArea] End");
                return new ResponseEntity<>(new Gson().toJson(areaService.getAll()), HttpStatus.OK);
            }
        }

        LOG.info("[getArea] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/getAreaInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getAreaInfo(@RequestHeader(value = "Device-Token") String deviceToken,
                                                @RequestBody NameObj nameObj) {
        LOG.info("[getAreaInfo] Start");
        String savedToken = (String) jedisClient.get(nameObj.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                jedisClient.set(nameObj.getUsername(), savedToken);
                LOG.info("[getBeacon] End");
                return new ResponseEntity<>(new Gson().toJson(areaService.getInfo(nameObj.getName())), HttpStatus.OK);
            }
        }
        LOG.info("[getAreaInfo] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/insertArea", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> insertArea(@RequestHeader(value = "Device-Token") String deviceToken,
                                             @RequestBody AreaObj areaObj) {
        LOG.info("[insertArea] Start");
        String savedToken = (String) jedisClient.get(areaObj.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                boolean result = areaService.insert(areaObj);
                jedisClient.set(areaObj.getUsername(), savedToken);
                if (result) {
                    LOG.info("[insertArea] End");
                    return new ResponseEntity<>("", HttpStatus.OK);
                }
                LOG.info("[insertArea] End");
                return new ResponseEntity<>("", HttpStatus.ALREADY_REPORTED);
            }
        }

        LOG.info("[insertArea] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/updateArea", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> updateArea(@RequestHeader(value = "Device-Token") String deviceToken,
                                             @RequestBody AreaObj areaObj) {
        LOG.info("[updateArea] Start");
        String savedToken = (String) jedisClient.get(areaObj.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                int result = areaService.update(areaObj);
                jedisClient.set(areaObj.getUsername(), savedToken);
                if (result == 1) {
                    LOG.info("[updateArea] End");
                    return new ResponseEntity<>("", HttpStatus.OK);
                } else if (result == -1) {
                    LOG.info("[updateArea] End");
                    return new ResponseEntity<>("", HttpStatus.ALREADY_REPORTED);
                }
                LOG.info("[updateArea] End");
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        }
        LOG.info("[updateArea] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }
}
