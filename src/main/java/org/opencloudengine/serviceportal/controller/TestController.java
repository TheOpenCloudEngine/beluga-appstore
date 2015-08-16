package org.opencloudengine.serviceportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opencloudengine.serviceportal.db.entity.Car;
import org.opencloudengine.serviceportal.service.GarudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private GarudaService garudaService;

    @RequestMapping(value = "/car", method = RequestMethod.GET)
    public void getCar(HttpServletResponse response) {

        Car car = new Car();
        car.setColor("Blue");
        car.setMiles(100);
        car.setVIN("1234");
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.getWriter().print(mapper.writeValueAsString(car));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/garudainfo")
    @ResponseBody
    public String testGaruda() {
        return garudaService.getEndPoint();
    }
}
