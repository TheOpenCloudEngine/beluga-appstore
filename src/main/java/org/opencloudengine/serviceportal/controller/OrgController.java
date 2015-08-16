package org.opencloudengine.serviceportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by swsong on 2015. 8. 17..
 */

@Controller
@RequestMapping("/o")
public class OrgController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/o/apps");
        return mav;
    }

    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    public ModelAndView apps() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/apps");
        return mav;
    }

    @RequestMapping(value = "/appEdit", method = RequestMethod.GET)
    public ModelAndView appEdit() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/appEdit");
        return mav;
    }

    @RequestMapping(value = "/appInfo", method = RequestMethod.GET)
    public ModelAndView appInfo() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/appInfo");
        return mav;
    }

    @RequestMapping(value = "/appNew", method = RequestMethod.GET)
    public ModelAndView appNew() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/appNew");
        return mav;
    }

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public ModelAndView manage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/manage");
        return mav;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/profile");
        return mav;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("o/settings");
        return mav;
    }
}
