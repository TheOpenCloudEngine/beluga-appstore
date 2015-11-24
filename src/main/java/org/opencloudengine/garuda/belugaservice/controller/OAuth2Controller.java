package org.opencloudengine.garuda.belugaservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by swsong on 2015. 11. 25..
 */

@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() throws Exception {
        return "Hello";
    }
}
