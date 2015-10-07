package org.opencloudengine.garuda.belugaservice.controller;

import org.opencloudengine.garuda.belugaservice.service.GarudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private GarudaService garudaService;


    @RequestMapping("/garudainfo")
    @ResponseBody
    public String testGaruda() {
        return garudaService.getEndPoint();
    }
}
