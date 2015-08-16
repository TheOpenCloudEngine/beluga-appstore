package org.opencloudengine.serviceportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by swsong on 2015. 8. 16..
 */

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:store.html");
        return mav;
    }

    @RequestMapping("/signUp")
    public ModelAndView signUp() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signUp");
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
}
