package com.fu.api.controller;

import com.fu.api.model.Account;
import com.fu.api.service.FloorService;
import com.fu.cache.client.JedisClient;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by manlm on 11/14/2016.
 */
@RestController
public class ApiFloorController {

    private static final Logger LOG = Logger.getLogger(ApiBeaconController.class);

    private final FloorService floorService;

    private final JedisClient jedisClient;

    @Autowired
    public ApiFloorController(FloorService floorService, JedisClient jedisClient) {
        this.floorService = floorService;
        this.jedisClient = jedisClient;
    }

    @RequestMapping(value = "/getFloor", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> getFloor(@RequestHeader(value = "Device-Token") String deviceToken,
                                           @RequestBody Account account) {
        LOG.info("[getFloor] Start");
        String savedToken = (String) jedisClient.get(account.getUsername());
        if (savedToken != null) {
            if (savedToken.equals(deviceToken)) {
                jedisClient.set(account.getUsername(), savedToken);
                LOG.info("[getFloor] End");
                return new ResponseEntity<>(new Gson().toJson(floorService.getAll()), HttpStatus.OK);
            }
        }

        LOG.info("[getFloor] End");
        return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("tầng trệt");
        list.add("tầng 1");
        list.add("tầng 3");
        list.add("tầng 5");
        list.add("tầng 4");
        list.add("tầng 2");
        Collections.sort(list);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
