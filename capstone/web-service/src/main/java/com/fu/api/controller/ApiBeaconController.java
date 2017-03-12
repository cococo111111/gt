package com.fu.api.controller;

import com.fu.api.model.Account;
import com.fu.api.model.BeaconObj;
import com.fu.api.model.MacObj;
import com.fu.api.service.BeaconService;
import com.fu.api.service.IsmbRestService;
import com.fu.cache.client.JedisClient;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by manlm on 11/11/2016.
 */
@RestController
public class ApiBeaconController {

    private static final Logger LOG = Logger.getLogger(ApiBeaconController.class);

    private final BeaconService beaconService;

    private final JedisClient jedisClient;

    private final IsmbRestService ismbRestService;

    @Autowired
    public ApiBeaconController(BeaconService beaconService, JedisClient jedisClient, IsmbRestService ismbRestService) {
        this.beaconService = beaconService;
        this.jedisClient = jedisClient;
        this.ismbRestService = ismbRestService;
    }

    @RequestMapping(value = "/getBeaconStaff", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getBeaconStaff(@RequestHeader(value = "Device-Token") String deviceToken,
                                                 @RequestBody Account account) {
        LOG.info("[getBeaconStaff] Start");
        String savedToken = (String) jedisClient.get(account.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                jedisClient.set(account.getUsername(), savedToken);
                LOG.info("[getBeaconStaff] End");
                return new ResponseEntity<>(new Gson().toJson(ismbRestService.getBeacon()), HttpStatus.OK);
            }
        }
        LOG.info("[getBeaconStaff] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/generateBeaconMinor", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> generateInfo(@RequestHeader(value = "Device-Token") String deviceToken,
                                               @RequestBody Account account) {
        LOG.info("[generateBeaconMinor] Start");
        String savedToken = (String) jedisClient.get(account.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                jedisClient.set(account.getUsername(), savedToken);
                LOG.info("[generateBeaconMinor] End");
                return new ResponseEntity<>(new Gson().toJson(beaconService.generateInfo()), HttpStatus.OK);
            }
        }
        LOG.info("[generateBeaconMinor] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/getBeaconInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getBeaconInfo(@RequestHeader(value = "Device-Token") String deviceToken,
                                                @RequestBody MacObj macObj) {
        LOG.info("[getBeaconInfo] Start");
        String savedToken = (String) jedisClient.get(macObj.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                jedisClient.set(macObj.getUsername(), savedToken);
                LOG.info("[getBeaconInfo] End");
                return new ResponseEntity<>(new Gson().toJson(beaconService.getInfo(macObj.getMacAddress())), HttpStatus.OK);
            }
        }
        LOG.info("[getBeaconInfo] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/insertBeacon", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> insertBeacon(@RequestHeader(value = "Device-Token") String deviceToken,
                                               @RequestBody BeaconObj beaconObj) {
        LOG.info("[insertBeacon] Start");
        String savedToken = (String) jedisClient.get(beaconObj.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                boolean result = beaconService.insert(beaconObj);
                jedisClient.set(beaconObj.getUsername(), savedToken);
                if (result) {
                    LOG.info("[insertBeacon] End");
                    return new ResponseEntity<>("", HttpStatus.OK);
                }
                LOG.info("[insertBeacon] End");
                return new ResponseEntity<>("", HttpStatus.ALREADY_REPORTED);
            }
        }
        LOG.info("[insertBeacon] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/updateBeacon", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> updateBeacon(@RequestHeader(value = "Device-Token") String deviceToken,
                                               @RequestBody BeaconObj beaconObj) {
        LOG.info("[updateBeacon] Start");
        String savedToken = (String) jedisClient.get(beaconObj.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                int result = beaconService.update(beaconObj);
                jedisClient.set(beaconObj.getUsername(), savedToken);
                if (result == 1) {
                    LOG.info("[updateBeacon] End");
                    return new ResponseEntity<>("", HttpStatus.OK);
                } else if (result == -1) {
                    LOG.info("[updateBeacon] End");
                    return new ResponseEntity<>("", HttpStatus.ALREADY_REPORTED);
                }
                LOG.info("[updateBeacon] End");
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        }
        LOG.info("[updateBeacon] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }
}
