package com.lithanarianaren.notavito.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdvertisementController {

    @RequestMapping("/advertisements")
    public String index() {
        return "";
    }
}
